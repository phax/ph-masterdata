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
package com.helger.masterdata.email;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.email.EmailAddressUtils;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.MasterdataUtils;

// ESCA-JAVA0116:
/**
 * Default writable implementation of {@link IExtendedEmailAddress}.
 * 
 * @author Philip Helger
 */
@MappedSuperclass
@Embeddable
@Access (value = AccessType.PROPERTY)
public class ExtendedEmailAddress implements IExtendedEmailAddress
{
  public static final String FIELD_TYPE = "emailtype";
  public static final String FIELD_ADDRESS = "addr";
  public static final int LENGTH_ADDRESS = 256;
  public static final String FIELD_PERSONAL = "personal";
  public static final int LENGTH_PERSONAL = 256;
  private static final Logger s_aLogger = LoggerFactory.getLogger (ExtendedEmailAddress.class);

  private EEmailAddressType m_eType;
  private String m_sAddress;
  private String m_sPersonal;

  public ExtendedEmailAddress ()
  {}

  public ExtendedEmailAddress (@Nonnull final ExtendedEmailAddress aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    m_eType = aBase.m_eType;
    m_sAddress = aBase.m_sAddress;
    m_sPersonal = aBase.m_sPersonal;
  }

  public ExtendedEmailAddress (@Nonnull final IReadonlyExtendedEmailAddress aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    setType (aBase.getType ());
    setAddress (aBase.getAddress ());
    setPersonal (aBase.getPersonal ());
  }

  public ExtendedEmailAddress (@Nullable final EEmailAddressType eType)
  {
    this (eType, null, null);
  }

  public ExtendedEmailAddress (@Nullable final EEmailAddressType eType, @Nullable final String sAddress)
  {
    this (eType, sAddress, null);
  }

  public ExtendedEmailAddress (@Nullable final EEmailAddressType eType,
                               @Nullable final String sAddress,
                               @Nullable final String sPersonal)
  {
    setType (eType);
    setAddress (sAddress);
    setPersonal (sPersonal);
  }

  @Column (name = FIELD_TYPE)
  @Nullable
  public EEmailAddressType getType ()
  {
    return m_eType;
  }

  @Nonnull
  public EChange setType (@Nullable final EEmailAddressType eType)
  {
    if (EqualsUtils.equals (eType, m_eType))
      return EChange.UNCHANGED;
    m_eType = eType;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_ADDRESS, length = LENGTH_ADDRESS)
  @Nullable
  public String getAddress ()
  {
    return m_sAddress;
  }

  /**
   * Set the address part of the email address. Performs a validity check of the
   * email address.
   * 
   * @return {@link EChange#CHANGED} if the address was valid and different from
   *         the existing one. Returns {@link EChange#UNCHANGED} if the email
   *         address was the same as before, or the email address itself was
   *         invalid.
   */
  @Nonnull
  public EChange setAddress (@Nullable final String sAddress)
  {
    final String sRealAddress = MasterdataUtils.getEnsuredLength (EmailAddressUtils.getUnifiedEmailAddress (sAddress),
                                                                  LENGTH_ADDRESS);
    if (EqualsUtils.equals (sRealAddress, m_sAddress))
      return EChange.UNCHANGED;

    // Check only without MX record check here, because this is a performance
    // bottleneck when having multiple customers
    if (sRealAddress != null && !EmailAddressUtils.isValid (sRealAddress))
    {
      s_aLogger.error ("Found an illegal email address: '" + sRealAddress + "'");
      return EChange.UNCHANGED;
    }
    m_sAddress = sRealAddress;
    return EChange.CHANGED;
  }

  @Column (name = FIELD_PERSONAL, length = LENGTH_PERSONAL)
  @Nullable
  public String getPersonal ()
  {
    return m_sPersonal;
  }

  @Nonnull
  public EChange setPersonal (@Nullable final String sPersonal)
  {
    final String sRealPersonal = MasterdataUtils.getEnsuredLength (sPersonal, LENGTH_PERSONAL);
    if (EqualsUtils.equals (sRealPersonal, m_sPersonal))
      return EChange.UNCHANGED;
    m_sPersonal = sRealPersonal;
    return EChange.CHANGED;
  }

  @Transient
  @Nonnull
  public String getDisplayName ()
  {
    if (StringHelper.hasText (m_sPersonal))
      return m_sPersonal + " <" + m_sAddress + ">";
    return m_sAddress;
  }

  @Transient
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
    return EqualsUtils.equals (m_eType, rhs.m_eType) &&
           EqualsUtils.equals (m_sAddress, rhs.m_sAddress) &&
           EqualsUtils.equals (m_sPersonal, rhs.m_sPersonal);
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
  public static ExtendedEmailAddress createOnDemand (@Nullable final String sAddress)
  {
    if (StringHelper.hasNoText (sAddress))
      return null;

    final ExtendedEmailAddress ret = new ExtendedEmailAddress ();
    ret.setAddress (sAddress);
    return ret;
  }
}
