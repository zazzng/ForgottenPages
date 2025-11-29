package com.StasticObject.Spots;

import util.ImageLoader;

/*
 * Drawer is a subclass that represents a Drawer object with an instruction (the question)
 * and two image state with or without a key. 
 */
public class Drawer extends Spots {
	private final String instruction = "The last digit: Looking at this Sierpiński triangle, how many levels of recursive division have been applied to form the pattern?";

	public Drawer(double xPos, double yPos, double sca) {
		super(xPos, yPos, sca);
		instructionVisible = true;
		img = ImageLoader.loadImage("assets/Drawer.png");
	}

	@Override
	public String getInstruction() {
		return instruction;
	}

	@Override
	public void setImg(int state) {
		if (state == 0) {
			img = ImageLoader.loadImage("assets/Drawer.png");
		} else if (state == 1) {
			img = ImageLoader.loadImage("assets/OpenedDrawer.png");
		}
	}

}
