package edu.it.itba.swing.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;

import edu.it.itba.enums.ImageType;
import edu.it.itba.listeners.DragAndDrop;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.swing.interfaces.ATIJPanel;
import edu.it.itba.utils.ImageUtils;

public class ATImageJPanel extends ATIJPanel {

	private static final long serialVersionUID = 1L;

	private ATImage image;
	private DragAndDrop dragAndDrop;

	public ATImageJPanel(File file, Dimension dim) throws IOException {
		setBorder(BorderFactory.createLineBorder(Color.black));
		BufferedImage image;
		image = ImageUtils.load(file, dim);
		Dimension imageDimension = new Dimension(image.getWidth(),
				image.getHeight());
		this.image = new ATImage(file, image, ImageType.RGB);

		dragAndDrop = new DragAndDrop(this);

		addMouseListener(dragAndDrop);
		addMouseMotionListener(dragAndDrop);

		setMaximumSize(imageDimension);
		setSize(imageDimension);
	}

	public ATImageJPanel(File file, ATIJFrame parent, BufferedImage image) {
		super();
		this.image = new ATImage(file, image, ImageType.RGB);
		Dimension imgDim = new Dimension(image.getWidth(), image.getHeight());

		dragAndDrop = new DragAndDrop(this);

		addMouseListener(dragAndDrop);
		addMouseMotionListener(dragAndDrop);

		setMaximumSize(imgDim);
		setSize(imgDim);
	}

	public ATImageJPanel(ATImage image) {
		super();
		this.image = image;
		Dimension imgDim = new Dimension(image.getWidth(), image.getHeight());

		dragAndDrop = new DragAndDrop(this);

		addMouseListener(dragAndDrop);
		addMouseMotionListener(dragAndDrop);

		setMaximumSize(imgDim);
		setSize(imgDim);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the image on the panel.
		g.drawImage(image.getVisual(), 0, 0, null);
	}

	@Override
	public ATImage getImage() {
		return image;
	}

	public void setImage(ATImage img) {
		this.image = img;
	}

	public int[] getSquare() {
		return dragAndDrop.getSquare();
	}

}