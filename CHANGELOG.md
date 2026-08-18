# Changelog

All notable changes to this examples project are documented here.
Format loosely follows [Keep a Changelog](https://keepachangelog.com/).

## [Unreleased]

### Added
- Translations: `Mt103Pacs008TranslationExample`, `Pacs008Mt103TranslationExample`, `Camt053Mt940TranslationExample`, `PagedTranslationExample`, `BulkTranslationExample`, `ClearingSystemTranslationExample` and `TranslatorConfigurationExample` — factory provider based translations for the main payment pairs, truncation and coverage reports, paged and bulk modes, and the field 103 clearing dispatcher.
- Validation: `MtValidationEngineExample`, `MxValidationEngineFactoryExample`, `MxValidationCustomRulesExample` and `MxValidationConfigurationExample` — the current MT and MX validation entry points, custom MX rules, and the external code set and missing data element options.
- SDK: `SwiftMessageFactoryFormatsExample`, `MxExpandedPrintoutExample`, `Block4XmlExample`, `MtSanitizerExample`, `ExternalCodeSetsExample` and `LauVerificationExample`.
- MyFormat: `TransformationsShowcaseExample` (tour of the transformation functions with the verbose execution log), `CustomTransformerExample`, `FixedLen2MxExample` and `Mx2FixedLenExample` (fixed length in both directions), `MappingTableCsvLoaderExample`, `MappingTableDatabaseExample`, `Mx2MtMyFormatExample` and `MxBuilderExample`.
- MyFormat: `ConditionalMappingExample` — conditional target field selection from a mapping table: there is no conditions column, guards in the transformation pipeline decide which rule writes.
- CBPR+: new `cbpr` package with `CbprParseExample`, `CbprValidationExample` and `CbprTranslationExample`.
- SCORE: new `score` package with `Score798ParseExample` and `Score798ValidationExample`.
- SIC: new `sic` package with `SicParseExample`, `SicValidationExample` and `SicTranslationExample`.
- MyFormat: spreadsheet driven JSON mapping examples — `Json2MtExample1..3` and `Json2MtExample5` (`json2mt.xls`), `Json2MxExample1..4` (`json2mx.xls`), `Mx2JsonExample1..3` (`mx2json.xls`) and `Mt2JsonExcelExample` (`mt2json.xls`), covering literal and indexed selectors, path aliases, value sets, and repetitive and nested sequences.
- MyFormat: `myformat/README.md` now indexes every spreadsheet, sheet and example class, and separates the spreadsheet driven examples from the programmatic ones.
- MyFormat: `Json2MtExample4` — proprietary JSON into MT564, showing sequence and qualifier aware target selectors (`E2/19B/ENTL/3`) and header targets (`b1/LogicalTerminal`).
- MyFormat: `Mt2JsonExample2` — MT564 into proprietary JSON and from there into your own POJO model with Gson.
- SDK: `Mt564JsonPojoExample` — MT564 to POJO to canonical JSON and back, plus message creation with the sequences API, all without a mapping table.
- `./gradlew listExamples` — enumerates every runnable example grouped by Integrator module.
- `./gradlew doctor` — health check that confirms which Integrator modules are present in `lib/` and reports their SRU versions; missing modules are reported but do not abort the run.
- `CHANGELOG.md` to track functional changes in this examples project.

### Changed
- `Json2MtExample4` also maps the optional UTC offset of the 98E announcement date (components 5 and 6), producing `:98E::ANOU//20260805103000/N0500`.
- README module table extended with the CBPR+, SCORE and SIC packages.
- `listExamples` groups and names the examples correctly on Windows (path separator normalization).
- `VersionChecker`/`doctor` accepts more than one probe class per module, so a module is still detected when its model classes are relocated between releases (the SIC messages moved to version specific packages such as `...model.mx.sic.v4_10` in SRU2026).
- `MessageValidationWithCustomSchema` builds the custom scheme `release` from `SRU.currentString()` instead of a hardcoded year, which made it fail whenever the jars were upgraded to a new SRU.
- README restructured around the prospect evaluation journey (first-run path, module-to-example index, troubleshooting).
- `lib/put_your_jars_here.txt` refreshed to reflect the current SRU2025 module set.
- Third-party dependency versions in `build.gradle` and the templates aligned with `pw-swift-integrator` (gson 2.14.0, POI 4.1.2, jaxb-impl 4.0.6, etc.).
- Templates renamed: `*_Java11+.*-example` → `*.*-example` (the project requires Java 11+ so the suffix is no longer meaningful).
- Example classes that were missing a class-level javadoc (DataPDUParserExample, DataPDUWriterExample, Mt2JsonExample1) now have one; a copy-pasted javadoc in `Mx_DataPDU_LAU_Example` was corrected.

### Fixed
- `csv2mx.xls` sheet `example3` targeted two paths that do not exist in `pain.001.001.03` (`CdtTrfTxInf/EndToEndId` missing its `PmtId` parent, and a misspelled `ClrSysMmbId/ClrSysId`), so `Csv2MxExample3` printed mapping errors on every run and dropped both fields.
- `json2mx.xls` sheet `REP_VALUESET` was missing a separator in `root.GRP_HDR.CUSTREF`, so `Json2MxExample4` emitted a message with no `GrpHdr`.
- `DataPDUParserExample` and `DataPDUWriterExample` verified values the code no longer produces; both now print what they parse or build instead of asserting it, since assertions are disabled by default.
- `JsonExample` printed nothing and wrote its output to a hardcoded `/tmp` path; it now prints the four JSON representations.
- Sample data is dummy throughout: no BIC in the project resolves to a real institution, and identifiable names, network trailers, message references and spreadsheet authoring metadata have been replaced with placeholders.

### Removed
- All references to the deprecated `pw-swift-integrator-sepa` jar.
- All references to the `pw-swift-integrator-data` jar.
- Java 8 template files (`gradle_repo_build_Java8.gradle-example`, `mvn_repo_pom_Java8.xml-example`).
- Stray `derby.log` from the working tree (now also explicitly gitignored together with `*.lck`).
