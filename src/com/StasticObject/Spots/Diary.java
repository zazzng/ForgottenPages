package com.StasticObject.Spots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import Main.GamePanel;
import util.ImageLoader;
/*
 * This class is a specialized Spots subclass that acts like a locked diary requiring a 4-digit password input. 
 * It handles user input, open/closed states, 
 * and rendering the diary along with user input text when needed.
 */

public class Diary extends Spots {
	private String instruction = "Enter 4 digits in the order. Good Luck!";
	private Ellipse2D.Double circle = new Ellipse2D.Double();
	private Rectangle2D.Double frameForPasswordInput = new Rectangle2D.Double();
	private boolean isOpen = false;
	private String correctPassword = "2275";
	public String userInput = "";
	protected boolean showInput = false;
	private GamePanel panel;

	// protected BufferedImage img;
	public Diary(double xPos, double yPos, double sca, GamePanel panel) {
		super(xPos, yPos, sca);
		instructionVisible = false;
		this.panel = panel;
		img = ImageLoader.loadImage("assets/Diary.png");
		setShapeAttributes();
	}

//Toggle whether the user is currently typing input for the diary
	public void toggleInput() {
		showInput = !showInput;
		userInput = ""; // Clear input when toggling
	}

	public void addCharacter(char c) {
		if (showInput && isOpen == false) {
			userInput += c;
		}
	}

	// Check if user is currently typing input
	public boolean isTyping() {
		return showInput;
	}
	
	public void closeInput() {
        showInput = false;
    }

	// Submit the input and check if it matches the correct password
	public void submit() {
		if (userInput.equals(correctPassword)) {
			isOpen = true;
			showInput = false;
		} else {
			userInput = "";
		}
	}

	// Remove the last character from the input (e.g., backspace)
	public void removeLastCharacter() {
		if (!userInput.isEmpty()) {
			userInput = userInput.substring(0, userInput.length() - 1);
		}
	}

	public boolean getWinningState() {
		return isOpen;
	}

	@Override
	public String getInstruction() {
		if(panel.getUnlockedToken() == 4) {
			return instruction;
		} else {
			return null;
		}
		
	}

	protected void setShapeAttributes() {
		frameForPasswordInput.setFrame(-50, -25, 200, 50);
	}

	protected void setOutline() {
		outline = new Area();
		outline.add(new Area(circle));
	}

	public void draw(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(sca, sca);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);

		if (showInput && !isOpen) {
			AffineTransform old = g2.getTransform();
			g2.translate(xPos, yPos - 100);
			g2.setColor(new Color(245, 213, 220, 255));
			g2.fill(frameForPasswordInput);
			g2.setColor(new Color(98, 52, 62));
			g2.drawString("Enter: " + userInput, (int) 0, (int) 0);			
			g2.setTransform(old);		
		}
	}

}
