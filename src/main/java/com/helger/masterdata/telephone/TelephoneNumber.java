/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.masterdata.telephone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.MasterdataUtils;

// ESCA-JAVA0116:
/**
 * Default writable version of {@link ITelephoneNumber}.
 * 
 * @author Philip Helger
 */
@MappedSuperclass
@Embeddable
@Access (value = AccessType.PROPERTY)
public class TelephoneNumber implements ITelephoneNumber
{
  public static final String FIELD_TYPE = "teltype";
  public static final int LENGTH_TYPE = 6;
  public static final String FIELD_COUNTRYCODE = "telcountry";
  public static final int LENGTH_COUNTRYCODE = 5;
  public static final String FIELD_AREACODE = "telareacode";
  public static final int LENGTH_AREACODE = 10;
  public static final String FIELD_LINE = "telline";
  public static final int LENGTH_LINE = 100;
  public static final String FIELD_DIRECTDIAL = "teldirect";
  public static final int LENGTH_DIRECTDIAL = 50;

  private ETelephoneType m_eType;
  private String m_sCountryCode;
  private String m_sAreaCode;
  private String m_sLine;
  private String m_sDirectDial;

  public TelephoneNumber ()
  {}

  public TelephoneNumber (@Nonnull final IReadonlyTelephoneNumber aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    setType (aBase.getType ());
    setCountryCode (aBase.getCountryCode ());
    setAreaCode (aBase.getAreaCode ());
    setLine (aBase.getLine ());
    setDirectDial (aBase.getDirectDial ());
  }

  public TelephoneNumber (@Nullable final ETelephoneType eType)
  {
    setType (eType);
  }

  public TelephoneNumber (@Nullable final ETelephoneType eType,
                          @Nullable final String sCountryCode,
                          @Nullable final String sAreaCode,
                          @Nullable final String sLine,
                          @Nullable final String sDirectDial)
  {
    setType (eType);
    setCountryCode (sCountryCode);
    setAreaCode (sAreaCode);
    setLine (sLine);
    setDirectDial (sDirectDial);
  }

  @Column (name = FIELD_TYPE, length = LENGTH_TYPE)
  @Nullable
  public ETelephoneType getType ()
  {
    return m_eType;
  }

  @Nonnull
  public EChange setType (@Nullable final ETelephoneType eType)
  {
    if (EqualsUtils.equals (m_eType, eType))
      return EChange.UNCHANGED;
    m_eType = eType;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_COUNTRYCODE, length = LENGTH_COUNTRYCODE)
  @Nullable
  public String getCountryCode ()
  {
    return m_sCountryCode;
  }

  @Nonnull
  public EChange setCountryCode (@Nullable final String sCountryCode)
  {
    final String sRealCountryCode = MasterdataUtils.getEnsuredLength (sCountryCode, LENGTH_COUNTRYCODE);
    if (EqualsUtils.equals (m_sCountryCode, sRealCountryCode))
      return EChange.UNCHANGED;
    m_sCountryCode = sRealCountryCode;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_AREACODE, length = LENGTH_AREACODE)
  @Nullable
  public String getAreaCode ()
  {
    return m_sAreaCode;
  }

  @Nonnull
  public EChange setAreaCode (@Nullable final String sAreaCode)
  {
    final String sRealAreaCode = MasterdataUtils.getEnsuredLength (sAreaCode, LENGTH_AREACODE);
    if (EqualsUtils.equals (m_sAreaCode, sRealAreaCode))
      return EChange.UNCHANGED;
    m_sAreaCode = sRealAreaCode;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_LINE, length = LENGTH_LINE)
  @Nullable
  public String getLine ()
  {
    return m_sLine;
  }

  @Nonnull
  public EChange setLine (@Nullable final String sLine)
  {
    final String sRealLine = MasterdataUtils.getEnsuredLength (TelephoneUtils.getCleanedLine (sLine), LENGTH_LINE);
    if (EqualsUtils.equals (m_sLine, sRealLine))
      return EChange.UNCHANGED;
    m_sLine = sRealLine;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_DIRECTDIAL, length = LENGTH_DIRECTDIAL)
  @Nullable
  public String getDirectDial ()
  {
    return m_sDirectDial;
  }

  @Nonnull
  public EChange setDirectDial (@Nullable final String sDirectDial)
  {
    final String sRealDirectDial = MasterdataUtils.getEnsuredLength (sDirectDial, LENGTH_DIRECTDIAL);
    if (EqualsUtils.equals (m_sDirectDial, sRealDirectDial))
      return EChange.UNCHANGED;
    m_sDirectDial = sRealDirectDial;
    return EChange.CHANGED;
  }

  @Transient
  @Nonnull
  public TelephoneNumber getClone ()
  {
    return new TelephoneNumber (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final TelephoneNumber rhs = (TelephoneNumber) o;
    return EqualsUtils.equals (m_eType, rhs.m_eType) &&
           EqualsUtils.equals (m_sCountryCode, rhs.m_sCountryCode) &&
           EqualsUtils.equals (m_sAreaCode, rhs.m_sAreaCode) &&
           EqualsUtils.equals (m_sLine, rhs.m_sLine) &&
           EqualsUtils.equals (m_sDirectDial, rhs.m_sDirectDial);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eType)
                                       .append (m_sCountryCode)
                                       .append (m_sAreaCode)
                                       .append (m_sLine)
                                       .append (m_sDirectDial)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).appendIfNotNull ("type", m_eType)
                                       .appendIfNotNull ("countryCode", m_sCountryCode)
                                       .appendIfNotNull ("areaCode", m_sAreaCode)
                                       .appendIfNotNull ("line", m_sLine)
                                       .appendIfNotNull ("directDial", m_sDirectDial)
                                       .toString ();
  }

  @Nullable
  public static TelephoneNumber createOnDemandLineOnly (@Nullable final String sLine)
  {
    if (StringHelper.hasNoText (sLine))
      return null;

    final TelephoneNumber ret = new TelephoneNumber ();
    ret.setLine (sLine);
    return ret;
  }
}
