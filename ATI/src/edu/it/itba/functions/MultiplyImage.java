package edu.it.itba.functions;

import edu.it.itba.models.ATImage;

public class MultiplyImage extends OptFunction {

	public MultiplyImage(ATImage image, ATImage toMult) {
		super(image, toMult);

	}

	@Override
	public double optPixel(double value1, double value2) {
		return value1 * value2;
	}

}
