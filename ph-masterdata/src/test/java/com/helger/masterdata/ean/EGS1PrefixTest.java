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
package com.helger.masterdata.ean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.CommonsHashSet;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsSet;
import com.helger.text.locale.country.CountryCache;

/**
 * Test class for class {@link EGS1Prefix}.
 *
 * @author Philip Helger
 */
public class EGS1PrefixTest
{
  @Test
  public void testBasic ()
  {
    for (final EGS1Prefix e : EGS1Prefix.values ())
    {
      final String sFrom = e.getFrom ();
      final String sTo = e.getTo ();
      final String sDesc = e.getDescription ();
      final ICommonsList <String> aCCs = e.getAllCountryCodes ();

      assertTrue (StringHelper.isNotEmpty (sFrom));
      assertEquals (sFrom.length (), e.getPrefixLength ());
      if (e.hasTo ())
      {
        assertEquals (sFrom.length (), sTo.length ());
        assertTrue (Integer.parseInt (sFrom) < Integer.parseInt (sTo));
      }
      assertTrue (StringHelper.isNotEmpty (sDesc));
      assertTrue (e.hasCountryCode () == aCCs.isNotEmpty ());
      if (e.hasCountryCode ())
      {
        assertEquals (aCCs.getFirstOrNull (), e.getCountryCode ());
        for (final String sCC : aCCs)
        {
          final Locale aC = CountryCache.getInstance ().getCountry (sCC);
          assertNotNull ("No such country '" + sCC + "'", aC);
          assertNotEquals (sCC, aC.getDisplayCountry (Locale.US));
        }
      }
      else
        assertNull (e.getCountryCode ());
    }
  }

  @Test
  public void testIterateAllPrefixes ()
  {
    final ICommonsList <String> aAll = new CommonsArrayList <> ();
    EGS1Prefix.US_1.iterateAllPrefixes (aAll::add);
    assertEquals (9, aAll.size ());
    assertEquals ("00001", aAll.get (0));
    assertEquals ("00002", aAll.get (1));
    assertEquals ("00003", aAll.get (2));
    assertEquals ("00004", aAll.get (3));
    assertEquals ("00005", aAll.get (4));
    assertEquals ("00006", aAll.get (5));
    assertEquals ("00007", aAll.get (6));
    assertEquals ("00008", aAll.get (7));
    assertEquals ("00009", aAll.get (8));

    aAll.clear ();
    EGS1Prefix.BG.iterateAllPrefixes (aAll::add);
    assertEquals (1, aAll.size ());
    assertEquals ("380", aAll.get (0));

    for (final EGS1Prefix e : EGS1Prefix.values ())
    {
      final ICommonsList <String> aList = new CommonsArrayList <> ();
      final ICommonsSet <String> aSet = new CommonsHashSet <> ();
      e.iterateAllPrefixes (aList::add);
      e.iterateAllPrefixes (aSet::add);
      assertFalse (aList.isEmpty ());
      assertEquals (aList.size (), aSet.size ());
    }
  }

  @Test
  public void testNoOverlappingPrefixes ()
  {
    // Ensure that no single prefix is claimed by more than one enum entry
    final ICommonsMap <String, EGS1Prefix> aAll = new CommonsHashMap <> ();
    for (final EGS1Prefix e : EGS1Prefix.values ())
      e.iterateAllPrefixes (sPrefix -> {
        final EGS1Prefix eOld = aAll.put (sPrefix, e);
        assertNull ("Prefix '" + sPrefix + "' is used by " + eOld + " and " + e, eOld);
      });

    // Ensure that every single prefix resolves back to the enum entry it came from
    for (final var aEntry : aAll.entrySet ())
      assertSame (aEntry.getKey (), aEntry.getValue (), EGS1Prefix.getPrefixFromCode (aEntry.getKey () + "0000000"));
  }

