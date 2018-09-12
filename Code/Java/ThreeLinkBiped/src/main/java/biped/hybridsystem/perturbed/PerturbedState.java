
package biped.hybridsystem.perturbed;

import edu.ucsc.cross.jheq.core.object.DataStructure;

/**
 * A state
 */
public class PerturbedState extends DataStructure
{

	/**
	 * Example state value
	 */
	public double perturbationAngle;

	/**
	 * Construct the states
	 * 
	 * @param state_value_1
	 *            value of state 1
	 * @param state_value_1
	 *            value of state 2
	 */
	public PerturbedState(double default_state_value)
	{

		perturbationAngle = default_state_value;
		this.getProperties().setName("Perturbation");
		this.getProperties().setAccessKey(PerturbedState.class);
	}

	/**
	 * Construct the states
	 * 
	 * @param state_value_1
	 *            value of state 1
	 * @param state_value_1
	 *            value of state 2
	 */
	public PerturbedState()
	{
		this(.1);
	}
}
