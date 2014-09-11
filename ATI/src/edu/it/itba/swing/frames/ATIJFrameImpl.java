package edu.it.itba.swing.frames;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.swing.interfaces.ATIJPanel;
import edu.it.itba.swing.menus.ATIMenu;
import edu.it.itba.swing.panels.ATImageJPanel;
import edu.it.itba.utils.ImageUtils;

public class ATIJFrameImpl extends ATIJFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	private ATImageJPanel imageLeft;
	private ATImageJPanel imageRight;

	public ATIJFrameImpl() {
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

	@Override
	public ATIJPanel[] getPanels() {
		ATIJPanel resp[] = new ATIJPanel[2];
		resp[0] = imageLeft;
		resp[1] = imageRight;
		return resp;
	}

	@Override
	public void addImage(BufferedImage img) {
		if (imageLeft == null) {
			imageLeft = new ATImageJPanel(img);
			mainPanel.add(imageLeft);
			imageLeft.revalidate();
			imageLeft.repaint();
		} else if (imageRight == null) {
			imageRight = new ATImageJPanel(img);
			mainPanel.add(imageRight);
			imageRight.revalidate();
			imageRight.repaint();
		} else {
			mainPanel.removeAll();
			imageLeft = null;
			imageRight = null;
			addImage(img);
		}
	}

	public void loadImage(File file, Dimension dim) {
		try {
			addImage(ImageUtils.load(file, dim));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return imageLeft.getImage();
	}

	public ATImageJPanel getLeftImagePanel() {
		return imageLeft;
	}

	public ATImageJPanel getRightImagePanel() {
		return imageRight;
	}

	public void clear() {
		mainPanel.removeAll();
		mainPanel.revalidate();
		mainPanel.repaint();
		imageLeft = null;
		imageRight = null;
	}

}
