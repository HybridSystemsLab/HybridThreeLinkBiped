
package biped.data;

import java.util.ArrayList;

import edu.ucsc.cross.jheq.core.app.ObjectGenerator;
import edu.ucsc.cross.jheq.core.model.HybridSystem;

public class VirtualBipedState extends BipedState
{

	public double trajTimer;

	private TrajectoryParameters trajectoryParameters;

	public VirtualBipedState()
	{
		super();
		trajTimer = 0.0;
		trajectoryParameters = new TrajectoryParameters();
		super.getProperties().setStoreTrajectory(true);
	}

	public void setVirtualBipedState(BipedState state)
	{
		this.plantedLegAngle = state.plantedLegAngle;
		this.swingLegAngle = state.swingLegAngle;
		this.torsoAngle = state.torsoAngle;
		this.plantedLegVelocity = state.plantedLegVelocity;
		this.swingLegVelocity = state.swingLegVelocity;
		this.torsoVelocity = state.torsoVelocity;
		trajTimer = 0.0;

	}

	public void initializeTrajectoryParams(BipedState initial, ArrayList<Object> parameters)
	{
		BipedParameters biped = BipedParameters.get(parameters);
		if (initial.system.equals(BipedSystem.VIRTUAL))
		{
			initializeTrajectoryParams(initial, parameters, biped.getStepTime());
		} else
		{
			initializeTrajectoryParams(initial, parameters, biped.getSetStepTime());

		}
	}

	public void initializeTrajectoryParams(BipedState initial, ArrayList<Object> parameters, Double step_time)
	{

		TrajectoryParameters equilib = TrajectoryParameters.getEquilibParams(parameters);
		BipedParameters biped = BipedParameters.get(parameters);

		if (initial.system.equals(BipedSystem.VIRTUAL))
		{
			this.trajectoryParameters.recompute(initial, equilib.getFinalState(), biped.getSetStepTime(), biped);
		} else
		{
			this.trajectoryParameters.recompute(initial, equilib.getFinalState(), biped);

		}
		this.trajectoryParameters.recompute(initial, equilib.getFinalState(), step_time, biped);
	}

	public static void main(String args[])
	{
		ObjectGenerator<VirtualBipedState> gen = ObjectGenerator.getConfig(new VirtualBipedState());
		// XMLFile.saveXML(gen);

	}

	public VirtualBipedState(Double planted_angle, Double swing_angle, Double torso_angle, Double planted_velocity,
	Double swing_velocity, Double torso_velocity, ArrayList<Object> parameters)
	{

		super(planted_angle, swing_angle, torso_angle, planted_velocity, swing_velocity, torso_velocity,
		BipedSystem.VIRTUAL);
		trajTimer = 0.0;
		TrajectoryParameters equilib = TrajectoryParameters.getEquilibParams(parameters);
		BipedParameters bipedParams = BipedParameters.get(parameters);
		trajectoryParameters = new TrajectoryParameters(this, equilib.getFinalState(), bipedParams);
		super.getProperties().setStoreTrajectory(true);
	}

	public TrajectoryParameters getTrajectoryParameters()
	{
		if (trajectoryParameters.getInitialState() == null)
		{
			HybridSystem sys = this.getParentSystem();
			initializeTrajectoryParams(this, sys.getParameters());
		}
		return trajectoryParameters;
	}

	public boolean isTrajectoryParametersInitialized()
	{
		return (trajectoryParameters.getInitialState() != null);

	}

	public void setTrajectoryParameters(TrajectoryParameters trajectoryParameters)
	{
		this.trajectoryParameters = trajectoryParameters;
	}

}
