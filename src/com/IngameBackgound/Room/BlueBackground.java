package com.IngameBackgound.Room;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import com.IngameBackgound.Room.DecoratorPattern.BaseDecorator;
import com.IngameBackgound.Room.DecoratorPattern.Component;

import util.ImageLoader;
/*
 * The blue background is actually the room. This is one of the concrete decorators
 */
public class BlueBackground extends BaseDecorator{
	private double xPos, yPos, scale;
	protected BufferedImage img;
	public BlueBackground(Component inner, double xPos, double yPos, double scale) {
		super(inner);
		this.xPos = xPos;
		this.yPos = yPos;
		this.scale = scale;
		img = ImageLoader.loadImage("assets/RoomBackground.png");
	}
	
	@Override
	public void draw(Graphics2D g2) {
		inner.draw(g2);
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);	
	}
}
