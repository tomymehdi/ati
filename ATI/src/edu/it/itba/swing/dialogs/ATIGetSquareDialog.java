package edu.it.itba.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;
import edu.it.itba.swing.panels.ATImageJPanel;

public class ATIGetSquareDialog extends JDialog implements ActionListener {
	private ATIJFrame owner;
	private JButton close;
	private ATImage img;
	ATImageJPanel image;
	JPanel mainPanel;

	public ATIGetSquareDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Get Square", true);
		this.owner = owner;
		this.img = new ATImage(img);

		close = new JButton("Close");
		close.addActionListener(this);

		mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(close);
		addImage(this.img);

		centralPanel.add(p);
		mainPanel.add(centralPanel);

		this.add(mainPanel);

		setPreferredSize(new Dimension(800, 600));
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int[] square = image.getSquare();
		new ATITrackingVideoDialog(owner, square[0], square[1], square[2],
				square[3]);

		handleClose();
	}

	private void handleClose() {

		setVisible(false);
		dispose();
		return;
	}

	public void addImage(ATImage img) {
		if (image == null) {
			image = new ATImageJPanel(img);
			mainPanel.add(image);
			image.revalidate();
			image.repaint();
		} else {
			mainPanel.removeAll();
			image = null;
			addImage(img);
		}
	}
}
