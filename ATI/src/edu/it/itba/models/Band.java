package edu.it.itba.models;

public class Band {

	private double matrix[][];

	public Band(int rows, int cols) {
		matrix = new double[rows][cols];
	}

	public void set(int row, int col, double value) {
		matrix[row][col] = value;
	}

	public double getValue(int row, int col) {
		return matrix[row][col];
	}
}
