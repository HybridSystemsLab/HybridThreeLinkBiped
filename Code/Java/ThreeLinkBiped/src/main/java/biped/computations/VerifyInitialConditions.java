package biped.computations;

import com.be3short.obj.access.FieldFinder;

import biped.data.BipedParameters;
import biped.data.BipedSystem;
import biped.data.TrajectoryParameters;
import biped.data.VirtualBipedState;
import edu.ucsc.cross.jheq.core.app.ApplicationFeature;
import edu.ucsc.cross.jheq.core.environment.HSEnvironment;
import edu.ucsc.cross.jheq.core.model.HybridSystem;

public class VerifyInitialConditions implements ApplicationFeature<String>
{

	@Override
	public ApplicationFeature<String> create()
	{
		return new VerifyInitialConditions();
	}

	@Override
	public String perform(HSEnvironment env, String input)
	{
		for (HybridSystem<?, ?, ?> sys : env.getSystems().getSystems())
		{
			if (FieldFinder.containsSuperClass(sys.getState(), VirtualBipedState.class))
			{
				try
				{
					VirtualBipedState bip = (VirtualBipedState) sys.getState();
					BipedParameters bipP = BipedParameters.get(sys.getParameters());
					TrajectoryParameters params = EquilibriumComputer.getEquilibriumParameters(bipP);
					double step_time = bipP.getStepTime() + bip.trajTimer;
					if (!bip.isTrajectoryParametersInitialized())
					{
						if (bip.system.equals(BipedSystem.REFERENCE))
						{
							bip.setVirtualBipedState(params.initialState);
							step_time = bipP.getStepTime();
						}
						bip.initializeTrajectoryParams(bip, sys.getParameters(), step_time);

					}
					bip.trajTimer = 0.0;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
