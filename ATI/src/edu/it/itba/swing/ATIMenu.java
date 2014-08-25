package edu.it.itba.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
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
	JMenuItem subImage;

	JMenuItem blankCircle;
	JMenuItem blankSquare;
	JMenuItem greyScale;
	JMenuItem colorScale;

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

		subImage = addMenuItemToMenu("Sub Image...", newImage, false);

		blankCircle = addMenuItemToMenu("Blank Circle", newImage, true);
		blankSquare = addMenuItemToMenu("Blank Square", newImage, true);
		greyScale = addMenuItemToMenu("Gray Scale", newImage, true);
		colorScale = addMenuItemToMenu("Color Scale", newImage, true);

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
			else if (source == save)
				handleSave();
			else if (source == pixelValue)
				handleShowPixelValue();
			else if (source == modifyPixelValue)
				handleModifyPixelValue();
			else if (source == subImage)
				handleSubImage();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleColorScale() {
		parent.createColorScaleImage();
		activateMenuOptions();
	}

	private void handleGreyScale() {
		parent.createGreyScaleImage();
		activateMenuOptions();
	}

	private void handleBlankSquare() {
		parent.createBlankSquare();
		activateMenuOptions();
	}

	private void handleBlankCircle() {
		parent.createBlankCircle();
		activateMenuOptions();
	}

	private void handleLoad() throws IOException {
		new ATILoadImagePanel(parent);
		activateMenuOptions();
	}

	private void handleSubImage() {
		new ATISubImageJPanel(parent);
	}

	private void handleModifyPixelValue() {
		// TODO Auto-generated method stub

	}

	private void handleShowPixelValue() {

		new ATIPixelValueJPanel(parent);

	}

	private void handleSave() {

		JFileChooser chooser = new JFileChooser();
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter fw = new FileWriter(chooser.getSelectedFile());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void activateMenuOptions() {
		save.setEnabled(true);
		pixelValue.setEnabled(true);
		modifyPixelValue.setEnabled(true);
		subImage.setEnabled(true);

	}
}
