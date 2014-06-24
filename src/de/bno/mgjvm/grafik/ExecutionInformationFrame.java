package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ExecutionInformationFrame extends JFrame {

	private static final long serialVersionUID = -5722266287943015195L;

	private JPanel contentPane;

	private ProgramCounter programCounter;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private int tmpDefCloseOp;

	private LinkedList<StackFrame> stackframes;

	public ExecutionInformationFrame() {
		setTitle("MGJVM");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tmpDefCloseOp = getDefaultCloseOperation();
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

		scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		stackframes = new LinkedList<StackFrame>();
	}

	public StackFrame createNewStackFrame(String... fields) {
		StackFrame sf = new StackFrame(fields.length);

		for (String s : fields) {
			sf.pushField(s);
		}

		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		container.setLayout(new BorderLayout());
		container.add(sf, BorderLayout.CENTER);

		panel_1.add(container);
		stackframes.add(sf);

		return sf;
	}

	public StackFrame peekActiveStackFrame() {
		StackFrame ret = null;

		if (!stackframes.isEmpty()) {
			ret = stackframes.getLast();
		}

		return ret;
	}

	public StackFrame popActiveStackFrame() {
		StackFrame ret = peekActiveStackFrame();

		if (ret != null) {
			panel_1.remove(ret.getParent());
			panel_1.revalidate();
		}

		return ret;
	}

	public ProgramCounter getPC() {
		return programCounter;
	}

	public void resetPC() {
		programCounter.setActualCommand(null);
	}

	public void resetStackFrames() {
		stackframes.clear();
		panel_1.removeAll();
		panel_1.revalidate();
	}

	@Override
	public void setTitle(String s) {
		super.setTitle(s + " - Execution Information");
	}

	public void setClosable(boolean b) {
		if (b) {
			setDefaultCloseOperation(tmpDefCloseOp);
		} else {
			tmpDefCloseOp = getDefaultCloseOperation();
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}
}
