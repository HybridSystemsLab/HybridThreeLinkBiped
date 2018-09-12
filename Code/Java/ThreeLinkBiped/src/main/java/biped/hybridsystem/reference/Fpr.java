
package biped.hybridsystem.reference;

import java.util.ArrayList;

import Jama.Matrix;
import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.BipedState;
import biped.data.VirtualBipedState;
import edu.ucsc.cross.jheq.core.model.ControlType;
import edu.ucsc.cross.jheq.core.model.Controller;
import edu.ucsc.cross.jheq.core.model.FlowMap;

/**
 * A flow map
 */
public class Fpr implements FlowMap<VirtualBipedState, BipedInput>
{

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            state derivative values
	 */
	@Override
	public void F(VirtualBipedState x, VirtualBipedState x_dot, BipedInput input, ArrayList<Object> parameters)
	{

		x_dot.trajTimer = 1.0;
		Controller<BipedState, BipedInput, Matrix> controller = Controller.get(parameters, ControlType.CONTINUOUS);
		BipedParameters bipedParams = BipedParameters.get(parameters);
		BipedComputer.computeChangeBetweenImpact(x, x_dot, bipedParams, controller.uC(x, input, parameters));
	}

}
