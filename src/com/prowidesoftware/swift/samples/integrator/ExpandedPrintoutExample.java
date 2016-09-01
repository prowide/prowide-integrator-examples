package com.prowidesoftware.swift.samples.integrator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.prowidesoftware.swift.BICDirectory;
import com.prowidesoftware.swift.model.BICRecord;
import com.prowidesoftware.swift.model.LogicalTerminalAddress;
import com.prowidesoftware.swift.model.SwiftBlock2Input;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.GenericField;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.MTInfo;
import com.prowidesoftware.swift.model.mt.MtStandard;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;
import com.prowidesoftware.swift.model.mt.mt5xx.MT515;
import com.prowidesoftware.swift.model.mt.mt5xx.MT518;
import com.prowidesoftware.swift.model.mt.mt5xx.MT535;
import com.prowidesoftware.swift.model.mt.mt6xx.MT671;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.prowidesoftware.swift.scheme.Scheme;
import com.prowidesoftware.swift.scheme.SchemeSequence;

/**
 * This example shows how to generate text printout of messages generically (for any message type)
 * using features from Prowide Integrator SDK as the BIC directory to expand BICs and the MTInfo 
 * helper class to gather sequence labels. 
 * <br />
 * It can be used as base code to extend custom pritouts of MT content.
 * 
 *<pre> 
------------------------- Instance Type and Transmission -------------------------
Copy sent to SWIFT
Priority/Delivery : Normal

------------------------- Message Header -----------------------------------------
Swift    : FIN 515 Client Confirmation of Purchase or Sale
Sender   : RBDSJPJTBXXX - RBC CAPITAL MARKETS (JAPAN) LTD., TOKYO (JAPAN)
Receiver : BOJPJPJTXMF1 - BANK OF JAPAN - (FOR JAPAN'S RESERVES USE ONLY) (JAPAN)
MUR      : FOOB3926BE868XXX

------------------------- Message Text -------------------------------------------

A - General Information
20C: Reference
	Qualifier: Sender's Message Reference
	Reference: M1999999.1
23G: Function of the Message
	Function: NEWM
22F: Indicator
	Qualifier: Trade Transaction Type
	Indicator: TRAD

A1 - Linkages
20C: Reference
	Qualifier: Previous Message Reference
	Reference: M1999999.1

C - Confirmation Details
98A: Date
	Qualifier: Trade Date/Time
	Date: Jul 12, 2016
98A: Date
	Qualifier: Settlement Date/Time
	Date: Jul 15, 2016
90A: Price
	Qualifier: Deal Price
	Percentage Type Code: PRCT
	Price: 99
22H: Indicator
	Qualifier: Buy/Sell Indicator
	Code: BUYI
22H: Indicator
	Qualifier: Payment Indicator
	Code: APMT

C1 - Confirmation Parties
95P: Party
	Qualifier: Seller
	BIC: BOJPJPJTMF1 - BANK OF JAPAN - (FOR JAPAN'S RESERVES USE ONLY) (JAPAN)
97A: Account
	Qualifier: Safekeeping Account
	Account: MGT10

C1 - Confirmation Parties
95P: Party
	Qualifier: Buyer
	BIC: RCMCUS31XXX - RBC CAPITAL MARKETS, LLC (UNITED STATES OF AMERICA)
36B: Quantity of Financial Instrument
	Qualifier: Quantity of Financial Instrument Confirmed
	Quantity Type Code: FAMT
	Quantity: 15,000,000
36B: Quantity of Financial Instrument
	Qualifier: Quantity of Financial Instrument Ordered
	Quantity Type Code: FAMT
	Quantity: 640,843.05
35B: Identification of the Financial Instrument
	Qualifier: ISIN
	ISIN: US99991UUT05
	Description: FN 999999
	Description 2: 5
	Description 3: 2033/01/01

D - Settlement Details
22F: Indicator
	Qualifier: Type of Settlement Transaction Indicator
	Indicator: TRAD

D1 - Settlement Parties
95P: Party
	Qualifier: Delivering Agent
	BIC: CHASUS33XXX - JPMORGAN CHASE BANK, N.A. (UNITED STATES OF AMERICA)

D1 - Settlement Parties
95P: Party
	Qualifier: Deliverer's Custodian
	BIC: CHASJPJTXXX - JPMORGAN CHASE BANK, N.A. - (TOKYO BRANCH) (JAPAN)

D1 - Settlement Parties
95P: Party
	Qualifier: Seller
	BIC: BOJPJPJTMF1 - BANK OF JAPAN - (FOR JAPAN'S RESERVES USE ONLY) (JAPAN)

D3 - Amounts
19A: Amount
	Qualifier: Settlement Amount
	Currency: USD
	Amount: 234,567

D3 - Amounts
19A: Amount
	Qualifier: Trade Amount
	Currency: USD
	Amount: 123,456

D3 - Amounts
19A: Amount
	Qualifier: Accrued Interest Amount
	Currency: USD
	Amount: 1,246.5

------------------------- Message Trailer -----------------------------------------
CHK: 3916EF336FF7
 * </pre> 
 * 
 * @author www.prowidesoftware.com
 * @since 7.8.4
 */
