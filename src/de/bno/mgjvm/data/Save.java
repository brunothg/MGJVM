package de.bno.mgjvm.data;

import java.awt.Component;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.FieldPool;

public class Save {

	private static final String ENCODING = "UTF-8";

	public static String save(String s, ConstantPool cp, FieldPool fp,
			String path, Component parent) throws UnsupportedEncodingException,
			IOException {
		if (path == null || path.isEmpty()) {
			return saveAs(s, cp, fp, parent);
		}

		Path file = Paths.get(path);

		if (!Files.exists(file)) {
			return saveAs(s, cp, fp, parent);
		}

		OutputStream out = Files.newOutputStream(file);
		out.write((getPoolString(cp, fp)).getBytes(ENCODING));
		out.write("\n===\n".getBytes(ENCODING));
		out.write(s.getBytes(ENCODING));
		out.flush();
		out.close();

		return path;
	}

	private static String getPoolString(ConstantPool cp, FieldPool fp) {
		String ret = "";

		if (cp != null) {

			for (int i = 0; i < cp.getConstantCount(); i++) {
				Variable constant = cp.getConstant(i);
				ret += "const " + constant.getType().replaceAll(" ", "") + ":"
						+ constant.getValue() + "\n";
			}

		}

		if (fp != null) {
			for (int i = 0; i < fp.getFieldCount(); i++) {
				Variable constant = fp.getField(i);
				ret += "field " + constant.getType().replaceAll(" ", "") + ":"
						+ constant.getValue() + "\n";
			}
		}

		return ret;
	}

	public static String saveAs(String s, ConstantPool cp, FieldPool fp,
			Component parent) throws UnsupportedEncodingException, IOException {

		File saveTo = getSaveFile("jvm_assembly.gmj", parent);

		if (saveTo == null) {
			return null;
		}

		FileOutputStream out = new FileOutputStream(saveTo);
		out.write(getPoolString(cp, fp).getBytes(ENCODING));
		out.write("\n".getBytes(ENCODING));
		out.write(s.getBytes(ENCODING));
		out.flush();
		out.close();

		return saveTo.getPath();
	}

	private static File getSaveFile(String name, Component parent) {

		JFileChooser fc = new JFileChooser();

		fc.setSelectedFile(new File(name));

		boolean breakB = false;

		do {
			int ret = fc.showSaveDialog(parent);

			if (ret == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();

				if (file.exists()) {
					int answer = JOptionPane
							.showConfirmDialog(
									parent,
									"The file you try to save to exists.\n Do you want to overwrite it?",
									"File exists...",
									JOptionPane.YES_NO_CANCEL_OPTION);

					if (answer == JOptionPane.CANCEL_OPTION) {
						file = null;
						breakB = true;
					} else if (answer == JOptionPane.YES_OPTION) {
						breakB = true;
					}

				} else {
					breakB = true;
				}

				return file;
			} else {
				breakB = true;
			}
		} while (!breakB);

		return null;
	}

	public static void print(String s, String name) throws PrinterException {

		PrinterJob pjob = PrinterJob.getPrinterJob();
		pjob.setJobName("MGJVM - "
				+ ((name != null && !name.isEmpty()) ? name : "Unknown"));

		if (pjob.printDialog() == false) {
			return;
		}

		pjob.setPrintable(new PrintableText(s));
		pjob.print();
	}
}
