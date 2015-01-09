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
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.system.SystemHelper;
import com.helger.db.jpa.annotations.UsedOnlyByJPA;
import com.helger.masterdata.MasterdataUtils;

// ESCA-JAVA0116:
/**
 * Writable implementation of the {@link IAddress} interface.
 *
 * @author Philip Helger
 */
@MappedSuperclass
@Embeddable
@Access (value = AccessType.PROPERTY)
@NotThreadSafe
public class Address implements IAddress
{
  public static final String FIELD_TYPE = "addresstype";
  public static final String FIELD_COUNTRY = "country";
  public static final int LENGTH_COUNTRY = 100;
  public static final String FIELD_STATE = "state";
  public static final int LENGTH_STATE = 100;
  public static final String FIELD_POSTALCODE = "zipcode";
  public static final int LENGTH_POSTALCODE = 20;
  public static final String FIELD_CITY = "city";
  public static final int LENGTH_CITY = 100;
  public static final String FIELD_STREET = "street";
  public static final int LENGTH_STREET = 200;
  public static final String FIELD_BUILDINGNUMBER = "buildingnumber";
  public static final int LENGTH_BUILDINGNUMBER = 50;
  public static final String FIELD_POBOX = "pobox";
  public static final int LENGTH_POBOX = 50;

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

  @Column (name = FIELD_TYPE)
  @Nullable
  public EAddressType getType ()
  {
    return m_eType;
  }

  @Nonnull
  public EChange setType (@Nullable final EAddressType eType)
  {
    if (EqualsUtils.equals (m_eType, eType))
      return EChange.UNCHANGED;
    m_eType = eType;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_COUNTRY, length = LENGTH_COUNTRY)
  @Nullable
  public String getCountry ()
  {
    return m_sCountry;
  }

  @Transient
  @Nullable
  public String getCountryDisplayName (@Nonnull final Locale aDisplayLocale)
  {
    final Locale aCountry = CountryCache.getInstance ().getCountry (m_sCountry);
    return aCountry == null ? null : aCountry.getDisplayCountry (aDisplayLocale);
  }

  @Transient
  @Nullable
  public Locale getCountryLocale ()
  {
    return CountryCache.getInstance ().getCountry (m_sCountry);
  }

  @Nonnull
  @Deprecated
  @UsedOnlyByJPA
  public EChange setCountry (@Nullable final String sCountry)
  {
    return setCountry (sCountry, SystemHelper.getSystemLocale ());
  }

  @Nonnull
  public EChange setCountry (@Nullable final Locale aCountry, @Nonnull final Locale aSortLocale)
  {
    return setCountry (aCountry == null ? null : aCountry.getCountry (), aSortLocale);
  }

  @Nonnull
  public EChange setCountry (@Nullable final String sCountry, @Nonnull final Locale aSortLocale)
  {
    final String sRealCountry = MasterdataUtils.getEnsuredLength (AddressUtils.getUnifiedCountry (sCountry, aSortLocale),
                                                                  LENGTH_COUNTRY);
    if (EqualsUtils.equals (m_sCountry, sRealCountry))
      return EChange.UNCHANGED;
    m_sCountry = sRealCountry == null ? null : sRealCountry.intern ();
    return EChange.CHANGED;
  }

  @Column (name = FIELD_STATE, length = LENGTH_STATE)
  @Nullable
  public String getState ()
  {
    return m_sState;
  }

  @Nonnull
  @Deprecated
  @UsedOnlyByJPA
  public EChange setState (@Nullable final String sState)
  {
    return setState (sState, SystemHelper.getSystemLocale ());
  }

  @Nonnull
  public EChange setState (@Nullable final String sState, @Nonnull final Locale aSortLocale)
  {
    final String sRealState = MasterdataUtils.getEnsuredLength (AddressUtils.getUnifiedState (sState, aSortLocale),
                                                                LENGTH_STATE);
    if (EqualsUtils.equals (m_sState, sRealState))
      return EChange.UNCHANGED;
    m_sState = sRealState;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_POSTALCODE, length = LENGTH_POSTALCODE)
  @Nullable
  public String getPostalCode ()
  {
    return m_sPostalCode;
  }

