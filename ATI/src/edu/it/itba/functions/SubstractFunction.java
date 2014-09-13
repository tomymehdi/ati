package edu.it.itba.functions;

import edu.it.itba.models.ATImage;

public class SubstractFunction extends OptFunction {

	public SubstractFunction(ATImage image, ATImage substractImage) {
		super(image, substractImage);

	}

	@Override
	public double optPixel(double value1, double value2) {
		return value1 - value2;
	}

}
