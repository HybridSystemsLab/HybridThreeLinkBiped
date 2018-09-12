package biped.control;

import java.util.ArrayList;
import java.util.HashMap;

import Jama.Matrix;
import biped.data.BipedLimb;
import firefly.hybridsystem.ParameterDecode;

public class PlantFlowControlParameters
{

	public static PlantFlowControlParameters get(ArrayList<Object> params)
	{
		return ParameterDecode.getAny(PlantFlowControlParameters.class, params);
	}

	public Double kOne;

	public Double kTwo;

	public boolean constrainInput;
	public HashMap<BipedLimb, Double> constraint;

	public Matrix getInputTorquesConstrained(Matrix unconstrained)
	{

		Matrix constrained = unconstrained.copy();
		if (constrainInput)
		{
			for (BipedLimb limb : BipedLimb.values())
			{
				Double unconstrainedVal = unconstrained.get(limb.vectorIndex, 0);
				Double max = constraint.get(limb);
				if (Math.abs(unconstrainedVal) > max)
				{
					constrained.set(limb.vectorIndex, 0, Math.signum(unconstrainedVal) * max);
				}
			}
		}
		return constrained;
	}

	public PlantFlowControlParameters()
	{
		kOne = (2000.0);
		kTwo = (100.0);
		constrainInput = false;
		constraint = new HashMap<BipedLimb, Double>();
		for (BipedLimb limb : BipedLimb.values())
		{
			constraint.put(limb, 10.0);
		}
	}
}
