package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.bno.mgjvm.grafik.data.InternalImage;

public class ConstantPool extends JPanel {

	private static final long serialVersionUID = 971262485449227240L;
	private JTable table;
	private JButton btnAdd;
	private JButton btnRemove;

	public ConstantPool() {
		setLayout(new BorderLayout());

		table = new JTable();
		table.setModel(new TypeValueModel());
		table.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(buttonPanel, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(InternalImage
				.load("Actions-list-add-icon.png")));
		buttonPanel.add(btnAdd);

		btnRemove = new JButton("Remove");
		btnRemove.setIcon(new ImageIcon(InternalImage
				.load("Actions-edit-delete-icon.png")));
		buttonPanel.add(btnRemove);
	}

	class TypeValueModel extends DefaultTableModel {

		private static final long serialVersionUID = 6792727545456523046L;

		public TypeValueModel() {
			super(new String[][] { { "Class", "this" }, }, new String[] {
					"Type", "Value" });
		}

		public Class<String> getColumnClass(int columnIndex) {
			return String.class;
		}

		public boolean isCellEditable(int row, int column) {

			if (row == 0) {
				return false;
			}

			return true;
		}

	}

}
