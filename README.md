prowide-integrator-examples
===========================

Source code examples for "Prowide Integrator". 

IMPORTANT: This code is intended for Integrator customers or developers using an Integrator trial. 
This project will not compile without a running version of the Integrator library jars.
 

If you are interested on this software package you may request a trial download here: http://www.prowidesoftware.com/contact-us

Requirements
============
* Java 11+
* Prowide Integrator library jars (trial or production version)

Build
=====
In build the project:
* Unpack your acquired production or trial Integrator jars in the lib folder.
* Review the jar versions in the build.gradle dependencies
* Run ./gradlew build 

You can create configuration for Intellij Idea and Eclipse with
./gradlew idea
./gradlew eclipse

Notice the build could fail for some classes if your Integrator trial/purchase does not contain all modules.
So you might need to remove the missing module dependencies from the build.gradle and also remove or comment the 
missing module source code. For example, if your trial is only for Integrator Validation, then you should remove the
dependencies for pw-swift-integrator-translations-*.jar and also remove the source folder 
src/main/java/com/prowidesoftware/swift/samples/integrator/translations

Run
===
The primary intention of the examples is to show code. Most of them produce some kind of output though. 
All example classes have a main method that you can easily run in your IDE.

Please refer to [Compile and Run](https://dev.prowidesoftware.com/latest/getting-started/#compile-and-run) in order to set up your project dependencies

Issues
===
For Prowide Integration questions or issues please use the Prowide Customer Jira portal


Quick Overview
==============

"Prowide Integrator" is a SWIFT message development library, built on top of the open source library "Prowide Core".

It is intended for organizations looking for reduced efforts in the implementation and maintenance of its SWIFT software infrastructure. Especially suited to automate today's complex message flows.

The Integrator is distributed as an SDK with a range of optional modules. It can thus be tailored to fit your own SWIFT Integration needs. The SDK is the cornerstone of the package; as it implements the model and parser for both FIN MT and MX standards on which functional modules are implemented. Each optional module provides specific features.

For additional information please check: http://www.prowidesoftware.com/products/integrator

