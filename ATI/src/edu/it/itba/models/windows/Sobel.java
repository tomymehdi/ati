package edu.it.itba.models.windows;

import edu.it.itba.enums.Direction;

public class Sobel extends Window {
	
private Direction dir;
	
	public Sobel(int size, Direction dir) {
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

	private void horizontal() {
		double [] aux = {1,0,-1,
				 2,0,-2,
				 1,0,-1};
		for(int i = 0; i < size*size ; i++){
			window[i] = aux[i];
		}
	}

	private void vertical() {
		double [] aux = {1,2,1,
		  		 0,0,0,
	  			 -1,-2,-1};
		for(int i = 0; i < size*size ; i++){
			window[i] = aux[i];
		}
	}

	private void diagonal() {
		double [] aux = {2,1,0,
						 1,0,-1,
						 0,-1,-2};
		for(int i = 0; i < size*size ; i++){
			window[i] = aux[i];
		}
	}

}
