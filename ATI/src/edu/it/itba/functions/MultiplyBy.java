package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class MultiplyBy implements Function {
	private double scalar;
	
	public MultiplyBy( double scalar ) {
		this.scalar = scalar;
	}
	
	@Override
	public double apply(double value, int row, int col, Bands band) {
		return value*scalar;
	}

}
