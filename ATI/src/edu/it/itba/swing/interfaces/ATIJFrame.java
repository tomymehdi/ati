package edu.it.itba.swing.interfaces;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import edu.it.itba.enums.Compressions;
import edu.it.itba.functions.LinearTransform;
import edu.it.itba.functions.LogTransformation;
import edu.it.itba.models.ATImage;

@SuppressWarnings("serial")
public abstract class ATIJFrame extends JFrame {
	
	public Compressions compression = Compressions.LC;

	public abstract ATIJPanel[] getPanels();

	public abstract void clear();

	public abstract void addImage(ATImage image);

	public abstract void loadImage(File file, Dimension dim);
	
	public void applyTransform(ATImage img){
		if(Compressions.LC == compression){
			img.applyFunction(new LinearTransform(img), 100);
		} else if(Compressions.DC == compression){
			img.applyFunction(new LogTransformation(img), 100);
		}
	}
}
