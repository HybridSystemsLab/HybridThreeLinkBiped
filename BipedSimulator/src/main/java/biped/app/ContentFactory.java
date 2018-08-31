
package biped.app;

import Jama.Matrix;
import biped.data.base.TrajectoryParameters;
import biped.data.virtual.Parameters;
import biped.data.virtual.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.HybridSystem;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class ContentFactory {

	public static biped.data.virtual.State generateState(Parameters parameters, Double planted_angle,
			Double swing_angle, Double torso_angle, Double planted_velocity, Double swing_velocity,
			Double torso_velocity) {

		State state = new State(parameters, planted_angle, swing_angle, torso_angle, planted_velocity, swing_velocity,
				torso_velocity);
		return state;
	}

	public static Parameters generateParameters(boolean randomize) {

		biped.data.base.Parameters parameters = new biped.data.base.Parameters();
		if (!randomize) {
			parameters.gravity = 9.81;
			parameters.hipMass = 1.0;
			parameters.legLength = 1.0;
			parameters.legMass = 1.0;
			parameters.stepAngle = .6;
			parameters.torsoAngle = .2;
			parameters.torsoLength = 1.0;
			parameters.torsoMass = 1.0;
			parameters.useProperTorqueRelationship = true;
			parameters.walkSpeed = 1.0;
		} else {
			parameters = biped.data.base.Parameters.getTestParams();
		}
		return new Parameters(parameters);

	}

	/**
	 * Create the hybrid system
	 * 
	 * @param state
	 *            system state
	 * @param parameters
	 *            system parameters
	 * @return initialized hybrid system
	 */
	public static HybridSystem<biped.data.base.State, biped.system.plant.Parameters> createRealSystem(
			biped.data.base.State state, biped.system.plant.Parameters parameters,
			Controller<biped.data.base.State, Matrix> continuous_controller) {

		state.getProperties().setName("Physical");

		// Initialize the flow map
		biped.system.plant.Fp f = new biped.system.plant.Fp(continuous_controller);
		// Initialize the jump map
		biped.system.plant.Gp g = new biped.system.plant.Gp();
		// Initialize the flow set
		biped.system.plant.Cp c = new biped.system.plant.Cp();
		// Initialize the jump set
		biped.system.plant.Dp d = new biped.system.plant.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.data.base.State, biped.system.plant.Parameters> system = new HybridSystem<biped.data.base.State, biped.system.plant.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static HybridSystem<biped.data.virtual.State, biped.system.virtual.Parameters> createVirtualSystem(
			biped.data.virtual.State state, biped.system.virtual.Parameters parameters,
			Controller<biped.data.virtual.State, Matrix> continuous_controller,
			Controller<biped.data.virtual.State, TrajectoryParameters> discrete_controller) {

		state.bipedState.getProperties().setName("Virtual");

		// Initialize the flow map
		biped.system.virtual.Fp f = new biped.system.virtual.Fp(continuous_controller);
		// Initialize the jump map
		biped.system.virtual.Gp g = new biped.system.virtual.Gp(discrete_controller);
		// Initialize the flow set
		biped.system.virtual.Cp c = new biped.system.virtual.Cp();
		// Initialize the jump set
		biped.system.virtual.Dp d = new biped.system.virtual.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.data.virtual.State, biped.system.virtual.Parameters> system = new HybridSystem<biped.data.virtual.State, biped.system.virtual.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static HybridSystem<biped.data.virtual.State, biped.data.virtual.Parameters> createReferenceSystem(
			biped.data.virtual.State state, biped.data.virtual.Parameters parameters,
			Controller<biped.data.virtual.State, Matrix> continuous_controller,
			Controller<biped.data.virtual.State, TrajectoryParameters> discrete_controller) {

		state.bipedState.getProperties().setName("Reference");

		// Initialize the flow map
		biped.system.reference.Fp f = new biped.system.reference.Fp(continuous_controller);
		// Initialize the jump map
		biped.system.reference.Gp g = new biped.system.reference.Gp(discrete_controller);
		// Initialize the flow set
		biped.system.reference.Cp c = new biped.system.reference.Cp();
		// Initialize the jump set
		biped.system.reference.Dp d = new biped.system.reference.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.data.virtual.State, biped.data.virtual.Parameters> system = new HybridSystem<biped.data.virtual.State, biped.data.virtual.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static HybridSystem<biped.system.perturbation.State, biped.system.perturbation.Parameters> createPerturbationSystem(
			biped.system.perturbation.State state, biped.system.perturbation.Parameters parameters) {

		// Initialize the flow map
		biped.system.perturbation.Fp f = new biped.system.perturbation.Fp();
		// Initialize the jump map
		biped.system.perturbation.Gp g = new biped.system.perturbation.Gp();
		// Initialize the flow set
		biped.system.perturbation.Cp c = new biped.system.perturbation.Cp();
		// Initialize the jump set
		biped.system.perturbation.Dp d = new biped.system.perturbation.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.system.perturbation.State, biped.system.perturbation.Parameters> system = new HybridSystem<biped.system.perturbation.State, biped.system.perturbation.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}
}
