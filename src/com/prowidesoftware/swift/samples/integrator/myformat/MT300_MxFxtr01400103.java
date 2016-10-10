/*******************************************************************************
 * Copyright (c) 2016 Prowide Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of private license agreements
 * between Prowide Inc. and its commercial customers and partners.
 *******************************************************************************/
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.mt3xx.MT300;
import com.prowidesoftware.swift.model.mx.MxFxtr01400103;
import com.prowidesoftware.swift.myformat.translations.MT300_MxFxtr01400103_Translation;

/**
 * Basic example using out-of-the-box translation implementation from MT300 to MxFxtr01400103
 * 
 * @author sebastian
 * @since 7.8.5
 */
public class MT300_MxFxtr01400103 {
	
	public static void main(String[] args) {
		
		MT300 mt = MT300.parse("{1:F01TESTUSAAAXXX1009000001}{2:I300TESTUSBBXXXXN}{4:\n" +
				":15A:\n" +
				":20:MYREF\n" +
				":21:RELREF\n" +
				":22A:NEWT\n" +
				":94A:BROK\n" +
				":22C:TESTAA2321TESTBB\n" +
				":17T:Y\n" +
				":17U:N\n" +
				":17I:Y\n" +
				":82A:/D/123423423423\n" +
				"CITIVNVXHCM\n" +
				":87D:/2342342342\n" +
				"FOO CORP\n" +
				"3398 Fire Access Road\n" +
				":83J:/NAME/FUNDNAME01\n" +
				"/ACCT/CCITIBEBXXXX\n" +
				"/LEIC/LEGALENTITYIDENTIF99\n" +
				":77H:OTHER/20161020//3221\n" +
				":77D:Hello World\n" +
				"second line\n" +
				":14C:2016\n" +
				":15B:\n" +
				":30T:20161029\n" +
				":30V:20161030\n" +
				":36:1,234567\n" +
				":32B:USD1423412323,\n" +
				":53A:/252352354234\n" +
				"ITAUKYKCXXX\n" +
				":56D:/2423423\n" +
				"Fleming\n" +
				"12 Street 00121\n" +
				"Maryland\n" +
				":57J:/ABIC/UKWN/NAME/BANK1/ACCT/O-\n" +
				"XYZ23456\n" +
				":33B:ARS1500000,\n" +
				":53J:/ABIC/UKWN/NAME/FOO INTERNATIONAL SA\n" +
				":56A:/D/22423423423\n" +
				"ICRAITRRTE0\n" +
				":57D:/234523452345\n" +
				"PW99 EEEEE\n" +
				"437 Lilac Lane\n" +
				"Vidalia\n" +
				":58J:/ABIC/INSCARB1XXX/NAME/BANK2/ACCT/O-\n" +
				"XYZ23456/LEIC/CLSBNKFEDBKNEWYORK37\n" +
				":15C:\n" +
				":29A:Joe Doe\n" +
				"12312322\n" +
				":24D:PHON/234212423423\n" +
				":84B:/234234234\n" +
				"Buenos Aires\n" +
				":85J:/NAME/FOO INTERNATIONAL SA/ABIC/UKWN\n" +
				":88A:SEPBIRTH677\n" +
				":71F:USD256,23\n" +
				":26H:FOOREF232\n" +
				":21G:OTHRREF2222\n" +
				":72:/ACC/GTMS\n" +
				"//L1710833-1-1\n" +
				":15E:\n" +
				":22L:New York\n" +
				":91D:/23423452352345234\n" +
				"Geargia Marlow\n" +
				"3300 Randall Drive\n" +
				":22M:UUYET762829\n" +
				":22N:12000000AS888\n" +
				":22P:ER333333344\n" +
				":22R:QQQQQOOOUU777\n" +
				":81A:HSAABE21XXX\n" +
				":89D:EEEEEE 999\n" +
				":96A:/23424523423\n" +
				"ITAUKYKYXXX\n" +
				":22S:C/BROKER 76 WW\n" +
				":22T:FOO 2323\n" +
				":17E:Y\n" +
				":22U:FXNDFO\n" +
				":17H:P\n" +
				":17P:U\n" +
				":22V:HHHHH  888\n" +
				":98D:20161019101112\n" +
				":17W:Y\n" +
				":22W:ABCD123456\n" +
				":17Y:N\n" +
				":17Z:Y\n" +
				":22Q:DDSSS\n" +
				":17L:Y\n" +
				":17M:F\n" +
				":17Q:Y\n" +
				":17S:Y\n" +
				":17X:Y\n" +
				":98G:20161027121212\n" +
				":77A:AAAA 7778FOO\n" +
				"FOOOO 1888\n" +
				"-}");
		 
		/*
		 * Initialize specific out-of-the-box translation
		 */
		final MT300_MxFxtr01400103_Translation translation = new MT300_MxFxtr01400103_Translation();

		/*
		 * call translation on source MT300
		 */
		final MxFxtr01400103 mx = translation.translate(mt);

		/*
		 * print created MX
		 */
		System.out.println(mx.message("message"));
	}
	
