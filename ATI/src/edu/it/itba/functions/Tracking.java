package edu.it.itba.functions;

import java.util.HashSet;
import java.util.Set;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Pixel;

public class Tracking implements Function {

	private Set<Pixel> in;
	private Set<Pixel> out;
	private int fis[][];
	private ATImage img;
	
	public Tracking(ATImage img, int row, int col, int width, int height,
			HashSet<Pixel> in, HashSet<Pixel> out) {
		this.img = img;
		this.in = in;
		this.out = out;
		runAlgorithm();
	}

	private void runAlgorithm() {
		if(in.isEmpty() || out.isEmpty()){
			initializeSetsAndFis();
		}
		
		// Paso 1 expandir
		// Paso 2 contraer
		// Paso 3 ventana gaussiana
		
	}

	private void initializeSetsAndFis() {
		fis = new int[img.getHeight()][img.getWidth()];
		
		
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		// TODO Auto-generated method stub
		return 0;
	}

}
