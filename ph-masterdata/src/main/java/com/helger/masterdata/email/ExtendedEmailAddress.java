/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.email.EmailAddressHelper;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Default writable implementation of {@link IExtendedEmailAddress}.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class ExtendedEmailAddress implements IExtendedEmailAddress, ICloneable <ExtendedEmailAddress>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (ExtendedEmailAddress.class);

  private IEmailAddressType m_aAddressType;
  private String m_sAddress;
  private String m_sPersonal;

  public ExtendedEmailAddress ()
  {}

  public ExtendedEmailAddress (@Nonnull final ExtendedEmailAddress aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    m_aAddressType = aBase.m_aAddressType;
    m_sAddress = aBase.m_sAddress;
    m_sPersonal = aBase.m_sPersonal;
  }

  public ExtendedEmailAddress (@Nonnull final IExtendedEmailAddress aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    setType (aBase.getType ());
    setAddress (aBase.getAddress ());
    setPersonal (aBase.getPersonal ());
  }

  public ExtendedEmailAddress (@Nullable final IEmailAddressType aAddressType, @Nonnull final String sAddress)
  {
    this (aAddressType, sAddress, null);
  }

  public ExtendedEmailAddress (@Nullable final IEmailAddressType aAddressType,
                               @Nonnull final String sAddress,
                               @Nullable final String sPersonal)
  {
    setType (aAddressType);
    setAddress (sAddress);
    setPersonal (sPersonal);
  }

  @Nullable
  public IEmailAddressType getType ()
  {
    return m_aAddressType;
  }

  @Nonnull
  public final EChange setType (@Nullable final IEmailAddressType aAddressType)
  {
    if (EqualsHelper.equals (aAddressType, m_aAddressType))
      return EChange.UNCHANGED;
    m_aAddressType = aAddressType;
    return EChange.CHANGED;
  }

  @Nonnull
  public String getAddress ()
  {
    return m_sAddress;
  }

  /**
   * Set the address part of the email address. Performs a validity check of the
   * email address.
   *
   * @param sAddress
   *        The address part to be set. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if the address was valid and different from
   *         the existing one. Returns {@link EChange#UNCHANGED} if the email
   *         address was the same as before, or the email address itself was
   *         invalid.
   */
  @Nonnull
  public final EChange setAddress (@Nonnull final String sAddress)
  {
    ValueEnforcer.notNull (sAddress, "Address");

    final String sRealAddress = EmailAddressHelper.getUnifiedEmailAddress (sAddress);
    if (EqualsHelper.equals (sRealAddress, m_sAddress))
      return EChange.UNCHANGED;

    // Check only without MX record check here, because this is a performance
    // bottleneck when having multiple customers
    if (sRealAddress != null && !EmailAddressHelper.isValid (sRealAddress))
    {
      if (LOGGER.isErrorEnabled ())
        LOGGER.error ("Found an illegal email address: '" + sRealAddress + "'");
      return EChange.UNCHANGED;
    }
    m_sAddress = sRealAddress;
    return EChange.CHANGED;
  }

  @Nullable
  public String getPersonal ()
  {
    return m_sPersonal;
  }

  @Nonnull
  public final EChange setPersonal (@Nullable final String sPersonal)
  {
    final String sRealPersonal = sPersonal;
    if (EqualsHelper.equals (sRealPersonal, m_sPersonal))
      return EChange.UNCHANGED;
    m_sPersonal = sRealPersonal;
    return EChange.CHANGED;
  }

  @Nonnull
  public String getDisplayName ()
  {
    if (m_sAddress == null)
      return "";

    if (StringHelper.hasText (m_sPersonal))
      return m_sPersonal + " <" + m_sAddress + ">";
    return m_sAddress;
  }

  @Nonnull
  public ExtendedEmailAddress getClone ()
  {
    return new ExtendedEmailAddress (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ExtendedEmailAddress rhs = (ExtendedEmailAddress) o;
    return EqualsHelper.equals (m_aAddressType, rhs.m_aAddressType) &&
           EqualsHelper.equals (m_sAddress, rhs.m_sAddress) &&
           EqualsHelper.equals (m_sPersonal, rhs.m_sPersonal);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aAddressType).append (m_sAddress).append (m_sPersonal).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).appendIfNotNull ("addressType", m_aAddressType)
                                       .appendIfNotNull ("address", m_sAddress)
                                       .appendIfNotNull ("personal", m_sPersonal)
                                       .getToString ();
  }

  @Nullable
  public static ExtendedEmailAddress createOnDemand (@Nullable final String sAddress)
  {
    if (StringHelper.hasNoText (sAddress))
      return null;

    final ExtendedEmailAddress ret = new ExtendedEmailAddress ();
    ret.setAddress (sAddress);
    return ret;
  }
}
