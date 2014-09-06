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


@SuppressWarnings("serial")
public class ATIPixelValueEditJPanel extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField x;
	private JTextField y;
	private JTextField r;
	private JTextField g;
	private JTextField b;
	private JButton setValue;
	private JButton close;
	private JPanel answerPanel;
	private int[] value = new int[3];
	private Side side;

	public ATIPixelValueEditJPanel(ATIJFrame owner, Side side) {
		super(owner, "Set Pixel Value", true);
		this.owner = owner;
		this.side = side;

		setValue = new JButton("Set pixel");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		x = new JTextField(4);
		y = new JTextField(4);
		r = new JTextField(4);
		g = new JTextField(4);
		b = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("x"));
		p.add(x);

		p.add(new JLabel("y"));
		centralPanel.add(p);
		p.add(y);

		p.add(new JLabel("r"));
		centralPanel.add(p);
		p.add(r);

		p.add(new JLabel("g"));
		centralPanel.add(p);
		p.add(g);

		p.add(new JLabel("b"));
		centralPanel.add(p);
		p.add(b);

		p = new JPanel();
		p.add(setValue);
		p.add(close);
		centralPanel.add(p);

		answerPanel = new JPanel();
		answerPanel.add(new JLabel("Pixel edited."));
		answerPanel.setVisible(false);
		centralPanel.add(answerPanel);

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

		BufferedImage image = owner.getPanels()[side.getValue()].getImage();

		try {
			value[0] = Integer.parseInt(r.getText());
			value[1] = Integer.parseInt(g.getText());
			value[2] = Integer.parseInt(b.getText());
			image.getRaster().setPixel(Integer.parseInt(x.getText()),
					Integer.parseInt(y.getText()), value);
			owner.getPanels()[side.getValue()].revalidate();
			owner.getPanels()[side.getValue()].repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}

		answerPanel.setVisible(true);
	}
}
