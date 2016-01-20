package edu.it.itba.app;

import edu.it.itba.swing.frames.ATIJFrameImpl;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class App {

//	public static void main(String[] args) {
//		// Schedule a job for the event-dispatching thread:
//		// creating and showing this application's GUI.
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				new ATIJFrameImpl();
//			}
//		});
//	}
	
	public static void main(String[] args) {
        File imageFile = new File("/Users/tomymehdi/itba/ati/ATI/resources/tesseract4jtests/tesseract4j-test2.jpg");
        Tesseract instance = Tesseract.getInstance();  // JNA Interface Mapping
//         Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
