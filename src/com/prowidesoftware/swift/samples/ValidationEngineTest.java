package com.prowidesoftware.swift.samples;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.validator.BaseTestCase;
import com.prowidesoftware.swift.validator.StructureProblem;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Sample validation for many different message types
 * 
 * @author www.prowidesoftware.com
 */
public class ValidationEngineTest extends BaseTestCase {

	protected ValidationEngine engine;

	@Before
	public void setUp() throws Exception {
		this.engine = new ValidationEngine();
		this.engine.initialize();
	}

	@After
	public void tearDown() throws Exception {
		this.engine.dispose();
	}

	public static void dumpProblems(final List<ValidationProblem> problems, SwiftMessage m) {
		if (problems.isEmpty()) {
			System.out.println("MESSAGE OK. NO VALIDATION PROBLEMS FOUND.");
		} else {
			System.out.println("MALFORMED MESSAGE, "+problems.size()+" VALIDATION PROBLEM"+(problems.size()==1?"":"S")+" FOUND.");
			int k = 1;
			for (ValidationProblem p : problems) {
				String tagName = "";
				if (m != null && p.getTagIndex() != null) {
					Tag t = m.getBlock4().getTag(p.getTagIndex().intValue());
					tagName = " "+t.getName();
				}
				String seq ="";
				if (p.getSequence() != null) {
					seq = " (sequence: "+p.getSequence().getName()+")";
				}
				System.out.println(k + "/" + problems.size() + tagName + ": " + p.getErrorKey() + 
									" tag index " + p.getTagIndex() + " --> "+p.getMessage() + seq);
				k++;
			}
		}
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test971_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I971AAAAAAAAXXXXN}{4:\n" +
	    ":20:98031645\n" +
	    ":25:UFOOME\n" +
	    ":62F:D980314EUR1234,5\n" +
	    ":25:USDPRIME\n" +
	    ":62F:D980314USD1234,5\n" +
	    "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test950_1() throws Exception {
		String s = "{1:F01XXXXXXXXAXXX0387241036}{2:O9502352060913YYYYYYYYYYYY18884819330609140052N}{3:{108:0000000309140009}}{4:\n" +
				":20:123456\n" +
				":25:123 456789\n" +
				":28C:102\n" +
				":60F:C020527EUR3723495,\n" +
				":61:020528D1,2FBNK494935/DEV//67914\n" +
				":61:020528D30,2NCHK78911//123464\n" +
				":61:020528D250,NCHK67822//123460\n" +
				":61:020528D450,S103494933/DEV//P064118\n" +
				"FOO A. DESMIDEN\n" +
				":61:020528D500,NCH45633//123456\n" +
				":61:020528D1058,47S103494931//3841188\n" +
				"FOO B. F. JOE DOE\n" +
				":61:020528D2500,NCHK56728//123457\n" +
				":61:020528D3840,S103494935//3841189\n" +
				"FOO B. F. JOE DOE\n" +
				":61:020528D5000,S20023/200516//47829\n" +
				"ORDER ROTTERDAM\n" +
				":62F:C020528EUR3709865,13\n-}{5:{CHK:C0EB56371C00}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test942_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I942AAAAAAAAXXXXN}{4:\n" +
	    ":20:345678\n" +
			":21:5678\n" +
			":25:123-45678\n" +
			":28C:124/1\n" +
			":34F:EURD100000,\n" +
			":34F:EURC50000,\n" +
			":13D:0206261200+1200\n" +
			":61:020626D120000,NCOLABCD//12345\n" +
			":61:020626C55000,NFEX99485//678922\n" +
			":90D:9EUR210000,\n" +
			":90C:87EUR385700,\n" +
	    "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test941_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I941AAAAAAAAXXXXN}{4:\n" +
	    ":20:234567\n" +
	    ":21:765432\n" +
	    ":25:6894-77381\n" +
	    ":28:212\n" +
	    ":13D:0206041515+0200\n" +
	    ":60F:C020603EUR595771,95\n" +
	    ":90D:72EUR385920,\n" +
	    ":90C:44EUR450000,\n" +
	    ":62F:C020604EUR659851,95\n" +
	    ":64:C020604EUR480525,87\n" +
	    ":65:C020605EUR530691,95\n" +
	    "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test900_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I900AAAAAAAAXXXXN}{4:\n" +
	    ":20:C11126A1378\n" +
	    ":21:5482ABC\n" +
	    ":25:9-9876543\n" +
	    ":32A:980123USD233530,\n" +
	    "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test700_1() throws Exception {
		String s = "{1:F01CARBVEC0AXXX5480000053}{2:I700BICPDVSAXXXXN}{4:\n" +
				":27:1/1\n" +
				":40A:IRREVOCABLE\n" +
				":20:12345\n" +
				":23:PREADV/030510\n" +
				":31C:030517\n" +
				":40E:UCP LATEST VERSION\n" +
				":31D:030730AMSTERDAM\n" +
				":50:ABC COMPANY\n" +
				"KAERNTNERSTRASSE 3\n" +
				"VIENNA\n" +
				":59:AMDAM COMPANY\n" +
				"PO BOX 123\n" +
				"AMSTERDAM\n" +
				":32B:EUR100000,\n" +
				":41A:AMRONL2A\n" +
				"BY PAYMENT\n" +
				":43P:ALLOWED\n" +
				":43T:ALLOWED\n" +
				":44A:AMSTERDAM\n" +
				":44B:VIENNA\n" +
				":45A:+400,000 BOTTLES OF BEER\n" +
				"PACKED 12 TO AN EXPORT CARTON\n" +
				"+FCA AMSTERDAM\n" +
				":46A:+SIGNED COMMERCIAL INVOICE IN QUINTUPLICATE\n" +
				"+ FORWARDING AGENTS CERTIFICATE OF RECEIPT SHOWING GOODS\n" +
				"+ADDRESSED TO THE APPLICANT\n" +
				":48:WITHIN 6 DAYS OF ISSUANCE OF FCR\n" +
				":49:CONFIRM\n" +
				":57A:MEESNL2A\n" +
				"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test547_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX1535683724}{2:O5471227081107BBBBUS00DGST08299244490811070727N}{3:{108:000575CQ8256667}}{4:\n" +
		
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//X100003\n" +			//1
			":23G:NEWM\n" +						//3
			":16R:LINK\n" +						//4
			":22F::LINK//INFO\n" +				//5
			":13A::LINK//543\n" +				//6
			":20C::RELA//09010110000003\n" +	//7
			":16S:LINK\n" +						//8
			":16S:GENL\n" +						//9
			
			//Trade Details
			":16R:TRADDET\n" +					//10
			":98A::SETT//20090101\n" +			//11
			":98A::TRAD//20090101\n" +			//12
			":98A::ESET//20090101\n" +			//13
			":35B:ISIN IT0001465159\n" +		//14
			"/XX/5975932\n" +
			"ITALCEMENTI\n" +
			"EUR1\n" +
			":70E::SPRO///COMPLETE/\n" +		//15
			":16S:TRADDET\n" +					//16
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//17
			":36B::ESTT//UNIT/20000,\n" +		//18
			":97A::SAFE//987654321\n" +			//19
			":16S:FIAC\n" +						//20
			
			//Settlement Details
			":16R:SETDET\n" +					//21
			":22F::SETR//TRAD\n" +				//22
			
				//Settlement Parties
				":16R:SETPRTY\n" +								//23
				":95Q::BUYR//TEXT GOES HERE DDDDDDDDXXX\n" +		//24
				":97A::SAFE//123456789\n" +							//25
				":16S:SETPRTY\n" +								//26
				//Settlement Parties
				":16R:SETPRTY\n" +								//27
				":95Q::REAG//TEXT GOES HERE CCCCCCCCXXX\n" +		//28
				":16S:SETPRTY\n" +								//29
				//Settlement Parties
				":16R:SETPRTY\n" +								//30
				":95P::PSET//PPPPPPPP\n" +						//31
				":16S:SETPRTY\n" +								//32
				//Amount
				":16R:AMT\n" +									//33
				":19A::ESTT//EUR190764,\n" +					//34
				":98A::VALU//20081107\n" +						//35
				":16S:AMT\n" +									//36
				
			":16S:SETDET\n" +					//37
			"-}{5:{CHK:7B800746E45B}}\n" +		//38
			"";
	
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test546_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX1535683613}{2:O5461015081107BBBBUS00DGST08299115840811070515N}{3:{108:000575CQ8241552}}{4:\n" +
		
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//X100002\n" +			//1
			":23G:NEWM\n" +						//3
			":16R:LINK\n" +						//4
			":22F::LINK//INFO\n" +				//5
			":13A::LINK//542\n" +				//6
			":20C::RELA//09010110000002\n" +	//7
			":16S:LINK\n" +						//8
			":16S:GENL\n" +						//9
			
			//Trade Details
			":16R:TRADDET\n" +					//10
			":98A::SETT//20090101\n" +			//11
			":98A::TRAD//20090101\n" +			//12
			":98A::ESET//20090101\n" +			//13
			":35B:ISIN IT0003430813\n" +		//14
			"/XX/B0N64J1\n" +
			"MYCOMP GROUP\n" +
			"EUR0.25\n" +
			":70E::SPRO///COMPLETE/\n" +		//15
			":16S:TRADDET\n" +					//16
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//17
			":36B::ESTT//UNIT/110000,\n" +		//18
			":97A::SAFE//987654321\n" +			//19
			":16S:FIAC\n" +						//20
			
			//Settlement Details
			":16R:SETDET\n" +					//21
			":22F::SETR//TRAD\n" +				//22
			//":22F::DBNM//INTE\n" +				//22
			
				//Settlement Parties
				":16R:SETPRTY\n" +								//23
				":95Q::BUYR//TEXT GOES HERE DDDDDDDDXXX\n" +	//24
				":97A::SAFE//123456789\n" +						//25
				":16S:SETPRTY\n" +								//26
				//Settlement Parties
				":16R:SETPRTY\n" +								//27
				":95Q::REAG//TEXT GOES HERE CCCCCCCCXXX\n" +				//28
				":16S:SETPRTY\n" +								//29
				//Settlement Parties
				":16R:SETPRTY\n" +								//30
				":95P::PSET//PPPPPPPP\n" +						//31
				":16S:SETPRTY\n" +								//32
				
			":16S:SETDET\n" +					//33
			"-}{5:{CHK:7E62833FBD4D}}\n" +		//34
			"";
	
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test545_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX1540684651}{2:O5451117081110BBBBUS00DGST08309832700811100617N}{3:{108:000576CQ8231905}}{4:\n" +
		
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//X100001\n" +			//1
			":23G:NEWM\n" +						//3
			":16R:LINK\n" +						//4
			":22F::LINK//INFO\n" +				//5
			":13A::LINK//541\n" +				//6
			":20C::RELA//09010110000001\n" +	//7
			":16S:LINK\n" +						//8
			":16S:GENL\n" +						//9
			
			//Trade Details
			":16R:TRADDET\n" +					//10
			":98A::SETT//20090101\n" +			//11
			":98A::TRAD//20090101\n" +			//12
			":98A::ESET//20090101\n" +			//13
			":35B:ISIN IT0003211601\n" +		//14
			"/XX/7277528\n" +
			"BANCA FOO ABC-CASSA RISP GENOVA\n" +
			"EUR1\n" +
			":70E::SPRO///COMPLETE/\n" +		//15
			":16S:TRADDET\n" +					//16
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//17
			":36B::ESTT//UNIT/5000,\n" +		//18
			":97A::SAFE//987654321\n" +			//19
			":16S:FIAC\n" +						//20
			
			//Settlement Details
			":16R:SETDET\n" +					//21
			":22F::SETR//TRAD\n" +				//22
			
				//Settlement Parties
				":16R:SETPRTY\n" +								//23
				":95Q::SELL//TEXT GOES HERE DDDDDDDDXXX\n" +			//24
				":97A::SAFE//123456789\n" +					//25
				":16S:SETPRTY\n" +								//26
				//Settlement Parties
				":16R:SETPRTY\n" +								//27
				":95Q::DEAG//TEXT GOES HERE CCCCCCCCXXX\n" +	//28
				":16S:SETPRTY\n" +								//29
				//Settlement Parties
				":16R:SETPRTY\n" +								//30
				":95P::PSET//PPPPPPPP\n" +						//31
				":16S:SETPRTY\n" +								//32
				//Amount
				":16R:AMT\n" +	//33
				":19A::ESTT//EUR11235,\n"+ //34
				":19A::OCMT//EUR11235,\n" +						//35
				":98A::VALU//20081110\n" +						//36
				":16S:AMT\n" +									//37
				
			":16S:SETDET\n" +					//38
			"-}{5:{CHK:D6D37459446C}}\n" +		//39
			"";
	
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test544_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX1534682759}{2:O5441216081106BBBBUS00DGST08298582490811060716N}{3:{108:000574CQ8270598}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//X100000\n" +			//1
			":23G:NEWM\n" +						//2
			":16R:LINK\n" +						//3
			":22F::LINK//INFO\n" +				//4
			":13A::LINK//540\n" +				//5
			":20C::RELA//09010110000000\n" +	//6
			":16S:LINK\n" +						//7
			":16S:GENL\n" +						//8
			
			//Trade Details
			":16R:TRADDET\n" +					//9
			":98A::SETT//20090101\n" +			//10
			":98A::TRAD//20090101\n" +			//11
			":98A::ESET//20090101\n" +			//12
			":35B:ISIN IT0004176001\n" +		//13
			"/XX/B1W4V69\n" +
			"JOEJOE SPA\n" +
			"EUR0.10\n" +
			":70E::SPRO//COMPLETE\n" +			//14
			":16S:TRADDET\n" +					//15
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//16
			":36B::ESTT//UNIT/299000,\n" +		//17
			":97A::SAFE//987654321\n" +			//18
			":16S:FIAC\n" +						//19
			
			//Settlement Details
			":16R:SETDET\n" +					//20
			":22F::SETR//TRAD\n" +				//21
			
				//Settlement Parties
				":16R:SETPRTY\n" +								//22
				":95Q::SELL//TEXT GOES HERE DDDDDDDDXXX\n" +	//23
				":97A::SAFE//009643850229\n" +					//24
				":16S:SETPRTY\n" +								//25
				//Settlement Parties
				":16R:SETPRTY\n" +								//26
				":95Q::DEAG//TEXT GOES HERE CCCCCCCCXXX\n" +	//27
				":16S:SETPRTY\n" +								//28
				//Settlement Parties
				":16R:SETPRTY\n" +								//29
				":95P::PSET//PPPPPPPP\n" +						//30
				":16S:SETPRTY\n" +								//31
				
			":16S:SETDET\n" +					//32
			"-}{5:{CHK:625F3A7E17C7}}\n" +		//33
			"";
	
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test543_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX0000000000}{2:I543BBBBUS00XGSTN}{3:{108:09010110000003}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//09010110000003\n" +	//1
			":23G:NEWM\n" +						//2
			":16S:GENL\n" +						//3
			
			//Trade Details
			":16R:TRADDET\n" +					//4
			":98A::SETT//20090101\n" +			//5
			":98A::TRAD//20090101\n" +			//6
			":35B:ISIN IT0001465159\n" +		//7
			"1000000-3\n" +	
			"FOOCEMENTI FOO COMMON\n" +
			":16S:TRADDET\n" +					//8
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//9
			":36B::SETT//UNIT/20000,\n" +		//10
			":97A::SAFE//XXX123\n" +				//11
			":16S:FIAC\n" +						//12
			
			//Settlement Details
			":16R:SETDET\n" +					//13
			":22F::SETR//TRAD\n" +				//14
			
				//Settlement Parties
				":16R:SETPRTY\n" +				//15
				":95P::REAG//CCCCCCCC\n" +	//16
				":16S:SETPRTY\n" +				//17
				//Settlement Parties
				":16R:SETPRTY\n" +				//18
				":95Q::PSET//XXX\n" +			//19
				":16S:SETPRTY\n" +				//20
				//Settlement Parties
				":16R:SETPRTY\n" +				//21
				":95P::BUYR//DDDDDDDDXXX\n" +	//22
				":97A::SAFE//123456789\n" +			//24
				":16S:SETPRTY\n" +				//25
				//Amount
				":16R:AMT\n" +					//26
				":19A::SETT//EUR190764,00\n" +	//27
				":16S:AMT\n" +					//28
				
			":16S:SETDET\n" +					//29
			"-}\n" +
			"";
	
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test542_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX0000000000}{2:I542BBBBUS00XGSTN}{3:{108:09010110000002}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//09010110000002\n" +	//1
			":23G:NEWM\n" +						//2
			":16S:GENL\n" +						//3
			
			//Trade Details
			":16R:TRADDET\n" +					//4
			":98A::SETT//20090101\n" +			//5
			":98A::TRAD//20090101\n" +			//6
			":35B:ISIN IT0003430813\n" +		//7
			"1000000-2\n" +	
			"SAFOO GROUP CORP\n" +
			":16S:TRADDET\n" +					//8
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//9
			":36B::SETT//UNIT/110000,\n" +		//10
			":97A::SAFE//XXX123\n" +				//11
			":16S:FIAC\n" +						//12
			
			//Settlement Details
			":16R:SETDET\n" +					//13
			":22F::SETR//TRAD\n" +				//14
			
				//Settlement Parties
				":16R:SETPRTY\n" +				//15
				":95P::REAG//CCCCCCCC\n" +		//16
				":16S:SETPRTY\n" +				//17
				//Settlement Parties
				":16R:SETPRTY\n" +				//18
				":95Q::PSET//XXX\n" +			//19
				":16S:SETPRTY\n" +				//20
				//Settlement Parties
				":16R:SETPRTY\n" +				//21
				":95P::BUYR//DDDDDDDDXXX\n" +	//22
				":97A::SAFE//123456789\n" +		//23
				":16S:SETPRTY\n" +				//24
				
			":16S:SETDET\n" +					//25
			"-}\n" +
			"";
	
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test541_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX0000000000}{2:I541BBBBUS00XGSTN}{3:{108:09010110000001}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//09010110000001\n" +	//1
			":23G:NEWM\n" +						//2
			":16S:GENL\n" +						//3
			
			//Trade Details
			":16R:TRADDET\n" +					//4
			":98A::SETT//20090101\n" +			//5
			":98A::TRAD//20090101\n" +			//6
			":35B:ISIN IT0003211601\n" +		//7
			"1000000-1\n" +	
			"BANCA FOO COMMON\n" +
			":16S:TRADDET\n" +					//8
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//9
			":36B::SETT//UNIT/5000,\n" +		//10
			":97A::SAFE//XXX123\n" +				//11
			":16S:FIAC\n" +						//12
			
			//Settlement Details
			":16R:SETDET\n" +					//13
			":22F::SETR//TRAD\n" +				//14
			
				//Settlement Parties
				":16R:SETPRTY\n" +				//15
				":95Q::DEAG//CCCCCCCC\n" +		//16
				":16S:SETPRTY\n" +				//17
				//Settlement Parties
				":16R:SETPRTY\n" +				//18
				":95P::SELL//DDDDDDDDXXX\n" +		//19
				":97A::SAFE//123456789\n" +	//20
				":16S:SETPRTY\n" +				//21
				//Settlement Parties
				":16R:SETPRTY\n" +				//22
				":95Q::PSET//XXX\n" +			//23
				":16S:SETPRTY\n" +				//24
				//Amount
				":16R:AMT\n" +					//25
				":19A::SETT//EUR11235,00\n" +	//26
				":16S:AMT\n" +					//27
				
			":16S:SETDET\n" +					//28
			"-}\n" +
			"";
	
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test540_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX0000000000}{2:I540BBBBUS00XGSTN}{3:{108:09010110000000}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//23423423\n" +	//1
			":23G:NEWM\n" +						//2
			":16S:GENL\n" +						//3
			
			//Trade Details
			":16R:TRADDET\n" +					//4
			":98A::SETT//20090101\n" +			//5
			":98A::TRAD//20090101\n" +			//6
			":35B:ISIN IT0004176001\n" +		//7
			"1000000-0\n" +	
			"FOOSAAAN SPA COMMON\n" +
			":16S:TRADDET\n" +					//8
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//9
			":36B::SETT//UNIT/299000,\n" +		//10
			":97A::SAFE//XXX123\n" +			//11
			":16S:FIAC\n" +						//12
			
			//Settlement Details
			":16R:SETDET\n" +					//13
			":22F::SETR//TRAD\n" +				//14
			
				//Settlement Parties
				":16R:SETPRTY\n" +				//15
				":95Q::DEAG//CCCCCCCC\n" +		//16
				":16S:SETPRTY\n" +				//17
				//Settlement Parties
				":16R:SETPRTY\n" +				//18
				":95P::SELL//DDDDDDDDXXX\n" +	//19
				":97A::SAFE//123456789\n" +		//20
				":16S:SETPRTY\n" +				//21
				//Settlement Parties
				":16R:SETPRTY\n" +				//22
				":95Q::PSET//XXX\n" +			//23
				":16S:SETPRTY\n" +				//24
				
			":16S:SETDET\n" +					//25
			"-}\n" +
			"";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test300_1() throws Exception {
		String s = "{1:F01ABCBUS33AXXX3768156193}{2:O3001139050822XYZBUS33AFXO29569650200508221139N}{3:{108:FC003105ded7970a}}{4:\n" +
			   ":15A:\n" +
			   ":20:QCOUCN\n" +
			   ":21:NEW\n" +
			   ":22A:CANC\n" +
			   ":22C:ABCB334209XYZB33\n" +
			   ":82A:XYZBUS33FXO\n" +
			   ":87A:ABCBUS33XXX\n" +
			   ":77D:/VALD/20040509\n" +
			   "/SETC/USD\n" +
			   ":15B:\n" +
			   ":30T:20070422\n" +
			   ":30V:20070513\n" +
			   ":36:542,09\n" +
			   ":32B:CLP3794630000,\n" +
			   ":53A:XYZBUS33FXO\n" +
			   ":57D:NET SETTLEMENT\n" +
			   ":33B:USD7000000,00\n" +
			   ":53A:ABCBUS33XXX\n" +
			   ":57D:NET SETTLEMENT\n" +
			   ":58A:/9301011483\n" +
			   "ABCBUS33XXX\n" +
			   ":15C:\n" +
			   ":24D:BROK\n" +
			   ":88D:GFI-NY\n" +
			   ":72:/ACC/GTMS:\n" +
			   "//L1710833-1-1\n" +
			   "-}{5:{CHK:97BE21F26A78}}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test202() throws Exception {
		String s = "{1:F01BICFOOYYAXXX8628453424}{2:O2021300050901IRVTLULXALTA06556102830509011300N}{4:\n" + 
				":20:RFSAMPPGN0031091\n" + 
				":21:RFSAMPPGN0031091\n" + 
				":13C:/RNCTIME/1356+0000\n" +
				":13C:/RNCTIME/1410+0000\n" +
				":32A:050901EUR19265,53\n" + 
				":52A:IRVTLULXLTA\n" + 
				":53A:/D/1234A0123456ABC012345\n" + 
				"BICFOOYY\n" + 
				":54A:BICFOOYY\n" + 
				":56A:BICFOOYY\n" + 
				":57A:BICFOOYY\n" + 
				":58A:/ES12 1234 6789 1234 1111 1234\n" + 
				"BICFOOYY\n" + 
				":72:/BNF/00002695 0001 2005083130110\n" + 
				"-}\n" + 
				"";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

	/**
	 * No error. Field 79 not present, additional fields not present
	 * @throws Exception
	 */
	@Test
	public void test195_ok() throws Exception {
		String s = "{1:F01CARBVEC0AXXX3048000056}{2:I195AAAAAAAAXXXXN}{4:\n" +
				":20: 516722/QUERY\n" +
				":21:948LA\n" +
				":75:FOO FOO FOO BAR\n" +
				":77A: Please explain the NSTP claim\n" +
				"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertNotReported(r, StructureProblem.T13);
	}

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test103_1() throws Exception {
		String s = "{1:F01FOOBVEC0AXXX0344000050}{2:I103FOOAANC0XXXXN}{4:\n"+
					":20:TBEXO200909031\n"+
					":23B:CRED\n"+
					":32A:090903USD23453,\n"+
					":50K:/01111001444234567890\n"+
					"JOE DOE\n"+
					"R00000V0574734\n"+
					":53B:/00010333800002001234\n"+
					"MI BANCO\n"+
					":59:/00013533310020179998\n"+
					"COMPANY\n"+
					"R00000V000034534\n"+
					":71A:OUR\n"+
					":72:/TIPO/422\n"+
					"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r, msg);
	}

	/**
	 * This example contains a non-existing field 99.
	 */
	@SuppressWarnings("boxing")
	@Test
	public void test103_2() throws Exception {
		String s = "{1:F01FOOBVEC0AXXX0344000050}{2:I103FOOAANC0XXXXN}{4:\n"+
					//":20:TBEXO200909031\n"+
					":23B:CRED\n"+
					":32A:090903USD23453,\n"+
					":99:RFSAMPPGN0039991\n" +
					":50K:/01111001711131117890\n"+
					"JOE DOE\n"+
					"R00000V0574734\n"+
					":53B:00010013800002001234\n"+
					"MY BANK\n"+
					":59:/00013500510023333998\n"+
					"COMPFOO CORP\n"+
					"R00000V000034111\n"+
					":71A:OUR\n"+
					":72:/TIPO/422\n"+
					"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r, msg);
	}

}
