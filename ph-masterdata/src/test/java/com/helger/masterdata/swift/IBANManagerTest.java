/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.masterdata.swift;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Month;
import java.util.Locale;

import org.junit.Test;

import com.helger.base.string.StringParser;
import com.helger.datetime.helper.PDTFactory;

/**
 * Test class for class {@link IBANManager}.
 *
 * @author Philip Helger
 */
public final class IBANManagerTest
{
  static final String [] VALID_IBANS = { "DE89 3704 0044 0532 0130 00",
                                         "FR14 2004 1010 0505 0001 3M02 606",
                                         "GB29 NWBK 6016 1331 9268 19",
                                         "GR16 0110 1250 0000 0001 2300 695",
                                         "MT84 MALT 0110 0001 2345 MTLC AST0 01S",
                                         "GR16 0110 1050 0000 1054 7023 795",
                                         "GB35 MIDL 4025 3432 1446 70",
                                         "CH51 0868 6001 2565 1500 1",
                                         "AD1200012030200359100100",
                                         "AE070331234567890123456",
                                         "AL47212110090000000235698741",
                                         "AT611904300234573201",
                                         "AZ21NABZ00000000137010001944",
                                         "BA391290079401028494",
                                         "BE68539007547034",
                                         "BF6712345678901234567890",
                                         "BG80BNBG96611020345678",
                                         "BH67BMAG00001299123456",
                                         "BI8420433218196001338908386",
                                         "BR9700360305000010009795493P1",
                                         "BY79O2RA2654JNVGFYGWWQC38HYF",
                                         "CH9300762011623852957",
                                         "CR05015202001026284066",
                                         "CS73505000012345678951",
                                         "CY17002001280000001200527600",
                                         "CZ6508000000192000145399",
                                         "DE89370400440532013000",
                                         "DJ6894959310341316475255341",
                                         "DK5000400440116243",
                                         "DO28BAGR00000001212453611324",
                                         "EE382200221020145685",
                                         "EG753832764835030564139537672",
                                         "ES9121000418450200051332",
                                         "FI2112345600000785",
                                         "FK38IE388496965328",
                                         "FO6264600001631634",
                                         "FR1420041010050500013M02606",
                                         "GB29NWBK60161331926819",
                                         "GE29NB0000000101904917",
                                         "GI75NWBK000000007099453",
                                         "GL8964710001000206",
                                         "GR1601101250000000012300695",
                                         "GT82TRAJ01020000001210029690",
                                         "HN63PCYB12269166978480184514",
                                         "HR1210010051863000160",
                                         "HU42117730161111101800000000",
                                         "IE29AIBK93115212345678",
                                         "IL620108000000099999999",
                                         "IQ46NFOA482814893252880",
                                         "IS140159260076545510730339",
                                         "IT60X0542811101000000123456",
                                         "JO12TKPA1543DPFF5E8II49KQ71N8M",
                                         "KW81CBKU0000000000001234560101",
                                         "KZ86125KZT5004100100",
                                         "LB62099900000001001901229114",
                                         "LC39WJMVX272HPOEVB9OOAEDOECVE6PR",
                                         "LI21088100002324013AA",
                                         "LT121000011101001000",
                                         "LU280019400644750000",
                                         "LV80BANK0000435195001",
                                         "LY28838299737631165667010",
                                         "MC5811222000010123456789030",
                                         "MD24AG000225100013104168",
                                         "ME25505000012345678951",
                                         "MK07250120000058984",
                                         "MN047513338726247317",
                                         "MR1300020001010000123456753",
                                         "MT84MALT011000012345MTLCAST001S",
                                         "MU17BOMM0101101030300200000MUR",
                                         "NI16ZRDB80132677360260647468",
                                         "NL91ABNA0417164300",
                                         "NO9386011117947",
                                         "OM24823SND8DUDD467KD6FL",
                                         "PK36SCBL0000001123456702",
                                         "PL61109010140000071219812874",
                                         "PS92PALS000000000400123456702",
                                         "PT50000201231234567890154",
                                         "QA21CTCVPZHPCF07UQNUPQZIT3UEA",
                                         "RO49AAAA1B31007593840000",
                                         "RS35260005601001611379",
                                         "RU7789911838425135SK28T7A9TGIQHG9",
                                         "SA0380000000608010167519",
                                         "SC51EIJT35348740164005242786RAD",
                                         "SD5922805982620450",
                                         "SE4550000000058398257466",
                                         "SI56263300012039086",
                                         "SK3112000000198742637541",
                                         "SM86U0322509800000000270100",
                                         "SO396331586923226025634",
                                         "ST18316073375433036541458",
                                         "SV91MVRK01429401965569816934",
                                         "TL383870831727889579868",
                                         "TN5910006035183598478831",
                                         "TR330006100519786457841326",
                                         "UA39827743R75PR2ESPRVU8FIJOYJ",
                                         "VA02416658760366909670",
                                         "VG96VPVG0000012345678901",
                                         "XK136466889373467065",
                                         "YE45VVZM27298BZBF1I3LDQYUN3UVY" };

  @Test
  public void testIBANCountries ()
  {
    for (final String sCountry : IBANManager.getAllSupportedCountries ())
    {
      final Locale aLocale = new Locale ("", sCountry);
      assertNotNull (aLocale.getDisplayCountry (Locale.getDefault ()));
      assertNotNull (IBANManager.getCountryData (sCountry));
    }
  }

  @Test
  public void testValidity ()
  {
    final IBANCountryData aData = IBANManager.getCountryData ("MR");
    assertNotNull (aData);
    assertEquals (PDTFactory.createLocalDate (2012, Month.JANUARY, 1), aData.getStart ());
    assertNull (aData.getEnd ());
  }

  @Test
  public void testCheckDigits ()
  {
    IBANCountryData aData = IBANManager.getCountryData ("TN");
    assertNotNull (aData);
    assertTrue (aData.hasFixedCheckDigits ());
    assertEquals ("59", aData.getFixedCheckDigits ());

    aData = IBANManager.getCountryData ("AT");
    assertNotNull (aData);
    assertFalse (aData.hasFixedCheckDigits ());
    assertNull (aData.getFixedCheckDigits ());
  }

  @Test
  public void testIBANValidity ()
  {
    // check valid IBANs
    for (final String sIBAN : VALID_IBANS)
      assertTrue (sIBAN, IBANManager.isValidIBAN (sIBAN));

    // check if checksum creation works
    for (final String sIBAN : VALID_IBANS)
      assertEquals (sIBAN + " has other checksum than expected;",
                    StringParser.parseInt (sIBAN.substring (2, 4), -1),
                    IBANManager.createChecksumOfNewIBAN (sIBAN.substring (0, 2), sIBAN.substring (4)));
  }

  @Test
  public void testCreateIBANs ()
  {
    assertEquals ("DE79000000001234567890", IBANManager.createIBANWithValidChecksum ("DE", "000000001234567890"));
    assertTrue (IBANManager.isValidIBAN ("DE79000000001234567890"));

    assertEquals ("DE16000000002345678901", IBANManager.createIBANWithValidChecksum ("DE", "000000002345678901"));
    assertTrue (IBANManager.isValidIBAN ("DE16000000002345678901"));
  }
}
