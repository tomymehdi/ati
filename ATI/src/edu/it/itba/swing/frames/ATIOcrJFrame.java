package edu.it.itba.swing.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.it.itba.enums.ImageType;
import edu.it.itba.enums.Side;
import edu.it.itba.functions.BinaryRGBClusterization;
import edu.it.itba.functions.OtzuUmbralizationRGB;
import edu.it.itba.functions.Tracking;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Pixel;
import edu.it.itba.swing.panels.ATImageJPanel;
import edu.it.itba.utils.ImageUtils;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ATIOcrJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	int[] square;
	Tracking tracking;
	private int i = 0;
	List<Pixel> in = new ArrayList<Pixel>();
	List<Pixel> out = new ArrayList<Pixel>();
	int[][] fis;
	List<File> files;
	Timer timer;
	ATImage draw;
	public JButton stop;
	public JButton start;
	public JButton next;
	public JButton previous;
	private String moviePath = "/Users/tomymehdi/itba/ati/ATI/resources/videos/movie1";

	private ATImageJPanel image;

	public ATIOcrJFrame() throws IOException {
		super();

		// Create and set up the window.
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);

		// Create the main panel
		mainPanel = new JPanel();
		mainPanel.setSize(400, 400);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		add(mainPanel);
		stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ATIOcrJFrame.this.timer.stop();
			}
		});

		start = new JButton("Start");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ATIOcrJFrame.this.timer.start();
			}
		});
		
		next = new JButton("Next");
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				OCR(i+1);
			}
		});
		
		previous = new JButton("Previous");
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				OCR(i-1);
			}
		});
		add(stop);
		add(start);
		add(next);
		add(previous);
		// Display the window.
		setVisible(true);

	}

	public void playVideo() throws IOException {
		File folder = new File(moviePath);
		File[] listOfFiles = folder.listFiles();
		files = new ArrayList<File>();

		for (File e : listOfFiles) {
			files.add(e);
		}

		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File arg0, File arg1) {
				return arg0.getAbsolutePath().compareTo(arg1.getAbsolutePath());
			}
		});

		ActionListener timerTask = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				OCR(i);
			}
		};

		timer = new Timer(100, timerTask);
		timer.setRepeats(true);
	}

	public void addImage(ATImage img) {
		if (image == null) {
			image = new ATImageJPanel(img);
			mainPanel.add(image);
			image.revalidate();
			image.repaint();
		} else {
			mainPanel.removeAll();
			image = null;
			addImage(img);
		}
	}

	public void loadImage(File file, Dimension dim) {
		try {

			BufferedImage image = ImageUtils.load(file, dim);

			ImageType type = ImageType.GRAYSCALE;

			switch (image.getType()) {
			case BufferedImage.TYPE_INT_RGB:
				type = ImageType.RGB;
				break;
			case 5:
				type = ImageType.GRAYSCALE;
				break;
			case BufferedImage.TYPE_BYTE_GRAY:
				type = ImageType.GRAYSCALE;
				break;
			}

			addImage(new ATImage(file, image, type));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		mainPanel.removeAll();
		mainPanel.revalidate();
		mainPanel.repaint();
		image = null;
	}
	
	private void OCR(int imageIndex) {
		ATImage first;
		try {
			first = new ATImage(files.get(i), ImageUtils.load(files.get(i+1), null),
					ImageType.RGB);
		
			// TODO OCR to image
			
			// OCR without applying algorithm
			File imageFile = first.getFile();
			Tesseract instance = Tesseract.getInstance();  // JNA Interface Mapping
			try {
			    String result = instance.doOCR(imageFile);
			    System.out.println(result);
			} catch (TesseractException e) {
			    System.err.println(e.getMessage());
			}
			
			// Step 1 - Otzu umbralization RGB
			ATImage image = first;
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
			
			addImage(colorVideoAlgorithmImage);
			
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
			
			// END OCR to image
			
			ATImage resp = first;
	
			addImage(resp);
			if(imageIndex >= i){
				i++;
			} else {
				i--;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


}
