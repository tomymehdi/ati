package edu.it.itba.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import edu.it.itba.swing.frames.ATITrackingJFrame;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class ATITrackingVideoDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField x;
	private JTextField y;
	private JTextField width;
	private JTextField height;
	private JTextField delta;
	private JButton getSubImage;
	private JLabel data;

	public ATITrackingVideoDialog(ATIJFrame owner) {

		super(owner, "TRacking Video", true);
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
		handleClose();
		int col = Integer.parseInt(this.x.getText());
		int row = Integer.parseInt(this.y.getText());
		int width = Integer.parseInt(this.width.getText());
		int height = Integer.parseInt(this.height.getText());
		double deltaP = Double.parseDouble(this.delta.getText());

		try {
			ATITrackingJFrame video = new ATITrackingJFrame(col, row, height,
					width, deltaP);
			video.playVideo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleClose() {
		setVisible(false);
		dispose();
		return;
	}
}