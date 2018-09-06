package de.amr.samples.statemachine.markise;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import de.amr.easy.game.entity.GameEntityUsingSprites;
import de.amr.easy.game.input.Mouse;
import de.amr.easy.game.sprite.Sprite;

public class Fernbedienung extends GameEntityUsingSprites {

	private static final Map<String, Rectangle> BUTTONS = new HashMap<>();

	static {
		BUTTONS.put("up", new Rectangle(77, 116, 32, 32));
		BUTTONS.put("stop", new Rectangle(110, 116, 32, 26));
		BUTTONS.put("down", new Rectangle(148, 116, 24, 25));
	}

	private final Markise markise;
	private String event;

	public Fernbedienung(Markise markise) {
		this.markise = markise;
		setSprite("s_remote", Sprite.ofAssets("remotecontrol.jpg"));
		setSelectedSprite("s_remote");
		tf.setWidth(getSelectedSprite().getWidth());
		tf.setHeight(getSelectedSprite().getHeight());
	}

	@Override
	public void update() {
		if (Mouse.clicked()) {
			handleClick(Mouse.getX(), Mouse.getY());
		}
		if (event != null) {
			markise.raiseEvent(event);
			event = null;
		}
	}

	private void handleClick(int x, int y) {
		/*@formatter:off*/
		BUTTONS.entrySet().stream()
			.filter(entry -> entry.getValue().contains(x, y))
			.map(entry -> entry.getKey())
			.findFirst()
			.ifPresent(event -> this.event = event);
		/*@formatter:on*/
	}
}