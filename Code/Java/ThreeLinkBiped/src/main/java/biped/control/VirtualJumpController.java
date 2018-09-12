
package biped.control;

import java.util.ArrayList;

import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedParameters;
import biped.data.BipedState;
import biped.data.TrajectoryParameters;
import biped.data.VirtualBipedState;
import edu.ucsc.cross.jheq.core.model.ControlType;
import edu.ucsc.cross.jheq.core.model.Controller;
import edu.ucsc.cross.jheq.core.model.Indexed;

public class VirtualJumpController implements Controller<VirtualBipedState, BipedInput, TrajectoryParameters>, Indexed
{

	@Override
	public TrajectoryParameters uC(VirtualBipedState state, BipedInput input, ArrayList<Object> params)
	{
		BipedParameters bipedParams = BipedParameters.get(params);
		TrajectoryParameters equilib = TrajectoryParameters.getEquilibParams(params);
		BipedState x0 = BipedComputer.computeChangeAtImpact(input.plantState, bipedParams);
		BipedState xf = equilib.getFinalState();
		double tPlus = BipedComputer.computeTimeToNextImpactStep(input.referenceState, bipedParams);
		TrajectoryParameters paramz = TrajectoryParameters.compute(x0, xf, tPlus, bipedParams);

		return paramz;
	}

	@Override
	public Object getIndex()
	{
		// TODO Auto-generated method stub
		return ControlType.DISCRETE;
	}

}
