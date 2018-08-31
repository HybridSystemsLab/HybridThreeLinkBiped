
package biped.system.reference;

import biped.computation.BipedComputer;
import biped.data.base.TrajectoryParameters;
import biped.data.virtual.Parameters;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.JumpMap;
import edu.ucsc.cross.hse.core.modeling.Controller;

/**
 * A jump map
 */
public class Gp implements JumpMap<State, Parameters> {

	/**
	 * Controller
	 */
	Controller<State, TrajectoryParameters> controller;

	/**
	 * Constructor for jump map
	 */
	public Gp(Controller<State, TrajectoryParameters> controller) {

		this.controller = controller;

	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(State x, State x_plus, Parameters parameters) {

		biped.data.base.State state = BipedComputer.computeChangeAtImpact(x.bipedState, parameters.bipedParams);

		x_plus.bipedState.plantedLegAngle = state.plantedLegAngle;
		x_plus.bipedState.swingLegAngle = state.swingLegAngle;
		x_plus.bipedState.torsoAngle = state.torsoAngle;
		x_plus.bipedState.plantedLegVelocity = state.plantedLegVelocity;
		x_plus.bipedState.swingLegVelocity = state.swingLegVelocity;
		x_plus.bipedState.torsoVelocity = state.torsoVelocity;
		TrajectoryParameters newTrajParams = controller.k(x);
		x_plus.trajectoryParameters.B0 = newTrajParams.B0;
		x_plus.trajectoryParameters.B1 = newTrajParams.B1;
		x_plus.trajectoryParameters.finalState = newTrajParams.getFinalState();
		x_plus.trajectoryParameters.initialState = newTrajParams.getInitialState();
		x_plus.trajTimer = 0.0;

	}

}
