/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.string.StringHelper;

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
      final String sCC = e.getCountryCode ();

      assertTrue (StringHelper.hasText (sFrom));
      assertEquals (sFrom.length (), e.getPrefixLength ());
      if (e.hasTo ())
        assertEquals (sFrom.length (), sTo.length ());
      assertTrue (StringHelper.hasText (sDesc));
      if (e.hasCountryCode ())
      {
        final Locale aC = CountryCache.getInstance ().getCountry (sCC);
        assertNotNull ("No such country '" + sCC + "'", aC);
        assertNotEquals (sCC, aC.getDisplayCountry (Locale.US));
      }
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

    for (int i = 0; i < 1_000; ++i)
    {
      EGS1Prefix.getPrefixFromCode (Integer.toString (i));
      EGS1Prefix.getPrefixFromCode (Integer.toString (i) + "0000000000000");
    }
  }
}