	/* Created MX message:
 
<?xml version="1.0" encoding="UTF-8" ?>
<message>

 <h:AppHdr xmlns:h="urn:iso:std:iso:20022:tech:xsd:head.001.001.01" xmlns:xsi="{http://www.w3.org/2000/xmlns/}ns2">
  <h:Fr>
   <h:FIId>
    <h:FinInstnId>
     <h:BICFI>TESTUSAAXXX</h:BICFI>
    </h:FinInstnId>
   </h:FIId>
  </h:Fr>
 
  <h:To>
   <h:FIId>
    <h:FinInstnId>
     <h:BICFI>TESTUSBBXXX</h:BICFI>
    </h:FinInstnId>
   </h:FIId>
  </h:To>
 
  <h:BizMsgIdr>MYREF</h:BizMsgIdr>
 
  <h:MsgDefIdr>fxtr.014.001.03</h:MsgDefIdr>
 
  <h:CreDt>2016-10-10T18:23:52Z</h:CreDt>
 </h:AppHdr>


 <Doc:Document xmlns:Doc="urn:iso:std:iso:20022:tech:xsd:fxtr.014.001.03" xmlns:xsi="{http://www.w3.org/2000/xmlns/}Doc">
  <Doc:FXTradInstr>
   <Doc:TradInf>
    <Doc:TradDt>2016-10-29</Doc:TradDt>
   
    <Doc:OrgtrRef>MYREF</Doc:OrgtrRef>
   
    <Doc:CmonRef>TESTAA2321TESTBB</Doc:CmonRef>
   
    <Doc:OprTp>NEWT</Doc:OprTp>
   
    <Doc:OprScp>BROK</Doc:OprScp>
   
    <Doc:PmtVrssPmtInd>true</Doc:PmtVrssPmtInd>
   </Doc:TradInf>
  
   <Doc:TradgSdId>
    <Doc:SubmitgPty>
     <Doc:AnyBIC>
      <Doc:AnyBIC>CITIVNVXHCM</Doc:AnyBIC>
     </Doc:AnyBIC>
    </Doc:SubmitgPty>
   
    <Doc:TradPty>
     <Doc:AnyBIC>
      <Doc:AnyBIC>CITIVNVXHCM</Doc:AnyBIC>
     </Doc:AnyBIC>
    </Doc:TradPty>
   </Doc:TradgSdId>
  
   <Doc:CtrPtySdId>
    <Doc:SubmitgPty>
     <Doc:NmAndAdr>
      <Doc:Nm>FOO CORP
3398 Fire Access Road</Doc:Nm>
     </Doc:NmAndAdr>
    </Doc:SubmitgPty>
   
    <Doc:TradPty>
     <Doc:NmAndAdr>
      <Doc:Nm>FOO CORP
3398 Fire Access Road</Doc:Nm>
     </Doc:NmAndAdr>
    </Doc:TradPty>
   </Doc:CtrPtySdId>
  
   <Doc:TradAmts>
    <Doc:TradgSdBuyAmt Ccy="USD" >1423412323.0</Doc:TradgSdBuyAmt>
   
    <Doc:TradgSdSellAmt Ccy="ARS" >1500000.0</Doc:TradgSdSellAmt>
   
    <Doc:SttlmDt>2016-10-30</Doc:SttlmDt>
   </Doc:TradAmts>
  
   <Doc:AgrdRate>
    <Doc:XchgRate>1.234567</Doc:XchgRate>
   </Doc:AgrdRate>
  
   <Doc:TradgSdSttlmInstrs>
    <Doc:DlvryAgt>
     <Doc:AnyBIC>
      <Doc:AnyBIC>ITAUKYKCXXX</Doc:AnyBIC>
     </Doc:AnyBIC>
    </Doc:DlvryAgt>
   
    <Doc:Intrmy>
     <Doc:NmAndAdr>
      <Doc:Nm>Fleming
12 Street 00121
Maryland</Doc:Nm>
     </Doc:NmAndAdr>
    </Doc:Intrmy>
   
    <Doc:RcvgAgt>
     <Doc:PtyId>
      <Doc:PtyNm>BANK1</Doc:PtyNm>
     
      <Doc:AcctNb>O-
XYZ23456</Doc:AcctNb>
     </Doc:PtyId>
    </Doc:RcvgAgt>
   </Doc:TradgSdSttlmInstrs>
  
   <Doc:CtrPtySdSttlmInstrs>
    <Doc:DlvryAgt>
     <Doc:PtyId>
      <Doc:PtyNm>FOO INTERNATIONAL SA</Doc:PtyNm>
     </Doc:PtyId>
    </Doc:DlvryAgt>
   
    <Doc:Intrmy>
     <Doc:AnyBIC>
      <Doc:AnyBIC>ICRAITRRTE0</Doc:AnyBIC>
     </Doc:AnyBIC>
    </Doc:Intrmy>
   
    <Doc:RcvgAgt>
     <Doc:NmAndAdr>
      <Doc:Nm>PW99 EEEEE
437 Lilac Lane
Vidalia</Doc:Nm>
     </Doc:NmAndAdr>
    </Doc:RcvgAgt>
   
    <Doc:BnfcryInstn>
     <Doc:PtyId>
      <Doc:PtyNm>BANK2</Doc:PtyNm>
     
      <Doc:AnyBIC>
       <Doc:AnyBIC>INSCARB1XXX</Doc:AnyBIC>
      </Doc:AnyBIC>
     
      <Doc:AcctNb>O-
XYZ23456</Doc:AcctNb>
     
      <Doc:LglNttyIdr>CLSBNKFEDBKNEWYORK37</Doc:LglNttyIdr>
     </Doc:PtyId>
    </Doc:BnfcryInstn>
   </Doc:CtrPtySdSttlmInstrs>
  
   <Doc:OptnlGnlInf>
    <Doc:DealgMtd>PHON</Doc:DealgMtd>
   
    <Doc:BrkrId>
     <Doc:AnyBIC>
      <Doc:AnyBIC>SEPBIRTH677</Doc:AnyBIC>
     </Doc:AnyBIC>
    </Doc:BrkrId>
   
    <Doc:CtrPtyRef>FOOREF232</Doc:CtrPtyRef>
   
    <Doc:BrkrsComssn Ccy="USD" >256.23</Doc:BrkrsComssn>
   
    <Doc:SndrToRcvrInf>/ACC/GTMS
//L1710833-1-1</Doc:SndrToRcvrInf>
   
    <Doc:DealgBrnchTradgSd>
     <Doc:NmAndAdr>
      <Doc:Nm>Buenos Aires</Doc:Nm>
     </Doc:NmAndAdr>
    </Doc:DealgBrnchTradgSd>
   
    <Doc:DealgBrnchCtrPtySd>
     <Doc:PtyId>
      <Doc:PtyNm>FOO INTERNATIONAL SA</Doc:PtyNm>
     </Doc:PtyId>
    </Doc:DealgBrnchCtrPtySd>
   
    <Doc:CtctInf>
     <Doc:Nm>Joe Doe
12312322</Doc:Nm>
    </Doc:CtctInf>
   </Doc:OptnlGnlInf>
  
   <Doc:RgltryRptg>
    <Doc:TradgSdTxRptg>
     <Doc:RptgJursdctn>New York</Doc:RptgJursdctn>
    
     <Doc:RptgPty>
      <Doc:NmAndAdr>
       <Doc:Nm>Geargia Marlow
3300 Randall Drive</Doc:Nm>
      </Doc:NmAndAdr>
     </Doc:RptgPty>
    
     <Doc:TradgSdUnqTxIdr>
      <Doc:UnqTxIdr>UUYET76282912000000AS888</Doc:UnqTxIdr>
     
      <Doc:PrrUnqTxIdr>ER333333344QQQQQOOOUU777</Doc:PrrUnqTxIdr>
     </Doc:TradgSdUnqTxIdr>
    </Doc:TradgSdTxRptg>
   
    <Doc:CtrPtySdTxRptg>
     <Doc:CtrPtySdUnqTxIdr>
      <Doc:UnqTxIdr>/ACC/GTMS
//L1710833-1-1</Doc:UnqTxIdr>
     </Doc:CtrPtySdUnqTxIdr>
    </Doc:CtrPtySdTxRptg>
   
    <Doc:CntrlCtrPtyClrHs>
     <Doc:AnyBIC>
      <Doc:AnyBIC>HSAABE21XXX</Doc:AnyBIC>
     </Doc:AnyBIC>
    </Doc:CntrlCtrPtyClrHs>
   
    <Doc:ClrBrkr>
     <Doc:NmAndAdr>
      <Doc:Nm>EEEEEE 999</Doc:Nm>
     </Doc:NmAndAdr>
    </Doc:ClrBrkr>
   
    <Doc:ClrXcptnPty>
     <Doc:AnyBIC>
      <Doc:AnyBIC>ITAUKYKYXXX</Doc:AnyBIC>
     </Doc:AnyBIC>
    </Doc:ClrXcptnPty>
   
    <Doc:ClrBrkrId>
     <Doc:SdInd>CLNT</Doc:SdInd>
    
     <Doc:ClrBrkrId>BROKER 76 WW</Doc:ClrBrkrId>
    </Doc:ClrBrkrId>
   
    <Doc:ClrThrshldInd>true</Doc:ClrThrshldInd>
   
    <Doc:ClrdPdctId>FOO 2323</Doc:ClrdPdctId>
   
    <Doc:UndrlygPdctIdr>NDFO</Doc:UndrlygPdctIdr>
   
    <Doc:AllcnInd>PREA</Doc:AllcnInd>
   
    <Doc:CollstnInd>UNCO</Doc:CollstnInd>
   
    <Doc:ExctnVn>HHHHH  888</Doc:ExctnVn>
   
    <Doc:ExctnTmstmp>
     <Doc:DtTm>2016-10-19T10:11:12</Doc:DtTm>
    </Doc:ExctnTmstmp>
   
    <Doc:NonStdFlg>true</Doc:NonStdFlg>
   
    <Doc:LkSwpId>00000000000000000000000000000000ABCD123456</Doc:LkSwpId>
   
    <Doc:FinNtrOfTheCtrPtyInd>false</Doc:FinNtrOfTheCtrPtyInd>
   
    <Doc:CollPrtflInd>true</Doc:CollPrtflInd>
   
    <Doc:CollPrtflCd>DDSSS</Doc:CollPrtflCd>
   
    <Doc:PrtflCmprssnInd>true</Doc:PrtflCmprssnInd>
   
    <Doc:CorpSctrInd>F</Doc:CorpSctrInd>
   
    <Doc:TradWthNonEEACtrPtyInd>true</Doc:TradWthNonEEACtrPtyInd>
   
    <Doc:NtrgrpTradInd>true</Doc:NtrgrpTradInd>
   
    <Doc:ComrclOrTrsrFincgInd>true</Doc:ComrclOrTrsrFincgInd>
   
    <Doc:AddtlRptgInf>AAAA 7778FOO
FOOOO 1888</Doc:AddtlRptgInf>
   </Doc:RgltryRptg>
  </Doc:FXTradInstr>
 </Doc:Document>

</message>
	 */
}
