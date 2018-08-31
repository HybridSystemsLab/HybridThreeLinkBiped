
package biped.system.plant;

import Jama.Matrix;
import biped.computation.BipedComputer;
import biped.data.base.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.FlowMap;
import edu.ucsc.cross.hse.core.modeling.Controller;

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

		BipedComputer.computeChangeBetweenImpact(x, x_dot, parameters.bipedParams, controller.k(x));
	}

}
