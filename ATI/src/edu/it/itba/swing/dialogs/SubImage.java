package edu.it.itba.swing.dialogs;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class SubImage implements Function {

	int x, y;
	ATImage img;

	public SubImage(ATImage img, int x, int y) {
		this.x = x;
		this.y = y;
		this.img = img;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {

		return img.getBand(band).getValue(row + x, col + y);
	}

}
