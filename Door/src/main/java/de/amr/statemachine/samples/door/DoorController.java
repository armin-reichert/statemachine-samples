package de.amr.statemachine.samples.door;

import static de.amr.statemachine.samples.door.DoorController.DoorEvent.CLOSE_DOOR;
import static de.amr.statemachine.samples.door.DoorController.DoorEvent.LOCK_DOOR;
import static de.amr.statemachine.samples.door.DoorController.DoorEvent.OPEN_DOOR;
import static de.amr.statemachine.samples.door.DoorController.DoorEvent.UNLOCK_DOOR;
import static de.amr.statemachine.samples.door.DoorController.DoorState.CLOSED;
import static de.amr.statemachine.samples.door.DoorController.DoorState.LOCKED;
import static de.amr.statemachine.samples.door.DoorController.DoorState.OPEN;

import de.amr.statemachine.api.TransitionMatchStrategy;
import de.amr.statemachine.core.StateMachine;
import de.amr.statemachine.samples.door.DoorController.DoorEvent;
import de.amr.statemachine.samples.door.DoorController.DoorState;

public class DoorController extends StateMachine<DoorState, DoorEvent> {

	public enum DoorState {
		OPEN, CLOSED, LOCKED
	}

	public enum DoorEvent {
		OPEN_DOOR, CLOSE_DOOR, LOCK_DOOR, UNLOCK_DOOR
	}

	public DoorController() {
		super(DoorState.class, TransitionMatchStrategy.BY_VALUE);
		//@formatter:off
		beginStateMachine()
			.initialState(LOCKED)
			.description("Door")
		.states()
		.transitions()
			.when(LOCKED).then(CLOSED).on(UNLOCK_DOOR)
			.when(CLOSED).then(LOCKED).on(LOCK_DOOR)
			.when(CLOSED).then(OPEN).on(OPEN_DOOR)
			.when(OPEN).then(CLOSED).on(CLOSE_DOOR)
		.endStateMachine();
		//@formatter:on
	}
}