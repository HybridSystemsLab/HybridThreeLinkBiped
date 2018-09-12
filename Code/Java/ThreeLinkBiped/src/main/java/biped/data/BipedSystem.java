package biped.data;

import java.util.ArrayList;

import firefly.hybridsystem.ParameterDecode;

public enum BipedSystem
{

	VIRTUAL,
	REFERENCE,
	PLANT,
	PERTURBATION;

	public static BipedSystem get(ArrayList<Object> parameters)

	{
		return ParameterDecode.getAny(BipedSystem.class, parameters);
	}
}
