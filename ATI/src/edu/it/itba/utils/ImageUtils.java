package edu.it.itba.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

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

	public static BufferedImage load(File file, Dimension dim)
			throws IOException {

		BufferedImage ret;
		String extension = file.getName()
				.substring(file.getName().length() - 3).toLowerCase();
		if (!extension.equals("raw")) {
			ret = ImageIO.read(file);
			return ret;
		}

		byte[] data = getBytesFromFile(file);
		ret = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = ret.getRaster();
		int k = 0;
		for (int j = 0; j < dim.height; j++) {
			for (int i = 0; i < dim.width; i++) {
				raster.setSample(i, j, 0, data[k]);
				k = k + 1;
			}
		}
		BufferedImage image = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < dim.width; i++) {
			for (int j = 0; j < dim.height; j++) {
				image.setRGB(i, j, ret.getRGB(i, j));
			}
		}
		return image;
	}

	public static BufferedImage grayScale() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				raster.setSample(col, row, 0, col / 2);
			}
		}
		return image;
	}

	public static BufferedImage getSubImage(BufferedImage image, int x, int y,
			int widht, int height) {
		return image.getSubimage(x, y, widht, height);
	}

	public static void modifyPixel(BufferedImage image, int x, int y, int[] s) {
		image.getRaster().setPixel(x, y, s);
	}

	public static BufferedImage colorScale() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_INT_RGB);
		int i = 0;
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				image.setRGB(row, col, 0x000001 * 1 / 1024 * i + 0x000100 * 1
						/ 1024 * i + 0x010000 * 1 / 1024 * i);
				i++;
			}
		}
		return image;
	}
	
	public static Color hsvToRgb(float hue, float saturation, float value) {
	    int h = (int)(hue * 6);
	    float f = hue * 6 - h;
	    float p = value * (1 - saturation);
	    float q = value * (1 - f * saturation);
	    float t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return new Color(value, t, p);
	      case 1: return new Color(q, value, p);
	      case 2: return new Color(p, value, t);
	      case 3: return new Color(p, q, value);
	      case 4: return new Color(t, p, value);
	      case 5: return new Color(value, p, q);
	      default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
	    }
	}
	
	public static Color rgbToHsv(int R, int G, int B) {
		float hsv[] = new float[3];
		Color.RGBtoHSB(R,G,B, hsv);
		return new Color(hsv[0], hsv[1], hsv[2]);
	}

	public static BufferedImage blankSquare() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
				if((col>=100 && col <=412) && (row>=100 && row <= 412)){
					raster.setSample(col, row, 0, 255);
				} else {
					raster.setSample(col, row, 0, 0);
				}
			}
		}
		return image;
	}
	
	/*
	 * Bresenham algorithm
	 * 
	 * First paint if full black and draw the blank circle and paint the interior.
	 */
	public static BufferedImage blankCircle() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
					raster.setSample(col, row, 0, 0);
			}
		}
		
		int x0 = 255, y0 = 255; // Circle center
		int x = 100, y = 0;
		int radiusError = 1-x;
		
		while(x >= y) {
			for(int i = 0; i<x ; i++){
				raster.setSample( i + x0, y + y0, 0, 255);
				raster.setSample(-i + x0, y + y0, 0, 255);
				raster.setSample( i + x0, -y + y0, 0, 255);
				raster.setSample(-i + x0, -y + y0, 0, 255);
				
				raster.setSample( y + x0, i + y0, 0, 255);
				raster.setSample(-y + x0, i + y0, 0, 255);
				raster.setSample( y + x0, -i + y0, 0, 255);
				raster.setSample(-y + x0, -i + y0, 0, 255);
			}
		    y++;
		    if (radiusError<0) {
		      radiusError += 2 * y + 1;
		    } else {
		      x--;
		      radiusError += 2 * (y - x + 1);
		    }
		}
		return image;
	}
	/*
	 * Paint circle radius 100 after 99 after 98 ... 0;
	 * Bad image
	 */
	
	public static BufferedImage blankCircle2() {
		BufferedImage image = new BufferedImage(512, 512,
				BufferedImage.TYPE_BYTE_GRAY);

		WritableRaster raster = image.getRaster();
		for (int row = 0; row < 512; row++) {
			for (int col = 0; col < 512; col++) {
					raster.setSample(col, row, 0, 0);
			}
		}
		
		int x0 = 255, y0 = 255; // Circle center
		int x, y;
		int radiusError;
		int i = 100;
		
		while(i >= 0){
			x = i;
			radiusError = 1-x;
			y = 0;
			while(x >= y) {
				raster.setSample( x + x0, y + y0, 0, 255);
				raster.setSample(-x + x0, y + y0, 0, 255);
	
				raster.setSample( x + x0, -y + y0, 0, 255);
				raster.setSample(-x + x0, -y + y0, 0, 255);
				
				raster.setSample( y + x0, x + y0, 0, 255);
				raster.setSample(-y + x0, x + y0, 0, 255);
				
				raster.setSample( y + x0, -x + y0, 0, 255);
				raster.setSample(-y + x0, -x + y0, 0, 255);
			    y++;
			    if (radiusError<0) {
			      radiusError += 2 * y + 1;
			    } else {
			      x--;
			      radiusError += 2 * (y - x + 1);
			    }
			}
			i--;
			y++;
		}
		return image;
	}
}
