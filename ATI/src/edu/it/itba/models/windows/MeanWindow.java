package edu.it.itba.models.windows;

public class MeanWindow extends Window {

	public MeanWindow(int size) {
		super(size);
		fillWindowValues();
	}

	@Override
	public void fillWindowValues() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				window[i + j * size] = (double) 1 / (size * size);
			}
		}
	}

}
