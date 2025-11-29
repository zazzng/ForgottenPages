package com.Screen;

import util.ImageLoader;
/*
 * IntroductionScreen class is a simple drawable object representing 
 * the introduction (how to play) screen background image with position and scale properties. 
 */
public class IntroductionScreen extends IntroScreen{
	public IntroductionScreen(double x , double y, double sca) {
		super(x, y, sca);
		img = ImageLoader.loadImage("assets/HowToPlay.png");		
	}
}
