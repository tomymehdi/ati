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
import edu.it.itba.models.windows.BorderWindow;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class ATIBorderWindowDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ATIJFrame owner;
	private JTextField s;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATIBorderWindowDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Window size", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Set size");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		s = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Window size"));
		p.add(s);

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
		int size = Integer.valueOf(s.getText());

		img.applyFunction(new PassAdditiveWindow(img, new BorderWindow(size)),
				100);
		owner.addImage(img);

		handleClose();
	}
}
