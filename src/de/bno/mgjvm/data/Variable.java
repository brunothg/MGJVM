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
		return type;
	}

	public String getValue() {
		return value;
	}

	public boolean isMutable() {
		return this.isMutable;
	}

	public String TypeChar() {
		String ret = null;

		switch (type.toLowerCase()) {
		case "short":
			ret = "S";
			break;
		case "int":
			ret = "I";
			break;
		case "long":
			ret = "L";
			break;
		case "float":
			ret = "F";
			break;
		case "double":
			ret = "D";
			break;
		case "byte":
			ret = "B";
			break;
		case "char":
			ret = "C";
			break;
		default:
			ret = type;
			break;
		}

		return ret;
	}
}
