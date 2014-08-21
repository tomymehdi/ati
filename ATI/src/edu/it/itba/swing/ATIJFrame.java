package edu.it.itba.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
		new ATIImageJFrame(new ATImageJPanel(file, dim));
	}

	public void createGreyScaleImage() {
		mainPanel.removeAll();
		ATImageJPanel jp = new ATImageJPanel(ImageUtils.grayScale());
		mainPanel.add(jp, BorderLayout.CENTER);
		jp.draw();
	}

	public void createColorScaleImage() {
		mainPanel.removeAll();
		ATImageJPanel jp = new ATImageJPanel(ImageUtils.colorScale());
		mainPanel.add(jp, BorderLayout.CENTER);
		jp.draw();
	}

	public void createBlankSquare() {
		mainPanel.removeAll();
		ATImageJPanel jp = new ATImageJPanel(ImageUtils.blankSquare());
		mainPanel.add(jp, BorderLayout.CENTER);
		jp.draw();
	}

	public void createBlankCircle() {
		mainPanel.removeAll();
		ATImageJPanel jp = new ATImageJPanel(ImageUtils.blankCircle());
		mainPanel.add(jp, BorderLayout.CENTER);
		jp.draw();
	}

}
