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

import edu.it.itba.functions.Diffusion;
import edu.it.itba.functions.IsotropicDiffussion;
import edu.it.itba.functions.IsotropicMaterialHeatDistribution;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class ATIsotropicDiffusionDialog extends JDialog implements
		ActionListener {

	private ATIJFrame owner;
	private JTextField t;
	//private JTextField sigmaTF;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATIsotropicDiffusionDialog(ATIJFrame owner, ATImage img) {
		super(owner, "isotropic Difussion", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Diffuminate");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		t = new JTextField(4);
		//sigmaTF = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("t"));
		p.add(t);
		
		//p.add(new JLabel("sigma"));
		//p.add(sigmaTF);

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
		//double sigma = Double.valueOf(sigmaTF.getText());

		for (int i = 0; i < time; i++) {
			//img.applyFunction(new IsotropicDiffussion(img, sigma ), 100);
			img.applyFunction(new Diffusion(img,
				new IsotropicMaterialHeatDistribution(), i), 100);
		}
		owner.addImage(img);
		handleClose();
	}

}
