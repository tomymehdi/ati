package edu.it.itba.swing.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.it.itba.swing.interfaces.ATIJFrame;


@SuppressWarnings("serial")
public class ATIMenu2 extends JMenuBar implements ActionListener{
	
	private ATIJFrame parent;
	
	private JMenuItem save;
	
	public ATIMenu2(ATIJFrame parent) {
		super();
		this.parent = parent;

		JMenu file = new JMenu("File");

		// File
		save = addMenuItemToMenu("Save...", file, true);
		
		addToMenu(file);
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// File
	private void handleSave() {
		try {
			String dir = System.getProperty("user.dir");
			dir += "/tests/results/";
			File histogram = new File(dir + "H.jpg");
		    ImageIO.write(parent.getPanels()[0].getImage().getVisual(), "jpg", histogram);
		} catch (Exception ex) {
			
		}
	}

}
