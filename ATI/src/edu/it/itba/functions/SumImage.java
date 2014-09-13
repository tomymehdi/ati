package edu.it.itba.functions;

import edu.it.itba.models.ATImage;

public class SumImage extends OptFunction {

	public SumImage(ATImage image, ATImage toSum) {
		super(image, toSum);
	}

	@Override
	public double optPixel(double value1, double value2) {
		return value1 + value2;
	}

}
