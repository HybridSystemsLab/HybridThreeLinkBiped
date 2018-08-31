
package biped.app;

import java.io.File;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.file.CollectionFile;
import edu.ucsc.cross.hse.core.logging.Console;

/**
 * An application that prepares, runs, and processes an environment. This class
 * does not need to be changed.
 */
public class HSEApplication {

	public static final String defaultFilePath = "application/setup.xml";

	public String systemLoader;

	public String configurationLoader;

	public boolean saveResults;

	public boolean saveFiguresWithBrowser;

	public HSEApplication() {

		systemLoader = SystemLoader.defaultFilePath;
		configurationLoader = ConfigurationLoader.defaultFilePath;
		saveResults = true;

		saveFiguresWithBrowser = false;
		File configurationLoaderFile = new File(configurationLoader);
		if (!configurationLoaderFile.exists()) {
			ConfigurationLoader.createFile(configurationLoaderFile);
		}
		File systemLoaderFile = new File(systemLoader);
		if (!systemLoaderFile.exists()) {
			SystemLoader.createFile(systemLoaderFile);
		}
	}

	public HSEApplication(File file) {

		CollectionFile unzip = CollectionFile.loadFromFile(file);
		HSEApplication app = null;
		if (file.exists()) {
			app = unzip.getObject(HSEApplication.class);
		}
		if (app != null) {
			systemLoader = app.systemLoader;
			configurationLoader = app.configurationLoader;
			saveResults = app.saveResults;
		} else {
			app = new HSEApplication();
			File setupFile = new File(HSEApplication.defaultFilePath);
			if (!setupFile.exists()) {
				CollectionFile f = new CollectionFile(app);
				f.saveToFile(setupFile, false);
			}
		}

	}

	/**
	 * Method that will be executed when application is run
	 */
	public static void main(String args[]) {

		HSEApplication app = new HSEApplication(new File(HSEApplication.defaultFilePath));

		// load console settings
		ConfigurationLoader configLoader = CollectionFile.loadFromFile(new File(app.configurationLoader))
				.getObject(ConfigurationLoader.class);

		SystemLoader systemLoader = CollectionFile.loadFromFile(new File(app.systemLoader))
				.getObject(SystemLoader.class);
		// initialize environment
		HSEnvironment environment = new HSEnvironment();

		SystemLoader.loadEnvironmentContent(environment, systemLoader);
		environment.loadSettings(configLoader.environment);
		Console.setSettings(configLoader.console);
		// load environment content
		// environment.saveToFile(false);
		// run environment
		environment.run();
		// process results
		PostProcessor.processEnvironmentData(environment, app.saveFiguresWithBrowser);
		if (app.saveResults) {
			environment.saveToFile(new File("application/output/env_" + System.currentTimeMillis()));
		}

	}

}