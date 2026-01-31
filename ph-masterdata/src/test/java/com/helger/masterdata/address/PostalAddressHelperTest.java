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
package com.helger.masterdata.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import org.junit.Test;

import com.helger.collection.commons.CommonsArrayList;

/**
 * Test class for class {@link PostalAddressHelper}.
 *
 * @author Philip Helger
 */
public final class PostalAddressHelperTest
{
  @Test
  public void testGetUnifiedStreetEnabled ()
  {
    PostalAddressHelper.setComplexAddressHandlingEnabled (true);
    try
    {
      final Locale aLocale = Locale.GERMAN;
      assertNull (PostalAddressHelper.getUnifiedStreet (null, aLocale));
      assertEquals ("Abc", PostalAddressHelper.getUnifiedStreet ("abc", aLocale));
      assertEquals ("Straße", PostalAddressHelper.getUnifiedStreet ("str.", aLocale));
      assertEquals ("Gasse", PostalAddressHelper.getUnifiedStreet ("g.", aLocale));
      assertEquals ("Hauptstraße 1", PostalAddressHelper.getUnifiedStreet ("Hauptstr. 1", aLocale));
      assertEquals ("Sandgasse 1", PostalAddressHelper.getUnifiedStreet ("Sandg. 1", aLocale));
    }
    finally
    {
      PostalAddressHelper.setComplexAddressHandlingEnabled (false);
    }
  }

  @Test
  public void testGetUnifiedStreetDisabled ()
  {
    assertFalse (PostalAddressHelper.isComplexAddressHandlingEnabled ());

    final Locale aLocale = Locale.GERMAN;
    assertNull (PostalAddressHelper.getUnifiedStreet (null, aLocale));
    assertEquals ("abc", PostalAddressHelper.getUnifiedStreet ("abc", aLocale));
    assertEquals ("str.", PostalAddressHelper.getUnifiedStreet ("str.", aLocale));
    assertEquals ("g.", PostalAddressHelper.getUnifiedStreet ("g.", aLocale));
    assertEquals ("Hauptstr. 1", PostalAddressHelper.getUnifiedStreet ("Hauptstr. 1", aLocale));
    assertEquals ("Sandg. 1", PostalAddressHelper.getUnifiedStreet ("Sandg. 1", aLocale));
  }

  @Test
  public void testCareOfPrefix ()
  {
    final Locale aLocale = Locale.GERMAN;
    final PostalAddress a = new PostalAddress ();
    a.setCareOf ("any", aLocale);
    assertEquals ("c/o any", PostalAddressHelper.getAddressString (a, aLocale));
    PostalAddressHelper.setCareOfPrefix ("bla-");
    assertEquals ("bla-any", PostalAddressHelper.getAddressString (a, aLocale));
    PostalAddressHelper.setCareOfPrefix (PostalAddressHelper.DEFAULT_CARE_OF_PREFIX);
    assertEquals ("c/o any", PostalAddressHelper.getAddressString (a, aLocale));
  }

  @Test
  public void testGetAddressString ()
  {
    final Locale aLocale = Locale.GERMAN;
    final PostalAddress a = new PostalAddress ();
    assertEquals ("", PostalAddressHelper.getAddressString (a, aLocale));
    a.setCareOf ("any", aLocale);
    assertEquals ("c/o any", PostalAddressHelper.getAddressString (a, aLocale));
    a.setStreet ("Main road", aLocale);
    assertEquals ("c/o any" + "\n" + "Main road", PostalAddressHelper.getAddressString (a, aLocale));
    a.setBuildingNumber ("7");
    assertEquals ("c/o any" + "\n" + "Main road 7", PostalAddressHelper.getAddressString (a, aLocale));
    a.setPostalCode ("12345");
    assertEquals ("c/o any" + "\n" + "Main road 7" + "\n" + "12345", PostalAddressHelper.getAddressString (a, aLocale));
    a.setCity ("Eindhoven", aLocale);
    assertEquals ("c/o any" + "\n" + "Main road 7" + "\n" + "12345 Eindhoven", PostalAddressHelper.getAddressString (a, aLocale));
    a.setPostOfficeBox ("PO12", aLocale);
    assertEquals ("c/o any" + "\n" + "Main road 7" + "\n" + "12345 Eindhoven" + "\n" + "PO12",
                  PostalAddressHelper.getAddressString (a, aLocale));
    a.setCountry ("AT", aLocale);
    assertEquals ("c/o any" + "\n" + "Main road 7" + "\n" + "12345 Eindhoven" + "\n" + "PO12" + "\n" + "Österreich",
                  PostalAddressHelper.getAddressString (a, aLocale));
    assertEquals ("c/o any" + "\n" + "Main road 7" + "\n" + "12345 Eindhoven" + "\n" + "PO12" + "\n" + "Austria",
                  PostalAddressHelper.getAddressString (a, Locale.UK));
    assertEquals ("Main road 7" + "\n" + "Austria",
                  PostalAddressHelper.getAddressString (a,
                                                        new CommonsArrayList <> (EPostalAddressField.STREET_AND_BUILDING_NUMBER,
                                                                                 EPostalAddressField.COUNTRY),
                                                        Locale.UK));
    assertEquals ("Main road 7" + "\n" + "Main road 7" + "\n" + "Austria",
                  PostalAddressHelper.getAddressString (a,
                                                        new CommonsArrayList <> (EPostalAddressField.STREET_AND_BUILDING_NUMBER,
                                                                                 EPostalAddressField.STREET_AND_BUILDING_NUMBER,
                                                                                 EPostalAddressField.COUNTRY),
                                                        Locale.UK));
  }
}
