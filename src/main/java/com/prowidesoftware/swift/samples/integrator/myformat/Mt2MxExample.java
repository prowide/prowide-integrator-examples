/*******************************************************************************
 * Copyright (c) 2019 Prowide Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as 
 *     published by the Free Software Foundation, either version 3 of the 
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *     
 *     Check the LGPL at <http://www.gnu.org/licenses/> for more details.
 *******************************************************************************/
package com.prowidesoftware.swift.samples.integrator.myformat;

import java.util.ArrayList;
import java.util.List;

import com.prowidesoftware.swift.model.MxId;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field21;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;
import com.prowidesoftware.swift.model.mx.MxCamt00300105;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;

/**
 * This example shows how to convert and MT into an MX
 * using API from Prowide Integrator MyFormat module.
 * <br>
 * The mapping rules in this example are defined programmatically.
 * 
 * @since 7.8
 */
public class Mt2MxExample {
	public static void main(String[] args) {
		/*
		 * source message
		 */
		MT202 mt = new MT202();
		mt.addField(new Field20("12345"));
		mt.addField(new Field21("CHNG"));

		/*
		 * programmatic mapping rules
		 */
		List<MappingRule> rules = new ArrayList<MappingRule>();
		rules.add(new MappingRule("20", "/AppHdr/Fr/FIId/BrnchId/Id", new Transformation(Key.prepend, "id")));
		rules.add(new MappingRule("\"foo\"", "/AppHdr/To/FIId/BrnchId/Id"));
		rules.add(new MappingRule("\"1602\"", "/AppHdr/Fr/FIId/BrnchId/PstlAdr/PstCd"));
		rules.add(new MappingRule("21", "/Document/GetAcct/AcctQryDef/QryTp"));

		/*
		 * translation call
		 */
		MxCamt00300105 mx = (MxCamt00300105) MyFormatEngine.translate(mt, new MxId("camt.003.001.05"), rules);

		/*
		 * Print result:
		 * 
		 * <?xml version="1.0" encoding="UTF-8" ?>
		 * <message>
		 * 	 <AppHdr>
		 *     <Fr>
		 *       <FIId>
		 *         <BrnchId>
		 *           <Id>id12345</Id>
		 *           <PstlAdr>
		 *             <PstCd>1602</PstCd>
		 *           </PstlAdr>
		 *         </BrnchId>
		 *       </FIId>
		 *     </Fr>
		 *     <To>
		 *       <FIId>
		 *         <BrnchId>
		 *           <Id>foo</Id>
		 *         </BrnchId>
		 *       </FIId>
		 *     </To>
		 *   </AppHdr>
		 *   <Doc:Document xmlns:Doc="urn:swift:xsd:camt.003.001.05" xmlns:xsi="{http://www.w3.org/2000/xmlns/}Doc">
		 *     <Doc:GetAcct>
		 *       <Doc:AcctQryDef>
		 *         <Doc:QryTp>CHNG</Doc:QryTp>
		 *       </Doc:AcctQryDef>
		 *     </Doc:GetAcct>
		 *   </Doc:Document>
		 * </message>
		 */
		System.out.println(mx.message("message"));
	}
}
