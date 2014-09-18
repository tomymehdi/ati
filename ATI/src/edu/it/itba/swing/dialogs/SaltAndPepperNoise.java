package edu.it.itba.swing.dialogs;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class SaltAndPepperNoise implements Function {

	@Override
	public double apply(double value, int row, int col, Bands band) {

		double random = Math.random();

		if (random < 0.5)

			return 0;
		else
			return 255;
	}

}
