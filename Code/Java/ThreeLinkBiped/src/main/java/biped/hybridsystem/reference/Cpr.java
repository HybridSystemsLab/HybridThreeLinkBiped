
package biped.hybridsystem.reference;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.VirtualBipedState;
import edu.ucsc.cross.jheq.core.model.FlowSet;

/**
 * A flow set
 */
public class Cpr implements FlowSet<VirtualBipedState, BipedInput>
{

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(VirtualBipedState x, BipedInput input, ArrayList<Object> parameters)
	{
		BipedParameters bipedParams = BipedParameters.get(parameters);

		boolean inC = false;
		double hVal = BipedComputer.computeStepRemainder(x, bipedParams, input.perturbedState);
		inC = inC || hVal >= 0.0;

		return inC;
	}

}
