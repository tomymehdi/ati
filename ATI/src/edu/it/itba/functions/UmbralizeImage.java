package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class UmbralizeImage implements Function {

	int umbral;

	public UmbralizeImage(int umbralToApply) {
		umbral = umbralToApply;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {

		if (value > umbral)
			return 255;
		else
			return 0;
	}

}
