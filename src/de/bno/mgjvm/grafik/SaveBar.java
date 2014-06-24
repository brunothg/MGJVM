package de.bno.mgjvm.grafik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import de.bno.mgjvm.grafik.data.InternalImage;

public class SaveBar extends JToolBar {

	private static final long serialVersionUID = -8587316660629738129L;

	private ActionListener actionListener;
	private SaveListener saveListener;

	private JButton btnSave;

	private JButton btnSaveAs;

	private JButton btnPrint;

	public SaveBar() {
		super("Save...");

		btnSave = new JButton();
		btnSave.setIcon(new ImageIcon(InternalImage
				.load("Actions-document-save-icon.png")));
		btnSave.setToolTipText("Save (CTRL + S)");
		btnSave.addActionListener(createActionListener());
		add(btnSave);

		btnSaveAs = new JButton();
		btnSaveAs.setIcon(new ImageIcon(InternalImage
				.load("Actions-document-save-as-icon.png")));
		btnSaveAs.setToolTipText("Save as (CTRL + SHIFT + S)");
		btnSaveAs.addActionListener(createActionListener());
		add(btnSaveAs);

		btnPrint = new JButton();
		btnPrint.setIcon(new ImageIcon(InternalImage
				.load("Actions-document-print-icon.png")));
		btnPrint.setToolTipText("Print (CTRL + P)");
		btnPrint.addActionListener(createActionListener());
		add(btnPrint);
	}

	public SaveBar(SaveListener listener) {
		this();

		this.saveListener = listener;
	}

	@Override
	public void setEnabled(boolean b) {
		btnPrint.setEnabled(b);
		btnSave.setEnabled(b);
		btnSaveAs.setEnabled(b);
	}

	private ActionListener createActionListener() {
		if (actionListener != null) {
			return actionListener;
		}

		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object source = e.getSource();

				if (source == btnSave) {
					if (saveListener != null) {
						saveListener.save();
					}
				} else if (source == btnSaveAs) {
					if (saveListener != null) {
						saveListener.saveAs();
					}
				} else if (source == btnPrint) {
					if (saveListener != null) {
						saveListener.print();
					}
				}

			}
		};

		return actionListener;
	}

	public void setSaveListener(SaveListener listener) {
		this.saveListener = listener;
	}
}
