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
	JMenuItem subImage;

	JMenuItem pixelValue;

	JMenuItem modifyPixelValue;

	JMenuItem blankCircle;
	JMenuItem blankSquare;
	JMenuItem greyScale;
	JMenuItem colorScale;

	JMenuItem sumImages;
	JMenuItem substractImages;
	JMenuItem negImage;

	public ATIMenu(ATIJFrame parent) {
		super();
		this.parent = parent;

		JMenu file = new JMenu("File");
		JMenu view = new JMenu("View");
		JMenu edit = new JMenu("Edit");
		JMenu newImage = new JMenu("New");
		JMenu operation = new JMenu("Operation");

		// File
		load = addMenuItemToMenu("Load...", file, true);
		save = addMenuItemToMenu("Save...", file, true);
		
		// View
		pixelValue = addMenuItemToMenu("Pixel Value...", view, true);
		
		// Edit
		modifyPixelValue = addMenuItemToMenu("Pixel Value...", edit, true);

		// New
		blankCircle = addMenuItemToMenu("Blank Circle", newImage, true);
		blankSquare = addMenuItemToMenu("Blank Square", newImage, true);
		greyScale = addMenuItemToMenu("Gray Scale", newImage, true);
		colorScale = addMenuItemToMenu("Color Scale", newImage, true);
		
		// Operation
		subImage = addMenuItemToMenu("Sub Image...", operation, true);
		sumImages = addMenuItemToMenu("Sum Images", operation, true);
		substractImages = addMenuItemToMenu("Substract Images", operation, true);
		negImage = addMenuItemToMenu("Negative Image", operation, true);

		addToMenu(file);
		addToMenu(view);
		addToMenu(edit);
		addToMenu(newImage);
		addToMenu(operation);
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
				handleEditPixelValue();
			else if (source == subImage)
				handleSubImage();
			else if (source == substractImages)
				handleSubstractImages();
			else if (source == negImage)
				handleNegImage();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Operation
	private void handleSubImage() {
		new ATISubImageJPanel(parent);
	}

	private void handleNegImage() {
		// TODO Auto-generated method stub
	}

	private void handleSubstractImages() {
		// TODO Auto-generated method stub
	}

	// Edit
	private void handleEditPixelValue() {
		new ATIPixelValueEditJPanel(parent);
	}
	
	// View
	private void handleShowPixelValue() {
		new ATIPixelValueJPanel(parent);
	}
	
	// New
	private void handleColorScale() {
		parent.createColorScaleImage();
	}

	private void handleGreyScale() {
		parent.createGreyScaleImage();
	}

	private void handleBlankSquare() {
		parent.createBlankSquare();
	}

	private void handleBlankCircle() {
		parent.createBlankCircle();
	}
	
	
	// File
	private void handleLoad() throws IOException {
		new ATILoadImagePanel(parent);
	}

	private void handleSave() {

		JFileChooser chooser = new JFileChooser();
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				new FileWriter(chooser.getSelectedFile());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
