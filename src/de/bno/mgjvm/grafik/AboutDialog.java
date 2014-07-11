package de.bno.mgjvm.grafik;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Frame;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

import de.bno.mgjvm.data.Open;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = -1209450467974900300L;
	private JScrollPane scrollPane;
	private JEditorPane taAbout;
	private HyperlinkListener listener;

	private AboutDialog(Frame owner, boolean mode) {
		super(owner, mode);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("About MGJVM");
		setSize(650, 280);
		setLocationByPlatform(true);
		setLayout(new BorderLayout());

		scrollPane = new JScrollPane();
		add(scrollPane);

		taAbout = new JEditorPane();
		taAbout.setContentType("text/html");
		taAbout.setEditable(false);
		taAbout.setOpaque(false);
		setText();
		taAbout.addHyperlinkListener(createHyperLinkListener());
		scrollPane.setViewportView(taAbout);
	}

	private HyperlinkListener createHyperLinkListener() {

		if (listener != null) {
			return listener;
		}

		listener = new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == EventType.ACTIVATED) {
					try {
						openInBrowser(e.getURL());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		};

		return listener;
	}

	protected void openInBrowser(URL url) throws URISyntaxException,
			IOException {
		if (url == null) {
			return;
		}

		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				URI uri = url.toURI();
				desktop.browse(uri);
			}
		}
	}

	private void setText() {
		String txt = Open.loadInternalFile("about.txt");

		txt = txt.replaceAll("\r", "");
		txt = txt.replaceAll("\n", "<br>");
		txt = txt.replaceAll("\t", "&emsp;&emsp;");

		taAbout.setText(txt);
	}

	public static void showDialog(Frame owner) {
		AboutDialog dialog = new AboutDialog(owner, true);
		dialog.setVisible(true);
	}
}
