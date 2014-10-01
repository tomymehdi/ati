package edu.it.itba.models.windows;

import edu.it.itba.enums.Direction;

public class Kirsh extends Window {
	
	private Direction dir;

	public Kirsh(int size, Direction dir) {
		super(size);
		this.dir = dir;
		fillWindowValues();
	}
	
	@Override
	public void fillWindowValues() {
		if(dir.equals(Direction.HORIZONTAL)){
			horizontal();
		} else if(dir.equals(Direction.VERTICAL)){
			vertical();
		} else if(dir.equals(Direction.DIAGONAL)){
			diagonal();
		}
	}

	private void diagonal() {
		double [] aux = {5,5,-3,
						 5,0,-3,
						 -3,-3,-3};
		for(int i = 0; i < size*size ; i++){
			window[i] = aux[i];
		}
	}

	private void vertical() {
		double [] aux = {5,5,5,
						 -3,0,-3,
						 -3,-3,-3};
		for(int i = 0; i < size*size ; i++){
			window[i] = aux[i];
		}
	}

	private void horizontal() {
		double [] aux = {5,-3,-3,
						 5,0,-3,
						 5,-3,-3};
		for(int i = 0; i < size*size ; i++){
			window[i] = aux[i];
		}
	}
	
}
