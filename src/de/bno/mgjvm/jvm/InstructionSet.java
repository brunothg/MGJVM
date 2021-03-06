package de.bno.mgjvm.jvm;

import java.util.Map;

import de.bno.mgjvm.data.Variable;
import de.bno.mgjvm.grafik.CallStack;
import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.ExecutionInformationFrame;
import de.bno.mgjvm.grafik.ProgramCounter;
import de.bno.mgjvm.grafik.StackFrame;

public class InstructionSet {

	private static final double DOUBLE_DEF = 0.0;
	private static final float FLOAT_DEF = 0.0f;
	private static final int INTEGER_DEF = 0;
	private static final long LONG_DEF = 0;
	private static final String DEF_EMPTY_LOCAL_VAR = "-";
	private static final String typeValues = "IJSBCDF";

	public static int execICONST_(String string) {
		int value = -2;

		switch (string.substring(string.indexOf('_') + 1)) {
		case "m1":
			value = -1;
			break;
		case "0":
			value = 0;
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

		if (value == -2) {
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
			throw new JVMTypeException("iadd wrong type on stack V1:" + v2
					+ " V2:" + v1);
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
			throw new JVMTypeException("isub wrong type on stack V1:" + v2
					+ " V2:" + v1);
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
			throw new JVMTypeException("imul wrong type on stack V1:" + v2
					+ " V2:" + v1);
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
			throw new JVMTypeException("idiv wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Integer(Value(v2)) / Integer(Value(v1));

		return ret;
	}

	public static int execIAND(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("iand wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Integer(Value(v2)) & Integer(Value(v1));

		return ret;
	}

	public static int execIOR(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("ior wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Integer(Value(v2)) | Integer(Value(v1));

		return ret;
	}

	public static int execIXOR(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("ixor wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Integer(Value(v2)) ^ Integer(Value(v1));

		return ret;
	}

	public static int execIREM(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("irem wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		int iv2 = Integer(Value(v2));
		int iv1 = Integer(Value(v1));
		ret = iv2 - (iv2 / iv1) * iv1;

		return ret;
	}

	public static int execISHL(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("ishl wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		int iv2 = Integer(Value(v2));
		int iv1 = Integer(Value(v1));
		ret = iv2 << (iv1 & 0x1f);

		return ret;
	}

	public static int execISHR(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("ishr wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		int iv2 = Integer(Value(v2));
		int iv1 = Integer(Value(v1));
		ret = iv2 >> (iv1 & 0x1f);

		return ret;
	}

	public static int execIUSHR(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("ishr wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		int iv2 = Integer(Value(v2));
		int iv1 = Integer(Value(v1));
		ret = iv2 >>> (iv1 & 0x1f);

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

		if (v1.equals(DEF_EMPTY_LOCAL_VAR)) {
			return INTEGER_DEF;
		}

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("iload wrong type on stack: " + v1);
		}
		ret = Integer(Value(v1));

		return ret;
	}

	public static void execISTORE_(StackFrame sf, String istore) {

		int index = Integer(istore.substring(istore.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(istore + " unknown");
		}

		execISTORE(sf, index);
	}

	public static void execISTORE(StackFrame sf, int index) {

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("istore wrong type on stack: " + v1);
		}

		sf.setField(v1, index);
	}

	public static void execIRETURN(StackFrame sf,
			ExecutionInformationFrame eif, CallStack cs, ProgramCounter pc) {

		eif.popActiveStackFrame();

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("ireturn wrong type on stack V1: " + v1);
		}
		ret = Integer(Value(v1));

		StackFrame stackFrame = eif.peekActiveStackFrame();
		stackFrame.push(ret + "I");

		execRETURN(pc, null, cs);
	}

	public static int execI2B(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("i2b wrong type on stack V1: " + v1);
		}
		ret = (int) ((byte) Integer(Value(v1)));

		return ret;
	}

	public static int execI2C(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("i2c wrong type on stack V1: " + v1);
		}
		ret = (int) ((char) Integer(Value(v1)));

		return ret;
	}

	public static double execI2D(StackFrame sf) {

		double ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("i2c wrong type on stack V1: " + v1);
		}

		ret = (double) Integer(Value(v1));

		return ret;
	}

	public static float execI2F(StackFrame sf) {

		float ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("i2c wrong type on stack V1: " + v1);
		}

		ret = (float) Integer(Value(v1));

		return ret;
	}

	public static long execI2L(StackFrame sf) {

		long ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("i2c wrong type on stack V1: " + v1);
		}

		ret = (long) Integer(Value(v1));

		return ret;
	}

	public static int execI2S(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("i2c wrong type on stack V1: " + v1);
		}

		ret = (int) ((short) Integer(Value(v1)));

		return ret;
	}

	public static void execIINC(StackFrame sf, int index, byte add) {

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		String v1 = sf.getField(index);

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("iinc wrong type on stack V1: " + v1);
		}

		int ret = Integer(Value(v1)) + (int) add;

		sf.setField(ret + "I", index);
	}

	public static void execIF_ICMP(String cond, int line, String[] prog,
			ProgramCounter pc, StackFrame sf) {

		boolean branch = false;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("I"))) {
			throw new JVMTypeException("if_icmp" + cond
					+ " wrong type on stack V1:" + v2 + " V2:" + v1);
		}

