
package biped.data;

import java.util.ArrayList;

import biped.computations.EquilibriumComputer;
import edu.ucsc.cross.jheq.core.object.DataStructure;
import firefly.hybridsystem.ParameterDecode;

/**
 * A parameter set
 */
public class VirtualBipedParameters extends DataStructure
{

	public static VirtualBipedParameters get(ArrayList<Object> params)
	{
		return ParameterDecode.getAny(VirtualBipedParameters.class, params);
	}

	public BipedParameters bipedParams;

	public TrajectoryParameters equilibParams;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public VirtualBipedParameters(BipedParameters traj_params)
	{

		bipedParams = traj_params;
		equilibParams = EquilibriumComputer.getEquilibriumParameters(bipedParams);
		this.getProperties().setStoreTrajectory(false);
	}
}
