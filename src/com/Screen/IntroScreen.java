package com.Screen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import util.ImageLoader;

/*
 * IntroScreen class is a simple drawable object representing 
 * the intro screen background image with position and scale properties. 
 */
public class IntroScreen {
    private double xPos, yPos, scale;
    protected BufferedImage img;
    
    public IntroScreen(double x, double y, double sca) {
        xPos = x;
        yPos = y;
        scale = sca;
        img = ImageLoader.loadImage("assets/StartScreenBackground.png");
    }

    public void draw(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);
	}

}
