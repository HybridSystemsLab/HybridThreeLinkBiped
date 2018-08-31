
package biped.system.virtual;

import biped.computation.BipedComputer;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.JumpSet;

/**
 * A jump set
 */
public class Dp implements JumpSet<State, Parameters> {

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(State x, Parameters parameters) {

		double hVal = BipedComputer.computeStepRemainder(parameters.plant.y(), parameters.bipedParams.bipedParams,
				parameters.perturbation);
		boolean inD = hVal <= 0.0 && x.bipedState.plantedLegVelocity > 0.0;
		return inD;
	}

}
