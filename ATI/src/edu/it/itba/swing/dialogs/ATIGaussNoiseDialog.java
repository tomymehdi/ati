package edu.it.itba.swing.dialogs;

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

import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.utils.ImageUtils;

@SuppressWarnings("serial")
public class ATIGaussNoiseDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JTextField d;
	private JTextField m;
	private JTextField s;
	private JButton setValue;
	private JButton close;
	private BufferedImage img;

	public ATIGaussNoiseDialog(ATIJFrame owner, BufferedImage img) {
		super(owner, "Generate gaussian noise", true);
		this.owner = owner;
		this.img = img;
		
		setValue = new JButton("Set density");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		d = new JTextField(4);
		m = new JTextField(4);
		s = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Density"));
		p.add(d);
		
		p.add(new JLabel("Mu"));
		p.add(m);
		
		p.add(new JLabel("Sigma"));
		p.add(s);
		
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
		int value = Integer.valueOf(d.getText());
		double mu = Double.valueOf(m.getText());
		double sigma = Double.valueOf(s.getText());
		BufferedImage image = ImageUtils.additiveGaussinianNoise(img, mu, sigma, value);
		owner.addImage(image);
		handleClose();
	}
}
