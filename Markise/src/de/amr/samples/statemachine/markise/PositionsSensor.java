package de.amr.samples.statemachine.markise;

public class PositionsSensor {

	private final Markise markise;

	public PositionsSensor(Markise markise) {
		this.markise = markise;
	}

	public boolean inEndPosition() {
		return markise.getPosition() == 100;
	}

	public boolean inStartPosition() {
		return markise.getPosition() == 0;
	}

}