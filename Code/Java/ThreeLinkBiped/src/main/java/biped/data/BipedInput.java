package biped.data;

import java.util.ArrayList;

import biped.hybridsystem.perturbed.PerturbedState;
import edu.ucsc.cross.jheq.core.model.HybridSystem;
import edu.ucsc.cross.jheq.core.model.Input;
import edu.ucsc.cross.jheq.core.model.Signal;

public class BipedInput implements Input<BipedInput>
{

	public BipedState plantState;

	public VirtualBipedState virtualState;

	public VirtualBipedState referenceState;

	public PerturbedState perturbedState;

	public BipedInput()
	{

	}

	public BipedInput(BipedState plantState, VirtualBipedState virtualState, VirtualBipedState referenceState,
	PerturbedState perturbationState)
	{
		this.plantState = plantState;
		this.virtualState = virtualState;
		this.referenceState = referenceState;
		this.perturbedState = perturbationState;
	}

	@Override
	public BipedInput getSystemInputs(HybridSystem<?, BipedInput, ?> system)
	{

		ArrayList<Signal<BipedState>> connectedStates = system.getNetwork().getConnectionSignals(system,
		BipedState.class, true);

		for (Signal<BipedState> sig : connectedStates)
		{
			switch (sig.getValue().system)

			{
			case PLANT:
			{
				plantState = sig.getValue();
				break;
			}
			case REFERENCE:
			{
				referenceState = (VirtualBipedState) sig.getValue();
				break;
			}
			case VIRTUAL:

				virtualState = (VirtualBipedState) sig.getValue();
				break;

			default:
				break;

			}
		}
		Signal<PerturbedState> perturbedSig = system.getNetwork().getConnectionSignal(system, PerturbedState.class,
		true);
		if (perturbedSig != null)
		{
			perturbedState = perturbedSig.getValue();
		}
		return this;
	}

}
