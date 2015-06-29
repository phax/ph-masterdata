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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * Writable implementation of the {@link IAddress} interface.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class Address implements IAddress
{
  private EAddressType m_eType;
  private String m_sCountry;
  private String m_sState;
  private String m_sPostalCode;
  private String m_sCity;
  private String m_sStreet;
  private String m_sBuildingNumber;
  private String m_sPostOfficeBox;

  public Address ()
  {}

  public Address (@Nonnull final Address aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    m_eType = aBase.m_eType;
    m_sCountry = aBase.m_sCountry;
    m_sState = aBase.m_sState;
    m_sPostalCode = aBase.m_sPostalCode;
    m_sCity = aBase.m_sCity;
    m_sStreet = aBase.m_sStreet;
    m_sBuildingNumber = aBase.m_sBuildingNumber;
    m_sPostOfficeBox = aBase.m_sPostOfficeBox;
  }

  public Address (@Nonnull final IReadonlyAddress aBase, @Nonnull final Locale aSortLocale)
  {
    ValueEnforcer.notNull (aBase, "Base");
    setType (aBase.getType ());
    setCountry (aBase.getCountry (), aSortLocale);
    setState (aBase.getState (), aSortLocale);
    setPostalCode (aBase.getPostalCode ());
    setCity (aBase.getCity (), aSortLocale);
    setStreet (aBase.getStreet (), aSortLocale);
    setBuildingNumber (aBase.getBuildingNumber ());
    setPostOfficeBox (aBase.getPostOfficeBox (), aSortLocale);
  }

  public Address (@Nullable final EAddressType eType)
  {
    setType (eType);
  }

  public Address (@Nullable final EAddressType eType,
                  @Nullable final String sCountry,
                  @Nullable final String sState,
                  @Nullable final String sPostalCode,
                  @Nullable final String sCity,
                  @Nullable final String sStreet,
                  @Nullable final String sBuildingNumber,
                  @Nullable final String sPostOfficeBox,
                  @Nonnull final Locale aSortLocale)
  {
    setType (eType);
    setCountry (sCountry, aSortLocale);
    setState (sState, aSortLocale);
    setPostalCode (sPostalCode);
    setCity (sCity, aSortLocale);
    setStreet (sStreet, aSortLocale);
    setBuildingNumber (sBuildingNumber);
    setPostOfficeBox (sPostOfficeBox, aSortLocale);
  }

  @Nullable
  public EAddressType getType ()
  {
    return m_eType;
  }

  @Nonnull
  public EChange setType (@Nullable final EAddressType eType)
  {
    if (EqualsHelper.equals (m_eType, eType))
      return EChange.UNCHANGED;
    m_eType = eType;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCountry ()
  {
    return m_sCountry;
  }

  @Nullable
  public String getCountryDisplayName (@Nonnull final Locale aDisplayLocale)
  {
    final Locale aCountry = CountryCache.getInstance ().getCountry (m_sCountry);
    return aCountry == null ? null : aCountry.getDisplayCountry (aDisplayLocale);
  }

  @Nullable
  public Locale getCountryLocale ()
  {
    return CountryCache.getInstance ().getCountry (m_sCountry);
  }

  @Nonnull
  public EChange setCountry (@Nullable final Locale aCountry, @Nonnull final Locale aSortLocale)
  {
    return setCountry (aCountry == null ? null : aCountry.getCountry (), aSortLocale);
  }

  @Nonnull
  public EChange setCountry (@Nullable final String sCountry, @Nonnull final Locale aSortLocale)
  {
    final String sRealCountry = AddressUtils.getUnifiedCountry (sCountry, aSortLocale);
    if (EqualsHelper.equals (m_sCountry, sRealCountry))
      return EChange.UNCHANGED;
    m_sCountry = sRealCountry == null ? null : sRealCountry.intern ();
    return EChange.CHANGED;
  }

  @Nullable
  public String getState ()
  {
    return m_sState;
  }

  @Nonnull
  public EChange setState (@Nullable final String sState, @Nonnull final Locale aSortLocale)
  {
    final String sRealState = AddressUtils.getUnifiedState (sState, aSortLocale);
    if (EqualsHelper.equals (m_sState, sRealState))
      return EChange.UNCHANGED;
    m_sState = sRealState;
    return EChange.CHANGED;
  }

  @Nullable
  public String getPostalCode ()
  {
    return m_sPostalCode;
  }

  @Nonnull
  public EChange setPostalCode (@Nullable final String sPostalCode)
  {
    final String sRealPostalCode = sPostalCode;
    if (EqualsHelper.equals (m_sPostalCode, sRealPostalCode))
      return EChange.UNCHANGED;
    m_sPostalCode = sRealPostalCode;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCity ()
  {
    return m_sCity;
  }

  @Nonnull
  public EChange setCity (@Nullable final String sCity, @Nonnull final Locale aSortLocale)
  {
    final String sRealCity = AddressUtils.getUnifiedCity (sCity, aSortLocale);
    if (EqualsHelper.equals (m_sCity, sRealCity))
      return EChange.UNCHANGED;
    m_sCity = sRealCity;
    return EChange.CHANGED;
  }

  @Nullable
  public String getStreet ()
  {
    return m_sStreet;
  }

  @Nonnull
  public EChange setStreet (@Nullable final String sStreet, @Nonnull final Locale aSortLocale)
  {
    final String sRealStreet = AddressUtils.getUnifiedStreet (sStreet, aSortLocale);
    if (EqualsHelper.equals (m_sStreet, sRealStreet))
      return EChange.UNCHANGED;
    m_sStreet = sRealStreet;
    return EChange.CHANGED;
  }

  @Nullable
  public String getBuildingNumber ()
  {
    return m_sBuildingNumber;
  }

  @Nonnull
  public EChange setBuildingNumber (@Nullable final String sBuildingNumber)
  {
    if (EqualsHelper.equals (m_sBuildingNumber, sBuildingNumber))
      return EChange.UNCHANGED;
    m_sBuildingNumber = sBuildingNumber;
    return EChange.CHANGED;
  }

  @Nullable
  public String getPostOfficeBox ()
  {
    return m_sPostOfficeBox;
  }

  @Nonnull
  public EChange setPostOfficeBox (@Nullable final String sPostOfficeBox, @Nonnull final Locale aSortLocale)
  {
    final String sRealPostOfficeBox = AddressUtils.getUnifiedPOBox (sPostOfficeBox, aSortLocale);
    if (EqualsHelper.equals (m_sPostOfficeBox, sRealPostOfficeBox))
      return EChange.UNCHANGED;
    m_sPostOfficeBox = sRealPostOfficeBox;
    return EChange.CHANGED;
  }

  @Nonnull
  public Address getClone ()
  {
    return new Address (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final Address rhs = (Address) o;
    return EqualsHelper.equals (m_eType, rhs.m_eType) &&
           EqualsHelper.equals (m_sCountry, rhs.m_sCountry) &&
           EqualsHelper.equals (m_sState, rhs.m_sState) &&
           EqualsHelper.equals (m_sPostalCode, rhs.m_sPostalCode) &&
           EqualsHelper.equals (m_sCity, rhs.m_sCity) &&
           EqualsHelper.equals (m_sStreet, rhs.m_sStreet) &&
           EqualsHelper.equals (m_sBuildingNumber, rhs.m_sBuildingNumber) &&
           EqualsHelper.equals (m_sPostOfficeBox, rhs.m_sPostOfficeBox);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eType)
                                       .append (m_sCountry)
                                       .append (m_sState)
                                       .append (m_sPostalCode)
                                       .append (m_sCity)
                                       .append (m_sStreet)
                                       .append (m_sBuildingNumber)
                                       .append (m_sPostOfficeBox)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).appendIfNotNull ("type", m_eType)
                                       .appendIfNotNull ("country", m_sCountry)
                                       .appendIfNotNull ("state", m_sState)
                                       .appendIfNotNull ("postalCode", m_sPostalCode)
                                       .appendIfNotNull ("city", m_sCity)
                                       .appendIfNotNull ("street", m_sStreet)
                                       .appendIfNotNull ("buildingNumber", m_sBuildingNumber)
                                       .appendIfNotNull ("pobox", m_sPostOfficeBox)
                                       .toString ();
  }
}
