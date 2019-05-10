prowide-integrator-examples
===========================

Source code examples for "Prowide Integrator". 

IMPORTANT: This code is intended for Integrator customers or developers using an Integrator trial. Code in this repo will not compile without a running version of the Integrator library jars. In order to run the examples you should unpack your acquired jars in the lib dir and run ant here.

If you are interested on this software package you may request a trial download here: http://www.prowidesoftware.com/contact-us

Build
=====
You should place in the lib directory the pw-swift-integrator-SRU{version}.jar received with your Integrator trial/purchase
and configure the dependency accordingly in the build.gradle

You can create configuration for Intellij Idea and Eclipse with
./gradlew idea
./gradlew eclipse

To build
./gradlew build
Notice the build could will fail for some classes if your Intgrator trial/purchase does not contain all modules.

Run
===
The primary intention of the examples is to show code. Although, all examples are provided as single classes with a main
that you can run in your IDE.


Quick Overview
==============

"Prowide Integrator" is a SWIFT messages development library, built on top of the open source library "Prowide Core".

It is intended for organizations looking for reduced efforts in the implementation and maintenance of its SWIFT software infrastructure. Especially suited to automate today's complex message flows.

The Integrator is distributed as an SDK with a range of optional modules. It can thus be tailored to fit your own SWIFT Integration needs. The SDK is the cornerstone of the package; as it implements the model and parser for both FIN MT and MX standards on which functional modules are implemented. Each optional module provides specific features.

For additional information please check: http://www.prowidesoftware.com/products/integrator

