package de.bno.mgjvm.data;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import de.bno.mgjvm.grafik.ConstantPool;
import de.bno.mgjvm.grafik.FieldPool;

public class Open {

	private static final String ENCODING = "UTF-8";

	public static String[] open(Component parent, ConstantPool cp, FieldPool fp)
			throws IOException {

		File file = getOpenFile("MGJVM file", "gmj", parent);
		if (file == null || !file.exists()) {
			return null;
		}

		return loadFile(file, cp, fp);
	}

	public static String[] loadFile(File file, ConstantPool cp, FieldPool fp)
			throws FileNotFoundException, IOException,
			UnsupportedEncodingException {
		String gmjString = null;

		FileInputStream in = new FileInputStream(file);
		List<Byte> bytes = new LinkedList<Byte>();

		byte[] buffer = new byte[1024];

		int read = -1;
		while ((read = in.read(buffer)) != -1) {
			for (int i = 0; i < read; i++) {
				bytes.add(buffer[i]);
			}
		}

		buffer = new byte[bytes.size()];

		int index = 0;
		for (Byte b : bytes) {
			buffer[index] = b.byteValue();
			index++;
		}
		gmjString = new String(buffer, ENCODING);

		in.close();

		gmjString = readConstantsAndFields(gmjString, cp, fp);

		return new String[] { file.getPath(), gmjString };
	}

	private static String readConstantsAndFields(String gmjString,
			ConstantPool cp, FieldPool fp) {
		String ret = "";

		String[] rows = gmjString.split("[\n\r\f]");

		boolean read = true;
		for (int i = 0; i < rows.length; i++) {
			String line = rows[i];

			if (!read) {
				ret += line + ((i < rows.length - 1) ? "\n" : "");
			} else {
				if (line.startsWith("const ")) {
					if (cp != null) {
						int indexOfDP = line.indexOf(':');
						cp.addLineToTable(line.substring(6, indexOfDP),
								line.substring(indexOfDP + 1));
					}
				} else if (line.startsWith("field ")) {
					if (fp != null) {
						int indexOfDP = line.indexOf(':');
						fp.addLineToTable(line.substring(6, indexOfDP),
								line.substring(indexOfDP + 1));
					}
				} else if (line.startsWith("===")) {
					read = false;
				}
			}

		}

		return ret;
	}

	private static File getOpenFile(final String fileDesc, final String ext,
			Component parent) {

		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}

				if (f.isFile()
						&& f.getName().toLowerCase()
								.endsWith(("." + ext).toLowerCase())) {
					return true;
				}

				return false;
			}

			@Override
			public String getDescription() {
				return fileDesc + " (." + ext + ")";
			}
		});

		int ret = fc.showOpenDialog(parent);

		if (ret == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();

			return file;
		}

		return null;
	}

	public static String loadInternalFile(String name) {

		try {
			String readFile = null;

			InputStream in = Open.class
					.getResourceAsStream("/de/bno/mgjvm/data/" + name);

			List<Byte> bytes = new LinkedList<Byte>();

			byte[] buffer = new byte[1024];

			int read = -1;
			while ((read = in.read(buffer)) != -1) {
				for (int i = 0; i < read; i++) {
					bytes.add(buffer[i]);
				}
			}

			buffer = new byte[bytes.size()];

			int index = 0;
			for (Byte b : bytes) {
				buffer[index] = b.byteValue();
				index++;
			}
			readFile = new String(buffer, ENCODING);

			in.close();
			return readFile;
		} catch (Exception e) {
			return null;
		}
	}

}
