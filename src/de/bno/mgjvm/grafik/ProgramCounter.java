package de.bno.mgjvm.grafik;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;

public class ProgramCounter extends JPanel {

	private static final long serialVersionUID = -3358453183916529967L;

	long pc;

	private JLabel lblPc;

	private JLabel lblActCommand;

	public ProgramCounter() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		lblPc = new JLabel();
		lblPc.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPc.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblPc, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblCommand = new JLabel("Command:");
		lblCommand.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblCommand, BorderLayout.WEST);

		lblActCommand = new JLabel();
		lblActCommand.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblActCommand.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblActCommand, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblProgramCounter = new JLabel("Program Counter");
		panel_1.add(lblProgramCounter);
		lblProgramCounter.setFont(new Font("Tahoma", Font.BOLD, 11));

		JSeparator separator = new JSeparator();
		panel_1.add(separator, BorderLayout.SOUTH);

		setProgramCount(1);
		setActualCommand(null);
	}

	public void setActualCommand(String s) {
		if (s == null) {
			lblActCommand.setText("N/A");
			return;
		}

		lblActCommand.setText(s);
	}

	public String getActualCommand() {
		return lblActCommand.getText();
	}

	public long getProgramCount() {
		return pc;
	}

	public void setProgramCount(long pc) {
		this.pc = pc;
		lblPc.setText("" + pc);
	}

}
