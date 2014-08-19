package edu.it.itba.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
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

		load = addMenuItemToMenu("Load...", file, true);
		save = addMenuItemToMenu("Save...", file, false);

		pixelValue = addMenuItemToMenu("Pixel Value...", view, false);

		modifyPixelValue = addMenuItemToMenu("Pixel Value...", edit, false);
		copyImage = addMenuItemToMenu("Copy Image...", edit, false);

		blankCircle = addMenuItemToMenu("Blank Circle", newImage, true);
		blankSquare = addMenuItemToMenu("Blank Square", newImage, true);

		addToMenu(file);
		addToMenu(view);
		addToMenu(edit);
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

			if (source == save)
				handleSave();
			else if (source == load)
				handleLoad();
			else if (source == pixelValue)
				handleShowPixelValue();
			else if (source == modifyPixelValue)
				handleModifyPixelValue();
			else if (source == copyImage)
				handleCopyImage();
			else if (source == blankCircle)
				handleBlankCircle();
			else if (source == blankSquare)
				handleBlankSquare();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleBlankSquare() {
		// TODO Auto-generated method stub

	}

	private void handleBlankCircle() {
		// TODO Auto-generated method stub

	}

	private void handleCopyImage() {
		// TODO Auto-generated method stub

	}

	private void handleModifyPixelValue() {
		// TODO Auto-generated method stub

	}

	private void handleShowPixelValue() {
		// TODO Auto-generated method stub

	}

	private void handleLoad() throws IOException {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// parent.createImagePanels(file, new Dimension(width, height));
			// BufferedImage imageRaw = ImageUtils.load(file, width, height);
			changeButons();

		}
	}

	private void changeButons() {
		save.setEnabled(!save.isEnabled());
		pixelValue.setEnabled(!pixelValue.isEnabled());
		modifyPixelValue.setEnabled(!modifyPixelValue.isEnabled());
		copyImage.setEnabled(copyImage.isEnabled());

	}

	private void handleSave() {
		// TODO Auto-generated method stub

	}
}
