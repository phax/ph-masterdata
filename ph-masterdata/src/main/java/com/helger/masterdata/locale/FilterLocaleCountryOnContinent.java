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
package com.helger.masterdata.locale;

import java.util.Locale;
import java.util.Set;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.filter.IFilter;

/**
 * A locale filter that checks if a locale is on the specified continent or not.
 *
 * @author Philip Helger
 */
public class FilterLocaleCountryOnContinent implements IFilter <Locale>
{
  private final EContinent m_eContinent;

  public FilterLocaleCountryOnContinent (@Nonnull final EContinent eContinent)
  {
    m_eContinent = ValueEnforcer.notNull (eContinent, "Continent");
  }

  @Nonnull
  public EContinent getContinent ()
  {
    return m_eContinent;
  }

  public boolean test (@Nonnull final Locale aValue)
  {
    // Get all continents
    final Set <EContinent> aContinents = ContinentHelper.getContinentsOfCountry (aValue);
    return aContinents != null && aContinents.contains (m_eContinent);
  }
}
