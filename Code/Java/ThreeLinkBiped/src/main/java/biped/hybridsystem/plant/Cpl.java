
package biped.hybridsystem.plant;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.BipedState;
import edu.ucsc.cross.jheq.core.model.FlowSet;

/**
 * A flow set
 */
public class Cpl implements FlowSet<BipedState, BipedInput>
{

	/**
	 * Flow set
	 * 
	 * @param x
	 *            curr ent state
	 */
	@Override
	public boolean C(BipedState x, BipedInput param, ArrayList<Object> parameters)
	{

		boolean inC = true;
		double hVal = BipedComputer.computeStepRemainder(x, BipedParameters.get(parameters), param.perturbedState);
		inC = hVal >= 0.0 && BipedComputer.isInStateSpace(x);
		return inC;
	}

}
