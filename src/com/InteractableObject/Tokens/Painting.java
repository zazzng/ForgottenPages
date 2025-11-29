package com.InteractableObject.Tokens;
import com.StasticObject.Spots.PaintingFrame;
import com.StasticObject.Spots.Spots;

import util.ImageLoader;
/*
 * The Painting class extends Tokens and represents a specific token type with an image and a method to 
 * check if hit with PaintingFrame spots which is its linked spot. 
 */
public class Painting extends Tokens{
	public Painting (Spots spot, double xPos, double yPos, double scale) {
		super(spot, xPos, yPos,scale);
		img = ImageLoader.loadImage("assets/MissingPainting.png");

	}
	
	public boolean hit(PaintingFrame pframe) {
		return Math.abs(getXPos() - pframe.getXPos()) < 50 && 
	             Math.abs(getYPos() - pframe.getYPos()) < 50;
	}
	
	@Override
	//Override because each of the token has different number
    public int getTokenCategory() {
        return 3; 
    }
}
