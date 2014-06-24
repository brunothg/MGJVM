package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class ExecutionInformationFrame extends JFrame {

	private static final long serialVersionUID = -5722266287943015195L;

	private JPanel contentPane;

	private ProgramCounter programCounter;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExecutionInformationFrame frame = new ExecutionInformationFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ExecutionInformationFrame() {
		setTitle("MGJVM");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		programCounter = new ProgramCounter();
		programCounter.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(programCounter);
	}

	@Override
	public void setTitle(String s) {
		super.setTitle(s + " - Execution Information");
	}

}
