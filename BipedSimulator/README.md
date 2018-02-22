## Overview
This is an example of how to set up a simulation using the three link biped model (https://github.com/HybridSystemsEnvironment/ThreeLinkBipedLib) with the hybrid systems environment (https://github.com/HybridSystemsEnvironment/HybridSystemsEnvironment).  This example is basic, there is a single file where the parameters can be adjusted (ie initial conditions, setttings, runtime, etc).  This is best used with an IDE such as Eclipse where the jar does not need to be recompiled each time a change is made to run the timulation.  There are many other ways to use the library, see the documentation for the hybrid systems environment

## Usage 

Method 1: Download Eclipse Java IDE and import the project directory.  Run the BipedSimulatorExample as a java application.  

Method 2: Build a jar file to run the simulator.  Open a terminal and navigate to the directory where this readme is located and use the following code snippit to build the simulator:

----

./gradlew clean
./gradlew jar

----

Run the simulator from the project directory (that contains this file) using the following code:

----

    java -jar ./build/libs/Biped.jar

----

To create a new parameter file:

----

    java -jar ./build/libs/ParameterVerification.jar -new

----

To check the validity of a set of parameters

----

    java -jar ./build/libs/ParameterVerification.jar -check

----