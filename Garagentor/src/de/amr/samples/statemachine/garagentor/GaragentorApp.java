package de.amr.samples.statemachine.garagentor;

import java.awt.Color;

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
		settings.bgColor = Color.WHITE;
	}

	@Override
	public void init() {
		Garagentor tor = new Garagentor();
		tor.tf.setWidth(settings().width);
		tor.tf.setHeight(100);
		tor.tf.setY(settings().height - 100);
		setController(tor);
		clock().setTargetFramerate(10);
	}
}