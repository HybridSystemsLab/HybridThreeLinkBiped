
package biped.hybridsystem.plant;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.BipedState;
import edu.ucsc.cross.jheq.core.model.JumpMap;

/**
 * A jump map
 */
public class Gpl implements JumpMap<BipedState, BipedInput>
{

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(BipedState x, BipedState x_plus, BipedInput input, ArrayList<Object> parameters)
	{

		BipedComputer.computeChangeAtImpact(x, x_plus, BipedParameters.get(parameters));
	}

}
