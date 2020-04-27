package de.amr.samples.statemachine.ampel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.view.View;

public class TrafficLightUI implements Lifecycle, View {

	private final int width;
	private final int height;
	private TrafficLight trafficLight;

	public TrafficLightUI(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setAmpel(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

	@Override
	public void init() {
		if (trafficLight != null) {
			trafficLight.init();
		}
	}

	@Override
	public void update() {
		if (trafficLight != null) {
			trafficLight.update();
		}
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		if (trafficLight == null) {
			return;
		}

		final int inset = 3;
		final int diameter = width - inset * 2;
		g.setStroke(new BasicStroke(inset));

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(trafficLight.getState().equals(TrafficLight.Light.RED) ? Color.RED : Color.BLACK);
		g.fillOval(inset, inset, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset, diameter, diameter);

		g.setColor(trafficLight.getState().equals(TrafficLight.Light.YELLOW) ? Color.YELLOW : Color.BLACK);
		g.fillOval(inset, inset + height / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height / 3, diameter, diameter);

		g.setColor(trafficLight.getState().equals(TrafficLight.Light.GREEN) ? Color.GREEN : Color.BLACK);
		g.fillOval(inset, inset + height * 2 / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height * 2 / 3, diameter, diameter);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}