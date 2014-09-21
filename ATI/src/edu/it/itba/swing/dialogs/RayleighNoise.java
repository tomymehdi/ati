package edu.it.itba.swing.dialogs;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class RayleighNoise implements Function {

	private double eta;

	public RayleighNoise(double eta) {

		this.eta = eta;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		return rayleigh(eta) * value;
	}

	private double rayleigh(double eta) {

		double seed = Math.random();

		double rayleightRandom = eta * Math.sqrt(-2 * Math.log(1 - seed));
		return rayleightRandom;
	}

}
