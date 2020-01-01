package de.amr.samples.statemachine.markise;

import java.awt.Color;

import de.amr.easy.game.GenericApplication;

public class MarkiseApp extends GenericApplication {

	public static void main(String[] args) {
		launch(new MarkiseApp(), args);
	}

	public MarkiseApp() {
		settings.title = "Markise Simulation";
		settings.width = 800;
		settings.height = 600;
		settings.bgColor = Color.WHITE;
		clock.setFrequency(5);
	}

	@Override
	public void init() {
		setController(new MarkiseUI());
	}
}