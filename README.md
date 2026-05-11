prowide-integrator-examples
===========================

Source code samples for **Prowide Integrator** — meant for prospects evaluating
a trial and for licensed customers exploring features in code.

> This project will not compile or run without the Integrator library jars.
> Request a trial at <http://www.prowidesoftware.com/contact-us>.

Requirements
------------
* Java 11 or higher
* The Prowide Integrator library jars (trial or production), dropped into `lib/`

First run
---------
1. Copy the Integrator jars you received into `lib/`. See `lib/put_your_jars_here.txt`
   for the expected file set.
2. Verify the jars are detected and aligned:
   ```
   ./gradlew doctor
   ```
   Sample output:
   ```
   [OK]      pw-swift-core                          SRU2025-10.3.12
   [OK]      pw-iso20022                            SRU2025-10.3.5
   [OK]      pw-swift-integrator-sdk                SRU2025-10.3.26
   ...
   ```
3. List every available example grouped by module:
   ```
   ./gradlew listExamples
   ```
4. Run any example by its fully qualified class name:
   ```
   ./gradlew runExample -PmainClass=com.prowidesoftware.swift.samples.integrator.sdk.MxCreation1Example
   ```
   Every example class also has a `main` method, so you can run it directly from
   IntelliJ IDEA without going through Gradle.

What's in here
--------------
Examples are organized by Integrator module. Trials are typically scoped to a
subset of modules — `./gradlew doctor` tells you which examples will work in
your environment.

| Module        | Package                                                     | Highlights                                            |
|---------------|-------------------------------------------------------------|-------------------------------------------------------|
| SDK           | `com.prowidesoftware.swift.samples.integrator.sdk`          | MX creation/parsing, BIC directory, DataPDU, LAU      |
| Validation    | `com.prowidesoftware.swift.samples.integrator.validation`   | MT/MX validation, custom rules, NAK creation          |
| Translations  | `com.prowidesoftware.swift.samples.integrator.translations` | MT ↔ MX translations                                  |
| MyFormat      | `com.prowidesoftware.swift.samples.integrator.myformat`     | CSV/XML ↔ MT/MX conversions, JSON, MT940 round-trip   |

The placeholder root example, `VersionChecker`, is the same code as the
`doctor` task — run either to confirm your setup.

Partial trials
--------------
If your trial does not include every module, the build will fail to compile the
corresponding sample sources. Two options:

* Remove the matching module jars from `lib/` **and** delete the matching
  package under `src/main/java/com/prowidesoftware/swift/samples/integrator/`.
* Or comment out the relevant `implementation 'com.prowidesoftware:...'` line
  if you switch to a Maven-repo template (see `templates/`).

Templates
---------
The `templates/` folder contains alternative `build.gradle` and `pom.xml`
examples for prospects who:

* Resolve the Integrator jars from a Maven/Gradle repository (typically Prowide's
  Nexus, with credentials) instead of dropping them into `lib/`.
* Prefer Maven over Gradle.

Issues
------
Customers can open tickets through the Prowide Customer Jira portal.

About Prowide Integrator
------------------------
Prowide Integrator is a SWIFT message development library, built on top of the
open-source `prowide-core`. It targets organizations that need to reduce the
effort of implementing and maintaining SWIFT messaging — covering both FIN MT
and ISO 20022 MX standards through a single SDK plus a set of optional
functional modules.

More information: <http://www.prowidesoftware.com/products/integrator>
Developer docs: <https://dev.prowidesoftware.com/latest/getting-started/>
