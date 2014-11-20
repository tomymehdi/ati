package edu.it.itba.models;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.it.itba.enums.Bands;
import edu.it.itba.enums.ImageType;
import edu.it.itba.functions.LinearTransform;
import edu.it.itba.interfaces.Function;

public class ATImage {

	public Band R;
	public Band G;
	public Band B;
	private int rows;
	private int cols;
	public ImageType type;

	public ATImage(int rows, int cols, ImageType type) {
		this.rows = rows;
		this.cols = cols;
		this.type = type;
		R = new Band(rows, cols, Bands.R);
		G = new Band(rows, cols, Bands.G);
		B = new Band(rows, cols, Bands.B);
	}

	public ATImage(BufferedImage image, ImageType type) {
		this.rows = image.getHeight();
		this.cols = image.getWidth();
		this.type = type;

		R = new Band(rows, cols, Bands.R);
		G = new Band(rows, cols, Bands.G);
		B = new Band(rows, cols, Bands.B);

		Raster raster = image.getRaster();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				R.set(row, col, raster.getSample(col, row, 0));
				if (ImageType.RGB == type) {
					G.set(row, col, raster.getSample(col, row, 1));
					B.set(row, col, raster.getSample(col, row, 2));
				}
			}
		}
	}

	public ATImage(ATImage image) {
		this.rows = image.getHeight();
		this.cols = image.getWidth();
		this.type = image.type;

		R = new Band(rows, cols, Bands.R);
		G = new Band(rows, cols, Bands.G);
		B = new Band(rows, cols, Bands.B);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				R.set(row, col, image.getBand(Bands.R).getValue(row, col));
				G.set(row, col, image.getBand(Bands.G).getValue(row, col));
				B.set(row, col, image.getBand(Bands.B).getValue(row, col));
			}
		}
	}

	public void applyFunction(Function function, double density) {
		List<Pixel> positions = getPixels(density);

		applyFunctionR(function, positions);
		if (ImageType.RGB == type) {
			applyFunctionG(function, positions);
			applyFunctionB(function, positions);
		}
	}

	private List<Pixel> getPixels(double density) {
		double value = density / 100;
		int amountPixels = (int) (value * rows * cols);

		List<Pixel> pixels = new ArrayList<Pixel>();
		List<Pixel> positions = new ArrayList<Pixel>();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				positions.add(new Pixel(i, j));
			}
		}
		if (density == 100) {
			return positions;
		}

		Collections.shuffle(positions);

		for (int i = 0; i < amountPixels; i++) {
			pixels.add(positions.get(i));
		}

		return pixels;
	}

	private void applyFunctionR(Function function, List<Pixel> positions) {
		for (Pixel pos : positions) {
			R.apply(function, pos.row, pos.col);
		}
	}

	private void applyFunctionG(Function function, List<Pixel> positions) {
		for (Pixel pos : positions) {
			G.apply(function, pos.row, pos.col);
		}
	}

	private void applyFunctionB(Function function, List<Pixel> positions) {
		for (Pixel pos : positions) {
			B.apply(function, pos.row, pos.col);
		}
	}

	public BufferedImage getVisual() {
		int format = BufferedImage.TYPE_INT_RGB;
		if (type.equals(ImageType.GRAYSCALE)) {
			format = BufferedImage.TYPE_BYTE_GRAY;
		} else if (type.equals(ImageType.BINARY)) {
			format = BufferedImage.TYPE_BYTE_BINARY;
		} else if (type.equals(ImageType.RGB)) {
			format = BufferedImage.TYPE_INT_RGB;
		} else if (type.equals(ImageType.HSV)) {
			// TODO HSV images
		}

		// Apply compression, always is linear. To apply other compression go to
		// compressions tab.
		applyFunction(new LinearTransform(this), 100);

		BufferedImage resp = new BufferedImage(cols, rows, format);
		WritableRaster raster = resp.getRaster();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				raster.setSample(col, row, 0, R.getValue(row, col));
				if (ImageType.RGB == type) {
					raster.setSample(col, row, 1, G.getValue(row, col));
					raster.setSample(col, row, 2, B.getValue(row, col));
				}
			}
		}

		return resp;
	}

	public int getHeight() {
		return rows;
	}

	public int getWidth() {
		return cols;
	}

	public Band getBand(Bands band) {
		if (band.equals(Bands.R)) {
			return R;
		} else if (band.equals(Bands.G)) {
			return G;
		} else if (band.equals(Bands.B)) {
			return B;
		}
		return null;
	}

	public double[] avgEachBand() {

		int height = getHeight();
		int width = getWidth();
		double resp[] = { 0.0, 0.0, 0.0 };

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				resp[0] += R.getValue(j, i);
				resp[1] += G.getValue(j, i);
				resp[2] += B.getValue(j, i);
			}
		}

		resp[0] = resp[0] / (height * width);
		resp[1] = resp[1] / (height * width);
		resp[2] = resp[2] / (height * width);

		return resp;
	}

	public ATImage applyLayer(ATImage img) {

		BufferedImage image1 = getVisual();
		BufferedImage image = new BufferedImage(image1.getWidth(),
				image1.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < image1.getWidth(); i++) {
			for (int j = 0; j < image1.getHeight(); j++) {
				image.setRGB(i, j, image1.getRGB(i, j));
			}
		}

		ATImage ret = new ATImage(image, ImageType.RGB);

		for (int row = 0; row < getHeight(); row++)
			for (int col = 0; col < getWidth(); col++) {
				if (img.getBand(Bands.R).getValue(row, col) == 255)
					ret.getBand(Bands.R).set(row, col, 255);

				if (img.getBand(Bands.G).getValue(row, col) == 255)
					ret.getBand(Bands.G).set(row, col, 255);

				if (img.getBand(Bands.B).getValue(row, col) == 255)
					ret.getBand(Bands.B).set(row, col, 255);
			}
		return ret;
	}
	
	public void drawCircle(int x0, int y0, int radius) {

		int error = 1 - radius;
		int errorY = 1;
		int errorX = -2 * radius;
		int x = radius, y = 0;

		R.set(x0, y0 + radius, 255);
		R.set(x0, y0 - radius, 255);
		R.set(x0 + radius, y0, 255);
		R.set(x0 - radius, y0, 255);

		while (y < x) {
			if (error > 0) // >= 0 produces a slimmer circle. =0 produces the
							// circle picture at radius 11 above
			{
				x--;
				errorX += 2;
				error += errorX;
			}
			y++;
			errorY += 2;
			error += errorY;

			R.set(x0 + x, y0 + y, 255);
			R.set(x0 - x, y0 + y, 255);
			R.set(x0 + x, y0 - y, 255);
			R.set(x0 - x, y0 - y, 255);
			R.set(x0 + y, y0 + x, 255);
			R.set(x0 - y, y0 + x, 255);
			R.set(x0 + y, y0 - x, 255);
			R.set(x0 - y, y0 - x, 255);
		}
	}

}
