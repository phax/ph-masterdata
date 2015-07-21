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

import java.util.Locale;

import org.junit.Test;

/**
 * Test class for class {@link AddressHelper}.
 * 
 * @author Philip Helger
 */
public final class AddressUtilsTest
{
  @Test
  public void testEnabled ()
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
  public void testDisabled ()
  {
    final Locale aLocale = Locale.GERMAN;
    assertNull (AddressHelper.getUnifiedStreet (null, aLocale));
    assertEquals ("abc", AddressHelper.getUnifiedStreet ("abc", aLocale));
    assertEquals ("str.", AddressHelper.getUnifiedStreet ("str.", aLocale));
    assertEquals ("g.", AddressHelper.getUnifiedStreet ("g.", aLocale));
    assertEquals ("Hauptstr. 1", AddressHelper.getUnifiedStreet ("Hauptstr. 1", aLocale));
    assertEquals ("Sandg. 1", AddressHelper.getUnifiedStreet ("Sandg. 1", aLocale));
  }
}
