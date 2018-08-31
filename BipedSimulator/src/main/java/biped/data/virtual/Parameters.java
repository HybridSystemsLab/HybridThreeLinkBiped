
package biped.data.virtual;

import biped.computation.EquilibriumComputer;
import biped.data.base.TrajectoryParameters;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	public biped.data.base.Parameters bipedParams;

	public TrajectoryParameters equilibParams;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.data.base.Parameters traj_params) {

		bipedParams = traj_params;

		equilibParams = EquilibriumComputer.getEquilibriumParameters(bipedParams);
		this.getProperties().setStoreTrajectory(false);

	}
}
