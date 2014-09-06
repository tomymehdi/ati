package edu.it.itba.swing.frames;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.it.itba.swing.interfaces.ATIJPanel;

@SuppressWarnings("serial")
public class ATIImageJFrame extends JFrame {

	private JPanel mainPanel;

	public ATIImageJFrame(ATIJPanel image) {

		super();
		// Create and set up the window.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 600);
		setLayout(new BorderLayout());
		setBackground(new Color(0,0,255));

		// Create the main panel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setSize(400, 400);
		mainPanel.add(image);
		add(mainPanel);

		// Display the window.
		setVisible(true);
	}

}