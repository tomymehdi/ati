package edu.it.itba.functions;


public class LeclercBorderDetector extends MaterialHeatDistribution {

	private double sigma;
	
	public LeclercBorderDetector(double sigma){
		this.sigma = sigma;
	}
	
	@Override
	public double g(double x) {
		return Math.exp(-Math.pow(Math.abs(x), 2) / Math.pow(sigma, 2));
	}

}