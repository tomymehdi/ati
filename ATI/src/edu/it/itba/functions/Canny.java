package edu.it.itba.functions;

import edu.it.itba.models.ATImage;
import edu.it.itba.models.windows.GaussianWIndow;

public class Canny {

	ATImage img;
	ATImage ret;

	public Canny(ATImage image, int windowSize, double sigma, int t1, int t2) {
		this.img = new ATImage(image);
		img.applyFunction(new PassAdditiveWindow(img, new GaussianWIndow(
				windowSize, sigma)), 100);

		MaxSuppression maxs = new MaxSuppression(image);

		ATImage suppressed = maxs.supressMaxs();

		suppressed.applyFunction(
				new HisteresisUmbralization(suppressed, t1, t2), 100);

		ret = new ATImage(suppressed);

		// TODO Auto-generated constructor stub
	}

	public ATImage applyCanny() {
		return ret;
	}
}
