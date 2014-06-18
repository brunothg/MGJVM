package de.bno.mgjvm;

import javax.swing.UIManager;

import de.bno.mgjvm.grafik.GrafischeJVM;

public class MGJVM {

	public static void main(String[] args) {

		setLookAndFeel();

		GrafischeJVM jvm = new GrafischeJVM();
		jvm.setVisible(true);

	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
