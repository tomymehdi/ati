package edu.it.itba.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ATIMenu extends JMenuBar implements ActionListener {

	ATIJFrame parent;
	JMenuItem load;
	JMenuItem save;
	JMenuItem pixelValue;
	JMenuItem modifyPixelValue;
	JMenuItem copyImage;
	JMenuItem blankCircle;
	JMenuItem blankSquare;

	public ATIMenu(ATIJFrame parent) {
		super();
		this.parent = parent;

		JMenu file = new JMenu("File");
		JMenu view = new JMenu("View");
		JMenu edit = new JMenu("Edit");
		JMenu newImage = new JMenu("New");

		load = addMenuItemToMenu("Load...", file);
		save = addMenuItemToMenu("Save...", file);

		pixelValue = addMenuItemToMenu("Pixel Value...", view);

		modifyPixelValue = addMenuItemToMenu("Pixel Value...", edit);
		copyImage = addMenuItemToMenu("Copy Image...", edit);

		blankCircle = addMenuItemToMenu("Blank Circle", newImage);
		blankSquare = addMenuItemToMenu("Blank Square", newImage);

		addToMenu(file);
		addToMenu(view);
		addToMenu(edit);
		addToMenu(newImage);
	}

	public void addToMenu(JMenu menu) {
		add(menu);
	}

	public JMenuItem addMenuItemToMenu(String item, JMenu menu) {

		JMenuItem resp = new JMenuItem(item);
		resp.addActionListener(this);

		menu.add(resp);
		return resp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			Object source = e.getSource();

			if (source == save)
				handleSave();
			else if (source == load)
				handleLoad();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleLoad() throws IOException {
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (file.getName().endsWith(".raw"))
				loadRAW(file);
			else
				loadImage(file);

		}
	}

	private void loadImage(File file) throws IOException {
		BufferedImage image = ImageIO.read(file);

	}

	private void loadRAW(File file) {
		// TODO Auto-generated method stub

	}

	private void handleSave() {
		// TODO Auto-generated method stub

	}
}
