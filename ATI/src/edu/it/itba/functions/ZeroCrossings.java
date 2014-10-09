package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class ZeroCrossings implements Function {
	
	private ATImage img;
	private ATImage result;
	private ATImage result2;
	
	public ZeroCrossings(ATImage img) {
		this.img = img;
		this.result = new ATImage(img);
		this.result2 = new ATImage(img);
		calculateResultImage();
	}

	private void calculateResultImage() {
		int aux = 0;
		for(int row = 0 ; row < result.getHeight() ; row++){
			aux = 0;
			for(int col = 0 ; col < result.getWidth() ; col++){
				if(img.R.getValue(row, col) < 0 && img.R.getValue(row, col-1) > 0 ||
						img.R.getValue(row, col) > 0 && img.R.getValue(row, col-1) < 0) {
					if(Math.abs(img.R.getValue(row, col)-img.R.getValue(row, col-1))>100){
						if(aux == 0){
							aux = 255;
						} else {
							aux = 0;
						}
					}
				}
				result.R.set(row, col, aux);
			}
		}
		
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		return result.getBand(band).getValue(row, col);
	}

}
