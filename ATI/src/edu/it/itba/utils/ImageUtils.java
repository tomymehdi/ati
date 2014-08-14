package edu.it.itba.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	public static Image scaleImage(Image image, int width, int height) {
		Image scaledImage;

		float iw = image.getWidth(null);
		float ih = image.getHeight(null);
		float pw = width;
		float ph = height;

		if (pw < iw || ph < ih) {
			if ((pw / ph) > (iw / ih)) {
				iw = -1;
				ih = ph;
			} else {
				iw = pw;
				ih = -1;
			}
			if (iw == 0) {
				iw = -1;
			}
			if (ih == 0) {
				ih = -1;
			}

			scaledImage = image.getScaledInstance(new Float(iw).intValue(),
					new Float(ih).intValue(), Image.SCALE_DEFAULT);

		} else {
			scaledImage = image;
		}

		return scaledImage;
	}
	
	@SuppressWarnings("resource")
	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[(int) file.length()];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new IOException();
		}
		is.close();
		return bytes;
	}
	
	public static BufferedImage load(File file, int width, int height)
			throws IOException {
		
		BufferedImage ret;
		byte[] data = getBytesFromFile(file);
		ret = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = ret.getRaster();
		int k = 0;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				raster.setSample(i, j, 0, data[k]);
				k = k + 1;
			}
		} 
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image.setRGB(i, j, ret.getRGB(i, j));
			}
		}
		return image;
	}
	
	public static Image loadImage(String file) {
		Image resp = null;
		try {
			resp =  ImageIO.read(new File(file));
		} catch (IOException e) {
			// TODO Show correct error
			e.printStackTrace();
		}
		return resp;
	}


}
