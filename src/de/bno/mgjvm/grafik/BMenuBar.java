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
	private OpenListener openListener;

	// File Menu
	private JMenuItem mniOpen;
	private JMenuItem mniSave;
	private JMenuItem mniSaveAs;
	private JMenuItem mniPrint;
	private JMenuItem mniNew;

	// File Menu

	// Execution Menu
	private JMenu mnExecution;

	private JToogleMenuItem mniStartStop;

	private JMenuItem mniOneStep;

	private JMenuItem mniFinish;

	// Execution Menu

	public BMenuBar() {

		createFileMenu();
		createExecutionMenu();
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
					if (openListener != null) {
						openListener.open();
					}
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
				} else if (source == mniNew) {
					if (openListener != null) {
						openListener.newFile();
					}
				}
			}
		};

		return actionListener;
	}

	private void createFileMenu() {

		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic(KeyEvent.VK_F);
		add(mnFile);

		mniNew = new JMenuItem("New");
		setShortcut(mniNew, KeyEvent.VK_N, ActionEvent.ALT_MASK
				| ActionEvent.SHIFT_MASK);
		mniNew.addActionListener(createActionListener());
		mnFile.add(mniNew);

		mniOpen = new JMenuItem("Open File...");
		setShortcut(mniOpen, KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		mniOpen.setIcon(new ImageIcon(InternalImage.load("MGJVM.png", 16, 16)));
		mniOpen.addActionListener(createActionListener());
		mnFile.add(mniOpen);

		mnFile.addSeparator();

		mniSave = new JMenuItem("Save");
		setShortcut(mniSave, KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		mniSave.setIcon(new ImageIcon(InternalImage
				.load("Actions-document-save-icon.png")));
		mniSave.addActionListener(createActionListener());
		mnFile.add(mniSave);

		mniSaveAs = new JMenuItem("Save As...");
		setShortcut(mniSaveAs, KeyEvent.VK_A, KeyEvent.VK_S,
				ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK);
		mniSaveAs.setIcon(new ImageIcon(InternalImage
				.load("Actions-document-save-as-icon.png")));
		mniSaveAs.addActionListener(createActionListener());
		mnFile.add(mniSaveAs);

		mnFile.addSeparator();

		mniPrint = new JMenuItem("Print");
		setShortcut(mniPrint, KeyEvent.VK_P, ActionEvent.CTRL_MASK);
		mniPrint.setIcon(new ImageIcon(InternalImage
				.load("Actions-document-print-icon.png")));
		mniPrint.addActionListener(createActionListener());
		mnFile.add(mniPrint);
	}

	private void createExecutionMenu() {

		mnExecution = new JMenu("Execution");
		mnExecution.setMnemonic(KeyEvent.VK_E);
		add(mnExecution);

		mniStartStop = new JToogleMenuItem("Start");
		mniStartStop.setSelectedText("Stop");
		mnExecution.add(mniStartStop);

		mniOneStep = new JMenuItem("Next step");
		mnExecution.add(mniOneStep);

		mniFinish = new JMenuItem("Run to end");
		mnExecution.add(mniFinish);
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

	public void setOpenListener(OpenListener listener) {
		this.openListener = listener;
	}
}
