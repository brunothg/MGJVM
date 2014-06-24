package de.bno.mgjvm.jvm;

import java.util.HashMap;
import java.util.Map;

import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.FieldPool;
import de.bno.mgjvm.grafik.ProgramCounter;

public class JVM {

	private String[] prog;
	private ProgramCounter pc;
	private ConstantPool cp;
	private FieldPool fp;

	private boolean deleted;

	private Map<String, Integer> functionTable;

	private JVMListener jvmListener;

	public JVM(String[] prog, ProgramCounter pc, ConstantPool cp, FieldPool fp) {
		this.prog = prog;
		this.pc = pc;
		this.cp = cp;
		this.fp = fp;
		this.functionTable = new HashMap<String, Integer>(30, 0.6f);

		createFunctionTable();
		if (!functionTable.containsKey("<init>(V)V")) {
			throw new JVMNoEntryException(
					"No program entry found. Missing function <init>(V)V");
		}

		pc.setProgramCount(0);
		pc.setActualCommand("Ready search <init>(V)V...");

		int entryIndex = goToEntry();
		pc.setProgramCount(entryIndex);
		pc.setActualCommand(prog[entryIndex]);
	}

	private int goToEntry() {
		int entryIndex = functionTable.get("<init>(V)V") + 1;

		while (prog[entryIndex] == null || prog[entryIndex].isEmpty()
				|| isComment(prog[entryIndex])) {
			entryIndex++;
		}

		return entryIndex;
	}

	private boolean isComment(String string) {

		if (string.startsWith("//")) {
			return true;
		}

		return false;
	}

	private void createFunctionTable() {

		for (int i = 0; i < prog.length; i++) {
			if (prog[i].endsWith(";")) {
				functionTable.put(getFunctionIdentifier(prog[i]),
						new Integer(i));
			}
		}
	}

	private String getFunctionIdentifier(String func) {
		String ret = "";

		int indexOfParamStart = func.indexOf('(');
		int indexOfParamEnd = func.indexOf(')');
		int indexOfEnd = func.indexOf(';');

		if (indexOfParamStart < 0 || indexOfParamEnd < 0 || indexOfEnd < 0) {
			throw new JVMParseException("Can not find function in " + func);
		}

		if (indexOfParamEnd - indexOfParamStart == 1) {
			ret = (func.substring(0, indexOfParamStart + 1) + "V" + func
					.substring(indexOfParamEnd, indexOfEnd))
					.replaceAll(" ", "");
		} else {
			ret = func.substring(0, indexOfEnd).replaceAll(" ", "");
		}

		return ret;
	}

	public void delete() {
		pc.setProgramCount(0);
		pc.setActualCommand("Deleted");
		pc = null;
		cp = null;
		fp = null;
		functionTable = null;
		prog = null;
		// TODO: Delete JVM

		deleted = true;
	}

	public void setJVMListener(JVMListener listener) {
		this.jvmListener = listener;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
