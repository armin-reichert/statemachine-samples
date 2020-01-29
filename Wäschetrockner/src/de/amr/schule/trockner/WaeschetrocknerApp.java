package de.amr.schule.trockner;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;

public class WaeschetrocknerApp extends Application {

	public static void main(String[] args) {
		launch(WaeschetrocknerApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		settings.title = "Wäschetrockner Simulation";
		settings.width = 800;
		settings.height = 360;
	}

	@Override
	public void init() {
		setController(new WaeschetrocknerUI(settings().width, settings().height, new Waeschetrockner()));
	}
}