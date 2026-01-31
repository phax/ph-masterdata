/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.id.factory.GlobalIDFactory;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.type.ObjectType;
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

  public CompanySite (@NonNull final ICompany aCompany)
  {
    this (GlobalIDFactory.getNewPersistentStringID (), aCompany);
  }

  public CompanySite (@NonNull @Nonempty final String sID, @NonNull final ICompany aCompany)
  {
    m_sID = ValueEnforcer.notEmpty (sID, "ID");
    m_aCompany = ValueEnforcer.notNull (aCompany, "Company");
  }

  @NonNull
  public ObjectType getObjectType ()
  {
    return OT;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
  public ICompany getCompany ()
  {
    return m_aCompany;
  }

  @Nullable
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @NonNull
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

  @NonNull
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

  @NonNull
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

  @NonNull
  public EChange setVirtualSite (final boolean bIsVirtualSite)
  {
    if (m_bIsVirtualSite == bIsVirtualSite)
      return EChange.UNCHANGED;
    m_bIsVirtualSite = bIsVirtualSite;
    return EChange.CHANGED;
  }

  @NonNull
  public IPostalAddress getAddress ()
  {
    return m_aAddress;
  }

  @NonNull
  public EChange setAddress (@NonNull final IPostalAddress aAddress)
  {
    ValueEnforcer.notNull (aAddress, "Address");

    if (m_aAddress.equals (aAddress))
      return EChange.UNCHANGED;
    m_aAddress = aAddress;
    return EChange.CHANGED;
  }

  @NonNull
  public ITelephoneNumber getDefaultTelNo ()
  {
    return m_aTelNo;
  }

  @NonNull
  public EChange setDefaultTelNo (@NonNull final ITelephoneNumber aTelNo)
  {
    ValueEnforcer.notNull (aTelNo, "TelNo");

    if (m_aTelNo.equals (aTelNo))
      return EChange.UNCHANGED;
    m_aTelNo = aTelNo;
    return EChange.CHANGED;
  }

  @NonNull
  public ITelephoneNumber getDefaultFaxNo ()
  {
    return m_aFaxNo;
  }

  @NonNull
  public EChange setDefaultFaxNo (@NonNull final ITelephoneNumber aFaxNo)
  {
    ValueEnforcer.notNull (aFaxNo, "FaxNo");

    if (m_aFaxNo.equals (aFaxNo))
      return EChange.UNCHANGED;
    m_aFaxNo = aFaxNo;
    return EChange.CHANGED;
  }

  @NonNull
  public IExtendedEmailAddress getDefaultEmailAddress ()
  {
    return m_aEmailAddress;
  }

  @NonNull
  public EChange setDefaultEmailAddress (@NonNull final IExtendedEmailAddress aEmailAddress)
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
