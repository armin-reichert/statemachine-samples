package de.amr.schule.trockner;

import static de.amr.easy.game.Application.LOGGER;
import static de.amr.easy.game.Application.app;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.input.Mouse;
import de.amr.statemachine.core.EventMatchStrategy;
import de.amr.statemachine.core.StateMachine;

public class Waeschetrockner implements Lifecycle {

	private Map<String, Rectangle> sensors = new HashMap<>();
	{
		sensors.put("StartTaste", new Rectangle(505, 209, 60, 30));
		sensors.put("TürAuf", new Rectangle(679, 201, 74, 33));
		sensors.put("EinAusTaste", new Rectangle(694, 146, 61, 32));
		sensors.put("Auf20", new Rectangle(34, 171, 103, 32));
		sensors.put("Auf15", new Rectangle(38, 205, 84, 22));
	}

	public StateMachine<String, String> steuerung;
	public StateMachine<String, String> tür;
	public StateMachine<Integer, String> zeitwahl;

	public Waeschetrockner() {
		// Steuerung

		//@formatter:off
		steuerung = StateMachine.beginStateMachine(String.class, String.class, EventMatchStrategy.BY_EQUALITY)
			.description("Trockner")
			.initialState("Aus")
		
		.states()
			.state("Aus")
			.state("Bereit")
			.state("Läuft").timeoutAfter(() -> app().clock.sec(zeitwahl.getState()))
				
		.transitions()
			.when("Aus").then("Bereit").on("EinAusTaste")
			.when("Aus").then("Läuft").on("StartTaste").condition(() -> tür.getState().equals("Zu"))
			.stay("Aus").on("StartTaste").act(() -> LOGGER.info("Bitte Tür schließen"))
			.stay("Aus").on("TürAuf")
			
			.when("Bereit").then("Aus").on("EinAusTaste")
			.when("Bereit").then("Läuft").on("StartTaste").condition(() -> tür.getState().equals("Zu"))
			.when("Bereit").then("Aus").on("TürAuf")
			.stay("Bereit").on("StartTaste").condition(() -> tür.getState().equals("Auf"))
				.act(() -> LOGGER.info("Bitte Tür schließen"))
			
			.when("Läuft").then("Aus").on("EinAusTaste").act(() -> tür.process("TürAuf"))
			.when("Läuft").then("Aus").on("TürAuf")
			.when("Läuft").then("Aus").onTimeout().act(() -> Assets.sound("fertig.mp3").play())
	
		.endStateMachine();

		tür = StateMachine.beginStateMachine(String.class, String.class, EventMatchStrategy.BY_EQUALITY)
				.description("Tür")
				.initialState("Zu")
				
		.states()
			.state("Auf")
			.state("Zu")
			
		.transitions()
			.when("Zu").then("Auf").on("TürAuf")
			.stay("Zu").on("TürZu")
			.when("Auf").then("Zu").on("TürZu")
			.stay("Auf").on("TürAuf")
		
		.endStateMachine();

		zeitwahl = StateMachine.beginStateMachine(Integer.class, String.class, EventMatchStrategy.BY_EQUALITY)
			.description("Zeitwahl")
			.initialState(15)

		.states()
			.state(15)
			.state(20)
			
		.transitions()
			.when(15).then(20).on("Auf20")
			.stay(15).on("Auf15")
			.when(20).then(15).on("Auf15")
			.stay(20).on("Auf20")
			
	  .endStateMachine();
		//@formatter:on
	}

	@Override
	public void init() {
		Stream.of(steuerung, tür, zeitwahl).forEach(automat -> {
			automat.setLogger(LOGGER);
			automat.init();
		});
	}

	public void handleMouseClick(int x, int y) {
		for (String event : sensors.keySet()) {
			if (sensors.get(event).contains(x, y)) {
				dispatch(event);
			}
		}
	}

	@Override
	public void update() {
		if (Mouse.clicked()) {
			handleMouseClick(Mouse.getX(), Mouse.getY());
		}
		if (Keyboard.keyPressedOnce(KeyEvent.VK_E)) {
			dispatch("EinAusTaste");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_S)) {
			dispatch("StartTaste");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_T)) {
			dispatch("TürAuf");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_Z)) {
			dispatch("TürZu");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_2)) {
			dispatch("Auf20");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_1)) {
			dispatch("Auf15");
		}
		Stream.of(steuerung, tür, zeitwahl).forEach(StateMachine::update);
	}

	// hot spots

	private void dispatch(String event) {
		switch (event) {
		case "StartTaste":
		case "EinAusTaste":
			steuerung.enqueue(event);
			return;
		case "TürAuf":
			steuerung.enqueue(event);
			tür.enqueue(event);
			return;
		case "TürZu":
			tür.enqueue(event);
			break;
		case "Auf15":
		case "Auf20":
			zeitwahl.enqueue(event);
			return;
		default:
			throw new IllegalArgumentException("Unknown event: " + event);
		}
	}
}