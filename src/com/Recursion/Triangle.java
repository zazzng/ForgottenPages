package com.Recursion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

/*
 * This is the triangle shape drawn using fractals created using recursion. 
 * The triangle is used as one of the question to unlock to diary
 */
public class Triangle extends JPanel {
	private float xPos, yPos;

	public Triangle(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		setLayout(null);
	}

	public void draw(Graphics2D g2, int d) {
		
		double size = 100;
		Point2D a = new Point2D.Double(0, -size) ;
		Point2D b = new Point2D.Double(-size, size) ;
		Point2D c = new Point2D.Double(size, size) ;
		AffineTransform originalTransform = g2.getTransform();
		g2.translate(xPos, yPos); 
		Color color = new Color(44, 73, 120);
		g2.setColor(color);

		drawTriangleRcursive(g2, d, a, b, c);

		g2.setTransform(originalTransform);
	}

	private void drawTriangleRcursive(Graphics2D g2, int d, Point2D a, Point2D b, Point2D c) {

		if (d == 0) {
			Path2D path = new Path2D.Double();
			path.moveTo(a.getX(), a.getY());
			path.lineTo(b.getX(), b.getY());
			path.lineTo(c.getX(), c.getY());
			path.closePath();
			g2.fill(path);
			return;
		}
		Point2D ab = mid(a, b);
		Point2D bc = mid(b, c);
		Point2D ca = mid(c, a);
		drawTriangleRcursive(g2, d - 1, a, ab, ca);
		drawTriangleRcursive(g2, d - 1, ab, b, bc);
		drawTriangleRcursive(g2, d - 1, ca, bc, c);

	}

	private Point2D mid(Point2D p, Point2D q) {
		return new Point2D.Double((p.getX() + q.getX()) / 2.0, (p.getY() + q.getY()) / 2.0);
	}

}
