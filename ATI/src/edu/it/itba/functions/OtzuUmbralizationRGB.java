package edu.it.itba.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Band;

public class OtzuUmbralizationRGB implements Function {
	
	private double umbralCalculated;
	

	private Map<Integer, Integer> histogram = new HashMap<Integer,Integer>();
	
	private List<Band> rgbBands = new ArrayList<Band>();
	private int height;
	private int width;
	
	public OtzuUmbralizationRGB( ATImage img ) {
		rgbBands.add(0, img.R);
		rgbBands.add(1, img.G);
		rgbBands.add(2, img.B);
		height = img.getHeight();
		width = img.getWidth();
		calculateHistogram();
		algorithm();
	}

	private void calculateHistogram() {
		for(int row = 0 ; row< height ; row++){
			for(int col = 0 ; col < width ; col++){
				for(Band band: rgbBands){
					if(histogram.containsKey((int) band.getValue(row, col))){
						histogram.put((int) band.getValue(row, col), histogram.get((int) band.getValue(row, col))+1);
					} else {
						histogram.put((int) band.getValue(row, col), 1);
					}
				}
			}
		}		
	}

	private void algorithm() {
		int sum = 0;
		for(int i = 0 ; i < 256 ; i++){
			if(histogram.containsKey(i)){
				sum += i * histogram.get(i);
			}
		}
		int sumB = 0;
		int wB = 0, wF = 0;
		int mB, mF;
		int total = height * width;
		double max = 0.0, between;
		double threshold1 = 0.0, threshold2 = 0.0;
		for(int i = 0; i < histogram.size(); ++i) {
			if(histogram.containsKey(i)){
				wB += histogram.get(i);
				if (wB == 0)
					continue;
				wF = total - wB;
				if (wF == 0)
					break;
				sumB += i * histogram.get(i);
				mB = sumB / wB;
				mF = (sum - sumB) / wF;
				between = wB * wF * Math.pow(mB - mF, 2);
				if ( between >= max ) {
					threshold1 = i;
					if ( between > max ) {
						threshold2 = i;
					}
					max = between;            
				}
			}
	    }
		
		umbralCalculated = ( threshold1 + threshold2 ) / 2.0;
		
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		if (value > umbralCalculated)
			return 255;
		else
			return 0;
	}
	
	public double getUmbralCalculated() {
		return umbralCalculated;
	}
}
