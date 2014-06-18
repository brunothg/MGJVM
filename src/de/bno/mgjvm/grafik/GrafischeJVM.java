package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class GrafischeJVM extends JFrame {

	private static final long serialVersionUID = 6719113940473276102L;
	private JPanel contentPane;

	public GrafischeJVM() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				GrafischeJVM.class
						.getResource("/de/bno/mgjvm/grafik/MGJVM.png")));
		setTitle("MGJVM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		Editor editor = new Editor();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(editor);
		contentPane.add(scrollPane, BorderLayout.CENTER);

	}

}
