
package biped.system.perturbation;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class State extends DataStructure {

	public double perturbationAngle;

	public State(double default_state_value) {

		perturbationAngle = default_state_value;
		this.getProperties().setName("perturbation");
	}

}
