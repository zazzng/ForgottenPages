package util;

import java.awt.Color;

import processing.core.PVector;

public class Util {

	public static double random(int min, int max) {
		return Math.random()*(max-min)+min;
	}
	
	public static double random(int max) {
		return Math.random()*max;
	}
	
	public static Color randomColor() {
		int r = (int) random(255);
		int g = (int) random(255);
		int b = (int) random(255);
		
		return new Color(r,g,b);
	}
	
	public static PVector randomPVector(int maxX, int maxY) {		
		return new PVector((float)random(maxX), (float)random(maxY));	
	}
	
	public static PVector randomPVector(float maxSpeed) {
		return PVector.random2D().mult(maxSpeed);
	}
}