package de.bno.mgjvm.jvm;

import de.bno.mgjvm.data.Variable;
import de.bno.mgjvm.grafik.CallStack;
import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.ExecutionInformationFrame;
import de.bno.mgjvm.grafik.ProgramCounter;
import de.bno.mgjvm.grafik.StackFrame;

public class InstructionSet {

	public static int execICONST_(String string) {
		int value = 0;

		switch (string.substring(string.indexOf('_') + 1)) {
		case "m1":
			value = -1;
			break;
		case "1":
			value = 1;
			break;
		case "2":
			value = 2;
			break;
		case "3":
			value = 3;
			break;
		case "4":
			value = 4;
			break;
		case "5":
			value = 5;
			break;
		}

		if (value == 0) {
			throw new JVMParseException(string + " unknown");
		}

		return value;
	}

	public static int execIADD(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("iadd wrong type on stack V1:" + v1
					+ " V2:" + v2);
		}
		ret = Integer.valueOf(Value(v1)).intValue()
				+ Integer.valueOf(Value(v2)).intValue();

		return ret;
	}

	public static int execISUB(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("isub wrong type on stack V1:" + v1
					+ " V2:" + v2);
		}
		ret = Integer.valueOf(Value(v2)).intValue()
				- Integer.valueOf(Value(v1)).intValue();

		return ret;
	}

	public static int execIMUL(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("imul wrong type on stack V1:" + v1
					+ " V2:" + v2);
		}
		ret = Integer.valueOf(Value(v2)).intValue()
				* Integer.valueOf(Value(v1)).intValue();

		return ret;
	}

	public static int execIDIV(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("idiv wrong type on stack V1:" + v1
					+ " V2:" + v2);
		}
		ret = Integer.valueOf(Value(v2)).intValue()
				/ Integer.valueOf(Value(v1)).intValue();

		return ret;
	}

	public static int execINEG(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("ineg wrong type on stack V1:" + v1);
		}
		ret = -Integer.valueOf(Value(v1)).intValue();

		return ret;
	}

	public static String execLDC(ConstantPool cp, int index) {

		String ret = "";

		if (index < 0 || index >= cp.getConstantCount()) {
			throw new JVMParseException("Constant index out of bounds " + index
					+ ". Max. is " + (cp.getConstantCount() - 1));
		}

		Variable constant = cp.getConstant(index);
		ret = constant.getValue() + constant.TypeChar();

		return ret;
	}

	public static void execRETURN(ProgramCounter pc,
			ExecutionInformationFrame info, CallStack cs) {
		pc.setProgramCount(cs.popCallStack());
		if (pc.getProgramCount() > 0) {
			info.popActiveStackFrame();
		}
	}

	private static String Value(String s) {
		return s.substring(0, s.length() - 1);
	}

}
