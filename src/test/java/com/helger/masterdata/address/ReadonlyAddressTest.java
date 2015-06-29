/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
package com.helger.masterdata.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.helger.commons.mock.AbstractCommonsTestCase;
import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link ReadonlyAddress}
 *
 * @author Philip Helger
 */
public final class ReadonlyAddressTest extends AbstractCommonsTestCase
{
  @Test
  public void testBasic ()
  {
    ReadonlyAddress a = new ReadonlyAddress (null, null, null, null, null, null, null, null, L_DE);
    assertNull (a.getType ());
    assertNull (a.getCountry ());
    assertNull (a.getState ());
    assertNull (a.getPostalCode ());
    assertNull (a.getCity ());
    assertNull (a.getStreet ());
    assertNull (a.getBuildingNumber ());
    assertNull (a.getPostOfficeBox ());

    a = new ReadonlyAddress (EAddressType.OFFICE, "de", "NÖ", "1010", "Wien", "Hauptstr.", "1", "12AB", L_DE);
    assertEquals (EAddressType.OFFICE, a.getType ());
    assertEquals ("de", a.getCountry ());
    assertEquals ("Deutschland", a.getCountryDisplayName (L_DE));
    assertEquals ("DE", a.getCountryLocale ().getCountry ());
    assertEquals ("NÖ", a.getState ());
    assertEquals ("1010", a.getPostalCode ());
    assertEquals ("Wien", a.getCity ());
    assertEquals ("Hauptstr.", a.getStreet ());
    assertEquals ("1", a.getBuildingNumber ());
    assertEquals ("12AB", a.getPostOfficeBox ());
  }

  @Test
  public void testDefaultImpl ()
  {
    final ReadonlyAddress a = new ReadonlyAddress (EAddressType.OFFICE,
                                                   "de",
                                                   "NÖ",
                                                   "1010",
                                                   "Wien",
                                                   "Hauptstr.",
                                                   "1",
                                                   "12AB",
                                                   L_DE);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ReadonlyAddress (EAddressType.OFFICE,
                                                                                               "de",
                                                                                               "NÖ",
                                                                                               "1010",
                                                                                               "Wien",
                                                                                               "Hauptstr.",
                                                                                               "1",
                                                                                               "12AB",
                                                                                               L_DE));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ReadonlyAddress (a, L_DE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a,
                                                                           new ReadonlyAddress (EAddressType.OFFICE2,
                                                                                                "de",
                                                                                                "NÖ",
                                                                                                "1010",
                                                                                                "Wien",
                                                                                                "Hauptstr.",
                                                                                                "1",
                                                                                                "12AB",
                                                                                                L_DE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ReadonlyAddress (EAddressType.OFFICE,
                                                                                                   "at",
                                                                                                   "NÖ",
                                                                                                   "1010",
                                                                                                   "Wien",
                                                                                                   "Hauptstr.",
                                                                                                   "1",
                                                                                                   "12AB",
                                                                                                   L_DE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ReadonlyAddress (EAddressType.OFFICE,
                                                                                                   "de",
                                                                                                   "OÖ",
                                                                                                   "1010",
                                                                                                   "Wien",
                                                                                                   "Hauptstr.",
                                                                                                   "1",
                                                                                                   "12AB",
                                                                                                   L_DE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ReadonlyAddress (EAddressType.OFFICE,
                                                                                                   "de",
                                                                                                   "NÖ",
                                                                                                   "1020",
                                                                                                   "Wien",
                                                                                                   "Hauptstr.",
                                                                                                   "1",
                                                                                                   "12AB",
                                                                                                   L_DE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ReadonlyAddress (EAddressType.OFFICE,
                                                                                                   "de",
                                                                                                   "NÖ",
                                                                                                   "1010",
                                                                                                   "Graz",
                                                                                                   "Hauptstr.",
                                                                                                   "1",
                                                                                                   "12AB",
                                                                                                   L_DE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ReadonlyAddress (EAddressType.OFFICE,
                                                                                                   "de",
                                                                                                   "NÖ",
                                                                                                   "1010",
                                                                                                   "Wien",
                                                                                                   "Hauptstr.",
                                                                                                   "2",
                                                                                                   "12AB",
                                                                                                   L_DE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ReadonlyAddress (EAddressType.OFFICE,
                                                                                                   "de",
                                                                                                   "NÖ",
                                                                                                   "1010",
                                                                                                   "Wien",
                                                                                                   "Hauptstr.",
                                                                                                   "1",
                                                                                                   "13AB",
                                                                                                   L_DE));
  }
}
