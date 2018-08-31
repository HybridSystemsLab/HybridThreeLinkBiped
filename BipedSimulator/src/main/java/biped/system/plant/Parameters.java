
package biped.system.plant;

import biped.data.base.State;
import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.modeling.Port;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	public biped.data.base.Parameters bipedParams;

	public Port<biped.system.perturbation.State> perturbation;

	public Port<biped.data.virtual.State> virtual;

	public static State getBipedState(State state) {

		return new State(state.plantedLegAngle, state.swingLegAngle, state.torsoAngle, state.plantedLegVelocity,
				state.swingLegVelocity, state.torsoVelocity);
	}

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.data.base.Parameters traj_params) {

		bipedParams = traj_params;
		virtual = new Port<biped.data.virtual.State>();
		perturbation = new Port<biped.system.perturbation.State>();
	}
}
