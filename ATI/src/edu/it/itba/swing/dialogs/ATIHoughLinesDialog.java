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
import edu.it.itba.models.Line;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class ATIHoughLinesDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JTextField e;
	private JTextField t;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATIHoughLinesDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Detect lines", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("See image");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		e = new JTextField(4);
		t = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();

		p.add(new JLabel("Umbral(%)"));
		p.add(t);

		p.add(new JLabel("Epsilon(0.1 recomendado)"));
		p.add(e);

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
		
		img.applyFunction(new LinearTransform(img), 100);
		img.applyFunction(new OtzuUmbralization(img), 100);
		owner.addImage(img);
		
		// Para cada pixel blanco analizar si cumple la ecuacion de la recta en todas las direcciones
		int D = Math.max(img.getWidth(), img.getHeight());
		int [][] votes = new int[(int) (2*Math.sqrt(2) * D)][180];
		
		for(int row = 1; row < img.getHeight()-1 ; row++){
			for(int col = 1 ; col < img.getWidth()-1 ; col++){
				
				if(img.R.getValue(row, col) == 255){
					
					for(int theta = 0 ; theta < 180 ; theta ++){
						
						double thetaValue = -90 + theta;
						double thetaTerm = col
								* Math.cos(thetaValue * Math.PI / 180) - row
								* Math.sin(thetaValue * Math.PI / 180);
						
						for(int ro = 0 ; ro < 2*Math.sqrt(2) * D -1; ro ++){
							double roValue = -Math.sqrt(2) * D + ro;
							double total = roValue - thetaTerm;
							
							if (Math.abs(total) < epsilon) {
								votes[ro][theta] +=1;
							}
						}
					}
				}
				
				
			}
		}

		Set<Line> allBuckets = new HashSet<Line>();
		for (int theta = 0; theta < 180; theta++) {
			for (int ro = 0; ro < 2*Math.sqrt(2) * D -1; ro++) {
				Line l = new Line(ro, theta, votes[ro][theta]);
				allBuckets.add(l);
			}
		}
		
		List<Line> allBucketsAsList = new ArrayList<Line>(
				allBuckets);
		Collections.sort(allBucketsAsList);
		int maxVotes = allBucketsAsList.get(0).votes;
		
		ATImage aux = new ATImage(img);
		aux.type = ImageType.RGB;
		
		if (maxVotes > 1) {
			for (Line b : allBucketsAsList) {

				// Only for those with max votes
				if (b.votes < maxVotes * threshold) {
					break;
				}

				double roValue = -Math.sqrt(2) * D + b.ro;
				double thetaValue = -90 + b.theta;

				for (int row = 0; row < img.getHeight(); row++) {
					for (int col = 0; col < img.getWidth(); col++) {
						double thetaTerm = col
								* Math.cos(thetaValue * Math.PI / 180) - row
								* Math.sin(thetaValue * Math.PI / 180);
						double total = roValue - thetaTerm;
						// Step 6
						if (Math.abs(total) < epsilon) {
							aux.R.set(row, col, 0);
							aux.B.set(row, col, 0);
							aux.G.set(row, col, 255);
						}
					}
				}

			}
		}
		
		owner.addImage(aux);
		handleClose();
	}
}
