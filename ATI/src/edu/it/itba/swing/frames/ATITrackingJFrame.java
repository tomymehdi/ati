package edu.it.itba.swing.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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

	Tracking tracking;
	private int i = 0;
	List<Pixel> in = new ArrayList<Pixel>();
	List<Pixel> out = new ArrayList<Pixel>();
	int[][] fis;
	File[] listOfFiles;
	Timer timer;
	ATImage draw;
	public JButton stop;
	public JButton start;

	private ATImageJPanel imageLeft;
	private ATImageJPanel imageRight;
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
		File folder = new File("/home/dinu/Downloads/movie1");
		listOfFiles = folder.listFiles();

		ATImage first = new ATImage(ImageUtils.load(listOfFiles[0], null),
				ImageType.GRAYSCALE);
		addImage(first);
		tracking = new Tracking(first, row, col, widht, height,
				new ArrayList<Pixel>(), new ArrayList<Pixel>(), null, delta);

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
		first.applyLayer(draw);

		addImage(draw);

		ActionListener timerTask = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				ATImage current;
				try {
					current = new ATImage(
							ImageUtils.load(listOfFiles[i], null),
							ImageType.GRAYSCALE);

					addImage(current);
					tracking = new Tracking(current, row, col, widht, height,
							in, out, fis, delta);

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
					current.applyLayer(draw);

					addImage(draw);
					in = tracking.in;
					out = tracking.out;
					fis = tracking.fis;

					i++;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		timer = new Timer(10, timerTask);
		timer.start();

	}

	public void addImage(ATImage img) {
		if (imageLeft == null) {
			imageLeft = new ATImageJPanel(this, img);
			mainPanel.add(imageLeft);
			imageLeft.revalidate();
			imageLeft.repaint();
		} else if (imageRight == null) {
			imageRight = new ATImageJPanel(this, img);
			mainPanel.add(imageRight);
			imageRight.revalidate();
			imageRight.repaint();
		} else {
			mainPanel.removeAll();
			imageLeft = null;
			imageRight = null;
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
		imageLeft = null;
		imageRight = null;
	}
}
