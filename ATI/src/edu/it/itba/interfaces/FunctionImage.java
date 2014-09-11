package edu.it.itba.interfaces;

import edu.it.itba.enums.Bands;

public interface FunctionImage {

	double applyImage(double value, int row, int col, Bands band);

}
