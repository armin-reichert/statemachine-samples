package de.amr.samples.statemachine.ampel;

import static de.amr.easy.game.Application.LOGGER;
import static de.amr.easy.game.Application.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.view.Controller;
import de.amr.easy.game.view.View;
import de.amr.statemachine.StateMachine;

/**
 * Die Ampel.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class Ampel extends StateMachine<String, Void> implements Controller, View {

	private final int width;
	private final int height;

	public Ampel(int width, int height) {
		//@formatter:off
		super(String.class);
		beginStateMachine()
			.description("Ampel")
			.initialState("Aus")
			.states()
				.state("Aus")
				.state("Rot").timeoutAfter(() -> app().clock.sec(3))
				.state("Gelb").timeoutAfter(() -> app().clock.sec(2))
				.state("Grün").timeoutAfter(() -> app().clock.sec(5))
			.transitions()
				.when("Aus").then("Rot").condition(() -> Keyboard.keyPressedOnce(KeyEvent.VK_SPACE))
				.when("Rot").then("Grün").onTimeout()
				.when("Grün").then("Gelb").onTimeout()
				.when("Gelb").then("Rot").onTimeout()
		.endStateMachine();
		//@formatter:off
		this.width = width;
		this.height = height;
		traceTo(LOGGER, app().clock::getFrequency);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		final int inset = 3;
		final int diameter = width - inset * 2;
		g.setStroke(new BasicStroke(inset));
		
		g.setColor(getState().equals("Rot") ? Color.RED : Color.BLACK);
		g.fillOval(inset, inset, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset, diameter, diameter);
		
		g.setColor(getState().equals("Gelb") ? Color.YELLOW : Color.BLACK);
		g.fillOval(inset, inset + height / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height / 3, diameter, diameter);
		
		g.setColor(getState().equals("Grün") ? Color.GREEN : Color.BLACK);
		g.fillOval(inset, inset + height * 2 / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + height * 2 / 3, diameter, diameter);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}