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

import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.filter.IFilter;

/**
 * A locale filter that checks if a locale is on at least one of the specified
 * continents.
 * 
 * @author Philip Helger
 */
public class FilterLocaleCountryOnAnyContinent implements IFilter <Locale>
{
  private final EnumSet <EContinent> m_aContinents;

  public FilterLocaleCountryOnAnyContinent (@Nonnull @Nonempty final EContinent... aContinents)
  {
    ValueEnforcer.notEmptyNoNullValue (aContinents, "Continents");
    m_aContinents = EnumSet.noneOf (EContinent.class);
    for (final EContinent eContinent : aContinents)
      m_aContinents.add (eContinent);
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public Set <EContinent> getAllContinents ()
  {
    return EnumSet.copyOf (m_aContinents);
  }

  public boolean matchesFilter (@Nonnull final Locale aValue)
  {
    // Get all continents of the passed locale
    final Set <EContinent> aContinents = ContinentUtils.getContinentsOfCountry (aValue);
    if (aContinents == null)
      return false;

    // Retain only the specified ones
    aContinents.retainAll (m_aContinents);

    // If at least one locale is left, we have a match
    return !aContinents.isEmpty ();
  }
}
