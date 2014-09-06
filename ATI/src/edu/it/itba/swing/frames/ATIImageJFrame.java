package edu.it.itba.swing.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.swing.interfaces.ATIJPanel;
import edu.it.itba.swing.menus.ATIMenu2;
import edu.it.itba.swing.panels.ATImageJPanel;

@SuppressWarnings("serial")
public class ATIImageJFrame extends ATIJFrame {

	private JPanel mainPanel;
	private ATIJPanel image;

	public ATIImageJFrame(ATIJPanel image) {

		super();
		this.image = image;
		// Create and set up the window.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 600);
		setLayout(new BorderLayout());
		setBackground(new Color(0,0,255));
		
		// Create and add menu bar
		setJMenuBar(new ATIMenu2(this));

		// Create the main panel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setSize(400, 400);
		mainPanel.add(image);
		add(mainPanel);

		// Display the window.
		setVisible(true);
	}

	@Override
	public ATIJPanel[] getPanels() {
		ATIJPanel resp[] = new ATIJPanel[1];
		resp[0] = image;
		return resp;
	}

	@Override
	public void clear() {
		mainPanel.removeAll();
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	@Override
	public void addImage(BufferedImage img) {
		mainPanel.removeAll();
		image = new ATImageJPanel(img);
		mainPanel.add(image);
		image.revalidate();
		image.repaint();
	}
	
	@Override
	public void loadImage(File file, Dimension dim) {
		// TODO Auto-generated method stub
	}

}