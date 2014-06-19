package de.bno.mgjvm.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PrintableText implements Printable {

	private String printText;
	private String[] lines;

	public PrintableText(String printText) {
		this.printText = printText;
		lines = this.printText.split("\n");
	}

	private final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 13);

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		Graphics2D g = (Graphics2D) graphics;

		g.setFont(font);
		g.setColor(Color.BLACK);

		double x = pageFormat.getImageableX();
		double y = pageFormat.getImageableY();
		// double width = pageFormat.getImageableWidth();
		double height = pageFormat.getImageableHeight();
		int border = 30;
		// double realWidth = width - 2 * border;
		double realHeight = height - 2 * border;

		g.translate(x, y);

		FontMetrics metrics = g.getFontMetrics();

		int lineHeight = metrics.getHeight();
		int linesPerPage = (int) (realHeight / lineHeight);
		int pages = lines.length / linesPerPage
				+ ((lines.length % linesPerPage != 0) ? 1 : 0);

		if (pageIndex >= pages) {
			return Printable.NO_SUCH_PAGE;
		}

		int numBreaks = (lines.length - 1) / linesPerPage;
		int[] pageBreaks = new int[numBreaks];
		for (int b = 0; b < numBreaks; b++) {
			pageBreaks[b] = (b + 1) * linesPerPage;
		}

		int linePage = border;
		int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
		int end = (pageIndex == pageBreaks.length) ? lines.length
				: pageBreaks[pageIndex];
		for (int line = start; line < end; line++) {
			linePage += lineHeight;
			g.drawString(lines[line], border, linePage);
		}

		return Printable.PAGE_EXISTS;
	}
}
