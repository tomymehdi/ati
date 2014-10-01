package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;

public class Diffusion implements Function {

	ATImage img;
	MaterialHeatDistribution mhd;
	int t;

	public Diffusion(ATImage img, MaterialHeatDistribution mhd, int t) {

		this.img = new ATImage(img);
		this.mhd = mhd;
		this.t = t;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		double oldValueIJ = value;

		double DnIij = row > 0 ? img.getBand(band).getValue(row - 1, col)
				- oldValueIJ : 0;
		double DsIij = row < img.getHeight() - 1 ? img.getBand(band).getValue(
				row + 1, col)
				- oldValueIJ : 0;
		double DeIij = col < img.getWidth() - 1 ? img.getBand(band).getValue(
				row, col + 1)
				- oldValueIJ : 0;
		double DoIij = col > 0 ? img.getBand(band).getValue(row, col - 1)
				- oldValueIJ : 0;

		double Cnij = mhd.g(DnIij);
		double Csij = mhd.g(DsIij);
		double Ceij = mhd.g(DeIij);
		double Coij = mhd.g(DoIij);

		double DnIijCnij = DnIij * Cnij;
		double DsIijCsij = DsIij * Csij;
		double DeIijCeij = DeIij * Ceij;
		double DoIijCoij = DoIij * Coij;

		double lambda = 0.25;
		double newValueIJ = oldValueIJ + lambda
				* (DnIijCnij + DsIijCsij + DeIijCeij + DoIijCoij);
		return newValueIJ /** GaussianKernel(row, col, t) */
		;
	}

	private double GaussianKernel(int row, int col, double t) {

		return (1 / (4 * Math.PI * t))
				* Math.pow(Math.E, (Math.pow(row, 2) + Math.pow(col, 2))
						/ (4 * t));
	}

}
