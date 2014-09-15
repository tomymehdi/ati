package edu.it.itba.swing.interfaces;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import edu.it.itba.models.ATImage;

@SuppressWarnings("serial")
public abstract class ATIJFrame extends JFrame {

	public abstract ATIJPanel[] getPanels();

	public abstract void clear();

	public abstract void addImage(ATImage image);

	public abstract void loadImage(File file, Dimension dim);
}
