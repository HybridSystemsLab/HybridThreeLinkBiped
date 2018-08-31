
package biped.computation;

import biped.data.base.Parameters;
import biped.data.base.State;
import biped.data.base.TrajectoryParameters;

public class EquilibriumComputer {

	private Double mL;

	private Double mT;

	private Double mH;

	private Double sA;

	private Double tA;

	private Double lL;

	private Double lT;

	private Double t;

	private Parameters params;

	public EquilibriumComputer(Parameters params) {

		this.params = params;
		mL = params.legMass;
		mT = params.torsoMass;
		mH = params.hipMass;
		sA = params.stepAngle;
		tA = params.torsoAngle;
		lL = params.legLength;
		lT = params.torsoLength;
		t = params.getStepTime();
	}

	public TrajectoryParameters getEquilibriumParameters() {

		TrajectoryParameters trajParams = null;
		State in = getInitialEquilibState();
		State fin = getFinalEquilibState();
		trajParams = new TrajectoryParameters(in, fin, params);
		return trajParams;
	}

	public static TrajectoryParameters getEquilibriumParameters(Parameters params) {

		TrajectoryParameters trajParams = null;
		EquilibriumComputer comp = new EquilibriumComputer(params);
		State in = comp.getInitialEquilibState();
		State fin = comp.getFinalEquilibState();
		trajParams = new TrajectoryParameters(in, fin, params);
		return trajParams;
	}

	public State getInitialEquilibState() {

		State initial = new State();
		initial.plantedLegAngle = (-params.stepAngle);
		initial.swingLegAngle = (params.stepAngle);
		initial.torsoAngle = (params.torsoAngle);
		initial.plantedLegVelocity = (computeInitialPlantedLegVelocity());
		initial.swingLegVelocity = (computeInitialSwingLegVelocity());
		initial.torsoVelocity = (computeInitialTorsoVelocity());
		return initial;

	}

	public State getFinalEquilibState() {

		State finalState = new State();// .component().copy(false, true);
		finalState.plantedLegAngle = (params.stepAngle);
		finalState.swingLegAngle = (-params.stepAngle);
		finalState.torsoAngle = (params.torsoAngle);
		finalState.plantedLegVelocity = (computeFinalPlantedLegVelocity());
		finalState.swingLegVelocity = (computeFinalSwingLegVelocity());
		finalState.torsoVelocity = (computeFinalTorsoVelocity());
		return finalState;

	}

	public Double computeInitialPlantedLegVelocity() {

		return -(6 * mL * sA - cos(2 * sA) * (24 * mH * sA + 12 * mL * sA + 12 * mT * sA) + 12 * mT * sA * cos(2 * tA))
				/ (t * (4 * mH - mL + 2 * mT + 8 * mH * cos(2 * sA) - 2 * mL * cos(4 * sA) + 4 * mT * cos(2 * sA)
						- 4 * mT * cos(2 * tA) - 2 * mT * cos(2 * sA + 2 * tA)));// equilibPlantedLegVel;

	}

	public Double computeInitialSwingLegVelocity() {

		return -(6 * sA
				* (4 * mL * sq(sin(sA)) - 7 * mL - 4 * mH - 4 * mT * sq(sin(sA - tA)) + 8 * mH * sq(sin(2 * sA))
						+ 8 * mL * sq(sin(2 * sA)) + 4 * mT * sq(sin(2 * sA))))
				/ (t * (12 * mH - 3 * mL - 16 * mH * sq(sin(sA)) - 8 * mT * sq(sin(sA)) + 8 * mT * sq(sin(tA))
						+ 4 * mL * sq(sin(2 * sA)) + 4 * mT * sq(sin(sA + tA))));
	}

