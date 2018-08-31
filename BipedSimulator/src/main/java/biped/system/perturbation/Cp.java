
package biped.system.perturbation;

import edu.ucsc.cross.hse.core.hybridsystem.input.FlowSet;

public class Cp implements FlowSet<State, Parameters> {

	@Override
	public boolean C(State x, Parameters parameters) {

		boolean inC = true; // add logic to determine if x in flow set
		return inC;
	}

}
