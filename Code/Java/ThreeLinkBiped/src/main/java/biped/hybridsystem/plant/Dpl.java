
package biped.hybridsystem.plant;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.BipedState;
import edu.ucsc.cross.jheq.core.model.JumpSet;

/**
 * A jump set
 */
public class Dpl implements JumpSet<BipedState, BipedInput>
{

	@Override
	public boolean D(BipedState x, BipedInput input, ArrayList<Object> parameters)
	{

		double hVal = BipedComputer.computeStepRemainder(x, BipedParameters.get(parameters));
		boolean inD = hVal <= 0.0 && x.plantedLegVelocity > 0.0;
		return inD;
	}

}
