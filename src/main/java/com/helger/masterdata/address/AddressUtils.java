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

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.exceptions.InitializationException;
import com.helger.commons.string.StringHelper;

/**
 * Contains utility methods for addresses.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class AddressUtils
{
  private static final String [] STREET_SEARCH = new String [] { "str.", "g." };
  private static final String [] STREET_REPLACE = new String [] { "straße", "gasse" };

  private static final AtomicBoolean s_aComplexAddressHandlingEnabled = new AtomicBoolean (false);

  static
  {
    if (STREET_SEARCH.length != STREET_REPLACE.length)
      throw new InitializationException ("Search and replace arrays for street have different length!");
  }

  private AddressUtils ()
  {}

  public static void setComplexAddressHandlingEnabled (final boolean bEnabled)
  {
    s_aComplexAddressHandlingEnabled.set (bEnabled);
  }

  public static boolean isComplexAddressHandlingEnabled ()
  {
    return s_aComplexAddressHandlingEnabled.get ();
  }

  @Nullable
  private static String _unifyPart (@Nonnull final String sPart, @Nonnull final Locale aSortLocale)
  {
    // empty name?
    String s = sPart.trim ();
    final int nLength = s.length ();
    if (nLength == 0)
      return null;

    // all upper case name?
    if (nLength == 1)
      return s.toUpperCase (aSortLocale);

    // uppercase first only
    s = s.substring (0, 1).toUpperCase (aSortLocale) + s.substring (1);

    return s;
  }

  @Nullable
  public static String getUnifiedStreet (@Nullable final String sStreet, @Nonnull final Locale aSortLocale)
  {
    if (!isComplexAddressHandlingEnabled ())
      return sStreet;

    if (sStreet == null)
      return null;

    final String s = StringHelper.replaceMultiple (sStreet, STREET_SEARCH, STREET_REPLACE);
    return _unifyPart (s, aSortLocale);
  }

  @Nullable
  public static String getUnifiedCity (@Nullable final String sCity, @Nonnull final Locale aSortLocale)
  {
    if (!isComplexAddressHandlingEnabled ())
      return sCity;

    if (sCity == null)
      return null;

    return _unifyPart (sCity, aSortLocale);
  }

  @Nullable
  public static String getUnifiedPOBox (@Nullable final String sPOBox, @Nonnull final Locale aSortLocale)
  {
    if (!isComplexAddressHandlingEnabled ())
      return sPOBox;

    if (sPOBox == null)
      return null;

    return _unifyPart (sPOBox, aSortLocale);
  }

  @Nullable
  public static String getUnifiedState (@Nullable final String sState, @Nonnull final Locale aSortLocale)
  {
    if (!isComplexAddressHandlingEnabled ())
      return sState;

    if (sState == null)
      return null;

    return _unifyPart (sState, aSortLocale);
  }

  @Nullable
  public static String getUnifiedCountry (@Nullable final String sCountry, @Nonnull final Locale aSortLocale)
  {
    if (!isComplexAddressHandlingEnabled ())
      return sCountry;

    if (sCountry == null)
      return null;

    return _unifyPart (sCountry, aSortLocale);
  }

  @Nullable
  public static String getStreetAndBuildingNumber (@Nullable final IReadonlyAddress aAddress)
  {
    if (aAddress == null)
      return null;
    return StringHelper.getImplodedNonEmpty (' ', aAddress.getStreet (), aAddress.getBuildingNumber ());
  }

  @Nullable
  public static String getPostalCodeAndCity (@Nullable final IReadonlyAddress aAddress)
  {
    if (aAddress == null)
      return null;
    return StringHelper.getImplodedNonEmpty (' ', aAddress.getPostalCode (), aAddress.getCity ());
  }

  @Nullable
  public static String getAddressString (@Nullable final IReadonlyAddress aAddress, @Nonnull final Locale aDisplayLocale)
  {
    return getAddressString (aAddress, aDisplayLocale, "\n");
  }

  @Nullable
  public static String getAddressString (@Nullable final IReadonlyAddress aAddress,
                                         @Nonnull final Locale aDisplayLocale,
                                         @Nonnull final String sLineSeparator)
  {
    ValueEnforcer.notNull (aDisplayLocale, "DisplayLocale");
    ValueEnforcer.notNull (sLineSeparator, "LineSeparator");
    if (aAddress == null)
      return null;

    final StringBuilder aSB = new StringBuilder ();

    // Street + building number
    final String sStreet = getStreetAndBuildingNumber (aAddress);
    if (StringHelper.hasText (sStreet))
      aSB.append (sStreet);

    // Postal code + city
    final String sNextLine = getPostalCodeAndCity (aAddress);
    if (StringHelper.hasText (sNextLine))
    {
      if (aSB.length () > 0)
        aSB.append (sLineSeparator);
      aSB.append (sNextLine);
    }

    if (StringHelper.hasText (aAddress.getPostOfficeBox ()))
    {
      if (aSB.length () > 0)
        aSB.append (sLineSeparator);
      aSB.append (aAddress.getPostOfficeBox ());
    }

    if (StringHelper.hasText (aAddress.getCountry ()))
    {
      if (aSB.length () > 0)
        aSB.append (sLineSeparator);

      aSB.append (aAddress.getCountryDisplayName (aDisplayLocale));
    }

    return aSB.toString ();
  }
}
