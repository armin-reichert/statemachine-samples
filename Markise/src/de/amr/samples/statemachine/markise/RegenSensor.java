package de.amr.samples.statemachine.markise;

import java.awt.event.KeyEvent;

import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.input.Keyboard;

public class RegenSensor implements Lifecycle {

	private int regenTropfen;

	public boolean esRegnet() {
		return regenTropfen > 10;
	}

	@Override
	public void init() {
		regenTropfen = 0;
	}

	@Override
	public void update() {
		if (Keyboard.keyDown(KeyEvent.VK_R)) {
			regenTropfen += 1;
		} else if (Keyboard.keyDown(KeyEvent.VK_S)) {
			regenTropfen -= 1;
		}
	}
}