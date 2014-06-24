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
	private ExecutionListener executionListener;

	// File Menu
	private JMenu mnFile;
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

	public void setFileMenuEnabled(boolean b) {
		mnFile.setEnabled(b);
		mniOpen.setEnabled(b);
		mniSave.setEnabled(b);
		mniSaveAs.setEnabled(b);
		mniPrint.setEnabled(b);
		mniNew.setEnabled(b);
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
				} else if (source == mniStartStop) {
					if (executionListener != null) {
						if (mniStartStop.isSelected()) {
							executionListener.stopExecution();
						} else {
							executionListener.startExecution();
						}
					}

					mniOneStep.setEnabled(mniStartStop.isSelected());
					mniFinish.setEnabled(mniStartStop.isSelected());
				} else if (source == mniOneStep) {
					if (executionListener != null) {
						executionListener.executeOneStep();
					}
				} else if (source == mniFinish) {
					if (executionListener != null) {
						executionListener.execute();
					}

				}
			}
		};

		return actionListener;
	}

	private void createFileMenu() {

		mnFile = new JMenu("File");
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
		setShortcut(mniStartStop, -1, KeyEvent.VK_F9, ActionEvent.CTRL_MASK);
		mniStartStop.setNormalIcon(new ImageIcon(InternalImage
				.load("Actions-media-playback-start-icon.png")));
		mniStartStop.setSelectedIcon(new ImageIcon(InternalImage
				.load("Actions-media-playback-stop-icon.png")));
		mniStartStop.addActionListener(createActionListener());
		mnExecution.add(mniStartStop);

		mniOneStep = new JMenuItem("Next step");
		setShortcut(mniOneStep, -1, KeyEvent.VK_F10, ActionEvent.CTRL_MASK);
		mniOneStep.setIcon(new ImageIcon(InternalImage
				.load("Actions-media-seek-forward-icon.png")));
		mniOneStep.addActionListener(createActionListener());
		mniOneStep.setEnabled(false);
		mnExecution.add(mniOneStep);

		mniFinish = new JMenuItem("Run to end");
		setShortcut(mniFinish, -1, KeyEvent.VK_F11, ActionEvent.CTRL_MASK);
		mniFinish.setIcon(new ImageIcon(InternalImage
				.load("Actions-media-skip-forward-icon.png")));
		mniFinish.addActionListener(createActionListener());
		mniFinish.setEnabled(false);
		mnExecution.add(mniFinish);
	}

	private void setShortcut(JMenuItem mni, int key, int mask) {
		setShortcut(mni, key, key, mask);
	}

	private void setShortcut(JMenuItem mni, int mnemonic, int key, int mask) {
		if (mnemonic != -1) {
			mni.setMnemonic(mnemonic);
		}
		mni.setAccelerator(KeyStroke.getKeyStroke(key, mask));
	}

	public void setSaveListener(SaveListener listener) {
		this.saveListener = listener;
	}

	public void setOpenListener(OpenListener listener) {
		this.openListener = listener;
	}

	public void setExecutionListener(ExecutionListener listener) {
		this.executionListener = listener;
	}

	public void setExecutionSelected(boolean selected) {
		mniStartStop.setSelected(selected);

		mniOneStep.setEnabled(mniStartStop.isSelected());
		mniFinish.setEnabled(mniStartStop.isSelected());
	}
}
