package de.bno.mgjvm.grafik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Line extends JComponent {

	private static final long serialVersionUID = 8453233785843384132L;
	private int thickness;
	private boolean paint;

	public Line(int thickness) {
		this.thickness = thickness;
		this.paint = true;

		setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
		setForeground(Color.RED);
	}

	public Line() {
		this(1);
	}

	public void paint(boolean b) {
		this.paint = b;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!paint) {
			return;
		}

		Insets insets = getInsets();

		g.setColor(getForeground());
		g.fillRect(insets.left, insets.top, getWidth()
				- (insets.left + insets.right), getHeight()
				- (insets.top + insets.bottom));
	}

	@Override
	public Dimension getMinimumSize() {
		Insets insets = getInsets();
		return new Dimension(1 + insets.left + insets.right, thickness
				+ insets.top + insets.bottom);
	}

	@Override
	public Dimension getPreferredSize() {
		Insets insets = getInsets();
		return new Dimension(1 + insets.left + insets.right, thickness
				+ insets.top + insets.bottom);
	}

	@Override
	public Dimension getMaximumSize() {
		Insets insets = getInsets();
		return new Dimension(Integer.MAX_VALUE, thickness + insets.top
				+ insets.bottom);
	}
}
