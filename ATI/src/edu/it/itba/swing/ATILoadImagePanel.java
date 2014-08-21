package edu.it.itba.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ATILoadImagePanel extends JDialog implements ActionListener {

	private ATIJFrame owner;
	private JTextField widht;
	private JTextField height;
	private File file;
	private JFileChooser fileChooser;
	private JButton load;

	public ATILoadImagePanel(ATIJFrame owner) {
		super(owner, "Load Image", true);

		this.owner = owner;
		load = new JButton("Load");
		widht = new JTextField(25);
		height = new JTextField(25);

		fileChooser = new JFileChooser();

		JPanel mainPanel = new JPanel();
		JPanel centralPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		fileChooser.showOpenDialog(this);
		//p.add(fileChooser);
		//centralPanel.add(p);

		p = new JPanel();
		p.add(new JLabel("widht*"));
		p.add(widht);
		centralPanel.add(p);

		p = new JPanel();
		p.add(new JLabel("height*"));
		p.add(height);
		centralPanel.add(p);

		p = new JPanel();
		load.addActionListener(this);
		p.add(load);
		centralPanel.add(p, BorderLayout.SOUTH);

		mainPanel.add(centralPanel);

		this.add(mainPanel);

		setPreferredSize(new Dimension(650, 650));
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		file = fileChooser.getSelectedFile();
		String fileName = file.getName();
		String extension = fileName.substring(fileName.length() - 3)
				.toLowerCase();
		Integer widht = 0;
		Integer height = 0;
		System.out.println(e.getActionCommand());

		if (true) {
			try {
				owner.createImagePanels(file, null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (extension.equals("raw") && !this.widht.getText().equals("")
				&& !this.height.getText().equals("")) {
			widht = Integer.parseInt(this.widht.getText());
			height = Integer.parseInt(this.height.getText());
			try {
				owner.createImagePanels(file, new Dimension(widht, height));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		setVisible(false);
		dispose();
		return;
	}
}
