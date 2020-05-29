package de.amr.statemachine.samples.ampel;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;

/**
 * Simple traffic light simulation using finite state machine.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class TrafficLightApp extends Application {

	public static void main(String[] args) {
		launch(TrafficLightApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		settings.title = "Traffic Light Simulation";
		settings.width = 150;
		settings.height = 450;
	}

	@Override
	public void init() {
		TrafficLight ampel = new TrafficLight();
		ampel.getTracer().setLogger(LOGGER);
		TrafficLightUI viewController = new TrafficLightUI(settings().width, settings().height);
		viewController.setAmpel(ampel);
		setController(viewController);
	}
}