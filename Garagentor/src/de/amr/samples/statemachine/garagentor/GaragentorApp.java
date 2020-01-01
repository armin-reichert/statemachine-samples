package de.amr.samples.statemachine.garagentor;

import java.awt.Color;

import de.amr.easy.game.GenericApplication;

/**
 * Simuliert unser Garagentor.
 * 
 * @author Armin Reichert, Anna und Peter Schillo
 */
public class GaragentorApp extends GenericApplication {

	public static void main(String[] args) {
		launch(new GaragentorApp(), args);
	}

	public GaragentorApp() {
		settings.title = "Garagentor Simulation";
		settings.width = 800;
		settings.height = 600;
		settings.bgColor = Color.WHITE;
		clock.setFrequency(10);
	}

	@Override
	public void init() {
		Garagentor tor = new Garagentor();
		tor.tf.setWidth(settings.width);
		tor.tf.setHeight(100);
		tor.tf.setY(settings.height - 100);
		setController(tor);
	}
}