package de.amr.statemachine.samples.turnstile;

public interface Turnstile {

	public TurnstileState getState();

	public void event(TurnstileEvent event);
}
