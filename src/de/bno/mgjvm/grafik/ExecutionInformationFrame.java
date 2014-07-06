package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import de.bno.mgjvm.grafik.data.InternalImage;

public class ExecutionInformationFrame extends JFrame {

	private static final long serialVersionUID = -5722266287943015195L;

	private JPanel contentPane;

	private ProgramCounter programCounter;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private int tmpDefCloseOp;

	private LinkedList<StackFrame> stackframes;

	private JFrame parent;
	private ComponentListener componentListener;

	private int orientationAttach;
	private JToggleButton tglbtnAttach;
	private ActionListener actionListener;

	private boolean attach;
	private Component horizontalGlue;

	public ExecutionInformationFrame(JFrame parent) {
		this();
		setParent(parent);
	}

	public ExecutionInformationFrame() {
		setAutoRequestFocus(false);
		setTitle("MGJVM");
		setIconImage(InternalImage.load("MGJVM.png"));
		// setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tmpDefCloseOp = getDefaultCloseOperation();
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		programCounter = new ProgramCounter();
		programCounter.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(programCounter);

		tglbtnAttach = new JToggleButton();
		tglbtnAttach.setIcon(new ImageIcon(InternalImage
				.load("Status-object-unlocked-icon.png")));
		tglbtnAttach.setSelectedIcon(new ImageIcon(InternalImage
				.load("Status-object-locked-icon.png")));
		tglbtnAttach.setToolTipText("Lock to parent frame");
		tglbtnAttach.addActionListener(createActionListener());

		horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		panel.add(tglbtnAttach);

		scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		stackframes = new LinkedList<StackFrame>();
	}

	public void setParent(JFrame parent) {
		if (this.parent != null) {
			this.parent.removeComponentListener(createWindowListener());
		}

		this.parent = parent;
		parent.addComponentListener(createWindowListener());
	}

	private ActionListener createActionListener() {

		if (actionListener != null) {
			return actionListener;
		}

		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();

				if (source == tglbtnAttach) {
					if (tglbtnAttach.isSelected()) {
						int orientation = SwingConstants.RIGHT;

						if (parent != null
								&& getX() + getWidth() / 2 < parent.getX()
										+ parent.getWidth() / 2) {
							orientation = SwingConstants.LEFT;
						}

						attachToParent(true, orientation);
					} else {
						attachToParent(false, 0);
					}
				}
			}
		};

		return actionListener;
	}

	public void attachToParent(boolean b, int orientation) {
		if (parent == null) {
			return;
		}

		tglbtnAttach.setSelected(b);

		if (b) {
			this.orientationAttach = orientation;
			this.attach = true;
			setLocationToAttachPosition();
		} else {
			this.attach = false;
		}
	}

	private void setLocationToAttachPosition() {
		if (parent == null) {
			return;
		}

		if (orientationAttach == SwingConstants.LEFT) {
			setLocation(parent.getX() - getWidth(), parent.getY());
		} else {
			setLocation(parent.getX() + parent.getWidth(), parent.getY());
		}
	}

	private ComponentListener createWindowListener() {

		if (componentListener != null) {
			return componentListener;
		}

		componentListener = new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				if (attach) {
					setLocationToAttachPosition();
				}
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		};

		return componentListener;
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
			stackframes.removeLast();
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
