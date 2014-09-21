package edu.it.itba.swing.dialogs;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class ExponentialNoise implements Function {

	double lambda;

	public ExponentialNoise(double lambda) {
		this.lambda = lambda;

	}

	@Override
	public double apply(double value, int row, int col, Bands band) {

		return exponential(lambda) * value;
	}

	private double exponential(double lambda) {
		double seed = Math.random();
		double exponentialRandom = (-1 / lambda) * Math.log(seed);
		
		return exponentialRandom;
	}
}
