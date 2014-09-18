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

import edu.it.itba.enums.Side;
import edu.it.itba.functions.Contrast;
import edu.it.itba.functions.LinearTransform;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class ATIContrastDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField r1;
	private JTextField r2;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATIContrastDialog(ATIJFrame owner, Side side) {
		super(owner, "Contrast", true);
		this.owner = owner;
		this.img = new ATImage(owner.getPanels()[side.getValue()].getImage());

		setValue = new JButton("apply contrast");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		r1 = new JTextField(4);
		r2 = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("R1"));
		p.add(r1);

		p.add(new JLabel("R2"));
		p.add(r2);

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
		int value1 = Integer.valueOf(r1.getText());
		int value2 = Integer.valueOf(r2.getText());

		img.applyFunction(new Contrast(value1, value2), 100);

		img.applyFunction(new LinearTransform(img), 100);
		owner.addImage(img);
		handleClose();
	}
}
