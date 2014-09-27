package edu.it.itba.models.windows;

import edu.it.itba.enums.Direction;

public class Prewitt extends Window {

	private Direction dir;
	
	public Prewitt(int size, Direction dir) {
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
	
	private void horizontal(){
		for(int i = 0 ; i < size ; i++){
			for( int j = 0 ; j < size ; j++){
				if(j==0){
					window[i * size + j ] = (double) 1.0;
				} else if(j==1){
					window[i * size + j ] = (double) 0.0;
				} else if(j==2){
					window[i * size + j ] = (double) -1.0;
				}
			}
		}
	}
	
	private void vertical(){
		for(int i = 0 ; i < size ; i++){
			for( int j = 0 ; j < size ; j++){
				if(i==0){
					window[i * size + j ] = (double) 1.0;
				} else if(i==1){
					window[i * size + j ] = (double) 0.0;
				} else if(i==2){
					window[i * size + j ] = (double) -1.0;
				}
			}
		}
	}
	
	private void diagonal(){
		for(int i = 0 ; i < size ; i++){
			for( int j = 0 ; j < size ; j++){
				if((i+j)==2){
					window[i * size + j ] = (double) 0.0;
				} else if((i+j)<2){
					window[i * size + j ] = (double) 1.0;
				} else if((i+j)>2){
					window[i * size + j ] = (double) -1.0;
				}
			}
		}
	}

}
