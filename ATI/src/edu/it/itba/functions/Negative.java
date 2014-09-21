package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class Negative implements Function {

	@Override
	public double apply(double value, int row, int col, Bands band) {
		return 255 - value;
	}

}
