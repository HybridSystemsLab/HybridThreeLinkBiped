
package biped.computations;

import java.util.HashMap;

import Jama.Matrix;
import biped.data.BipedLimb;
import biped.data.BipedMotion;
import biped.data.BipedParameters;
import biped.data.BipedState;
import biped.data.VirtualBipedState;
import biped.hybridsystem.perturbed.PerturbedState;
import edu.ucsc.cross.jheq.core.zerodetect.Zero;
import edu.ucsc.hsl.hse.tools.math.shortcuts.MathShortcuts;

public class BipedComputer
{

	public static Matrix computeJumpMatrixD(BipedState biped, BipedParameters params)
	{

		// COMPUTEJUMPMATRIX Computes the expanded inertial matrix for jumps
		double theta1 = biped.plantedLegAngle;
		double theta2 = biped.swingLegAngle;
		double theta3 = biped.torsoAngle;

		// shortened Math.sin / Math.cos definitions

		double c12 = Math.cos(theta1 - theta2);
		double c13 = Math.cos(theta1 - theta3);

		// matrix D definition
		double D11 = ((5 / 4) * params.legMass + params.hipMass + params.torsoMass) * Math.pow(params.legLength, 2);
		double D12 = -.5 * params.legMass * Math.pow(params.legLength, 2) * c12;
		double D13 = params.torsoMass * params.legLength * params.torsoLength * c13;
		double D14 = ((3 / 2) * params.legMass + params.hipMass + params.torsoMass) * params.legLength
		* Math.cos(theta1);
		double D15 = -(1.5 * params.legMass + params.hipMass + params.torsoMass) * params.legLength * Math.sin(theta1);
		double D21 = D12;
		double D22 = .25 * params.legMass * Math.pow(params.legLength, 2);
		double D23 = 0;
		double D24 = -.5 * params.legMass * params.legLength * Math.cos(theta2);
		double D25 = .5 * params.legMass * params.legLength * Math.sin(theta2);
		double D31 = D13;
		double D32 = D23;
		double D33 = params.torsoMass * Math.pow(params.torsoLength, 2);
		double D34 = params.torsoMass * params.torsoLength * Math.cos(theta3);
		double D35 = -params.torsoMass * params.torsoLength * Math.sin(theta3);
		double D41 = D14;
		double D42 = D24;
		double D43 = D34;
		double D44 = 2 * params.legMass + params.hipMass + params.torsoMass;
		double D45 = 0;
		double D51 = D15;
		double D52 = D25;
		double D53 = D35;
		double D54 = D45;
		double D55 = 2 * params.legMass + params.hipMass + params.torsoMass;

		double[][] dMatrixDouble =
		{
				{ D11, D12, D13, D14, D15 },
				{ D21, D22, D23, D24, D25 },
				{ D31, D32, D33, D34, D35 },
				{ D41, D42, D43, D44, D45 },
				{ D51, D52, D53, D54, D55 } };
		Matrix jumpD = new Matrix(dMatrixDouble);

		return jumpD;
	}

	public static Matrix computeJumpMatrixE(BipedState biped, BipedParameters params)
	{

		// COMPUTEJUMPMATRIX Computes the expanded inertial matrix for jumps
		double theta1 = biped.plantedLegAngle;
		double theta2 = biped.swingLegAngle;

		double[][] jumpEArray =
		{
				{ params.legLength * Math.cos(theta1), -params.legLength * Math.cos(theta2), 0, 1, 0 },
				{ -params.legLength * Math.sin(theta1), params.legLength * Math.sin(theta2), 0, 0, 1 } };
		Matrix jumpE = new Matrix(jumpEArray);
		return jumpE;
	}

	public static Matrix computeFlowDMatrix(BipedState biped, BipedParameters params)
	{

		double theta1 = biped.plantedLegAngle;
		double theta2 = biped.swingLegAngle;
		double theta3 = biped.torsoAngle;

		double D11 = ((5 / 4) * params.legMass + params.hipMass + params.torsoMass) * sq(params.legLength);
		double D12 = -.5 * params.legMass * sq(params.legLength) * cos(theta1 - theta2);
		double D13 = params.torsoMass * params.legLength * params.torsoLength * cos(theta1 - theta3);
		double D21 = D12;
		double D22 = .25 * params.legMass * sq(params.legLength);
		double D23 = 0;
		double D31 = D13;
		double D32 = D23;
		double D33 = params.torsoMass * sq(params.torsoLength);
		double[][] dMatrixDouble =
		{
				{ D11, D12, D13 },
				{ D21, D22, D23 },
				{ D31, D32, D33 } };
		Matrix flowD = new Matrix(dMatrixDouble);
		return flowD;
	}

