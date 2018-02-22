package sim;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modelling.HybridSystem;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import edu.ucsc.hsl.hse.model.biped.threelink.factories.BipedModelFactory;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.states.BipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.ClosedLoopBipedSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.ReferenceSystem;

public class BipedSimulatorExample
{

	public static void main(String args[])
	{
		runUnperturbedSimulation();
	}

	public static void runUnperturbedSimulation()
	{
		// setup environment based on file setup
		HSEnvironment env = setupUnperturbedSimulationEnvironment();

		// execute simulation
		TrajectorySet solution = env.run();

		// create figure from simulation solution trajectories
		Figure figure = generateQuadFigure(solution);

		// export figure to file (uncomment to use)
		//figure.exportToFile(new File("outputs/unperturbedBipedFigure"), GraphicFormat.EPS, 1000, 1000);

		// display figure
		figure.display();
	}

	public static HSEnvironment setupUnperturbedSimulationEnvironment()
	{
		// Get configured parameters
		BipedParameters params = getConfiguredBipedParameters();

		// Setup states
		BipedState plantState = getBipedState();
		VirtualBipedState virtualState = getVirtualBipedState();
		VirtualBipedState referenceState = getReferenceBipedState();

		// Setup systems
		ReferenceSystem referenceSystem = BipedModelFactory.getReferenceSystem(referenceState, params);
		ClosedLoopBipedSystem closedLoopSystem = BipedModelFactory.getClosedLoopUnperturbed(plantState, virtualState,
		referenceSystem, params);

		// Setup environment
		HSEnvironment env = getConfiguredEnvironment(referenceSystem, closedLoopSystem);

		return env;

	}

	public static BipedState getBipedState()
	{
		BipedState bipedState = new BipedState();
		bipedState.plantedLegAngle = .1;
		bipedState.plantedLegVelocity = 0.0;
		bipedState.swingLegAngle = -.1;
		bipedState.swingLegVelocity = 0.0;
		bipedState.torsoAngle = 0.0;
		bipedState.torsoVelocity = 0.0;
		return bipedState;
	}

	public static VirtualBipedState getVirtualBipedState()
	{
		VirtualBipedState virtualState = new VirtualBipedState(new BipedState());
		virtualState.plantedLegAngle = -.1;
		virtualState.plantedLegVelocity = 0.0;
		virtualState.swingLegAngle = .1;
		virtualState.swingLegVelocity = 0.0;
		virtualState.torsoAngle = 0.0;
		virtualState.torsoVelocity = 0.0;
		return virtualState;
	}

	public static VirtualBipedState getReferenceBipedState()
	{
		VirtualBipedState referenceState = new VirtualBipedState(new BipedState());
		referenceState.plantedLegAngle = 0.0;
		referenceState.plantedLegVelocity = 0.0;
		referenceState.swingLegAngle = 0.0;
		referenceState.swingLegVelocity = 0.0;
		referenceState.torsoAngle = 0.0;
		referenceState.torsoVelocity = 0.0;
		return referenceState;
	}

	public static BipedParameters getConfiguredBipedParameters()
	{
		BipedParameters parameters = new BipedParameters();
		parameters.gravity = 9.81;
		parameters.hipMass = 1.0;
		parameters.legLength = 1.0;
		parameters.legMass = 1.0;
		parameters.stepAngle = .5;
		parameters.torsoAngle = .1;
		parameters.torsoLength = 1.0;
		parameters.torsoMass = 1.0;
		parameters.useProperTorqueRelationship = true;
		parameters.walkSpeed = .5;
		return parameters;
	}

	public static ExecutionParameters getExecutionParameters()
	{
		ExecutionParameters params = new ExecutionParameters();
		params.maximumJumps = 40;
		params.maximumTime = 10.0;
		params.dataPointInterval = .1;
		return params;
	}

	public static EnvironmentSettings getEnvironmentSettings()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = .00005;
		settings.odeMaximumStepSize = .001;
		settings.odeSolverAbsoluteTolerance = 1.0e-8;
		settings.odeRelativeTolerance = 1.0e-8;
		settings.eventHandlerMaximumCheckInterval = .5E-5;
		settings.eventHandlerConvergenceThreshold = .00000000000001;
		settings.maxEventHandlerIterations = 100;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	public static ConsoleSettings getConsoleSettings()
	{
		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 20.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = false;
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		return console;
	}

	public static Figure generateQuadFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(1000, 600);

		ChartPanel pA = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle");
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegAngle");
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "torsoAngle");
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegVelocity");
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegVelocity");
		ChartPanel tV = ChartUtils.createPanel(solution, HybridTime.TIME, "torsoVelocity");

		figure.addComponent(1, 0, pA);
		figure.addComponent(2, 0, sA);
		figure.addComponent(3, 0, tA);
		figure.addComponent(1, 1, pV);
		figure.addComponent(2, 1, sV);
		figure.addComponent(3, 1, tV);

		ChartUtils.configureLabels(pA, "Time (sec)", "plantedLegAngle", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "swingLegAngle", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "torsoAngle", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "plantedLegVelocity", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "swingLegVelocity", null, true);
		ChartUtils.configureLabels(tV, "Time (sec)", "torsoVelocity", null, false);

		figure.getTitle().setText("Three Link Biped: Limb Angles & Velocities");
		return figure;
	}

	public static HSEnvironment getConfiguredEnvironment(HybridSystem<?>... systems)
	{
		HSEnvironment env = new HSEnvironment();
		env.loadSettings(getEnvironmentSettings());
		env.loadParameters(getExecutionParameters());
		env.getSystems().add(systems);
		Console.setSettings(getConsoleSettings());
		return env;
	}
}
