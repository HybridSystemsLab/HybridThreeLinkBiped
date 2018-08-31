
package biped.system.reference;

import biped.computation.BipedComputer;
import biped.data.base.TrajectoryParameters;
import biped.data.virtual.Parameters;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class ReferenceJumpController implements Controller<State, TrajectoryParameters> {

	Parameters parameters;

	/**
	 * Constructor for discrete reference controller
	 * 
	 * @param parameters
	 */
	public ReferenceJumpController(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Discrete update law kappa_r for reference system
	 */
	@Override
	public TrajectoryParameters k(State x) {

		biped.data.base.State x0 = BipedComputer.computeChangeAtImpact(x.bipedState, parameters.bipedParams);
		biped.data.base.State xf = parameters.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeStepTime(parameters.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams);
		return params;
	}

	@Override
	public TrajectoryParameters u() {

		// TODO Auto-generated method stub
		return null;
	}

}
