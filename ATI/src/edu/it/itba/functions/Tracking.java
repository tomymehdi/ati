package edu.it.itba.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Pixel;
import edu.it.itba.utils.ImageUtils;

public class Tracking implements Function {

	private Map<Pixel, Integer> in;
	private Map<Pixel, Integer> out;
	private int fis[][];
	private ATImage img;
	int row, col, width, height;
	double[] avgColor;
	int delta;

	public Tracking(ATImage img, int row, int col, int width, int height,
			HashMap<Pixel, Integer> in, HashMap<Pixel, Integer> out, int delta) {
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
		boolean change = false;
		while (change && iter < Math.max(img.getHeight(), img.getWidth())) {
			change = false;
			for (Entry<Pixel, Integer> e : out.entrySet()) {
				if (func(e.getKey()) > 0) {
					change = true;
					expand(e.getKey());
				}
			}

			for (Entry<Pixel, Integer> e : in.entrySet()) {
				if (func(e.getKey()) > 0) {
					change = true;
					contract(e.getKey());
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
		in.put(p, 1);
		fis[p.getRow()][p.getCol()] = -1;
		if (fis[p.getRow() - 1][p.getCol()] == 3) {
			out.put(new Pixel(p.getRow() - 1, p.getCol()), 1);
			fis[p.getRow() - 1][p.getCol()] = 1;
		} else if (fis[p.getRow()][p.getCol() + 1] == 3) {
			out.put(new Pixel(p.getRow(), p.getCol() + 1), 1);
			fis[p.getRow()][p.getCol() + 1] = 1;
		} else if (fis[p.getRow() + 1][p.getCol()] == 3) {
			out.put(new Pixel(p.getRow() + 1, p.getCol()), 1);
			fis[p.getRow() + 1][p.getCol()] = 1;
		} else if (fis[p.getRow()][p.getCol() - 1] == 3) {
			out.put(new Pixel(p.getRow(), p.getCol() - 1), 1);
			fis[p.getRow()][p.getCol() - 1] = 1;
		}
	}

	private void contract(Pixel p) {
		in.remove(p);
		out.put(p, 1);
		fis[p.getRow()][p.getCol()] = 1;
		if (fis[p.getRow() - 1][p.getCol()] == -3) {
			out.put(new Pixel(p.getRow() - 1, p.getCol()), 1);
			fis[p.getRow() - 1][p.getCol()] = -1;
		} else if (fis[p.getRow()][p.getCol() + 1] == -3) {
			out.put(new Pixel(p.getRow(), p.getCol() + 1), 1);
			fis[p.getRow()][p.getCol() + 1] = -1;
		} else if (fis[p.getRow() + 1][p.getCol()] == -3) {
			out.put(new Pixel(p.getRow() + 1, p.getCol()), 1);
			fis[p.getRow() + 1][p.getCol()] = -1;
		} else if (fis[p.getRow()][p.getCol() - 1] == -3) {
			out.put(new Pixel(p.getRow(), p.getCol() - 1), 1);
			fis[p.getRow()][p.getCol() - 1] = -1;
		}
	}

	private void initializeSetsAndFis() {
		fis = new int[img.getHeight()][img.getWidth()];

		for (int row = this.row; row < this.row + height; row++) {
			in.put(new Pixel(row, this.col), 1);
			in.put(new Pixel(row, this.col + this.width), 1);
			out.put(new Pixel(row, this.col - 1), 1);
			out.put(new Pixel(row, this.col + this.width + 1), 1);
		}

		for (int col = this.col; col < this.col + width; col++) {
			in.put(new Pixel(this.row, col), 1);
			in.put(new Pixel(this.row + height, col), 1);
			out.put(new Pixel(this.row - 1, col), 1);
			out.put(new Pixel(this.row + height + 1, col), 1);

		}

		for (int row = 0; row < img.getHeight(); row++) {
			for (int col = 0; col < img.getWidth(); col++) {

				Pixel p = new Pixel(row, col);
				if (in.containsKey(p))
					fis[row][col] = -1;

				else if (out.containsKey(p))
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
		// TODO Auto-generated method stub
		return 0;
	}

}
