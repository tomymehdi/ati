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

import edu.it.itba.utils.ImageUtils;

@SuppressWarnings("serial")
public class ATIMenu extends JMenuBar implements ActionListener {

	ATIJFrame parent;
	
	JMenuItem load;
	JMenuItem save;
	JMenuItem subImage;

	JMenuItem pixelValue;
	JMenuItem histogramLeft;
	JMenuItem histogramRight;

	JMenuItem modifyPixelValue;

	JMenuItem blankCircle;
	JMenuItem blankSquare;
	JMenuItem greyScale;
	JMenuItem colorScale;

	JMenuItem sumImages;
	JMenuItem substractImages;
	JMenuItem negImage;
	
	JMenuItem clear;

	public ATIMenu(ATIJFrame parent) {
		super();
		this.parent = parent;

		JMenu file = new JMenu("File");
		JMenu view = new JMenu("View");
		JMenu edit = new JMenu("Edit");
		JMenu newImage = new JMenu("New");
		JMenu operation = new JMenu("Operation");
		JMenu options = new JMenu("Options");

		// File
		load = addMenuItemToMenu("Load...", file, true);
		save = addMenuItemToMenu("Save...", file, true);
		
		// View
		pixelValue = addMenuItemToMenu("Pixel Value...", view, true);
		histogramLeft = addMenuItemToMenu("Histrogram left image", view, true);
		histogramRight = addMenuItemToMenu("Histogram right image", view, true);
		
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
		
		// Options
		clear = addMenuItemToMenu("Clear", options, true);

		addToMenu(file);
		addToMenu(view);
		addToMenu(edit);
		addToMenu(newImage);
		addToMenu(operation);
		addToMenu(options);
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
			else if (source == sumImages)
				handleSumImages();
			else if (source == substractImages)
				handleSubstractImages();
			else if (source == negImage)
				handleNegImage();
			else if (source == clear)
				handleClear();
			else if (source == histogramLeft)
				handleHistogramLeft();
			else if (source == histogramRight)
				handleHistogramRight();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Options
	private void handleClear() {
		parent.clear();
	}

	// Operation
	private void handleSubImage() {
		new ATISubImageJPanel(parent);
	}

	private void handleNegImage() {
		BufferedImage img = ImageUtils.negative(parent.getLeftImagePanel().getImage());
		parent.addImage(img);
		
	}

	private void handleSumImages() {
		BufferedImage img = ImageUtils.optImages(parent.getLeftImagePanel().getImage(), parent.getRightImagePanel().getImage(), 0);
		parent.addImage(img);
	}

	private void handleSubstractImages() {
		BufferedImage img = ImageUtils.optImages(parent.getLeftImagePanel().getImage(), parent.getRightImagePanel().getImage(), 2);
		parent.addImage(img);
	}
	
	// Edit
	private void handleEditPixelValue() {
		new ATIPixelValueEditJPanel(parent);
	}
	
	// View
	private void handleShowPixelValue() {
		new ATIPixelValueJPanel(parent);
	}
	
	private void handleHistogramLeft() {
		new ATIImageJFrame(new ATImageJPanel(ImageUtils.histogram(parent.getLeftImagePanel().getImage())));
	}
	
	private void handleHistogramRight() {
		new ATIImageJFrame(new ATImageJPanel(ImageUtils.histogram(parent.getRightImagePanel().getImage())));
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
				File outputfile = chooser.getSelectedFile().getAbsoluteFile();
			    ImageIO.write(parent.getLeftImagePanel().getImage(), "jpg", outputfile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
