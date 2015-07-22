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
package com.helger.masterdata.email;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.email.EmailAddressHelper;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Default read-only implementation of {@link IExtendedEmailAddress}.
 *
 * @author Philip Helger
 */
@Immutable
public final class ReadOnlyExtendedEmailAddress implements IExtendedEmailAddress
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (ReadOnlyExtendedEmailAddress.class);

  private final EEmailAddressType m_eType;
  private final String m_sAddress;
  private final String m_sPersonal;

  public ReadOnlyExtendedEmailAddress (@Nonnull final IExtendedEmailAddress aBase)
  {
    this (aBase.getType (), aBase.getAddress (), aBase.getPersonal ());
  }

  public ReadOnlyExtendedEmailAddress (@Nullable final EEmailAddressType eType, @Nullable final String sAddress)
  {
    this (eType, sAddress, null);
  }

  public ReadOnlyExtendedEmailAddress (@Nullable final EEmailAddressType eType,
                                       @Nullable final String sAddress,
                                       @Nullable final String sPersonal)
  {
    m_eType = eType;
    String sRealAddress = EmailAddressHelper.getUnifiedEmailAddress (sAddress);
    if (sRealAddress != null && !EmailAddressHelper.isValid (sRealAddress))
    {
      s_aLogger.error ("Illegal email address passed: '" + sRealAddress + "'");
      sRealAddress = null;
    }
    m_sAddress = sRealAddress;
    m_sPersonal = sPersonal;
  }

  @Nullable
  public EEmailAddressType getType ()
  {
    return m_eType;
  }

  @Nullable
  public String getAddress ()
  {
    return m_sAddress;
  }

  @Nullable
  public String getPersonal ()
  {
    return m_sPersonal;
  }

  @Nonnull
  public String getDisplayName ()
  {
    if (StringHelper.hasText (m_sPersonal))
      return m_sPersonal + " <" + m_sAddress + ">";
    return m_sAddress;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ReadOnlyExtendedEmailAddress rhs = (ReadOnlyExtendedEmailAddress) o;
    return EqualsHelper.equals (m_eType, rhs.m_eType) &&
           EqualsHelper.equals (m_sAddress, rhs.m_sAddress) &&
           EqualsHelper.equals (m_sPersonal, rhs.m_sPersonal);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eType).append (m_sAddress).append (m_sPersonal).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).appendIfNotNull ("type", m_eType)
                                       .appendIfNotNull ("address", m_sAddress)
                                       .appendIfNotNull ("personal", m_sPersonal)
                                       .toString ();
  }

  @Nullable
  public static ReadOnlyExtendedEmailAddress createOnDemand (@Nullable final String sAddress)
  {
    if (StringHelper.hasNoText (sAddress))
      return null;

    return new ReadOnlyExtendedEmailAddress (null, sAddress, null);
  }
}
