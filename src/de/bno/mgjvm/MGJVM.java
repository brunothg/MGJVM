package de.bno.mgjvm;

import java.io.File;

import javax.swing.UIManager;

import de.bno.mgjvm.grafik.GrafischeJVM;

public class MGJVM {

	public static void main(String[] args) {

		setLookAndFeel();

		GrafischeJVM jvm = new GrafischeJVM();
		jvm.setVisible(true);

		if (args.length > 0) {
			String file = args[0];
			File f = new File(file);

			if (f.exists()) {
				jvm.setActiveFile(f);
			}
		}
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