  @Nonnull
  public EChange setPostalCode (@Nullable final String sPostalCode)
  {
    final String sRealPostalCode = MasterdataUtils.getEnsuredLength (sPostalCode, LENGTH_POSTALCODE);
    if (EqualsUtils.equals (m_sPostalCode, sRealPostalCode))
      return EChange.UNCHANGED;
    m_sPostalCode = sRealPostalCode;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_CITY, length = LENGTH_CITY)
  @Nullable
  public String getCity ()
  {
    return m_sCity;
  }

  @Nonnull
  @Deprecated
  @UsedOnlyByJPA
  public EChange setCity (@Nullable final String sCity)
  {
    return setCity (sCity, SystemHelper.getSystemLocale ());
  }

  @Nonnull
  public EChange setCity (@Nullable final String sCity, @Nonnull final Locale aSortLocale)
  {
    final String sRealCity = MasterdataUtils.getEnsuredLength (AddressUtils.getUnifiedCity (sCity, aSortLocale),
                                                               LENGTH_CITY);
    if (EqualsUtils.equals (m_sCity, sRealCity))
      return EChange.UNCHANGED;
    m_sCity = sRealCity;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_STREET, length = LENGTH_STREET)
  @Nullable
  public String getStreet ()
  {
    return m_sStreet;
  }

  @Nonnull
  @UsedOnlyByJPA
  @Deprecated
  public EChange setStreet (@Nullable final String sStreet)
  {
    return setStreet (sStreet, SystemHelper.getSystemLocale ());
  }

  @Nonnull
  public EChange setStreet (@Nullable final String sStreet, @Nonnull final Locale aSortLocale)
  {
    final String sRealStreet = MasterdataUtils.getEnsuredLength (AddressUtils.getUnifiedStreet (sStreet, aSortLocale),
                                                                 LENGTH_STREET);
    if (EqualsUtils.equals (m_sStreet, sRealStreet))
      return EChange.UNCHANGED;
    m_sStreet = sRealStreet;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_BUILDINGNUMBER, length = LENGTH_BUILDINGNUMBER)
  @Nullable
  public String getBuildingNumber ()
  {
    return m_sBuildingNumber;
  }

  @Nonnull
  public EChange setBuildingNumber (@Nullable final String sBuildingNumber)
  {
    if (EqualsUtils.equals (m_sBuildingNumber, sBuildingNumber))
      return EChange.UNCHANGED;
    m_sBuildingNumber = sBuildingNumber;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_POBOX, length = LENGTH_POBOX)
  @Nullable
  public String getPostOfficeBox ()
  {
    return m_sPostOfficeBox;
  }

  @Nonnull
  @Deprecated
  @UsedOnlyByJPA
  public EChange setPostOfficeBox (@Nullable final String sPostOfficeBox)
  {
    return setPostOfficeBox (sPostOfficeBox, SystemHelper.getSystemLocale ());
  }

  @Nonnull
  public EChange setPostOfficeBox (@Nullable final String sPostOfficeBox, @Nonnull final Locale aSortLocale)
  {
    final String sRealPostOfficeBox = MasterdataUtils.getEnsuredLength (AddressUtils.getUnifiedPOBox (sPostOfficeBox,
                                                                                                      aSortLocale),
                                                                        LENGTH_POBOX);
    if (EqualsUtils.equals (m_sPostOfficeBox, sRealPostOfficeBox))
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
    return EqualsUtils.equals (m_eType, rhs.m_eType) &&
           EqualsUtils.equals (m_sCountry, rhs.m_sCountry) &&
           EqualsUtils.equals (m_sState, rhs.m_sState) &&
           EqualsUtils.equals (m_sPostalCode, rhs.m_sPostalCode) &&
           EqualsUtils.equals (m_sCity, rhs.m_sCity) &&
           EqualsUtils.equals (m_sStreet, rhs.m_sStreet) &&
           EqualsUtils.equals (m_sBuildingNumber, rhs.m_sBuildingNumber) &&
           EqualsUtils.equals (m_sPostOfficeBox, rhs.m_sPostOfficeBox);
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
