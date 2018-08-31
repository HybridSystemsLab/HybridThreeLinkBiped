
package biped.system.plant;

import biped.computation.BipedComputer;
import biped.data.base.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.JumpSet;

public class Dp implements JumpSet<State, Parameters> {

	@Override
	public boolean D(State x, Parameters parameters) {

		boolean inD = false; // add logic to determine if x in jump set
		biped.data.base.State trigger = x;

		double hVal = BipedComputer.computeStepRemainder(trigger, parameters.bipedParams, parameters.perturbation);
		inD = hVal <= 0.0 && x.plantedLegVelocity > 0.0;
		return inD;
	}

}
