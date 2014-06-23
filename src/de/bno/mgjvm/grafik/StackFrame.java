package de.bno.mgjvm.grafik;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StackFrame extends JPanel {

	private static final long serialVersionUID = -3091479309650103296L;
	private Line seperator;

	List<String> fields;
	LinkedList<String> stack;

	public StackFrame(int fieldSize) {

		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		seperator = new Line(2);
		seperator.setForeground(Color.RED);
		add(seperator);

		fields = new ArrayList<String>(fieldSize);
		stack = new LinkedList<String>();
	}

	public StackFrame() {
		this(10);
	}

	public void push(String s) {
		stack.add(s);

		JLabel lblNew = new JLabel(s);

		add(lblNew, 0);
	}

	public String peek() {
		return (!stack.isEmpty()) ? stack.getLast() : null;
	}

	public String pop() {
		String peek = peek();

		if (peek != null) {
			stack.removeLast();
		}

		Component topComponent = getComponent(0);
		if (topComponent != seperator) {
			remove(0);
			revalidate();
		}

		return peek;
	}

	public void pushField(String s) {
		fields.add(s);

		JLabel lblNew = new JLabel(s);

		add(lblNew);
	}

	public String getField(int index) {
		return fields.get(index);
	}

	public int getFieldSize() {
		return fields.size();
	}

	/**
	 * Das gleiche wie {@link #getPreferredSize()}
	 */
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
