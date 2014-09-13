package edu.it.itba.functions;

import edu.it.itba.models.ATImage;

public abstract class OptFunction extends Function {

	private ATImage ImageToOpt;

	public OptFunction(ATImage image, ATImage imageToOpt) {
		super(image);
		this.ImageToOpt = imageToOpt;

	}

	public void apply() {

		ATImage image = getImage();
		double originalPixel;
		double pixelToOpt;
		for (int col = 0; col < image.getWidth(); col++) {
			for (int row = 0; row < image.getHeight(); row++) {
				for (int band = 0; band < 3; band++) {
					originalPixel = image.getPixel(row, col, band);
					pixelToOpt = ImageToOpt.getPixel(row, col, band);
					image.setPixel(row, col, band,
							optPixel(originalPixel, pixelToOpt));
				}
			}
		}

	}

	public abstract double optPixel(double value1, double value2);
}
