package edu.it.itba.functions;

import java.util.HashMap;
import java.util.Map;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class Equalize implements Function {
	
	private ATImage image;
	private Map<Integer, Integer> relativeFrec = new HashMap<Integer, Integer>();
	
	public Equalize(ATImage image) {
		this.image = image;
		calculateRelativeFrec();
	}

	private void calculateRelativeFrec() {
		for(int row = 0 ; row < image.getHeight() ; row++) {
			for(int col = 0 ; col < image.getWidth() ; col++) {
				Integer value = (int) image.getBand(Bands.R).getValue(row, col);
				relativeFrec.put(value, relativeFrec.get(value) + 1);
			}
		}
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		// TODO Auto-generated method stub
		return 0;
	}

}
