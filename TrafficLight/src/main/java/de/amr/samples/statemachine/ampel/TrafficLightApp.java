package de.amr.samples.statemachine.ampel;

import de.amr.easy.game.Application;

/**
 * Simple traffic light simulation using finite state machine.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class TrafficLightApp extends Application {

	public static void main(String[] args) {
		launch(new TrafficLightApp(), args);
	}

	public TrafficLightApp() {
		settings.title = "Traffic Light Simulation";
		settings.width = 150;
		settings.height = 450;
	}

	@Override
	public void init() {
		TrafficLight ampel = new TrafficLight();
		ampel.traceTo(LOGGER, clock::getFrequency);
		TrafficLightUI viewController = new TrafficLightUI(settings.width, settings.height);
		viewController.setAmpel(ampel);
		setController(viewController);
	}
}