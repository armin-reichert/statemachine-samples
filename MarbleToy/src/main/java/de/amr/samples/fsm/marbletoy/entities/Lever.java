package de.amr.samples.fsm.marbletoy.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import de.amr.easy.game.entity.Entity;
import de.amr.easy.game.view.View;

public class Lever extends Entity implements View {

	private int size = 30;
	private int leg = size * 75 / 100;
	private boolean pointsLeft;

	public Lever(int x, int y) {
		tf.setPosition(x - size / 2, y - size / 2);
		tf.width =(size);
		tf.height =(size);
	}

	public boolean pointsLeft() {
		return pointsLeft;
	}

	public void setPointsLeft(boolean left) {
		this.pointsLeft = left;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(200, 200, 200));
		g.setStroke(new BasicStroke(4));
		g.translate(tf.x, tf.y);
		g.translate(size / 2, size / 2);
		if (pointsLeft) {
			g.drawLine(leg, -leg, -leg, leg);
		} else {
			g.drawLine(-leg, -leg, leg, leg);
		}
		g.translate(-size / 2, -size / 2);
		g.fillOval(size / 4, size / 4, size / 2, size / 2);
		g.translate(-tf.x, -tf.y);
		// g.setColor(Color.BLACK);
		// g.setStroke(new BasicStroke(1));
		// g.draw(getCollisionBox());
	}
}