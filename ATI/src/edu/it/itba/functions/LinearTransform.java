package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class LinearTransform implements Function {

	private ATImage image;
	private double min = Double.MAX_VALUE, max = Double.MIN_VALUE;

	public LinearTransform(ATImage image) {
		this.image = image;
		calculateMinMax();
	}

	public double apply(double value, int row, int col, Bands band) {
		return linearTransform0255(value, min, max);
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

	private static double linearTransform0255(double value, double min,
			double max) {
		double resp, m;
		double minRGB = 0;
		double maxRGB = 255;

		// f(x) = m*x + c
		// f(x) = y = m*x + c;
		// y = m*x +c
		// y tiene que estar entre 0 y 255 => c = -min+minRGB => ya el menor
		// valor coincide con el menor valor RGB.
		// Ahora hay que calcular m. Me determina como "achatamos"
		// Esta pendiente es m va a ser (minRGB - min)/ (maxRGB - min)
		// Ejemplo:
		// min = -100 max = 900
		// entonces c = 100
		// y m = (900+100)/(255-0)
		m = (maxRGB - minRGB) / (max - min);
		resp = m * value - min;

		return resp;
	}
}
