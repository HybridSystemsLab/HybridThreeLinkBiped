package edu.ucsc.hsl.biped.paramverify;

import java.io.File;

import com.be3short.io.general.FileSystemInteractor;
import com.be3short.io.xml.XMLParser;

import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.hsl.hse.model.biped.threelink.computors.ParameterEvaluator;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;

public class VerifyParameters
{

	public static void main(String args[])
	{
		parseInput(args);
	}

	public static void parseInput(String args[])
	{
		if (args.length == 0)
		{
			System.out.println(
			"\nVerify Parameters Usage Arguments:\n\n -new : create a new parameter file\n -check : check an existing set of parameters from file \n\n please run again with one of the arguments");
		} else
		{
			for (String arg : args)
			{
				if (args[0].equals("-new"))
				{
					createNewParameterFile();
				} else if (args[0].equals("-check"))
				{
					checkParameterFile();
				}
			}
		}
	}

	public static void createNewParameterFile()
	{
		File location = FileBrowser.save();
		BipedParameters params = new BipedParameters();
		if (!location.getName().contains(".xml"))
		{
			location = new File(location.getPath() + ".xml");
		}
		FileSystemInteractor.createOutputFile(location, XMLParser.serializeObject(params));

	}

	public static void checkParameterFile()
	{
		File location = FileBrowser.load();
		BipedParameters params = (BipedParameters) XMLParser.getObject(location);
		ParameterEvaluator.validParameters(params);

	}
}
