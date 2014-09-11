package edu.it.itba.swing.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.it.itba.enums.Side;
import edu.it.itba.swing.dialogs.ATIExpDialog;
import edu.it.itba.swing.dialogs.ATIGaussNoiseDialog;
import edu.it.itba.swing.dialogs.ATIImpulsiveNoiseDialog;
import edu.it.itba.swing.dialogs.ATILoadImageDialog;
import edu.it.itba.swing.dialogs.ATIPixelValueDialog;
import edu.it.itba.swing.dialogs.ATIPixelValueEditDialog;
import edu.it.itba.swing.dialogs.ATIRaylightDialog;
import edu.it.itba.swing.dialogs.ATISubImageDialog;
import edu.it.itba.swing.frames.ATIImageJFrame;
import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.swing.panels.ATISaltAndPepperDialog;
import edu.it.itba.swing.panels.ATIUmbralDialog;
import edu.it.itba.utils.ImageUtils;

@SuppressWarnings("serial")
public class ATIMenu extends JMenuBar implements ActionListener {

	private ATIJFrame parent;

	private JMenuItem load;
	private JMenuItem save;

	private JMenuItem blankCircle;
	private JMenuItem blankSquare;
	private JMenuItem greyScale;
	private JMenuItem colorScale;

	private JMenuItem pixelValueLeft;
	private JMenuItem pixelValueRight;
	private JMenuItem histogramLeft;
	private JMenuItem histogramRight;

	private JMenuItem modifyPixelValueLeft;
	private JMenuItem modifyPixelValueRight;

	private JMenuItem subImage;
	private JMenuItem sumImages;
	private JMenuItem substractImages;
	private JMenuItem negImage;

	private JMenuItem impulsiveSee;
	private JMenuItem impulsiveAppLeft;
	private JMenuItem impulsiveAppRight;
	
	private JMenuItem gaussSee;
	private JMenuItem gaussAppLeft;
	private JMenuItem gaussAppRight;
	
	private JMenuItem raylightSee;
	private JMenuItem raylightAppLeft;
	private JMenuItem raylightAppRight;
	
	private JMenuItem expSee;
	private JMenuItem expAppLeft;
	private JMenuItem expAppRight;
	
	private JMenuItem umbralAppLeft;
	private JMenuItem umbralAppRight;

	private JMenuItem clear;

