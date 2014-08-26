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
package com.helger.masterdata.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.type.ObjectType;

// ESCA-JAVA0116:
/**
 * The default implementation of the {@link ICompany} interface.
 * 
 * @author Philip Helger
 */
public final class Company implements ICompany
{
  public static final ObjectType TYPE_COMPANY = new ObjectType ("company");

  public static final String FIELD_ID = "id";
  public static final String FIELD_PUBLICNAME = "pubname";
  public static final String FIELD_OFFICIALNAME = "offname";
  public static final String FIELD_STEHQ = "hqsite";

  private final String m_sID;
  private String m_sPublicName;
  private String m_sOfficialName;
  private final Map <String, ICompanySite> m_aAllSites = new HashMap <String, ICompanySite> ();
  private ICompanySite m_aHeadQuarterSite;

  public Company (@Nonnull @Nonempty final String sID)
  {
    m_sID = ValueEnforcer.notEmpty (sID, "ID");
  }

  @Nonnull
  public ObjectType getTypeID ()
  {
    return TYPE_COMPANY;
  }

  @Nonnull
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

  @Nonnull
  public EChange setPublicName (@Nullable final String sPublicName)
  {
    if (EqualsUtils.equals (m_sPublicName, sPublicName))
      return EChange.UNCHANGED;
    m_sPublicName = sPublicName;
    return EChange.CHANGED;
  }

  @Nullable
  public String getOfficialName ()
  {
    return m_sOfficialName;
  }

  @Nonnull
  public EChange setOfficialName (@Nullable final String sOfficialName)
  {
    if (EqualsUtils.equals (m_sOfficialName, sOfficialName))
      return EChange.UNCHANGED;
    m_sOfficialName = sOfficialName;
    return EChange.CHANGED;
  }

  @Nonnegative
  public int getSiteCount ()
  {
    return m_aAllSites.size ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public Collection <ICompanySite> getAllSites ()
  {
    return ContainerHelper.newList (m_aAllSites.values ());
  }

  @Nonnull
  @ReturnsMutableCopy
  public Collection <ICompanySite> getAllNonVirtualSites ()
  {
    final List <ICompanySite> ret = new ArrayList <ICompanySite> ();
    for (final ICompanySite aSite : m_aAllSites.values ())
      if (!aSite.isVirtualSite ())
        ret.add (aSite);
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public Collection <ICompanySite> getAllVirtualSites ()
  {
    final List <ICompanySite> ret = new ArrayList <ICompanySite> ();
    for (final ICompanySite aSite : m_aAllSites.values ())
      if (aSite.isVirtualSite ())
        ret.add (aSite);
    return ret;
  }

  @Nonnull
  public EChange addSite (@Nonnull final ICompanySite aSite)
  {
    ValueEnforcer.notNull (aSite, "Site");

    final String sSiteID = aSite.getID ();
    if (m_aAllSites.containsKey (sSiteID))
      return EChange.UNCHANGED;
    m_aAllSites.put (sSiteID, aSite);
    return EChange.CHANGED;
  }

  @Nonnull
  public EChange removeSite (@Nonnull final ICompanySite aSite)
  {
    ValueEnforcer.notNull (aSite, "Site");

    if (m_aHeadQuarterSite != null && aSite.equals (m_aHeadQuarterSite))
      m_aHeadQuarterSite = null;
    return EChange.valueOf (m_aAllSites.remove (aSite.getID ()) != null);
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
    if (m_aAllSites.size () == 1)
      return ContainerHelper.getFirstElement (m_aAllSites.values ());
    return null;
  }

  @Nonnull
  public EChange setHeadQuarterSite (@Nonnull final ICompanySite aHeadQuarterSite)
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
    for (final IReadonlyCompanySite aSite : m_aAllSites.values ())
      if (!aSite.isDeletable ())
        return true;
    return false;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof Company))
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
                                       .toString ();
  }
}
