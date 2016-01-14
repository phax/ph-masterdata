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
package com.helger.masterdata.telephone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Default read-only implementation of {@link ITelephoneNumber}
 *
 * @author Philip Helger
 */
@Immutable
public final class ReadOnlyTelephoneNumber implements ITelephoneNumber
{
  private final ITelephoneType m_aType;
  private final String m_sCountryCode;
  private final String m_sAreaCode;
  private final String m_sLine;
  private final String m_sDirectDial;

  public ReadOnlyTelephoneNumber (@Nonnull final ITelephoneNumber aBase)
  {
    this (aBase.getType (), aBase.getCountryCode (), aBase.getAreaCode (), aBase.getLine (), aBase.getDirectDial ());
  }

  public ReadOnlyTelephoneNumber (@Nullable final ITelephoneType aType,
                                  @Nullable final String sCountryCode,
                                  @Nullable final String sAreaCode,
                                  @Nullable final String sLine,
                                  @Nullable final String sDirectDial)
  {
    m_aType = aType;
    m_sCountryCode = sCountryCode;
    m_sAreaCode = sAreaCode;
    m_sLine = TelephoneHelper.getCleanedLine (sLine);
    m_sDirectDial = sDirectDial;
  }

  @Nullable
  public ITelephoneType getType ()
  {
    return m_aType;
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
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ReadOnlyTelephoneNumber rhs = (ReadOnlyTelephoneNumber) o;
    return EqualsHelper.equals (m_aType, rhs.m_aType) &&
           EqualsHelper.equals (m_sCountryCode, rhs.m_sCountryCode) &&
           EqualsHelper.equals (m_sAreaCode, rhs.m_sAreaCode) &&
           EqualsHelper.equals (m_sLine, rhs.m_sLine) &&
           EqualsHelper.equals (m_sDirectDial, rhs.m_sDirectDial);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aType)
                                       .append (m_sCountryCode)
                                       .append (m_sAreaCode)
                                       .append (m_sLine)
                                       .append (m_sDirectDial)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).appendIfNotNull ("type", m_aType)
                                       .appendIfNotNull ("countryCode", m_sCountryCode)
                                       .appendIfNotNull ("areaCode", m_sAreaCode)
                                       .appendIfNotNull ("line", m_sLine)
                                       .appendIfNotNull ("directDial", m_sDirectDial)
                                       .toString ();
  }

  @Nullable
  public static ReadOnlyTelephoneNumber createOnDemandLineOnly (@Nullable final String sLine)
  {
    if (StringHelper.hasNoText (sLine))
      return null;

    return new ReadOnlyTelephoneNumber ((ITelephoneType) null, (String) null, (String) null, sLine, (String) null);
  }
}
