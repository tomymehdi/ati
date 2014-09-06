package edu.it.itba.swing.panels;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import edu.it.itba.swing.interfaces.ATIJFrame;


@SuppressWarnings("serial")
public class ATILoadImagePanel extends JDialog {

	private ATIJFrame parent;
	private File file;

	public ATILoadImagePanel(ATIJFrame parent) {
		super(parent, "Load Image", true);
		this.parent = parent;

		chooseFile();

		if (file != null) {
			String fileName = file.getName();
			String extension = fileName.substring(fileName.length() - 3)
					.toLowerCase();

			if (extension.equals("raw")) {
				loadDimensions();
			} else {
				parent.loadImage(file, null);
			}
		}

	}

	private void chooseFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
	}

	private void loadDimensions() {
		new ATIDimensionPanel(this);
	}

	public void loadRaw(Dimension dim) {
		parent.loadImage(file, dim);
	}

}
