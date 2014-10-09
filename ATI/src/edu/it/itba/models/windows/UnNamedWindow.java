package edu.it.itba.models.windows;

import edu.it.itba.enums.Direction;

public class UnNamedWindow extends Window {

	Direction dir;

	public UnNamedWindow(int size, Direction dir) {
		super(size);
		this.dir = dir;
		fillWindowValues();
	}

	@Override
	public void fillWindowValues() {
		if (dir.equals(Direction.HORIZONTAL)) {
			horizontal();
		} else if (dir.equals(Direction.VERTICAL)) {
			vertical();
		} else if (dir.equals(Direction.DIAGONAL)) {
			diagonal();
		} else if (dir.equals(Direction.ADIAGONAL))
			adiagonal();

	}

	private void adiagonal() {
		double[] aux = { -1, 1, 1, -1, -2, 1, -1, 1, 1 };
		for (int i = 0; i < size * size; i++) {
			window[i] = aux[i];
		}
	}

	private void diagonal() {
		double[] aux = { 1, 1, -1, 1, -2, -1, 1, 1, -1 };
		for (int i = 0; i < size * size; i++) {
			window[i] = aux[i];
		}
	}

	private void vertical() {
		double[] aux = { 1, 1, 1, 1, -2, 1, -1, -1, -1 };
		for (int i = 0; i < size * size; i++) {
			window[i] = aux[i];
		}
	}

	private void horizontal() {
		double[] aux = { 1, 1, -1, 1, -2, -1, 1, 1, -1 };
		for (int i = 0; i < size * size; i++) {
			window[i] = aux[i];
		}
	}
}
