# Changelog

All notable changes to this examples project are documented here.
Format loosely follows [Keep a Changelog](https://keepachangelog.com/).

## [Unreleased]

### Added
- MyFormat: `Json2MtExample` — proprietary JSON into MT564, showing sequence and qualifier aware target selectors (`E2/19B/ENTL/3`) and header targets (`b1/LogicalTerminal`).
- MyFormat: `Mt2JsonExample2` — MT564 into proprietary JSON and from there into your own POJO model with Gson.
- SDK: `Mt564JsonPojoExample` — MT564 to POJO to canonical JSON and back, plus message creation with the sequences API, all without a mapping table.
- `./gradlew listExamples` — enumerates every runnable example grouped by Integrator module.
- `./gradlew doctor` — health check that confirms which Integrator modules are present in `lib/` and reports their SRU versions; missing modules are reported but do not abort the run.
- `CHANGELOG.md` to track functional changes in this examples project.

### Changed
- `VersionChecker`/`doctor` accepts more than one probe class per module, so a module is still detected when its model classes are relocated between releases (the SIC messages moved to version specific packages such as `...model.mx.sic.v4_10` in SRU2026).
- `MessageValidationWithCustomSchema` builds the custom scheme `release` from `SRU.currentString()` instead of a hardcoded year, which made it fail whenever the jars were upgraded to a new SRU.
- README restructured around the prospect evaluation journey (first-run path, module-to-example index, troubleshooting).
- `lib/put_your_jars_here.txt` refreshed to reflect the current SRU2025 module set.
- Third-party dependency versions in `build.gradle` and the templates aligned with `pw-swift-integrator` (gson 2.14.0, POI 4.1.2, jaxb-impl 4.0.6, etc.).
- Templates renamed: `*_Java11+.*-example` → `*.*-example` (the project requires Java 11+ so the suffix is no longer meaningful).
- Example classes that were missing a class-level javadoc (DataPDUParserExample, DataPDUWriterExample, Mt2JsonExample) now have one; a copy-pasted javadoc in `Mx_DataPDU_LAU_Example` was corrected.

### Removed
- All references to the deprecated `pw-swift-integrator-sepa` jar.
- All references to the `pw-swift-integrator-data` jar.
- Java 8 template files (`gradle_repo_build_Java8.gradle-example`, `mvn_repo_pom_Java8.xml-example`).
- Stray `derby.log` from the working tree (now also explicitly gitignored together with `*.lck`).