	public static Matrix computeFlowCMatrix(BipedState biped, BipedParameters params)
	{

		double theta1 = biped.plantedLegAngle;
		double theta2 = biped.swingLegAngle;
		double theta3 = biped.torsoAngle;
		double omega1 = biped.plantedLegVelocity;
		double omega2 = biped.swingLegVelocity;
		double omega3 = biped.torsoVelocity;

		double C11 = 0;
		double C12 = -.5 * params.legMass * sq(params.legLength) * sin(theta1 - theta2) * omega2;
		double C13 = params.torsoMass * params.legLength * params.torsoLength * sin(theta1 - theta3) * omega3;
		double C21 = .5 * params.legMass * sq(params.legLength) * sin(theta1 - theta2) * omega1;
		double C22 = 0;
		double C23 = 0;
		double C31 = -params.torsoMass * params.legLength * params.torsoLength * sin(theta1 - theta3) * omega1;
		double[][] matC =
		{
				{ C11, C12, C13 },
				{ C21, C22, C23 },
				{ C31, C22, C23 } };
		Matrix cFlow = new Matrix(matC);
		return cFlow;
	}

	public static Matrix computeFlowGMatrix(BipedState biped, BipedParameters params)
	{

		double theta1 = biped.plantedLegAngle;
		double theta2 = biped.swingLegAngle;
		double theta3 = biped.torsoAngle;

		double G11 = -.5 * params.gravity * (2 * params.hipMass + 3 * params.legMass + 2 * params.torsoMass)
		* params.legLength * sin(theta1);
		double G21 = .5 * params.gravity * params.legMass * params.legLength * sin(theta2);
		double G31 = -params.gravity * params.torsoMass * params.torsoLength * sin(theta3);
		double[][] matG =
		{
				{ G11 },
				{ G21 },
				{ G31 } };
		Matrix gravityMat = new Matrix(matG);
		return gravityMat;
	}

	public static Double cos(Double val)
	{

		return Math.cos(val);
	}

	public static Double sin(Double val)
	{

		return Math.sin(val);
	}

	public static Double sq(Double val)
	{

		return Math.pow(val, 2);
	}

	public static MathShortcuts m = new MathShortcuts();

	public static Double calculateCoeff(BipedParameters params, Double step_time, BipedState initial_BipedState,
	BipedState final_BipedState, BipedLimb limb, Integer coef_num)
	{

		HashMap<BipedMotion, Double> info = initial_BipedState.getLimbState(limb);
		Double thetai = (Double) info.get(BipedMotion.ANGLE);
		Double thetaf = params.getFinalAngle(limb);
		Double omegai = (Double) info.get(BipedMotion.ANGULAR_VELOCITY);
		Double omegaf = final_BipedState.getLimbState(limb).get(BipedMotion.ANGULAR_VELOCITY);

		Double b = null;
		if (coef_num == 0)
		{
			b = calculateB0(thetai, thetaf, omegai, omegaf, step_time);
		} else if (coef_num == 1)
		{
			b = calculateB1(thetai, thetaf, omegai, omegaf, step_time);
		}
		return b;
	}

	public static Double calculateB0(Double thetai, Double thetaf, Double omegai, Double omegaf, Double step_time)
	{

		Double b1 = -(2 * (3 * thetai - 3 * thetaf + omegaf * step_time + 2 * omegai * step_time)) / m.sq(step_time);
		return b1;
	}

	public static Double calculateB1(Double thetai, Double thetaf, Double omegai, Double omegaf, Double step_time)
	{

		Double b1 = -(6 * (2 * thetai - 2 * thetaf + omegaf * step_time + omegai * step_time)) / m.cu(step_time);
		return b1;
	}

