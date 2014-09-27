package edu.it.itba.models.windows;

public class LoG extends Window {
	private double sigma;
	public LoG(int size, double sigma) {
		super(size);
		this.sigma = sigma;
	}

	@Override
	public void fillWindowValues() {
		for(int i = 0; i< size; i++){
			for(int j = 0; j<size ; j++){
				window[i*size + j] = log(i,j);
			}
		}
	}

	private double log(int x, int y) {
		return (-1/(sigma*Math.PI)) * (1 - ((x*x+y*y) / (2*sigma*sigma))) * (Math.pow(Math.E, (- ((x*x+y*y) / (2*sigma*sigma))) ));
	}
}
