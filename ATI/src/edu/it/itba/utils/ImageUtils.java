package edu.it.itba.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import edu.it.itba.models.Histogram;

public class ImageUtils {

	private static final int MAX_COLOR = 255;
	private static final int ADD = 1;
	private static final int MULTIPLY = 0;
	private static final int SUSTRACT = 2;

	@SuppressWarnings("resource")
	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[(int) file.length()];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new IOException();
		}
		is.close();
		return bytes;
	}

	public static BufferedImage load(File file, Dimension dim)
			throws IOException {

		BufferedImage ret;
		String extension = file.getName()
				.substring(file.getName().length() - 3).toLowerCase();
		if (!extension.equals("raw")) {
			ret = ImageIO.read(file);
			return ret;
		}

		byte[] data = getBytesFromFile(file);
		ret = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = ret.getRaster();
		int k = 0;
		for (int j = 0; j < dim.height; j++) {
			for (int i = 0; i < dim.width; i++) {
				raster.setSample(i, j, 0, data[k]);
				k = k + 1;
			}
		}
		BufferedImage image = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < dim.width; i++) {
			for (int j = 0; j < dim.height; j++) {
				image.setRGB(i, j, ret.getRGB(i, j));
			}
		}
		return image;
	}

	public static BufferedImage grayScale() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				raster.setSample(col, row, 0, col / 2);
			}
		}
		return image;
	}

	public static BufferedImage getSubImage(BufferedImage image, int x, int y,
			int widht, int height) {
		return image.getSubimage(x, y, widht, height);
	}

	public static void modifyPixel(BufferedImage image, int x, int y, int[] s) {
		image.getRaster().setPixel(x, y, s);
	}

	public static BufferedImage colorScale() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_INT_RGB);
		int i = 0;
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				image.setRGB(row, col, 0x000001 * 1 / 1024 * i + 0x000100 * 1
						/ 1024 * i + 0x010000 * 1 / 1024 * i);
				i++;
			}
		}
		return image;
	}

	public static Color hsvToRgb(float hue, float saturation, float value) {
		int h = (int) (hue * 6);
		float f = hue * 6 - h;
		float p = value * (1 - saturation);
		float q = value * (1 - f * saturation);
		float t = value * (1 - (1 - f) * saturation);

		switch (h) {
		case 0:
			return new Color(value, t, p);
		case 1:
			return new Color(q, value, p);
		case 2:
			return new Color(p, value, t);
		case 3:
			return new Color(p, q, value);
		case 4:
			return new Color(t, p, value);
		case 5:
			return new Color(value, p, q);
		default:
			throw new RuntimeException(
					"Something went wrong when converting from HSV to RGB. Input was "
							+ hue + ", " + saturation + ", " + value);
		}
	}

	public static Color rgbToHsv(int R, int G, int B) {
		float hsv[] = new float[3];
		Color.RGBtoHSB(R, G, B, hsv);
		return new Color(hsv[0], hsv[1], hsv[2]);
	}

	public static BufferedImage blankSquare() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				if ((col >= 100 && col <= 412) && (row >= 100 && row <= 412)) {
					raster.setSample(col, row, 0, 255);
				} else {
					raster.setSample(col, row, 0, 0);
				}
			}
		}
		return image;
	}

	/*
	 * Bresenham algorithm
	 * 
	 * First paint if full black and draw the blank circle and paint the
	 * interior.
	 */
	public static BufferedImage blankCircle() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				raster.setSample(col, row, 0, 0);
			}
		}

		int x0 = 255, y0 = 255; // Circle center
		int x = 100, y = 0;
		int radiusError = 1 - x;

		while (x >= y) {
			for (int i = 0; i < x; i++) {
				raster.setSample(i + x0, y + y0, 0, 255);
				raster.setSample(-i + x0, y + y0, 0, 255);
				raster.setSample(i + x0, -y + y0, 0, 255);
				raster.setSample(-i + x0, -y + y0, 0, 255);

				raster.setSample(y + x0, i + y0, 0, 255);
				raster.setSample(-y + x0, i + y0, 0, 255);
				raster.setSample(y + x0, -i + y0, 0, 255);
				raster.setSample(-y + x0, -i + y0, 0, 255);
			}
			y++;
			if (radiusError < 0) {
				radiusError += 2 * y + 1;
			} else {
				x--;
				radiusError += 2 * (y - x + 1);
			}
		}
		return image;
	}

	/*
	 * Paint circle radius 100 after 99 after 98 ... 0; Bad image
	 */

	public static BufferedImage blankCircle2() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				raster.setSample(col, row, 0, 0);
			}
		}

		int x0 = 255, y0 = 255; // Circle center
		int x, y;
		int radiusError;
		int i = 100;

		while (i >= 0) {
			x = i;
			radiusError = 1 - x;
			y = 0;
			while (x >= y) {
				raster.setSample(x + x0, y + y0, 0, 255);
				raster.setSample(-x + x0, y + y0, 0, 255);

				raster.setSample(x + x0, -y + y0, 0, 255);
				raster.setSample(-x + x0, -y + y0, 0, 255);

				raster.setSample(y + x0, x + y0, 0, 255);
				raster.setSample(-y + x0, x + y0, 0, 255);

				raster.setSample(y + x0, -x + y0, 0, 255);
				raster.setSample(-y + x0, -x + y0, 0, 255);
				y++;
				if (radiusError < 0) {
					radiusError += 2 * y + 1;
				} else {
					x--;
					radiusError += 2 * (y - x + 1);
				}
			}
			i--;
			y++;
		}
		return image;
	}

	/*
	 * Si opt es 1 Multiplica las imagenes, si es 0 las suma, 2 resta
	 */

	public static BufferedImage optImages(BufferedImage im1, BufferedImage im2,
			int opt) {

		if (im1.getHeight() != im2.getHeight()
				|| im1.getWidth() != im2.getWidth())
			return null;

		BufferedImage returnImage = new BufferedImage(im1.getWidth(),
				im1.getHeight(), im1.getType());

		Raster im1Raster = im1.getData();
		Raster im2Raster = im2.getData();

		WritableRaster raster = returnImage.getRaster();
		for (int i = 0; i < returnImage.getWidth(); i++) {
			for (int j = 0; j < returnImage.getHeight(); j++) {
				int[] im1Pixel = new int[3];
				int[] im2Pixel = new int[3];
				int[] returnImagePixel = new int[3];
				im1Raster.getPixel(i, j, im1Pixel);
				im2Raster.getPixel(i, j, im2Pixel);
				for (int k = 0; k < im1Pixel.length; k++) {
					if (opt == ADD)
						returnImagePixel[k] = im1Pixel[k] + im2Pixel[k];
					else if (opt == MULTIPLY)
						returnImagePixel[k] = im1Pixel[k] * im2Pixel[k];
					else if (opt == SUSTRACT)
						returnImagePixel[k] = im1Pixel[k] - im2Pixel[k];
					else
						return null;

				}
				raster.setPixel(i, j, returnImagePixel);
			}
		}
		return returnImage;
	}

	public static BufferedImage multiplyEscalar(BufferedImage image, int scalar) {
		BufferedImage returnImage = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());

		Raster imageRaster = image.getData();

		WritableRaster raster = returnImage.getRaster();

		for (int i = 0; i < returnImage.getWidth(); i++) {
			for (int j = 0; j < returnImage.getHeight(); j++) {
				int[] imagePixel = new int[3];
				int[] returnImagePixel = new int[3];
				imageRaster.getPixel(i, j, imagePixel);
				for (int k = 0; k < imagePixel.length; k++) {
					returnImagePixel[k] = imagePixel[k] * scalar;
				}
				raster.setPixel(i, j, returnImagePixel);

			}

		}
		return returnImage;
	}

	public static BufferedImage negative(BufferedImage image) {

		BufferedImage returnImage = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());

		Raster imageRaster = image.getData();

		WritableRaster raster = returnImage.getRaster();

		for (int i = 0; i < returnImage.getWidth(); i++) {
			for (int j = 0; j < returnImage.getHeight(); j++) {
				int[] imagePixel = new int[3];
				int[] returnImagePixel = new int[3];
				imageRaster.getPixel(i, j, imagePixel);
				for (int k = 0; k < imagePixel.length; k++) {
					returnImagePixel[k] = MAX_COLOR - imagePixel[k];
				}
				raster.setPixel(i, j, returnImagePixel);

			}

		}
		return returnImage;
	}

	public static double gauss(double mu, double sigma) {

		double seed = Math.random();
		double seed2 = Math.random();

		double gaussRandom = Math.sqrt(-2 * Math.log(seed))
				* Math.cos(2 * Math.PI * seed2);

		return gaussRandom;
	}

	public static double rayleigh(double eta) {

		double seed = Math.random();

		double rayleightRandom = 1 - Math.pow(Math.E, -Math.pow(seed, 2)
				/ (2 * Math.pow(eta, 2)));
		return rayleightRandom;
	}

	public static double exponential(double lambda) {

		double seed = Math.random();

		double exponentialRandom = -1 / lambda * Math.log(seed);

		return exponentialRandom;
	}

	// Recives and image and returns an Histogram that extends JPanel so it know
	// how to paint on the frame.
	public static Histogram histogram(BufferedImage image) {

		if (image == null)
			return null;

		return new Histogram(image);
	}

	public static double[] avgEachBand(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();
		double resp[] = { 0.0, 0.0, 0.0 };
		double values[] = new double[3];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				image.getRaster().getPixel(i, j, values);
				resp[0] += values[0];
				resp[1] += values[1];
				resp[2] += values[2];
			}
		}

		resp[0] = resp[0] / (height * width);
		resp[1] = resp[1] / (height * width);
		resp[2] = resp[2] / (height * width);

		return resp;
	}

	public static BufferedImage additiveGaussinianNoise(BufferedImage image,
			double mu, double sigma, float d) {

		float density = d / 100;
		BufferedImage retImage = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());

		Raster imageData = image.getData();
		WritableRaster raster = retImage.getRaster();
		for (int row = 0; row < image.getHeight(); row++) {
			for (int col = 0; col < image.getWidth(); col++) {
				if (Math.random() < density) {
					int oldPixelValue = imageData.getSample(col, row, 0);
					double noise = gauss(mu, sigma);
					int newPixelValue = ((int) noise + oldPixelValue);
					raster.setSample(col, row, 0, newPixelValue);
				}
			}
		}

		return retImage;
	}

	public static BufferedImage multiplicativeRayleighNoise(
			BufferedImage image, double eta, float d) {

		float density = d / 100;
		BufferedImage retImage = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());

		Raster imageData = image.getData();
		WritableRaster raster = retImage.getRaster();
		for (int row = 0; row < image.getHeight(); row++) {
			for (int col = 0; col < image.getWidth(); col++) {
				if (Math.random() < density) {
					int oldPixelValue = imageData.getSample(col, row, 0);
					double noise = rayleigh(eta);
					int newPixelValue = ((int) noise * oldPixelValue);
					raster.setSample(col, row, 0, newPixelValue);
				}
			}
		}

		return retImage;
	}

	public static BufferedImage multiplicativeExponentialNoise(
			BufferedImage image, double lambda, float d) {

		float density = d / 100;
		BufferedImage retImage = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());

		Raster imageData = image.getData();
		WritableRaster raster = retImage.getRaster();
		for (int row = 0; row < image.getHeight(); row++) {
			for (int col = 0; col < image.getWidth(); col++) {
				if (Math.random() < density) {
					int oldPixelValue = imageData.getSample(col, row, 0);
					double noise = exponential(lambda);
					int newPixelValue = ((int) noise * oldPixelValue);
					raster.setSample(col, row, 0, newPixelValue);
				}
			}
		}

		return retImage;
	}

	public static BufferedImage applyUmbral(BufferedImage image, double umbral) {

		BufferedImage returnImage = new BufferedImage(image.getWidth(),
				image.getWidth(), BufferedImage.TYPE_BYTE_GRAY);

		Raster imageRaster = image.getData();

		WritableRaster raster = returnImage.getRaster();
		for (int row = 0; row < image.getHeight(); row++) {
			for (int col = 0; col < image.getWidth(); col++) {
				if (imageRaster.getSample(col, row, 0) < umbral)
					raster.setSample(col, row, 0, 0);
				else
					raster.setSample(col, row, 0, 255);
			}
		}
		return returnImage;
	}

	public static BufferedImage guassImage(double mu, double sigma) {

		BufferedImage retImage = new BufferedImage(100, 100,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = retImage.getRaster();
		for (int row = 0; row < retImage.getHeight(); row++) {
			for (int col = 0; col < retImage.getWidth(); col++) {
				raster.setSample(col, row, 0, gauss(mu, sigma));
			}
		}

		return retImage;
	}

	public static BufferedImage exponentialImage(double lambda) {

		BufferedImage retImage = new BufferedImage(100, 100,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = retImage.getRaster();
		for (int row = 0; row < retImage.getHeight(); row++) {
			for (int col = 0; col < retImage.getWidth(); col++) {
				raster.setSample(col, row, 0, exponential(lambda));
			}
		}
		return retImage;
	}

	public static BufferedImage rayleighImage(double eta) {
		BufferedImage retImage = new BufferedImage(100, 100,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = retImage.getRaster();
		for (int row = 0; row < retImage.getHeight(); row++) {
			for (int col = 0; col < retImage.getWidth(); col++) {
				raster.setSample(col, row, 0, rayleigh(eta));
			}
		}
		return retImage;
	}

	private static void adaptValues(BufferedImage image) {

		WritableRaster imageRaster = image.getRaster();
		int minValue = 255;
		int maxValue = 0;
		int currValue;

		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				currValue = imageRaster.getSample(j, i, 0);
				if (currValue < minValue)
					minValue = currValue;
				if (currValue > maxValue)
					maxValue = currValue;
			}
		}

		// TODO preguntar que hacer.

	}
}
