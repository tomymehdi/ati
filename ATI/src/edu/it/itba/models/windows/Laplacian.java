package edu.it.itba.models.windows;

public class Laplacian extends Window {

	public Laplacian(int size) {
		super(3);
	}

	@Override
	public void fillWindowValues() {
		double [] aux = {0,-1,0,
						 -1,4,-1,
						 0,-1,0};
		for(int i = 0; i < size*size ; i++) {
			window[i] = aux[i];
		}
	}

}
