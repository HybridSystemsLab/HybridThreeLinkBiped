
package biped.app;

import java.io.File;

import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.file.CollectionFile;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;

/**
 * An environment and console setting configurer
 */
public class ConfigurationLoader {

	public static final String defaultFilePath = "application/config/default/configLoader.xml";

	public ConsoleSettings console;

	public EnvironmentSettings environment;

	public ConfigurationLoader() {

		console = new ConsoleSettings();
		environment = new EnvironmentSettings();
	}

	public static void createFile(File path) {

		ConfigurationLoader loader = new ConfigurationLoader();
		CollectionFile col = new CollectionFile(loader, false);
		col.saveToFile(path, false);
	}

}
