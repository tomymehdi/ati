package edu.it.itba.enums;

public enum Side {
	LEFT(0), RIGHT(1);
	
	int value;

	Side(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}
}
