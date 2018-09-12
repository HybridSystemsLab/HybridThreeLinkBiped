
package biped.hybridsystem.virtual;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.VirtualBipedState;
import edu.ucsc.cross.jheq.core.model.JumpSet;

/**
 * A jump set
 */
public class DprPerturbed implements JumpSet<VirtualBipedState, BipedInput>
{

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(VirtualBipedState x, BipedInput input, ArrayList<Object> parameters)
	{
		BipedParameters bipedParams = BipedParameters.get(parameters);

		double hVal = BipedComputer.computeStepRemainder(input.plantState, bipedParams, input.perturbedState);
		boolean inD = hVal <= 0.0;
		return inD;
	}

}
