
package biped.data;

import java.util.HashMap;

import Jama.Matrix;
import edu.ucsc.cross.jheq.core.object.DataStructure;

/**
 * A state
 */
public class BipedState extends DataStructure
{

	public double plantedLegAngle;

	public double swingLegAngle;

	public double torsoAngle;

	public double plantedLegVelocity;

	public double swingLegVelocity;

	public double torsoVelocity;

	public BipedSystem system;

	/*
	 * Constructs the default biped state with all values set to zero
	 */

	public BipedState()
	{

		this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, BipedSystem.PLANT);

	}

	public static BipedState getState(BipedState state)
	{

		return new BipedState(state.plantedLegAngle, state.swingLegAngle, state.torsoAngle, state.plantedLegVelocity,
		state.swingLegVelocity, state.torsoVelocity, state.system);
	}

	/*
	 * Constructs a biped state with defined values
	 */
	public BipedState(Double planted_angle, Double swing_angle, Double torso_angle, Double planted_velocity,
	Double swing_velocity, Double torso_velocity, BipedSystem system)
	{

		plantedLegAngle = planted_angle;
		swingLegAngle = swing_angle;
		torsoAngle = torso_angle;
		plantedLegVelocity = planted_velocity;
		swingLegVelocity = swing_velocity;
		torsoVelocity = torso_velocity;
		this.getProperties().setName("Biped State");
		this.system = system;
		getProperties().setStoreTrajectory(true);

	}

	public HashMap<BipedMotion, Double> getLimbState(BipedLimb limb)
	{

		HashMap<BipedMotion, Double> limbMovement = new HashMap<BipedMotion, Double>();
		switch (limb)
		{
		case PLANTED_LEG:
		{
			limbMovement.put(BipedMotion.ANGLE, plantedLegAngle);
			limbMovement.put(BipedMotion.ANGULAR_VELOCITY, plantedLegVelocity);
			break;
		}
		case SWING_LEG:
		{
			limbMovement.put(BipedMotion.ANGLE, swingLegAngle);
			limbMovement.put(BipedMotion.ANGULAR_VELOCITY, swingLegVelocity);
			break;
		}
		case TORSO:
		{
			limbMovement.put(BipedMotion.ANGLE, torsoAngle);
			limbMovement.put(BipedMotion.ANGULAR_VELOCITY, torsoVelocity);
			break;
		}
		}
		return limbMovement;
	}

	public Matrix getMotionMatrix(BipedMotion motion)
	{

		double[][] mat = new double[3][1];

		if (motion.equals(BipedMotion.ANGULAR_VELOCITY))
		{
			mat[0][0] = plantedLegVelocity;
			mat[1][0] = swingLegVelocity;
			mat[2][0] = torsoVelocity;
		} else if (motion.equals(BipedMotion.ANGLE))
		{
			mat[0][0] = plantedLegAngle;
			mat[1][0] = swingLegAngle;
			mat[2][0] = torsoAngle;
		}

		return new Matrix(mat);
	}

	public static BipedState getRandomizedState(BipedParameters params)
	{

		BipedState state = new BipedState();
		randomizeState(state, params);
		return state;
	}

	public static void randomizeState(BipedState state, BipedParameters params)
	{

		Double angleMax = params.stepAngle - .05;
		Double velMax = params.walkSpeed;
		state.plantedLegAngle = -Math.random() * angleMax;
		state.swingLegAngle = Math.random() * angleMax;
		state.torsoAngle = Math.random() * angleMax;
		state.plantedLegVelocity = Math.random() * velMax;
		state.swingLegVelocity = Math.random() * velMax;
		state.torsoVelocity = Math.random() * velMax;
	}
}