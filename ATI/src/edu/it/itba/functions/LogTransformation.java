package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class LogTransformation implements Function {

	private ATImage image;
	private double min = Double.MAX_VALUE, max = Double.MIN_VALUE;

	public LogTransformation(ATImage image) {
		this.image = image;
		calculateMinMax();
	}

	public double apply(double value, int row, int col, Bands band) {
		return logTransform0255(value, min, max);
	}

	private void calculateMinMax() {
		double r, g, b;
		for (int row = 0; row < image.getHeight(); row++) {
			for (int col = 0; col < image.getWidth(); col++) {
				r = image.R.getValue(row, col);
				if (r < min)
					min = r;
				if (r > max)
					max = r;
				g = image.G.getValue(row, col);
				if (g < min)
					min = g;
				if (g > max)
					max = g;
				b = image.B.getValue(row, col);
				if (b < min)
					min = b;
				if (b > max)
					max = b;
			}
		}

	}

	private static double logTransform0255(double value, double min,
			double max) {
		double resp;
		double minRGB = 0;
		double maxRGB = 255;
		double c;

		c = (minRGB + maxRGB)/Math.log((min+1)*(max+1));
		resp = c * Math.log(value + 1);

		return resp;
	}

}
