package de.amr.schule.trockner;

import static de.amr.easy.game.Application.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.amr.easy.game.view.Controller;
import de.amr.easy.game.view.View;
import de.amr.statemachine.core.State;

public class WaeschetrocknerUI implements View, Controller {

	private final int height;
	private final Waeschetrockner maschine;

	public WaeschetrocknerUI(int width, int height, Waeschetrockner maschine) {
		this.height = height;
		this.maschine = maschine;
		maschine.tf.setWidth(width);
		maschine.tf.setHeight(height);
		maschine.sprites.get("s_trockner").scale(width, height);
	}

	@Override
	public void init() {
		maschine.init();
	}

	@Override
	public void update() {
		maschine.update();
	}

	@Override
	public void draw(Graphics2D g) {
		maschine.draw(g);
		g.setColor(Color.white);
		g.setFont(new Font("Sans", Font.PLAIN, 30));
		float remainingTime = maschine.steuerung.state().getTicksRemaining();
		if (maschine.steuerung.state().getTicksRemaining() != State.ENDLESS) {
			float sec = remainingTime / app().clock.getFrequency();
			String text = String.format("Trockner: %s, Tür: %s, Zeit %s (noch %.1f s)",
					maschine.steuerung.getState(), maschine.tür.getState(), maschine.zeitwahl.getState(), sec);
			g.drawString(text, 100, height - 40);
		} else {
			String text = String.format("Trockner: %s, Tür: %s, Zeit %s", maschine.steuerung.getState(),
					maschine.tür.getState(), maschine.zeitwahl.getState());
			g.drawString(text, 100, height - 40);
		}
	}
}