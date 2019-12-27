package de.amr.schule.trockner;

import static de.amr.easy.game.Application.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.view.View;
import de.amr.statemachine.core.State;

public class WaeschetrocknerUI implements View, Lifecycle {

	private final int width;
	private final int height;
	private final Waeschetrockner maschine;
	private BufferedImage trocknerImage;

	public WaeschetrocknerUI(int width, int height, Waeschetrockner maschine) {
		this.width = width;
		this.height = height;
		this.maschine = maschine;
		trocknerImage = Assets.image("trockner.jpg");
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
		g.drawImage(trocknerImage, 0, 0, width, height, null);
		g.setColor(Color.white);
		g.setFont(new Font("Sans", Font.PLAIN, 30));
		float remainingTime = maschine.steuerung.state().getTicksRemaining();
		if (maschine.steuerung.state().getTicksRemaining() != State.ENDLESS) {
			float sec = remainingTime / app().clock.getFrequency();
			String text = String.format("Trockner: %s, Tür: %s, Zeit %s (noch %.1f s)",
					maschine.steuerung.getState(), maschine.tür.getState(), maschine.zeitwahl.getState(), sec);
			g.drawString(text, 100, height - 40);
		}
		else {
			String text = String.format("Trockner: %s, Tür: %s, Zeit %s", maschine.steuerung.getState(),
					maschine.tür.getState(), maschine.zeitwahl.getState());
			g.drawString(text, 100, height - 40);
		}
	}
}