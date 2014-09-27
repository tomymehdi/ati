package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class Contrast implements Function {

	int r1;
	int r2;

	public Contrast(int r1, int r2) {
		this.r1 = r1;
		this.r2 = r2;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		if (value < r1)
			return darken(value);
		else if (value > r2)
			return lighten(value);

		return value;
	}

	private double lighten(double value) {
		return value * 2;
	}

	private double darken(double value) {
		return value / 2;
	}
}
