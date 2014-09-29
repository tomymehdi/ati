package edu.it.itba.functions;

import java.util.HashMap;
import java.util.Map;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class OtzuUmbralization implements Function {
	
	private ATImage img;
	private double umbralCalculated;
	private Map<Integer, Integer> histogram = new HashMap<Integer,Integer>();
	private double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
	
	public OtzuUmbralization( ATImage img ) {
		this.img = img;
		calculateHistogram();
		algorithm();
	}

	private void calculateHistogram() {
		for(int row = 0 ; row< img.getHeight() ; row++){
			for(int col = 0 ; col < img.getWidth() ; col++){
				if(histogram.containsKey((int) img.R.getValue(row, col))){
					histogram.put((int) img.R.getValue(row, col), histogram.get((int) img.R.getValue(row, col))+1);
				} else {
					histogram.put((int) img.R.getValue(row, col), 1);
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
		int total = img.getHeight() * img.getWidth();
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
	
	private void calculateMinMax() {
		double r, g, b;
		for (int row = 0; row < img.getHeight(); row++) {
			for (int col = 0; col < img.getWidth(); col++) {
				r = img.R.getValue(row, col);
				if (r < min)
					min = r;
				if (r > max)
					max = r;
				g = img.G.getValue(row, col);
				if (g < min)
					min = g;
				if (g > max)
					max = g;
				b = img.B.getValue(row, col);
				if (b < min)
					min = b;
				if (b > max)
					max = b;
			}
		}

	}

}
