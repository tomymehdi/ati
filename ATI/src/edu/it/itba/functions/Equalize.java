package edu.it.itba.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
				if(relativeFrec.containsKey(value)){
					relativeFrec.put(value, relativeFrec.get(value) + 1);
				} else{
					relativeFrec.put(value, 1);
				}
			}
		}
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		int pixel = (int)image.getBand(band).getValue(row, col);
		double sum = 0;
		for(Entry<Integer, Integer> e: relativeFrec.entrySet()){
			if(e.getValue() <= pixel){
				sum += e.getValue();
			}
		}
		return sum/(image.getHeight()*image.getWidth());
	}

}
