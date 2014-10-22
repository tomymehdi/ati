package edu.it.itba.functions;

import edu.it.itba.enums.Bands;
import edu.it.itba.interfaces.Function;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.windows.SusanaWindow;
import edu.it.itba.models.windows.Window;

public class PassSusanWindow implements Function {
	Window win = new SusanaWindow(7);
	ATImage image;
	int delta;
	int opt;
	double error;

	public PassSusanWindow(ATImage image, int delta, int opt, double error) {
		this.image = new ATImage(image);
		this.delta = delta;
		this.opt = opt;
		this.error = error;
	}

	@Override
	public double apply(double value, int row, int col, Bands band) {
		int counter = 0;
		int colStart = col - (win.size / 2);
		int colEnd = col + (win.size / 2);
		int rowStart = row - (win.size / 2);
		int rowEnd = row + (win.size / 2);
		int k = 0;
		double currPixel;

		for (int i = rowStart; i <= rowEnd; i++) {
			for (int j = colStart; j <= colEnd; j++, k++) {
				if (win.window[k] == 1) {
					currPixel = image.getBand(band).getValue(i, j);
					if (Math.abs(currPixel - value) <= delta)
						counter++;
				}
			}
		}
		double porcentaje = counter / 37.0;
		switch (opt) {
		case 1:
			if (porcentaje > 0.5 - error && porcentaje < 0.5 + error)
				return 255;
			return 0;
		case 2:
			if (porcentaje > 0.75 - error && porcentaje < 0.75 + error)
				return 255;
			return 0;
		}
		return 0;
	}

}
