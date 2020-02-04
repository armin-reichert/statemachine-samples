package de.amr.samples.statemachine.markise;

import java.awt.Color;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;

public class MarkiseApp extends Application {

	public static void main(String[] args) {
		launch(MarkiseApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		settings.title = "Markise Simulation";
		settings.width = 800;
		settings.height = 600;
		settings.bgColor = Color.WHITE;
		clock().setTargetFramerate(5);
	}

	@Override
	public void init() {
		setController(new MarkiseUI());
	}
}