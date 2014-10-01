package edu.it.itba.functions;

public class LorentzBorderDetector extends MaterialHeatDistribution {

	private double sigma;

	public LorentzBorderDetector(double sigma) {
		this.sigma = sigma;
	}

	@Override
	public double g(double x) {
		return 1 / ((Math.pow(Math.abs(x), 2) / Math.pow(sigma, 2)) + 1);
	}

}
