
package biped.app;

import java.io.File;

import biped.system.plant.PlantFlowController;
import biped.system.reference.ReferenceJumpController;
import biped.system.virtual.VirtualFlowController;
import biped.system.virtual.VirtualJumpController;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.file.CollectionFile;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.hybridsystem.input.HybridSystem;
import edu.ucsc.cross.hse.core.logging.Console;

/**
 * A content loader
 */
public class SystemLoader {

	public static final String defaultFilePath = "application/config/default/systemLoader.xml";

	public biped.data.base.State plantState;

	public biped.data.base.State virtualState;

	public biped.system.perturbation.Parameters perturbationParameters;

	public biped.data.base.Parameters bipedParameters;

	public static void main(String args[]) {

		SystemLoader loader = new SystemLoader();
		CollectionFile file = new CollectionFile(loader);
		Console.info("Select a location for the new system loader file");
		file.saveToFile(FileBrowser.save(), false);
		ConfigurationLoader loader2 = new ConfigurationLoader();
		CollectionFile col2 = new CollectionFile(loader2, false);
		Console.info("Select a location for the new configuration loader file");

		col2.saveToFile(FileBrowser.save(), false);
	}

	public SystemLoader() {

		plantState = new biped.data.base.State();
		virtualState = new biped.data.base.State();
		perturbationParameters = new biped.system.perturbation.Parameters(null, 0.0, false);
		bipedParameters = new biped.data.base.Parameters();
	}

	public static void createFile(File path) {

		SystemLoader loader = new SystemLoader();
		CollectionFile col = new CollectionFile(loader, false);
		col.saveToFile(path, false);
	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static void loadEnvironmentContent(HSEnvironment environment) {

		loadEnvironmentContent(environment, FileBrowser.load());

	}

	/**
	 * Load the environment contents
	 * 
	 * @param env
	 *            environment to be loaded
	 */
	public static void loadEnvironmentContent(HSEnvironment env, File file) {

		CollectionFile cfile = CollectionFile.loadFromFile(file);
		SystemLoader loader = cfile.getObject(SystemLoader.class);
		loadEnvironmentContent(env, loader);
		// System.exit(0);

	}

	/**
	 * Load the environment contents
	 * 
	 * @param env
	 *            environment to be loaded
	 */
	public static void loadEnvironmentContent(HSEnvironment env, SystemLoader loader) {

		if (loader != null) {
			HybridSystem<biped.data.base.State, biped.system.plant.Parameters> plant = loadPlant(env, loader);
			HybridSystem<biped.data.virtual.State, biped.system.virtual.Parameters> virtual = loadVirtual(env, loader);
			HybridSystem<biped.data.virtual.State, biped.data.virtual.Parameters> reference = loadReference(env,
					loader);
			HybridSystem<biped.system.perturbation.State, biped.system.perturbation.Parameters> perturbations = loadPerturbations(
					env, loader);
			plant.getParams().virtual.setOutput(virtual);
			plant.getParams().perturbation.setOutput(perturbations);
			virtual.getParams().plant.setOutput(plant);
			virtual.getParams().perturbation.setOutput(perturbations);
			virtual.getParams().reference.setOutput(reference);
			perturbations.getParams().plant = plant;
			env.getSystems().add(plant, virtual, reference, perturbations);
		}

	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static HybridSystem<biped.data.base.State, biped.system.plant.Parameters> loadPlant(
			HSEnvironment environment, SystemLoader loader) {

		biped.system.plant.Parameters params = new biped.system.plant.Parameters(loader.bipedParameters);
		biped.data.base.State state = biped.data.base.State.getState(loader.plantState);

		PlantFlowController controller = new PlantFlowController(params);
		HybridSystem<biped.data.base.State, biped.system.plant.Parameters> plant = ContentFactory
				.createRealSystem(state, params, controller);

		return plant;

	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static HybridSystem<biped.data.virtual.State, biped.system.virtual.Parameters> loadVirtual(
			HSEnvironment environment, SystemLoader loader) {

		biped.system.virtual.Parameters params = new biped.system.virtual.Parameters(loader.bipedParameters);
		biped.data.virtual.State state = new biped.data.virtual.State(loader.virtualState, params.bipedParams);

		// Initialize the virtual flow controller
		VirtualFlowController virtualFlowControl = new VirtualFlowController(params.bipedParams);
		// Initialize the virtual jump controller
		VirtualJumpController virtualJumpControl = new VirtualJumpController(params);

		HybridSystem<biped.data.virtual.State, biped.system.virtual.Parameters> plant = ContentFactory
				.createVirtualSystem(state, params, virtualFlowControl, virtualJumpControl);

		return plant;

	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static HybridSystem<biped.data.virtual.State, biped.data.virtual.Parameters> loadReference(
			HSEnvironment environment, SystemLoader loader) {

		biped.data.virtual.Parameters params = new biped.data.virtual.Parameters(loader.bipedParameters);
		biped.data.virtual.State state = new biped.data.virtual.State(
				biped.data.base.State.getState(params.equilibParams.initialState), params);
		// Initialize the reference flow controller
		VirtualFlowController referenceFlowControl = new VirtualFlowController(params);
		// Initialize the reference jump controller
		ReferenceJumpController referenceJumpControl = new ReferenceJumpController(params);

		HybridSystem<biped.data.virtual.State, biped.data.virtual.Parameters> plant = ContentFactory
				.createReferenceSystem(state, params, referenceFlowControl, referenceJumpControl);

		return plant;

	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static HybridSystem<biped.system.perturbation.State, biped.system.perturbation.Parameters> loadPerturbations(
			HSEnvironment environment, SystemLoader loader) {

		biped.system.perturbation.Parameters params = loader.perturbationParameters;
		params.bipedParameters = loader.bipedParameters;
		biped.system.perturbation.State state = new biped.system.perturbation.State(
				loader.perturbationParameters.perturbationPercent);

		HybridSystem<biped.system.perturbation.State, biped.system.perturbation.Parameters> plant = ContentFactory
				.createPerturbationSystem(state, params);

		return plant;

	}

}
