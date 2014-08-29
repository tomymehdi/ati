package edu.it.itba.swing;

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
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);

		// Create the main panel
		mainPanel = new JPanel();
		mainPanel.setSize(400, 400);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		add(mainPanel);

		// Create and add menu bar
		setJMenuBar(new ATIMenu(this));

		// Display the window.
		setVisible(true);

	}

	public void createGreyScaleImage() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.grayScale());
		mainPanel.add(image);
		image.revalidate();
		image.repaint();
	}

	public void createColorScaleImage() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.colorScale());
		mainPanel.add(image);
		image.revalidate();
		image.repaint();
	}

	public void createBlankSquare() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.blankSquare());
		mainPanel.add(image);
		image.revalidate();
		image.repaint();
	}

	public void createBlankCircle() {
		mainPanel.removeAll();
		image = new ATImageJPanel(ImageUtils.blankCircle());
		mainPanel.add(image);
		image.revalidate();
		image.repaint();
	}

	public void loadImage(File file, Dimension dim) {
		try {
			mainPanel.removeAll();
			image = new ATImageJPanel(ImageUtils.load(file, dim));
			mainPanel.add(image);
			image.revalidate();
			image.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return image.getImage();
	}

	public void createSubImage(BufferedImage subImage) {
		modImage = new ATImageJPanel(subImage);
		mainPanel.add(modImage);
		image.revalidate();
		image.repaint();
	}

	public ATImageJPanel getImagePanel() {
		return image;
	}

}
