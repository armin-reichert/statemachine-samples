package de.amr.samples.statemachine.markise;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.entity.Entity;
import de.amr.easy.game.input.Mouse;
import de.amr.easy.game.view.View;

public class Fernbedienung extends Entity implements Lifecycle, View {

	private static final Map<String, Rectangle> BUTTONS = new HashMap<>();

	static {
		BUTTONS.put("up", new Rectangle(77, 116, 32, 32));
		BUTTONS.put("stop", new Rectangle(110, 116, 32, 26));
		BUTTONS.put("down", new Rectangle(148, 116, 24, 25));
	}

	public final BufferedImage image;
	private final Markise markise;
	private String event;

	public Fernbedienung(Markise markise) {
		this.markise = markise;
		image = Assets.image("remotecontrol.jpg");
		tf.width =(image.getWidth());
		tf.height =(image.getHeight());
	}

	@Override
	public void init() {
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

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) tf.x, (int) tf.y, null);
	}
}