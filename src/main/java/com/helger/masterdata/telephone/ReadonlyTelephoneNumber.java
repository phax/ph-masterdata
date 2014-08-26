/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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
import javax.annotation.concurrent.Immutable;

import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Default read-only implementation of {@link IReadonlyTelephoneNumber}
 * 
 * @author Philip Helger
 */
@Immutable
public final class ReadonlyTelephoneNumber implements IReadonlyTelephoneNumber
{
  private final ETelephoneType m_eType;
  private final String m_sCountryCode;
  private final String m_sAreaCode;
  private final String m_sLine;
  private final String m_sDirectDial;

  public ReadonlyTelephoneNumber (@Nonnull final IReadonlyTelephoneNumber aBase)
  {
    this (aBase.getType (), aBase.getCountryCode (), aBase.getAreaCode (), aBase.getLine (), aBase.getDirectDial ());
  }

  public ReadonlyTelephoneNumber (@Nullable final ETelephoneType eType,
                                  @Nullable final String sCountryCode,
                                  @Nullable final String sAreaCode,
                                  @Nullable final String sLine,
                                  @Nullable final String sDirectDial)
  {
    m_eType = eType;
    m_sCountryCode = sCountryCode;
    m_sAreaCode = sAreaCode;
    m_sLine = TelephoneUtils.getCleanedLine (sLine);
    m_sDirectDial = sDirectDial;
  }

  @Nullable
  public ETelephoneType getType ()
  {
    return m_eType;
  }

  @Nullable
  public String getCountryCode ()
  {
    return m_sCountryCode;
  }

  @Nullable
  public String getAreaCode ()
  {
    return m_sAreaCode;
  }

  @Nullable
  public String getLine ()
  {
    return m_sLine;
  }

  @Nullable
  public String getDirectDial ()
  {
    return m_sDirectDial;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof ReadonlyTelephoneNumber))
      return false;
    final ReadonlyTelephoneNumber rhs = (ReadonlyTelephoneNumber) o;
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
  public static ReadonlyTelephoneNumber createOnDemandLineOnly (@Nullable final String sLine)
  {
    if (StringHelper.hasNoText (sLine))
      return null;

    return new ReadonlyTelephoneNumber ((ETelephoneType) null, (String) null, (String) null, sLine, (String) null);
  }
}
