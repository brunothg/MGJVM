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
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class GrafischeJVM extends JFrame implements SaveListener, OpenListener {

	private static final double DIVIDER_POSITION_AT_STARTUP = 0.7;

	private static final String TITLE = "MGJVM";

	private static final long serialVersionUID = 6719113940473276102L;

	private JPanel contentPane;
	private Editor editor;
	private boolean firstVisible;

	private JSplitPane splitPane;

	public GrafischeJVM() {
		setIconImage(InternalImage.load("MGJVM.png"));
		setTitle("Unknown*");
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

		SaveBar saveBar = new SaveBar(this);
		saveBar.setRollover(true);
		toolBarPanel.add(saveBar);

		UndoRedoBar undoRedoBar = new UndoRedoBar(editor);
		undoRedoBar.setRollover(true);
		toolBarPanel.add(undoRedoBar);

		JScrollPane editorScrollPane = new JScrollPane();
		editorScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setViewportView(editor);

		JPanel tablePanel = new JPanel();
		GridBagLayout gbl_tablePanel = new GridBagLayout();
		gbl_tablePanel.columnWidths = new int[] { 0, 0 };
		gbl_tablePanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_tablePanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_tablePanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		tablePanel.setLayout(gbl_tablePanel);

		splitPane = new JSplitPane();
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(1.0);
		splitPane.setLeftComponent(editorScrollPane);
		splitPane.setRightComponent(tablePanel);
		contentPane.add(splitPane, BorderLayout.CENTER);

		ProgramCounter programCounter = new ProgramCounter();
		GridBagConstraints gbc_programCounter = new GridBagConstraints();
		gbc_programCounter.anchor = GridBagConstraints.NORTH;
		gbc_programCounter.insets = new Insets(0, 0, 5, 0);
		gbc_programCounter.fill = GridBagConstraints.HORIZONTAL;
		gbc_programCounter.gridx = 0;
		gbc_programCounter.gridy = 0;
		tablePanel.add(programCounter, gbc_programCounter);

		ConstantPool constantPool = new ConstantPool();
		constantPool.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Constant Pool",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_constantPool = new GridBagConstraints();
		gbc_constantPool.fill = GridBagConstraints.BOTH;
		gbc_constantPool.gridx = 0;
		gbc_constantPool.gridy = 1;
		tablePanel.add(constantPool, gbc_constantPool);

		BMenuBar menuBar = new BMenuBar();
		menuBar.setSaveListener(this);
		menuBar.setOpenListener(this);
		setJMenuBar(menuBar);

		firstVisible = true;
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);

		editor.requestFocusInWindow();

		if (b && firstVisible) {
			firstVisible = false;
			splitPane.setDividerLocation(DIVIDER_POSITION_AT_STARTUP);
		}
	}

	public void setActiveFile(File f) {
		try {
			String[] loadFile = Open.loadFile(f);
			setTitle(fileName(loadFile[0]));

			editor.setFile(loadFile[0], loadFile[1]);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, GrafischeJVM.class.getName()
					+ ":setActiveFile \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void setTitle(String s) {
		String addTitle = "";

		if (s != null & !s.isEmpty()) {
			addTitle = " - " + s;
		}

		super.setTitle(TITLE + addTitle);
	}

	@Override
	public String open() {
		String ret = editor.open();
		setTitle(fileName(ret));

		return ret;
	}

	@Override
	public void newFile() {
		editor.newFile();
		setTitle("Unknown*");
	}

	@Override
	public String save() {
		String ret = editor.save();
		setTitle(fileName(ret));

		return ret;
	}

	@Override
	public String saveAs() {
		String ret = editor.saveAs();
		setTitle(fileName(ret));

		return ret;
	}

	private String fileName(String ret) {

		return ret.substring(Math.max(
				0,
				Math.min(
						Math.max(ret.lastIndexOf('/') + 1,
								ret.lastIndexOf('\\') + 1), ret.length())));
	}

	@Override
	public void print() {
		editor.print();
	}
}
