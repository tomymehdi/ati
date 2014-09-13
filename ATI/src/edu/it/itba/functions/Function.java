package edu.it.itba.functions;

import edu.it.itba.models.ATImage;

public abstract class Function {

	private ATImage image;

	public Function(ATImage image) {

		this.image = image.clone();
	}

	public abstract void apply();

	public ATImage getImage() {
		return image;
	}
}
