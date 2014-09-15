package edu.it.itba.swing.panels;

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

import edu.it.itba.enums.Side;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.dialogs.SaltAndPepperNoise;
import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.utils.ImageUtils;

public class ATISaltAndPepperDialog extends JDialog implements ActionListener {

	ATIJFrame parent;
	JButton applyNoise;
	JTextField density;
	Side side;

	public ATISaltAndPepperDialog(ATIJFrame parent, Side side) {

		super(parent, "Salt and Pepper Noise", true);
		this.parent = parent;
		this.side = side;
		applyNoise = new JButton("Apply Noise");
		applyNoise.addActionListener(this);
		density = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("density"));
		p.add(density);
		centralPanel.add(p);

		p = new JPanel();
		p.add(applyNoise);
		centralPanel.add(p);

		mainPanel.add(centralPanel);

		this.add(mainPanel);

		setPreferredSize(new Dimension(200, 120));
		setSize(getPreferredSize());
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == applyNoise)
			handleApplyNoise();

		setVisible(false);
		dispose();
		return;
	}

	private void handleApplyNoise() {

		int densityToApply = Integer.parseInt(density.getText());

		ATImage img = new ATImage(
				parent.getPanels()[side.getValue()].getImage());

		img.applyFunction(new SaltAndPepperNoise(), densityToApply);

		parent.addImage(img);
	}
}
