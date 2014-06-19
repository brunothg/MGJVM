package de.bno.mgjvm.grafik.data;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

public class InternalImage {

	public static Image load(String s) {
		return Toolkit.getDefaultToolkit().getImage(
				InternalImage.class.getResource("/de/bno/mgjvm/grafik/data/"
						+ s));
	}

	public static Image fullLoad(String s) throws IOException {
		return ImageIO.read(InternalImage.class
				.getResource("/de/bno/mgjvm/grafik/data/" + s));
	}

	public static Image load(String s, int width, int height) {
		Image img;
		try {
			img = fullLoad(s);
		} catch (IOException e) {
			return load(s);
		}

		BufferedImage ret = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		Graphics graphics = ret.getGraphics();

		ImageObserver observer = null;

		graphics.drawImage(img, 0, 0, width, height, 0, 0,
				img.getWidth(observer), img.getHeight(observer), observer);

		return ret;
	}
}
