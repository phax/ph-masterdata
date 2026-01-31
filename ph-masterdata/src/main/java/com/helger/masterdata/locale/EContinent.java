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
package com.helger.masterdata.locale;

import java.util.Locale;
import java.util.function.Predicate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.collection.CollectionFind;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsSortedSet;
import com.helger.text.display.IHasDisplayText;

/**
 * Enumeration with all continents
 *
 * @author Philip Helger
 */
public enum EContinent implements IHasID <String>, IHasDisplayText
{
  AFRICA ("af", EContinentName.AFRICA),
  ANTARCTICA ("an", EContinentName.ANTARCTICA),
  ASIA ("as", EContinentName.ASIA),
  EUROPE ("eu", EContinentName.EUROPE),
  NORTH_AMERICA ("na", EContinentName.NORTH_AMERICA),
  OCEANIA ("oc", EContinentName.OCEANIA),
  SOUTH_AMERICA ("sa", EContinentName.SOUTH_AMERICA),
  UNDEFINED ("ud", EContinentName.UNDEFINED);

  private final String m_sID;
  private final EContinentName m_aName;

  EContinent (@NonNull @Nonempty final String sID, @NonNull final EContinentName aName)
  {
    m_sID = sID;
    m_aName = aName;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public String getDisplayText (@NonNull final Locale aContentLocale)
  {
    return m_aName.getDisplayText (aContentLocale);
  }

  @Nullable
  public static EContinent getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EContinent.class, sID);
  }

  @NonNull
  public static Predicate <Locale> filterLocaleCountryOnContinent (@NonNull final EContinent eContinent)
  {
    ValueEnforcer.notNull (eContinent, "Continent");
    return aLocale -> CollectionFind.contains (ContinentHelper.getContinentsOfCountry (aLocale), eContinent);
  }

  @NonNull
  public static Predicate <Locale> filterLocaleCountryOnAnyContinent (@NonNull @Nonempty final EContinent... aContinents)
  {
    ValueEnforcer.notEmptyNoNullValue (aContinents, "Continents");
    return aLocale -> {
      // Get all continents of the passed locale
      final ICommonsSortedSet <EContinent> aContinentsOfLocale = ContinentHelper.getContinentsOfCountry (aLocale);
      if (aContinentsOfLocale == null)
        return false;

      // Retain only the specified ones
      aContinentsOfLocale.retainAll (new CommonsArrayList <> (aContinents));

      // If at least one locale is left, we have a match
      return !aContinentsOfLocale.isEmpty ();
    };
  }
}
