package de.amr.samples.marbletoy.entities;

import static de.amr.easy.game.assets.Assets.image;
import static de.amr.easy.game.assets.Assets.scaledImage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.entity.Entity;
import de.amr.easy.game.view.View;

public class Marble extends Entity implements Lifecycle, View {

	public final BufferedImage image;

	public Marble(int size) {
		image = scaledImage(image("marble.png"), size, size);
		tf.setWidth(size);
		tf.setHeight(size);
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		tf.move();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) tf.getX(), (int) tf.getY(), null);
	}
}