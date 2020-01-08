/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link PostalAddress}
 *
 * @author Philip Helger
 */
public final class PostalAddressTest
{
  private static final Locale L_DE = new Locale ("de");
  private static final Locale L_EN = new Locale ("en");

  @Test
  public void testBasic ()
  {
    final PostalAddress a = new PostalAddress ();
    assertNull (a.getType ());
    assertNull (a.getCountry ());
    assertNull (a.getState ());
    assertNull (a.getPostalCode ());
    assertNull (a.getCity ());
    assertNull (a.getStreet ());
    assertNull (a.getPostOfficeBox ());
    assertNull (a.getCareOf ());

    // Type
    assertTrue (a.setType (EPostalAddressType.OFFICE).isChanged ());
    assertEquals (EPostalAddressType.OFFICE, a.getType ());
    assertFalse (a.setType (EPostalAddressType.OFFICE).isChanged ());
    assertEquals (EPostalAddressType.OFFICE, a.getType ());
    assertTrue (a.setType (EPostalAddressType.PERSONAL).isChanged ());
    assertEquals (EPostalAddressType.PERSONAL, a.getType ());

    // Country
    assertTrue (a.setCountry ("de", L_EN).isChanged ());
    assertEquals ("de", a.getCountry ());
    assertFalse (a.setCountry ("de", L_EN).isChanged ());
    assertEquals ("de", a.getCountry ());
    assertEquals ("Deutschland", a.getCountryDisplayName (L_DE));
    assertEquals ("DE", a.getCountryLocale ().getCountry ());
    assertTrue (a.setCountry ("gb", L_EN).isChanged ());
    assertEquals ("gb", a.getCountry ());
    assertEquals ("GB", a.getCountryLocale ().getCountry ());

    // State
    assertTrue (a.setState ("NÖ", L_EN).isChanged ());
    assertEquals ("NÖ", a.getState ());
    assertFalse (a.setState ("NÖ", L_EN).isChanged ());
    assertEquals ("NÖ", a.getState ());
    assertTrue (a.setState ("OÖ", L_EN).isChanged ());
    assertEquals ("OÖ", a.getState ());

    // Postal code
    assertTrue (a.setPostalCode ("1010").isChanged ());
    assertEquals ("1010", a.getPostalCode ());
    assertFalse (a.setPostalCode ("1010").isChanged ());
    assertEquals ("1010", a.getPostalCode ());
    assertTrue (a.setPostalCode ("2020").isChanged ());
    assertEquals ("2020", a.getPostalCode ());

    // City
    assertTrue (a.setCity ("Wien", L_EN).isChanged ());
    assertEquals ("Wien", a.getCity ());
    assertFalse (a.setCity ("Wien", L_EN).isChanged ());
    assertEquals ("Wien", a.getCity ());
    assertTrue (a.setCity ("Mauerbach", L_EN).isChanged ());
    assertEquals ("Mauerbach", a.getCity ());

    // Street
    assertTrue (a.setStreet ("Hauptstr. 1", L_EN).isChanged ());
    assertEquals ("Hauptstr. 1", a.getStreet ());
    assertFalse (a.setStreet ("Hauptstr. 1", L_EN).isChanged ());
    assertEquals ("Hauptstr. 1", a.getStreet ());
    assertTrue (a.setStreet ("Hauptstr. 2", L_EN).isChanged ());
    assertEquals ("Hauptstr. 2", a.getStreet ());

    // POBox
    assertTrue (a.setPostOfficeBox ("12AB", L_EN).isChanged ());
    assertEquals ("12AB", a.getPostOfficeBox ());
    assertFalse (a.setPostOfficeBox ("12AB", L_EN).isChanged ());
    assertEquals ("12AB", a.getPostOfficeBox ());
    assertTrue (a.setPostOfficeBox ("XY11", L_EN).isChanged ());
    assertEquals ("XY11", a.getPostOfficeBox ());

    // POBox
    assertTrue (a.setCareOf ("co1", L_EN).isChanged ());
    assertEquals ("co1", a.getCareOf ());
    assertFalse (a.setCareOf ("co1", L_EN).isChanged ());
    assertEquals ("co1", a.getCareOf ());
    assertTrue (a.setCareOf ("co2", L_EN).isChanged ());
    assertEquals ("co2", a.getCareOf ());

    CommonsTestHelper.testDefaultSerialization (a);
  }

  @Test
  public void testDefaultImpl ()
  {
    PostalAddress a = new PostalAddress ();
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setType (EPostalAddressType.PERSONAL);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setCountry ("de", L_DE);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setState ("Wien", L_DE);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setPostalCode ("1140");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setCity ("St. Plöten", L_DE);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setStreet ("Hauptstr. 1", L_DE);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setPostOfficeBox ("12AB", L_DE);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);

    a = new PostalAddress ();
    a.setCareOf ("co", L_DE);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new PostalAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new PostalAddress (a, L_DE));
    CommonsTestHelper.testGetClone (a);
  }
}
