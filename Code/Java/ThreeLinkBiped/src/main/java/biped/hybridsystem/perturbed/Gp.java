
package biped.hybridsystem.perturbed;

import java.util.ArrayList;

import biped.data.BipedInput;
import biped.data.BipedParameters;
import edu.ucsc.cross.jheq.core.model.JumpMap;
import edu.ucsc.cross.jheq.core.variable.RandomVariable;
import firefly.hybridsystem.ParameterDecode;

/**
 * A jump map
 */
public class Gp implements JumpMap<PerturbedState, BipedInput>
{

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(PerturbedState x, PerturbedState x_plus, BipedInput params, ArrayList<Object> parameters)
	{
		PerturbationParameters parameter = ParameterDecode.getAny(PerturbationParameters.class, parameters);
		BipedParameters biped = BipedParameters.get(parameters);
		double perturbationAngle = parameter.perturbationPercent;
		if (parameter.randomize)
		{
			perturbationAngle = RandomVariable.generate(-perturbationAngle, perturbationAngle);
		}
		x_plus.perturbationAngle = perturbationAngle;
	}

}
