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
import edu.it.itba.functions.PassAdditiveWindow;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.windows.LoG;
import edu.it.itba.swing.interfaces.ATIJFrame;

@SuppressWarnings("serial")
public class LaplacianGaussianDialog extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField sizeTF;
	private JTextField sigmaTF;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public LaplacianGaussianDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Laplacian gaussian border detector", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Set density");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		sizeTF = new JTextField(4);
		sigmaTF = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Window size"));
		p.add(sizeTF);

		p.add(new JLabel("Sigma"));
		p.add(sigmaTF);

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
		
		int size = Integer.valueOf(sizeTF.getText());
		double sigma = Double.valueOf(sigmaTF.getText());

		img.applyFunction(new PassAdditiveWindow(img, new LoG(size, sigma)), 100);
		owner.addImage(img);
		handleClose();
	}
	
	

}
