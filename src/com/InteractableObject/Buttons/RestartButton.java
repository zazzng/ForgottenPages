package com.InteractableObject.Buttons;

import util.ImageLoader;

/*
 * The restart button extends StartButton implements Button. 
 * It inherits all behavior from startButoon class. It just changes the image to represent a restart button 
 * instead of a start button
 */
public class RestartButton extends StartButton implements Button{
	public RestartButton(double xPos, double yPos) {
		super(xPos, yPos);
		img = ImageLoader.loadImage("assets/RestartButton.png");
	}
}
