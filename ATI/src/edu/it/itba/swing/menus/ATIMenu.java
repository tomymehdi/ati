package edu.it.itba.swing.menus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.it.itba.enums.Direction;
import edu.it.itba.enums.ImageType;
import edu.it.itba.enums.Side;
import edu.it.itba.functions.BinaryRGBClusterization;
import edu.it.itba.functions.Crossing;
import edu.it.itba.functions.Equalize;
import edu.it.itba.functions.LinearTransform;
import edu.it.itba.functions.LogTransformation;
import edu.it.itba.functions.Max;
import edu.it.itba.functions.MultiplyBy;
import edu.it.itba.functions.Negative;
import edu.it.itba.functions.OtzuUmbralization;
import edu.it.itba.functions.OtzuUmbralizationRGB;
import edu.it.itba.functions.PassAdditiveWindow;
import edu.it.itba.functions.SumImage;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Corner;
import edu.it.itba.models.Pixel;
import edu.it.itba.models.windows.GaussianWIndow;
import edu.it.itba.models.windows.Kirsh;
import edu.it.itba.models.windows.Laplacian;
import edu.it.itba.models.windows.Prewitt;
import edu.it.itba.models.windows.Sobel;
import edu.it.itba.models.windows.UnNamedWindow;
import edu.it.itba.swing.dialogs.ATIAnisotropicDiffusionDialog;
import edu.it.itba.swing.dialogs.ATIBorderSusanDialog;
import edu.it.itba.swing.dialogs.ATIBorderWindowDialog;
import edu.it.itba.swing.dialogs.ATICannyDialog;
import edu.it.itba.swing.dialogs.ATIContrastDialog;
import edu.it.itba.swing.dialogs.ATICornerSusanDialog;
import edu.it.itba.swing.dialogs.ATIExpDialog;
import edu.it.itba.swing.dialogs.ATIExpImageDialog;
import edu.it.itba.swing.dialogs.ATIGaussNoiseDialog;
import edu.it.itba.swing.dialogs.ATIGaussNoiseImageDialog;
import edu.it.itba.swing.dialogs.ATIGaussWindowDialog;
import edu.it.itba.swing.dialogs.ATIGetSquareDialog;
import edu.it.itba.swing.dialogs.ATIHoughCirclesDialog;
import edu.it.itba.swing.dialogs.ATIHoughLinesDialog;
import edu.it.itba.swing.dialogs.ATILaplacianPendantDialog;
import edu.it.itba.swing.dialogs.ATILoadImageDialog;
import edu.it.itba.swing.dialogs.ATIMeanWindowDialog;
import edu.it.itba.swing.dialogs.ATIMediumWindowDialog;
import edu.it.itba.swing.dialogs.ATIMultiplyScalarDialog;
import edu.it.itba.swing.dialogs.ATIPixelValueDialog;
import edu.it.itba.swing.dialogs.ATIPixelValueEditDialog;
import edu.it.itba.swing.dialogs.ATIRayleighImageDialog;
import edu.it.itba.swing.dialogs.ATIRaylightDialog;
import edu.it.itba.swing.dialogs.ATISubImageDialog;
import edu.it.itba.swing.dialogs.ATITrackingStaticDialog;
import edu.it.itba.swing.dialogs.ATIsotropicDiffusionDialog;
import edu.it.itba.swing.dialogs.GlobalUmbralDialog;
import edu.it.itba.swing.dialogs.LaplacianGaussianDialog;
import edu.it.itba.swing.dialogs.SaltAndPepperNoise;
import edu.it.itba.swing.frames.ATIImageJFrame;
import edu.it.itba.swing.frames.ATIOcrJFrame;
import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.swing.panels.ATISaltAndPepperDialog;
import edu.it.itba.swing.panels.ATIUmbralDialog;
import edu.it.itba.utils.ImageUtils;
import mpi.cbg.fly.Feature;
import mpi.cbg.fly.Model;
import mpi.cbg.fly.PointMatch;
import mpi.cbg.fly.SIFT;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@SuppressWarnings("serial")
public class ATIMenu extends JMenuBar implements ActionListener {

	private ATIJFrame parent;

	private JMenuItem load;
	private JMenuItem save;

	private JMenuItem blankCircle;
	private JMenuItem blankSquare;
	private JMenuItem greyScale;
	private JMenuItem colorScale;
	private JMenuItem lena;
	private JMenuItem lenax;
	private JMenuItem girl;
	private JMenuItem barco;
	private JMenuItem fractal;

	private JMenuItem pixelValueLeft;
	private JMenuItem pixelValueRight;
	private JMenuItem histogramLeft;
	private JMenuItem histogramRight;

	private JMenuItem modifyPixelValueLeft;
	private JMenuItem modifyPixelValueRight;

