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

import edu.it.itba.functions.GlobalUmbralization;
import edu.it.itba.models.ATImage;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class GlobalUmbralDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField epocsTF;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public GlobalUmbralDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Generate exponential noise", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Set density");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		epocsTF = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Epocs"));
		p.add(epocsTF);


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
		int epocs = Integer.valueOf(epocsTF.getText());

		img.applyFunction(new GlobalUmbralization(img, epocs), 100);
		owner.addImage(img);
		handleClose();
	}

}
