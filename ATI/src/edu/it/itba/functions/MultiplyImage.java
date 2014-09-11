package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.FunctionImage;
import edu.it.itba.models.ATImage;

public class MultiplyImage implements FunctionImage {
	private ATImage image;
	
	public MultiplyImage(ATImage image) {
		this.image = image;
	}
	
	@Override
	public double applyImage(double value, int row, int col, Bands band) {
		return value * image.getBand(band).getValue(row, col);
	}

}
