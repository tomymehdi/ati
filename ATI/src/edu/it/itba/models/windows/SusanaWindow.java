package edu.it.itba.models.windows;

public class SusanaWindow extends Window {

	public SusanaWindow(int size) {
		super(size);
		fillWindowValues();
	}

	@Override
	public void fillWindowValues() {

		double sWindow[] = { 0, 0, 1, 1, 1, 0, 0,
							 0, 1, 1, 1, 1, 1, 0,
							 1, 1, 1, 1, 1, 1, 1, 
							 1, 1, 1, 1, 1, 1, 1,
							 1, 1, 1, 1, 1, 1, 1,
							 0, 1, 1, 1, 1, 1, 0,
							 0, 0, 1, 1, 1, 0, 0 };

		for (int i = 0; i < size * size; i++) {
			window[i] = sWindow[i];

		}
	}
}
