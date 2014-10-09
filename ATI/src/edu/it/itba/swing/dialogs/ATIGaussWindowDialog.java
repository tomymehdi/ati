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

import edu.it.itba.functions.PassAdditiveWindow;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.windows.GaussianWIndow;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class ATIGaussWindowDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JTextField size;
	private JTextField sigma;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATIGaussWindowDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Gauss Window", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Apply window");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		size = new JTextField(4);
		sigma = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Window size"));
		p.add(size);

		p.add(new JLabel("Sigma"));
		p.add(sigma);

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
		int windowSize = Integer.valueOf(size.getText());
		double gaussSigma = Double.valueOf(sigma.getText());

		img.applyFunction(new PassAdditiveWindow(img, new GaussianWIndow(
				windowSize, gaussSigma)), 100);
		owner.addImage(img);

		handleClose();
	}

}
