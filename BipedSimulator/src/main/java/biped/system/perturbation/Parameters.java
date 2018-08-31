
package biped.system.perturbation;

import edu.ucsc.cross.hse.core.hybridsystem.input.HybridSystem;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class Parameters extends DataStructure {

	public double perturbationPercent;

	public boolean randomize;

	public biped.data.base.Parameters bipedParameters;

	public HybridSystem<biped.data.base.State, biped.system.plant.Parameters> plant;

	public Parameters(biped.data.base.Parameters biped_parameters, double parameter_value, boolean randomize) {

		this.bipedParameters = biped_parameters;
		perturbationPercent = parameter_value;
		this.randomize = randomize;

	}
}
