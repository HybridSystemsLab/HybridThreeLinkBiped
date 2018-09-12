
package biped.control;

import java.util.ArrayList;

import Jama.Matrix;
import biped.computations.BipedComputer;
import biped.data.BipedInput;
import biped.data.BipedMotion;
import biped.data.BipedParameters;
import biped.data.BipedState;
import edu.ucsc.cross.jheq.core.model.Controller;

public class PlantFlowController implements Controller<BipedState, BipedInput, Matrix>
{

	public Matrix computeControlInput(BipedState realBiped, Matrix accelerations, ArrayList<Object> params)
	{
		BipedParameters bipedParams = BipedParameters.get(params);
		Matrix matD = BipedComputer.computeFlowDMatrix(realBiped, bipedParams);
		Matrix matC = BipedComputer.computeFlowCMatrix(realBiped, bipedParams);
		Matrix matG = BipedComputer.computeFlowGMatrix(realBiped, bipedParams);
		Matrix vels = realBiped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = bipedParams.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
		.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public Matrix getComputedAcceleration(BipedState realBiped, BipedState virtualBiped, ArrayList<Object> params)
	{
		PlantFlowControlParameters controlParam = PlantFlowControlParameters.get(params);
		double[][] positionDifference =
		{
				{ realBiped.plantedLegAngle - virtualBiped.plantedLegAngle },
				{ realBiped.swingLegAngle - virtualBiped.swingLegAngle },
				{ realBiped.torsoAngle - virtualBiped.torsoAngle } };
		Matrix deltaPos = new Matrix(positionDifference);
		double[][] velocityDifference =
		{
				{ realBiped.plantedLegVelocity - virtualBiped.plantedLegVelocity },
				{ realBiped.swingLegVelocity - virtualBiped.swingLegVelocity },
				{ realBiped.torsoVelocity - virtualBiped.torsoVelocity } };
		Matrix deltaVel = new Matrix(velocityDifference);
		deltaPos = deltaPos.times(-controlParam.kOne);
		deltaVel = deltaVel.times(-controlParam.kTwo);
		// TODO Auto-generated method stub
		return deltaPos.plusEquals(deltaVel);
	}

	@Override
	public Matrix uC(BipedState state, BipedInput input, ArrayList<Object> params)
	{
		Matrix virtualAcceleration = VirtualFlowController.computeOrbitTrackingAccelerations(input.virtualState);
		Matrix controlAccel = virtualAcceleration
		.plusEquals(getComputedAcceleration(state, input.virtualState, params));
		Matrix unconstrained = computeControlInput(state, controlAccel, params);

		PlantFlowControlParameters controlParam = PlantFlowControlParameters.get(params);
		Matrix constrained = controlParam.getInputTorquesConstrained(unconstrained);
		return constrained;
	}

}