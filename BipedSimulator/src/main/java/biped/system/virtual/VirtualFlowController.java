
package biped.system.virtual;

import Jama.Matrix;
import biped.computation.BipedComputer;
import biped.data.base.BipedLimb;
import biped.data.base.BipedMotion;
import biped.data.virtual.Parameters;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class VirtualFlowController implements Controller<State, Matrix> {

	public Parameters parameters;

	public VirtualFlowController(Parameters parameters) {

		this.parameters = parameters;
	}

	public Matrix computeControlInput(State biped, Matrix accelerations) {

		Matrix matD = BipedComputer.computeFlowDMatrix(biped.bipedState, parameters.bipedParams);
		Matrix matC = BipedComputer.computeFlowCMatrix(biped.bipedState, parameters.bipedParams);
		Matrix matG = BipedComputer.computeFlowGMatrix(biped.bipedState, parameters.bipedParams);
		Matrix vels = biped.bipedState.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.bipedParams.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
				.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public static Matrix computeOrbitTrackingAccelerations(State biped) {

		double[][] mat = new double[3][1];
		Double currTime = biped.trajTimer;
		Integer index = 0;

		mat[0][0] = biped.trajectoryParameters.getB0(BipedLimb.PLANTED_LEG)
				- biped.trajectoryParameters.getB1(BipedLimb.PLANTED_LEG) * currTime;
		mat[1][0] = biped.trajectoryParameters.getB0(BipedLimb.SWING_LEG)
				- biped.trajectoryParameters.getB1(BipedLimb.SWING_LEG) * currTime;
		mat[2][0] = biped.trajectoryParameters.getB0(BipedLimb.TORSO)
				- biped.trajectoryParameters.getB1(BipedLimb.TORSO) * currTime;
		index++;

		Matrix accel = new Matrix(mat);
		return accel;
	}

	@Override
	public Matrix k(State state) {

		Matrix accelerations = computeOrbitTrackingAccelerations(state);
		Matrix control = computeControlInput(state, accelerations);
		return control;
	}

	@Override
	public Matrix u() {

		// TODO Auto-generated method stub
		return null;
	}

}
