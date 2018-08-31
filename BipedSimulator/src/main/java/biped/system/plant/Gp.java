
package biped.system.plant;

import biped.computation.BipedComputer;
import biped.data.base.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.JumpMap;

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

		BipedComputer.computeChangeAtImpact(x, x_plus, parameters.bipedParams);

	}

}
