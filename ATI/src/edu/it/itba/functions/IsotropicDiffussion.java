package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class IsotropicDiffussion implements Function {
	
	private double sigma;
	private ATImage img;
	
	public IsotropicDiffussion(ATImage img, double sigma) {
		this.img = img;
		this.sigma = sigma;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		int x = col - img.getWidth()/2;
		int y = row - img.getHeight()/2;
		double resp = value * ((1/(2*Math.PI*sigma*sigma))*Math.pow(Math.E, ((-(y*y+x*x))/(2*sigma*sigma))));
		return resp;
	}

}
