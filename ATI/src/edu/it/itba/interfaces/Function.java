package edu.it.itba.interfaces;

import edu.it.itba.enums.Bands;

public interface Function {

	double apply(double value, int row, int col, Bands band);

}
