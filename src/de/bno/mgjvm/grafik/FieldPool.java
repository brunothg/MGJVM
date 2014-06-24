package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import de.bno.mgjvm.data.Pool;
import de.bno.mgjvm.data.Variable;
import de.bno.mgjvm.grafik.data.InternalImage;

public class FieldPool extends JPanel implements Pool<Variable> {

	private static final String[][] TYPES = new String[][] {
			{ "short", "int", "long", "float", "double", "byte", "char" },
			{ "0", "0", "0", "0.0", "0.0", "0", "a" } };
	private static final long serialVersionUID = 971262485449227240L;
	private JTable table;
	private JButton btnAdd;
	private JButton btnRemove;

	private ActionListener actionListener;
	private KeyListener keyListener;

	private TypeValueModel tableModel;

	public FieldPool() {
		setLayout(new BorderLayout());

		table = new JTable() {
			private static final long serialVersionUID = -4132559986303703465L;

			@Override
			public TableCellEditor getCellEditor(int row, int column) {
				Object value = super.getValueAt(row, column);
				if (value != null) {
					if (column == 0) {
						JComboBox<String> cbox = new JComboBox<String>(TYPES[0]);
						cbox.setEditable(true);
						cbox.setSelectedItem(value);
						return new DefaultCellEditor(((JComboBox<String>) cbox));
					}
					return getDefaultEditor(value.getClass());
				}
				return super.getCellEditor(row, column);
			}
		};
		tableModel = new TypeValueModel();
		table.setModel(tableModel);
		table.setAutoCreateRowSorter(false);
		table.setRowSorter(null);
		table.getTableHeader().setReorderingAllowed(false);
		table.addKeyListener(createKeyListener());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(buttonPanel, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(InternalImage
				.load("Actions-list-add-icon.png")));
		btnAdd.addActionListener(createActionListener());
		buttonPanel.add(btnAdd);

		btnRemove = new JButton("Remove");
		btnRemove.setIcon(new ImageIcon(InternalImage
				.load("Actions-edit-delete-icon.png")));
		btnRemove.addActionListener(createActionListener());
		buttonPanel.add(btnRemove);
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);

		btnAdd.setEnabled(b);
		btnRemove.setEnabled(b);
		table.setEnabled(b);
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
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					removeSelectedLinesFromTable();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};

		return keyListener;
	}

	private ActionListener createActionListener() {

		if (actionListener != null) {
			return actionListener;
		}

		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();

				if (source == btnAdd) {
					addLineToTable();
				} else if (source == btnRemove) {
					removeSelectedLinesFromTable();
				}
			}
		};

		return actionListener;
	}

	protected void removeSelectedLinesFromTable() {
		int[] selectedRows = table.getSelectedRows();

		for (int i = 0; i < selectedRows.length; i++) {
			int index = selectedRows[i];

			tableModel.removeRow(index);

			for (int j = i + 1; j < selectedRows.length; j++) {
				if (selectedRows[j] > index) {
					selectedRows[j] -= 1;
				}
			}
		}
	}

	protected void addLineToTable() {
		tableModel.addRow(new String[] { TYPES[0][0], TYPES[1][0] });
	}

	class TypeValueModel extends DefaultTableModel {

		private static final long serialVersionUID = 6792727545456523046L;

		public TypeValueModel() {
			super(new String[][] {}, new String[] { "Type", "Value" });
		}

		public Class<String> getColumnClass(int columnIndex) {

			return String.class;
		}

		public boolean isCellEditable(int row, int column) {

			return true;
		}

	}

	public int getFieldCount() {
		return table.getRowCount();
	}

	/**
	 * Feld
	 * 
	 * @param index
	 * @return new String[]{type, value}
	 */
	public Variable getField(int index) {

		String type = table.getValueAt(index, 0).toString();
		String value = table.getValueAt(index, 1).toString();

		return new Variable(type, value, true);
	}

	/**
	 * Ändere den Wert des Feldes
	 * 
	 * @param index
	 *            Index des Feldes
	 * @param value
	 *            Neuer Wert
	 */
	public void setValue(int index, String value) {
		table.setValueAt(value, index, 1);
	}

	public Variable[] getFields() {
		Variable[] ret = new Variable[getFieldCount()];

		for (int i = 0; i < ret.length; i++) {

			ret[i] = getField(i);
		}

		return ret;
	}

	@Override
	public int getPoolCount() {
		return getFieldCount();
	}

	@Override
	public Variable getPoolElement(int index) {
		return getField(index);
	}

}
