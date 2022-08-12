package de.amr.samples.fsm.marbletoy.entities;

import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.C;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.D;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.E;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.F;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.G;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.H;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.Initial;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.Random;

import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.controller.Lifecycle;
import de.amr.easy.game.entity.Entity;
import de.amr.easy.game.view.View;
import de.amr.samples.fsm.marbletoy.fsm.LeverControl;
import de.amr.samples.fsm.marbletoy.router.MarbleRouter;
import de.amr.samples.fsm.marbletoy.router.RoutingPoint;

public class MarbleToy extends Entity implements Lifecycle, View {

	private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);

	public final BufferedImage image;
	public final Lever[] levers = new Lever[3];
	private final EnumSet<RoutingPoint> auxPoints = EnumSet.of(E, F, G, H);
	private final Marble marble;
	private final MarbleRouter router;
	private LeverControl leverControl;

	public MarbleToy() {
		image = Assets.image("toy.png");
		this.marble = new Marble(50);
		marble.tf.setPosition(-marble.tf.width, -marble.tf.height);
		levers[0] = new Lever(178, 82);
		levers[1] = new Lever(424, 82);
		levers[2] = new Lever(301, 204);
		router = new MarbleRouter(this);
	}

	public void setLeverControl(LeverControl leverControl) {
		this.leverControl = leverControl;
	}

	@Override
	public void init() {
		leverControl.getFsm().init();
		router.getFsm().init();
		router.getFsm().enqueue('A');
	}

	@Override
	public void update() {
		if (router.getFsm().getState() == C || router.getFsm().getState() == D) {
			Character slot = new Random().nextBoolean() ? 'A' : 'B';
			router.getFsm().init();
			router.getFsm().enqueue(slot);
			leverControl.getFsm().enqueue(slot);
		}
		leverControl.getFsm().update();
		router.getFsm().update();
		marble.update();
	}

	public Marble getMarble() {
		return marble;
	}

	public Lever getLever(int index) {
		return levers[index];
	}

	public boolean isLeverRoutingLeft(int index) {
		return leverControl.isRoutingLeft(index);
	}

	public void updateLevers() {
		for (int i = 0; i < levers.length; ++i) {
			levers[i].setPointsLeft(isLeverRoutingLeft(i));
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) tf.x, (int) tf.y, null);
		for (Lever lever : levers) {
			lever.draw(g);
		}
		drawRoutingPointLabels(g);
		marble.draw(g);
	}

	private void drawRoutingPointLabels(Graphics2D g) {
		g.setFont(LABEL_FONT);
		FontMetrics fm = g.getFontMetrics(LABEL_FONT);
		for (RoutingPoint rp : RoutingPoint.values()) {
			if (rp == Initial) {
				continue;
			}
			g.setColor(auxPoints.contains(rp) ? Color.GRAY : Color.BLACK);
			Rectangle2D bounds = fm.getStringBounds(rp.name(), g);
			g.drawString(rp.name(), rp.getLocation().roundedX() - (int) bounds.getWidth() / 2,
					rp.getLocation().roundedY() + fm.getDescent());
		}
	}
}