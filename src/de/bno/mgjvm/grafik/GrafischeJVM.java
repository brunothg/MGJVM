package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import de.bno.mgjvm.data.Open;
import de.bno.mgjvm.grafik.data.InternalImage;

public class GrafischeJVM extends JFrame {

	private static final long serialVersionUID = 6719113940473276102L;

	private JPanel contentPane;
	private Editor editor;

	public GrafischeJVM() {
		setIconImage(InternalImage.load("MGJVM.png"));
		setTitle("MGJVM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);

		editor = new Editor();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel headPanel = new JPanel();
		contentPane.add(headPanel, BorderLayout.NORTH);
		headPanel.setLayout(new BorderLayout(0, 0));

		JPanel toolBarPanel = new JPanel();
		headPanel.add(toolBarPanel, BorderLayout.CENTER);
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.X_AXIS));

		SaveBar saveBar = new SaveBar(editor);
		saveBar.setRollover(true);
		toolBarPanel.add(saveBar);

		UndoRedoBar undoRedoBar = new UndoRedoBar(editor);
		toolBarPanel.add(undoRedoBar);

		JScrollPane editorScrollPane = new JScrollPane();
		editorScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setViewportView(editor);
		contentPane.add(editorScrollPane, BorderLayout.CENTER);

		BMenuBar menuBar = new BMenuBar();
		menuBar.setSaveListener(editor);
		menuBar.setOpenListener(editor);
		setJMenuBar(menuBar);
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);

		editor.requestFocusInWindow();
	}

	public void setActiveFile(File f) {
		try {
			String[] loadFile = Open.loadFile(f);
			editor.setFile(loadFile[0], loadFile[1]);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, GrafischeJVM.class.getName()
					+ ":setActiveFile \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
