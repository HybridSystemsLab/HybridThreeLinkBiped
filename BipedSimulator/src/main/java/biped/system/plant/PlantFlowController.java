
package biped.system.plant;

import Jama.Matrix;
import biped.computation.BipedComputer;
import biped.data.base.BipedMotion;
import biped.data.base.State;
import biped.system.virtual.VirtualFlowController;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class PlantFlowController implements Controller<State, Matrix> {

	public Parameters parameters;

	public Double kOne;

	public Double kTwo;

	public PlantFlowController(Parameters parameters) {

		this(parameters, 2000.0, 100.0);
	}

	public PlantFlowController(Parameters parameters, double k_one, double k_two) {

		this.parameters = parameters;
		kOne = (2000.0);
		kTwo = (100.0);
	}

	public Matrix computeControlInput(State realBiped, Matrix accelerations) {

		Matrix matD = BipedComputer.computeFlowDMatrix(realBiped, parameters.bipedParams);
		Matrix matC = BipedComputer.computeFlowCMatrix(realBiped, parameters.bipedParams);
		Matrix matG = BipedComputer.computeFlowGMatrix(realBiped, parameters.bipedParams);
		Matrix vels = realBiped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.bipedParams.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
				.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public Matrix getComputedAcceleration(State realBiped, State virtualBiped) {

		double[][] positionDifference = { { realBiped.plantedLegAngle - virtualBiped.plantedLegAngle },
				{ realBiped.swingLegAngle - virtualBiped.swingLegAngle },
				{ realBiped.torsoAngle - virtualBiped.torsoAngle } };
		Matrix deltaPos = new Matrix(positionDifference);
		double[][] velocityDifference = { { realBiped.plantedLegVelocity - virtualBiped.plantedLegVelocity },
				{ realBiped.swingLegVelocity - virtualBiped.swingLegVelocity },
				{ realBiped.torsoVelocity - virtualBiped.torsoVelocity } };
		Matrix deltaVel = new Matrix(velocityDifference);
		deltaPos = deltaPos.times(-kOne);
		deltaVel = deltaVel.times(-kTwo);
		// TODO Auto-generated method stub
		return deltaPos.plusEquals(deltaVel);
	}

	@Override
	public Matrix k(State state) {

		Matrix virtualAcceleration = VirtualFlowController.computeOrbitTrackingAccelerations(parameters.virtual.y());
		Matrix controlAccel = virtualAcceleration
				.plusEquals(getComputedAcceleration(state, parameters.virtual.y().bipedState));

		return computeControlInput(state, controlAccel);
	}

	@Override
	public Matrix u() {

		// TODO Auto-generated method stub
		return null;
	}

}