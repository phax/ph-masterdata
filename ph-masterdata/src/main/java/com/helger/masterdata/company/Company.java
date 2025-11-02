/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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
import com.helger.annotation.Nonnegative;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.type.ObjectType;
import com.helger.collection.CollectionFind;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsMap;

/**
 * The default implementation of the {@link ICompany} interface.
 *
 * @author Philip Helger
 */
public class Company implements ICompany, Serializable
{
  public static final ObjectType OT = new ObjectType ("company");

  public static final String FIELD_ID = "id";
  public static final String FIELD_PUBLICNAME = "pubname";
  public static final String FIELD_OFFICIALNAME = "offname";
  public static final String FIELD_STEHQ = "hqsite";

  private final String m_sID;
  private String m_sPublicName;
  private String m_sOfficialName;
  private final ICommonsMap <String, CompanySite> m_aAllSites = new CommonsHashMap <> ();
  private CompanySite m_aHeadQuarterSite;

  public Company (@NonNull @Nonempty final String sID)
  {
    m_sID = ValueEnforcer.notEmpty (sID, "ID");
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

  @Nullable
  public String getPublicName ()
  {
    return m_sPublicName;
  }

  @NonNull
  public EChange setPublicName (@Nullable final String sPublicName)
  {
    if (EqualsHelper.equals (m_sPublicName, sPublicName))
      return EChange.UNCHANGED;
    m_sPublicName = sPublicName;
    return EChange.CHANGED;
  }

  @Nullable
  public String getOfficialName ()
  {
    return m_sOfficialName;
  }

  @NonNull
  public EChange setOfficialName (@Nullable final String sOfficialName)
  {
    if (EqualsHelper.equals (m_sOfficialName, sOfficialName))
      return EChange.UNCHANGED;
    m_sOfficialName = sOfficialName;
    return EChange.CHANGED;
  }

  @Nonnegative
  public int getSiteCount ()
  {
    return m_aAllSites.size ();
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <? extends ICompanySite> getAllSites ()
  {
    return m_aAllSites.copyOfValues ();
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <? extends ICompanySite> getAllPhysicalSites ()
  {
    return m_aAllSites.copyOfValues (ICompanySite::isPhysicalSite);
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <? extends ICompanySite> getAllVirtualSites ()
  {
    return m_aAllSites.copyOfValues (ICompanySite::isVirtualSite);
  }

  @NonNull
  public EChange addSite (@NonNull final CompanySite aSite)
  {
    ValueEnforcer.notNull (aSite, "Site");

    final String sSiteID = aSite.getID ();
    if (m_aAllSites.containsKey (sSiteID))
      return EChange.UNCHANGED;
    m_aAllSites.put (sSiteID, aSite);
    return EChange.CHANGED;
  }

  @NonNull
  public EChange removeSite (@NonNull final ICompanySite aSite)
  {
    ValueEnforcer.notNull (aSite, "Site");

    if (m_aHeadQuarterSite != null && aSite.equals (m_aHeadQuarterSite))
      m_aHeadQuarterSite = null;
    return m_aAllSites.removeObject (aSite.getID ());
  }

  @Nullable
  public ICompanySite getSiteOfID (@Nullable final String sSiteID)
  {
    return m_aAllSites.get (sSiteID);
  }

  @Nullable
  public ICompanySite getHeadQuarterSite ()
  {
    if (m_aHeadQuarterSite != null)
      return m_aHeadQuarterSite;
    return m_aAllSites.getFirstValue ();
  }

  @NonNull
  public EChange setHeadQuarterSite (@NonNull final CompanySite aHeadQuarterSite)
  {
    ValueEnforcer.notNull (aHeadQuarterSite, "HeadQuarterSite");

    if (!m_aAllSites.containsKey (aHeadQuarterSite.getID ()))
      throw new IllegalArgumentException ("Passed headquarter site does not yet belong to this company: " +
                                          aHeadQuarterSite);

    if (aHeadQuarterSite.equals (m_aHeadQuarterSite))
      return EChange.UNCHANGED;
    m_aHeadQuarterSite = aHeadQuarterSite;
    return EChange.CHANGED;
  }

  public boolean containsAtLeastOneNotDeletableSite ()
  {
    return CollectionFind.containsAny (m_aAllSites.values (), aSite -> !aSite.isDeletable ());
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final Company rhs = (Company) o;
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
    return new ToStringGenerator (null).append ("ID", m_sID)
                                       .appendIfNotNull ("publicName", m_sPublicName)
                                       .appendIfNotNull ("officialName", m_sOfficialName)
                                       .append ("allSites", m_aAllSites)
                                       .appendIfNotNull ("headquarter", m_aHeadQuarterSite)
                                       .getToString ();
  }
}
