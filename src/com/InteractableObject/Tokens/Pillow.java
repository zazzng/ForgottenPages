package com.InteractableObject.Tokens;
import com.StasticObject.Spots.Bed;
import com.StasticObject.Spots.Spots;

import util.ImageLoader;
/*
 * The Pillow class extends Tokens and represents a specific token type with an image and a method to 
 * check if hit with Bed spots which is its linked spot. 
 */
public class Pillow  extends Tokens{
	public Pillow (Spots spot, double xPos, double yPos, double scale) {
		super(spot, xPos, yPos, scale);
		img = ImageLoader.loadImage("assets/Pillow.png");
	}
	
	public boolean hit(Bed bed) {
		return Math.abs(getXPos() - bed.getXPos()) < 50 && 
	             Math.abs(getYPos() - bed.getYPos()) < 30;
	}
	
	@Override
	//Override because each of the token has different number
    public int getTokenCategory() {
        return 2; 
    }
}
