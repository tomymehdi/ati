package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Window;

public class PassAdditiveWindow implements Function {
	
	private Window window;
	private ATImage image;
	
	public PassAdditiveWindow(ATImage image, Window window) {
		this.image = image;
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
		
		for (int i = rowStart, z = 0; i < rowEnd; i++, z++) {
			for (int j = colStart, k = 0; j < colEnd; j++, k++) {
				currPixel = image.getBand(band).getValue(i, j);
				resp += currPixel * window.window[z * window.size + k];
			}
		}
		
		return resp;
	}
}
