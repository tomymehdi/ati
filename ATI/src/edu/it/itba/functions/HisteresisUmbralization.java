package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class HisteresisUmbralization implements Function {
	ATImage img;
	int t1, t2;

	public HisteresisUmbralization(ATImage image, int t1, int t2) {
		this.img = new ATImage(image);
		this.t1 = t1;
		this.t2 = t2;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {

		if (value <= t2
				|| (value <= t1 && img.R.getValue(row, col - 1) == 0
						&& img.R.getValue(row, col + 1) == 0
						&& img.R.getValue(row - 1, col) == 0 && img.R.getValue(
						row + 1, col) == 0)) {
			return 0;
		}
		return 255;

	}

}
