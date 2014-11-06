package edu.it.itba.functions;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Pixel;
import edu.it.itba.utils.ImageUtils;

public class Tracking implements Function {

	public List<Pixel> in;
	public List<Pixel> out;
	public int fis[][];
	public ATImage img;
	int row, col, width, height;
	double[] avgColor;
	int delta;

	public Tracking(ATImage img, int row, int col, int width, int height,
			List<Pixel> in, List<Pixel> out, int delta) {
		this.img = img;
		this.in = in;
		this.out = out;
		this.row = row;
		this.height = height;
		this.col = col;
		this.width = width;
		avgColor = ImageUtils.avgEachBand(img.getVisual().getSubimage(col, row,
				width, height));
		this.delta = delta;
		runAlgorithm();

	}

	private void runAlgorithm() {
		if (in.isEmpty() || out.isEmpty()) {
			initializeSetsAndFis();
		}
		int iter = 0;
		boolean change = true;
		while (change && iter < Math.max(img.getHeight(), img.getWidth())) {
			change = false;
			for(int i = out.size()-1 ; i >= 0 ; i--){
				if (func(out.get(i)) > 0) {
					change = true;
					expand(out.get(i));
				}
			}
			
			for(int i = in.size()-1 ; i >= 0 ; i--){
				if (func(in.get(i)) > 0) {
					change = true;
					contract(in.get(i));
				}
			}

			iter++;
		}

		// Paso 3 ventana gaussiana

	}

	private int func(Pixel key) {
		if (avgColor[0] - img.R.getValue(key.getRow(), key.getCol()) < delta)
			return 1;
		else
			return -1;
	}

	private void expand(Pixel p) {
		out.remove(p);
		in.add(p);
		fis[p.getRow()][p.getCol()] = -1;
		if (fis[p.getRow() - 1][p.getCol()] == 3) {
			out.add(new Pixel(p.getRow() - 1, p.getCol()));
			fis[p.getRow() - 1][p.getCol()] = 1;
		} else if (fis[p.getRow()][p.getCol() + 1] == 3) {
			out.add(new Pixel(p.getRow(), p.getCol() + 1));
			fis[p.getRow()][p.getCol() + 1] = 1;
		} else if (fis[p.getRow() + 1][p.getCol()] == 3) {
			out.add(new Pixel(p.getRow() + 1, p.getCol()));
			fis[p.getRow() + 1][p.getCol()] = 1;
		} else if (fis[p.getRow()][p.getCol() - 1] == 3) {
			out.add(new Pixel(p.getRow(), p.getCol() - 1));
			fis[p.getRow()][p.getCol() - 1] = 1;
		}
	}

	private void contract(Pixel p) {
		in.remove(p);
		out.add(p);
		fis[p.getRow()][p.getCol()] = 1;
		if (fis[p.getRow() - 1][p.getCol()] == -3) {
			out.add(new Pixel(p.getRow() - 1, p.getCol()));
			fis[p.getRow() - 1][p.getCol()] = -1;
		} else if (fis[p.getRow()][p.getCol() + 1] == -3) {
			out.add(new Pixel(p.getRow(), p.getCol() + 1));
			fis[p.getRow()][p.getCol() + 1] = -1;
		} else if (fis[p.getRow() + 1][p.getCol()] == -3) {
			out.add(new Pixel(p.getRow() + 1, p.getCol()));
			fis[p.getRow() + 1][p.getCol()] = -1;
		} else if (fis[p.getRow()][p.getCol() - 1] == -3) {
			out.add(new Pixel(p.getRow(), p.getCol() - 1));
			fis[p.getRow()][p.getCol() - 1] = -1;
		}
	}

	private void initializeSetsAndFis() {
		fis = new int[img.getHeight()][img.getWidth()];

		for (int row = this.row; row < this.row + height; row++) {
			Pixel p1,p2,p3,p4;
			
			p1 = new Pixel(row, this.col);
			p2 = new Pixel(row, this.col + this.width);
			p3 = new Pixel(row, this.col - 1);
			p4 = new Pixel(row, this.col + this.width + 1);
			
			if(!in.contains(p1))
				in.add(p1);
			if(!in.contains(p2))
				in.add(p2);
			if(!out.contains(p3))
				out.add(p3);
			if(!out.contains(p4))
			    out.add(p4);
		}

		for (int col = this.col; col < this.col + width; col++) {
			
			Pixel p1,p2,p3,p4;
			p1 = new Pixel(this.row, col);
			p2 = new Pixel(this.row + height, col);
			p3 = new Pixel(this.row - 1, col);
			p4 = new Pixel(this.row + height + 1, col);
			
			if(!in.contains(p1))
				in.add(p1);
			if(!in.contains(p2))
				in.add(p2);
			if(!out.contains(p3))
				out.add(p3);
			if(!out.contains(p4))
			    out.add(p4);

		}

		for (int row = 0; row < img.getHeight(); row++) {
			for (int col = 0; col < img.getWidth(); col++) {

				Pixel p = new Pixel(row, col);
				if (in.contains(p))
					fis[row][col] = -1;

				else if (out.contains(p))
					fis[row][col] = 1;

				else if (row > this.row && row < this.row + this.height
						&& col > this.col && col < this.col + this.width)
					fis[row][col] = -3;
				else
					fis[row][col] = 3;

			}
		}

	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		return value;
	}

}
