/*
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.Month;
import java.util.Locale;

import org.junit.Test;

import com.helger.commons.datetime.PDTFactory;

/**
 * Test class for class {@link EEUCountry}.
 *
 * @author Philip Helger
 */
public final class EEUCountryTest
{
  @Test
  public void testBasic ()
  {
    assertEquals (28, EEUCountry.values ().length);
    for (final EEUCountry e : EEUCountry.values ())
      assertSame (e, EEUCountry.getFromIDOrNull (e.getID ()));
  }

  @Test
  public void testLeaveDate ()
  {
    assertTrue (EEUCountry.UNITED_KINGDOM.hasLeaveDate ());
    assertEquals (PDTFactory.createLocalDate (2020, Month.DECEMBER, 31), EEUCountry.UNITED_KINGDOM.getLeaveDate ());

    assertFalse (EEUCountry.GERMANY.hasLeaveDate ());
    assertNull (EEUCountry.GERMANY.getLeaveDate ());
  }

  @Test
  public void testIsInEU ()
  {
    assertFalse (EEUCountry.UNITED_KINGDOM.isInEUAt (PDTFactory.createLocalDate (1972, Month.DECEMBER, 31)));
    assertTrue (EEUCountry.UNITED_KINGDOM.isInEUAt (PDTFactory.createLocalDate (1973, Month.JANUARY, 1)));
    assertTrue (EEUCountry.UNITED_KINGDOM.isInEUAt (PDTFactory.createLocalDate (2020, Month.DECEMBER, 31)));
    assertFalse (EEUCountry.UNITED_KINGDOM.isInEUAt (PDTFactory.createLocalDate (2021, Month.JANUARY, 1)));

    assertFalse (EEUCountry.GERMANY.isInEUAt (PDTFactory.createLocalDate (1951, Month.DECEMBER, 31)));
    assertTrue (EEUCountry.GERMANY.isInEUAt (PDTFactory.createLocalDate (1952, Month.JANUARY, 1)));
    assertTrue (EEUCountry.GERMANY.isInEUAt (PDTFactory.createLocalDate (2020, Month.DECEMBER, 31)));
    assertTrue (EEUCountry.GERMANY.isInEUAt (PDTFactory.createLocalDate (2021, Month.JANUARY, 1)));
  }

  @Test
  public void testIsEUCountryAt ()
  {
    assertFalse (EEUCountry.isEUCountryAt (Locale.UK, PDTFactory.createLocalDate (1972, Month.DECEMBER, 31)));
    assertTrue (EEUCountry.isEUCountryAt (Locale.UK, PDTFactory.createLocalDate (1973, Month.JANUARY, 1)));
    assertTrue (EEUCountry.isEUCountryAt (Locale.UK, PDTFactory.createLocalDate (2020, Month.DECEMBER, 31)));
    assertFalse (EEUCountry.isEUCountryAt (Locale.UK, PDTFactory.createLocalDate (2021, Month.JANUARY, 1)));

    assertFalse (EEUCountry.isEUCountryAt (Locale.GERMANY, PDTFactory.createLocalDate (1951, Month.DECEMBER, 31)));
    assertTrue (EEUCountry.isEUCountryAt (Locale.GERMANY, PDTFactory.createLocalDate (1952, Month.JANUARY, 1)));
    assertTrue (EEUCountry.isEUCountryAt (Locale.GERMANY, PDTFactory.createLocalDate (2020, Month.DECEMBER, 31)));
    assertTrue (EEUCountry.isEUCountryAt (Locale.GERMANY, PDTFactory.createLocalDate (2021, Month.JANUARY, 1)));
  }
}
