
package biped.system.virtual;

import biped.computation.BipedComputer;
import biped.data.base.TrajectoryParameters;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class VirtualJumpController implements Controller<State, TrajectoryParameters> {

	Parameters parameters;

	/**
	 * Constructor for discrete reference controller
	 * 
	 * @param parameters
	 */
	public VirtualJumpController(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Discrete update law kappa_r for reference system
	 */
	@Override
	public TrajectoryParameters k(State x) {

		biped.data.base.State plant = parameters.plant.y();
		biped.data.base.State x0 = BipedComputer.computeChangeAtImpact(plant, parameters.bipedParams.bipedParams);
		biped.data.base.State xf = parameters.bipedParams.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeTimeToNextImpactStep(parameters.reference.y(),
				parameters.bipedParams.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams.bipedParams);
		return params;
	}

	@Override
	public TrajectoryParameters u() {

		// TODO Auto-generated method stub
		return null;
	}

}
