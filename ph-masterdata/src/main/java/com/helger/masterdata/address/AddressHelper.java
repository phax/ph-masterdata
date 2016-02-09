/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.string.StringHelper;

/**
 * Contains utility methods for addresses.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class AddressHelper
{
  public static final boolean DEFAULT_COMPLEX_ADDRESS_HANDLING_ENABLED = false;
  public static final String DEFAULT_CARE_OF_PREFIX = "c/o ";
  public static final String DEFAULT_LINE_SEPARATOR = "\n";

  private static final String [] STREET_SEARCH = new String [] { "str.", "g." };
  private static final String [] STREET_REPLACE = new String [] { "straße", "gasse" };

  static
  {
    if (STREET_SEARCH.length != STREET_REPLACE.length)
      throw new InitializationException ("Search and replace arrays for street have different length!");
  }

  private static final SimpleReadWriteLock s_aRWLock = new SimpleReadWriteLock ();
  @GuardedBy ("s_aRWLock")
  private static boolean s_bComplexAddressHandlingEnabled = DEFAULT_COMPLEX_ADDRESS_HANDLING_ENABLED;
  @GuardedBy ("s_aRWLock")
  private static String s_sCareOfPrefix = DEFAULT_CARE_OF_PREFIX;

  private AddressHelper ()
  {}

  public static void setComplexAddressHandlingEnabled (final boolean bEnabled)
  {
    s_aRWLock.writeLocked ( () -> s_bComplexAddressHandlingEnabled = bEnabled);
  }

  public static boolean isComplexAddressHandlingEnabled ()
  {
    return s_aRWLock.readLocked ( () -> s_bComplexAddressHandlingEnabled);
  }

  /**
   * Set the prefix to be added in front of "c/o" address line. By default it is
   * "c/o ".
   *
   * @param sCareOfPrefix
   *        The c/o prefix. May not be <code>null</code> but maybe empty.
   */
  public static void setCareOfPrefix (@Nonnull final String sCareOfPrefix)
  {
    ValueEnforcer.notNull (sCareOfPrefix, "CareOfPrefix");
    s_aRWLock.writeLocked ( () -> s_sCareOfPrefix = sCareOfPrefix);
  }

  /**
   * @return The prefix to be added in front of "c/o" lines. Never
   *         <code>null</code>.
   */
  @Nonnull
  public static String getCareOfPrefix ()
  {
    return s_aRWLock.readLocked ( () -> s_sCareOfPrefix);
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

    // upper case first only
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
  public static String getUnifiedCareOf (@Nullable final String sPOBox, @Nonnull final Locale aSortLocale)
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
  public static String getStreetAndBuildingNumber (@Nullable final IAddress aAddress)
  {
    if (aAddress == null)
      return null;
    return StringHelper.getImplodedNonEmpty (' ', aAddress.getStreet (), aAddress.getBuildingNumber ());
  }

  @Nullable
  public static String getPostalCodeAndCity (@Nullable final IAddress aAddress)
  {
    if (aAddress == null)
      return null;
    return StringHelper.getImplodedNonEmpty (' ', aAddress.getPostalCode (), aAddress.getCity ());
  }

  @Nullable
  public static String getAddressString (@Nullable final IAddress aAddress, @Nonnull final Locale aDisplayLocale)
  {
    return getAddressString (aAddress, aDisplayLocale, DEFAULT_LINE_SEPARATOR);
  }

  @Nullable
  public static String getAddressString (@Nullable final IAddress aAddress,
                                         @Nonnull final Locale aDisplayLocale,
                                         @Nonnull final String sLineSeparator)
  {
    return getAddressString (aAddress,
                             EnumSet.of (EAddressField.CARE_OF,
                                         EAddressField.STREET_AND_BUILDING_NUMBER,
                                         EAddressField.POSTAL_CODE_AND_CITY,
                                         EAddressField.POST_OFFICE_BOX,
                                         EAddressField.COUNTRY),
                             aDisplayLocale,
                             sLineSeparator);
  }

  @Nullable
  public static String getAddressString (@Nullable final IAddress aAddress,
                                         @Nonnull final Set <EAddressField> aFields,
                                         @Nonnull final Locale aDisplayLocale)
  {
    return getAddressString (aAddress, aFields, aDisplayLocale, DEFAULT_LINE_SEPARATOR);
  }

  @Nullable
  public static String getAddressString (@Nullable final IAddress aAddress,
                                         @Nonnull final Set <EAddressField> aFields,
                                         @Nonnull final Locale aDisplayLocale,
                                         @Nonnull final String sLineSeparator)
  {
    ValueEnforcer.notNull (aDisplayLocale, "DisplayLocale");
    ValueEnforcer.notNull (sLineSeparator, "LineSeparator");
    if (aAddress == null)
      return null;

    final List <String> aValues = new ArrayList <> (aFields.size ());
    for (final EAddressField eField : aFields)
      aValues.add (eField.get (aAddress, aDisplayLocale));
    return StringHelper.getImplodedNonEmpty (sLineSeparator, aValues);
  }
}
