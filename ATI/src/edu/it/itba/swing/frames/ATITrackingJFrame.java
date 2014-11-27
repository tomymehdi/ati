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
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.it.itba.enums.ImageType;
import edu.it.itba.functions.Tracking;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Pixel;
import edu.it.itba.swing.panels.ATImageJPanel;
import edu.it.itba.utils.ImageUtils;

public class ATITrackingJFrame extends JFrame {

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

	private ATImageJPanel image;
	int col, row, height, widht;
	double delta;

	public ATITrackingJFrame(int col, int row, int height, int widht,
			double delta) throws IOException {
		super();
		this.col = col;
		this.row = row;
		this.height = height;
		this.widht = widht;
		this.delta = delta;
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
				ATITrackingJFrame.this.timer.stop();

			}
		});

		start = new JButton("Start");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ATITrackingJFrame.this.timer.start();

			}
		});
		add(stop);
		add(start);
		// Display the window.
		setVisible(true);

	}

	public void playVideo() throws IOException {
		File folder = new File("/Users/Tom/Dropbox/ati-2013/TP3/Video/movie1");
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

		ATImage first = new ATImage(ImageUtils.load(files.get(0), null),
				ImageType.RGB);
		tracking = new Tracking(first, row, col, widht, height,
				new ArrayList<Pixel>(), new ArrayList<Pixel>(), null, delta,
				null);

		draw = new ATImage(first.getHeight(), first.getWidth(), ImageType.RGB);
		for (int r = 0; r < first.getHeight(); r++) {
			for (int c = 0; c < first.getWidth(); c++) {
				if (tracking.in.contains(new Pixel(r, c))) {
					draw.R.set(r, c, 255);
				}
				if (tracking.out.contains(new Pixel(r, c))) {
					draw.G.set(r, c, 255);
				}
			}
		}
		ATImage resp = first.applyLayer(draw);

		addImage(resp);

		ActionListener timerTask = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				ATImage current;
				try {
					current = new ATImage(ImageUtils.load(files.get(i), null),
							ImageType.RGB);

					tracking = new Tracking(current, row, col, widht, height,
							in, out, fis, delta, tracking.avgColor);

					tracking.runAlgorithm();

					draw = new ATImage(current.getHeight(), current.getWidth(),
							ImageType.RGB);
					for (int r = 0; r < current.getHeight(); r++) {
						for (int c = 0; c < current.getWidth(); c++) {
							if (tracking.in.contains(new Pixel(r, c))) {
								draw.R.set(r, c, 255);
							}
							if (tracking.out.contains(new Pixel(r, c))) {
								draw.G.set(r, c, 255);
							}
						}
					}
					ATImage resp = current.applyLayer(draw);

					addImage(resp);
					in = tracking.in;
					out = tracking.out;
					fis = tracking.fis;

					i++;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};

		timer = new Timer(100, timerTask);
		timer.setRepeats(true);

		timer.start();

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

			addImage(new ATImage(image, type));
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


}