	public static HashMap<BipedLimb, Double> computeCoefficient(BipedParameters params, Double step_time,
	BipedState initial_BipedState, BipedState final_BipedState, Integer coef_num)
	{

		HashMap<BipedLimb, Double> coef = new HashMap<BipedLimb, Double>();
		for (BipedLimb limb : BipedLimb.values())
		{
			coef.put(limb, calculateCoeff(params, step_time, initial_BipedState, final_BipedState, limb, coef_num));
		}
		return coef;
	}

	public static void computeDiscreteChange(BipedState biped, BipedState biped_plus, BipedParameters params)
	{

		double theta1 = biped.plantedLegAngle;
		double theta2 = biped.swingLegAngle;
		double theta3 = biped.torsoAngle;
		double omega1 = biped.plantedLegVelocity;
		double omega2 = biped.swingLegVelocity;
		double omega3 = biped.torsoVelocity;

		double omegapDot = (4 * params.hipMass * omega1 * m.cos(theta1 - theta2) - params.legMass * omega2
		+ 2 * params.legMass * omega1 * m.cos(theta1 - theta2) + 2 * params.torsoMass * omega1 * m.cos(theta1 - theta2)
		- 2 * params.torsoMass * omega1 * m.cos(theta1 + theta2 - 2 * theta3))
		/ (4 * params.hipMass + 3 * params.legMass + 2 * params.torsoMass
		- 2 * params.legMass * m.cos(2 * theta1 - 2 * theta2) - 2 * params.torsoMass * m.cos(2 * theta2 - 2 * theta3));

		double omegasDot = (4 * params.hipMass * omega1 + 3 * params.legMass * omega1 - 2 * params.legMass * omega2
		+ 4 * params.legMass * omega2 * m.sq(m.sin(theta1 / 2 - theta2 / 2))
		- 8 * params.hipMass * omega1 * m.sq(m.sin(theta1 - theta2))
		- 8 * params.legMass * omega1 * m.sq(m.sin(theta1 - theta2))
		- 4 * params.torsoMass * omega1 * m.sq(m.sin(theta1 - theta2))
		+ 4 * params.torsoMass * omega1 * m.sq(m.sin(theta1 - theta3)))
		/ (4 * params.hipMass + params.legMass + 4 * params.legMass * m.sq(m.sin(theta1 - theta2))
		+ 4 * params.torsoMass * m.sq(m.sin(theta2 - theta3)));

		double omegatDot = (4 * params.torsoLength * params.hipMass * omega3
		+ 3 * params.torsoLength * params.legMass * omega3 + 2 * params.torsoLength * params.torsoMass * omega3
		- 2 * params.torsoLength * params.legMass * omega3 * m.cos(2 * theta1 - 2 * theta2)
		- 2 * params.torsoLength * params.torsoMass * omega3 * m.cos(2 * theta2 - 2 * theta3)
		- 2 * params.legLength * params.hipMass * omega1 * m.cos(theta1 - 2 * theta2 + theta3)
		- 2 * params.legLength * params.legMass * omega1 * m.cos(theta1 - 2 * theta2 + theta3)
		- 2 * params.legLength * params.torsoMass * omega1 * m.cos(theta1 - 2 * theta2 + theta3)
		- params.legLength * params.legMass * omega1 * m.cos(2 * theta2 - 3 * theta1 + theta3)
		+ 2 * params.legLength * params.hipMass * omega1 * m.cos(theta1 - theta3)
		+ 2 * params.legLength * params.legMass * omega1 * m.cos(theta1 - theta3)
		+ params.legLength * params.legMass * omega2 * m.cos(theta2 - theta3)
		+ 2 * params.legLength * params.torsoMass * omega1 * m.cos(theta1 - theta3))
		/ (params.torsoLength * (4 * params.hipMass + 3 * params.legMass + 2 * params.torsoMass
		- 2 * params.legMass * m.cos(2 * theta1 - 2 * theta2) - 2 * params.torsoMass * m.cos(2 * theta2 - 2 * theta3)));
		biped_plus.plantedLegAngle = (theta2);
		biped_plus.swingLegAngle = (theta1);
		biped_plus.torsoAngle = (biped.torsoAngle);
		biped_plus.plantedLegVelocity = (omegapDot);
		biped_plus.swingLegVelocity = (omegasDot);
		biped_plus.torsoVelocity = (omegatDot);

	}

