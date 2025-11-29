package com.StasticObject.Spots;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import Main.GamePanel;
/*
 * Sketch class extends Diary and lets the user type and 
 * display multi-line text input within a bounded rectangle.
 */
public class Sketch extends Diary {
	private String instruction = "Your Sketch";
	private Rectangle2D.Double rectShapeSketch;

	public Sketch(double xPos, double yPos, double sca, GamePanel panel) {
		super(xPos, yPos, sca, panel);
		setShapeAttributes();
		setOutline();
	}

	@Override
	public void toggleInput() {
		showInput = !showInput;
	}

	@Override
	public void addCharacter(char c) {
		if (showInput) {
			if (c == '\n') {
				int currentLineCount = userInput.split("\n").length;
				if (currentLineCount < 6) {
					userInput += c;
				}
			} else {
				userInput += c;
			}
		}
	}

	@Override
	public String getInstruction() {
		return instruction;
	}

	protected void setShapeAttributes() {
		rectShapeSketch = new Rectangle2D.Double();
		rectShapeSketch.setFrame(-50, -50, 300, 150);
	}

	protected void setOutline() {
		outline = new Area();
		outline.add(new Area(rectShapeSketch));
	}
	
	/**
     * Draw the sketch input box with text wrapping and line limits.
     * Only draws when input mode is active (showInput == true).
     */
	public void draw(Graphics2D g2d) {
		if (showInput) {
			int maxLineWidth = 300; // Max width for a line of text
			AffineTransform old = g2d.getTransform();
			g2d.translate(xPos, yPos);
			g2d.scale(sca, sca);
			
			g2d.setColor(new Color(245, 213, 220,255));
			
			g2d.fill(rectShapeSketch);
			
			//Set font and color for the text
			Font f = new Font("Courier", Font.PLAIN, 12);
			Color textColor = new Color(98, 52, 62);
			g2d.setColor(textColor);
			g2d.setFont(f);
			
			 // Starting drawing coordinates inside the rectangle
			int drawX = -10;
			int drawY = -10;
			// Tracks how many lines drawn
			int lineCount = 0;
			// Limit lines to 6
			int maxLines = 6;

			FontMetrics metrics = g2d.getFontMetrics(f);

			String[] words = userInput.split(" "); // split word by word
			StringBuilder currentLine = new StringBuilder();
			int lineHeight = metrics.getHeight();
			
			// Loop through each word to handle wrapping and newlines
			for (String word : words) {
				// this check if the input contain return to create a new line, inserted by
				// player
				if (word.contains("\n")) {
					String[] parts = word.split("\n");
					for (int i = 0; i < parts.length; i++) {
						String part = parts[i];
						String testLine = currentLine + part + " ";
						int testLineWidth = metrics.stringWidth(testLine);

						if (testLineWidth <= maxLineWidth) {
							currentLine.append(part);
						} else {
							if (lineCount >= maxLines)
								break;
							g2d.drawString(currentLine.toString(), drawX, drawY);
							drawY += lineHeight;
							lineCount++;
							currentLine = new StringBuilder(part);
						}

						if (i < parts.length - 1) {
							if (lineCount >= maxLines)
								break;
							g2d.drawString(currentLine.toString(), drawX, drawY);
							drawY += lineHeight;
							lineCount++;
							currentLine = new StringBuilder();
						}
					}
					currentLine.append(" ");
				} else {
					String testLine = currentLine + word + " ";
					int testLineWidth = metrics.stringWidth(testLine);

					if (testLineWidth <= maxLineWidth) {
						currentLine.append(word).append(" ");
					} else {
						if (lineCount >= maxLines)
							break;
						g2d.drawString(currentLine.toString(), drawX, drawY);
						drawY += lineHeight;
						lineCount++;
						currentLine = new StringBuilder(word + " ");
					}
				}

				if (lineCount >= maxLines) {
					break;
				}
			}

			g2d.drawString(currentLine.toString(), drawX, drawY);
			g2d.setTransform(old);
		}

	}
}
