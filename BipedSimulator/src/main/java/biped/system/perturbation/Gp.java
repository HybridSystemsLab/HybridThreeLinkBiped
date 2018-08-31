
package biped.system.perturbation;

import edu.ucsc.cross.hse.core.hybridsystem.input.JumpMap;
import edu.ucsc.cross.hse.core.variable.RandomVariable;

/**
 * A jump map
 */
public class Gp implements JumpMap<State, Parameters> {

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(State x, State x_plus, Parameters parameters) {

		double perturbationAngle = parameters.perturbationPercent * parameters.bipedParameters.stepAngle;
		if (parameters.randomize) {
			perturbationAngle = RandomVariable.generate(-perturbationAngle, perturbationAngle);
		}
		x_plus.perturbationAngle = perturbationAngle;
	}

}
