package edu.it.itba.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ATImageJMenu extends JMenuBar implements ActionListener {

	ATIImageJFrame parent;
	JMenuItem save;
	JMenuItem pixelValue;
	JMenuItem modifyPixelValue;
	JMenuItem subImage;

	public ATImageJMenu(ATIImageJFrame parent) {
		super();
		this.parent = parent;

		JMenu file = new JMenu("File");
		JMenu view = new JMenu("View");
		JMenu edit = new JMenu("Edit");
		JMenu newImage = new JMenu("New");

		save = addMenuItemToMenu("Save...", file, true);

		pixelValue = addMenuItemToMenu("Pixel Value...", view, true);

		modifyPixelValue = addMenuItemToMenu("Pixel Value...", edit, true);

		subImage = addMenuItemToMenu("Sub Image...", newImage, true);

		addToMenu(file);
		addToMenu(view);
		addToMenu(edit);
		addToMenu(newImage);
	}

	public void addToMenu(JMenu menu) {
		add(menu);
	}

	public JMenuItem addMenuItemToMenu(String item, JMenu menu, boolean enable) {

		JMenuItem resp = new JMenuItem(item);
		resp.addActionListener(this);
		resp.setEnabled(enable);

		menu.add(resp);
		return resp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			Object source = e.getSource();

			if (source == save)
				handleSave();
			else if (source == pixelValue)
				handleShowPixelValue();
			else if (source == modifyPixelValue)
				handleModifyPixelValue();
			else if (source == subImage)
				handleSubImage();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleSubImage() {
		// new ATISubImageJPanel(parent);
	}

	private void handleModifyPixelValue() {
		// TODO Auto-generated method stub

	}

	private void handleShowPixelValue() {

		// new ATIPixelValueJPanel(parent);

	}

	private void handleSave() {

		JFileChooser chooser = new JFileChooser();
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter fw = new FileWriter(chooser.getSelectedFile());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
