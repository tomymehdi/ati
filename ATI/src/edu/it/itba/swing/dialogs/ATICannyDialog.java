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

import edu.it.itba.functions.Canny;
import edu.it.itba.functions.Diffusion;
import edu.it.itba.functions.LeclercBorderDetector;
import edu.it.itba.functions.LorentzBorderDetector;
import edu.it.itba.functions.MaterialHeatDistribution;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class ATICannyDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField window;
	private JTextField sigma;
	private JTextField t1;
	private JTextField t2;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATICannyDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Canny", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Apply Canny");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		window = new JTextField(4);
		sigma = new JTextField(4);
		t1 = new JTextField(4);
		t2 = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("window"));
		p.add(window);

		p.add(new JLabel("Sigma"));
		p.add(sigma);

		p.add(new JLabel("t1"));
		p.add(t1);

		p.add(new JLabel("t2"));
		p.add(t2);

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
		int windowSize = Integer.valueOf(window.getText());
		Double gaussSigma = Double.valueOf(sigma.getText());
		int umb1 = Integer.valueOf(t1.getText());
		int umb2 = Integer.valueOf(t2.getText());

		Canny can = new Canny(img, windowSize, gaussSigma, umb1, umb2);
		ATImage resp = can.applyCanny();
		owner.addImage(resp);
		handleClose();
	}

}
