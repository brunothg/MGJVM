package de.bno.mgjvm.grafik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import de.bno.mgjvm.grafik.data.InternalImage;

public class UndoRedoBar extends JToolBar {

	private static final long serialVersionUID = 4754619944888112104L;
	private JButton btnUndo;
	private JButton btnRedo;

	private UnRedoListener listener;
	private ActionListener actionListener;

	public UndoRedoBar() {
		super("Undo / Redo");

		btnUndo = new JButton();
		btnUndo.setIcon(new ImageIcon(InternalImage
				.load("Actions-edit-undo-icon.png")));
		btnUndo.setToolTipText("Undo last Action (CTRL + Z)");
		btnUndo.addActionListener(createActionListener());
		add(btnUndo);

		btnRedo = new JButton();
		btnRedo.setToolTipText("Redo last Action (CTRL + Y)");
		btnRedo.setIcon(new ImageIcon(InternalImage
				.load("Actions-edit-redo-icon.png")));
		btnRedo.addActionListener(createActionListener());
		add(btnRedo);

	}

	private ActionListener createActionListener() {
		if (actionListener != null) {
			return actionListener;
		}

		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object source = e.getSource();

				if (source == btnUndo) {
					if (listener != null) {
						listener.undo();
					}
				} else if (source == btnRedo) {
					if (listener != null) {
						listener.redo();
					}
				}

			}
		};

		return actionListener;
	}

	public UndoRedoBar(UnRedoListener listener) {
		this();

		this.listener = listener;
	}

	@Override
	public void setEnabled(boolean b) {
		btnRedo.setEnabled(b);
		btnUndo.setEnabled(b);
	}

	public void setUnRedoListener(UnRedoListener listener) {
		this.listener = listener;
	}

}
