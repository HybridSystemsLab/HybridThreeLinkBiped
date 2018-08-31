
package biped.system.virtual;

import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.modeling.Port;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	public biped.data.virtual.Parameters bipedParams;

	public Port<State> reference;

	public Port<biped.system.perturbation.State> perturbation;

	public Port<biped.data.base.State> plant;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.data.base.Parameters traj_params) {

		bipedParams = new biped.data.virtual.Parameters(traj_params);
		plant = new Port<biped.data.base.State>();
		reference = new Port<State>();
		perturbation = new Port<biped.system.perturbation.State>();

		this.getProperties().setStoreTrajectory(true);
	}
}
