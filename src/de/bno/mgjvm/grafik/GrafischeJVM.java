package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import de.bno.mgjvm.data.Open;
import de.bno.mgjvm.grafik.data.InternalImage;
import javax.swing.border.TitledBorder;

public class GrafischeJVM extends JFrame implements SaveListener, OpenListener,
		ExecutionListener {

	private static final double DIVIDER_POSITION_AT_STARTUP = 0.7;

	private static final String TITLE = "MGJVM";

	private static final long serialVersionUID = 6719113940473276102L;

	private JPanel contentPane;
	private Editor editor;
	private boolean firstVisible;

	private JSplitPane splitPane;
	private ExecutionControlBar executionControlBar;
	private BMenuBar menuBar;

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

		executionControlBar = new ExecutionControlBar(this);
		toolBarPanel.add(executionControlBar);

		JScrollPane editorScrollPane = new JScrollPane();
		editorScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setViewportView(editor);

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

		splitPane = new JSplitPane();
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(1.0);
		splitPane.setLeftComponent(editorScrollPane);
		splitPane.setRightComponent(tablePanel);

		ConstantPool constantPool = new ConstantPool();
		constantPool.setBorder(new TitledBorder(null, "Constant Pool",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablePanel.add(constantPool);

		FieldPool fieldPool = new FieldPool();
		fieldPool.setBorder(new TitledBorder(null, "Field Pool",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablePanel.add(fieldPool);
		contentPane.add(splitPane, BorderLayout.CENTER);

		menuBar = new BMenuBar();
		menuBar.setSaveListener(this);
		menuBar.setOpenListener(this);
		menuBar.setExecutionListener(this);
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

	@Override
	public void startExecution() {
		menuBar.setExecutionSelected(true);
		executionControlBar.setSelected(true);
		// TODO Start
	}

	@Override
	public void stopExecution() {
		menuBar.setExecutionSelected(false);
		executionControlBar.setSelected(false);
		// TODO Stop
	}

	@Override
	public void executeOneStep() {
		// TODO Step

	}

	@Override
	public void execute() {
		// TODO Finish

	}
}
