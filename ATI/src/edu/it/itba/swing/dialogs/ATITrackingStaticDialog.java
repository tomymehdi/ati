package edu.it.itba.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.it.itba.enums.ImageType;
import edu.it.itba.enums.Side;
import edu.it.itba.functions.Tracking;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.Pixel;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class ATITrackingStaticDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField x;
	private JTextField y;
	private JTextField width;
	private JTextField height;
	private JTextField delta;
	private JButton getSubImage;
	private JLabel data;

	public ATITrackingStaticDialog(ATIJFrame owner) {

		super(owner, "Get Sub Image", true);
		this.owner = owner;
		getSubImage = new JButton("Get Sub Image");
		getSubImage.addActionListener(this);
		x = new JTextField(4);
		y = new JTextField(4);
		width = new JTextField(4);
		height = new JTextField(4);
		data = new JLabel();
		delta = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("x"));
		p.add(x);
		p.add(new JLabel("delta"));
		p.add(delta);

		p.add(new JLabel("y"));
		p.add(y);
		centralPanel.add(p);

		p = new JPanel();
		p.add(new JLabel("widht"));
		p.add(width);

		p.add(new JLabel("height"));
		p.add(height);
		centralPanel.add(p);

		p = new JPanel();
		p.add(getSubImage);

		p.add(data);

		centralPanel.add(p);

		mainPanel.add(centralPanel);

		this.add(mainPanel);

		setPreferredSize(new Dimension(500, 120));
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == getSubImage)
			handleSubImage();

	}

	private void handleSubImage() {
		int col = Integer.parseInt(this.x.getText());
		int row = Integer.parseInt(this.y.getText());
		int width = Integer.parseInt(this.width.getText());
		int height = Integer.parseInt(this.height.getText());
		double deltaP = Double.parseDouble(this.delta.getText());
		ATImage img = new ATImage(
				owner.getPanels()[Side.LEFT.getValue()].getImage());

		Tracking tracking = new Tracking(img, row, col, width, height,
				new ArrayList<Pixel>(), new ArrayList<Pixel>(), deltaP);
		System.out.println(tracking.in.size());
		ATImage draw = new ATImage(img.getHeight(), img.getWidth(),
				ImageType.RGB);
		for (int r = 0; r < img.getHeight(); r++) {
			for (int c = 0; c < img.getWidth(); c++) {
				if (tracking.in.contains(new Pixel(r, c))) {
					draw.R.set(r, c, 255);
				}
				if(tracking.out.contains(new Pixel(r, c))){
					draw.G.set(r,c,255);
				}
			}
		}
		img.applyLayer(draw);

		owner.addImage(draw);
		handleClose();
	}
	
	private void handleClose() {
		setVisible(false);
		dispose();
		return;
	}
}