	private JMenuItem multplyByScalar;
	private JMenuItem subImage;
	private JMenuItem sumImages;
	private JMenuItem substractImages;
	private JMenuItem negImage;
	private JMenuItem applyContrastLeft;
	private JMenuItem applyContrastRight;
	private JMenuItem equalize;
	private JMenuItem isotropicDiffusion;
	private JMenuItem anisotropicDiffusion;
	private JMenuItem sift;

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
	private JMenuItem globralUmbral;
	private JMenuItem otzuUmbral;

	private JMenuItem gaussWindow;
	private JMenuItem meanWindow;
	private JMenuItem mediumWindow;
	private JMenuItem borderWindow;

	private JMenuItem unNamedMax;
	private JMenuItem prewittMax;
	private JMenuItem sobelMax;
	private JMenuItem kirshMax;
	private JMenuItem prewittV;
	private JMenuItem prewittH;
	private JMenuItem prewittD;
	private JMenuItem sobelV;
	private JMenuItem sobelH;
	private JMenuItem sobelD;
	private JMenuItem canny;
	private JMenuItem susanBorder;
	private JMenuItem harris;

	private JMenuItem susanCorner;

	private JMenuItem houghLines;
	private JMenuItem houghCircles;

	private JMenuItem kirshV;
	private JMenuItem kirshH;
	private JMenuItem kirshD;
	private JMenuItem laplacian;
	private JMenuItem laplacianPendant;
	private JMenuItem laplacianGaussian;

	private JMenuItem trackingStatic;
	private JMenuItem trackingVideo;

	private JMenuItem linearCompLeft;
	private JMenuItem linearCompRight;
	private JMenuItem logCompLeft;
	private JMenuItem logCompRight;

	private JMenuItem clear;
	private JMenuItem changePositions;
	
	private JMenuItem OCRimage;
	private JMenuItem OCRvideo;

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

		JMenu slideWindow = new JMenu("Fillter");

		JMenu borderDetection = new JMenu("Border detection");

		JMenu cornerDetection = new JMenu("Corner detecion");

		JMenu lineDetection = new JMenu("Line detection");

		JMenu tracking = new JMenu("Tracking");

		JMenu compression = new JMenu("Compressions");
		
		JMenu ocr = new JMenu("OCR");

		JMenu options = new JMenu("Options");

		// File
		load = addMenuItemToMenu("Load...", file, true);
		save = addMenuItemToMenu("Save...", file, true);

		// New
		blankCircle = addMenuItemToMenu("Blank Circle", newImage, true);
		blankSquare = addMenuItemToMenu("Blank Square", newImage, true);
		greyScale = addMenuItemToMenu("Gray Scale", newImage, true);
		colorScale = addMenuItemToMenu("Color Scale", newImage, true);
		lena = addMenuItemToMenu("Lena", newImage, true);
		lenax = addMenuItemToMenu("LenaX", newImage, true);
		barco = addMenuItemToMenu("Barco", newImage, true);
		girl = addMenuItemToMenu("Girl", newImage, true);
		fractal = addMenuItemToMenu("Fractal", newImage, true);

		// View
		pixelValueLeft = addMenuItemToMenu("Pixel left...", view, true);
		pixelValueRight = addMenuItemToMenu("Pixel right...", view, true);
		histogramLeft = addMenuItemToMenu("Histrogram left image", view, true);
		histogramRight = addMenuItemToMenu("Histogram right image", view, true);

		// Edit
		modifyPixelValueLeft = addMenuItemToMenu("Pixel left...", edit, true);
		modifyPixelValueRight = addMenuItemToMenu("Pixel right...", edit, true);

		// Operation
		multplyByScalar = addMenuItemToMenu("Multiply by scalar", operation,
				true);
		subImage = addMenuItemToMenu("Sub Image...", operation, true);
		sumImages = addMenuItemToMenu("Sum Images", operation, true);
		substractImages = addMenuItemToMenu("Substract Images", operation, true);
		negImage = addMenuItemToMenu("Negative Image", operation, true);
		applyContrastLeft = addMenuItemToMenu("Apply Contrast left image ...",
				operation, true);
		applyContrastRight = addMenuItemToMenu(
				"Apply Contrast right image ...", operation, true);
		equalize = addMenuItemToMenu("Equalize", operation, true);
		isotropicDiffusion = addMenuItemToMenu("Isotropic Diffusion...",
				operation, true);
		anisotropicDiffusion = addMenuItemToMenu("Anisotropic Diffusion...",
				operation, true);
		sift = addMenuItemToMenu("sift", operation, true);

		// Noises
		impulsiveSee = addMenuItemToMenu("See", impulsive, true);
		impulsiveAppLeft = addMenuItemToMenu("Apply left", impulsive, true);
		impulsiveAppRight = addMenuItemToMenu("Apply right", impulsive, true);

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
		umbralAppRight = addMenuItemToMenu("Apply umbral right...", umbrals,
				true);
		globralUmbral = addMenuItemToMenu("Global", umbrals, true);
		otzuUmbral = addMenuItemToMenu("Otzu", umbrals, true);

