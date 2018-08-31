
package biped.system.reference;

import biped.computation.BipedComputer;
import biped.data.virtual.Parameters;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.FlowSet;

/**
 * A flow set
 */
public class Cp implements FlowSet<State, Parameters> {

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(State x, Parameters parameters) {

		boolean inC = false;
		double hVal = BipedComputer.computeStepRemainder(x.bipedState, parameters.bipedParams);
		inC = hVal >= 0.0;

		return inC;
	}

}
