package edu.it.itba.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Band;
import edu.it.itba.models.Pixel;

public class BinaryRGBClusterization implements Function {
	
	private ATImage image, noModificationImage;
	private Map<Integer, ArrayList<Pixel>> clusters = new HashMap<Integer, ArrayList<Pixel>>();
	private Map<Integer, Double> meansR = new HashMap<Integer, Double>();
	private Map<Integer, Double> meansG = new HashMap<Integer, Double>();
	private Map<Integer, Double> meansB = new HashMap<Integer, Double>();
	private Map<Integer, Double> withinVariances = new HashMap<Integer, Double>();
	private Map<Integer, Double> betweenVariances = new HashMap<Integer, Double>();
	
	private List<Band> rgbBands = new ArrayList<Band>();

	private int height;
	private int width;
	
	public BinaryRGBClusterization(ATImage image, ATImage noModificationImage) {
		this.image = image;
		this.noModificationImage = noModificationImage;
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
				sumR += noModificationImage.R.getValue(pixel.getRow(), pixel.getCol());
				sumG += noModificationImage.G.getValue(pixel.getRow(), pixel.getCol());
				sumB += noModificationImage.B.getValue(pixel.getRow(), pixel.getCol());
			}
			double valueR, valueG, valueB;
			valueR = sumR/e.getValue().size();
			valueG = sumG/e.getValue().size();
			valueB = sumB/e.getValue().size();
			System.out.println("key: " + e.getKey() + " meanR: " + valueR);
			System.out.println("key: " + e.getKey() + " meanG: " + valueG);
			System.out.println("key: " + e.getKey() + " meanB: " + valueB);
			meansR.put(e.getKey(), valueR);
			meansG.put(e.getKey(), valueG);
			meansB.put(e.getKey(), valueB);
		}
	}

	public void withinVariance() {
		for(Entry<Integer, ArrayList<Pixel>> e: clusters.entrySet()) {
			double sum=0;
			for(Pixel pixel: e.getValue()) {
				sum += (
					  Math.pow((noModificationImage.R.getValue(pixel.getRow(), pixel.getCol()) - meansR.get(e.getKey())), 2) +
					  Math.pow((noModificationImage.G.getValue(pixel.getRow(), pixel.getCol()) - meansG.get(e.getKey())), 2) +
					  Math.pow((noModificationImage.B.getValue(pixel.getRow(), pixel.getCol()) - meansB.get(e.getKey())), 2)
				);
			}
			double value = Math.sqrt(sum)/e.getValue().size();
			System.out.println("key: " + e.getKey() + " withinVariance: " + value);
			withinVariances.put(e.getKey(), value);
		}
		
	}
	
	public void betweenVariance() {
		for(Entry<Integer, ArrayList<Pixel>> e1: clusters.entrySet()) {
			for(Entry<Integer, ArrayList<Pixel>> e2: clusters.entrySet()) {
				System.out.println(e1.getKey() + " holis " + e2.getKey());
				if (e1.getKey().equals(e2.getKey()) || 
						betweenVariances.containsKey(e1.getKey() * 1000 + e2.getKey()) ||
						betweenVariances.containsKey(e2.getKey() * 1000 + e1.getKey())) {
					continue;
				} else {
					double value = Math.sqrt(
							Math.pow(meansR.get(e1.getKey()) - meansR.get(e2.getKey()), 2) +
							Math.pow(meansG.get(e1.getKey()) - meansG.get(e2.getKey()), 2) +
							Math.pow(meansB.get(e1.getKey()) - meansB.get(e2.getKey()), 2)
						);
					System.out.println("key: " + (e1.getKey() * 1000 + e2.getKey()) + " betweenVariance: " + value);
					betweenVariances.put(e1.getKey() * 1000 + e2.getKey(), value);
				}
			}
		}
	}

	public void reclustering() {
		boolean clustersCountChanged = true;
		int clustersSize = clusters.size();
		while(clustersCountChanged) {
			Map<Integer,HashSet<Integer>> resultJoin = new HashMap<Integer,HashSet<Integer>>();
			for(Entry<Integer, ArrayList<Pixel>> e1: clusters.entrySet()) {
				for(Entry<Integer, ArrayList<Pixel>> e2: clusters.entrySet()) {
					if (e1.getKey().equals(e2.getKey()) ||
							!betweenVariances.containsKey(e1.getKey() * 1000 + e2.getKey())) {
						continue;
					} else {
						double betweenVariance = betweenVariances.get(e1.getKey() * 1000 + e2.getKey());
						double withinVariance1 = withinVariances.get(e1.getKey());
						double withinVariance2 = withinVariances.get(e2.getKey());
						if(withinVariance1 >= betweenVariance || 
								withinVariance2 >= betweenVariance) {
							System.out.println("key1: " + e1.getKey() + " key2: " + e2.getKey());
							if(resultJoin.containsKey(e1.getKey())) {
								resultJoin.get(e1.getKey()).add(e2.getKey());
								continue;
							}
							if(resultJoin.containsKey(e2.getKey())) {
								resultJoin.get(e2.getKey()).add(e1.getKey());
								continue;
							}
							boolean flag = true;
							for(Entry<Integer, HashSet<Integer>> e3: resultJoin.entrySet()){
								if(e3.getValue().contains(e1.getKey())) {
									e3.getValue().add(e2.getKey());
									flag = false;
									break;
								}
								if(e3.getValue().contains(e2.getKey())) {
									e3.getValue().add(e1.getKey());
									flag = false;
									break;
								}
							}
							if(flag) {
								resultJoin.put(e1.getKey(), new HashSet<Integer>());
								resultJoin.get(e1.getKey()).add(e2.getKey());
							}
						}
					}
				}
			}
			
			for(Entry<Integer, HashSet<Integer>> e1: resultJoin.entrySet()) {
				System.out.println("current set: " + e1.getKey());
				for(Integer keyToAdd: e1.getValue()){
					System.out.println("keytoadd: " + keyToAdd);
					clusters.get(e1.getKey()).addAll(clusters.get(keyToAdd));
					clusters.remove(keyToAdd);
				}
			}
			
			if(clustersSize == clusters.size()){
				clustersCountChanged = false;
			}
			System.out.println("Before " + clustersSize);
			clustersSize = clusters.size();
			System.out.println("After " + clustersSize);
			
			// Step 3 - Mean per class per band
			meansR.clear();
			meansG.clear();
			meansB.clear();
			means();
			
			// Step 4.1 - Variance per class
			withinVariances.clear();
			withinVariance();
			
			// Step 4.2 - Variance between classes
			betweenVariances.clear();
			betweenVariance();
		}
		
		// Step 7
		for(Entry<Integer, ArrayList<Pixel>> e: clusters.entrySet()) {
			for(Pixel pixel: e.getValue()) {
				image.R.set(pixel.getRow(), pixel.getCol(), meansR.get(e.getKey()));
				image.G.set(pixel.getRow(), pixel.getCol(), meansG.get(e.getKey()));
				image.B.set(pixel.getRow(), pixel.getCol(), meansB.get(e.getKey()));
			}
		}
	}

}
