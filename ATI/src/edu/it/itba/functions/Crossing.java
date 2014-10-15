package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class Crossing implements Function {

	private ATImage img;
	private ATImage result;
	private ATImage result2;
	private int umbral;

	public Crossing(ATImage img, int umbral) {
		this.img = img;
		this.result = new ATImage(img);
		this.result2 = new ATImage(img);
		this.umbral = umbral;
		calculateResultImage();
	}

	private void calculateResultImage() {
		int aux = 0;
		for (int row = 0; row < result.getHeight(); row++) {
			aux = 0;
			for (int col = 0; col < result.getWidth(); col++) {
				if (img.R.getValue(row, col) * img.R.getValue(row, col - 1) < 0) {
					if (Math.abs(img.R.getValue(row, col)
							- img.R.getValue(row, col - 1)) > umbral) {
						aux = 255;
					}
				}
				result.R.set(row, col, aux);
				aux=0;
			}
		}

	}

	public double apply(double value, int row, int col, Bands band) {
		return result.getBand(band).getValue(row, col);
	}
}
