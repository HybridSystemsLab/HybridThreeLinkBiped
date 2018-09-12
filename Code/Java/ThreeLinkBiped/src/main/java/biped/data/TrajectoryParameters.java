
package biped.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.be3short.data.cloning.ObjectCloner;

import biped.computations.BipedComputer;
import biped.computations.EquilibriumComputer;
import edu.ucsc.cross.jheq.core.object.DataStructure;
import firefly.hybridsystem.ParameterDecode;

public class TrajectoryParameters extends DataStructure
{

	public static TrajectoryParameters getEquilibParams(ArrayList<Object> params)
	{
		TrajectoryParameters param = ParameterDecode.getAny(TrajectoryParameters.class, params);
		if (param.initialState == null)
		{
			BipedParameters biped = BipedParameters.get(params);
			TrajectoryParameters equilib = EquilibriumComputer.getEquilibriumParameters(biped);
			param.recompute(equilib.getInitialState(), equilib.getFinalState(), biped);
		}
		return param;
	}

	public HashMap<BipedLimb, Double> B0;

	public HashMap<BipedLimb, Double> B1;

	public BipedState initialState;

	public BipedState finalState;

	public TrajectoryParameters()
	{

	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, BipedParameters params)
	{

		recompute(initial_state, final_state, params);
		this.getProperties().setStoreTrajectory(false);
	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, Double step_time,
	BipedParameters params)
	{

		recompute(initial_state, final_state, step_time, params);
		this.getProperties().setStoreTrajectory(false);
	}

	public double getB0(BipedLimb limb)
	{

		return B0.get(limb);
	}

	public double getB1(BipedLimb limb)

	{

		return B1.get(limb);
	}

	public HashMap<BipedLimb, Double> getB0()
	{

		return B0;
	}

	public HashMap<BipedLimb, Double> getB1()
	{

		return B1;
	}

	public BipedState getInitialState()
	{

		return initialState;
	}

	public BipedState getFinalState()
	{

		return finalState;
	}

	public void recompute(BipedState initial_state, BipedState final_state, BipedParameters params)
	{

		Double stepTime = params.getStepTime();
		recompute(initial_state, final_state, stepTime, params);
	}

	public void recompute(BipedState initial_state, BipedState final_state, Double step_time, BipedParameters params)
	{

		initialState = ObjectCloner.deepInstanceClone(initial_state);
		finalState = ObjectCloner.deepInstanceClone(final_state);//
		initialState.getProperties().setStoreTrajectory(false);
		finalState.getProperties().setStoreTrajectory(false);
		B0 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 1);
	}

	public static TrajectoryParameters compute(BipedState initial_state, BipedState final_state, Double step_time,
	BipedParameters params)
	{

		TrajectoryParameters param = new TrajectoryParameters(initial_state, final_state, step_time, params);
		return param;
	}
}