package edu.it.itba.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.it.itba.utils.ImageUtils;

public class ATImageJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	public ATImageJPanel(File file, Dimension dim) throws IOException {
		setBorder(BorderFactory.createLineBorder(Color.black));

		image = ImageUtils.load(file, dim);

		Dimension imageDimension = new Dimension(image.getWidth(),
				image.getHeight());
		setMaximumSize(imageDimension);
		setSize(imageDimension);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the image on the panel.
		g.drawImage(image, 0, 0, null);
	}

}
