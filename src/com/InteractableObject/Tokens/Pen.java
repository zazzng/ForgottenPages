package com.InteractableObject.Tokens;
import com.StasticObject.Spots.PenCase;
import com.StasticObject.Spots.Spots;

import util.ImageLoader;

/*
 * The Pen class extends Tokens and represents a specific token type with an image and a method to 
 * check if hit with Pencase spots which is its linked spot. 
 */
public class Pen extends Tokens{
	public Pen(Spots spot, double xPos, double yPos, double scale) {
		super(spot,xPos, yPos, scale);
		img = ImageLoader.loadImage("assets/Pen.png");

	}
	
	public boolean hit(PenCase penCase) {
		return Math.abs(getXPos() - penCase.getXPos()) < 20 && 
	               Math.abs(getYPos() - penCase.getYPos()) < 20;
	}
	
	@Override
	//Override because each of the token has different number
    public int getTokenCategory() {
        return 1; 
    }
}