		// Slide window
		gaussWindow = addMenuItemToMenu("Slide gauss window...", slideWindow,
				true);
		meanWindow = addMenuItemToMenu("Slide mean window...", slideWindow,
				true);
		mediumWindow = addMenuItemToMenu("Slide medium window...", slideWindow,
				true);
		borderWindow = addMenuItemToMenu("Slide border window ... ",
				slideWindow, true);

		// Border detection
		prewittV = addMenuItemToMenu("Prewitt vertical", borderDetection, true);
		prewittH = addMenuItemToMenu("Prewitt horizontal", borderDetection,
				true);
		prewittD = addMenuItemToMenu("Prewitt diagonal", borderDetection, true);

		sobelV = addMenuItemToMenu("Sobel vertical", borderDetection, true);
		sobelH = addMenuItemToMenu("Sobel horizontal", borderDetection, true);
		sobelD = addMenuItemToMenu("Sobel diagonal", borderDetection, true);

		kirshV = addMenuItemToMenu("Kirsh vertical", borderDetection, true);
		kirshH = addMenuItemToMenu("Kirsh horizontal", borderDetection, true);
		kirshD = addMenuItemToMenu("Kirsh diagonal", borderDetection, true);

		laplacian = addMenuItemToMenu("Laplacian", borderDetection, true);
		laplacianPendant = addMenuItemToMenu("Laplacian pendent",
				borderDetection, true);
		laplacianGaussian = addMenuItemToMenu("Laplacian Gaussian",
				borderDetection, true);

		prewittMax = addMenuItemToMenu("Max Prewitt", borderDetection, true);
		kirshMax = addMenuItemToMenu("Max Kirsh", borderDetection, true);
		sobelMax = addMenuItemToMenu("Max Sobel", borderDetection, true);
		unNamedMax = addMenuItemToMenu("Max UnNamed", borderDetection, true);
		canny = addMenuItemToMenu("Canny", borderDetection, true);
		susanBorder = addMenuItemToMenu("SUSAN", borderDetection, true);
		harris = addMenuItemToMenu("Harris", borderDetection, true);

		// Corner detection
		susanCorner = addMenuItemToMenu("SUSAN", cornerDetection, true);

		// Line detection
		houghLines = addMenuItemToMenu("Hough lines", lineDetection, true);
		houghCircles = addMenuItemToMenu("Hough circles", lineDetection, true);

		// Tracking
		trackingStatic = addMenuItemToMenu("Tracking imagen", tracking, true);
		trackingVideo = addMenuItemToMenu("Tracking video", tracking, true);

		// Compressions
		linearCompLeft = addMenuItemToMenu("LC left", compression, true);
		linearCompRight = addMenuItemToMenu("LC right", compression, true);
		logCompLeft = addMenuItemToMenu("DC left", compression, true);
		logCompRight = addMenuItemToMenu("DC right", compression, true);

		// OCR
		OCRimage = addMenuItemToMenu("OCR image", ocr, true);
		OCRvideo = addMenuItemToMenu("OCR video", ocr, true);
		
		// Options
		clear = addMenuItemToMenu("Clear", options, true);
		changePositions = addMenuItemToMenu("Change image positions", options,
				true);

