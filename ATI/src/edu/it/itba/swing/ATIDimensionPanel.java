package edu.it.itba.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ATIDimensionPanel extends JFrame {
	
	private ATILoadImagePanel parent;
	private JTextField width;
	private JTextField height;
	private JButton load;
	
	public ATIDimensionPanel(ATILoadImagePanel parent) {
		this.parent = parent;
		
		JPanel p = new JPanel();
		add(p);
		
		width = new JTextField(25);
		p.add(new JLabel("Widht:"));
		p.add(width);

		height = new JTextField(25);
		p.add(new JLabel("Height:"));
		p.add(height);
	
		load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Dimension dim = new Dimension(textWValue(), textHValue());
				edu.it.itba.swing.ATIDimensionPanel.this.parent.loadRaw(dim);
				edu.it.itba.swing.ATIDimensionPanel.this.dispose();
			}
		});
		p.add(load);
		
		setPreferredSize(new Dimension(350, 200));
		setSize(getPreferredSize());
		setVisible(true);
	}

	public int textWValue() {
		return Integer.valueOf(width.getText());
	}
	
	public int textHValue() {
		return Integer.valueOf(height.getText());
	}
}
