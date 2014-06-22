package de.bno.mgjvm;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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

		String system = getSystemLookAndFeel();
		String nimbus = getNimbusLookAndFeel();

		try {
			UIManager.setLookAndFeel(system);
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(nimbus);
			} catch (Exception e1) {
			}
		}
	}

	private static String getSystemLookAndFeel() {

		return UIManager.getSystemLookAndFeelClassName();
	}

	private static String getNimbusLookAndFeel() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				return (info.getClassName());
			}
		}

		return null;
	}

}
