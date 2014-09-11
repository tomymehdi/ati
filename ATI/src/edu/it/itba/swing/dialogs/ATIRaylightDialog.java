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
public class ATIRaylightDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JTextField d;
	private JTextField e;
	private JButton setValue;
	private JButton close;
	private BufferedImage img;

	public ATIRaylightDialog(ATIJFrame owner, BufferedImage img) {
		super(owner, "Generate impulsive noise", true);
		this.owner = owner;
		this.img = img;
		
		setValue = new JButton("Set density");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		d = new JTextField(4);
		e = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Density"));
		p.add(d);
		
		p.add(new JLabel("Eta"));
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
		int value = Integer.valueOf(d.getText());
		int eta = Integer.valueOf(e.getText());
		BufferedImage image = ImageUtils.multiplicativeRayleighNoise(img, eta, value);
		owner.addImage(image);
		handleClose();
	}
}
