/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.masterdata.locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.locale.LocaleCache;
import com.helger.commons.locale.country.CountryCache;

/**
 * Test class for class {@link DeprecatedLocaleHandler}.
 *
 * @author Philip Helger
 */
public final class DeprecatedLocaleHandlerTest
{
  @Test
  public void testDefault ()
  {
    final DeprecatedLocaleHandler x = DeprecatedLocaleHandler.getDefaultInstance ();
    assertNotNull (x);
    assertEquals (1, x.getAllDeprecatedLocales ().size ());

    // Deprecated one
    assertTrue (x.isDeprecatedLocale (CountryCache.getInstance ().getCountry ("CS")));

    // This locale is only implicitly deprecated
    assertFalse (x.isDeprecatedLocale (LocaleCache.getInstance ().getLocale ("cs", "CS")));

    // This country is not deprecated
    assertFalse (x.isDeprecatedLocale (CountryCache.getInstance ().getCountry ("AT")));

    // Deprecated one with fallback
    assertTrue (x.isDeprecatedLocaleWithFallback (CountryCache.getInstance ().getCountry ("CS")));

    // This locale is implicitly also deprecated
    assertTrue (x.isDeprecatedLocaleWithFallback (LocaleCache.getInstance ().getLocale ("cs", "CS")));

    // This country is not deprecated
    assertFalse (x.isDeprecatedLocaleWithFallback (CountryCache.getInstance ().getCountry ("AT")));
  }
}
