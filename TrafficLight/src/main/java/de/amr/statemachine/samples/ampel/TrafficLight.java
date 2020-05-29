package de.amr.statemachine.samples.ampel;

import static de.amr.easy.game.Application.app;
import static de.amr.statemachine.samples.ampel.TrafficLight.Light.GREEN;
import static de.amr.statemachine.samples.ampel.TrafficLight.Light.OFF;
import static de.amr.statemachine.samples.ampel.TrafficLight.Light.RED;
import static de.amr.statemachine.samples.ampel.TrafficLight.Light.YELLOW;

import java.awt.event.KeyEvent;

import de.amr.easy.game.input.Keyboard;
import de.amr.statemachine.core.StateMachine;
import de.amr.statemachine.samples.ampel.TrafficLight.Light;

/**
 * A simple traffic light.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class TrafficLight extends StateMachine<Light, Void> {

	public enum Light {
		OFF, RED, YELLOW, GREEN;
	}

	public TrafficLight() {
		//@formatter:off
		super(Light.class);
		beginStateMachine()
			.description("Traffic Light")
			.initialState(OFF)
			.states()
				.state(OFF)
				.state(RED).timeoutAfter(() -> app().clock().sec(3))
				.state(YELLOW).timeoutAfter(() -> app().clock().sec(2))
				.state(GREEN).timeoutAfter(() -> app().clock().sec(5))
			.transitions()
				.when(OFF).then(RED).condition(() -> Keyboard.keyPressedOnce(KeyEvent.VK_SPACE))
				.when(RED).then(GREEN).onTimeout()
				.when(GREEN).then(YELLOW).onTimeout()
				.when(YELLOW).then(RED).onTimeout()
		.endStateMachine();
		//@formatter:off
	}
}