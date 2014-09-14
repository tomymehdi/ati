package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Window;

public class PassAdditiveWindow implements Function {
	
	private Window window;
	private ATImage image;
	
	public PassAdditiveWindow(ATImage image, Window window) {
		this.window = window;
	}
	
	@Override
	public double apply(double value, int row, int col, Bands band) {
		int colStart = col - (window.size / 2);
		int colEnd = col + (window.size / 2);
		int rowStart = row - (window.size / 2);
		int rowEnd = row + (window.size / 2);
		
		double resp = 0;
		double currPixel;
		
		for (int i = colStart, k = 0; i < colEnd; i++, k++) {
			for (int j = rowStart, z = 0; j < rowEnd; j++, z++) {
				currPixel = image.getBand(band).getValue(i, j);
				resp += currPixel * window.window[z * window.size + k];
			}
		}
		
		return resp;
	}
}
