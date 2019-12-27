package de.amr.samples.statemachine.markise;

import de.amr.easy.game.controller.Lifecycle;

public class Motor implements Lifecycle {

	private final Markise markise;

	public Motor(Markise markise) {
		this.markise = markise;
	}

	void zurück() {
		markise.tf.setVelocityX(-2);
	}

	void schnellZurück() {
		markise.tf.setVelocityX(-4);
	}

	void vor() {
		markise.tf.setVelocityX(2);
	}

	@Override
	public void stop() {
		markise.tf.setVelocityX(0);
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		int newPosition = Math.round(markise.getPosition() + markise.tf.getVelocityX());
		if (newPosition < 0) {
			markise.tf.setVelocityX(-markise.getPosition());
			markise.setPosition(Math.round(markise.getPosition() + markise.tf.getVelocityX()));
		} else {
			markise.setPosition(newPosition);
		}
	}
}