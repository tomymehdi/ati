package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;

public class MultiplyBy implements Function {
	private double scalar;
	
	public MultiplyBy( double scalar ) {
		this.scalar = scalar;
	}
	// TODO Falta:
	//			-cambiar imagen de lado
	//			-equalizar imagen
	//			-ventana realce de borde
	//			-revisar rawlena por el punto naranja
	
	@Override
	public double apply(double value, int row, int col, Bands band) {
		return value*scalar;
	}

}
