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
	private JTextField delta;
	private JButton getSubImage;
	private JLabel data;
	int px, py, qx, qy;

	public ATITrackingVideoDialog(ATIJFrame owner, int px, int py, int qx,
			int qy) {

		super(owner, "TRacking Video", true);
		this.px = px;
		this.py = py;
		this.qx = qx;
		this.qy = qy;

		this.owner = owner;
		getSubImage = new JButton("Get Sub Image");
		getSubImage.addActionListener(this);
		data = new JLabel();
		delta = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("delta"));
		p.add(delta);
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
		double deltaP = Double.parseDouble(this.delta.getText());

		try {
			ATITrackingJFrame video = new ATITrackingJFrame(px, py, Math.abs(py
					- qy), Math.abs(px - qx), deltaP);
			video.playVideo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleClose() {
		setVisible(false);
		dispose();
		return;
	}
}