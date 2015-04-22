package com.prowidesoftware.swift.samples.integrator;

import java.util.List;

import com.prowidesoftware.swift.model.MtPath;
import com.prowidesoftware.swift.model.MtPathResult;
import com.prowidesoftware.swift.model.SwiftBlock2OutputField;
import com.prowidesoftware.swift.model.mt.mt5xx.MT535;

/**
 * Sample program to show main features of the MT path API.<br> 
 * This is a feature of Prowide Integrator SDK, used to retrieve content from 
 * MT messages using concepts derived from XPath expressions.
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MtPathExample {

    public static void main(final String[] args) throws Exception {
    	List<MtPathResult> found = null;
    	
    	/*
    	 * get second component of field 20C with qualifier SEME
    	 */
    	found = MtPath.evaluate("20C/SEME/2", mt);
    	System.out.println(found.get(0).getComponent());
    	
    	/*
    	 * get the fifth component of all fields 93 (any letter option)
    	 * with qualifier AVAI, present in the first instance of sequence B1b
    	 */
    	found = MtPath.evaluate("B1b[1]/93a/AVAI/5", mt);
    	for (MtPathResult f : found) {
    		System.out.println(f.getComponent());
    	}
    	
    	/*
    	 * get the first subsequence B1a from any sequence B1
    	 */
    	found = MtPath.evaluate("B/B1[*]/B1a[1]", mt);
    	System.out.println("total found: "+found.size());
    	
    	/*
    	 * get all fields 95P within a sequence B or any of its subsequences
    	 */
		found = MtPath.evaluate("B[*]/93B", mt);
    	for (MtPathResult f : found) {
    		System.out.println(f.getField().getValue());
    	}
    	
    	/*
    	 * get the forth component of fields 35B, found in the parent sequence
    	 * of each second instance of a sequence B1b
    	 */
		found = MtPath.evaluate("parent::B1b[2]/35B/4", mt);

		/*
		 * get the MIR logical terminal address from header block 2
		 */
		found = MtPath.evaluate("b2/" + SwiftBlock2OutputField.MIRLogicalTerminal, mt);
    	System.out.println(found.get(0).getComponent());

    }
    
    /*
     * sample message
     */
    static MT535 mt = MT535.parse(
			"{1:F01ZZZZZZZZZZZG0387240778}{2:O5350029060914XXXXXXXXXXXX03549878070609140029N}{4:\n" +
    
					// sequence A - General Information
					":16R:GENL\n" +
					":28E:00005/MORE\n" +
					":20C::SEME//ICF2750999999005\n" +
					":23G:NEWM\n" +
					":98A::STAT//20060913\n" +
					":22F::SFRE//DAIL\n" +
					":22F::CODE//COMP\n" +
					":22F::STTY//CUST\n" +
					":22F::STBA//TRAD\n" +
					":97A::SAFE//F275\n" +
					":17B::ACTI//Y\n" +
					":17B::AUDT//N\n" +
					":17B::CONS//N\n" +
					":16S:GENL\n" +

                    //sequence B - Sub-safekeeping account
                    ":16R:SUBSAFE\n" +
                    
                        //B1
                        ":16R:FIN\n" +
                        ":35B:/US/31392EXH8\n" +
                        "FEDERAL FOOO MTG ASSN\n" +
                        
	                        //B1a
	                        ":16R:FIA\n" +
	                        ":92A::CUFC//0,14528727\n" +
	                        ":16S:FIA\n" +
	                        ":93B::AGGR//FAMT/35732656,\n" +
	                        ":93B::AVAI//FAMT/35732656,\n" +
	                        
	                        //B1b
	                        ":16R:SUBBAL\n" +
	                        ":93B::AGGR//FAMT/1234,5\n" +
	                        ":93B::AVAI//FAMT/2345,6\n" +
	                        ":93B::AVAI//AMOR/3456,7\n" +
	                        ":94F::SAFE//NCSD/FRNYUS33\n" +
	                        ":16S:SUBBAL\n" +
	                        ":16S:FIN\n" +

                    ":16S:SUBSAFE\n" +

			"-}{5:{MAC:E19445CF}{CHK:D625798DFC51}}");

}
