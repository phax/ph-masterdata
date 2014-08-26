/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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
package com.helger.masterdata.postal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.locale.country.CountryCache;

/**
 * Test class for class {@link PostalCodeManager}.
 * 
 * @author Philip Helger
 */
public final class PostalCodeManagerTest
{
  @Test
  public void testDefault ()
  {
    final PostalCodeManager aMgr = PostalCodeManager.DEFAULT_MGR;
    assertNotNull (aMgr);

    // check valid
    assertTrue (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("AT"), "1234"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("AT"), "123"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("AT"), "12345"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("AT"), "A123"));

    assertTrue (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("DE"), "12345"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("DE"), "1234"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("DE"), "123456"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("DE"), "123 456"));

    assertTrue (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("CZ"), "12345"));
    assertTrue (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("CZ"), "123 45"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("CZ"), "12 345"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("CZ"), "1234 5"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("CZ"), "a234 5"));

    assertTrue (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("PL"), "12345"));
    assertTrue (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("PL"), "12-345"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("PL"), "123-45"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("PL"), "1234-5"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("PL"), "a234 5"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("PL"), "1234 5"));

    // Special one with country code
    assertTrue (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("IM"), "IM1 1AA"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("IM"), "IN1 1AA"));
    assertFalse (aMgr.isValidPostalCodeDefaultYes (CountryCache.getCountry ("IM"), "im1 1AA"));
  }

  @Test
  public void testSpecific ()
  {
    final PostalCodeManager aMgr = PostalCodeManager.DEFAULT_MGR;
    assertNotNull (aMgr);

    final IPostalCodeCountry aCountry = aMgr.getPostalCountryOfCountry ("AM");
    assertNotNull (aCountry);
    assertEquals (1, aCountry.getFormatCount ());
    final PostalCodeFormat aFormat = aCountry.getFormatOfIndex (0);
    assertNotNull (aFormat);
    assertEquals ("NNNN", aFormat.getFormatDefinitionString ());
    assertEquals ("^[0-9][0-9][0-9][0-9]$", aFormat.getRegExPattern ());
    assertEquals ("AM", aFormat.getISO ());
  }
}
