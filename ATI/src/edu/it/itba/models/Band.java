package edu.it.itba.models;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class Band {

	private Bands band;
	public double matrix[][];
	private int rows, cols;

	public Band(int rows, int cols, Bands band) {
		matrix = new double[rows][cols];
		this.band = band;
		this.rows = rows;
		this.cols = cols;
	}

	public void set(int row, int col, double value) {
		if (row >= rows || col >= cols || col < 0 || row < 0)
			return;
		matrix[row][col] = value;
	}

	public void apply(Function function, int row, int col) {
		matrix[row][col] = function.apply(matrix[row][col], row, col, band);
	}

	public double getValue(int row, int col) {
		if (row < 0 || col < 0 || row >= rows || col >= cols) {
			return 0;
		}
		return matrix[row][col];
	}

}