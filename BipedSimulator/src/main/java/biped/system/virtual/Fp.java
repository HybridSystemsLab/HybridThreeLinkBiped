
package biped.system.virtual;

import Jama.Matrix;
import biped.computation.BipedComputer;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.FlowMap;
import edu.ucsc.cross.hse.core.modeling.Controller;

/**
 * A flow map
 */
public class Fp implements FlowMap<State, Parameters> {

	public Controller<State, Matrix> controller;

	/**
	 * Constructor for flow map
	 */
	public Fp(Controller<State, Matrix> controller) {

		this.controller = controller;
	}

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

		x_dot.trajTimer = 1.0;
		Matrix control = controller.k(x);
		BipedComputer.computeChangeBetweenImpact(x.bipedState, x_dot.bipedState, parameters.bipedParams.bipedParams,
				control);
	}

}
