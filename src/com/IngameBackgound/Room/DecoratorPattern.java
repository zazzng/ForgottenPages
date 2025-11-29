package com.IngameBackgound.Room;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import util.ImageLoader;

/*
 * This is the decorator pattern class which is used to add new drawing/movement behavior 
 * to game components. This also use factory pattern to produce different products
 * as shown in then Component interface. 
 */
public class DecoratorPattern {
	/*
	 * Component interface. This is the base interface that both core objects
	 * (concrete component) and decorated objects must implement.
	 */

	public interface Component {
		void draw(Graphics2D g2d); //Factory Method (Kind of)
	}

	/*
	 * The concrete component is the original color background. It can change the opacity 
	 * to show the day and night.
	 */
	public static class ConcreteComponent implements Component {
		final double y, sca, x;
		protected BufferedImage img;
		private float opacity = 0f;
		private boolean increasing = true;
		private long lastUpdateTime = System.currentTimeMillis();

		public ConcreteComponent(double x, double y, double sca) {
			this.x = x;
			this.y = y;
			this.sca = sca;
			img = ImageLoader.loadImage("assets/BlueBackground.png");
		}

		private void opacity() {
			long now = System.currentTimeMillis();
			float delta = (now - lastUpdateTime) / 10000f;

			if (increasing) {
				opacity += delta;
				if (opacity >= 1f) {
					opacity = 1f;
					increasing = false;
				}
			} else {
				opacity -= delta;
				if (opacity <= 0f) {
					opacity = 0f;
					increasing = true;
				}
			}

			lastUpdateTime = now;
		}

		@Override
		public void draw(Graphics2D g2) {
			opacity();
			AffineTransform transform = g2.getTransform();
			g2.translate(x, y);
			g2.scale(sca, sca);
			
			Composite originalComposite = g2.getComposite();
	        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(opacity, 1f));
	        g2.setComposite(alpha);
			g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
			g2.setComposite(originalComposite);

			g2.setTransform(transform);
		}
	}

	/*
	 * The base decorator class is an abstract wrapper that holds a reference to
	 * another Component. Like an logic to wrap things around
	 */
	public static abstract class BaseDecorator implements Component {
		protected final Component inner;

		BaseDecorator(Component inner) {
			this.inner = inner;
		}
	}

}
