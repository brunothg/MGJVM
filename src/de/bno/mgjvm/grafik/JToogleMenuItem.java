package de.bno.mgjvm.grafik;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JMenuItem;

public class JToogleMenuItem extends JMenuItem {

	private static final long serialVersionUID = 986789637232329988L;

	private String normal;
	private String selected;
	private boolean isSelected;

	private Icon normalIcon;
	private Icon selectedIcon;

	public JToogleMenuItem(String s) {
		super();
		setNormalText(s);

		setSelected(false);
	}

	@Override
	public boolean isSelected() {
		return this.isSelected;
	}

	@Override
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;

		if (this.isSelected) {
			super.setText(this.selected);
			super.setIcon(this.selectedIcon);
		} else {
			super.setText(this.normal);
			super.setIcon(this.normalIcon);
		}
	}

	public void setNormalIcon(Icon icon) {
		this.normalIcon = icon;

		if (!isSelected()) {
			super.setIcon(this.normalIcon);
		}
	}

	public void setSelectedIcon(Icon icon) {
		this.selectedIcon = icon;

		if (isSelected()) {
			super.setIcon(this.selectedIcon);
		}
	}

	public void setNormalText(String s) {
		this.normal = (s != null) ? s : "";

		if (!isSelected()) {
			super.setText(this.normal);
		}
	}

	public String getNormalText() {
		return normal;
	}

	public void setSelectedText(String s) {
		this.selected = (s != null) ? s : "";

		if (isSelected()) {
			super.setText(this.selected);
		}
	}

	public String getSelectedText() {
		return selected;
	}

	@Override
	protected void fireActionPerformed(ActionEvent event) {
		boolean newSelectionStatus = !isSelected();
		super.fireActionPerformed(event);
		setSelected(newSelectionStatus);
	}

}
