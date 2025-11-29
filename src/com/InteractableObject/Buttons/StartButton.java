package com.InteractableObject.Buttons;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import util.ImageLoader;
/*
 * The StartButton class implements Button interface. This class creates the start button at the 
 * intro screen. When clicks, the main game will appears
 */
public class StartButton implements Button{

	protected double xPos;
	protected double yPos;
	protected double sca;
	protected BufferedImage img;

	public StartButton(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		sca = 0.25;
		img = ImageLoader.loadImage("assets/StartButton.png");
	}

	public void draw(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(sca, sca);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);
	}

	public boolean clicked(double x, double y) {
		boolean clicked = false;

		if (x > (xPos - ((double) img.getWidth()) / 2 * sca) && x < (xPos + ((double) img.getWidth()) / 2 * sca)
				&& y > (yPos - ((double) img.getHeight()) / 2 * sca)
				&& y < (yPos + ((double) img.getHeight()) / 2 * sca))
			clicked = true;

		return clicked;
	}

}