	public Double computeInitialTorsoVelocity() {

		return (2 * lL * sA * (8 * sq(mH) * cos(sA - tA) - 8 * sq(mH) * cos(3 * sA + tA) + 3 * sq(mL) * cos(sA - tA)
				- 6 * sq(mL) * cos(3 * sA + tA) + sq(mL) * cos(5 * sA + tA) + 2 * sq(mL) * cos(7 * sA + tA)
				+ 6 * sq(mT) * cos(sA - tA) - 2 * sq(mT) * cos(sA + 3 * tA) - 6 * sq(mT) * cos(3 * sA + tA)
				- 5 * sq(mL) * cos(3 * sA - tA) - 3 * sq(mL) * cos(5 * sA - tA) + 2 * sq(mL) * cos(7 * sA - tA)
				+ sq(mL) * cos(9 * sA - tA) + 2 * sq(mT) * cos(5 * sA + 3 * tA) - sq(mL) * cos(sA + tA)
				+ 8 * mH * mL * cos(sA - tA) - 16 * mH * mL * cos(3 * sA + tA) + 2 * mH * mL * cos(7 * sA + tA)
				+ 14 * mH * mT * cos(sA - tA) - 2 * mH * mT * cos(sA + 3 * tA) - 14 * mH * mT * cos(3 * sA + tA)
				+ 10 * mL * mT * cos(sA - tA) + 2 * mL * mT * cos(sA - 3 * tA) - 12 * mL * mT * cos(3 * sA + tA)
				+ 2 * mL * mT * cos(5 * sA + tA) + 3 * mL * mT * cos(7 * sA + tA) - 8 * mH * mL * cos(3 * sA - tA)
				- 6 * mH * mL * cos(5 * sA - tA) + 2 * mH * mT * cos(5 * sA + 3 * tA) - 4 * mL * mT * cos(3 * sA - tA)
				+ mL * mT * cos(3 * sA - 3 * tA) + mL * mT * cos(3 * sA + 3 * tA) - 4 * mL * mT * cos(5 * sA - tA)
				+ 2 * mL * mT * cos(5 * sA + 3 * tA) - 4 * mH * mL * cos(sA + tA) - mL * mT * cos(sA + tA)))
				/ (lT * t
						* (8 * mH * mL + 16 * mH * mT + 4 * mL * mT + 32 * sq(mH) * cos(2 * sA)
								- 4 * sq(mL) * cos(4 * sA) + 2 * sq(mL) * cos(8 * sA) + 12 * sq(mT) * cos(2 * sA)
								- 12 * sq(mT) * cos(2 * tA) - 8 * sq(mT) * cos(2 * sA + 2 * tA)
								+ 4 * sq(mT) * cos(2 * sA + 4 * tA) - 4 * sq(mT) * cos(4 * sA + 2 * tA)
								+ 2 * sq(mT) * cos(4 * sA + 4 * tA) + 16 * sq(mH) - sq(mL) + 6 * sq(mT)
								+ 16 * mH * mL * cos(2 * sA) - 16 * mH * mL * cos(4 * sA) - 8 * mH * mL * cos(6 * sA)
								+ 32 * mH * mT * cos(2 * sA) + 8 * mL * mT * cos(2 * sA) - 8 * mL * mT * cos(4 * sA)
								- 4 * mL * mT * cos(6 * sA) - 24 * mH * mT * cos(2 * tA) - 12 * mL * mT * cos(2 * tA)
								- 16 * mH * mT * cos(2 * sA + 2 * tA) - 8 * mH * mT * cos(4 * sA + 2 * tA)
								+ 4 * mL * mT * cos(2 * sA - 2 * tA) - 4 * mL * mT * cos(2 * sA + 2 * tA)
								+ 4 * mL * mT * cos(4 * sA - 2 * tA) + 4 * mL * mT * cos(4 * sA + 2 * tA)
								+ 4 * mL * mT * cos(6 * sA + 2 * tA)));

	}

	public Double computeFinalPlantedLegVelocity() {

		return (6 * sA
				* (4 * mH - 5 * mL + 8 * mL * sq(sin(sA)) + 4 * mL * sq(sin(2 * sA)) + 4 * mT * sq(sin(sA + tA))))
				/ (t * (12 * mH - 3 * mL - 16 * mH * sq(sin(sA)) - 8 * mT * sq(sin(sA)) + 8 * mT * sq(sin(tA))
						+ 4 * mL * sq(sin(2 * sA)) + 4 * mT * sq(sin(sA + tA))));
	}

