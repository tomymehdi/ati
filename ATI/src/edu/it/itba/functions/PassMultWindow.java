package edu.it.itba.functions;

import java.util.Arrays;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Window;

public class PassMultWindow implements Function {
	
	private Window window;
	private ATImage image;
	
	public PassMultWindow(ATImage image, Window window) {
		this.window = window;
	}
	
	@Override
	public double apply(double value, int row, int col, Bands band) {
		int colStart = col - (window.size / 2);
		int colEnd = col + (window.size / 2);
		int rowStart = row - (window.size / 2);
		int rowEnd = row + (window.size / 2);

		double[] w = new double[window.size * window.size];

		for (int i = colStart, k = 0; i < colEnd; i++) {
			for (int j = rowStart; j < rowEnd; j++, k++) {
				w[k] = image.getBand(band).getValue(i, j);
			}
		}
		Arrays.sort(w);

		return w[((window.size * window.size) / 2) + 1];
	}
}
