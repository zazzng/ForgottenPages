package com.StasticObject.Spots;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import processing.core.PVector;

/*
 * Spots abstract class represents a drawable location or interactive spot on 
 * the screen with position, scale, an image.
 */
public abstract class Spots{
	protected double xPos, yPos;
	protected double sca;
	protected Area outline;
	protected boolean instructionVisible;
	protected BufferedImage img;
	
	protected boolean isActive = false;
	
	public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
	protected Spots(double xPos, double yPos, double sca) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.sca = sca;
	}

	public void setPos(double x, double y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public PVector getPos() {
		return new PVector((float)xPos, (float)yPos);
	}

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

	public boolean checkMouseHit(double x, double y) {
		if(img == null) {
			return false;
		}

		return( (x > (xPos - ((double) img.getWidth()) / 2 * sca) && x < (xPos + ((double) img.getWidth()) / 2 * sca)
				&& y > (yPos - ((double) img.getHeight()) / 2 * sca)
				&& y < (yPos + ((double) img.getHeight()) / 2 * sca)));
			
	
	}

	public void showInstruction() {
		instructionVisible = true;
	}
	
	public void hideInstruction() {
		instructionVisible = false;
	}
	
	//this boolean is to track whether its instruction should be shown
	public boolean isInstructionVisible() {
		return instructionVisible;
	}

	public abstract String getInstruction() ;
	public  void setImg(int state) {
		
	}
	public void draw(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(sca, sca);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);
	}
}