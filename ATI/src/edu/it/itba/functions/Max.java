package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class Max implements Function {

	ATImage img;

	public Max(ATImage img) {

		this.img = img;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {

		double imgValue = img.getBand(band).getValue(row, col);

		return value > imgValue ? value : imgValue;
	}
}