	public Double computeFinalSwingLegVelocity() {

		return (6 * sA * (16 * mH * sq(sin(sA)) - 11 * mL - 20 * mH + 8 * mL * sq(sin(sA)) - 4 * mT * sq(sin(sA))
				- 20 * mT * sq(sin(tA)) + 16 * mH * sq(sin(2 * sA)) + 12 * mL * sq(sin(2 * sA))
				+ 8 * mT * sq(sin(2 * sA)) + 2 * mT * sin(2 * sA) * sin(2 * tA) + 24 * mT * sq(sin(sA)) * sq(sin(tA))))
				/ (t * (12 * mH - 3 * mL - 16 * mH * sq(sin(sA)) - 8 * mT * sq(sin(sA)) + 8 * mT * sq(sin(tA))
						+ 4 * mL * sq(sin(2 * sA)) + 4 * mT * sq(sin(sA + tA))));
	}

	public Double computeFinalTorsoVelocity() {

		return -(4 * lL * sA * (8 * sq(mH) * cos(sA - tA) - 8 * sq(mH) * cos(3 * sA + tA) + 3 * sq(mL) * cos(sA - tA)
				- 6 * sq(mL) * cos(3 * sA + tA) + sq(mL) * cos(5 * sA + tA) + 2 * sq(mL) * cos(7 * sA + tA)
				+ 6 * sq(mT) * cos(sA - tA) - 2 * sq(mT) * cos(sA + 3 * tA) - 6 * sq(mT) * cos(3 * sA + tA)
				- 5 * sq(mL) * cos(3 * sA - tA) - 3 * sq(mL) * cos(5 * sA - tA) + 2 * sq(mL) * cos(7 * sA - tA)
				+ sq(mL) * cos(9 * sA - tA) + 2 * sq(mT) * cos(5 * sA + 3 * tA) - sq(mL) * cos(sA + tA)
				+ 8 * mH * mL * cos(sA - tA) - 16 * mH * mL * cos(3 * sA + tA) + 2 * mH * mL * cos(7 * sA + tA)
				+ 14 * mH * mT * cos(sA - tA) - 2 * mH * mT * cos(sA + 3 * tA) - 14 * mH * mT * cos(3 * sA + tA)
				+ 10 * mL * mT * cos(sA - tA) + 2 * mL * mT * cos(sA - 3 * tA) - 12 * mL * mT * cos(3 * sA + tA)
				+ 2 * mL * mT * cos(5 * sA + tA) + 3 * mL * mT * cos(7 * sA + tA) - 8 * mH * mL * cos(3 * sA - tA)
				- 6 * mH * mL * cos(5 * sA - tA) + 2 * mH * mT * cos(5 * sA + 3 * tA) - 4 * mL * mT * cos(3 * sA - tA)
				+ mL * mT * cos(3 * sA - 3 * tA) + mL * mT * cos(3 * sA + 3 * tA) - 4 * mL * mT * cos(5 * sA - tA)
				+ 2 * mL * mT * cos(5 * sA + 3 * tA) - 4 * mH * mL * cos(sA + tA) - mL * mT * cos(sA + tA)))
				/ (lT * t
						* (8 * mH * mL + 16 * mH * mT + 4 * mL * mT + 32 * sq(mH) * cos(2 * sA)
								- 4 * sq(mL) * cos(4 * sA) + 2 * sq(mL) * cos(8 * sA) + 12 * sq(mT) * cos(2 * sA)
								- 12 * sq(mT) * cos(2 * tA) - 8 * sq(mT) * cos(2 * sA + 2 * tA)
								+ 4 * sq(mT) * cos(2 * sA + 4 * tA) - 4 * sq(mT) * cos(4 * sA + 2 * tA)
								+ 2 * sq(mT) * cos(4 * sA + 4 * tA) + 16 * sq(mH) - sq(mL) + 6 * sq(mT)
								+ 16 * mH * mL * cos(2 * sA) - 16 * mH * mL * cos(4 * sA) - 8 * mH * mL * cos(6 * sA)
								+ 32 * mH * mT * cos(2 * sA) + 8 * mL * mT * cos(2 * sA) - 8 * mL * mT * cos(4 * sA)
								- 4 * mL * mT * cos(6 * sA) - 24 * mH * mT * cos(2 * tA) - 12 * mL * mT * cos(2 * tA)
								- 16 * mH * mT * cos(2 * sA + 2 * tA) - 8 * mH * mT * cos(4 * sA + 2 * tA)
								+ 4 * mL * mT * cos(2 * sA - 2 * tA) - 4 * mL * mT * cos(2 * sA + 2 * tA)
								+ 4 * mL * mT * cos(4 * sA - 2 * tA) + 4 * mL * mT * cos(4 * sA + 2 * tA)
								+ 4 * mL * mT * cos(6 * sA + 2 * tA)));

	}

