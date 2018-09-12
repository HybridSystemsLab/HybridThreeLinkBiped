
package biped.hybridsystem.plant;

import java.util.ArrayList;

import Jama.Matrix;
import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.BipedState;
import edu.ucsc.cross.jheq.core.model.ControlType;
import edu.ucsc.cross.jheq.core.model.Controller;
import edu.ucsc.cross.jheq.core.model.FlowMap;

/**
 * A flow map
 */
public class Fpl implements FlowMap<BipedState, BipedInput>
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
	public void F(BipedState x, BipedState x_dot, BipedInput input, ArrayList<Object> parameters)
	{
		Controller<BipedState, BipedInput, Matrix> controller = Controller.get(parameters, ControlType.CONTINUOUS);
		BipedParameters bipedParams = BipedParameters.get(parameters);
		BipedComputer.computeChangeBetweenImpact(x, x_dot, bipedParams, controller.uC(x, input, parameters));
	}

}
