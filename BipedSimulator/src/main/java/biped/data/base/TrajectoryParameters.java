
package biped.data.base;

import java.util.HashMap;

import biped.computation.BipedComputer;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class TrajectoryParameters extends DataStructure {

	public HashMap<BipedLimb, Double> B0;

	public HashMap<BipedLimb, Double> B1;

	public State initialState;

	public State finalState;

	public TrajectoryParameters(State initial_state, State final_state, Parameters params) {

		recompute(initial_state, final_state, params);
		this.getProperties().setStoreTrajectory(false);
	}

	public TrajectoryParameters(State initial_state, State final_state, Double step_time, Parameters params) {

		recompute(initial_state, final_state, step_time, params);
		this.getProperties().setStoreTrajectory(false);
	}

	public double getB0(BipedLimb limb) {

		return B0.get(limb);
	}

	public double getB1(BipedLimb limb)

	{

		return B1.get(limb);
	}

	public HashMap<BipedLimb, Double> getB0() {

		return B0;
	}

	public HashMap<BipedLimb, Double> getB1() {

		return B1;
	}

	public State getInitialState() {

		return initialState;
	}

	public State getFinalState() {

		return finalState;
	}

	public void recompute(State initial_state, State final_state, Parameters params) {

		Double stepTime = params.getStepTime();
		recompute(initial_state, final_state, stepTime, params);
	}

	public void recompute(State initial_state, State final_state, Double step_time, Parameters params) {

		initialState = biped.data.base.State.getState(initial_state);
		finalState = biped.data.base.State.getState(final_state);//
		initialState.getProperties().setStoreTrajectory(false);
		finalState.getProperties().setStoreTrajectory(false);
		B0 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 1);
	}

	public static TrajectoryParameters compute(State initial_state, State final_state, Double step_time,
			Parameters params) {

		TrajectoryParameters param = new TrajectoryParameters(initial_state, final_state, step_time, params);
		return param;
	}
}