package de.amr.samples.statemachine.ampel;

import de.amr.easy.game.Application;

/**
 * Simuliert eine Ampel mithilfe eines Zustandsautomaten.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class AmpelApp extends Application {

	public static void main(String[] args) {
		launch(new AmpelApp(), args);
	}

	public AmpelApp() {
		settings.title = "Ampel Simulation";
		settings.width = 150;
		settings.height = 450;
	}

	@Override
	public void init() {
		Ampel ampel = new Ampel(settings.width, settings.height);
		setController(ampel);
	}
}