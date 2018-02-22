## Overview
The parameter verification tool determines if a set of parameters is valid, meaning it is within the parameter space and a limit cycle solution exists that defines the desired gait.  

## Setup
Open a terminal and navigate to the directory where this readme is located and use the following code snippit to build the tool:

----

    ./gradlew clean
    ./gradlew jar

----

## Usage 
The tool can create xml files where parameters can be specified, and can check the parameters specified in such files.  Run the tool from the project directory (that contains this file) using the following code to get the main prompt.

----

    java -jar ./build/libs/ParameterVerification.jar

----

To create a new parameter file:

----

    java -jar ./build/libs/ParameterVerification.jar -new

----

To check the validity of a set of parameters

----

    java -jar ./build/libs/ParameterVerification.jar -check

----