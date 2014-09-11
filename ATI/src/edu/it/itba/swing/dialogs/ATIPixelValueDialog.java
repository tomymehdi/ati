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


@SuppressWarnings("serial")
public class ATIPixelValueDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField x;
	private JTextField y;
	private JLabel answer;
	private JButton getValue;
	private JButton close;
	private JPanel answerPanel;
	private int resp[] = new int[3];

	public ATIPixelValueDialog(ATIJFrame owner) {

		super(owner, "Get Pixel Value", true);
		this.owner = owner;

		getValue = new JButton("Get Value");
		getValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		x = new JTextField(4);
		y = new JTextField(4);
		answer = new JLabel();

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("x"));
		p.add(x);

		p.add(new JLabel("y"));
		centralPanel.add(p);
		p.add(y);

		p = new JPanel();
		p.add(getValue);
		p.add(close);
		centralPanel.add(p);
		
		answerPanel = new JPanel();
		answerPanel.add(new JLabel("Pixel value:"));
		answerPanel.add(answer);
		answerPanel.setVisible(false);
		centralPanel.add(answerPanel);
		
		mainPanel.add(centralPanel);

		this.add(mainPanel);

		setPreferredSize(new Dimension(300, 250));
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		if (source == getValue)
			handleGetValue();
		else if (source == close)
			handleClose();
	}

	private void handleClose() {
		setVisible(false);
		dispose();
		return;
	}

	private void handleGetValue() {

		BufferedImage image = owner.getPanels()[0].getImage();

		try {
			image.getRaster().getPixel(Integer.parseInt(x.getText()),
					Integer.parseInt(y.getText()), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		answer.setText(String.valueOf(resp[0]) + " " + String.valueOf(resp[1]) + " " + String.valueOf(resp[2]));
		answerPanel.setVisible(true);
	}
}
