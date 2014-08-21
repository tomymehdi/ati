package edu.it.itba.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ATIMenu extends JMenuBar implements ActionListener {

	ATIJFrame parent;
	JMenuItem load;

	JMenuItem blankCircle;
	JMenuItem blankSquare;
	JMenuItem greyScale;
	JMenuItem colorScale;

	public ATIMenu(ATIJFrame parent) {
		super();
		this.parent = parent;

		JMenu file = new JMenu("File");
		JMenu newImage = new JMenu("New");

		load = addMenuItemToMenu("Load...", file, true);

		blankCircle = addMenuItemToMenu("Blank Circle", newImage, true);
		blankSquare = addMenuItemToMenu("Blank Square", newImage, true);
		greyScale = addMenuItemToMenu("Gray Scale", newImage, true);
		colorScale = addMenuItemToMenu("Color Scale", newImage, true);

		addToMenu(file);

		addToMenu(newImage);
	}

	public void addToMenu(JMenu menu) {
		add(menu);
	}

	public JMenuItem addMenuItemToMenu(String item, JMenu menu, boolean enable) {

		JMenuItem resp = new JMenuItem(item);
		resp.addActionListener(this);
		resp.setEnabled(enable);

		menu.add(resp);
		return resp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			Object source = e.getSource();

			if (source == load)
				handleLoad();
			else if (source == blankCircle)
				handleBlankCircle();
			else if (source == blankSquare)
				handleBlankSquare();
			else if (source == greyScale)
				handleGreyScale();
			else if (source == colorScale)
				handleColorScale();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleColorScale() {
		parent.createColorScaleImage();
	}

	private void handleGreyScale() {

		parent.createGreyScaleImage();
	}

	private void handleBlankSquare() {
		// TODO Auto-generated method stub

	}

	private void handleBlankCircle() {
		// TODO Auto-generated method stub

	}

	private void handleLoad() throws IOException {
		new ATILoadImagePanel(parent);

	}

}
