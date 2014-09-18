package edu.it.itba.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.it.itba.functions.LinearTransform;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.utils.ImageUtils;

@SuppressWarnings("serial")
public class ATIGaussNoiseImageDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField m;
	private JTextField s;
	private JButton seeImage;
	private JButton close;

	public ATIGaussNoiseImageDialog(ATIJFrame owner) {
		super(owner, "Generate noise image", true);
		this.owner = owner;

		seeImage = new JButton("See Image");
		seeImage.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		m = new JTextField(4);
		s = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Mu"));
		p.add(m);

		p.add(new JLabel("Sigma"));
		p.add(s);

		p.add(seeImage);
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
		if (source == seeImage)
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
		double mu = Double.valueOf(m.getText());
		double sigma = Double.valueOf(s.getText());
		ATImage image = ImageUtils.guassImage(mu, sigma);
		owner.applyTransform(image);
		owner.addImage(image);
		handleClose();
	}

}
