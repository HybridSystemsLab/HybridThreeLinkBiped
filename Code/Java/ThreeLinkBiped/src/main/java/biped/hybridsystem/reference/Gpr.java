
package biped.hybridsystem.reference;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.BipedState;
import biped.data.TrajectoryParameters;
import biped.data.VirtualBipedState;
import edu.ucsc.cross.jheq.core.model.ControlType;
import edu.ucsc.cross.jheq.core.model.Controller;
import edu.ucsc.cross.jheq.core.model.JumpMap;

/**
 * A jump map
 */
public class Gpr implements JumpMap<VirtualBipedState, BipedInput>
{

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(VirtualBipedState x, VirtualBipedState x_plus, BipedInput input, ArrayList<Object> parameters)
	{
		Controller<BipedState, BipedInput, TrajectoryParameters> controller = Controller.get(parameters,
		ControlType.DISCRETE);
		BipedParameters bipedParams = BipedParameters.get(parameters);
		BipedState state = BipedComputer.computeChangeAtImpact(x, bipedParams);

		x_plus.plantedLegAngle = state.plantedLegAngle;
		x_plus.swingLegAngle = state.swingLegAngle;
		x_plus.torsoAngle = state.torsoAngle;
		x_plus.plantedLegVelocity = state.plantedLegVelocity;
		x_plus.swingLegVelocity = state.swingLegVelocity;
		x_plus.torsoVelocity = state.torsoVelocity;
		TrajectoryParameters newTrajParams = controller.uC(x, input, parameters);
		x_plus.getTrajectoryParameters().B0 = newTrajParams.B0;
		x_plus.getTrajectoryParameters().B1 = newTrajParams.B1;
		x_plus.getTrajectoryParameters().finalState = newTrajParams.getFinalState();
		x_plus.getTrajectoryParameters().initialState = newTrajParams.getInitialState();
		x_plus.trajTimer = 0.0;

	}

}
