package de.bno.mgjvm.grafik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import de.bno.mgjvm.grafik.data.InternalImage;

public class ExecutionControlBar extends JToolBar {

	private static final long serialVersionUID = 4754619944888112104L;

	private ActionListener actionListener;
	private ExecutionListener executionListener;

	private JToggleButton btnStartStop;
	private JButton btnOneStep;
	private JButton btnFinish;

	public ExecutionControlBar() {
		super("Execution Control");

		btnStartStop = new JToggleButton();
		btnStartStop.setSelectedIcon(new ImageIcon(InternalImage
				.load("Actions-media-playback-stop-icon.png")));
		btnStartStop.setIcon(new ImageIcon(InternalImage
				.load("Actions-media-playback-start-icon.png")));
		btnStartStop.setToolTipText("Start / Stop (CTRL + F9)");
		btnStartStop.addActionListener(createActionListener());
		add(btnStartStop);

		btnOneStep = new JButton();
		btnOneStep.setIcon(new ImageIcon(InternalImage
				.load("Actions-media-seek-forward-icon.png")));
		btnOneStep.addActionListener(createActionListener());
		btnOneStep.setToolTipText("Next step (CTRL + F10)");
		btnOneStep.setEnabled(false);
		add(btnOneStep);

		btnFinish = new JButton();
		btnFinish.setIcon(new ImageIcon(InternalImage
				.load("Actions-media-skip-forward-icon.png")));
		btnFinish.setToolTipText("Run to end (CTRL + F11)");
		btnFinish.addActionListener(createActionListener());
		btnFinish.setEnabled(false);
		add(btnFinish);
	}

	public ExecutionControlBar(ExecutionListener listener) {
		this();

		setExecutionListener(listener);
	}

	private ActionListener createActionListener() {
		if (actionListener != null) {
			return actionListener;
		}

		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object source = e.getSource();

				if (source == btnStartStop) {
					if (executionListener != null) {
						if (btnStartStop.isSelected()) {
							executionListener.startExecution();
						} else {
							executionListener.stopExecution();
						}
					}
					btnOneStep.setEnabled(btnStartStop.isSelected());
					btnFinish.setEnabled(btnStartStop.isSelected());
				} else if (source == btnOneStep) {
					if (executionListener != null) {
						executionListener.executeOneStep();
					}
				} else if (source == btnFinish) {
					if (executionListener != null) {
						executionListener.execute();
					}
				}

			}
		};

		return actionListener;
	}

	public void setExecutionListener(ExecutionListener listener) {
		this.executionListener = listener;
	}

	/**
	 * Drückt den Start / Stop Knopf Um den Knopfstatus auf beendet zu setzen
	 * {@link #setStopped()} benutzen
	 */
	public void stop() {
		if (btnStartStop.isSelected()) {
			btnStartStop.doClick();
		}
	}

	/**
	 * Drückt den Start / Stop Knopf Um den Knopfstatus auf gestartet zu setzen
	 * {@link #setStopped()} benutzen
	 */
	public void start() {
		if (!btnStartStop.isSelected()) {
			btnStartStop.doClick();
		}
	}

	/**
	 * Setzt den Start / Stop Knopf Status
	 */
	public void setSelected(boolean selected) {
		btnStartStop.setSelected(selected);

		btnOneStep.setEnabled(btnStartStop.isSelected());
		btnFinish.setEnabled(btnStartStop.isSelected());
	}
}
