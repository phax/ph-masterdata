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
package com.helger.masterdata.address;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.string.StringHelper;

/**
 * Read-only interface of an address.
 *
 * @author Philip Helger
 */
public interface IPostalAddress
{
  /**
   * @return The semantic type of this address.
   */
  @Nullable
  IPostalAddressType getType ();

  default boolean hasType ()
  {
    return getType () != null;
  }

  /**
   * @return The country the address resides in. The upper case two-letter
   *         ISO-3166 code as used by java.util.Locale.
   */
  @Nullable
  String getCountry ();

  /**
   * @return The locale representing the country. May be <code>null</code>.
   */
  @Nullable
  Locale getCountryLocale ();

  /**
   * Get the display name of the country using the passed display locale.
   *
   * @param aDisplayLocale
   *        The locale in which the countries name should be retrieved.
   * @return <code>null</code> if no country is set
   */
  @Nullable
  String getCountryDisplayName (@Nonnull Locale aDisplayLocale);

  default boolean hasCountry ()
  {
    return getCountry () != null;
  }

  default boolean hasCountry (@Nonnull final String sCountryCode)
  {
    return StringHelper.hasNoText (sCountryCode) && sCountryCode.equals (getCountry ());
  }

  default boolean hasCountry (@Nullable final Locale aLocale)
  {
    return aLocale != null && aLocale.equals (getCountryLocale ());
  }

  /**
   * @return The optional state within the country. May be <code>null</code>.
   */
  @Nullable
  String getState ();

  default boolean hasState ()
  {
    return StringHelper.hasText (getState ());
  }

  /**
   * @return The ZIP code representing the area within a state/country.
   */
  @Nullable
  String getPostalCode ();

  default boolean hasPostalCode ()
  {
    return StringHelper.hasText (getPostalCode ());
  }

  /**
   * @return The name of the city the address resides in.
   */
  @Nullable
  String getCity ();

  default boolean hasCity ()
  {
    return StringHelper.hasText (getCity ());
  }

  /**
   * @return The street (including the number) of the address.
   */
  @Nullable
  String getStreet ();

  default boolean hasStreet ()
  {
    return StringHelper.hasText (getStreet ());
  }

  /**
   * @return The number of the building in the street (if it is not contained in
   *         the street).
   */
  @Nullable
  String getBuildingNumber ();

  default boolean hasBuildingNumber ()
  {
    return StringHelper.hasText (getBuildingNumber ());
  }

  /**
   * @return An optional post office box that should be used instead the street.
   *         May be <code>null</code>.
   */
  @Nullable
  String getPostOfficeBox ();

  default boolean hasPostOfficeBox ()
  {
    return StringHelper.hasText (getPostOfficeBox ());
  }

  /**
   * @return An optional "care of" (c/o) that is used when you’re sending mail
   *         to someone who does not actually live at the address in question.
   *         May be <code>null</code>.
   */
  @Nullable
  String getCareOf ();

  default boolean hasCareOf ()
  {
    return StringHelper.hasText (getCareOf ());
  }
}
