package edu.it.itba.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.it.itba.swing.interfaces.ATIJPanel;

@SuppressWarnings("serial")
public class Histogram extends ATIJPanel {

	protected static final int MIN_BAR_WIDTH = 4;
	private ATImage image;
	private Map<Integer, Integer> mapHistory = new HashMap<Integer, Integer>();

	public Histogram(ATImage image) {
		this.image = image;
		calculateHistogram();
		setSize(1200, 1800);
		setPreferredSize(new Dimension(1200, 1800));
	}

	private void calculateHistogram() {

		Band band = image.R;
		for (int i = 0; i < 255; i++) {
			mapHistory.put(i, 0);
		}

		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {

				// int value[] = new int[3];
				int value = 0;
				value = (int) band.getValue(i, j);
				int amount = 0;
				if (mapHistory.containsKey(value)) {
					amount = mapHistory.get(value);
					amount++;
				} else {
					amount = 1;
				}
				mapHistory.put(value, amount);
			}
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mapHistory != null) {
			int xOffset = 5;
			int yOffset = 5;
			int width = getWidth() - 1 - (xOffset * 2);
			int height = getHeight() - 1 - (yOffset * 2);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(Color.DARK_GRAY);
			g2d.drawRect(xOffset, yOffset, width, height);
			int barWidth = Math
					.max(MIN_BAR_WIDTH,
							(int) Math.floor((float) width
									/ (float) mapHistory.size()));
			System.out.println("width = " + width + "; size = "
					+ mapHistory.size() + "; barWidth = " + barWidth);
			int maxValue = 0;
			for (Integer key : mapHistory.keySet()) {
				int value = mapHistory.get(key);
				maxValue = Math.max(maxValue, value);
			}
			int xPos = xOffset;
			for (Integer key : mapHistory.keySet()) {
				int value = mapHistory.get(key);
				int barHeight = Math.round(((float) value / (float) maxValue)
						* height);
				//g2d.setColor(new Color(key, key, key));
				int yPos = height + yOffset - barHeight;
				// Rectangle bar = new Rectangle(xPos, yPos, barWidth,
				// barHeight);
				Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth,
						barHeight);
				g2d.fill(bar);
				g2d.setColor(Color.DARK_GRAY);
				g2d.draw(bar);
				xPos += barWidth;
			}
			g2d.dispose();
		}
	}

	@Override
	public ATImage getImage() {
		return image;
	}

}
