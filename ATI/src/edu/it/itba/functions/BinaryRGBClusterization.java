package edu.it.itba.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Band;
import edu.it.itba.models.Pixel;

public class BinaryRGBClusterization implements Function {
	
	private ATImage image;
	private Map<Integer, ArrayList<Pixel>> clusters = new HashMap<Integer, ArrayList<Pixel>>();
	private Map<Integer, Double> meansR = new HashMap<Integer, Double>();
	private Map<Integer, Double> meansG = new HashMap<Integer, Double>();
	private Map<Integer, Double> meansB = new HashMap<Integer, Double>();

	private List<Band> rgbBands = new ArrayList<Band>();

	private int height;
	private int width;
	
	public BinaryRGBClusterization(ATImage image) {
		this.image = image;
		rgbBands.add(0, image.R);
		rgbBands.add(1, image.G);
		rgbBands.add(2, image.B);
		height = image.getHeight();
		width = image.getWidth();
		clusterization();
	}
	
	private void clusterization() {
		for(int row = 0 ; row < height ; row++){
			for(int col = 0 ; col < width ; col++){
				int sum = 0;
				for(int i = 0; i < 3 ; i++){
					sum += rgbBands.get(i).getValue(row, col) * Math.pow(10, i);
				}
				if(!clusters.containsKey(sum)) {
					clusters.put(sum, new ArrayList<Pixel>());
				} 
				clusters.get(sum).add(new Pixel(row, col));
			}
		}
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		return value;
	}

	public void means() {
		for(Entry<Integer, ArrayList<Pixel>> e: clusters.entrySet()) {
			double sumR=0, sumG=0, sumB=0;
			for(Pixel pixel: e.getValue()) {
				sumR += image.R.getValue(pixel.getRow(), pixel.getCol());
				sumG += image.G.getValue(pixel.getRow(), pixel.getCol());
				sumB += image.B.getValue(pixel.getRow(), pixel.getCol());
			}
			meansR.put(e.getKey(), sumR/e.getValue().size());
			meansG.put(e.getKey(), sumG/e.getValue().size());
			meansB.put(e.getKey(), sumB/e.getValue().size());
		}
	}

}
