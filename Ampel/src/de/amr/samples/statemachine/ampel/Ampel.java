package de.amr.samples.statemachine.ampel;

import static de.amr.easy.game.Application.LOGGER;
import static de.amr.easy.game.Application.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import de.amr.easy.game.entity.AbstractGameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.view.View;
import de.amr.statemachine.StateMachine;

/**
 * Die Ampel.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class Ampel extends AbstractGameEntity implements View {

	private final StateMachine<String, Void> steuerung;

	public Ampel() {
		//@formatter:off
		steuerung = StateMachine.beginStateMachine(String.class, Void.class)
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
	}

	@Override
	public void init() {
		steuerung.traceTo(LOGGER, app().clock::getFrequency);
		steuerung.init();
	}

	@Override
	public void update() {
		steuerung.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(tf.getX(), tf.getY());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, tf.getWidth(), tf.getHeight());

		final int inset = 3;
		final int diameter = tf.getWidth() - inset * 2;
		g.setStroke(new BasicStroke(inset));
		
		g.setColor(steuerung.getState().equals("Rot") ? Color.RED : Color.BLACK);
		g.fillOval(inset, inset, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset, diameter, diameter);
		
		g.setColor(steuerung.getState().equals("Gelb") ? Color.YELLOW : Color.BLACK);
		g.fillOval(inset, inset + tf.getHeight() / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + tf.getHeight() / 3, diameter, diameter);
		
		g.setColor(steuerung.getState().equals("Grün") ? Color.GREEN : Color.BLACK);
		g.fillOval(inset, inset + tf.getHeight() * 2 / 3, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset + tf.getHeight() * 2 / 3, diameter, diameter);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.translate(-tf.getX(), -tf.getY());
	}
}