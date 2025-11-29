package com.StasticObject.Spots;
import util.ImageLoader;

/*
 * PaintingFrame is a subclass that represents a frame object with an instruction (the question)
 * and two image state with or without a painting. 
 */

public class PaintingFrame extends Spots{
	private final String instruction = "Third digit: If you double the number of days in a weekend, then add the number of primary colors, what digit do you end up with?";
	public PaintingFrame(double xPos, double yPos, double sca) {
		super(xPos, yPos, sca);
		instructionVisible = true;
		img = ImageLoader.loadImage("assets/Frame.png");

	}
	@Override
	public String getInstruction() {
		return instruction;
	}
	
	@Override
	public void setImg(int state) {
		if (state == 0) {
			img = ImageLoader.loadImage("assets/Frame.png");
		} else if (state == 1) {
			img = ImageLoader.loadImage("assets/FrameWithPainting.png");
		}
	}
}
