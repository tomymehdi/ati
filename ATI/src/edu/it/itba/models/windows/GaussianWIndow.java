package edu.it.itba.models.windows;

import edu.it.itba.utils.ImageUtils;

public class GaussianWIndow extends Window {

	private double sigma;

	public GaussianWIndow(int size, double sigma) {
		super(size);
		this.sigma = sigma;
		fillWindowValues();
	}

	@Override
	public void fillWindowValues() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				window[i * size + j ] = ImageUtils.gaussNumber(i-size/2, j-size/2, sigma);
			}
		}
	}

}
