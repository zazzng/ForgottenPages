package com.InteractableObject.Tokens;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import com.StasticObject.Spots.Spots;
/*
 * Tokens is an abstract class which represents a visual game token with position, scale, and 
 * an associated image.
 * It also keeps track of a logical connection to a Spots objects. 
 */
public abstract class Tokens {
	protected double xPos;
	protected double yPos;
	protected double scale;
	protected Area outline;
	protected BufferedImage img;
	private Spots spot;

	protected Tokens(Spots spot, double x, double y, double scale) {
		this.spot = spot;
        this.xPos = x;
        this.yPos = y;
        this.scale = scale;
	}
	
	/*
	 * Abstract getTokenCategory() so subclasses must specify this type.
	 * in case that when the mouse drags one token, the other token will not be collected by the mouse. 
	 * So this abstract method will help to assign a specific number to the token. 
	 * Later on in the main game panel, when the mouse drags one token, it will check 
	 * for that token's category number and will only keep that specific token that has this number.
	 */
	public abstract int getTokenCategory();

	public double getXPos() {
		return xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	protected BufferedImage getImg() {
		return img;
	}

	public void setPos(double x, double y) {
		this.xPos = x;
		this.yPos = y;
	}

	protected double getScale() {
		return scale;
	}
/*
 * Check if this token is linked to the given spot. 
 * returns the spot if matched, otherwise returns null
 * This is used to find a token's relationship to spot
 */
    public Spots getSpot(Spots linkedSpot) {
    	if (this.spot == linkedSpot) {
            return linkedSpot;
        } else {
            return null;
        }
    }

	public void draw(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);
	}

	public boolean checkMouseHit(double x, double y) {
		if(img == null) {
			return false;
		}

		return( (x > (xPos - ((double) img.getWidth()) / 2 * scale) && x < (xPos + ((double) img.getWidth()) / 2 * scale)
				&& y > (yPos - ((double) img.getHeight()) / 2 * scale)
				&& y < (yPos + ((double) img.getHeight()) / 2 * scale)));
	}

}
