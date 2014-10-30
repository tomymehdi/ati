package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.enums.Direction;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.windows.Sobel;

public class MaxSuppression implements Function {

	ATImage img;

	public MaxSuppression(ATImage image) {
		img = new ATImage(image);
	}

	public ATImage supressMaxs() {

		ATImage hor = new ATImage(img);
		hor.applyFunction(new PassAdditiveWindow(hor, new Sobel(3,
				Direction.HORIZONTAL)), 100);
		ATImage ver = new ATImage(img);
		ver.applyFunction(new PassAdditiveWindow(ver, new Sobel(3,
				Direction.VERTICAL)), 100);

		double gx, gy, angle;
		double magnitude[][] = new double[img.getHeight()][img.getWidth()];
		Direction direction[][] = new Direction[img.getHeight()][img.getWidth()];
		for (int row = 0; row < img.getHeight(); row++) {
			for (int col = 0; col < img.getWidth(); col++) {
				gx = hor.R.getValue(row, col);
				gy = ver.R.getValue(row, col);
				magnitude[row][col] = Math.sqrt(gx * gx + gy * gy);
				angle = Math.atan2(gy, gx);

				if (angle <= Math.PI / 8) {
					direction[row][col] = Direction.HORIZONTAL;
				} else if (angle <= Math.PI * 3 / 8) {
					direction[row][col] = Direction.DIAGONAL;
				} else if (angle <= Math.PI * 5 / 8) {
					direction[row][col] = Direction.VERTICAL;
				} else if (angle <= Math.PI * 7 / 8) {
					direction[row][col] = Direction.ADIAGONAL;
				} else {
					direction[row][col] = Direction.HORIZONTAL;
				}
			}
		}

		ATImage resp = new ATImage(hor);

		// Supresion de no maximos
		for (int row = 1; row < img.getHeight() - 1; row++) {
			for (int col = 1; col < img.getWidth() - 1; col++) {
				switch (direction[row][col]) {
				case HORIZONTAL:
					if (magnitude[row][col] < magnitude[row][col - 1]
							&& magnitude[row][col] < magnitude[row][col + 1]) {
						resp.R.set(row, col, 0);
					} else {
						resp.R.set(row, col, magnitude[row][col]);
					}
					break;
				case ADIAGONAL:
					if (magnitude[row][col] < magnitude[row + 1][col - 1]
							&& magnitude[row][col] < magnitude[row - 1][col + 1]) {
						resp.R.set(row, col, 0);
					} else {
						resp.R.set(row, col, magnitude[row][col]);
					}
					break;
				case DIAGONAL:
					if (magnitude[row][col] < magnitude[row - 1][col - 1]
							&& magnitude[row][col] < magnitude[row + 1][col + 1]) {
						resp.R.set(row, col, 0);
					} else {
						resp.R.set(row, col, magnitude[row][col]);
					}
					break;
				case VERTICAL:
					if (magnitude[row][col] < magnitude[row - 1][col]
							&& magnitude[row][col] < magnitude[row + 1][col]) {
						resp.R.set(row, col, 0);
					} else {
						resp.R.set(row, col, magnitude[row][col]);
					}
					break;
				default:
					break;
				}

			}
		}
		return resp;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		// TODO Auto-generated method stub
		return 0;
	}

}
