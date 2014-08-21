package edu.it.itba.swing;

import java.awt.BorderLayout;
import java.awt.Component;
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

	public ATIJFrame() {
		super();

		// Create and set up the window.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);
		setLayout(new BorderLayout());

		// Create the main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setSize(400, 400);
		add(mainPanel);

		// Create and add menu bar
		setJMenuBar(new ATIMenu(this));

		// Display the window.
		setVisible(true);

	}

	public void createImagePanels(File file, Dimension dim) throws IOException {
		mainPanel.removeAll();
		mainPanel.add(new ATImageJPanel(file, dim), BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void createGreyScaleImage() {
		mainPanel.removeAll();
		mainPanel.add(new ATImageJPanel(ImageUtils.grayScale()),
				BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();

	}

	public void createColorScaleImage() {
		mainPanel.removeAll();
		mainPanel.add(new ATImageJPanel(ImageUtils.colorScale()),
				BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void createBlankSquare() {
		mainPanel.removeAll();
		mainPanel.add(new ATImageJPanel(ImageUtils.blankSquare()),
				BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void createBlankCircle() {
		mainPanel.removeAll();
		mainPanel.add(new ATImageJPanel(ImageUtils.blankCircle()),
				BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

}
