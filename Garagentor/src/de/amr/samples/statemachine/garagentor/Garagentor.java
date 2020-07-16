package de.amr.samples.statemachine.garagentor;

import static de.amr.easy.game.Application.app;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorEreignis.FB_GEDRÜCKT;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorEreignis.SCHALTER_GEDRÜCKT;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorZustand.GESCHLOSSEN;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorZustand.GESTOPPT_BEIM_SCHLIESSEN;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorZustand.GESTOPPT_BEIM_ÖFFNEN;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorZustand.OFFEN;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorZustand.SCHLIESST;
import static de.amr.samples.statemachine.garagentor.Garagentor.TorZustand.ÖFFNET;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.entity.Entity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.view.View;
import de.amr.statemachine.api.TransitionMatchStrategy;
import de.amr.statemachine.core.StateMachine;

public class Garagentor extends Entity implements View, Lifecycle {

	public enum TorZustand {
		GESCHLOSSEN, ÖFFNET, OFFEN, GESTOPPT_BEIM_ÖFFNEN, SCHLIESST, GESTOPPT_BEIM_SCHLIESSEN
	}

	public enum TorEreignis {
		SCHALTER_GEDRÜCKT, FB_GEDRÜCKT,
	}

	private StateMachine<TorZustand, TorEreignis> steuerung;
	private int position;
	private boolean hindernis;
	private boolean lichtBrennt;

	public Garagentor() {
		//@formatter:off
		steuerung = StateMachine.beginStateMachine(TorZustand.class, TorEreignis.class, TransitionMatchStrategy.BY_VALUE)
				.description("Garagentor Steuerung")
				.initialState(GESCHLOSSEN)

		.states()
		
				.state(GESCHLOSSEN)
						.timeoutAfter(() -> app().clock().sec(5))
						.onEntry(() -> lichtAn())
						.onExit(() -> {
							System.out.println("Exit action");
							lichtAus();
						})
				
				.state(ÖFFNET)
					.onTick(() -> position++)
		
				.state(OFFEN)
				
				.state(SCHLIESST)
					.onTick(() -> position--)
				
		.transitions()
		
				.when(GESCHLOSSEN).then(ÖFFNET).on(SCHALTER_GEDRÜCKT)
				.when(GESCHLOSSEN).then(ÖFFNET).on(FB_GEDRÜCKT)
				.stay(GESCHLOSSEN).onTimeout().act(() -> {
					lichtAus();
					System.out.println("loop transition on timeout");
				})
				
				.when(ÖFFNET).then(OFFEN).condition(() -> endPunktErreicht())
				.when(ÖFFNET).then(GESTOPPT_BEIM_ÖFFNEN).on(SCHALTER_GEDRÜCKT)
				.when(ÖFFNET).then(GESTOPPT_BEIM_ÖFFNEN).on(FB_GEDRÜCKT)
				
				.when(OFFEN).then(SCHLIESST).on(SCHALTER_GEDRÜCKT)
				.when(OFFEN).then(SCHLIESST).on(FB_GEDRÜCKT)
				
				.when(SCHLIESST).then(GESCHLOSSEN).condition(() -> anfangsPunktErreicht())
				.when(SCHLIESST).then(GESTOPPT_BEIM_SCHLIESSEN).on(SCHALTER_GEDRÜCKT)
				.when(SCHLIESST).then(GESTOPPT_BEIM_SCHLIESSEN).on(FB_GEDRÜCKT)
				.when(SCHLIESST).then(ÖFFNET).condition(() -> hindernisErkannt())
				
				.when(GESTOPPT_BEIM_ÖFFNEN).then(SCHLIESST).on(SCHALTER_GEDRÜCKT)
				.when(GESTOPPT_BEIM_ÖFFNEN).then(SCHLIESST).on(FB_GEDRÜCKT)

				.when(GESTOPPT_BEIM_SCHLIESSEN).then(ÖFFNET).on(SCHALTER_GEDRÜCKT)
				.when(GESTOPPT_BEIM_SCHLIESSEN).then(ÖFFNET).on(FB_GEDRÜCKT)

		.endStateMachine();
		//@formatter:on
	}

	@Override
	public void init() {
		steuerung.init();
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			steuerung.enqueue(SCHALTER_GEDRÜCKT);
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_F)) {
			steuerung.enqueue(FB_GEDRÜCKT);
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_H)) {
			if (steuerung.getState() == GESCHLOSSEN && !hindernis) {
				// Hindernis nicht einschalten
			} else {
				hindernis = !hindernis;
			}
		}
		steuerung.update();
	}

	private boolean endPunktErreicht() {
		return position >= 100;
	}

	private boolean anfangsPunktErreicht() {
		return position <= 0;
	}

	private boolean hindernisErkannt() {
		return hindernis;
	}

	private void lichtAn() {
		lichtBrennt = true;
		System.out.println("Licht an");
	}

	private void lichtAus() {
		lichtBrennt = false;
		System.out.println("Licht aus");
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(tf.x, tf.y);
		g.setColor(Color.BLUE);
		int w = position * tf.width / 100;
		g.fillRect(0, 0, w, 20);
		g.translate(-tf.x, -tf.y);
	
		g.translate(tf.x, tf.y + 40);
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
		g.drawString(String.format("Position: %d, Zustand: %s, Hindernis: %s, %s", position, steuerung.getState(),
				hindernis ? "Ja" : "Nein", lichtBrennt ? "Licht brennt" : ""), 0, 0);
		g.translate(-tf.x, -tf.y);
	}
}