		addToMenu(file);
		addToMenu(newImage);
		addToMenu(view);
		addToMenu(edit);
		addToMenu(operation);
		addToMenu(noises);
		addToMenu(umbrals);
		addToMenu(slideWindow);
		addToMenu(borderDetection);
		addToMenu(cornerDetection);
		addToMenu(lineDetection);
		addToMenu(tracking);
		addToMenu(compression);
		addToMenu(ocr);
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
			else if (source == lena)
				handleLena();
			else if (source == lenax)
				handleLenax();
			else if (source == girl)
				handleGirl();
			else if (source == barco)
				handleBarco();
			else if (source == fractal)
				handleFractal();
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
			else if (source == gaussWindow)
				handleGaussWindow();
			else if (source == meanWindow)
				handleMeanWindow();
			else if (source == mediumWindow)
				handleMediumWindow();
			else if (source == borderWindow)
				handleBorderWindow();
			else if (source == applyContrastLeft)
				handleApplyContrast(Side.LEFT);
			else if (source == applyContrastRight)
				handleApplyContrast(Side.RIGHT);
			else if (source == linearCompLeft)
				handleLCL();
			else if (source == linearCompRight)
				handleLCR();
			else if (source == logCompLeft)
				handleDCL();
			else if (source == logCompRight)
				handleDCR();
			else if (source == multplyByScalar)
				handleMultiplyByScalar();
			else if (source == equalize)
				handleEqualize();
			else if (source == changePositions)
				handleChangePositions();
			else if (source == prewittV)
				handlePrewitt(Direction.VERTICAL);
			else if (source == prewittH)
				handlePrewitt(Direction.HORIZONTAL);
			else if (source == prewittD)
				handlePrewitt(Direction.DIAGONAL);
			else if (source == sobelV)
				handleSobel(Direction.VERTICAL);
			else if (source == sobelH)
				handleSobel(Direction.HORIZONTAL);
			else if (source == sobelD)
				handleSobel(Direction.DIAGONAL);
			else if (source == globralUmbral)
				handleGlobalUmbral();
			else if (source == otzuUmbral)
				handleOtzuUmbral();
			else if (source == isotropicDiffusion)
				handleIsotropicDiffusion();
			else if (source == anisotropicDiffusion)
				handleAnisotropicDiffusion();
			else if (source == kirshV)
				handleKirsh(Direction.VERTICAL);
			else if (source == kirshH)
				handleKirsh(Direction.HORIZONTAL);
			else if (source == kirshD)
				handleKirsh(Direction.DIAGONAL);
			else if (source == laplacian)
				handleLaplacian();
			else if (source == laplacianGaussian)
				handleLaplacianGaussian();
			else if (source == sobelMax)
				handleSobelMax();
			else if (source == kirshMax)
				handleKirshMax();
			else if (source == prewittMax)
				handlePrewittMax();
			else if (source == unNamedMax)
				handleUnNamedMax();
			else if (source == laplacianPendant)
				handleLaplacianPendant();
			else if (source == canny)
				handleCanny();
			else if (source == susanBorder)
				handleSusanBorder();
			else if (source == susanCorner)
				handleSusanCorner();
			else if (source == houghLines)
				handleHoughLines();
			else if (source == houghCircles)
				handleHoughCircles();
			else if (source == trackingStatic)
				handleTrackingStatic();
			else if (source == trackingVideo)
				handleTrackingVideo();
			else if (source == sift)
				handleSIFT();
			else if (source == harris)
				handleHarris();
			else if(source == OCRimage)
				handleOCRimage();
			else if(source == OCRvideo)
				handleOCRvideo();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void handleOCRimage() {
		
		// OCR without applying algorithm
		File imageFile = parent.getPanels()[Side.LEFT.getValue()].getImage().getFile();
//		Tesseract instance = Tesseract.getInstance();  // JNA Interface Mapping
		Tesseract instance = new Tesseract();  // JNA Direct Mapping
		instance.setLanguage("eng");
		try {
		    String result = instance.doOCR(imageFile);
		    System.out.println(result);
		} catch (TesseractException e) {
		    System.err.println(e.getMessage());
		}
		
		// Step 1 - Otzu umbralization RGB
		ATImage image = parent.getPanels()[Side.LEFT.getValue()].getImage();
		ATImage colorVideoAlgorithmImage = new ATImage(image);

		OtzuUmbralizationRGB otzuRGB = new OtzuUmbralizationRGB(colorVideoAlgorithmImage);
		
		// Step 2.1 - Binary treshholding
		colorVideoAlgorithmImage.applyFunction(otzuRGB, 100);
		
		// Step 2.2 - Clustering pixels into 8 classes
		BinaryRGBClusterization classes = new BinaryRGBClusterization(colorVideoAlgorithmImage, image);
		
		
		// Step 3 - Mean per class per band
		classes.means();
		
		// Step 4.1 - Variance per class
		classes.withinVariance();
		
		// Step 4.2 - Variance between classes
		classes.betweenVariance();
		
		// Step 5, 6 and 7
		classes.reclustering();
		
		
		parent.addImage(colorVideoAlgorithmImage);
		
		String dir = System.getProperty("user.dir");
		dir += "/tests/results/";
		File leftFile = new File(dir + "ocr.jpg");
		try {
			ImageIO.write(colorVideoAlgorithmImage.getVisual(), "jpg",
					leftFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
		    String result = instance.doOCR(leftFile);
		    System.out.println(result);
		} catch (TesseractException e) {
		    System.err.println(e.getMessage());
		}
		
	}
	
	// Video https://www.youtube.com/watch?v=L0MK7qz13bU
	private void handleOCRvideo() {
		ATIOcrJFrame ocrFrame;
		try {
			ocrFrame = new ATIOcrJFrame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleHarris(){
		ATImage harried = new ATImage(parent.getPanels()[Side.LEFT.getValue()].getImage());
		
		double threshold = 0.001;
		double k = 0;
		
		int w = harried.getWidth(), h = harried.getHeight();
		
		double[][] lx2 = new double[w][h];
		double[][] ly2 = new double[w][h];
		double[][] lxy = new double[w][h];
		List<Corner> corners = new ArrayList<Corner>();
		
		computeDerivatives(harried, lx2, ly2, lxy);
		
		double[][] harrismap = computeHarrisResponse(k, lx2, ly2, lxy);
		for (int x = 1; x < w - 1; x++) {
			for (int y = 1; y < h - 1; y++) {
				double v = harrismap[x][y];
				if (v >= threshold
						&& isSpatialMaxima(harrismap, (int) x, (int) y))
					corners.add(new Corner(x, y, v));
			}
		}
		for (Corner p : new ArrayList<Corner>(corners)) {
			for (Corner n : corners) {
				if (n != p) {
					int dist = (int) Math.sqrt((p.x - n.x) * (p.x - n.x)
							+ (p.y - n.y) * (p.y - n.y));
					if (dist <= 3 && n.measure < p.measure) {
						corners.remove(p);
						break;
					}
				}
			}
		}
		ATImage resp = new ATImage(harried.getHeight(), harried.getWidth(),
				 ImageType.GRAYSCALE);
		int col,row;
		for (Corner c : corners) {
			 col = c.y;
			 row = c.x;
			 resp.drawCircle(row, col, (int) c.measure/20);
			
		}
		parent.clear();
		parent.addImage(harried.applyLayer(resp));
		
		
	}
	
	private static boolean isSpatialMaxima(double[][] hmap, int x, int y) {
		int n = 8;
		int[] dx = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };
		int[] dy = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };
		double w = hmap[x][y];
		for (int i = 0; i < n; i++) {
			double wk = hmap[x + dx[i]][y + dy[i]];
			if (wk >= w)
				return false;
		}
		return true;
	}

	private static double[][] computeHarrisResponse(double k, double[][] lx2,
			double[][] ly2, double[][] lxy) {
		int width = ly2.length, height = ly2[0].length;
		double[][] harrisResponse = new double[width][height];
		double max = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				harrisResponse[x][y] = harrisResponse(x, y, k, lx2, ly2, lxy);
				if (harrisResponse[x][y] > max)
					max = harrisResponse[x][y];
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double r = harrisResponse[x][y];
				if (r < 0) {
					r = 0;
				} else {
					r = 100 * Math.log(1 + r) / Math.log(1 + max);
				}
				harrisResponse[x][y] = r;
			}
		}
		return harrisResponse;
	}

	private static double harrisResponse(int x, int y, double k,
			double[][] lx2, double[][] ly2, double[][] lxy) {
		double aux = lx2[x][y] * ly2[x][y] - lxy[x][y] * lxy[x][y] - k
				* (lx2[x][y] + ly2[x][y]) * (lx2[x][y] + ly2[x][y]);
		return aux;
	}
	
	private static void computeDerivatives(ATImage image, double[][] lx2, double[][] ly2, double[][] lxy) {
		int w = image.getWidth(), h = image.getHeight();
		
		ATImage imgHor = new ATImage(image);
		imgHor.applyFunction(new PassAdditiveWindow(imgHor, new Sobel(3,
				Direction.HORIZONTAL)), 100);
		ATImage imgVer = new ATImage(image);
		imgVer.applyFunction(new PassAdditiveWindow(imgVer, new Sobel(3,
				Direction.VERTICAL)), 100);
		
		GaussianWIndow gaussian = new GaussianWIndow(3, 1.4);

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				for (int dx = 0; dx < 3 ; dx++) {
					for (int dy = 0; dy < 3; dy++) {
						int xk = x + dx;
						int yk = y + dy;
						if (xk >= 0 && xk < w && yk >= 0 && yk < h) {
							double f = gaussian.window[dx + dy *3];
							double gxp = imgHor.R.getValue(xk, yk);
							double gyp = imgVer.R.getValue(xk, yk);
							lx2[x][y] += f * gxp * gxp;
							ly2[x][y] += f * gyp * gyp;
							lxy[x][y] += f * gxp * gyp;
						}
					}
				}
			}
		}
	}

	private void handleTrackingVideo() throws IOException {
		File file = new File("/Users/tomymehdi/itba/ati/ATI/resources/videos/movie1/PIC00001.jpg");

		new ATIGetSquareDialog(this.parent, new ATImage(file, ImageUtils.load(file,
				null), ImageType.RGB));

	}

	// Tracking
	private void handleTrackingStatic() {
		new ATITrackingStaticDialog(parent);
	}

	// Lines detection
	private void handleHoughLines() {
		handleCanny();
		ATImage img = new ATImage(
				parent.getPanels()[Side.RIGHT.getValue()].getImage());

		new ATIHoughLinesDialog(parent, img);
	}

	// Circles detection
	private void handleHoughCircles() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());

		new ATIHoughCirclesDialog(parent, img);
	}

	private void handleLaplacianPendant() {

		new ATILaplacianPendantDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());

	}

	// Corner detection
	private void handleSusanCorner() {

		new ATICornerSusanDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());

	}

	// Border detection
	private void handleSusanBorder() {

		new ATIBorderSusanDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleUnNamedMax() {
		ATImage imgHor = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgHor.applyFunction(new PassAdditiveWindow(imgHor, new UnNamedWindow(
				3, Direction.HORIZONTAL)), 100);
		ATImage imgVer = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgVer.applyFunction(new PassAdditiveWindow(imgVer, new UnNamedWindow(
				3, Direction.VERTICAL)), 100);
		ATImage imgDia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgDia.applyFunction(new PassAdditiveWindow(imgDia, new UnNamedWindow(
				3, Direction.DIAGONAL)), 100);
		ATImage imgAdia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgAdia.applyFunction(new PassAdditiveWindow(imgAdia,
				new UnNamedWindow(3, Direction.ADIAGONAL)), 100);

		ATImage resp = new ATImage(imgHor);
		resp.applyFunction(new Max(imgVer), 100);
		resp.applyFunction(new Max(imgDia), 100);
		resp.applyFunction(new Max(imgAdia), 100);

		parent.addImage(resp);
	}

	private void handlePrewittMax() {
		ATImage imgHor = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgHor.applyFunction(new PassAdditiveWindow(imgHor, new Prewitt(3,
				Direction.HORIZONTAL)), 100);
		ATImage imgVer = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgVer.applyFunction(new PassAdditiveWindow(imgVer, new Prewitt(3,
				Direction.VERTICAL)), 100);
		ATImage imgDia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgDia.applyFunction(new PassAdditiveWindow(imgDia, new Prewitt(3,
				Direction.DIAGONAL)), 100);
		ATImage imgAdia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgAdia.applyFunction(new PassAdditiveWindow(imgAdia, new Prewitt(3,
				Direction.ADIAGONAL)), 100);

		ATImage resp = new ATImage(imgHor);
		resp.applyFunction(new Max(imgVer), 100);
		resp.applyFunction(new Max(imgDia), 100);
		resp.applyFunction(new Max(imgAdia), 100);

		parent.addImage(resp);

	}

	private void handleKirshMax() {
		ATImage imgHor = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgHor.applyFunction(new PassAdditiveWindow(imgHor, new Kirsh(3,
				Direction.HORIZONTAL)), 100);
		ATImage imgVer = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgVer.applyFunction(new PassAdditiveWindow(imgVer, new Kirsh(3,
				Direction.VERTICAL)), 100);
		ATImage imgDia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgDia.applyFunction(new PassAdditiveWindow(imgDia, new Kirsh(3,
				Direction.DIAGONAL)), 100);
		ATImage imgAdia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgAdia.applyFunction(new PassAdditiveWindow(imgAdia, new Kirsh(3,
				Direction.ADIAGONAL)), 100);

		ATImage resp = new ATImage(imgHor);
		resp.applyFunction(new Max(imgVer), 100);
		resp.applyFunction(new Max(imgDia), 100);
		resp.applyFunction(new Max(imgAdia), 100);

		parent.addImage(resp);

	}

	private void handleSobelMax() {
		ATImage imgHor = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgHor.applyFunction(new PassAdditiveWindow(imgHor, new Sobel(3,
				Direction.HORIZONTAL)), 100);
		ATImage imgVer = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgVer.applyFunction(new PassAdditiveWindow(imgVer, new Sobel(3,
				Direction.VERTICAL)), 100);
		ATImage imgDia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgDia.applyFunction(new PassAdditiveWindow(imgDia, new Sobel(3,
				Direction.DIAGONAL)), 100);
		ATImage imgAdia = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		imgAdia.applyFunction(new PassAdditiveWindow(imgAdia, new Sobel(3,
				Direction.ADIAGONAL)), 100);

		ATImage resp = new ATImage(imgHor);
		resp.applyFunction(new Max(imgVer), 100);
		resp.applyFunction(new Max(imgDia), 100);
		resp.applyFunction(new Max(imgAdia), 100);

		parent.addImage(resp);

	}

	private void handleIsotropicDiffusion() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		new ATIsotropicDiffusionDialog(parent, img);
	}

	private void handleAnisotropicDiffusion() {

		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());

		new ATIAnisotropicDiffusionDialog(parent, img);
	}

	private void handleCanny() {

		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		new ATICannyDialog(parent, img);

	}

	private void handleLaplacianGaussian() {
		new LaplacianGaussianDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleLaplacian() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new PassAdditiveWindow(img, new Laplacian(3)), 100);
		parent.clear();
		parent.addImage(img);
		ATImage img2 = new ATImage(img);
		img2.applyFunction(new Crossing(img2, 0), 100);
		parent.addImage(img2);
	}

	private void handleKirsh(Direction dir) {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new PassAdditiveWindow(img, new Kirsh(3, dir)), 100);
		parent.addImage(img);
	}

	private void handlePrewitt(Direction dir) {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new PassAdditiveWindow(img, new Prewitt(3, dir)), 100);
		parent.addImage(img);
	}

	private void handleSobel(Direction dir) {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new PassAdditiveWindow(img, new Sobel(3, dir)), 100);
		parent.addImage(img);
	}

	// Compressions
	private void handleDCR() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.RIGHT.getValue()].getImage());
		img.applyFunction(new LogTransformation(img), 100);
		parent.addImage(img);
	}

	private void handleDCL() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new LogTransformation(img), 100);
		parent.addImage(img);
	}

	private void handleLCR() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.RIGHT.getValue()].getImage());
		img.applyFunction(new LinearTransform(img), 100);
		parent.addImage(img);
	}

	private void handleLCL() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new LinearTransform(img), 100);
		parent.addImage(img);
	}

	// Windows
	private void handleMediumWindow() {
		new ATIMediumWindowDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleMeanWindow() {
		new ATIMeanWindowDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleGaussWindow() {
		new ATIGaussWindowDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());

	}

	private void handleBorderWindow() {
		new ATIBorderWindowDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	// Umbrals
	private void handleUmbralAppLeft() {
		new ATIUmbralDialog(parent, Side.LEFT);

	}

	private void handleUmbralAppRight() {
		new ATIUmbralDialog(parent, Side.RIGHT);
	}

	private void handleGlobalUmbral() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		new GlobalUmbralDialog(parent, img);
	}

	private void handleOtzuUmbral() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new OtzuUmbralization(img), 100);
		parent.addImage(img);
	}

	// Noises
	private void handleImpulsiveAppRight() {
		new ATISaltAndPepperDialog(parent, Side.RIGHT);
	}

	private void handleImpulsiveAppLeft() {
		new ATISaltAndPepperDialog(parent, Side.LEFT);
	}

	private void handleImpulsiveSee() {

		ATImage img = new ATImage(100, 100, ImageType.GRAYSCALE);

		img.applyFunction(new SaltAndPepperNoise(), 100);
		parent.addImage(img);
	}

	private void handleGaussAppRight() {
		new ATIGaussNoiseDialog(parent,
				parent.getPanels()[Side.RIGHT.getValue()].getImage());
	}

	private void handleGaussAppLeft() {
		new ATIGaussNoiseDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleGaussSee() {
		new ATIGaussNoiseImageDialog(parent);
	}

	private void handleRaylightAppRight() {
		new ATIRaylightDialog(parent,
				parent.getPanels()[Side.RIGHT.getValue()].getImage());
	}

	private void handleRaylightAppLeft() {
		new ATIRaylightDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleRaylightSee() {

		ATImage img = new ATImage(100, 100, ImageType.GRAYSCALE);

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				img.R.set(i, j, 1);
				// img.G.set(i, j, 1);
				// img.B.set(i, j, 1);
			}
		}
		new ATIRayleighImageDialog(parent, img);
	}

	private void handleExpAppRight() {
		new ATIExpDialog(parent,
				parent.getPanels()[Side.RIGHT.getValue()].getImage());
	}

	private void handleExpAppLeft() {
		new ATIExpDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleExpSee() {
		ATImage img = new ATImage(100, 100, ImageType.GRAYSCALE);

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				img.R.set(i, j, 1);
				// img.G.set(i, j, 1);
				// img.B.set(i, j, 1);
			}
		}

		new ATIExpImageDialog(parent, img);
	}

	// Options
	private void handleChangePositions() {
		parent.swapImages();
	}

	private void handleClear() {
		parent.clear();
	}

	private void handleMultiplyByScalar() {
		new ATIMultiplyScalarDialog(parent,
				parent.getPanels()[Side.LEFT.getValue()].getImage());
	}

	private void handleApplyContrast(Side side) {
		new ATIContrastDialog(parent, side);
	}

	private void handleSubImage() {
		new ATISubImageDialog(parent);
	}

	private void handleNegImage() {

		ATImage img = new ATImage(parent.getPanels()[0].getImage());

		img.applyFunction(new Negative(), 100);
		parent.addImage(img);
	}

	private void handleSumImages() {
		ATImage imageL = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		ATImage imageR = new ATImage(
				parent.getPanels()[Side.RIGHT.getValue()].getImage());
		ATImage sum = new ATImage(imageL);
		sum.applyFunction(new SumImage(imageR), 100);
		parent.addImage(sum);
	}

	private void handleSubstractImages() {

		ATImage imgLeft = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());

		ATImage imgRight = new ATImage(
				parent.getPanels()[Side.RIGHT.getValue()].getImage());

		imgRight.applyFunction(new MultiplyBy(-1), 100);

		imgLeft.applyFunction(new SumImage(imgRight), 100);
		parent.addImage(imgLeft);
	}

	private void handleEqualize() {
		ATImage img = new ATImage(
				parent.getPanels()[Side.LEFT.getValue()].getImage());
		img.applyFunction(new Equalize(img), 100);
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
		new ATIImageJFrame(parent, ImageUtils.histogram(parent.getPanels()[0]
				.getImage()));
	}

	private void handleHistogramRight() {
		new ATIImageJFrame(parent, ImageUtils.histogram(parent.getPanels()[1]
				.getImage()));
	}

	// New
	private void handleColorScale() {
		ATImage img = ImageUtils.colorScale();
		parent.addImage(img);
	}

	private void handleGrayScale() {
		ATImage img = ImageUtils.grayScale();
		parent.addImage(img);
	}

	private void handleBlankSquare() {
		ATImage img = ImageUtils.blankSquare();
		parent.addImage(img);
	}

	private void handleBlankCircle() {
		ATImage img = ImageUtils.blankCircle();
		parent.addImage(img);
	}

	private void handleFractal() {
		String path = System.getProperty("user.dir")
				+ "/ATI/resources/images/fractal.raw";
		File file = new File(path);
		try {
			BufferedImage img = ImageUtils.load(file, new Dimension(
					200, 200));
			parent.addImage(new ATImage(file, img, ImageType.GRAYSCALE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleBarco() {
		String path = System.getProperty("user.dir")
				+ "/ATI/resources/images/barco.raw";
		File file = new File(path);
		try {
			BufferedImage img = ImageUtils.load(file, new Dimension(
					290, 207));
			parent.addImage(new ATImage(file, img, ImageType.GRAYSCALE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleGirl() {
		String path = System.getProperty("user.dir")
				+ "/ATI/resources/images/girl2.raw";
		File file = new File(path);
		try {
			BufferedImage img = ImageUtils.load(file, new Dimension(
					256, 256));
			parent.addImage(new ATImage(file, img, ImageType.GRAYSCALE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleLenax() {
		String path = System.getProperty("user.dir")
				+ "/ATI/resources/images/lenax.raw";
		File file = new File(path);
		try {
			BufferedImage img = ImageUtils.load(file, new Dimension(
					256, 256));
			parent.addImage(new ATImage(file, img, ImageType.GRAYSCALE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleLena() {
		String path = System.getProperty("user.dir")
				+ "/ATI/resources/images/lena.raw";
		File file = new File(path);
		try {
			BufferedImage img = ImageUtils.load(file, new Dimension(
					256, 256));
			parent.addImage(new ATImage(file, img, ImageType.GRAYSCALE));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			ImageIO.write(parent.getPanels()[Side.LEFT.getValue()].getImage().getVisual(), "jpg",
					left);
			ImageIO.write(parent.getPanels()[Side.RIGHT.getValue()].getImage().getVisual(), "jpg",
					right);
		} catch (Exception ex) {

		}
	}

	// SIFT
	private void handleSIFT() {
		 ATImage img = new ATImage(
		 parent.getPanels()[Side.LEFT.getValue()].getImage());
		 ATImage resp = new ATImage(img.getHeight(), img.getWidth(),
		 ImageType.GRAYSCALE);
		 Vector<Feature> features = SIFT.getFeatures(img.getVisual());
		 int col, row;
		 for (Feature feature : features) {
			 col = (int) (feature.location[0]);
			 row = (int) (feature.location[1]);
			 resp.drawCircle(row, col, (int) feature.scale);
		 }
		 
		 ATImage img2 = new ATImage(
				 parent.getPanels()[Side.RIGHT.getValue()].getImage());
				 ATImage resp2 = new ATImage(img2.getHeight(), img2.getWidth(),
				 ImageType.GRAYSCALE);
		 Vector<Feature> features2 = SIFT.getFeatures(img2.getVisual());
		 int col2, row2;
		 for (Feature feature : features2) {
			 col2 = (int) (feature.location[0]);
			 row2 = (int) (feature.location[1]);
			 resp2.drawCircle(row2, col2, (int) feature.scale);
		 }
		 parent.addImage(img.applyLayer(resp));
		 parent.addImage(img2.applyLayer(resp));
		 
		 Model model = new Model() {
			
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void shake(Collection<PointMatch> matches, float scale,
					float[] center) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void minimize(Collection<PointMatch> matches) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public AffineTransform getAffine() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean fit(PointMatch[] min_matches) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Model clone() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void applyInverseInPlace(float[] point) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public float[] applyInverse(float[] point) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void applyInPlace(float[] point) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public float[] apply(float[] point) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Collections.sort(features);
		Collections.sort(features2);
		
		Vector v = SIFT.createMatches(features, features2, 1.5f, null, Float.MAX_VALUE);
		System.out.println(v.size());
	}

}