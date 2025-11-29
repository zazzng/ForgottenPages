package com.Screen;

import util.ImageLoader;
/*
 * EndScreen class is a simple drawable object representing 
 * the end screen background image with position and scale properties. 
 */
public class EndScreen extends IntroScreen{
	public EndScreen(double x , double y, double sca) {
		super(x, y, sca);
		img = ImageLoader.loadImage("assets/EndScreen.png");		
	}
}
