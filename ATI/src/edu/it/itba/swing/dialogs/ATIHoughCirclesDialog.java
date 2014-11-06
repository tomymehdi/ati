package edu.it.itba.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.it.itba.enums.ImageType;
import edu.it.itba.functions.Canny;
import edu.it.itba.functions.LinearTransform;
import edu.it.itba.functions.OtzuUmbralization;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Circle;
import edu.it.itba.models.Line;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class ATIHoughCirclesDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JTextField r1, r2, t1, t2, window, gauss;
	private JTextField t;
	private JTextField e;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATIHoughCirclesDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Detect lines", true);
		this.owner = owner;
		this.img = new ATImage(img);
		setValue = new JButton("See image");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		r1 = new JTextField(4);
		r2 = new JTextField(4);
		t = new JTextField(4);
		e = new JTextField(4);
		window = new JTextField(4);
		gauss = new JTextField(4);
		t1 = new JTextField(4);
		t2 = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();

		p.add(new JLabel("windows"));
		p.add(window);

		p.add(new JLabel("gauss"));
		p.add(gauss);
		p.add(new JLabel("t1"));
		p.add(t1);
		p.add(new JLabel("t2"));
		p.add(t2);

		p.add(new JLabel("Umbral(%)"));
		p.add(t);

		p.add(new JLabel("Epsilon(0.1 recomendado)"));
		p.add(e);

		p.add(new JLabel("Epsilon(0.1 recomendado)"));
		p.add(r1);
		p.add(r2);

		p.add(setValue);
		p.add(close);
		centralPanel.add(p);

		mainPanel.add(centralPanel);

		this.add(mainPanel);

		setPreferredSize(new Dimension(450, 120));
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		if (source == setValue)
			handleSetValue();
		else if (source == close)
			handleClose();
	}

	private void handleClose() {
		setVisible(false);
		dispose();
		return;
	}

	private void handleSetValue() {
		double threshold = Double.valueOf(t.getText());
		double epsilon = Double.valueOf(e.getText());
		int rMin = Integer.valueOf(r1.getText());
		int rMax = Integer.valueOf(r2.getText());

		int windowSize = Integer.valueOf(window.getText());
		Double gaussSigma = Double.valueOf(gauss.getText());
		int umb1 = Integer.valueOf(t1.getText());
		int umb2 = Integer.valueOf(t2.getText());

		Canny can = new Canny(img, windowSize, gaussSigma, umb1, umb2);

		img = can.applyCanny();

		// Para cada pixel blanco analizar si cumple la ecuacion de la recta en
		// todas las direcciones
		int aSize = (int) (img.getWidth() - 2 * rMin);
		int bSize = (int) (img.getHeight() - 2 * rMin);
		int rSize = (int) (rMax - rMin);
		int[][][] votes = new int[aSize][bSize][rSize];

		for (int r = 0; r < rSize; r += 5) {
			double rValue = rMin + r;
			double rTerm = Math.pow(rValue, 2);
			for (int a = 0; a < aSize; a += 2) {
				double aValue = rMin + a;
				for (int b = 0; b < bSize; b += 2) {
					double bValue = rMin + b;

					for (int row = 0; row < img.getHeight(); row += 1) {
						double aTerm = Math.pow(row - aValue, 2);
						for (int col = 0; col < img.getWidth(); col += 1) {
							if (img.R.getValue(row, col) == 255) {
								double bTerm = Math.pow(col - bValue, 2);
								double total = rTerm - aTerm - bTerm;
								if (Math.abs(total) < epsilon) {
									votes[a][b][r] += 1;
								}
							}
						}
					}
				}
			}
		}

		Set<Circle> allBuckets = new HashSet<Circle>();
		for (int a = 0; a < aSize; a += 2) {
			for (int b = 0; b < bSize; b += 2) {
				for (int r = 0; r < rSize; r += 2) {
					if (votes[a][b][r] > 0) {
						Circle newBucket = new Circle(a, b, r, votes[a][b][r]);
						allBuckets.add(newBucket);
					}
				}
			}
		}

		ATImage aux = new ATImage(img);
		ATImage ret = new ATImage(aux);
		ret.type = ImageType.RGB;

		if (allBuckets.isEmpty()) {
			owner.addImage(aux);
		} else {
			List<Circle> allBucketsAsList = new ArrayList<Circle>(allBuckets);
			Collections.sort(allBucketsAsList);

			int maxHits = allBucketsAsList.get(0).votes;

			System.out.println("maxHits:" + maxHits);

			if (maxHits > 2)
				for (Circle b : allBucketsAsList) {
					if (b.votes < maxHits * threshold) {
						break;
					}

					int aValue = rMin + b.a;

					int bValue = rMin + b.b;
					int rValue = rMin + b.r;

					System.out.println("Circle: (" + aValue + "," + bValue
							+ "," + rValue + ")");

					drawCircle(ret, aValue, bValue, rValue);

				}

			owner.addImage(ret);
		}
		handleClose();
	}

	private static void drawCircle(ATImage image, int x0, int y0, int radius) {

		int error = 1 - radius;
		int errorY = 1;
		int errorX = -2 * radius;
		int x = radius, y = 0;

		image.G.set(x0, y0 + radius, 100);
		image.G.set(x0, y0 - radius, 100);
		image.G.set(x0 + radius, y0, 100);
		image.G.set(x0 - radius, y0, 100);

		while (y < x) {
			if (error > 0) // >= 0 produces a slimmer circle. =0 produces the
							// circle picture at radius 11 above
			{
				x--;
				errorX += 2;
				error += errorX;
			}
			y++;
			errorY += 2;
			error += errorY;

			image.G.set(x0 + x, y0 + y, 100);
			image.G.set(x0 - x, y0 + y, 100);
			image.G.set(x0 + x, y0 - y, 100);
			image.G.set(x0 - x, y0 - y, 100);
			image.G.set(x0 + y, y0 + x, 100);
			image.G.set(x0 - y, y0 + x, 100);
			image.G.set(x0 + y, y0 - x, 100);
			image.G.set(x0 - y, y0 - x, 100);
		}
	}
}
