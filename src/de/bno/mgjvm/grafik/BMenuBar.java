package de.bno.mgjvm.grafik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import de.bno.mgjvm.grafik.data.InternalImage;

public class BMenuBar extends JMenuBar {

	private static final long serialVersionUID = 8693130649315896164L;

	private ActionListener actionListener;
	private SaveListener saveListener;

	// File Menu
	private JMenuItem mniOpen;
	private JMenuItem mniSave;
	private JMenuItem mniSaveAs;
	private JMenuItem mniPrint;

	// File Menu

	public BMenuBar() {

		createFileMenu();
	}

	private ActionListener createActionListener() {

		if (actionListener != null) {
			return actionListener;
		}

		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object source = e.getSource();

				if (source == mniOpen) {
					// TODO MBar Open
				} else if (source == mniSave) {
					if (saveListener != null) {
						saveListener.save();
					}
				} else if (source == mniSaveAs) {
					if (saveListener != null) {
						saveListener.saveAs();
					}
				} else if (source == mniPrint) {
					if (saveListener != null) {
						saveListener.print();
					}
				}
			}
		};

		return actionListener;
	}

	private void createFileMenu() {

		JMenu mnFile = new JMenu("File");
		add(mnFile);

		mniOpen = new JMenuItem("Open File...");
		setShortcut(mniOpen, KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		mniOpen.setIcon(new ImageIcon(InternalImage.load("MGJVM.png", 16, 16)));
		mniOpen.addActionListener(createActionListener());
		mnFile.add(mniOpen);

		mnFile.addSeparator();

		mniSave = new JMenuItem("Save");
		setShortcut(mniSave, KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		mniSave.setIcon(new ImageIcon(InternalImage.load("save-icon.png")));
		mniSave.addActionListener(createActionListener());
		mnFile.add(mniSave);

		mniSaveAs = new JMenuItem("Save As...");
		setShortcut(mniSaveAs, KeyEvent.VK_S, ActionEvent.CTRL_MASK
				| ActionEvent.ALT_MASK);
		mniSaveAs
				.setIcon(new ImageIcon(InternalImage.load("save-all-icon.png")));
		mniSaveAs.addActionListener(createActionListener());
		mnFile.add(mniSaveAs);

		mnFile.addSeparator();

		mniPrint = new JMenuItem("Print");
		setShortcut(mniPrint, KeyEvent.VK_P, ActionEvent.CTRL_MASK);
		mniPrint.setIcon(new ImageIcon(InternalImage
				.load("Device-Printer-icon.png")));
		mniPrint.addActionListener(createActionListener());
		mnFile.add(mniPrint);
	}

	private void setShortcut(JMenuItem mni, int key, int mask) {
		setShortcut(mni, key, key, mask);
	}

	private void setShortcut(JMenuItem mni, int mnemonic, int key, int mask) {
		mni.setMnemonic(mnemonic);
		mni.setAccelerator(KeyStroke.getKeyStroke(key, mask));
	}

	public void setSaveListener(SaveListener listener) {
		this.saveListener = listener;
	}
}
