package com.InteractableObject.Tokens;
import com.StasticObject.Spots.Drawer;
import com.StasticObject.Spots.Spots;
import util.ImageLoader;

/*
 * The Key class extends Tokens and represents a specific token type with an image and a method to 
 * check if hit with drawer spots which is its linked spot. 
 */
public class Key extends Tokens{
	public Key (Spots spot, double xPos, double yPos, double scale) {
		super(spot, xPos, yPos, scale);
		img = ImageLoader.loadImage("assets/Key.png");

	}
	
	public boolean hit(Drawer drawer) {
		return Math.abs(getXPos() - drawer.getXPos()) < 50 && 
	             Math.abs(getYPos() - drawer.getYPos()) < 30;
	}
	
	@Override
	//Override because each of the token has different number
    public int getTokenCategory() {
        return 4; 
    }
}
