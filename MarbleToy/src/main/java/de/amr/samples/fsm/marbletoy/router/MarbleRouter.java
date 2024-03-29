package de.amr.samples.fsm.marbletoy.router;

import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.A;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.B;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.C;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.D;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.E;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.F;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.G;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.H;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.Initial;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.X1;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.X2;
import static de.amr.samples.fsm.marbletoy.router.RoutingPoint.X3;

import de.amr.easy.game.math.V2f;
import de.amr.samples.fsm.marbletoy.entities.MarbleToy;
import de.amr.statemachine.api.TransitionMatchStrategy;
import de.amr.statemachine.core.StateMachine;

public class MarbleRouter {

	private static final float MARBLE_SPEED = 1.5f;

	private final StateMachine<RoutingPoint, Character> fsm;
	private final MarbleToy toy;

	public MarbleRouter(MarbleToy toy) {
		this.toy = toy;
		//@formatter:off
		fsm = StateMachine.beginStateMachine(RoutingPoint.class, Character.class, TransitionMatchStrategy.BY_VALUE)
				
				.description("Marble Router")
				.initialState(RoutingPoint.Initial)

				.states()

					.state(Initial)
					.state(A).onEntry(() -> routeMarble(A, X1))
					.state(B).onEntry(() -> routeMarble(B, X2))
					.state(X1).onEntry(() -> routeMarble(X1, toy.getLever(0).pointsLeft() ? E : X3))
					.state(X2).onEntry(() -> routeMarble(X2, toy.getLever(1).pointsLeft() ? X3 : F))
					.state(X3).onEntry(() -> routeMarble(X3, toy.getLever(2).pointsLeft() ? G : H))
					.state(E).onEntry(() -> routeMarble(E, G))
					.state(F).onEntry(() -> routeMarble(F, H))
					.state(G).onEntry(() -> routeMarble(G, C))
					.state(H).onEntry(() -> routeMarble(H, D))
			
				.transitions()
		
					.when(Initial).then(A).on('A').act(e -> placeMarbleCenteredAt(A))
					.when(Initial).then(B).on('B').act(e -> placeMarbleCenteredAt(B))
					.when(A).then(X1).condition(() -> isMarbleAtLever(0))
					.when(B).then(X2).condition(() -> isMarbleAtLever(1))
					.when(X1).then(E).condition(() -> isMarbleAt(E))
					.when(X1).then(X3).condition(() -> isMarbleAtLever(2))
					.when(X2).then(X3).condition(() -> isMarbleAtLever(2))
					.when(X2).then(F).condition(() -> isMarbleAt(F))
					.when(X3).then(G).condition(() -> isMarbleAt(G))
					.when(X3).then(H).condition(() -> isMarbleAt(H))
					.when(E).then(G).condition(() -> isMarbleAt(G))
					.when(F).then(H).condition(() -> isMarbleAt(H))
					.when(G).then(C).condition( () -> isMarbleAt(C))
					.when(H).then(D).condition( () -> isMarbleAt(D))
				
		.endStateMachine();
		//@formatter:on
	}

	public StateMachine<RoutingPoint, Character> getFsm() {
		return fsm;
	}

	private void placeMarbleCenteredAt(RoutingPoint p) {
		toy.getMarble().tf.setPosition(p.getLocation().x() - toy.getMarble().tf.width / 2,
				p.getLocation().y() - toy.getMarble().tf.height / 2);
	}

	private void routeMarble(RoutingPoint from, RoutingPoint to) {
		placeMarbleCenteredAt(from);
		toy.getMarble().tf.setVelocity(V2f.smul(MARBLE_SPEED, V2f.diff(to.getLocation(), from.getLocation()).normalized()));
	}

	private boolean isMarbleAtLever(int leverIndex) {
		V2f pos = toy.getLever(leverIndex).tf.getCenter();
		return toy.getMarble().getCollisionBox().contains(pos.roundedX(), pos.roundedY());
	}

	private boolean isMarbleAt(RoutingPoint point) {
		V2f pos = point.getLocation();
		return toy.getMarble().getCollisionBox().contains(pos.roundedX(), pos.roundedY());
	}
}