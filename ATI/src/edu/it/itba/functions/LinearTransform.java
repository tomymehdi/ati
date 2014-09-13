package edu.it.itba.functions;

import edu.it.itba.models.ATImage;

public class LinearTransform extends Function {

	private double min = Double.MAX_VALUE, max = Double.MIN_VALUE;

	public LinearTransform(ATImage image) {
		super(image);
		calculateMinMax();
	}

	public void apply() {

		ATImage image = getImage();

		for (int col = 0; col < image.getWidth(); col++) {
			for (int row = 0; row < image.getHeight(); row++) {
				for (int band = 0; band < 3; band++) {
					image.setPixel(row, col, band,
							linearTransform0255(image.getPixel(row, col, band)));
				}
			}
		}

	}

	private void calculateMinMax() {
		ATImage image = getImage();

		double value;
		for (int row = 0; row < image.getHeight(); row++) {
			for (int col = 0; col < image.getWidth(); col++) {
				for (int k = 0; k < 3; k++) {
					value = image.bands[k].getValue(row, col);
					if (value < min)
						min = value;
					if (value > max)
						max = value;
				}

			}
		}

	}

	private double linearTransform0255(double value) {
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
