package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

import de.bno.mgjvm.data.Save;

public class Editor extends JPanel implements UnRedoListener, SaveListener {

	private static final long serialVersionUID = 6492931134074408453L;
	private static final int UNDO_LIMIT = 100;

	private JTextArea taLinenumbers;
	private JTextArea textArea;

	private DocumentListener docListener;
	private KeyListener keyListener;

	private UndoManager undoManager;

	private String path;

	public Editor() {
		setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		textArea.addKeyListener(createKeyListener());
		textArea.getDocument().addDocumentListener(createDocumentListener());
		textArea.getDocument().addUndoableEditListener(createUnRedo());
		add(textArea, BorderLayout.CENTER);

		taLinenumbers = new JTextArea("1");
		taLinenumbers.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		taLinenumbers.setDisabledTextColor(Color.BLACK);
		taLinenumbers.setBackground(UIManager.getColor("Label.background"));
		taLinenumbers.setEditable(false);
		add(taLinenumbers, BorderLayout.WEST);
	}

	private KeyListener createKeyListener() {
		if (keyListener != null) {
			return keyListener;
		}

		keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown()) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_D:
						deleteActualLine();
						break;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.isControlDown()) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_Z:
						undo();
						break;
					case KeyEvent.VK_Y:
						redo();
						break;
					}
				}

			}
		};

		return keyListener;
	}

	public void deleteActualLine() {
		String text = textArea.getText();

		if (text.isEmpty()) {
			return;
		}

		int pos = textArea.getCaretPosition();

		int start = Math.max(0, text.lastIndexOf('\n', pos) + 1);
		int end = Math.min(text.indexOf('\n', pos), text.length() - 1);

		if (end == -1) {
			end = text.length() - 1;
			start = Math.max(0, text.lastIndexOf('\n', end - 1));
		}

		if (end < start) {
			start = Math.max(0, text.lastIndexOf('\n', end - 1) + 1);
		}

		text = text.substring(0, start)
				+ text.substring(end + 1, text.length());

		textArea.setText(text);
		textArea.setCaretPosition(Math.max(0, Math.min(pos, text.length())));
	}

	private UndoManager createUnRedo() {
		if (undoManager != null) {
			return undoManager;
		}

		undoManager = new UndoManager();
		undoManager.setLimit(UNDO_LIMIT);

		return undoManager;
	}

	private DocumentListener createDocumentListener() {
		if (docListener != null) {
			return docListener;
		}

		docListener = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateLineNumbering();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateLineNumbering();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateLineNumbering();
			}
		};

		return docListener;
	}

	protected void updateLineNumbering() {
		int lines;

		lines = getNumberOfLines();

		String numbering = addSpaces("1", lines);

		for (int i = 1; i < lines; i++) {
			numbering += "\n" + addSpaces("" + (i + 1), lines);
		}

		taLinenumbers.setText(numbering);
	}

	private String addSpaces(String string, int lines) {
		String ret = string;

		int missingSpaces = ("" + lines).length() - string.length();

		for (int i = 0; i < missingSpaces; i++) {
			ret = "0" + ret;
		}

		return ret;
	}

	public int getNumberOfLines() {

		return textArea.getLineCount();
	}

	public String getText() {
		return textArea.getText();
	}

	public void setText(String s) {
		textArea.setText(s);
	}

	@Override
	public void requestFocus() {
		textArea.requestFocus();
	}

	@Override
	public boolean requestFocusInWindow() {
		return textArea.requestFocusInWindow();
	}

	@Override
	public void undo() {
		if (undoManager.canUndo()) {
			undoManager.undo();
		}
	}

	@Override
	public void redo() {
		if (undoManager.canRedo()) {
			undoManager.redo();
		}
	}

	@Override
	public void save() {
		try {
			path = Save.save(textArea.getText(), path, textArea);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(textArea, Editor.class.getName()
					+ ":save \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void saveAs() {
		try {
			path = Save.saveAs(textArea.getText(), textArea);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(textArea, Editor.class.getName()
					+ ":saveAs \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void print() {
		try {
			Save.print(textArea.getText());
		} catch (PrinterException e) {
			JOptionPane.showMessageDialog(textArea, Editor.class.getName()
					+ ":print \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
