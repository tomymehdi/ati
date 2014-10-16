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
import edu.it.itba.functions.Crossing;
import edu.it.itba.functions.PassAdditiveWindow;
import edu.it.itba.models.ATImage;
import edu.it.itba.models.windows.BorderWindow;
import edu.it.itba.models.windows.Laplacian;
import edu.it.itba.swing.interfaces.ATIJFrame;

public class ATILaplacianPendantDialog extends JDialog implements ActionListener{


	private static final long serialVersionUID = 1L;
	private ATIJFrame owner;
	private JTextField s;
	private JButton setValue;
	private JButton close;
	private ATImage img;

	public ATILaplacianPendantDialog(ATIJFrame owner, ATImage img) {
		super(owner, "Pendant Laplacian", true);
		this.owner = owner;
		this.img = new ATImage(img);

		setValue = new JButton("Set umbral");
		setValue.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		s = new JTextField(4);

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("umbral"));
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
		int umbral = Integer.valueOf(s.getText());

		
		img.applyFunction(new PassAdditiveWindow(img, new Laplacian(3)), 100);
		owner.clear();
		owner.addImage(img);
		ATImage img2 = new ATImage(img);
		img2.applyFunction(new Crossing(img2, umbral), 100);
		owner.addImage(img2);

		handleClose();
	}
}
