
package biped.control;

import java.util.ArrayList;

import Jama.Matrix;
import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedLimb;
import biped.data.BipedMotion;
import biped.data.BipedParameters;
import biped.data.VirtualBipedState;
import edu.ucsc.cross.jheq.core.model.ControlType;
import edu.ucsc.cross.jheq.core.model.Controller;
import edu.ucsc.cross.jheq.core.model.Indexed;

public class VirtualFlowController implements Controller<VirtualBipedState, BipedInput, Matrix>, Indexed
{

	public VirtualFlowController()
	{

	}

	public Matrix computeControlInput(VirtualBipedState biped, Matrix accelerations, ArrayList<Object> params)
	{
		BipedParameters bipedParams = BipedParameters.get(params);
		Matrix matD = BipedComputer.computeFlowDMatrix(biped, bipedParams);
		Matrix matC = BipedComputer.computeFlowCMatrix(biped, bipedParams);
		Matrix matG = BipedComputer.computeFlowGMatrix(biped, bipedParams);
		Matrix vels = biped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = bipedParams.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
		.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public static Matrix computeOrbitTrackingAccelerations(VirtualBipedState biped)
	{

		double[][] mat = new double[3][1];
		Double currTime = biped.trajTimer;
		Integer index = 0;

		mat[0][0] = biped.getTrajectoryParameters().getB0(BipedLimb.PLANTED_LEG)
		- biped.getTrajectoryParameters().getB1(BipedLimb.PLANTED_LEG) * currTime;
		mat[1][0] = biped.getTrajectoryParameters().getB0(BipedLimb.SWING_LEG)
		- biped.getTrajectoryParameters().getB1(BipedLimb.SWING_LEG) * currTime;
		mat[2][0] = biped.getTrajectoryParameters().getB0(BipedLimb.TORSO)
		- biped.getTrajectoryParameters().getB1(BipedLimb.TORSO) * currTime;
		index++;

		Matrix accel = new Matrix(mat);
		return accel;
	}

	@Override
	public Matrix uC(VirtualBipedState state, BipedInput input, ArrayList<Object> params)
	{

		Matrix accelerations = computeOrbitTrackingAccelerations(state);
		Matrix control = computeControlInput(state, accelerations, params);
		return control;
	}

	@Override
	public Object getIndex()
	{
		// TODO Auto-generated method stub
		return ControlType.CONTINUOUS;
	}

}
