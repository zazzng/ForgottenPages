package com.Recursion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import javax.swing.JPanel;

/*
 * This is the cloud shape drawn using Perlin noise.
 * This is used as the memory blurr. Every time when one token is returned back to the spot,
 * the memory blurr will be collapsed and at the end, the diary will be revealed for the players enter the password
 */
public class CloudPanel extends JPanel {
	private final PerlinNoise noise = new PerlinNoise(new Random().nextInt());
	private float xStart = new Random().nextFloat() * 10f;
	private float yStart = new Random().nextFloat() * 10f;
	private float xSeed, ySeed;
	private int width, height;
	private float xPos, yPos;

	private final float step = 0.2f;
	private final int spacing = 6;
	private final float ampRot = 680f;
	private final float diameterMax = 43f;
	protected boolean animated = true;
	protected boolean instructionVisible;
	private String instruction = "Get all the tokens to unlock to diary.";

	public CloudPanel(float x, float y) {
		width = 100;
		height = 100;
		xPos = x;
		yPos = y;
		setLayout(null);
	}

	public void onTick() {
		if (animated) {
			xStart += 0.001f;
			yStart += 0.001f;
		}
	}
	
	public String getInstruction() {
		return instruction;
	}
	
	public void showInstruction() {
		instructionVisible = true;
	}
	
	public void hideInstruction() {
		instructionVisible = false;
	}
	
	public boolean isInstructionVisible() {
		return instructionVisible;
	}

	public void draw(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		xSeed = xStart;
		ySeed = yStart;

		for (int y = 0; y <= height; y += spacing) {
			xSeed = xStart;
			for (int x = 0; x <= width; x += spacing) {
				xSeed += step;
				float nf = (float) noise.noise2D(xSeed, ySeed);
				AffineTransform at1 = g2.getTransform();
				g2.translate(x, y);
				g2.rotate(Math.toRadians(nf * ampRot));
				float dia = nf * diameterMax;
				g2.setColor(new Color(44, 73, 120, 100));
				g2.fill(new Ellipse2D.Float(-dia / 2, -dia / 4, dia, dia / 2));
				g2.setTransform(at1);
				xSeed += step;
			}
			ySeed += step;
		}
		g2.setTransform(at);
	}
	
	public void setWidth(int x) {
		this.width = x;
	}

	public void setHeight(int y) {
		this.height = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean checkMouseHit(double x, double y) {
		return x >= xPos && x <= xPos + width &&
		           y >= yPos && y <= yPos + height;
	}

	class PerlinNoise {
		private final int[] p = new int[512];

		PerlinNoise(int seed) {
			int[] permutation = new int[256];
			for (int i = 0; i < 256; i++)
				permutation[i] = i;
			Random r = new Random(seed);
			for (int i = 255; i > 0; i--) {
				int j = r.nextInt(i + 1);
				int tmp = permutation[i];
				permutation[i] = permutation[j];
				permutation[j] = tmp;
			}
			for (int i = 0; i < 512; i++)
				p[i] = permutation[i & 255];
		}

		public double noise1D(double x) {
			return noise3D(x, 0, 0);
		}

		public double noise2D(double x, double y) {
			return noise3D(x, y, 0);
		}

		public double noise3D(double x, double y, double z) {
			int X = (int) Math.floor(x) & 255;
			int Y = (int) Math.floor(y) & 255;
			int Z = (int) Math.floor(z) & 255;
			x -= Math.floor(x);
			y -= Math.floor(y);
			z -= Math.floor(z);
			double u = fade(x);
			double v = fade(y);
			double w = fade(z);
			int A = p[X] + Y, AA = p[A] + Z, AB = p[A + 1] + Z;
			int B = p[X + 1] + Y, BA = p[B] + Z, BB = p[B + 1] + Z;
			double res = lerp(w,
					lerp(v, lerp(u, grad(p[AA], x, y, z), grad(p[BA], x - 1, y, z)),
							lerp(u, grad(p[AB], x, y - 1, z), grad(p[BB], x - 1, y - 1, z))),
					lerp(v, lerp(u, grad(p[AA + 1], x, y, z - 1), grad(p[BA + 1], x - 1, y, z - 1)),
							lerp(u, grad(p[AB + 1], x, y - 1, z - 1), grad(p[BB + 1], x - 1, y - 1, z - 1))));
			return (res + 1.0) / 2.0;
		}

		private double fade(double t) {
			return t * t * t * (t * (t * 6 - 15) + 10);
		}

		private double lerp(double t, double a, double b) {
			return a + t * (b - a);
		}

		private double grad(int hash, double x, double y, double z) {
			int h = hash & 15;
			double u = h < 8 ? x : y;
			double v = h < 4 ? y : (h == 12 || h == 14 ? x : z);
			return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
		}
	}

}