		switch (cond) {
		case "eq":
			branch = Integer(Value(v2)) == Integer(Value(v1));
			break;
		case "ne":
			branch = Integer(Value(v2)) != Integer(Value(v1));
			break;
		case "lt":
			branch = Integer(Value(v2)) < Integer(Value(v1));
			break;
		case "le":
			branch = Integer(Value(v2)) <= Integer(Value(v1));
			break;
		case "gt":
			branch = Integer(Value(v2)) > Integer(Value(v1));
			break;
		case "ge":
			branch = Integer(Value(v2)) >= Integer(Value(v1));
			break;
		default:
			throw new JVMParseException("if_icmp<cond> unknown cond: " + cond);
		}

		if (branch) {
			execGOTO(line, prog, pc);
		}

	}

	public static void execIF(String cond, int line, String[] prog,
			ProgramCounter pc, StackFrame sf) {

		boolean branch = false;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("I"))) {
			throw new JVMTypeException("if" + cond + " wrong type on stack V1:"
					+ v1);
		}

		switch (cond) {
		case "eq":
			branch = Integer(Value(v1)) == 0;
			break;
		case "ne":
			branch = Integer(Value(v1)) != 0;
			break;
		case "lt":
			branch = Integer(Value(v1)) < 0;
			break;
		case "le":
			branch = Integer(Value(v1)) <= 0;
			break;
		case "gt":
			branch = Integer(Value(v1)) > 0;
			break;
		case "ge":
			branch = Integer(Value(v1)) >= 0;
			break;
		default:
			throw new JVMParseException("if<cond> unknown cond: " + cond);
		}

		if (branch) {
			execGOTO(line, prog, pc);
		}
	}

	public static int execBIPUSH(byte value) {
		return (int) value;
	}

	public static int execSIPUSH(short value) {
		return (int) value;
	}

	public static long execLCONST_(String string) {
		long value = -2;

		switch (string.substring(string.indexOf('_') + 1)) {
		case "0":
			value = 0;
			break;
		case "1":
			value = 1;
			break;
		}

		if (value == -2) {
			throw new JVMParseException(string + " unknown");
		}

		return value;
	}

	public static double execL2D(StackFrame sf) {

		double ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("J"))) {
			throw new JVMTypeException("l2d wrong type on stack V1: " + v1);
		}
		ret = (double) Long(Value(v1));

		return ret;
	}

	public static float execL2F(StackFrame sf) {

		float ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("J"))) {
			throw new JVMTypeException("l2f wrong type on stack V1: " + v1);
		}
		ret = (float) Long(Value(v1));

		return ret;
	}

	public static int execL2I(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("J"))) {
			throw new JVMTypeException("l2i wrong type on stack V1: " + v1);
		}
		ret = (int) Long(Value(v1));

		return ret;
	}

	public static long execLADD(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("ladd wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) + Long(Value(v1));

		return ret;
	}

	public static long execLAND(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("land wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) & Long(Value(v1));

		return ret;
	}

	public static int execLCMP(StackFrame sf) {

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("lcmp wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		long dif = Long(Value(v2)) - Long(Value(v1));
		;

		if (dif == 0) {
			ret = 0;
		} else if (dif > 0) {
			ret = 1;
		} else {
			ret = -1;
		}

		return ret;
	}

	public static long execLDIV(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("ldiv wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) / Long(Value(v1));

		return ret;
	}

	public static long execLMUL(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("lmul wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) * Long(Value(v1));

		return ret;
	}

	public static long execLNEG(StackFrame sf) {

		long ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("J"))) {
			throw new JVMTypeException("lneg wrong type on stack V1:" + v1);
		}
		ret = -Long(Value(v1));

		return ret;
	}

	public static long execLOR(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("lor wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) | Long(Value(v1));

		return ret;
	}

	public static long execLREM(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("lrem wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		long v2l = Long(Value(v2));
		long v1l = Long(Value(v1));

		ret = v2l - (v2l / v1l) * v1l;

		return ret;
	}

	public static long execLSHL(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("J"))) {
			throw new JVMTypeException("lshl wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) << Integer(Value(v1));

		return ret;
	}

	public static long execLSUB(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("lsub wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) - Long(Value(v1));

		return ret;
	}

	public static long execLSHR(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("J"))) {
			throw new JVMTypeException("lshr wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) >> Integer(Value(v1));

		return ret;
	}

	public static long execLUSHR(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("I") && v2.endsWith("J"))) {
			throw new JVMTypeException("lushr wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) >>> Integer(Value(v1));

		return ret;
	}

	public static long execLXOR(StackFrame sf) {

		long ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("J") && v2.endsWith("J"))) {
			throw new JVMTypeException("lxor wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Long(Value(v2)) ^ Long(Value(v1));

		return ret;
	}

	public static long execLLOAD_(StackFrame sf, String lload) {

		int index = Integer(lload.substring(lload.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(lload + " unknown");
		}

		return execLLOAD(sf, index);
	}

	public static long execLLOAD(StackFrame sf, int index) {

		long ret;

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.getField(index);

		if (v1.equals(DEF_EMPTY_LOCAL_VAR)) {
			return LONG_DEF;
		}

		if (!(v1.endsWith("J"))) {
			throw new JVMTypeException("lload wrong type on stack: " + v1);
		}
		ret = Long(Value(v1));

		return ret;
	}

	public static void execLSTORE_(StackFrame sf, String lstore) {

		int index = Integer(lstore.substring(lstore.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(lstore + " unknown");
		}

		execLSTORE(sf, index);
	}

	public static void execLSTORE(StackFrame sf, int index) {

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.pop();

		if (!(v1.endsWith("J"))) {
			throw new JVMTypeException("lstore wrong type on stack: " + v1);
		}

		sf.setField(v1, index);
	}

	public static void execLRETURN(StackFrame sf,
			ExecutionInformationFrame eif, CallStack cs, ProgramCounter pc) {

		eif.popActiveStackFrame();

		long ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("J"))) {
			throw new JVMTypeException("lreturn wrong type on stack V1: " + v1);
		}
		ret = Long(Value(v1));

		StackFrame stackFrame = eif.peekActiveStackFrame();
		stackFrame.push(ret + "J");

		execRETURN(pc, null, cs);
	}

	public static double execF2D(StackFrame sf) {

		double ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("F"))) {
			throw new JVMTypeException("f2d wrong type on stack V1: " + v1);
		}
		ret = (double) Float(Value(v1));

		return ret;
	}

	public static int execF2I(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("F"))) {
			throw new JVMTypeException("f2i wrong type on stack V1: " + v1);
		}
		ret = (int) Float(Value(v1));

		return ret;
	}

	public static long execF2L(StackFrame sf) {

		long ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("F"))) {
			throw new JVMTypeException("f2l wrong type on stack V1: " + v1);
		}
		ret = (long) Float(Value(v1));

		return ret;
	}

	public static float execFADD(StackFrame sf) {

		float ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("F") && v2.endsWith("F"))) {
			throw new JVMTypeException("fadd wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Float(Value(v2)) + Float(Value(v1));

		return ret;
	}

	public static float execFDIV(StackFrame sf) {

		float ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("F") && v2.endsWith("F"))) {
			throw new JVMTypeException("fdiv wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Float(Value(v2)) / Float(Value(v1));

		return ret;
	}

	public static float execFMUL(StackFrame sf) {

		float ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("F") && v2.endsWith("F"))) {
			throw new JVMTypeException("fmul wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Float(Value(v2)) * Float(Value(v1));

		return ret;
	}

	public static float execFNEG(StackFrame sf) {

		float ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("F"))) {
			throw new JVMTypeException("fadd wrong type on stack V1:" + v1);
		}
		ret = -Float(Value(v1));

		return ret;
	}

	public static float execFSUB(StackFrame sf) {

		float ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("F") && v2.endsWith("F"))) {
			throw new JVMTypeException("fadd wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Float(Value(v2)) - Float(Value(v1));

		return ret;
	}

	public static float execFREM(StackFrame sf) {

		float ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("F") && v2.endsWith("F"))) {
			throw new JVMTypeException("frem wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Float(Value(v2)) % Float(Value(v1));

		return ret;
	}

	public static int execFCMP(StackFrame sf, String fcmp) {

		boolean greater;

		if (fcmp.trim().charAt(fcmp.length() - 1) == 'g') {
			greater = true;
		} else if (fcmp.trim().charAt(fcmp.length() - 1) == 'l') {
			greater = false;
		} else {
			throw new JVMParseException(fcmp + " unknown");
		}

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("F") && v2.endsWith("F"))) {
			throw new JVMTypeException("fcmp wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		float v2f = Float(Value(v2));
		float v1f = Float(Value(v1));

		if (v2f == Float.NaN || v1f == Float.NaN) {
			ret = (greater) ? 1 : -1;
		} else {
			if (v2f == v1f) {
				ret = 0;
			} else if (v2f > v1f) {
				ret = 1;
			} else {
				ret = -1;
			}
		}

		return ret;
	}

	public static float execFCONST_(String string) {
		float value = -2;

		switch (string.substring(string.indexOf('_') + 1)) {
		case "0":
			value = 0;
			break;
		case "1":
			value = 1;
			break;
		case "2":
			value = 2;
			break;
		}

		if (value == -2) {
			throw new JVMParseException(string + " unknown");
		}

		return value;
	}

	public static float execFLOAD_(StackFrame sf, String fload) {

		int index = Integer(fload.substring(fload.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(fload + " unknown");
		}

		return execFLOAD(sf, index);
	}

	public static float execFLOAD(StackFrame sf, int index) {

		float ret;

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.getField(index);

		if (v1.equals(DEF_EMPTY_LOCAL_VAR)) {
			return FLOAT_DEF;
		}

		if (!(v1.endsWith("F"))) {
			throw new JVMTypeException("fload wrong type on stack: " + v1);
		}
		ret = Float(Value(v1));

		return ret;
	}

	public static void execFSTORE_(StackFrame sf, String fstore) {

		int index = Integer(fstore.substring(fstore.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(fstore + " unknown");
		}

		execFSTORE(sf, index);
	}

	public static void execFSTORE(StackFrame sf, int index) {

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.pop();

		if (!(v1.endsWith("F"))) {
			throw new JVMTypeException("fstore wrong type on stack: " + v1);
		}

		sf.setField(v1, index);
	}

	public static void execFRETURN(StackFrame sf,
			ExecutionInformationFrame eif, CallStack cs, ProgramCounter pc) {

		eif.popActiveStackFrame();

		float ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("F"))) {
			throw new JVMTypeException("freturn wrong type on stack V1: " + v1);
		}
		ret = Float(Value(v1));

		StackFrame stackFrame = eif.peekActiveStackFrame();
		stackFrame.push(ret + "F");

		execRETURN(pc, null, cs);
	}

	public static float execD2F(StackFrame sf) {

		float ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("D"))) {
			throw new JVMTypeException("d2f wrong type on stack V1: " + v1);
		}
		ret = (float) Double(Value(v1));

		return ret;
	}

	public static int execD2I(StackFrame sf) {

		int ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("D"))) {
			throw new JVMTypeException("d2i wrong type on stack V1: " + v1);
		}
		ret = (int) Double(Value(v1));

		return ret;
	}

	public static long execD2L(StackFrame sf) {

		long ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("D"))) {
			throw new JVMTypeException("d2l wrong type on stack V1: " + v1);
		}
		ret = (long) Double(Value(v1));

		return ret;
	}

	public static double execDADD(StackFrame sf) {

		double ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("D") && v2.endsWith("D"))) {
			throw new JVMTypeException("dadd wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Double(Value(v2)) + Double(Value(v1));

		return ret;
	}

	public static double execDDIV(StackFrame sf) {

		double ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("D") && v2.endsWith("D"))) {
			throw new JVMTypeException("dadd wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Double(Value(v2)) / Double(Value(v1));

		return ret;
	}

	public static double execDMUL(StackFrame sf) {

		double ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("D") && v2.endsWith("D"))) {
			throw new JVMTypeException("dmul wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Double(Value(v2)) * Double(Value(v1));

		return ret;
	}

	public static double execDNEG(StackFrame sf) {

		double ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("D"))) {
			throw new JVMTypeException("dneg wrong type on stack V1:" + v1);
		}
		ret = -Double(Value(v1));

		return ret;
	}

	public static double execDREM(StackFrame sf) {

		double ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("D") && v2.endsWith("D"))) {
			throw new JVMTypeException("drem wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Double(Value(v2)) % Double(Value(v1));

		return ret;
	}

	public static double execDSUB(StackFrame sf) {

		double ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("D") && v2.endsWith("D"))) {
			throw new JVMTypeException("dsub wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}
		ret = Double(Value(v2)) - Double(Value(v1));

		return ret;
	}

	public static double execDCONST_(String string) {
		double value = -2;

		switch (string.substring(string.indexOf('_') + 1)) {
		case "0":
			value = 0;
			break;
		case "1":
			value = 1;
			break;
		}

		if (value == -2) {
			throw new JVMParseException(string + " unknown");
		}

		return value;
	}

	public static int execDCMP(StackFrame sf, String dcmp) {

		boolean greater;

		if (dcmp.trim().charAt(dcmp.length() - 1) == 'g') {
			greater = true;
		} else if (dcmp.trim().charAt(dcmp.length() - 1) == 'l') {
			greater = false;
		} else {
			throw new JVMParseException(dcmp + " unknown");
		}

		int ret;

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		if (!(v1.endsWith("D") && v2.endsWith("D"))) {
			throw new JVMTypeException("dcmp wrong type on stack V1:" + v2
					+ " V2:" + v1);
		}

		double v2f = Double(Value(v2));
		double v1f = Double(Value(v1));

		if (v2f == Double.NaN || v1f == Double.NaN) {
			ret = (greater) ? 1 : -1;
		} else {
			if (v2f == v1f) {
				ret = 0;
			} else if (v2f > v1f) {
				ret = 1;
			} else {
				ret = -1;
			}
		}

		return ret;
	}

	public static double execDLOAD_(StackFrame sf, String dload) {

		int index = Integer(dload.substring(dload.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(dload + " unknown");
		}

		return execDLOAD(sf, index);
	}

	public static double execDLOAD(StackFrame sf, int index) {

		double ret;

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.getField(index);

		if (v1.equals(DEF_EMPTY_LOCAL_VAR)) {
			return DOUBLE_DEF;
		}

		if (!(v1.endsWith("D"))) {
			throw new JVMTypeException("dload wrong type on stack: " + v1);
		}
		ret = Double(Value(v1));

		return ret;
	}

	public static void execDSTORE_(StackFrame sf, String dstore) {

		int index = Integer(dstore.substring(dstore.indexOf('_') + 1));

		if (index < 0 || index > 3) {
			throw new JVMParseException(dstore + " unknown");
		}

		execDSTORE(sf, index);
	}

	public static void execDSTORE(StackFrame sf, int index) {

		String v1;

		if (index < 0 || index >= sf.getFieldSize()) {
			throw new JVMParseException("Field index out of bounds " + index
					+ ". Max is " + (sf.getFieldSize() - 1));
		}

		v1 = sf.pop();

		if (!(v1.endsWith("D"))) {
			throw new JVMTypeException("dstore wrong type on stack: " + v1);
		}

		sf.setField(v1, index);
	}

	public static void execDRETURN(StackFrame sf,
			ExecutionInformationFrame eif, CallStack cs, ProgramCounter pc) {

		eif.popActiveStackFrame();

		double ret;

		String v1;

		v1 = sf.pop();

		if (!(v1.endsWith("D"))) {
			throw new JVMTypeException("dreturn wrong type on stack V1: " + v1);
		}
		ret = Double(Value(v1));

		StackFrame stackFrame = eif.peekActiveStackFrame();
		stackFrame.push(ret + "D");

		execRETURN(pc, null, cs);
	}

	public static void execSWAP(StackFrame sf) {

		String v1, v2;

		v1 = sf.pop();
		v2 = sf.pop();

		sf.push(v1);
		sf.push(v2);
	}

	public static void execGOTO(int line, String[] prog, ProgramCounter pc) {

		if (line < 1 || line > prog.length) {
			throw new JVMParseException("goto " + line + " out of bounds.");
		}

		int search = 0;

		if (line > pc.getProgramCount()) {
			search = 1;
		} else if (line < pc.getProgramCount()) {
			search = -1;
		}

		if (search == 0) {
			return;
		}

		for (int i = pc.getProgramCount() - 1; i >= 0 && i < prog.length
				&& i != line - 1; i += search) {

			String aclL = prog[i];

			if (!isComment(aclL)) {
				if (isFunctionDeclaration(aclL)) {
					throw new JVMParseException(
							"goto try to leave function. not allowed.");
				}
			}
		}

		pc.setProgramCount(line - 1);

	}

	private static boolean isFunctionDeclaration(String aclL) {

		return aclL.trim().endsWith(";");
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

	public static void execDUP(StackFrame sf) {

		String v1 = sf.peek();

		sf.push(v1);
	}

	public static void execPOP(StackFrame sf) {
		sf.pop();
	}

	public static void execRETURN(ProgramCounter pc,
			ExecutionInformationFrame info, CallStack cs) {
		pc.setProgramCount(cs.popCallStack());

		if (pc.getProgramCount() > 0 && info != null) {
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

		for (int i = paramCount; i > 0; i--) {
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

	public static boolean isComment(String aclL) {
		return aclL.trim().startsWith("//");
	}

	public static int Integer(String s) {
		return Integer.valueOf(s).intValue();
	}

	public static byte Byte(String s) {
		return Byte.valueOf(s).byteValue();
	}

	public static short Short(String s) {
		return Short.valueOf(s).byteValue();
	}

	public static long Long(String s) {
		return Long.valueOf(s).byteValue();
	}

	public static float Float(String s) {
		return Float.valueOf(s).byteValue();
	}

	public static double Double(String s) {
		return Double.valueOf(s).byteValue();
	}

}
