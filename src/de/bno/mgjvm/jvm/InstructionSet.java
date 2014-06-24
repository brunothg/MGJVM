package de.bno.mgjvm.jvm;

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

	private static String Value(String s) {
		return s.substring(0, s.length() - 1);
	}
}
