package de.bno.mgjvm.jvm;

import static de.bno.mgjvm.jvm.InstructionSet.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import de.bno.mgjvm.grafik.CallStack;
import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.ExecutionInformationFrame;
import de.bno.mgjvm.grafik.FieldPool;
import de.bno.mgjvm.grafik.ProgramCounter;
import de.bno.mgjvm.grafik.StackFrame;

public class JVM implements CallStack {

	private String[] prog;
	private ProgramCounter pc;
	private ConstantPool cp;
	private FieldPool fp;
	private ExecutionInformationFrame info;

	private boolean deleted;

	private Map<String, Integer> functionTable;
	private LinkedList<Integer> callStack;

	private JVMListener jvmListener;

	public JVM(String[] prog, ProgramCounter pc, ConstantPool cp, FieldPool fp,
			ExecutionInformationFrame info) {
		this.prog = prog;
		this.pc = pc;
		this.cp = cp;
		this.fp = fp;
		this.info = info;
		this.functionTable = new HashMap<String, Integer>(30, 0.6f);
		this.callStack = new LinkedList<Integer>();

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

		if (cmdIndex <= 0) {
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
			stackFrame.push(execICONST_(parts[0]) + "I");
		} else if (parts[0].equals("iadd")) {
			stackFrame.push(execIADD(stackFrame) + "I");
		} else if (parts[0].equals("isub")) {
			stackFrame.push(execISUB(stackFrame) + "I");
		} else if (parts[0].equals("imul")) {
			stackFrame.push(execIMUL(stackFrame) + "I");
		} else if (parts[0].equals("idiv")) {
			stackFrame.push(execIDIV(stackFrame) + "I");
		} else if (parts[0].equals("ineg")) {
			stackFrame.push(execINEG(stackFrame) + "I");
		} else if (parts[0].equals("iload")) {
			stackFrame.push(execILOAD(stackFrame, Integer(parts[1])) + "I");
		} else if (parts[0].startsWith("iload_")) {
			stackFrame.push(execILOAD_(stackFrame, parts[0]) + "I");
		} else if (parts[0].startsWith("istore_")) {
			execISTORE_(stackFrame, parts[0]);
		} else if (parts[0].equals("istore")) {
			execISTORE(stackFrame, Integer(parts[1]));
		} else if (parts[0].equals("ireturn")) {
			execIRETURN(stackFrame, info);
		} else if (parts[0].equals("i2b")) {
			stackFrame.push(execI2B(stackFrame) + "I");
		} else if (parts[0].equals("i2c")) {
			stackFrame.push(execI2C(stackFrame) + "I");
		} else if (parts[0].equals("iinc")) {
			execIINC(stackFrame, Integer(parts[1]), Byte(parts[2]));
		} else if (parts[0].equals("bipush")) {
			stackFrame.push(execBIPUSH(Byte(parts[1])) + "I");
		} else if (parts[0].equals("dup")) {
			execDUP(stackFrame);
		} else if (parts[0].startsWith("ldc")) {
			stackFrame.push(execLDC(cp, Integer.valueOf(parts[1])));
		} else if (parts[0].equals("return")) {
			execRETURN(pc, info, this);
		} else if (parts[0].equals("invokevirtual")) {
			execINVOKEVIRTUAL(index, stackFrame, parts[1], prog, this, info,
					pc, functionTable);
		}

	}

	private int getNextCmd() {
		int ret = 0;

		if (pc.getProgramCount() < 0) {
			return ret;
		}

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

		while ((prog[entryIndex] == null || prog[entryIndex].isEmpty() || isComment(prog[entryIndex]))
				&& (entryIndex < prog.length)) {
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
		int indexOfLocalVars = func.indexOf(':');
		int indexOfEnd = func.indexOf(';');

		if (indexOfParamStart < 0 || indexOfParamEnd < 0 || indexOfEnd < 0) {
			throw new JVMParseException("Can not find function in " + func);
		}

		if (indexOfParamEnd - indexOfParamStart == 1) {
			ret = (func.substring(0, indexOfParamStart + 1) + "V" + func
					.substring(
							indexOfParamEnd,
							(indexOfLocalVars > indexOfParamEnd) ? indexOfLocalVars
									: indexOfEnd)).replaceAll(" ", "");
		} else {
			ret = func.substring(
					0,
					(indexOfLocalVars > indexOfParamEnd) ? indexOfLocalVars
							: indexOfEnd).replaceAll(" ", "");
		}

		return ret;
	}

	public int popCallStack() {

		if (callStack.isEmpty()) {
			return -1;
		}

		int ret = callStack.getLast().intValue();
		callStack.removeLast();

		return ret;
	}

	public void pushCallStack(int i) {
		if (i >= 0) {
			callStack.add(new Integer(i));
		}
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
