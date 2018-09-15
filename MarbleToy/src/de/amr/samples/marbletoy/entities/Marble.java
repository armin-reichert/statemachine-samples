package de.amr.samples.marbletoy.entities;

import de.amr.easy.game.entity.GameEntityUsingSprites;
import de.amr.easy.game.sprite.Sprite;

public class Marble extends GameEntityUsingSprites {

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