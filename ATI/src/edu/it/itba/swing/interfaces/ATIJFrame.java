package edu.it.itba.swing.interfaces;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class ATIJFrame extends JFrame{
	
	public abstract ATIJPanel[] getPanels();

	public abstract void clear();
	
	public abstract void addImage(BufferedImage image);
	
	public abstract void loadImage(File file, Dimension dim);
}
