package de.amr.samples.statemachine.markise;

import static de.amr.easy.game.Application.app;

import java.awt.Graphics2D;

import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.view.View;

public class MarkiseUI implements Lifecycle, View {

	private Markise markise;
	private Fernbedienung remote;

	public MarkiseUI() {
	}

	@Override
	public void init() {
		markise = new Markise();
		markise.tf.width =(app().settings().width);
		markise.tf.y=(app().settings().height - 100);
		markise.init();
		remote = new Fernbedienung(markise);
	}

	@Override
	public void update() {
		remote.update();
		markise.update();
	}

	@Override
	public void draw(Graphics2D g) {
		markise.draw(g);
		remote.draw(g);
	}
}