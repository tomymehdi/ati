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
import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.utils.ImageUtils;

public class ATIUmbralDialog extends JDialog implements ActionListener {

	ATIJFrame parent;
	JButton applyUmbral;
	JTextField umbral;
	Side side;

	public ATIUmbralDialog(ATIJFrame parent, Side side) {

		super(parent, "Set Umbral", true);
		this.parent = parent;
		this.side = side;
		applyUmbral = new JButton("Apply umbral");
		applyUmbral.addActionListener(this);
		umbral = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("umbral"));
		p.add(umbral);
		centralPanel.add(p);

		p = new JPanel();
		p.add(applyUmbral);
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

		if (source == applyUmbral)
			handleApplyUmbral();

		setVisible(false);
		dispose();
		return;
	}

	private void handleApplyUmbral() {

		int umbralToApply = Integer.parseInt(umbral.getText());

		BufferedImage img = ImageUtils.applyUmbral(
				parent.getPanels()[side.getValue()].getImage(), umbralToApply);
		parent.addImage(img);
	}

}
