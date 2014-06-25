package de.bno.mgjvm.grafik;

public interface SaveListener {

	public String save(ConstantPool cp, FieldPool fp);

	public String saveAs(ConstantPool cp, FieldPool fp);

	public void print();

}
