## Overview
The Hybrid Systems Environment (HSE) is a tool for modeling and simulation of networked hybrid dynamical systems.  It also provides post-processing and figure generation capabilities for data collected during simulations.  This package is capable of computing approximations of trajectories of hybrid systems that are defined by differential and difference equations with constraints.  It also performs these computations at a much higher speed than achieved using similar tools.  This environment is suitable for simulating hybrid systems with trajectories that can be Zeno or have multiple jumps at the same time. These hybrid systems can be interconnected and simulated with or without inputs.  It allows connections with external components, allowing for data to extend outside the environment.  

This readme does not contain documentation on how to use this software package.  Please see the complete documentation at https://hybridsystemsenvironment.github.io/  for more information.

Examples can be found at https://github.com/HybridSystemsEnvironment/HSE-Examples

## Installation
In order to use the Hybrid Systems Environment in a project, it must be included in the build path as a dependency.  The recommended way to do this is to use dependency management tools such as Maven or Gradle to include the package by making the following modifications to the project build script.

Maven: add the following items to pom.xml

```xml
    <!-- Add repository (if not already included) -->
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>

    <!-- Add dependency -->
    <dependency>
        <groupId>com.github.HybridSystemsEnvironment</groupId>
        <artifactId>HybridSystemsEnvironment</artifactId>
        <version>1.0.0</version>
    </dependency>
```

Gradle: add the following items to build.gradle

```java
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }

 
    dependencies {
    ...
    compile 'com.github.HybridSystemsEnvironment:HybridSystemsEnvironment:1.0.0'
    }
```

Alternatively, the jar file can be downloaded or assembled from the binaries and included as a project dependency manually.

## Contact 
Any questions or feedback is welcome at hybridsystemsenvironment@gmail.com.

## Acknowledgements
Thank you to the Center for Research in Open Source Software (CROSS) for the support and collaboration on this project (https://cross.ucsc.edu/).  Also thank you to Dr. Ricardo Sanfelice, for providing the opportunity to work on this, and for all of his support (https://hybrid.soe.ucsc.edu/).

