package edu.it.itba.app;

import edu.it.itba.swing.frames.ATIJFrameImpl;

public class App {

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ATIJFrameImpl();
			}
		});
	}
}
