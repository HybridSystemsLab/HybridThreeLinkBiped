
package biped.hybridsystem.perturbed;

import java.util.ArrayList;

import biped.data.BipedInput;
import edu.ucsc.cross.jheq.core.model.FlowSet;

/**
 * A flow set
 */
public class Cp implements FlowSet<PerturbedState, BipedInput>
{

	@Override
	public boolean C(PerturbedState x, BipedInput param, ArrayList<Object> parameters)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
