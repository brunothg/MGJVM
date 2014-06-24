package de.bno.mgjvm.grafik;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StackFrame extends JPanel {

	private static final long serialVersionUID = -3091479309650103296L;
	private Line seperator;

	List<String> fields;
	LinkedList<String> stack;
	private JPanel container;
	private Component verticalGlue;

	public StackFrame(int fieldSize) {

		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);

		container = new JPanel();
		add(container);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		seperator = new Line(2);
		seperator.setForeground(Color.RED);
		container.add(seperator);

		fields = new ArrayList<String>(fieldSize);
		stack = new LinkedList<String>();
	}

	public StackFrame() {
		this(10);
	}

	public void push(String s) {
		stack.add(s);

		JLabel lblNew = new JLabel(s);
		lblNew.setFont(new Font(Font.SERIF, Font.BOLD, 13));

		container.add(lblNew, 0);
	}

	public String peek() {
		return (!stack.isEmpty()) ? stack.getLast() : null;
	}

	public String pop() {
		String peek = peek();

		if (peek != null) {
			stack.removeLast();
		}

		Component topComponent = container.getComponent(0);
		if (topComponent != seperator) {
			container.remove(0);
			container.revalidate();
		}

		return peek;
	}

	public void pushField(String s) {
		fields.add(s);

		JLabel lblNew = new JLabel(s);
		lblNew.setFont(new Font(Font.SERIF, Font.BOLD, 13));

		container.add(lblNew);
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
