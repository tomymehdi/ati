package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class SumImage implements Function {
	
	private ATImage image;
	
	public SumImage(ATImage image) {
		this.image = image;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		return value + image.getBand(band).getValue(row, col);
	}

}