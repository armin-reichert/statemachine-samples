package de.amr.samples.statemachine.ampel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.amr.easy.game.view.View;
import de.amr.easy.game.view.ViewController;

public class AmpelUI implements View, ViewController {

	private final int width;
	private final int height;
	private Ampel ampel;

	public AmpelUI(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setAmpel(Ampel ampel) {
		this.ampel = ampel;
	}

	@Override
	public View currentView() {
		return this;
	}

	@Override
	public void init() {
		if (ampel != null) {
			ampel.init();
		}
	}

	@Override
	public void update() {
		if (ampel != null) {
			ampel.update();
		}
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		if (ampel == null) {
			return;
		}

		final int inset = 3;
		final int diameter = width - inset * 2;
		g.setStroke(new BasicStroke(inset));

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(ampel.getState().equals(Ampel.Light.RED) ? Color.RED : Color.BLACK);
		g.fillOval(inset, inset, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset, diameter, diameter);

		g.setColor(ampel.getState().equals(Ampel.Light.YELLOW) ? Color.YELLOW : Color.BLACK);
		g.fillOval(inset, inset + height / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height / 3, diameter, diameter);

		g.setColor(ampel.getState().equals(Ampel.Light.GREEN) ? Color.GREEN : Color.BLACK);
		g.fillOval(inset, inset + height * 2 / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height * 2 / 3, diameter, diameter);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}