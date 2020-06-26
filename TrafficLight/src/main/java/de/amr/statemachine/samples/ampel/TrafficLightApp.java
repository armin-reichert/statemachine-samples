package de.amr.statemachine.samples.ampel;

import static de.amr.easy.game.Application.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;
import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.view.View;

/**
 * Simple traffic light simulation using finite-state machine.
 * 
 * @author Armin Reichert, Anna Schillo
 */
public class TrafficLightApp extends Application {

	public static void main(String[] args) {
		launch(TrafficLightApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		settings.title = "Traffic Light";
		settings.width = 150;
		settings.height = 450;
	}

	@Override
	public void init() {
		setController(new TrafficLightUI(new TrafficLight()));
	}
}

class TrafficLightUI implements Lifecycle, View {

	private final int width;
	private final int height;
	private final TrafficLight trafficLight;

	public TrafficLightUI(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
		this.width = app().settings().width;
		this.height = app().settings().height;
	}

	@Override
	public void init() {
		trafficLight.init();
	}

	@Override
	public void update() {
		trafficLight.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		final int inset = 3;
		final int d = width - inset * 2;
		g.setStroke(new BasicStroke(inset));

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(trafficLight.getState() == TrafficLight.Light.RED ? Color.RED : Color.BLACK);
		g.fillOval(inset, inset, d, d);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset, d, d);

		g.setColor(trafficLight.getState() == TrafficLight.Light.YELLOW ? Color.YELLOW : Color.BLACK);
		g.fillOval(inset, inset + height / 3, d, d);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height / 3, d, d);

		g.setColor(trafficLight.getState() == TrafficLight.Light.GREEN ? Color.GREEN : Color.BLACK);
		g.fillOval(inset, inset + height * 2 / 3, d, d);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height * 2 / 3, d, d);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}