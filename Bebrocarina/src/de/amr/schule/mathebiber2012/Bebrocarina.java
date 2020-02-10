package de.amr.schule.mathebiber2012;

import de.amr.statemachine.api.EventMatchStrategy;
import de.amr.statemachine.core.StateMachine;

/**
 * @see https://bwinf.de/fileadmin/user_upload/Legacy/informatik-biber/uploads/media/Informatik-Biber_2012_Web_01032013_mitLoesungen.pdf
 */
public class Bebrocarina extends StateMachine<Integer, Character> {

	public static void main(String[] args) {
		Bebrocarina bc = new Bebrocarina();
		bc.prüfeObSpielbar("+ooo+ooo+ooo+ooo+");
		bc.prüfeObSpielbar("---o+-o--ooo+");
		bc.prüfeObSpielbar("-----o+++++o-----");
		bc.prüfeObSpielbar("--+--+--o-+--");
	}

	public Bebrocarina() {
		super(Integer.class, EventMatchStrategy.BY_EQUALITY);
		//@formatter:off
		beginStateMachine()
			.description("Bebrocarina")
			.initialState(1)

			.states()
			
				.state(1)
				.state(2)
				.state(3)
				.state(4)
				.state(5)
				.state(6)
				.state(-1)
			
			.transitions()

				.stay(1).on('o')
				.when(1).then(2).on('+')
				.when(1).then(-1).on('-')
		
				.when(2).then(2).on('o')
				.when(2).then(3).on('+')
				.when(2).then(1).on('-')
		
				.when(3).then(3).on('o')
				.when(3).then(4).on('+')
				.when(3).then(2).on('-')
		
				.when(4).then(4).on('o')
				.when(4).then(5).on('+')
				.when(4).then(3).on('-')
		
				.when(5).then(5).on('o')
				.when(5).then(6).on('+')
				.when(5).then(4).on('-')
		
				.when(6).then(6).on('o')
				.when(6).then(-1).on('+')
				.when(6).then(5).on('-')
		
				.stay(-1).on('o')
				.stay(-1).on('+')
				.stay(-1).on('-')
		
		.endStateMachine();
		//@formatter:on
	}

	void prüfeObSpielbar(String wort) {
		boolean spielbar = false;
		for (int startZustand = 1; startZustand <= 6; ++startZustand) {
			boolean ok = istSpielbar(startZustand, wort);
			if (ok) {
				spielbar = true;
				System.out.println(String.format("%s ist spielbar aus Zustand %d", wort, startZustand));
			}
		}
		if (!spielbar) {
			System.out.println(String.format("%s ist nicht spielbar", wort));
		}
	}

	boolean istSpielbar(int startZustand, String wort) {
		setState(startZustand);
		for (int i = 0; i < wort.length(); ++i) {
			enqueue(wort.charAt(i));
			update();
		}
		return getState() != -1;
	}
}