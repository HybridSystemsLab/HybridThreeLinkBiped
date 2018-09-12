
package biped.computations;

import com.be3short.obj.modification.XMLParser;

import biped.data.BipedParameters;

public class  ParameterEvaluator {

	public static boolean validParameters(BipedParameters params) {

		boolean validEquilibSolutions = validEquilibSolution(params);
		boolean paramsInParamSpace = paramsInParamSpace(params);
		boolean valid = validEquilibSolutions && paramsInParamSpace;
		System.out.println("Parameter validity report for parameters:\n\n" + XMLParser.serializeObject(params) + "\n");
		System.out.println("Parameters in parameter space: " + paramsInParamSpace);
		System.out.println("Valid equilibrium solutions: " + validEquilibSolutions + "\n");
		System.out.println("Solution existance for parameters: " + valid);
		return valid;
	}

	public static boolean validEquilibSolution(BipedParameters params) {

		EquilibriumComputer computer = new EquilibriumComputer(params);

		Double[] valuesToCheck = new Double[]
			{ computer.computeInitialPlantedLegVelocity(), computer.computeInitialPlantedLegVelocity(),
					computer.computeInitialTorsoVelocity(), computer.computeFinalPlantedLegVelocity(),
					computer.computeFinalSwingLegVelocity(), computer.computeFinalTorsoVelocity() };
		for (Double val : valuesToCheck) {
			try {
				if (val.isNaN()) {
					return false;
				}
				if (val.isInfinite()) {
					return false;
				}
			} catch (Exception badValues) {
				return false;
			}

		}
		return true;

		//
		// Double initPlantedVel = computer.computeInitialPlantedLegVelocity();
		// Double initSwingVel = computer.computeInitialSwingLegVelocity();
		// Double initTorsoVel = computer.computeInitialTorsoVelocity();
		// boolean initialVelValidNums = (!initPlantedVel.isNaN()) &&
		// (!initSwingVel.isNaN()) && (!initTorsoVel.isNaN());
		// boolean initialVelNonInfinite = (!initPlantedVel.isInfinite()) &&
		// (!initSwingVel.isInfinite())
		// && (!initTorsoVel.isInfinite());
		// boolean initVelValid = initialVelValidNums && initialVelNonInfinite;
		// Double finalPlantedVel = computer.computeFinalPlantedLegVelocity();
		// Double finalSwingVel = computer.computeFinalSwingLegVelocity();
		// Double finalTorsoVel = computer.computeFinalTorsoVelocity();
		// boolean FinalVelValidNums = (!finalPlantedVel.isNaN()) &&
		// (!finalSwingVel.isNaN()) && (!finalTorsoVel.isNaN());
		// boolean FinalVelNonInffinale = (!finalPlantedVel.isInfinite()) &&
		// (!finalSwingVel.isInfinite())
		// && (!finalTorsoVel.isInfinite());
		// boolean finalVelValid = FinalVelValidNums && FinalVelNonInffinale;

	}

	public static boolean paramsInParamSpace(BipedParameters params) {

		boolean stepAngleValid = (params.stepAngle > 0) && (params.stepAngle < (Math.PI / 2));
		boolean torsoAngleValid = (params.torsoAngle > (-Math.PI / 2)) && (params.torsoAngle < (Math.PI / 2));
		boolean allGoodVals = stepAngleValid && torsoAngleValid;
		Double[] positiveVals = new Double[]
			{ params.gravity, params.hipMass, params.legLength, params.legMass, params.torsoLength, params.torsoMass,
					params.walkSpeed, };
		for (Double val : positiveVals) {
			try {
				boolean pos = val > 0.0;
				allGoodVals = allGoodVals && pos;
			} catch (Exception badVal) {
				allGoodVals = false;
			}
		}
		return allGoodVals;
	}

	public static void main(String args[]) {

		BipedParameters params = BipedParameters.getTestParams();
		ParameterEvaluator.validParameters(params);
	}
}
