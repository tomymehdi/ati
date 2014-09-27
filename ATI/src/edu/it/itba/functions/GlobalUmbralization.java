package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class GlobalUmbralization implements Function {
	
	private ATImage img;
	private int epocs; // TODO consultar si esta bien usar epocas o hay que usar error(un epsilon)
	private double initialUmbral;
	private double umbralCalculated;
	private double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
	
	public GlobalUmbralization(ATImage img, int epocs) {
		this.img = new ATImage(img);
		this.epocs = epocs;
		calculateMinMax();
		initialUmbral = min + 0.5 * (max - min);
		algorithm();
	}

	private void algorithm() {
		double umbralCalculated = initialUmbral;
		double m1=0,m2=0;
		int countm1=0,countm2=0;
		for(int i = 0 ; i < epocs ; i++) {
			
			for(int row=0 ; row < img.getHeight() ; row++){
				for(int col=0 ; col < img.getWidth() ; col++){
					if(img.getBand(Bands.R).getValue(row, col) < umbralCalculated){
						countm1++;
						m1 += img.getBand(Bands.R).getValue(row, col);
					} else{
						countm2++;
						m2 += img.getBand(Bands.R).getValue(row, col);
					}
				}
			}
			
			m1 = m1/countm1;
			m2 = m2/countm2;
			
			umbralCalculated = 0.5 * (m1 + m2);
			
		}
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
