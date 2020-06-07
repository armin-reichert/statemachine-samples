package de.amr.samples.statemachine.garagentor;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;

/**
 * Simuliert unser Garagentor.
 * 
 * @author Armin Reichert, Anna und Peter Schillo
 */
public class GaragentorApp extends Application {

	public static void main(String[] args) {
		launch(GaragentorApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		settings.title = "Garagentor Simulation";
		settings.width = 800;
		settings.height = 600;
	}

	@Override
	public void init() {
		Garagentor tor = new Garagentor();
		tor.tf.width = (settings().width);
		tor.tf.height = (100);
		tor.tf.y = (settings().height - 100);
		setController(tor);
		clock().setTargetFrameRate(10);
	}
}