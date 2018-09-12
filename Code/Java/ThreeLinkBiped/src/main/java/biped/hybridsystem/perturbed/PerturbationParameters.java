
package biped.hybridsystem.perturbed;

import edu.ucsc.cross.jheq.core.object.DataStructure;

/**
 * A parameter set
 */
public class PerturbationParameters extends DataStructure
{

	/**
	 * Example parameter value
	 */
	public double perturbationPercent;

	public boolean randomize;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public PerturbationParameters()
	{
		this(.1, true);

	}

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public PerturbationParameters(double parameter_value, boolean randomize)
	{

		perturbationPercent = parameter_value;
		this.randomize = randomize;

	}
}
