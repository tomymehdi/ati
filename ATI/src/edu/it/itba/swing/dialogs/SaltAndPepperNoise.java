package edu.it.itba.swing.dialogs;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class SaltAndPepperNoise implements Function {

	@Override
	public double apply(double value, int row, int col, Bands band) {

		double random = Math.random();

		if (random < 0.2){
			return 0;
		} else if(random >0.8){
			return 255;
		} else{
			return value;
		}
	}

}
