package de.bno.mgjvm.jvm;

import static de.bno.mgjvm.jvm.InstructionSet.*;

import java.util.HashMap;
import java.util.Map;

import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.ExecutionInformationFrame;
import de.bno.mgjvm.grafik.FieldPool;
import de.bno.mgjvm.grafik.ProgramCounter;
import de.bno.mgjvm.grafik.StackFrame;

public class JVM {

	private String[] prog;
	private ProgramCounter pc;
	private ConstantPool cp;
	private FieldPool fp;
	private ExecutionInformationFrame info;

	private boolean deleted;

	private Map<String, Integer> functionTable;

	private JVMListener jvmListener;

	public JVM(String[] prog, ProgramCounter pc, ConstantPool cp, FieldPool fp,
			ExecutionInformationFrame info) {
		this.prog = prog;
		this.pc = pc;
		this.cp = cp;
		this.fp = fp;
		this.info = info;
		this.functionTable = new HashMap<String, Integer>(30, 0.6f);

		createFunctionTable();
		if (!functionTable.containsKey("<init>(V)V")) {
			throw new JVMNoEntryException(
					"No program entry found. Missing function <init>(V)V");
		}

		pc.setProgramCount(0);
		pc.setActualCommand("Ready search <init>(V)V...");

		int entryIndex = goToEntry();
		pc.setProgramCount(entryIndex + 1);
		pc.setActualCommand(prog[entryIndex]);
	}

	/**
	 * Führt einen einzigen Befehl aus
	 * 
	 * @return true wenn jvm noch läuft
	 */
	public synchronized boolean execute() {

		executeActual();

		int cmdIndex = getNextCmd();

		if (cmdIndex == 0) {
			delete();
		} else {
			pc.setProgramCount(cmdIndex + 1);
			pc.setActualCommand(prog[cmdIndex]);
		}

		return !isDeleted();
	}

	private void executeActual() {
		// TODO: Execute command
		int index = pc.getProgramCount() - 1;
		String cmd = prog[index];

		String[] parts = cmd.split("[\t\\s]+");
		StackFrame stackFrame = info.peekActiveStackFrame();

		if (parts[0].startsWith("iconst_")) {
			int addConst = execICONST_(parts[0]);
			stackFrame.push(addConst + "I");
		} else if (parts[0].equals("iadd")) {
			stackFrame.push(execIADD(stackFrame) + "I");
		} else if (parts[0].equals("isub")) {
			stackFrame.push(execISUB(stackFrame) + "I");
		}

	}

	private int getNextCmd() {
		int ret = 0;

		for (int i = pc.getProgramCount(); i < prog.length; i++) {
			if (prog[i] != null && !prog[i].isEmpty() && !isComment(prog[i])) {
				ret = i;
				break;
			}
		}

		return ret;
	}

	private int goToEntry() {
		int entryIndex = functionTable.get("<init>(V)V") + 1;

		while (prog[entryIndex] == null || prog[entryIndex].isEmpty()
				|| isComment(prog[entryIndex])) {
			entryIndex++;
		}

		info.createNewStackFrame("this");

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

		if (isDeleted()) {
			return;
		}

		pc.setProgramCount(0);
		pc.setActualCommand("Deleted");
		pc = null;
		cp = null;
		fp = null;
		functionTable = null;
		prog = null;
		// TODO: Delete JVM

		deleted = true;

		if (jvmListener != null) {
			jvmListener.executionFinished("deleted");
		}
	}

	public void setJVMListener(JVMListener listener) {
		this.jvmListener = listener;
	}

	public boolean isDeleted() {

		return deleted;
	}
}