	public ATIMenu(ATIJFrame parent) {
		super();
		this.parent = parent;

		JMenu file = new JMenu("File");
		JMenu newImage = new JMenu("New");
		JMenu view = new JMenu("View");
		JMenu edit = new JMenu("Edit");
		JMenu operation = new JMenu("Operation");

		JMenu noises = new JMenu("Noises");
		JMenu impulsive = new JMenu("Impulsive");
		noises.add(impulsive);
		JMenu gauss = new JMenu("Gauss");
		noises.add(gauss);
		JMenu raylight = new JMenu("Raylight");
		noises.add(raylight);
		JMenu exp = new JMenu("Exponential");
		noises.add(exp);
		
		JMenu umbrals = new JMenu("Umbrals");

		JMenu options = new JMenu("Options");

		// File
		load = addMenuItemToMenu("Load...", file, true);
		save = addMenuItemToMenu("Save...", file, true);

		// New
		blankCircle = addMenuItemToMenu("Blank Circle", newImage, true);
		blankSquare = addMenuItemToMenu("Blank Square", newImage, true);
		greyScale = addMenuItemToMenu("Gray Scale", newImage, true);
		colorScale = addMenuItemToMenu("Color Scale", newImage, true);

		// View
		pixelValueLeft = addMenuItemToMenu("Pixel left...", view, true);
		pixelValueRight = addMenuItemToMenu("Pixel right...", view, true);
		histogramLeft = addMenuItemToMenu("Histrogram left image", view, true);
		histogramRight = addMenuItemToMenu("Histogram right image", view, true);

		// Edit
		modifyPixelValueLeft = addMenuItemToMenu("Pixel left...", edit, true);
		modifyPixelValueRight = addMenuItemToMenu("Pixel right...", edit, true);

		// Operation
		subImage = addMenuItemToMenu("Sub Image...", operation, true);
		sumImages = addMenuItemToMenu("Sum Images", operation, true);
		substractImages = addMenuItemToMenu("Substract Images", operation, true);
		negImage = addMenuItemToMenu("Negative Image", operation, true);

		// Noises
		impulsiveSee = addMenuItemToMenu("See", impulsive, true);
		impulsiveAppLeft = addMenuItemToMenu("Apply left...", impulsive, true);
		impulsiveAppRight = addMenuItemToMenu("Apply right...", impulsive, true);
		
		gaussSee = addMenuItemToMenu("See", gauss, true);
		gaussAppLeft = addMenuItemToMenu("Apply left", gauss, true);
		gaussAppRight = addMenuItemToMenu("Apply right", gauss, true);
		
		raylightSee = addMenuItemToMenu("See", raylight, true);
		raylightAppLeft = addMenuItemToMenu("Apply left", raylight, true);
		raylightAppRight = addMenuItemToMenu("Apply right", raylight, true);
		
		expSee = addMenuItemToMenu("See", exp, true);
		expAppLeft = addMenuItemToMenu("Apply left", exp, true);
		expAppRight = addMenuItemToMenu("Apply right", exp, true);
		
		// Umbrals
		umbralAppLeft = addMenuItemToMenu("Apply umbral left...", umbrals, true);
		umbralAppRight = addMenuItemToMenu("Apply umbral right...", umbrals, true);

		// Options
		clear = addMenuItemToMenu("Clear", options, true);

		addToMenu(file);
		addToMenu(newImage);
		addToMenu(view);
		addToMenu(edit);
		addToMenu(operation);
		addToMenu(noises);
		addToMenu(umbrals);
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
				handleGrayScale();
			else if (source == colorScale)
				handleColorScale();
			else if (source == save)
				handleSave();
			else if (source == pixelValueLeft)
				handleShowPixelValueLeft();
			else if (source == pixelValueRight)
				handleShowPixelValueRight();
			else if (source == modifyPixelValueLeft)
				handleEditPixelValueLeft();
			else if (source == modifyPixelValueRight)
				handleEditPixelValueRight();
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
			else if (source == impulsiveSee)
				handleImpulsiveSee();
			else if (source == impulsiveAppLeft)
				handleImpulsiveAppLeft();
			else if (source == impulsiveAppRight)
				handleImpulsiveAppRight();
			else if (source == umbralAppLeft)
				handleUmbralAppLeft();
			else if (source == umbralAppRight)
				handleUmbralAppRight();
			else if (source == gaussSee)
				handleGaussSee();
			else if (source == gaussAppLeft)
				handleGaussAppLeft();
			else if (source == gaussAppRight)
				handleGaussAppRight();
			else if (source == raylightSee)
				handleRaylightSee();
			else if (source == raylightAppLeft)
				handleRaylightAppLeft();
			else if (source == raylightAppRight)
				handleRaylightAppRight();
			else if (source == expSee)
				handleExpSee();
			else if (source == expAppLeft)
				handleExpAppLeft();
			else if (source == expAppRight)
				handleExpAppRight();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Umbrals
	private void handleUmbralAppLeft() {

		new ATIUmbralDialog(parent, Side.LEFT);
		
	}

	private void handleUmbralAppRight() {

		new ATIUmbralDialog(parent, Side.RIGHT);
	
	}

	// Noises
	private void handleImpulsiveAppRight() {
		new ATISaltAndPepperDialog(parent, Side.RIGHT);
	
	}

	private void handleImpulsiveAppLeft() {
		new ATIImpulsiveNoiseDialog(parent, parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleImpulsiveSee() {
		new ATIImpulsiveNoiseDialog(parent, new BufferedImage(100,100,BufferedImage.TYPE_BYTE_GRAY));
	}
	
	private void handleGaussAppRight() {
		new ATIGaussNoiseDialog(parent, parent.getPanels()[Side.RIGHT.getValue()].getImage());
	}

	private void handleGaussAppLeft() {
		new ATIGaussNoiseDialog(parent, parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleGaussSee() {
		new ATIGaussNoiseDialog(parent, new BufferedImage(100,100,BufferedImage.TYPE_BYTE_GRAY));
	}
	
	private void handleRaylightAppRight() {
		new ATIRaylightDialog(parent, parent.getPanels()[Side.RIGHT.getValue()].getImage());
	}

	private void handleRaylightAppLeft() {
		new ATIRaylightDialog(parent, parent.getPanels()[Side.LEFT.getValue()].getImage());		
	}

	private void handleRaylightSee() {
		new ATIRaylightDialog(parent, new BufferedImage(100,100,BufferedImage.TYPE_BYTE_GRAY));
	}
	
	private void handleExpAppRight() {
		new ATIExpDialog(parent, parent.getPanels()[Side.RIGHT.getValue()].getImage());
	}

	private void handleExpAppLeft() {
		new ATIExpDialog(parent, parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleExpSee() {
		new ATIExpDialog(parent, new BufferedImage(100,100,BufferedImage.TYPE_BYTE_GRAY));
	}

	// Options
	private void handleClear() {
		parent.clear();
	}

	// Operation
	private void handleSubImage() {
		new ATISubImageDialog(parent);
	}

	private void handleNegImage() {
		BufferedImage img = ImageUtils.negative(parent.getPanels()[0]
				.getImage());
		parent.addImage(img);
	}

	private void handleSumImages() {
		BufferedImage img = ImageUtils.optImages(
				parent.getPanels()[0].getImage(),
				parent.getPanels()[1].getImage(), 0);
		parent.addImage(img);
	}

	private void handleSubstractImages() {
		BufferedImage img = ImageUtils.optImages(
				parent.getPanels()[0].getImage(),
				parent.getPanels()[1].getImage(), 2);
		parent.addImage(img);
	}

	// Edit
	private void handleEditPixelValueLeft() {
		new ATIPixelValueEditDialog(parent, Side.LEFT);
	}

	private void handleEditPixelValueRight() {
		new ATIPixelValueEditDialog(parent, Side.RIGHT);
	}

	// View
	private void handleShowPixelValueLeft() {
		new ATIPixelValueDialog(parent);
	}

	private void handleShowPixelValueRight() {
		new ATIPixelValueDialog(parent);
	}

	private void handleHistogramLeft() {
		new ATIImageJFrame(ImageUtils.histogram(parent.getPanels()[0]
				.getImage()));
	}

	private void handleHistogramRight() {
		new ATIImageJFrame(ImageUtils.histogram(parent.getPanels()[1]
				.getImage()));
	}

	// New
	private void handleColorScale() {
		BufferedImage img = ImageUtils.colorScale();
		parent.addImage(img);
	}

	private void handleGrayScale() {
		BufferedImage img = ImageUtils.grayScale();
		parent.addImage(img);
	}

	private void handleBlankSquare() {
		BufferedImage img = ImageUtils.blankSquare();
		parent.addImage(img);
	}

	private void handleBlankCircle() {
		BufferedImage img = ImageUtils.blankCircle();
		parent.addImage(img);
	}

	// File
	private void handleLoad() throws IOException {
		new ATILoadImageDialog(parent);
	}

	private void handleSave() {
		try {
			String dir = System.getProperty("user.dir");
			dir += "/tests/results/";
			File left = new File(dir + "L.jpg");
			File right = new File(dir + "R.jpg");
			ImageIO.write(parent.getPanels()[0].getImage(), "jpg", left);
			ImageIO.write(parent.getPanels()[1].getImage(), "jpg", right);
		} catch (Exception ex) {

		}
	}

}
