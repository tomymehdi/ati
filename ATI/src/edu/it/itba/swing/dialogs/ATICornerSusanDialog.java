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

import edu.it.itba.functions.PassSusanWindow;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class ATICornerSusanDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JTextField error;
	private JTextField delta;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATICornerSusanDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Susan Border Detector", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Apply Susan");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		error = new JTextField(4);
		delta = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("error"));
		p.add(error);

		p.add(new JLabel("delta"));
		p.add(delta);

		p.add(setValue);
		p.add(close);
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
		Double porcentajeError = Double.valueOf(error.getText());
		int pixelDelta = Integer.valueOf(delta.getText());
		ATImage img2 = new ATImage(img);
		img.applyFunction(new PassSusanWindow(img, pixelDelta, 2,
				porcentajeError), 100);

		ATImage ret = img2.applyLayer(img);
		owner.addImage(img);
		handleClose();
	}

}