public class ExpandedPrintoutExample {

	public static void main(String[] args) throws IOException {
		//System.out.println(expanded(mt103.getSwiftMessage()));
		System.out.println(expanded(mt515.getSwiftMessage()));
		//System.out.println(expanded(mt940.getSwiftMessage()));
		//System.out.println(expanded(mt202.getSwiftMessage()));
		//System.out.println(expanded(mt518.getSwiftMessage()));
		//System.out.println(expanded(mt535.getSwiftMessage()));
		//System.out.println(expanded(mt671.getSwiftMessage()));
		//files(mt103, mt515, mt940, mt202, mt518, mt535, mt671);
	}
		
	public static void files(AbstractMT ... mts) throws IOException {
		for (AbstractMT mt : mts) {
			File f = new File("/tmp/mt"+mt.getMessageType()+".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		    writer.write(expanded(mt.getSwiftMessage()));
		    writer.close();
		}
	}
	
	/**
	 * Takes any message as parameter and returns a formatted printout.
	 * @param msg SwiftMessage can be loaded from any source using specific MTnnn classes or the generic SwiftParser
	 * @return formatted print out
	 */
	private static String expanded(final SwiftMessage msg) {
		/*
		 * create directory instance (only available in Prowide Integrator)
		 */
		BICDirectory repo = new BICDirectory();
		
		/*
		 * create instances of MTinfo and Scheme (only available in Prowide Integrator)
		 */
		Locale locale = Locale.getDefault();
		MTInfo info = new MTInfo(locale, msg);
		Scheme scheme = MtStandard.SRU2016.type(msg.getMtId()).scheme();
		
		/*
		 * Create builder for output
		 */
		StringBuilder out = new StringBuilder();
		
		out.append("------------------------- Instance Type and Transmission -------------------------").append("\n");
		
		if (msg.isIncoming()) {
			out.append("Copy received from SWIFT").append("\n");
		} else if (msg.isOutgoing()) {
			out.append("Copy sent to SWIFT").append("\n");
		}
		
		if (msg.getBlock2().getMessagePriorityType() != null) {
			out.append("Priority/Delivery : ");
			out.append(msg.getBlock2().getMessagePriorityType().getLabel());
			if (msg.isOutgoing()) {
				SwiftBlock2Input b2i = (SwiftBlock2Input) msg.getBlock2();
				if (b2i.getDeliveryMonitoringType() != null) {
					out.append("/").append(b2i.getDeliveryMonitoringType().getLabel());
				}
			}
			out.append("\n");
		}
		
		if (msg.isIncoming()) {
			out.append("Message Input Reference : ").append(msg.getMIR()).append("\n");;
		}
		
		out.append("\n------------------------- Message Header -----------------------------------------").append("\n");
		out.append("Swift    : ").append(StringUtils.replace(msg.getMtId().id(), "fin.", "FIN "));
		final String name = info.mtName();
		if (name != null) {
			out.append(" ").append(name);
		}
		out.append("\n");
		
		/*
		 * expand sender
		 */
		out.append("Sender   : ").append(msg.getSender());
		final String expandedSender = expandLT(repo, msg.getSender());
		if (expandedSender != null) {
			out.append(" - ").append(expandedSender);
		}
		out.append("\n");
		
		/*
		 * expand receiver
		 */
		out.append("Receiver : ").append(msg.getReceiver());
		final String expandedReceiver = expandLT(repo, msg.getReceiver());
		if (expandedReceiver != null) {
			out.append(" - ").append(expandedReceiver);
		}
		out.append("\n");
		
		if (msg.getMUR() != null) {
			out.append("MUR      : ").append(msg.getMUR()).append("\n");
		}
		
		out.append("\n------------------------- Message Text -------------------------------------------").append("\n");
				
		boolean inSequence = false;
		String currentSequence = null;
		/*
		 * iterate message fields detecting sequences start/end
		 */
		for (Tag tag : msg.getBlock4().getTags()) {
			if ("16R".equals(tag.getName())) {
				/*
				 * start sequence
				 */
				SchemeSequence seq = scheme.getSequenceBy16RQualifier(tag.getValue());
				currentSequence = seq.getName();
				inSequence = true;
				/*
				 * print sequence name and description
				 */
				out.append("\n").append(currentSequence).append(" - ").append(info.sequenceName(currentSequence)).append("\n");
			} else if ("16S".equals(tag.getName())) {
				/*
				 * close current sequence
				 */
				currentSequence = null;
			} else if (tag.getNumber() == 15) {
				currentSequence = tag.getLetterOption();
				if (inSequence) {
					/*
					 * close current sequence
					 */
				}
				inSequence = true;
				/*
				 * start sequence
				 * print sequence name and description
				 */
				out.append("\n").append(currentSequence).append(" - ").append(info.sequenceName(tag.getLetterOption())).append("\n");
			} else {
				/*
				 * print field components
				 */
				String fieldLabel = info.fieldName(tag.getName(), currentSequence);
				Field f = tag.asField();
				out.append(f.getName()).append(": ");
				if (f.getName().length() < 3) {
					out.append(" ");
				}
				out.append(fieldLabel).append("\n");
				for (int component=1; component<=f.getComponents().size(); component++) {
					final String value = f.getComponent(component);
					if (value != null) {
						final String label = f.getComponentLabel(component);
						out.append("\t").append(label).append(": ");
						if (StringUtils.equals("BIC", label)) {
							/*
							 * if component is a BIC, expand the institution data
							 */
							out.append(value);
							final String expandedBICInfo = expandBIC(repo, value.trim());
							if (expandedBICInfo != null) {
								out.append(" - ").append(expandedBICInfo);
							}
						} else {
							String s = null;
							if (component == 1 && f instanceof GenericField) {
								/*
								 * for generic fields, expand the qualifier
								 * (this could also be used as more specific field label)
								 */
								s = info.qualifierName(f.getName(), value, currentSequence);
							}
							if (s == null) {
								/*
								 * try to use formatted output from field
								 */
								s = f.getValueDisplay(component, locale);
							}
							if (s == null) {
								/*
								 * default with component value as is
								 */
								s = value;
							}
							out.append(s);
						}
						out.append("\n");
					}
				}
			}
		}
		
		if (msg.getBlock5() != null) {
			out.append("\n------------------------- Message Trailer -----------------------------------------\n");
			for (Tag t : msg.getBlock5().getTags()) {
				out.append(t.getName()).append(": ").append(StringUtils.trimToEmpty(t.getValue())).append("\n");
			}
		}
		
		return out.toString();
	}

	/**
	 * extracts the BIC from a logical terminal address from message header and 
	 * calls the expansion method to get the institution name
	 * @param repo BIC directory catalog
	 * @param lt a logical terminal address from message header
	 * @return expanded info or null if BIC is not found in the repository
	 */
	private static String expandLT(final BICDirectory repo, final String lt) {
		LogicalTerminalAddress address = new LogicalTerminalAddress(lt);
		return expandBIC(repo, address.getBic11());
	}
	
	/**
	 * Searches the parameter BIC in the repository, and if found,
	 * returns a string with the institution name and, if present,
	 * branch information and country
	 * @param repo BIC directory catalog
	 * @param bic a BIC 8 or BIC 11 from message header or field BIC components
	 * @return expanded info or null if BIC is not found in the repository
	 */
	private static String expandBIC(final BICDirectory repo, final String bic) {
		List<BICRecord> found = repo.query(bic, true);
		if (!found.isEmpty()) {
			if (found.size() > 1) {
				//log warning, expected one and found n
			}
			BICRecord br = found.get(0);
			final String name = StringUtils.trimToNull(br.getInstitutionName());
			if (name != null) {
				StringBuilder result = new StringBuilder();
				result.append(name);
				if (br.getBranchInformation() != null) {
					result.append(" - ").append(br.getBranchInformation());
				}
				if (br.getCountryName() != null) {
					result.append(" (").append(br.getCountryName()).append(")");
				}
				return result.toString();
			}
		} else {
			//log warning not found
		}
		return null;
	}
	
	/*
	 * sample data
	 */
	static MT103 mt103 = MT103.parse("{1:F01BACOARB1A0B20000000000}{2:I103ADRBNL21XXXXN}{3:{108:FOOB3926BE868XXX}}{4:\n" +
			":20:REFERENCE\n" + 
			":23B:CRED\n" + 
			":32A:130204USD1234567,89\n" + 
			":50A:/12345678901234567890\n" + 
			"CFIMHKH1XXX\n" + 
			":59:/12345678901234567890\n" + 
			"JOE DOE\n" + 
			":71A:OUR\n" + 
			"-}{5:{CHK:3916EF336FF7}}");
	
	static MT515 mt515 = MT515.parse("{1:F01RBDSJPJTBXXX0000000000}{2:I515BOJPJPJTXMF1N}{3:{108:FOOB3926BE868XXX}}{4:\n" +
			":16R:GENL\n" +
			":20C::SEME//M1999999.1\n" +
			":23G:NEWM\n" +
			":22F::TRTR//TRAD\n" +
			":16R:LINK\n" +
			":20C::PREV//M1999999.1\n" +
			":16S:LINK\n" +
			":16S:GENL\n" +
			":16R:CONFDET\n" +
			":98A::TRAD//20160712\n" +
			":98A::SETT//20160715\n" +
			":90A::DEAL//PRCT/99,00000000\n" +
			":22H::BUSE//BUYI\n" +
			":22H::PAYM//APMT\n" +
			":16R:CONFPRTY\n" +
			":95P::SELL//BOJPJPJTMF1\n" +
			":97A::SAFE//MGT10\n" +
			":16S:CONFPRTY\n" +
			":16R:CONFPRTY\n" +
			":95P::BUYR//RCMCUS31XXX\n" +
			":16S:CONFPRTY\n" +
			":36B::CONF//FAMT/15000000,\n" +
			":36B::ORDR//FAMT/640843,05\n" +
			":35B:ISIN US99991UUT05\n" +
			"FN 999999\n" +
			"5\n" +
			"2033/01/01\n" +
			":16S:CONFDET\n" +
			":16R:SETDET\n" +
			":22F::SETR//TRAD\n" +
			":16R:SETPRTY\n" +
			":95P::DEAG//CHASUS33XXX\n" +
			":16S:SETPRTY\n" +
			":16R:SETPRTY\n" +
			":95P::DECU//CHASJPJTXXX\n" +
			":16S:SETPRTY\n" +
			":16R:SETPRTY\n" +
			":95P::SELL//BOJPJPJTMF1\n" +
			":16S:SETPRTY\n" +
			":16R:AMT\n" +
			":19A::SETT//USD234567,00\n" +
			":16S:AMT\n" +
			":16R:AMT\n" +
			":19A::DEAL//USD123456,00\n" +
			":16S:AMT\n" +
			":16R:AMT\n" +
			":19A::ACRU//USD1246,50\n" +
			":16S:AMT\n" +
			":16S:SETDET\n" +
			"-}{5:{CHK:3916EF336FF7}}");
	
	static MT940 mt940 = MT940.parse("{1:F01BACOARB1A0B20000000000}{2:I940BSCHGB2LXEQUN}{108:FOOB3926BE868XXX}{4:\n" +
            ":20:123456\n"+
			":25:123-304958\n"+
			":28C:123/1\n"+
			":60F:C980622USD395212311,71\n"+
			":61:980623C50000000,NTRFNONREF//8951234\n"+
			"ORDER BK OF NYC WESTERN CASH RESERVE\n"+
			":61:980625C5700000,NFEX036960//8954321\n"+
			":61:980626C200000,NDIVNONREF//8846543\n"+
			":86:DIVIDEND LORAL CORP\n"+
			"PREFERRED STOCK 1ST QUARTER 1998\n"+
			":62F:C980623USD451112311,71\n"+
			":64:C980623USD445212311,71\n"+
			":65:C980625USD450912311,71\n"+
			":65:C980626USD451112311,71\n"+
			":86:PRIME RATE AS OF TODAY 11 PCT\n"+ 
            "-}{5:{CHK:3916EF336FF7}}");
	
	static MT202 mt202 = MT202.parse("{1:F01BICFOOYYAXXX8628453424}{2:O2021300050901IRVTLULXALTA06556102830509011300N}{108:FOOB3926BE868XXX}{4:\n" + 
			":20:RFSAMPPGN0031091\n" + 
			":21:RFSAMPPGN0031091\n" + 
			":13C:/RNCTIME/1356+0000\n" +
			":13C:/RNCTIME/1410+0000\n" +
			":32A:050901EUR19265,53\n" + 
			":52A:IRVTLULXLTA\n" + 
			":53A:/D/1234A0123456ABC012345\n" + 
			"BICFOOYY\n" + 
			":54A:BAPPIT21AP8\n" + 
			":56A:BMISLBB1025\n" + 
			":57A:HLFXGB21L17\n" + 
			":58A:/ES12 1234 6789 1234 1111 1234\n" + 
			"SEICUS33CAI\n" + 
			":72:/BNF/00002695 0001 2005083130110\n" + 
			"-}{5:{CHK:3916EF336FF7}}");
	
	static MT518 mt518 = MT518.parse("{1:F01ANASCH20AXXX0000000000}{2:O5180750040609LRLRXXXX4A0400004386330406090954U}{4:\n" +
			":16R:GENL\n"+
			":20C::SEME//900031\n"+
			":23G:NEWM\n"+
			":98C::PREP//19991207130605\n"+
			":22F::TRTR/GSCC/CASH\n"+
			":16S:GENL\n"+
			":16R:CONFDET\n"+
			":98C::TRAD//19991207130433\n"+
			":98A::SETT//19991208\n"+
			":90A::DEAL//PRCT/99,375\n"+
			":19A::SETT//USD14961933,38\n"+
			":22H::BUSE//BUYI\n"+
			":22F::PROC/GSCC/CMPR\n"+
			":22H::PAYM//APMT\n"+
			":16R:CONFPRTY\n"+
			":95R::BUYR/GSCC/PART8520\n"+
			":16S:CONFPRTY\n"+
			":16R:CONFPRTY\n"+
			":95R::SELL/GSCC/PART8535\n"+
			":20C::PROC//1999120701776\n"+
			":70E::DECL//GSCC/CORRSIMU\n"+
			":16S:CONFPRTY\n"+
			":36B::CONF//FAMT/15000000,\n"+
			":35B:/US/9128275S7\n"+
			":16S:CONFDET\n"+
			"-}{5:{MAC:307F606D}{CHK:521A0E3826D3}{TNG:}}");
	
	static MT535 mt535 = MT535.parse("{1:F01BBHCUS3IADNC0000000000}{2:O5350837080313CHASGB2LIXXX06988488300803130437N}{3:{108:0001234Q1651234}}{4:\n"+
			":16R:GENL\n"+
			":28E:6/MORE\n"+
			":20C::SEME//H200803121138571\n"+
			":23G:NEWM\n"+
			":98A::STAT//20080312\n"+
			":22F::SFRE//DAIL\n"+
			":22F::CODE//COMP\n"+
			":22F::STTY//CUST\n"+
			":22F::STBA//TRAD\n"+
			":97A::SAFE//S 02500\n"+
			":17B::ACTI//Y\n"+
			":17B::AUDT//N\n"+
			":17B::CONS//N\n"+
			":16S:GENL\n"+
			":16R:SUBSAFE\n"+
			":16R:FIN\n"+
			":35B:/US/AGGRAVAI\n"+
			"AGGR=300, AVAI=200\n"+
			":16R:FIA\n"+
			":12A::CLAS/ISIT/STF\n"+
			":16S:FIA\n"+
			":93B::AGGR//FAMT/300,\n"+
			":93B::AVAI//FAMT/200,\n"+
			":16R:SUBBAL\n"+
			":93B::AGGR//FAMT/50,\n"+
			":94F::SAFE//CUST/CHASUS33\n"+
			":70C::SUBB//REGISTRATION CODE FOOBAR\n"+
			":16S:SUBBAL\n"+
			":16S:FIN\n"+
			":16R:FIN\n"+
			"-}{5:{MAC:307F606D}{CHK:521A0E3826D3}}");
	
	static MT671 mt671 = MT671.parse("{1:F01OTPVHUH0AXXX1000000000}{2:O6711639140102CITIGB20DSWP08469391731401021739N}{3:{108:CITI41847}}{4:\n" +
			":16R:GENL\n" +
			":20C::SEME//ICF2750609140005\n" +
			":23G:NEWM\n" +
			":95P::SUBM//20060913\n" +
			":22F::UDTP//CASH\n" +
			":97A::SAFE//F275\n" +
			":16S:GENL\n" +
			":16R:SSIDET\n" +
			":22H::SSIP//NEWS\n" +
			":11A::SETT//USD\n" +
			":98A::EFFD//20150827\n" +
			":22F::MARK//CASH\n" +
			":16R:CSHPRTY\n" +
			":95P::BENM//IRVTLUL1LTA\n" +
			":16S:CSHPRTY\n" +
			":16S:SSIDET\n" +
			"-}{5:{MAC:307F606D}{CHK:521A0E3826D3}}");
}
