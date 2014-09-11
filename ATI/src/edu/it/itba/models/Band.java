package edu.it.itba.models;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.interfaces.FunctionImage;

public class Band {
	
	private Bands band;
	private double matrix[][];
	
	public Band(int rows, int cols, Bands band) {
		matrix = new double[rows][cols];
		this.band = band;
	}
	
	public void set(int row, int col, double value){
		matrix[row][col] = value;
	}
	
	public void apply(Function function, int row, int col){
		matrix[row][col] = function.apply(matrix[row][col]);
	}
	
	public void applyBand(FunctionImage function, int row, int col){
		matrix[row][col] = function.applyImage(matrix[row][col], row, col, band);
	}

	public double getValue(int row, int col) {
		return matrix[row][col];
	}

}
