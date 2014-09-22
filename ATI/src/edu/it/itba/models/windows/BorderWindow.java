package edu.it.itba.models.windows;

public class BorderWindow extends Window {

	public BorderWindow(int size) {
		super(size);
		fillWindowValues();

	}

	@Override
	public void fillWindowValues() {

		for (int i = 0; i < size * size; i++) {
			if (i != (size * size) / 2)
				window[i] = -1;
			else
				window[i] = (size * size) - 1;
		}

	}
}
