package edu.it.itba.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.it.itba.utils.ImageUtils;

public class ATIJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	private ATImageJPanel image;
	private ATImageJPanel modImage;

	public ATIJFrame() {
		super();

		// Create and set up the window.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);
		setLayout(new BorderLayout());

		// Create the main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setSize(400, 400);
		add(mainPanel);

		// Create and add menu bar
		setJMenuBar(new ATIMenu(this));

		// Display the window.
		setVisible(true);

	}

	public void createImagePanels(File file, Dimension dim) throws IOException {
		mainPanel.removeAll();
		image = new ATImageJPanel(file, dim);
		mainPanel.add(image, BorderLayout.WEST);
	}

	public void createGreyScaleImage() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.grayScale());
		mainPanel.add(image, BorderLayout.WEST);
		image.draw();
	}

	public void createColorScaleImage() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.colorScale());
		mainPanel.add(image, BorderLayout.WEST);
		image.draw();
	}

	public void createBlankSquare() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.blankSquare());
		mainPanel.add(image, BorderLayout.WEST);
		image.draw();
	}

	public void createBlankCircle() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.blankCircle());
		mainPanel.add(image, BorderLayout.WEST);
		image.draw();
	}

	public BufferedImage getImage() {
		return image.getImage();
	}

	public void createSubImage(BufferedImage subImage) {
		modImage = new ATImageJPanel(subImage);
		mainPanel.add(modImage, BorderLayout.EAST);
		modImage.draw();
	}
}
