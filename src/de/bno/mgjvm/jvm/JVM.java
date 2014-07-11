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
		} else if (parts[0].equals("iand")) {
			stackFrame.push(execIAND(stackFrame) + "I");
		} else if (parts[0].equals("ior")) {
			stackFrame.push(execIOR(stackFrame) + "I");
		} else if (parts[0].equals("ixor")) {
			stackFrame.push(execIXOR(stackFrame) + "I");
		} else if (parts[0].equals("irem")) {
			stackFrame.push(execIREM(stackFrame) + "I");
		} else if (parts[0].equals("ishl")) {
			stackFrame.push(execISHL(stackFrame) + "I");
		} else if (parts[0].equals("ishr")) {
			stackFrame.push(execISHR(stackFrame) + "I");
		} else if (parts[0].equals("iushr")) {
			stackFrame.push(execIUSHR(stackFrame) + "I");
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
			execIRETURN(stackFrame, info, this, pc);
		} else if (parts[0].equals("i2b")) {
			stackFrame.push(execI2B(stackFrame) + "I");
		} else if (parts[0].equals("i2c")) {
			stackFrame.push(execI2C(stackFrame) + "I");
		} else if (parts[0].equals("i2d")) {
			stackFrame.push(execI2D(stackFrame) + "D");
		} else if (parts[0].equals("i2f")) {
			stackFrame.push(execI2F(stackFrame) + "F");
		} else if (parts[0].equals("i2l")) {
			stackFrame.push(execI2L(stackFrame) + "J");
		} else if (parts[0].equals("i2s")) {
			stackFrame.push(execI2S(stackFrame) + "I");
		} else if (parts[0].equals("iinc")) {
			execIINC(stackFrame, Integer(parts[1]), Byte(parts[2]));
		} else if (parts[0].startsWith("if_icmp")) {
			execIF_ICMP(parts[0].substring(7), Integer(parts[1]), prog, pc,
					stackFrame);
		} else if (parts[0].startsWith("if")) {
			execIF(parts[0].substring(2), Integer(parts[1]), prog, pc,
					stackFrame);
		} else if (parts[0].equals("bipush")) {
			stackFrame.push(execBIPUSH(Byte(parts[1])) + "I");
		} else if (parts[0].equals("sipush")) {
			stackFrame.push(execSIPUSH(Short(parts[1])) + "I");
		} else if (parts[0].startsWith("lconst_")) {
			stackFrame.push(execLCONST_(parts[0]) + "J");
		} else if (parts[0].equals("l2d")) {
			stackFrame.push(execL2D(stackFrame) + "D");
		} else if (parts[0].equals("l2f")) {
			stackFrame.push(execL2F(stackFrame) + "F");
		} else if (parts[0].equals("l2i")) {
			stackFrame.push(execL2I(stackFrame) + "I");
		} else if (parts[0].equals("ladd")) {
			stackFrame.push(execLADD(stackFrame) + "J");
		} else if (parts[0].equals("land")) {
			stackFrame.push(execLAND(stackFrame) + "J");
		} else if (parts[0].equals("lcmp")) {
			stackFrame.push(execLCMP(stackFrame) + "I");
		} else if (parts[0].equals("ldiv")) {
			stackFrame.push(execLDIV(stackFrame) + "J");
		} else if (parts[0].equals("lmul")) {
			stackFrame.push(execLMUL(stackFrame) + "J");
		} else if (parts[0].equals("lneg")) {
			stackFrame.push(execLNEG(stackFrame) + "J");
		} else if (parts[0].equals("lor")) {
			stackFrame.push(execLOR(stackFrame) + "J");
		} else if (parts[0].equals("lrem")) {
			stackFrame.push(execLREM(stackFrame) + "J");
		} else if (parts[0].equals("lshl")) {
			stackFrame.push(execLSHL(stackFrame) + "J");
		} else if (parts[0].equals("lshr")) {
			stackFrame.push(execLSHR(stackFrame) + "J");
		} else if (parts[0].equals("lsub")) {
			stackFrame.push(execLSUB(stackFrame) + "J");
		} else if (parts[0].equals("lushr")) {
			stackFrame.push(execLUSHR(stackFrame) + "J");
		} else if (parts[0].equals("lxor")) {
			stackFrame.push(execLXOR(stackFrame) + "J");
		} else if (parts[0].equals("lload")) {
			stackFrame.push(execLLOAD(stackFrame, Integer(parts[1])) + "J");
		} else if (parts[0].startsWith("lload_")) {
			stackFrame.push(execLLOAD_(stackFrame, parts[0]) + "J");
		} else if (parts[0].startsWith("lstore_")) {
			execLSTORE_(stackFrame, parts[0]);
		} else if (parts[0].equals("lstore")) {
			execLSTORE(stackFrame, Integer(parts[1]));
		} else if (parts[0].equals("lreturn")) {
			execLRETURN(stackFrame, info, this, pc);
		} else if (parts[0].equals("f2d")) {
			stackFrame.push(execF2D(stackFrame) + "D");
		} else if (parts[0].equals("f2i")) {
			stackFrame.push(execF2I(stackFrame) + "I");
		} else if (parts[0].equals("f2l")) {
			stackFrame.push(execF2L(stackFrame) + "J");
		} else if (parts[0].equals("fadd")) {
			stackFrame.push(execFADD(stackFrame) + "F");
		} else if (parts[0].equals("fdiv")) {
			stackFrame.push(execFDIV(stackFrame) + "F");
		} else if (parts[0].equals("fmul")) {
			stackFrame.push(execFMUL(stackFrame) + "F");
		} else if (parts[0].equals("fneg")) {
			stackFrame.push(execFNEG(stackFrame) + "F");
		} else if (parts[0].equals("fsub")) {
			stackFrame.push(execFSUB(stackFrame) + "F");
		} else if (parts[0].equals("frem")) {
			stackFrame.push(execFREM(stackFrame) + "F");
		} else if (parts[0].startsWith("fcmp")) {
			stackFrame.push(execFCMP(stackFrame, parts[0]) + "I");
		} else if (parts[0].startsWith("fconst_")) {
			stackFrame.push(execFCONST_(parts[0]) + "F");
		} else if (parts[0].equals("fload")) {
			stackFrame.push(execFLOAD(stackFrame, Integer(parts[1])) + "F");
		} else if (parts[0].startsWith("fload_")) {
			stackFrame.push(execFLOAD_(stackFrame, parts[0]) + "F");
		} else if (parts[0].startsWith("fstore_")) {
			execFSTORE_(stackFrame, parts[0]);
		} else if (parts[0].equals("fstore")) {
			execFSTORE(stackFrame, Integer(parts[1]));
		} else if (parts[0].equals("freturn")) {
			execFRETURN(stackFrame, info, this, pc);
		} else if (parts[0].equals("d2f")) {
			stackFrame.push(execD2F(stackFrame) + "F");
		} else if (parts[0].equals("d2i")) {
			stackFrame.push(execD2I(stackFrame) + "I");
		} else if (parts[0].equals("d2l")) {
			stackFrame.push(execD2L(stackFrame) + "J");
		} else if (parts[0].equals("dup")) {
			execDUP(stackFrame);
		} else if (parts[0].equals("swap")) {
			execSWAP(stackFrame);
		} else if (parts[0].equals("pop")) {
			execPOP(stackFrame);
		} else if (parts[0].equals("goto")) {
			execGOTO(Integer(parts[1]), prog, pc);
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
			if (!isComment(prog[i]) && prog[i].endsWith(";")) {
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
