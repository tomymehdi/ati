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

import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class ATIExpImageDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField l;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATIExpImageDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Generate exponential noise", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Set density");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		l = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("lambda"));
		p.add(l);

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
		double lambda = Double.valueOf(l.getText());

		img.applyFunction(new ExponentialNoise(lambda), 100);

		// BufferedImage image = ImageUtils.multiplicativeExponentialNoise(img,
		// lambda, value);
		owner.addImage(img);
		handleClose();
	}

}
