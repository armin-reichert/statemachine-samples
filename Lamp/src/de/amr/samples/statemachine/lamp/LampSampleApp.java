package de.amr.samples.statemachine.lamp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import de.amr.statemachine.Match;
import de.amr.statemachine.StateMachine;

public class LampSampleApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(LampSampleApp::new);
	}

	public LampSampleApp() {
		LampView view = new LampView();
		JFrame frame = new JFrame("Lamp Sample");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.add(view, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		StateMachine<Boolean, Boolean> controller = StateMachine.beginStateMachine(Boolean.class, Boolean.class, Match.BY_EQUALITY)
		//@formatter:off
			.initialState(false)
			.states().state(false).state(true)
			.transitions()
				.when(false).then(true).on(true).act(e -> view.showLight(true))
				.when(true).then(false).on(true).act(e -> view.showLight(false))
		.endStateMachine();
		//@formatter:off
		view.addButtonHandler(controller::process);
		controller.init();
	}
}