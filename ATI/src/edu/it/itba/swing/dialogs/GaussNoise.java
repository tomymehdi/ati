package edu.it.itba.swing.dialogs;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class GaussNoise implements Function {

	private double mu, sigma;

	public GaussNoise(double mu, double sigma) {

		this.mu = mu;
		this.sigma = sigma;
		// TODO Auto-generated constructor stub
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {

		return gauss(mu, sigma) + value;
	}

	private double gauss(double mu, double sigma) {

		double seed = Math.random();
		double seed2 = Math.random();

		// double gaussRandom = (1 / (sigma * Math.sqrt(2 * Math.PI)))
		// * Math.pow(Math.E,
		// Math.pow(2, (-seed - mu)) / (2 * Math.pow(2, sigma)));

		double gaussRandom = Math.sqrt(-2 * Math.log(seed))
				* Math.cos(2 * Math.PI * seed2);

		gaussRandom = sigma * gaussRandom + mu;

		return gaussRandom;
	}
}
