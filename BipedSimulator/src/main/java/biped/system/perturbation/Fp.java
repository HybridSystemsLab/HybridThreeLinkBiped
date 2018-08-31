
package biped.system.perturbation;

import edu.ucsc.cross.hse.core.hybridsystem.input.FlowMap;

/**
 * A flow map
 */
public class Fp implements FlowMap<State, Parameters> {

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            state derivative values
	 */
	@Override
	public void F(State x, State x_dot, Parameters parameters) {

		// compute x_dot values here
	}

}
