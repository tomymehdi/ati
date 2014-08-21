package edu.it.itba.utils;

import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageUtilsTest {

	@Test
	public void testGrayScale() {
		BufferedImage image = ImageUtils.grayScale();
		assert(image.getClass().equals(BufferedImage.class));
		File file = new File("tests/greyScale.jpg");
		try {
			ImageIO.write(image, "JPEG", file);
		} catch (IOException e) {
			fail("Fail to generate or save the image.");
		}
	}
	
	@Test
	public void testColorScale() {
		BufferedImage image = null;
			image = ImageUtils.colorScale();
		try {
			ImageIO.write(image, "JPEG", new File("tests/cs.jpg"));
		} catch (IOException e) {
			fail("Fail to generate or save the image.");
		}
	}
	
	@Test
	public void testLoadRaw(){
		BufferedImage image = null;
		try {
			image = ImageUtils.load(new File("tests/GIRL.RAW"), new Dimension(389,164));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ImageIO.write(image, "JPEG", new File("tests/gt.jpg"));
		} catch (IOException e) {
			fail("Fail to generate or save the image.");
		}
	}
	
	@Test
	public void testLoadJpg() {
		BufferedImage image = null;
		try {
			image = ImageUtils.load(new File("tests/Test.jpg"), null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ImageIO.write(image, "JPEG", new File("tests/Test.jpg.jpg"));
		} catch (IOException e) {
			fail("Fail to generate or save the image.");
		}
	}

	@Test
	public void testLoadGif() {
		BufferedImage image = null;
		try {
			image = ImageUtils.load(new File("tests/wdg3.gif"), null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ImageIO.write(image, "JPEG", new File("tests/wdg3.gif.gif"));
		} catch (IOException e) {
			fail("Fail to generate or save the image.");
		}
	}
	
	@Test
	public void testLoadTif() {
		BufferedImage image = null;
		try {
			image = ImageUtils.load(new File("tests/PLAQS1.TIF"), null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ImageIO.write(image, "JPEG", new File("tests/PLAQS1.TIF"));
		} catch (IOException e) {
			e.printStackTrace();
			fail("Fail to generate or save the image.");
		}
	}
	
	
}
