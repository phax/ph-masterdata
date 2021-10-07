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

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * Writable implementation of the {@link IPostalAddress} interface.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PostalAddress implements IPostalAddress, ICloneable <PostalAddress>, Serializable
{
  private IPostalAddressType m_aAddressType;
  private String m_sCountry;
  private String m_sState;
  private String m_sPostalCode;
  private String m_sCity;
  private String m_sStreet;
  private String m_sBuildingNumber;
  private String m_sPostOfficeBox;
  private String m_sCareOf;
  // Status vars
  private Locale m_aCountry;

  public PostalAddress ()
  {}

  public PostalAddress (@Nonnull final PostalAddress aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    m_aAddressType = aBase.m_aAddressType;
    m_sCountry = aBase.m_sCountry;
    m_sState = aBase.m_sState;
    m_sPostalCode = aBase.m_sPostalCode;
    m_sCity = aBase.m_sCity;
    m_sStreet = aBase.m_sStreet;
    m_sBuildingNumber = aBase.m_sBuildingNumber;
    m_sPostOfficeBox = aBase.m_sPostOfficeBox;
    m_sCareOf = aBase.m_sCareOf;
  }

  public PostalAddress (@Nonnull final IPostalAddress aBase, @Nonnull final Locale aSortLocale)
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
    setCareOf (aBase.getCareOf (), aSortLocale);
  }

  public PostalAddress (@Nullable final IPostalAddressType aType)
  {
    setType (aType);
  }

  public PostalAddress (@Nullable final IPostalAddressType aType,
                        @Nullable final String sCountry,
                        @Nullable final String sState,
                        @Nullable final String sPostalCode,
                        @Nullable final String sCity,
                        @Nullable final String sStreet,
                        @Nullable final String sBuildingNumber,
                        @Nullable final String sPostOfficeBox,
                        @Nullable final String sCareOf,
                        @Nonnull final Locale aSortLocale)
  {
    setType (aType);
    setCountry (sCountry, aSortLocale);
    setState (sState, aSortLocale);
    setPostalCode (sPostalCode);
    setCity (sCity, aSortLocale);
    setStreet (sStreet, aSortLocale);
    setBuildingNumber (sBuildingNumber);
    setPostOfficeBox (sPostOfficeBox, aSortLocale);
    setCareOf (sCareOf, aSortLocale);
  }

  @Nullable
  public IPostalAddressType getType ()
  {
    return m_aAddressType;
  }

  @Nonnull
  public EChange setType (@Nullable final IPostalAddressType aAddressType)
  {
    if (EqualsHelper.equals (m_aAddressType, aAddressType))
      return EChange.UNCHANGED;
    m_aAddressType = aAddressType;
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
    final Locale aCountry = getCountryLocale ();
    return aCountry == null ? null : aCountry.getDisplayCountry (aDisplayLocale);
  }

  @Nullable
  public Locale getCountryLocale ()
  {
    Locale ret = m_aCountry;
    if (ret == null && m_sCountry != null)
    {
      m_aCountry = ret = CountryCache.getInstance ().getCountry (m_sCountry);
    }
    return ret;
  }

  @Nonnull
  public EChange setCountry (@Nullable final Locale aCountry, @Nonnull final Locale aSortLocale)
  {
    return setCountry (aCountry == null ? null : aCountry.getCountry (), aSortLocale);
  }

  @Nonnull
  public EChange setCountry (@Nullable final String sCountry, @Nonnull final Locale aSortLocale)
  {
    final String sRealCountry = PostalAddressHelper.getUnifiedCountry (sCountry, aSortLocale);
    if (EqualsHelper.equals (m_sCountry, sRealCountry))
      return EChange.UNCHANGED;
    m_sCountry = sRealCountry == null ? null : sRealCountry.intern ();
    m_aCountry = null;
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
    final String sRealState = PostalAddressHelper.getUnifiedState (sState, aSortLocale);
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
    final String sRealCity = PostalAddressHelper.getUnifiedCity (sCity, aSortLocale);
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
    final String sRealStreet = PostalAddressHelper.getUnifiedStreet (sStreet, aSortLocale);
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
    final String sRealPostOfficeBox = PostalAddressHelper.getUnifiedPOBox (sPostOfficeBox, aSortLocale);
    if (EqualsHelper.equals (m_sPostOfficeBox, sRealPostOfficeBox))
      return EChange.UNCHANGED;
    m_sPostOfficeBox = sRealPostOfficeBox;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCareOf ()
  {
    return m_sCareOf;
  }

  @Nonnull
  public EChange setCareOf (@Nullable final String sCareOf, @Nonnull final Locale aSortLocale)
  {
    final String sRealCareOf = PostalAddressHelper.getUnifiedCareOf (sCareOf, aSortLocale);
    if (EqualsHelper.equals (m_sCareOf, sRealCareOf))
      return EChange.UNCHANGED;
    m_sCareOf = sRealCareOf;
    return EChange.CHANGED;
  }

  @Nonnull
  public PostalAddress getClone ()
  {
    return new PostalAddress (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final PostalAddress rhs = (PostalAddress) o;
    return EqualsHelper.equals (m_aAddressType, rhs.m_aAddressType) &&
           EqualsHelper.equals (m_sCountry, rhs.m_sCountry) &&
           EqualsHelper.equals (m_sState, rhs.m_sState) &&
           EqualsHelper.equals (m_sPostalCode, rhs.m_sPostalCode) &&
           EqualsHelper.equals (m_sCity, rhs.m_sCity) &&
           EqualsHelper.equals (m_sStreet, rhs.m_sStreet) &&
           EqualsHelper.equals (m_sBuildingNumber, rhs.m_sBuildingNumber) &&
           EqualsHelper.equals (m_sPostOfficeBox, rhs.m_sPostOfficeBox) &&
           EqualsHelper.equals (m_sCareOf, rhs.m_sCareOf);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aAddressType)
                                       .append (m_sCountry)
                                       .append (m_sState)
                                       .append (m_sPostalCode)
                                       .append (m_sCity)
                                       .append (m_sStreet)
                                       .append (m_sBuildingNumber)
                                       .append (m_sPostOfficeBox)
                                       .append (m_sCareOf)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).appendIfNotNull ("addressType", m_aAddressType)
                                       .appendIfNotNull ("country", m_sCountry)
                                       .appendIfNotNull ("state", m_sState)
                                       .appendIfNotNull ("postalCode", m_sPostalCode)
                                       .appendIfNotNull ("city", m_sCity)
                                       .appendIfNotNull ("street", m_sStreet)
                                       .appendIfNotNull ("buildingNumber", m_sBuildingNumber)
                                       .appendIfNotNull ("pobox", m_sPostOfficeBox)
                                       .appendIfNotNull ("careOf", m_sCareOf)
                                       .getToString ();
  }
}
