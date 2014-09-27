package edu.it.itba.functions;

import java.util.List;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class OtzuUmbralization implements Function {
	
	private ATImage img;
	private double umbralCalculated;
	private List<Double> classes;
	public OtzuUmbralization(ATImage img, List<Double> classes ) {
		this.img = img;
		this.classes = classes;
		calculatePs();
		calculateWs();
		calculateMus(); //esperanza
		algorithm();
	}

	private void algorithm() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		// TODO Auto-generated method stub
		return 0;
	}

}
