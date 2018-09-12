
package biped.data;

import java.util.ArrayList;

import Jama.Matrix;
import edu.ucsc.cross.jheq.core.object.DataStructure;
import firefly.hybridsystem.ParameterDecode;

/**
 * A parameter set
 */
public class BipedParameters extends DataStructure
{

	public Double gravity;

	public Double hipMass;

	public Double legLength;

	public Double legMass;

	public Double stepAngle;

	public Double stepTimeSet;

	public Double torsoAngle;

	public Double torsoLength;

	public Double torsoMass;

	public boolean useProperTorqueRelationship = false;

	public Double walkSpeed;

	public BipedParameters()
	{

		legMass = 0.0;
		torsoMass = 0.0;
		hipMass = 0.0;
		walkSpeed = 0.0;
		stepAngle = 0.0;
		torsoAngle = 0.0;
		legLength = 0.0;
		torsoLength = 0.0;
		stepTimeSet = null;
		gravity = 0.0;
	}

	public Double getFinalAngle(BipedLimb limb)
	{

		switch (limb)
		{
		case PLANTED_LEG:
			return stepAngle;
		case SWING_LEG:
			return -stepAngle;
		case TORSO:
			return torsoAngle;
		default:
			return null;
		}
	}

	public Double getInitialAngle(BipedLimb limb)
	{

		switch (limb)
		{
		case PLANTED_LEG:
			return -stepAngle;
		case SWING_LEG:
			return stepAngle;
		case TORSO:
			return torsoAngle;
		default:
			return null;
		}
	}

	public Double getStepTime()
	{
		Double t = (2.0 * legLength * Math.sin(stepAngle)) / walkSpeed;

		return t;
	}

	public Double getSetStepTime()
	{
		Double t = getStepTime();
		if (stepTimeSet != null)
		{
			return stepTimeSet * t;
		}
		return t;
	}

	public Matrix getTorqueRelationship()
	{

		double[][] tor =
		{
				{ 1, 0, 0 },
				{ 0, 1, 0 },
				{ 0, 0, 1 } };
		if (useProperTorqueRelationship)
		{
			tor = new double[][]
			{
					{ -1, 0, -1 },
					{ 0, -1, -1 },
					{ 1, 1, 1 } };
		}
		Matrix torqueRelationship = new Matrix(tor);
		return torqueRelationship;

	}

	public static BipedParameters getTestParams()
	{

		BipedParameters p = new BipedParameters();
		p.gravity = 9.81;
		p.hipMass = 1.0;
		p.legLength = 1.0;
		p.legMass = 1.0;
		p.stepAngle = .7;
		p.walkSpeed = .6;
		p.torsoLength = 1.0;
		p.torsoAngle = .5;
		p.torsoMass = 1.0;
		return p;
	}

	public static BipedParameters get(ArrayList<Object> params)
	{
		return ParameterDecode.getAny(BipedParameters.class, params);
	}

}
