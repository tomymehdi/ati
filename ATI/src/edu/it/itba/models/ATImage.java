package edu.it.itba.models;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import edu.it.itba.enums.Bands;
import edu.it.itba.enums.ImageType;
import edu.it.itba.interfaces.Function;
import edu.it.itba.interfaces.FunctionImage;

public class ATImage {
	
	public Band R;
	public Band G;
	public Band B;
	private int rows;
	private int cols;
	private ImageType type;
	
	public ATImage(int rows, int cols, ImageType type) {
		this.rows = rows;
		this.cols = cols;
		this.type = type;
		R = new Band(rows,cols,Bands.R);
		G = new Band(rows,cols,Bands.G);
		B = new Band(rows,cols,Bands.B);
	}
	
	public ATImage(BufferedImage image, ImageType type) {
		this.rows = image.getHeight();
		this.cols = image.getWidth();
		this.type = type;
		
		R = new Band(rows,cols,Bands.R);
		G = new Band(rows,cols,Bands.G);
		B = new Band(rows,cols,Bands.B);
		
		Raster raster = image.getRaster();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				R.set(row, col, raster.getSample(row, col, 0));
				G.set(row, col, raster.getSample(row, col, 0));
				B.set(row, col, raster.getSample(row, col, 0));
			}
		}
	}
	
	public void applyFunction(Function function, Integer density){
		applyFunctionR(function, density);
		applyFunctionG(function, density);
		applyFunctionB(function, density);
	}
	
	public void applyFunction(FunctionImage function, Integer density){
		applyFunctionImageR(function, density);
		applyFunctionImageG(function, density);
		applyFunctionImageB(function, density);
	}

	private void applyFunctionImageB(FunctionImage function, Integer density) {
		if(density == null){
			density = 100;
		}
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				B.applyBand(function, row, col);
			}
		}
	}

	private void applyFunctionImageG(FunctionImage function, Integer density) {
		if(density == null){
			density = 100;
		}
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				G.applyBand(function, row, col);
			}
		}
		
	}

	private void applyFunctionImageR(FunctionImage function, Integer density) {
		if(density == null){
			density = 100;
		}
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				R.applyBand(function, row, col);
			}
		}
		
	}

	private void applyFunctionB(Function function, Integer density) {
		if(density == null){
			density = 100;
		}
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				B.apply(function, row, col);
			}
		}
	}

	private void applyFunctionG(Function function, Integer density) {
		if(density == null){
			density = 100;
		} 
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				G.apply(function, row, col);
			}
		}
	}

	private void applyFunctionR(Function function, Integer density) {
		if(density == null){
			density = 100;
		} 
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				R.apply(function, row, col);
			}
		}
	}
	
	public BufferedImage getVisual(){
		int format = BufferedImage.TYPE_INT_RGB;
		if(type.equals(ImageType.GRAYSCALE)){
			format = BufferedImage.TYPE_BYTE_GRAY;
		} else if( type.equals(ImageType.BINARY)){
			format = BufferedImage.TYPE_BYTE_BINARY;
		} else if(type.equals(ImageType.RGB)){
			format = BufferedImage.TYPE_INT_RGB;
		} else if(type.equals(ImageType.HSV)){
			//TODO
		}
		BufferedImage resp = new BufferedImage(rows, cols, format);
		WritableRaster raster = resp.getRaster();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				raster.setSample(row,col,0,R.getValue(row,col));
				raster.setSample(row,col,1,G.getValue(row,col));
				raster.setSample(row,col,2,B.getValue(row,col));
			}
		}
		
		return resp;
	}

	public int getHeight() {
		return rows;
	}

	public int getWidth() {
		return cols;
	}

	public Band getBand(Bands band) {
		if(band.equals(Bands.R)){
			return R;
		} else if(band.equals(Bands.G)){
			return G;
		} else if(band.equals(Bands.B)){
			return B;
		}
		return null;
	}
}
