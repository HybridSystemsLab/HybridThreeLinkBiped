
package biped.system.plant;

import biped.computation.BipedComputer;
import biped.data.base.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.FlowSet;

public class Cp implements FlowSet<State, Parameters> {

	@Override
	public boolean C(State x, Parameters parameters) {

		boolean inC = true;
		double hVal = BipedComputer.computeStepRemainder(x, parameters.bipedParams, parameters.perturbation);
		inC = inC || hVal >= 0.0;
		return inC;
	}

}
