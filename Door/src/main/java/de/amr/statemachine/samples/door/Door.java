package de.amr.statemachine.samples.door;

import de.amr.statemachine.api.Log;
import de.amr.statemachine.core.StateMachine.MissingTransitionBehavior;
import de.amr.statemachine.samples.door.DoorController.DoorEvent;

public class Door {

	public static void main(String[] args) {
		Door door = new Door();
		door.unlock();
		door.open();
		door.open();
		door.lock();
		door.close();
		door.unlock();
		door.lock();
	}

	DoorController state = new DoorController();

	public Door() {
		state.getTracer().setLogger(new Log() {
			
			@Override
			public void shutUp(boolean shutUp) {
			}
			
			@Override
			public void loginfo(String messageFormat, Object... args) {
				System.out.println(String.format(messageFormat, args));
			}
			
			@Override
			public boolean isShutUp() {
				return false;
			}
		});
		state.setMissingTransitionBehavior(MissingTransitionBehavior.LOG);
		state.init();
	}

	public void open() {
		state.process(DoorEvent.OPEN_DOOR);
	}

	public void close() {
		state.process(DoorEvent.CLOSE_DOOR);
	}

	public void lock() {
		state.process(DoorEvent.LOCK_DOOR);
	}

	public void unlock() {
		state.process(DoorEvent.UNLOCK_DOOR);
	}
}