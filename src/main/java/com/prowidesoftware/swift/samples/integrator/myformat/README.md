MyFormat module examples
========================

Examples in this source directory corresponds to API from Prowide Integrator MyFormat module.

To run this examples you need a license or trial of Prowide Integrator including the MyFormat module. For additional
information please check: https://www.prowidesoftware.com/products/integrator/my-format

Spreadsheet driven examples
---------------------------

These load their mapping rules from a spreadsheet in `src/main/resources/myformat/`. One sheet is one mapping table,
and one example class loads one sheet.

| Spreadsheet    | Sheet          | Example                 | Shows                                               |
|----------------|----------------|-------------------------|-----------------------------------------------------|
| `json2mt.xls`  | `SIMPLE`       | `Json2MtExample1`       | flat JSON to MT, transformations, `UPDATE` mode      |
|                | `INDEX`        | `Json2MtExample2`       | source array addressed by literal position           |
|                | `SEQUENCE`     | `Json2MtExample3`       | repetitive sequences via `foreach`, typed `MtWriter` |
|                | `REP_SEQ`      | `Json2MtExample5`       | sequences nested three levels deep                   |
| `json2mx.xls`  | `SIMPLE`       | `Json2MxExample1`       | nested arrays driving repetitive MX elements         |
|                | `PATH`         | `Json2MxExample2`       | source path aliases via `pathNames`                  |
|                | `REPLICATED`   | `Json2MxExample3`       | uppercase placeholders replicate the parent value    |
|                | `REP_VALUESET` | `Json2MxExample4`       | value set selector `[*]` feeding the repetition      |
| `mx2json.xls`  | `SIMPLE`       | `Mx2JsonExample1`       | absolute selectors, indexed and non indexed mixed    |
|                | `RELATIVE`     | `Mx2JsonExample2`       | the `//` shorthand, same result as `SIMPLE`          |
|                | `FOREACH`      | `Mx2JsonExample3`       | `FOREACH` keeps every repetition as its own entry    |
|                | `FOREACH_REL`  | *(reference only)*      | `FOREACH` written with the `//` shorthand            |
| `mt2json.xls`  | `SIMPLE`       | `Mt2JsonExcelExample`   | MT to JSON driven by a spreadsheet                   |
| `csv2mt.xls`   | `example1`     | `Csv2MtExample1`        | CSV to MT                                            |
| `csv2mx.xls`   | `example1..4`  | `Csv2MxExample1..4`     | CSV to MX                                            |
| `mt2csv.xls`   | `example 1..2` | `Mt2CsvExample1..2`     | MT to CSV                                            |
| `xml2mt.xls`   | `example1..2`  | `Xml2MtExample1..2`     | XML to MT                                            |
| `MT940/*.xls`  | `Mapping`      | `Csv2MT940`, `MT9402Csv`| CSV to and from MT940                                |

`Mx2JsonExample1` and `Mx2JsonExample2` run the same input through two sheets that differ only in selector syntax, so
their output is identical on purpose. `FOREACH_REL` has no example class for the same reason: run every sheet of
`mx2json.xls` against one input and `SIMPLE` equals `RELATIVE`, and `FOREACH` equals `FOREACH_REL`. The absolute
versus relative syntax makes no difference to the result, so it is demonstrated once, by `Mx2JsonExample2`.

Programmatic examples
---------------------

These build the mapping rules in code instead of loading a spreadsheet: `Csv2MtExample2`, `Csv2MxExample5`,
`Json2MtExample4`, `Mt2CsvProgrammaticExample`, `Mt2JsonExample1`, `Mt2JsonExample2`, `Mt2MxExample` and
`Mx2CsvExample`. `Csv2MxExample1` and `Csv2MxExample3` do both, loading a sheet and then adding rules in code.
