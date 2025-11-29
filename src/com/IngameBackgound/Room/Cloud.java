package com.IngameBackgound.Room;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.IngameBackgound.Room.DecoratorPattern.BaseDecorator;
import com.IngameBackgound.Room.DecoratorPattern.Component;

import Main.GameApp;
import util.ImageLoader;

/*
 * This is a concrete decorator that adds a cloud image on top of an existing Component
 * When draw() is called, it first delegates drawing to the wrapped object, then draws the cloud 
 * on top. 
 * 
 * The cloud can also constantly move horizontally from 0 - WIDTH
 */
public class Cloud extends BaseDecorator{
	private  double xPos, yPos, scale;
	protected BufferedImage img;
	private double speed;
	
	public Cloud (Component inner, double xPos, double yPos, double scale, double speed){
		super(inner);
		this.xPos = xPos;
		this.yPos = yPos; 
		this.scale = scale;
		this.speed = speed;
		img = ImageLoader.loadImage("assets/Cloud.png");
	}
	
	public void draw(Graphics2D g2) {
		inner.draw(g2);
//		move();	
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);
		
	}
    public void move() {
        wallDetection();
        xPos += speed; 
        
	}
	
	public void wallDetection() {
		if((xPos > GameApp.WIDTH - img.getWidth()/2 * scale)) {
			xPos = 0;
		}
	}

}
