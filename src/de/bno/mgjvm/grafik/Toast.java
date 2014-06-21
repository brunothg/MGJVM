package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class Toast extends JDialog {

	private static final long serialVersionUID = 3393993025933958015L;
	private Container contentPane;
	private JLabel lblToast;

	private Toast(String s) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setType(Type.POPUP);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		lblToast = new JLabel(s);
		lblToast.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblToast.setOpaque(true);
		lblToast.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblToast, BorderLayout.CENTER);

		setToFitSize();
	}

	private void setToFitSize() {
		Dimension minimumSize = lblToast.getMinimumSize();
		int width = minimumSize.width;
		int height = minimumSize.height;

		setSize(Math.max(width, 100), Math.max(height, 50));
	}

	public void setVisible(boolean b) {
		super.setVisible(b);

		setToFitSize();
	}

	public static void showToast(String toast, final long millis, Point p) {
		final Toast t = new Toast(toast);
		t.setLocation(new Point(p.x - t.getWidth() / 2, p.y - t.getHeight() / 2));

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (!t.isVisible()) {
					Thread.yield();
				}

				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
				}

				t.setVisible(false);
			}
		}).start();

		t.setVisible(true);
	}

}
