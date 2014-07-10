package de.bno.mgjvm.data;

public class Variable {

	private String type;
	private String value;

	private boolean isMutable;

	public Variable(String type, String value, boolean mutable) {
		this.type = type;
		this.value = value;
		this.isMutable = mutable;
	}

	public Variable(String type, String value) {
		this(type, value, false);
	}

	public String getType() {
		String ret = type;

		return ret;
	}

	public String getValue() {
		String ret = value;

		switch (type.toLowerCase()) {
		case "short":
			ret = "" + Short.valueOf(ret).shortValue();
			break;
		case "int":
			ret = "" + Integer.valueOf(ret).intValue();
			break;
		case "long":
			ret = "" + Long.valueOf(ret).longValue();
			break;
		case "float":
			ret = "" + Float.valueOf(ret).floatValue();
			break;
		case "double":
			ret = "" + Double.valueOf(ret).doubleValue();
			break;
		case "byte":
			ret = "" + Byte.valueOf(ret).byteValue();
			break;
		case "char":
			ret = "" + ((int) addCharValues(ret));
			break;
		}

		return ret;
	}

	private int addCharValues(String s) {
		int ret = 0;

		for (int i = 0; i < s.length(); i++) {
			ret += s.charAt(i);
		}

		return ret;
	}

	public boolean isMutable() {
		return this.isMutable;
	}

	public String TypeChar() {
		String ret = null;

		switch (type.toLowerCase()) {
		case "short":
			ret = "I";
			break;
		case "int":
			ret = "I";
			break;
		case "long":
			ret = "J";
			break;
		case "float":
			ret = "F";
			break;
		case "double":
			ret = "D";
			break;
		case "byte":
			ret = "I";
			break;
		case "char":
			ret = "I";
			break;
		default:
			ret = type;
			break;
		}

		return ret;
	}
}
