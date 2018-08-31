
package biped.system.perturbation;

import edu.ucsc.cross.hse.core.hybridsystem.input.JumpSet;

public class Dp implements JumpSet<State, Parameters> {

	@Override
	public boolean D(State x, Parameters parameters) {

		biped.data.base.State trigger = parameters.plant.getState();
		boolean inD = parameters.plant.getJumpSet().D(trigger, parameters.plant.getParams()); // add logic to determine
																								// if x in

		return inD;
	}

}
