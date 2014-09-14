package edu.it.itba.models;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import edu.it.itba.enums.ImageType;

public class ATImage {

	public Band[] bands = new Band[3];

	private int rows;
	private int cols;
	private ImageType type;

	public ATImage(int rows, int cols, ImageType type) {
		this.rows = rows;
		this.cols = cols;
		this.type = type;
		for (int i = 0; i < 3; i++) {
			bands[i] = new Band(rows, cols);
		}
	}

	public ATImage(BufferedImage image, ImageType type) {
		this.rows = image.getHeight();
		this.cols = image.getWidth();
		this.type = type;

		for (int i = 0; i < 3; i++) {
			bands[i] = new Band(rows, cols);
		}

		Raster raster = image.getRaster();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				for (int k = 0; k < 3; k++) {
					try {
						bands[k].set(row, col, raster.getSample(col, row, 0));
					} catch (IndexOutOfBoundsException e) {

					}

				}
			}
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
			// TODO
		}
		BufferedImage resp = new BufferedImage(cols, rows, format);
		WritableRaster raster = resp.getRaster();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				for (int k = 0; k < 3; k++) {
					try {
						raster.setSample(col, row, k,
								bands[k].getValue(row, col));
					} catch (IndexOutOfBoundsException e) {

					}
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

	@Override
	public ATImage clone() {

		return new ATImage(getVisual(), type);
	}

	public double getPixel(int row, int col, int band) {
		return bands[band].getValue(row, col);
	}

	public void setPixel(int row, int col, int band, double value) {
		bands[band].set(row, col, value);
	}

}