	public Double computeB1P() {

		return -(24 * sA
				* (4 * mH * sq(sin(sA)) - 3 * mL + 6 * mL * sq(sin(sA)) + 2 * mT * sq(sin(sA)) - 2 * mT * sq(sin(tA))
						+ 2 * mL * sq(sin(2 * sA)) + 2 * mT * sq(sin(sA + tA))))
				/ (cu(t) * (12 * mH - 3 * mL - 16 * mH * sq(sin(sA)) - 8 * mT * sq(sin(sA)) + 8 * mT * sq(sin(tA))
						+ 4 * mL * sq(sin(2 * sA)) + 4 * mT * sq(sin(sA + tA))));
	}

	public Double computeB1S() {

		return -(24 * sA * (8 * mH * sq(sin(sA)) - 9 * mL - 12 * mH + 6 * mL * sq(sin(sA)) - 4 * mT * sq(sin(sA))
				- 12 * mT * sq(sin(tA)) + 12 * mH * sq(sin(2 * sA)) + 10 * mL * sq(sin(2 * sA))
				+ 6 * mT * sq(sin(2 * sA)) + 2 * mT * sin(2 * sA) * sin(2 * tA) + 16 * mT * sq(sin(sA)) * sq(sin(tA))))
				/ (cu(t) * (12 * mH - 3 * mL - 16 * mH * sq(sin(sA)) - 8 * mT * sq(sin(sA)) + 8 * mT * sq(sin(tA))
						+ 4 * mL * sq(sin(2 * sA)) + 4 * mT * sq(sin(sA + tA))));
	}

