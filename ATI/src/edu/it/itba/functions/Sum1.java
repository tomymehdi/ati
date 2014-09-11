package edu.it.itba.functions;

import edu.it.itba.interfaces.Function;

public class Sum1 implements Function {

	@Override
	public double apply(double value) {
		return value + 1;
	}

}
