package com.StasticObject.Spots;
import util.ImageLoader;

/*
 * Bed is a subclass that represents a bed object with an instruction (the question)
 * and two image state with or without a pillow. 
 */
public class Bed extends Spots {
	private final String instruction = "The second digit: If you roll a standard six-sided die and a standard four-sided die, then add the results, what is the smallest possible sum you could roll?";

	public Bed(double xPos, double yPos, double sca) {
		super(xPos, yPos, sca);
		instructionVisible = true;
		img = ImageLoader.loadImage("assets/Bed.png");

	}

	@Override
	public String getInstruction() {
		return instruction;
	}
	@Override
	public void setImg(int state) {
		if (state == 0) {
			img = ImageLoader.loadImage("assets/Bed.png");
		} else if (state == 1) {
			img = ImageLoader.loadImage("assets/BedWithPillow.png");
		}
	}
}
