package de.bno.mgjvm.jvm;

import java.util.Map;

import de.bno.mgjvm.data.Variable;
import de.bno.mgjvm.grafik.CallStack;
import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.ExecutionInformationFrame;
import de.bno.mgjvm.grafik.ProgramCounter;
import de.bno.mgjvm.grafik.StackFrame;

public class InstructionSet {

	private static final String DEF_EMPTY_LOCAL_VAR = "-";
	private static final String typeValues = "IJSBCDF";

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
		ret = Integer(Value(v1)) + Integer(Value(v2));

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
		ret = Integer(Value(v2)) - Integer(Value(v1));

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
		ret = Integer(Value(v2)) * Integer(Value(v1));

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
		ret = Integer(Value(v2)) / Integer(Value(v1));

		return ret;
	}

	public static int execINEG(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("ineg wrong type on stack V1: " + v1);
		}
		ret = -Integer(Value(v1));

		return ret;
	}

	public static int execILOAD_(StackFrame sf, String iload) {

		int index = Integer(iload.substring(iload.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(iload + " unknown");
		}

		return execILOAD(sf, index);
	}

	public static int execILOAD(StackFrame sf, int index) {

		int ret;

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.getField(index);

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("iload wrong type on stack: " + v1);
		}
		ret = Integer(Value(v1));

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

	public static void execINVOKEVIRTUAL(int callIndex,
			StackFrame callStackFrame, String callFunction, String[] prog,
			CallStack cs, ExecutionInformationFrame info, ProgramCounter pc,
			Map<String, Integer> ft) {

		cs.pushCallStack(callIndex + 1);

		String funcId = callFunction.replaceAll(" ", "");
		int funcEntry = ft.get(funcId);

		pc.setProgramCount(funcEntry + 1);

		String funcDecl = prog[funcEntry].replaceAll(" ", "");

		int beginIndex = funcDecl.indexOf(':') + 1;
		int endIndex = funcDecl.indexOf(';');
		int localVars = ((beginIndex > 0) ? Integer.valueOf(
				funcDecl.substring(beginIndex, endIndex)).intValue() : 0) + 1;

		String[] vars = new String[localVars];
		vars[0] = "this";

		int paramCount = getParamCount(funcDecl);

		String params = getParamString(funcDecl);

		for (int i = 1; i < paramCount + 1; i++) {
			vars[i] = callStackFrame.pop();

			char typeChar = vars[i].charAt(vars[i].length() - 1);
			if (typeChar != params.charAt(i - 1)) {
				throw new JVMTypeException("Found wrong type: "
						+ params.charAt(i - 1) + " expected but found "
						+ typeChar);
			}
		}

		for (int i = 1 + paramCount; i < vars.length; i++) {
			vars[i] = DEF_EMPTY_LOCAL_VAR;
		}

		info.createNewStackFrame(vars);
	}

	private static String getParamString(String funcDecl) {
		int beginIndex = funcDecl.indexOf('(') + 1;
		int endIndex = funcDecl.indexOf(')');
		String params = funcDecl.substring(beginIndex, endIndex);

		return params;
	}

	private static int getParamCount(String funcDecl) {

		int ret = 0;

		String params = getParamString(funcDecl);

		for (int i = 0; i < params.length(); i++) {
			if (typeValues.indexOf(params.charAt(i)) >= 0) {
				ret++;
			}
		}

		return ret;
	}

	private static String Value(String s) {
		return s.trim().substring(0, s.length() - 1);
	}

	public static int Integer(String s) {
		return Integer.valueOf(s).intValue();
	}

}
