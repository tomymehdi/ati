package edu.it.itba.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.it.itba.functions.Diffusion;
import edu.it.itba.functions.LeclercBorderDetector;
import edu.it.itba.functions.LorentzBorderDetector;
import edu.it.itba.functions.MaterialHeatDistribution;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class ATICannyDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField t;
	private JTextField sigma;
	private JButton setValue;
	private JButton close;
	private ATImage img;
	private JRadioButton lorentz;
	private JRadioButton leclerc;

	public ATICannyDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Canny", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Apply Canny");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		t = new JTextField(4);
		sigma = new JTextField(4);

		lorentz = new JRadioButton("Lorentz");
		leclerc = new JRadioButton("Leclerc");
		lorentz.setSelected(true);
		ButtonGroup group = new ButtonGroup();
		group.add(lorentz);
		group.add(leclerc);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("t"));
		p.add(t);

		p.add(new JLabel("Sigma"));
		p.add(sigma);

		p.add(lorentz);
		p.add(leclerc);
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
		int time = Integer.valueOf(t.getText());
		Double gaussSigma = Double.valueOf(sigma.getText());
		MaterialHeatDistribution mhd;
		if (lorentz.isSelected())
			mhd = new LorentzBorderDetector(gaussSigma);
		else
			mhd = new LeclercBorderDetector(gaussSigma);

		for (int i = 0; i < time; i++) {
			img.applyFunction(new Diffusion(img, mhd, i), 100);
		}
		owner.addImage(img);
		handleClose();
	}

}
