package com.InteractableObject.Buttons;

import util.ImageLoader;

/*
 * The back button extends StartButton implements Button. 
 * It inherits all behavior from startButoon class. It just changes the image to represent a back button 
 * instead of a start button
 */
public class BackButton extends StartButton implements Button{
	public BackButton(double xPos, double yPos) {
		super(xPos, yPos);
		img = ImageLoader.loadImage("assets/BackButton.png");
	}
}