  @Test
  public void testGetPrefix ()
  {
    assertNull (EGS1Prefix.getPrefixFromCode (null));
    assertNull (EGS1Prefix.getPrefixFromCode (""));
    assertNull (EGS1Prefix.getPrefixFromCode ("0"));
    assertNull (EGS1Prefix.getPrefixFromCode ("000000"));
    assertSame (EGS1Prefix.X0, EGS1Prefix.getPrefixFromCode ("0000000"));
    assertSame (EGS1Prefix.X0, EGS1Prefix.getPrefixFromCode ("00000000"));
    assertSame (EGS1Prefix.X0, EGS1Prefix.getPrefixFromCode ("000000000"));
    assertSame (EGS1Prefix.X1, EGS1Prefix.getPrefixFromCode ("0000001"));
    assertSame (EGS1Prefix.X1, EGS1Prefix.getPrefixFromCode ("00000010"));
    assertSame (EGS1Prefix.X1, EGS1Prefix.getPrefixFromCode ("000000100"));
    assertSame (EGS1Prefix.BG, EGS1Prefix.getPrefixFromCode ("3800123"));
    assertSame (EGS1Prefix.NZ, EGS1Prefix.getPrefixFromCode ("9420123"));

    // Newly added prefixes
    assertSame (EGS1Prefix.XK, EGS1Prefix.getPrefixFromCode ("3810123"));
    assertSame (EGS1Prefix.UG, EGS1Prefix.getPrefixFromCode ("6050123"));
    assertSame (EGS1Prefix.QA, EGS1Prefix.getPrefixFromCode ("6300123"));
    assertSame (EGS1Prefix.CN_1, EGS1Prefix.getPrefixFromCode ("6800123"));
    assertSame (EGS1Prefix.CN_2, EGS1Prefix.getPrefixFromCode ("6900123"));
    assertSame (EGS1Prefix.MM, EGS1Prefix.getPrefixFromCode ("8830123"));

    // Prefixes that GS1 lists as managed by the GS1 Global Office
    assertSame (EGS1Prefix.X17, EGS1Prefix.getPrefixFromCode ("6100123"));
    assertSame (EGS1Prefix.X18, EGS1Prefix.getPrefixFromCode ("6140123"));
    assertSame (EGS1Prefix.X19, EGS1Prefix.getPrefixFromCode ("7580123"));
    assertSame (EGS1Prefix.X20, EGS1Prefix.getPrefixFromCode ("8940123"));
    // 612 is not listed by GS1 at all
    assertNull (EGS1Prefix.getPrefixFromCode ("6120123"));

    // Corrected country assignments
    assertSame (EGS1Prefix.TW, EGS1Prefix.getPrefixFromCode ("4710123"));
    assertSame (EGS1Prefix.HK, EGS1Prefix.getPrefixFromCode ("4890123"));
    assertSame (EGS1Prefix.MO, EGS1Prefix.getPrefixFromCode ("9580123"));
    assertSame (EGS1Prefix.TR, EGS1Prefix.getPrefixFromCode ("8680123"));

    // GTIN-8 allocations use a 4 digit prefix
    assertSame (EGS1Prefix.X7, EGS1Prefix.getPrefixFromCode ("96001234"));
    assertSame (EGS1Prefix.X7, EGS1Prefix.getPrefixFromCode ("96241234"));
    assertSame (EGS1Prefix.X15, EGS1Prefix.getPrefixFromCode ("96251234"));
    assertSame (EGS1Prefix.X16, EGS1Prefix.getPrefixFromCode ("96271234"));
    assertSame (EGS1Prefix.X16, EGS1Prefix.getPrefixFromCode ("96991234"));
    assertSame (EGS1Prefix.X12, EGS1Prefix.getPrefixFromCode ("99001234"));

    for (int i = 0; i < 1_000; ++i)
    {
      EGS1Prefix.getPrefixFromCode (Integer.toString (i));
      EGS1Prefix.getPrefixFromCode (Integer.toString (i) + "0000000000000");
    }
  }

  @Test
  public void testGetCountryCodeFromCode ()
  {
    assertNull (EGS1Prefix.getCountryCodeFromCode (null));
    assertNull (EGS1Prefix.getCountryCodeFromCode (""));
    // No country code assigned
    assertNull (EGS1Prefix.getCountryCodeFromCode ("0000000"));
    assertNull (EGS1Prefix.getCountryCodeFromCode ("9770123456789"));

    assertEquals ("DE", EGS1Prefix.getCountryCodeFromCode ("4001234567890"));
    assertEquals ("AT", EGS1Prefix.getCountryCodeFromCode ("9001234567890"));
    assertEquals ("AU", EGS1Prefix.getCountryCodeFromCode ("9301234567890"));
    assertEquals ("JP", EGS1Prefix.getCountryCodeFromCode ("4501234567890"));
    assertEquals ("SK", EGS1Prefix.getCountryCodeFromCode ("8581234567890"));

    // Multi country prefixes return the primary country
    assertEquals ("FR", EGS1Prefix.getCountryCodeFromCode ("3001234567890"));
    assertEquals ("BE", EGS1Prefix.getCountryCodeFromCode ("5401234567890"));
    assertEquals ("DK", EGS1Prefix.getCountryCodeFromCode ("5701234567890"));
    assertEquals ("CH", EGS1Prefix.getCountryCodeFromCode ("7601234567890"));
    assertEquals ("IT", EGS1Prefix.getCountryCodeFromCode ("8001234567890"));
    assertEquals ("ES", EGS1Prefix.getCountryCodeFromCode ("8401234567890"));
  }

  @Test
  public void testGetAllCountryCodes ()
  {
    assertEquals (new CommonsArrayList <> ("FR", "MC"), EGS1Prefix.FR.getAllCountryCodes ());
    assertEquals (new CommonsArrayList <> ("BE", "LU"), EGS1Prefix.BE.getAllCountryCodes ());
    assertEquals (new CommonsArrayList <> ("DK", "FO", "GL"), EGS1Prefix.DK.getAllCountryCodes ());
    assertEquals (new CommonsArrayList <> ("CH", "LI"), EGS1Prefix.CH.getAllCountryCodes ());
    assertEquals (new CommonsArrayList <> ("IT", "SM", "VA"), EGS1Prefix.IT.getAllCountryCodes ());
    assertEquals (new CommonsArrayList <> ("ES", "AD"), EGS1Prefix.ES.getAllCountryCodes ());
    assertTrue (EGS1Prefix.X0.getAllCountryCodes ().isEmpty ());
  }
}
