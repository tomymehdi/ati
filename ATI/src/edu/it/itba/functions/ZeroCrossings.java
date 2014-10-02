package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class ZeroCrossings implements Function {
	
	private ATImage img;
	private ATImage result;
	
	public ZeroCrossings(ATImage img) {
		this.img = img;
		this.result = new ATImage(img);
		calculateResultImage();
	}

	private void calculateResultImage() {
		for(int row = 0 ; row < result.getHeight() ; row++){
			for(int col = 0 ; col < result.getWidth() ; col++){
				if(img.R.getValue(row, col) == 0){
					result.R.set(row, col, 0);
				} else if (img.R.getValue(row, col-1) < 0 &&
						img.R.getValue(row, col) > 0){
					result.R.set(row, col, 255);
				} else {
					result.R.set(row, col, 0);
				}
			}
		}
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		return result.getBand(band).getValue(row, col);
	}

}
