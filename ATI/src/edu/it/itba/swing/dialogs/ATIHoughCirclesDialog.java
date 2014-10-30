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
import edu.it.itba.functions.LinearTransform;
import edu.it.itba.functions.OtzuUmbralization;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Circle;
import edu.it.itba.models.Line;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class ATIHoughCirclesDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JTextField r1,r2;
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

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();

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
		
		img.applyFunction(new LinearTransform(img), 100);
		img.applyFunction(new OtzuUmbralization(img), 100);
		owner.addImage(img);
		
		// Para cada pixel blanco analizar si cumple la ecuacion de la recta en todas las direcciones
		int aSize = (int) (img.getWidth() - 2*rMin);
		int bSize = (int) (img.getHeight() - 2*rMin);
		int rSize = (int) (rMax - rMin);
		int[][][] votes = new int[aSize][bSize][rSize];

		for (int r = 0; r < rSize; r += 2) {
			double rValue = rMin + r;
			double rTerm = Math.pow(rValue, 2);
			for (int a = 0; a < aSize; a += 2) {
				double aValue = rMin + a;
				for (int b = 0; b < bSize; b += 2) {
					double bValue = rMin + b;
					
					for (int row = 0; row < img.getWidth(); row += 2) {
						double aTerm = Math.pow(row - aValue, 2);
						for (int col = 0; col < img.getHeight(); col += 2) {
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
						Circle newBucket = new Circle(a, b,
								r, votes[a][b][r]);
						allBuckets.add(newBucket);
					}
				}
			}
		}
		
		ATImage aux = new ATImage(img);
		aux.type = ImageType.RGB;
		
		if (allBuckets.isEmpty()) {
			owner.addImage(aux);
		} else {
			List<Circle> allBucketsAsList = new ArrayList<Circle>(
					allBuckets);
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
	
					System.out.println("Circle: (" + aValue + "," + bValue + ","
							+ rValue + ")");
	
					drawCircle(aux, aValue, bValue, rValue);
	
				}
	
			owner.addImage(aux);
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
		 
		  while(y < x)
		  {
		    if(error > 0) // >= 0 produces a slimmer circle. =0 produces the circle picture at radius 11 above
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
