package de.amr.samples.statemachine.markise;

import de.amr.easy.game.controller.Lifecycle;

public class Motor implements Lifecycle {

	private final Markise markise;

	public Motor(Markise markise) {
		this.markise = markise;
	}

	void zurück() {
		markise.tf.vx = -2;
	}

	void schnellZurück() {
		markise.tf.vx = -4;
	}

	void vor() {
		markise.tf.vx = 2;
	}

	@Override
	public void stop() {
		markise.tf.vx = 0;
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		int newPosition = Math.round(markise.getPosition() + markise.tf.vx);
		if (newPosition < 0) {
			markise.tf.vx = -markise.getPosition();
			markise.setPosition(Math.round(markise.getPosition() + markise.tf.vx));
		} else {
			markise.setPosition(newPosition);
		}
	}
}