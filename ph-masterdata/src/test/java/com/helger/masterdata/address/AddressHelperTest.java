/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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

import java.util.EnumSet;
import java.util.Locale;

import org.junit.Test;

/**
 * Test class for class {@link AddressHelper}.
 *
 * @author Philip Helger
 */
public final class AddressHelperTest
{
  @Test
  public void testGetUnifiedStreetEnabled ()
  {
    AddressHelper.setComplexAddressHandlingEnabled (true);
    try
    {
      final Locale aLocale = Locale.GERMAN;
      assertNull (AddressHelper.getUnifiedStreet (null, aLocale));
      assertEquals ("Abc", AddressHelper.getUnifiedStreet ("abc", aLocale));
      assertEquals ("Straße", AddressHelper.getUnifiedStreet ("str.", aLocale));
      assertEquals ("Gasse", AddressHelper.getUnifiedStreet ("g.", aLocale));
      assertEquals ("Hauptstraße 1", AddressHelper.getUnifiedStreet ("Hauptstr. 1", aLocale));
      assertEquals ("Sandgasse 1", AddressHelper.getUnifiedStreet ("Sandg. 1", aLocale));
    }
    finally
    {
      AddressHelper.setComplexAddressHandlingEnabled (false);
    }
  }

  @Test
  public void testGetUnifiedStreetDisabled ()
  {
    assertFalse (AddressHelper.isComplexAddressHandlingEnabled ());

    final Locale aLocale = Locale.GERMAN;
    assertNull (AddressHelper.getUnifiedStreet (null, aLocale));
    assertEquals ("abc", AddressHelper.getUnifiedStreet ("abc", aLocale));
    assertEquals ("str.", AddressHelper.getUnifiedStreet ("str.", aLocale));
    assertEquals ("g.", AddressHelper.getUnifiedStreet ("g.", aLocale));
    assertEquals ("Hauptstr. 1", AddressHelper.getUnifiedStreet ("Hauptstr. 1", aLocale));
    assertEquals ("Sandg. 1", AddressHelper.getUnifiedStreet ("Sandg. 1", aLocale));
  }

  @Test
  public void testCareOfPrefix ()
  {
    final Locale aLocale = Locale.GERMAN;
    final Address a = new Address ();
    a.setCareOf ("any", aLocale);
    assertEquals ("c/o any", AddressHelper.getAddressString (a, aLocale));
    AddressHelper.setCareOfPrefix ("bla-");
    assertEquals ("bla-any", AddressHelper.getAddressString (a, aLocale));
    AddressHelper.setCareOfPrefix (AddressHelper.DEFAULT_CARE_OF_PREFIX);
    assertEquals ("c/o any", AddressHelper.getAddressString (a, aLocale));
  }

  @Test
  public void testGetAddressString ()
  {
    final Locale aLocale = Locale.GERMAN;
    final Address a = new Address ();
    assertEquals ("", AddressHelper.getAddressString (a, aLocale));
    a.setCareOf ("any", aLocale);
    assertEquals ("c/o any", AddressHelper.getAddressString (a, aLocale));
    a.setStreet ("Main road", aLocale);
    assertEquals ("c/o any" + "\n" + "Main road", AddressHelper.getAddressString (a, aLocale));
    a.setBuildingNumber ("7");
    assertEquals ("c/o any" + "\n" + "Main road 7", AddressHelper.getAddressString (a, aLocale));
    a.setPostalCode ("12345");
    assertEquals ("c/o any" + "\n" + "Main road 7" + "\n" + "12345", AddressHelper.getAddressString (a, aLocale));
    a.setCity ("Eindhoven", aLocale);
    assertEquals ("c/o any" +
                  "\n" +
                  "Main road 7" +
                  "\n" +
                  "12345 Eindhoven",
                  AddressHelper.getAddressString (a, aLocale));
    a.setPostOfficeBox ("PO12", aLocale);
    assertEquals ("c/o any" +
                  "\n" +
                  "Main road 7" +
                  "\n" +
                  "12345 Eindhoven" +
                  "\n" +
                  "PO12",
                  AddressHelper.getAddressString (a, aLocale));
    a.setCountry ("AT", aLocale);
    assertEquals ("c/o any" +
                  "\n" +
                  "Main road 7" +
                  "\n" +
                  "12345 Eindhoven" +
                  "\n" +
                  "PO12" +
                  "\n" +
                  "Österreich",
                  AddressHelper.getAddressString (a, aLocale));
    assertEquals ("c/o any" +
                  "\n" +
                  "Main road 7" +
                  "\n" +
                  "12345 Eindhoven" +
                  "\n" +
                  "PO12" +
                  "\n" +
                  "Austria",
                  AddressHelper.getAddressString (a, Locale.UK));
    assertEquals ("Main road 7" +
                  "\n" +
                  "Austria",
                  AddressHelper.getAddressString (a,
                                                  EnumSet.of (EAddressField.STREET_AND_BUILDING_NUMBER,
                                                              EAddressField.COUNTRY),
                                                  Locale.UK));
  }
}
