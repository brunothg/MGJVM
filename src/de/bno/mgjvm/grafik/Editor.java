package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.UIManager;

import java.awt.Color;

public class Editor extends JPanel implements DocumentListener {

	private static final long serialVersionUID = 6492931134074408453L;
	private JTextArea taLinenumbers;
	private JTextArea textArea;

	public Editor() {
		setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		textArea.getDocument().addDocumentListener(this);
		add(textArea, BorderLayout.CENTER);

		taLinenumbers = new JTextArea("1");
		taLinenumbers.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		taLinenumbers.setDisabledTextColor(Color.BLACK);
		taLinenumbers.setBackground(UIManager.getColor("Label.background"));
		taLinenumbers.setEditable(false);
		add(taLinenumbers, BorderLayout.WEST);
	}

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
}
