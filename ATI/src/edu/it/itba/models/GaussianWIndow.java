package edu.it.itba.models;

import edu.it.itba.utils.ImageUtils;

public class GaussianWIndow extends Window {
	
	private double sigma;
	
	public GaussianWIndow(int size, double sigma) {
		this.size = size;
		this.sigma = sigma;
		this.window = new double[this.size*this.size];
		fillWindowValues();
	}
	
	@Override
	public void fillWindowValues() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				window[i + j * size] = ImageUtils.gaussNumber(i, j, sigma);
			}
		}
	}

}
