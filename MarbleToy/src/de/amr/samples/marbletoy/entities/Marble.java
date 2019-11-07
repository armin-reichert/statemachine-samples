package de.amr.samples.marbletoy.entities;

import de.amr.easy.game.entity.Entity;
import de.amr.easy.game.ui.sprites.Sprite;

public class Marble extends Entity {

	public Marble(int size) {
		sprites.set("s_marble", Sprite.ofAssets("marble.png").scale(size));
		sprites.select("s_marble");
		tf.setWidth(size);
		tf.setHeight(size);
	}

	@Override
	public void update() {
		tf.move();
	}
}