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

public class Open {

	private static final String ENCODING = "UTF-8";

	public static String[] open(Component parent) throws IOException {

		File file = getOpenFile("MGJVM file", "gmj", parent);
		if (file == null || !file.exists()) {
			return null;
		}

		return loadFile(file);
	}

	public static String[] loadFile(File file) throws FileNotFoundException,
			IOException, UnsupportedEncodingException {
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

		return new String[] { file.getPath(), gmjString };
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
