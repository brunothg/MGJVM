package de.bno.mgjvm.grafik;

public interface OpenListener {
	public String open(ConstantPool cp, FieldPool fp);

	public void newFile();
}
