package com.IngameBackgound.Room;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import util.ImageLoader;

import com.IngameBackgound.Room.DecoratorPattern.BaseDecorator;
import com.IngameBackgound.Room.DecoratorPattern.Component;

/*
 * This is a concrete decorator that adds a wall shelf image on top of an existing Component
 * When draw() is called, it first delegates drawing to the wrapped object, then draws the wall shelf 
 * on top.
 */
public class WallShell extends BaseDecorator{
	private final double xPos, yPos, scale;
	protected BufferedImage img;
	
	public WallShell (Component inner, double xPos, double yPos, double scale){
		super(inner);
		this.xPos = xPos;
		this.yPos = yPos; 
		this.scale = scale;
		img = ImageLoader.loadImage("assets/WallShell.png");
	}
	
	public void draw(Graphics2D g2) {
		inner.draw(g2);
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);
	}
}
