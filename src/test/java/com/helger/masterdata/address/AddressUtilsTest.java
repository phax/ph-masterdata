/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
 * Test class for class {@link AddressUtils}.
 * 
 * @author Philip Helger
 */
public final class AddressUtilsTest
{
  @Test
  public void testEnabled ()
  {
    AddressUtils.setComplexAddressHandlingEnabled (true);
    try
    {
      final Locale aLocale = Locale.GERMAN;
      assertNull (AddressUtils.getUnifiedStreet (null, aLocale));
      assertEquals ("Abc", AddressUtils.getUnifiedStreet ("abc", aLocale));
      assertEquals ("Straße", AddressUtils.getUnifiedStreet ("str.", aLocale));
      assertEquals ("Gasse", AddressUtils.getUnifiedStreet ("g.", aLocale));
      assertEquals ("Hauptstraße 1", AddressUtils.getUnifiedStreet ("Hauptstr. 1", aLocale));
      assertEquals ("Sandgasse 1", AddressUtils.getUnifiedStreet ("Sandg. 1", aLocale));
    }
    finally
    {
      AddressUtils.setComplexAddressHandlingEnabled (false);
    }
  }

  @Test
  public void testDisabled ()
  {
    final Locale aLocale = Locale.GERMAN;
    assertNull (AddressUtils.getUnifiedStreet (null, aLocale));
    assertEquals ("abc", AddressUtils.getUnifiedStreet ("abc", aLocale));
    assertEquals ("str.", AddressUtils.getUnifiedStreet ("str.", aLocale));
    assertEquals ("g.", AddressUtils.getUnifiedStreet ("g.", aLocale));
    assertEquals ("Hauptstr. 1", AddressUtils.getUnifiedStreet ("Hauptstr. 1", aLocale));
    assertEquals ("Sandg. 1", AddressUtils.getUnifiedStreet ("Sandg. 1", aLocale));
  }
}