	public Double computeB1T() {

		return (12 * lL * sA * (8 * sq(mH) * cos(sA - tA) - 8 * sq(mH) * cos(3 * sA + tA) + 3 * sq(mL) * cos(sA - tA)
				- 6 * sq(mL) * cos(3 * sA + tA) + sq(mL) * cos(5 * sA + tA) + 2 * sq(mL) * cos(7 * sA + tA)
				+ 6 * sq(mT) * cos(sA - tA) - 2 * sq(mT) * cos(sA + 3 * tA) - 6 * sq(mT) * cos(3 * sA + tA)
				- 5 * sq(mL) * cos(3 * sA - tA) - 3 * sq(mL) * cos(5 * sA - tA) + 2 * sq(mL) * cos(7 * sA - tA)
				+ sq(mL) * cos(9 * sA - tA) + 2 * sq(mT) * cos(5 * sA + 3 * tA) - sq(mL) * cos(sA + tA)
				+ 8 * mH * mL * cos(sA - tA) - 16 * mH * mL * cos(3 * sA + tA) + 2 * mH * mL * cos(7 * sA + tA)
				+ 14 * mH * mT * cos(sA - tA) - 2 * mH * mT * cos(sA + 3 * tA) - 14 * mH * mT * cos(3 * sA + tA)
				+ 10 * mL * mT * cos(sA - tA) + 2 * mL * mT * cos(sA - 3 * tA) - 12 * mL * mT * cos(3 * sA + tA)
				+ 2 * mL * mT * cos(5 * sA + tA) + 3 * mL * mT * cos(7 * sA + tA) - 8 * mH * mL * cos(3 * sA - tA)
				- 6 * mH * mL * cos(5 * sA - tA) + 2 * mH * mT * cos(5 * sA + 3 * tA) - 4 * mL * mT * cos(3 * sA - tA)
				+ mL * mT * cos(3 * sA - 3 * tA) + mL * mT * cos(3 * sA + 3 * tA) - 4 * mL * mT * cos(5 * sA - tA)
				+ 2 * mL * mT * cos(5 * sA + 3 * tA) - 4 * mH * mL * cos(sA + tA) - mL * mT * cos(sA + tA)))
				/ (lT * cu(t)
						* (8 * mH * mL + 16 * mH * mT + 4 * mL * mT + 32 * sq(mH) * cos(2 * sA)
								- 4 * sq(mL) * cos(4 * sA) + 2 * sq(mL) * cos(8 * sA) + 12 * sq(mT) * cos(2 * sA)
								- 12 * sq(mT) * cos(2 * tA) - 8 * sq(mT) * cos(2 * sA + 2 * tA)
								+ 4 * sq(mT) * cos(2 * sA + 4 * tA) - 4 * sq(mT) * cos(4 * sA + 2 * tA)
								+ 2 * sq(mT) * cos(4 * sA + 4 * tA) + 16 * sq(mH) - sq(mL) + 6 * sq(mT)
								+ 16 * mH * mL * cos(2 * sA) - 16 * mH * mL * cos(4 * sA) - 8 * mH * mL * cos(6 * sA)
								+ 32 * mH * mT * cos(2 * sA) + 8 * mL * mT * cos(2 * sA) - 8 * mL * mT * cos(4 * sA)
								- 4 * mL * mT * cos(6 * sA) - 24 * mH * mT * cos(2 * tA) - 12 * mL * mT * cos(2 * tA)
								- 16 * mH * mT * cos(2 * sA + 2 * tA) - 8 * mH * mT * cos(4 * sA + 2 * tA)
								+ 4 * mL * mT * cos(2 * sA - 2 * tA) - 4 * mL * mT * cos(2 * sA + 2 * tA)
								+ 4 * mL * mT * cos(4 * sA - 2 * tA) + 4 * mL * mT * cos(4 * sA + 2 * tA)
								+ 4 * mL * mT * cos(6 * sA + 2 * tA)));
	}

	private Double sin(Double value) {

		return Math.sin(value);
	}

	private Double cos(Double value) {

		return Math.cos(value);
	}

	private Double sq(Double value) {

		return Math.pow(value, 2);
	}

	private Double cu(Double value) {

		return Math.pow(value, 3);
	}

	/*
	 * public static void main(String args[]) { // First : -0.600000000000000
	 * 0.600000000000000 0.200000000000000 // 0.00879611678413795 -1.09178336809296
	 * 1.00534223184332 // Last : 0.600000000000000 -0.600000000000000
	 * 0.200000000000000 // 1.09815805034499 1.06781645227266 0 Agent bipedAgent =
	 * new Agent(new AgentStateData(ThreeLinkThreeLinkStateElements.values()));
	 * EquilibriumComputer comp = new EquilibriumComputer(bipedAgent);
	 * System.out.println("Equilib Initial Planted Leg Velocity : " +
	 * comp.computeInitialPlantedLegVelocity());
	 * System.out.println("Equilib Initial Planted Leg Velocity : " +
	 * comp.computeInitialSwingLegVelocity());
	 * System.out.println("Equilib Initial Torso Velocity : " +
	 * comp.computeInitialTorsoVelocity());
	 * System.out.println("Equilib Final Planted Leg Velocity : " +
	 * comp.computeFinalPlantedLegVelocity());
	 * System.out.println("Equilib Final Planted Leg Velocity : " +
	 * comp.computeFinalSwingLegVelocity());
	 * System.out.println("Equilib Final Torso Velocity : " +
	 * comp.computeFinalTorsoVelocity()); }
	 */
}