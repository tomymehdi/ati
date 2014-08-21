package edu.it.itba.utils;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageUtilsTest {

	@Test
	public void test() {
		BufferedImage image = ImageUtils.grayScale();
		assert(image.getClass().equals(BufferedImage.class));
		File file = new File("tests/greyScale.jpg");
		try {
			ImageIO.write(image, "JPEG", file);
		} catch (IOException e) {
			fail("Fail to generate or save the image.");
		}
	}

}
