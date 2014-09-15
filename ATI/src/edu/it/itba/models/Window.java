package edu.it.itba.models;

public abstract class Window {

	public double[] window;
	public int size;

	public Window(int size) {

		window = new double[size * size];
		this.size = size;
	}

	public abstract void fillWindowValues();

}
