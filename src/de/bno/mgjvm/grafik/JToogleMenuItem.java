package de.bno.mgjvm.grafik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class JToogleMenuItem extends JMenuItem {

	private static final long serialVersionUID = 986789637232329988L;

	String normal;
	String selected;
	boolean isSelected;

	public JToogleMenuItem(String s) {
		super();
		setNormalText(s);

		setSelected(false);

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSelected(!isSelected());
			}
		});
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;

		if (this.isSelected) {
			super.setText(this.selected);
		} else {
			super.setText(this.normal);
		}
	}

	public void setNormalText(String s) {
		normal = (s != null) ? s : "";
	}

	public String getNormalText() {
		return normal;
	}

	public void setSelectedText(String s) {
		selected = (s != null) ? s : "";
	}

	public String getSelectedText() {
		return selected;
	}

}
