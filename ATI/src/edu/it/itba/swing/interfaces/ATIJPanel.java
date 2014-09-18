package edu.it.itba.swing.interfaces;

import javax.swing.JPanel;

import edu.it.itba.models.ATImage;

@SuppressWarnings("serial")
public abstract class ATIJPanel extends JPanel {

	public abstract ATImage getImage();

}