	public Matrix computeControlInput(Matrix accelerations, BipedState biped, BipedParameters parameters)
	{

		Matrix matD = BipedComputer.computeFlowDMatrix(biped, parameters);
		Matrix matC = BipedComputer.computeFlowCMatrix(biped, parameters);
		Matrix matG = BipedComputer.computeFlowGMatrix(biped, parameters);
		Matrix vels = biped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
		.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public static double computeStepRemainder(BipedState BipedState, BipedParameters parameters)
	{

		return computeStepRemainder(BipedState, parameters, null);
	}

	public static double computeStepRemainder(BipedState BipedState, BipedParameters parameters, PerturbedState perturb)
	{

		double perturbAngle = 0.0;
		if (perturb != null)
		{
			perturbAngle = perturb.perturbationAngle * parameters.stepAngle;
		}
		double hVal = (parameters.stepAngle + perturbAngle) - BipedState.plantedLegAngle;
		return hVal;
	}

	public static Double computeTimeToNextImpactStep(VirtualBipedState biped_BipedState, BipedParameters params)
	{

		Double stepTime = params.getStepTime();
		Double timeToNext = stepTime - biped_BipedState.trajTimer;

		if (timeToNext > (stepTime / 2.0))
		{
			timeToNext = stepTime - biped_BipedState.trajTimer;
		} else
		{
			timeToNext = (2.0 * stepTime) - biped_BipedState.trajTimer;
		}
		return timeToNext;
	}

	public static boolean isImpactOccurring(BipedState biped_BipedState, BipedParameters params,
	PerturbedState perturbation)
	{

		boolean stepAngleReached = ((params.stepAngle
		+ perturbation.perturbationAngle) <= biped_BipedState.plantedLegAngle);//

		return stepAngleReached;
	}

	public static boolean isImpactOccurring(BipedState biped_BipedState, BipedParameters params)
	{

		// boolean stepAngleReached = (params.stepAngle <=
		// biped_BipedState.plantedLegAngle);//
		boolean stepAngleReached = (Zero.equal(biped_BipedState, params.stepAngle - biped_BipedState.plantedLegAngle)
		&& biped_BipedState.plantedLegVelocity >= 0.0);//
		return stepAngleReached;
	}

	public static BipedState computeChangeAtImpact(BipedState biped_BipedState, BipedState biped_plus,
	BipedParameters params, boolean return_copy)
	{

		return computeChangeAtImpact(biped_BipedState, biped_plus, params);
	}

	public static BipedState computeChangeAtImpact(BipedState biped, BipedState biped_plus, BipedParameters params)
	{

		double theta1 = biped.plantedLegAngle;
		double theta2 = biped.swingLegAngle;
		double theta3 = biped.torsoAngle;
		double omega1 = biped.plantedLegVelocity;
		double omega2 = biped.swingLegVelocity;
		double omega3 = biped.torsoVelocity;

		double omegapDot = (4 * params.hipMass * omega1 * m.cos(theta1 - theta2) - params.legMass * omega2
		+ 2 * params.legMass * omega1 * m.cos(theta1 - theta2) + 2 * params.torsoMass * omega1 * m.cos(theta1 - theta2)
		- 2 * params.torsoMass * omega1 * m.cos(theta1 + theta2 - 2 * theta3))
		/ (4 * params.hipMass + 3 * params.legMass + 2 * params.torsoMass
		- 2 * params.legMass * m.cos(2 * theta1 - 2 * theta2) - 2 * params.torsoMass * m.cos(2 * theta2 - 2 * theta3));

		double omegasDot = (4 * params.hipMass * omega1 + 3 * params.legMass * omega1 - 2 * params.legMass * omega2
		+ 4 * params.legMass * omega2 * m.sq(m.sin(theta1 / 2 - theta2 / 2))
		- 8 * params.hipMass * omega1 * m.sq(m.sin(theta1 - theta2))
		- 8 * params.legMass * omega1 * m.sq(m.sin(theta1 - theta2))
		- 4 * params.torsoMass * omega1 * m.sq(m.sin(theta1 - theta2))
		+ 4 * params.torsoMass * omega1 * m.sq(m.sin(theta1 - theta3)))
		/ (4 * params.hipMass + params.legMass + 4 * params.legMass * m.sq(m.sin(theta1 - theta2))
		+ 4 * params.torsoMass * m.sq(m.sin(theta2 - theta3)));

		double omegatDot = (4 * params.torsoLength * params.hipMass * omega3
		+ 3 * params.torsoLength * params.legMass * omega3 + 2 * params.torsoLength * params.torsoMass * omega3
		- 2 * params.torsoLength * params.legMass * omega3 * m.cos(2 * theta1 - 2 * theta2)
		- 2 * params.torsoLength * params.torsoMass * omega3 * m.cos(2 * theta2 - 2 * theta3)
		- 2 * params.legLength * params.hipMass * omega1 * m.cos(theta1 - 2 * theta2 + theta3)
		- 2 * params.legLength * params.legMass * omega1 * m.cos(theta1 - 2 * theta2 + theta3)
		- 2 * params.legLength * params.torsoMass * omega1 * m.cos(theta1 - 2 * theta2 + theta3)
		- params.legLength * params.legMass * omega1 * m.cos(2 * theta2 - 3 * theta1 + theta3)
		+ 2 * params.legLength * params.hipMass * omega1 * m.cos(theta1 - theta3)
		+ 2 * params.legLength * params.legMass * omega1 * m.cos(theta1 - theta3)
		+ params.legLength * params.legMass * omega2 * m.cos(theta2 - theta3)
		+ 2 * params.legLength * params.torsoMass * omega1 * m.cos(theta1 - theta3))
		/ (params.torsoLength * (4 * params.hipMass + 3 * params.legMass + 2 * params.torsoMass
		- 2 * params.legMass * m.cos(2 * theta1 - 2 * theta2) - 2 * params.torsoMass * m.cos(2 * theta2 - 2 * theta3)));
		biped_plus.plantedLegAngle = (theta2);
		biped_plus.swingLegAngle = (theta1);
		biped_plus.torsoAngle = (biped.torsoAngle);
		biped_plus.plantedLegVelocity = (omegapDot);
		biped_plus.swingLegVelocity = (omegasDot);
		biped_plus.torsoVelocity = (omegatDot);

		return biped_plus;
	}

	public static BipedState computeChangeAtImpact(BipedState biped, BipedParameters params)
	{

		BipedState bipedPlus = new BipedState();

		return computeChangeAtImpact(biped, bipedPlus, params);
	}

	public static Double computeStepTime(BipedParameters params)
	{

		Double t = (2.0 * params.legLength * Math.sin(params.stepAngle)) / params.walkSpeed;
		return t;
	}

	public static boolean isInStateSpace(BipedState biped)
	{
		boolean inStateSpace = true;
		inStateSpace = inStateSpace && (Math.abs(biped.plantedLegAngle) <= (Math.PI / 2) + .1);
		return inStateSpace;
	}

	public static void computeChangeBetweenImpact(BipedState biped_BipedState, BipedState biped_dot,
	BipedParameters params, Matrix control_input)
	{

		double omega1 = biped_BipedState.plantedLegVelocity;
		double omega2 = biped_BipedState.swingLegVelocity;
		double omega3 = biped_BipedState.torsoVelocity;

		Matrix matD = computeFlowDMatrix(biped_BipedState, params);
		Matrix matC = computeFlowCMatrix(biped_BipedState, params);
		Matrix matG = computeFlowGMatrix(biped_BipedState, params);
		Matrix vels = biped_BipedState.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);

		Matrix controlInput = control_input;
		Matrix accelerations = (matD.inverse().times((matC.times(vels)).minus(matG)))
		.plus(params.getTorqueRelationship().times(controlInput));
		double[][] accels = accelerations.getArray();
		biped_dot.plantedLegVelocity = (accels[0][0]);
		biped_dot.swingLegVelocity = (accels[1][0]);
		biped_dot.torsoVelocity = (accels[2][0]);
		biped_dot.plantedLegAngle = (omega1);
		biped_dot.swingLegAngle = (omega2);
		biped_dot.torsoAngle = (omega3);

	}
}
