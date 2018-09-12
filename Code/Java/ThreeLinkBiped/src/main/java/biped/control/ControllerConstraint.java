
package biped.control;

import java.util.HashMap;

import Jama.Matrix;
import biped.data.BipedLimb;
import edu.ucsc.cross.jheq.core.object.DataStructure;

public class ControllerConstraint extends DataStructure
{

	public HashMap<BipedLimb, Double> constraint;

	public ControllerConstraint(double cons)
	{
		constraint = new HashMap<BipedLimb, Double>();
		for (BipedLimb limb : BipedLimb.values())
		{
			constraint.put(limb, cons);
		}
	}

	public ControllerConstraint()
	{
		this(10.0);
	}

	public Matrix getInputTorquesConstrained(Matrix unconstrained)
	{

		Matrix constrained = unconstrained.copy();
		for (BipedLimb limb : BipedLimb.values())
		{
			Double unconstrainedVal = unconstrained.get(limb.vectorIndex, 0);
			Double max = constraint.get(limb);
			if (unconstrainedVal > max)
			{
				constrained.set(limb.vectorIndex, 0, max);
			}
		}

		return constrained;
	}

}
