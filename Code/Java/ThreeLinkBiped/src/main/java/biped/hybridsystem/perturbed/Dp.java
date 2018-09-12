
package biped.hybridsystem.perturbed;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import edu.ucsc.cross.jheq.core.model.JumpSet;

/**
 * A jump set
 */
public class Dp implements JumpSet<PerturbedState, BipedInput>
{

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(PerturbedState x, BipedInput param, ArrayList<Object> parameters)
	{
		double hVal = BipedComputer.computeStepRemainder(param.plantState, BipedParameters.get(parameters), x);
		boolean inD = hVal <= 0.0 && param.plantState.plantedLegVelocity > 0.0;
		return inD;
	}

}
