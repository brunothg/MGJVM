package de.bno.mgjvm.data;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PrintableText implements Printable {

	private String printText;

	public PrintableText(String printText) {
		this.printText = printText;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		// TODO Print text
		return 0;
	}

}
