package edu.it.itba.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.it.itba.utils.ImageUtils;

public class ATISubImageJPanel extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField x;
	private JTextField y;
	private JTextField widht;
	private JTextField height;
	private JButton getSubImage;

	public ATISubImageJPanel(ATIJFrame owner) {

		super(owner, "Get Sub Image", true);
		this.owner = owner;
		getSubImage = new JButton("Get Sub Image");
		getSubImage.addActionListener(this);
		x = new JTextField(4);
		y = new JTextField(4);
		widht = new JTextField(4);
		height = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("x"));
		p.add(x);

		p.add(new JLabel("y"));
		p.add(y);
		centralPanel.add(p);

		p = new JPanel();
		p.add(new JLabel("widht"));
		p.add(widht);

		p.add(new JLabel("height"));
		p.add(height);
		centralPanel.add(p);

		p = new JPanel();
		p.add(getSubImage);
		centralPanel.add(p);

		mainPanel.add(centralPanel);

		this.add(mainPanel);

		setPreferredSize(new Dimension(300, 250));
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
		int x = Integer.parseInt(this.x.getText());
		int y = Integer.parseInt(this.y.getText());
		int widht = Integer.parseInt(this.widht.getText());
		int height = Integer.parseInt(this.height.getText());

		BufferedImage subImage = ImageUtils.getSubImage(owner.getImage(), x, y,
				widht, height);
		owner.createSubImage(subImage);
		setVisible(false);
		dispose();
		return;
	}
}
