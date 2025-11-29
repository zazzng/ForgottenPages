package com.StasticObject.Spots;
import util.ImageLoader;

/*
 * PenCase is a subclass that represents a pen case object with an instruction (the question)
 * and two image state with or without a pen. 
 */
public class PenCase extends Spots{
	private final String instruction = "The first digit of the password is positive x. Knowing that: x + x^2 - 6 = 0";
	public PenCase(double xPos, double yPos, double sca) {
		super(xPos, yPos, sca);
		instructionVisible = true;
		img = ImageLoader.loadImage("assets/PenCase.png");
	}
	@Override
	public String getInstruction() {
		return instruction;
	}
	
	@Override
	public void setImg(int state) {
		if (state == 0) {
			img = ImageLoader.loadImage("assets/PenCase.png");
		} else if (state == 1) {
			img = ImageLoader.loadImage("assets/PenCaseWithPen.png");
		}
	}
}
