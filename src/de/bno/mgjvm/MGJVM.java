package de.bno.mgjvm;

import javax.swing.UIManager;

public class MGJVM {

	public static void main(String[] args) {

		setLookAndFeel();
		// TODO: App starten

	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
