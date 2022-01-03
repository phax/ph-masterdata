/*
 * Copyright (C) 2014-2022 Philip Helger (www.helger.com)
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
package com.helger.masterdata.company;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.id.factory.GlobalIDFactory;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.type.ObjectType;
import com.helger.masterdata.address.IPostalAddress;
import com.helger.masterdata.address.PostalAddress;
import com.helger.masterdata.email.ExtendedEmailAddress;
import com.helger.masterdata.email.IExtendedEmailAddress;
import com.helger.masterdata.telephone.ITelephoneNumber;
import com.helger.masterdata.telephone.TelephoneNumber;

/**
 * The default implementation of the {@link ICompanySite} interface.
 *
 * @author Philip Helger
 */
public class CompanySite implements ICompanySite, Serializable
{
  public static final ObjectType OT = new ObjectType ("company-site");
  public static final boolean DEFAULT_DELETABLE = true;
  public static final boolean DEFAULT_VIRTUALSITE = false;

  private final String m_sID;
  private final ICompany m_aCompany;
  private String m_sDisplayName;
  private String m_sLongName;
  private boolean m_bIsDeletable = DEFAULT_DELETABLE;
  private boolean m_bIsVirtualSite = DEFAULT_VIRTUALSITE;
  private IPostalAddress m_aAddress = new PostalAddress ();
  private ITelephoneNumber m_aTelNo = new TelephoneNumber ();
  private ITelephoneNumber m_aFaxNo = new TelephoneNumber ();
  private IExtendedEmailAddress m_aEmailAddress = new ExtendedEmailAddress ();

  public CompanySite (@Nonnull final ICompany aCompany)
  {
    this (GlobalIDFactory.getNewPersistentStringID (), aCompany);
  }

  public CompanySite (@Nonnull @Nonempty final String sID, @Nonnull final ICompany aCompany)
  {
    m_sID = ValueEnforcer.notEmpty (sID, "ID");
    m_aCompany = ValueEnforcer.notNull (aCompany, "Company");
  }

  @Nonnull
  public ObjectType getObjectType ()
  {
    return OT;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  public ICompany getCompany ()
  {
    return m_aCompany;
  }

  @Nullable
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @Nonnull
  public EChange setDisplayName (@Nullable final String sDisplayName)
  {
    if (EqualsHelper.equals (m_sDisplayName, sDisplayName))
      return EChange.UNCHANGED;
    m_sDisplayName = sDisplayName;
    return EChange.CHANGED;
  }

  @Nullable
  public String getLongName ()
  {
    return m_sLongName;
  }

  @Nonnull
  public EChange setLongName (@Nullable final String sLongName)
  {
    if (EqualsHelper.equals (m_sLongName, sLongName))
      return EChange.UNCHANGED;
    m_sLongName = sLongName;
    return EChange.CHANGED;
  }

  public boolean isDeletable ()
  {
    return m_bIsDeletable;
  }

  @Nonnull
  public EChange setDeletable (final boolean bIsDeletable)
  {
    if (m_bIsDeletable == bIsDeletable)
      return EChange.UNCHANGED;
    m_bIsDeletable = bIsDeletable;
    return EChange.CHANGED;
  }

  public boolean isVirtualSite ()
  {
    return m_bIsVirtualSite;
  }

  @Nonnull
  public EChange setVirtualSite (final boolean bIsVirtualSite)
  {
    if (m_bIsVirtualSite == bIsVirtualSite)
      return EChange.UNCHANGED;
    m_bIsVirtualSite = bIsVirtualSite;
    return EChange.CHANGED;
  }

  @Nonnull
  public IPostalAddress getAddress ()
  {
    return m_aAddress;
  }

  @Nonnull
  public EChange setAddress (@Nonnull final IPostalAddress aAddress)
  {
    ValueEnforcer.notNull (aAddress, "Address");

    if (m_aAddress.equals (aAddress))
      return EChange.UNCHANGED;
    m_aAddress = aAddress;
    return EChange.CHANGED;
  }

  @Nonnull
  public ITelephoneNumber getDefaultTelNo ()
  {
    return m_aTelNo;
  }

  @Nonnull
  public EChange setDefaultTelNo (@Nonnull final ITelephoneNumber aTelNo)
  {
    ValueEnforcer.notNull (aTelNo, "TelNo");

    if (m_aTelNo.equals (aTelNo))
      return EChange.UNCHANGED;
    m_aTelNo = aTelNo;
    return EChange.CHANGED;
  }

  @Nonnull
  public ITelephoneNumber getDefaultFaxNo ()
  {
    return m_aFaxNo;
  }

  @Nonnull
  public EChange setDefaultFaxNo (@Nonnull final ITelephoneNumber aFaxNo)
  {
    ValueEnforcer.notNull (aFaxNo, "FaxNo");

    if (m_aFaxNo.equals (aFaxNo))
      return EChange.UNCHANGED;
    m_aFaxNo = aFaxNo;
    return EChange.CHANGED;
  }

  @Nonnull
  public IExtendedEmailAddress getDefaultEmailAddress ()
  {
    return m_aEmailAddress;
  }

  @Nonnull
  public EChange setDefaultEmailAddress (@Nonnull final IExtendedEmailAddress aEmailAddress)
  {
    ValueEnforcer.notNull (aEmailAddress, "EmailAddress");

    if (m_aEmailAddress.equals (aEmailAddress))
      return EChange.UNCHANGED;
    m_aEmailAddress = aEmailAddress;
    return EChange.CHANGED;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CompanySite rhs = (CompanySite) o;
    return m_sID.equals (rhs.m_sID);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_sID)
                                       .append ("companyID", m_aCompany.getID ())
                                       .appendIfNotNull ("displayName", m_sDisplayName)
                                       .appendIfNotNull ("longName", m_sLongName)
                                       .append ("virtual", m_bIsVirtualSite)
                                       .appendIfNotNull ("address", m_aAddress)
                                       .appendIfNotNull ("telNo", m_aTelNo)
                                       .appendIfNotNull ("faxNo", m_aFaxNo)
                                       .appendIfNotNull ("email", m_aEmailAddress)
                                       .getToString ();
  }
}
