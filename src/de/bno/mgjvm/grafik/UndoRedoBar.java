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

		btnUndo = new JButton();
		btnUndo.setIcon(new ImageIcon(InternalImage.load("arrow-undo-icon.png")));
		add(btnUndo);

		btnRedo = new JButton();
		btnRedo.setIcon(new ImageIcon(InternalImage.load("arrow-redo-icon.png")));
		add(btnRedo);

		createActionListener();
		btnRedo.addActionListener(actionListener);
		btnUndo.addActionListener(actionListener);
	}

	private void createActionListener() {
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

	}

	public UndoRedoBar(UnRedoListener listener) {
		this();

		this.listener = listener;
	}

	public void setUnRedoListener(UnRedoListener listener) {
		this.listener = listener;
	}

}
