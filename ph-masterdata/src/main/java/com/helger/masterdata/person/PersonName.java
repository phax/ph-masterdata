/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.masterdata.person;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

public class PersonName implements IPersonName
{
  private ESalutation m_eSalutation;
  private String m_sPrefixTitle;
  private String m_sFirstName;
  private String m_sMiddleName;
  private String m_sLastName;
  private String m_sSuffixTitle;

  public PersonName ()
  {}

  public PersonName (@Nonnull final PersonName aBase)
  {
    ValueEnforcer.notNull (aBase, "Base");
    m_eSalutation = aBase.m_eSalutation;
    m_sPrefixTitle = aBase.m_sPrefixTitle;
    m_sFirstName = aBase.m_sFirstName;
    m_sMiddleName = aBase.m_sMiddleName;
    m_sLastName = aBase.m_sLastName;
    m_sSuffixTitle = aBase.m_sSuffixTitle;
  }

  public PersonName (@Nonnull final IPersonName aBase, @Nonnull final Locale aSortLocale)
  {
    ValueEnforcer.notNull (aBase, "Base");
    setSalutation (aBase.getSalutation ());
    setPrefixTitle (aBase.getPrefixTitle ());
    setFirstName (aBase.getFirstName (), aSortLocale);
    setMiddleName (aBase.getMiddleName (), aSortLocale);
    setLastName (aBase.getLastName (), aSortLocale);
    setSuffixTitle (aBase.getSuffixTitle ());
  }

  public PersonName (@Nullable final ESalutation eSalutation,
                     @Nullable final String sPrefixTitle,
                     @Nullable final String sFirstName,
                     @Nullable final String sMiddleName,
                     @Nullable final String sLastName,
                     @Nullable final String sSuffixTitle,
                     @Nonnull final Locale aSortLocale)
  {
    setSalutation (eSalutation);
    setPrefixTitle (sPrefixTitle);
    setFirstName (sFirstName, aSortLocale);
    setMiddleName (sMiddleName, aSortLocale);
    setLastName (sLastName, aSortLocale);
    setSuffixTitle (sSuffixTitle);
  }

  @Nullable
  public ESalutation getSalutation ()
  {
    return m_eSalutation;
  }

  @Nonnull
  public EChange setSalutation (@Nullable final ESalutation eSalutation)
  {
    if (EqualsHelper.equals (m_eSalutation, eSalutation))
      return EChange.UNCHANGED;
    m_eSalutation = eSalutation;
    return EChange.CHANGED;
  }

  @Nullable
  public String getPrefixTitle ()
  {
    return m_sPrefixTitle;
  }

  @Nonnull
  public EChange setPrefixTitle (@Nullable final String sPrefixTitle)
  {
    final String sRealPrefixTitle = sPrefixTitle;
    if (EqualsHelper.equals (m_sPrefixTitle, sRealPrefixTitle))
      return EChange.UNCHANGED;
    m_sPrefixTitle = sRealPrefixTitle;
    return EChange.CHANGED;
  }

  @Nullable
  public String getFirstName ()
  {
    return m_sFirstName;
  }

  @Nonnull
  public EChange setFirstName (@Nullable final String sFirstName, @Nonnull final Locale aSortLocale)
  {
    final String sRealFirstName = PersonNameHelper.unifyName (sFirstName, aSortLocale);
    if (EqualsHelper.equals (m_sFirstName, sRealFirstName))
      return EChange.UNCHANGED;
    m_sFirstName = sRealFirstName;
    return EChange.CHANGED;
  }

  @Nullable
  public String getMiddleName ()
  {
    return m_sMiddleName;
  }

  @Nonnull
  public EChange setMiddleName (@Nullable final String sMiddleName, @Nonnull final Locale aSortLocale)
  {
    final String sRealMiddleName = PersonNameHelper.unifyName (sMiddleName, aSortLocale);
    if (EqualsHelper.equals (m_sMiddleName, sRealMiddleName))
      return EChange.UNCHANGED;
    m_sMiddleName = sRealMiddleName;
    return EChange.CHANGED;
  }

  @Nullable
  public String getLastName ()
  {
    return m_sLastName;
  }

  @Nonnull
  public EChange setLastName (@Nullable final String sLastName, @Nonnull final Locale aSortLocale)
  {
    final String sRealLastName = PersonNameHelper.unifyName (sLastName, aSortLocale);
    if (EqualsHelper.equals (m_sLastName, sRealLastName))
      return EChange.UNCHANGED;
    m_sLastName = sRealLastName;
    return EChange.CHANGED;
  }

  @Nullable
  public String getSuffixTitle ()
  {
    return m_sSuffixTitle;
  }

  @Nonnull
  public EChange setSuffixTitle (@Nullable final String sSuffixTitle)
  {
    final String sRealSuffixTitle = sSuffixTitle;
    if (EqualsHelper.equals (m_sSuffixTitle, sRealSuffixTitle))
      return EChange.UNCHANGED;
    m_sSuffixTitle = sRealSuffixTitle;
    return EChange.CHANGED;
  }

  @Nonnull
  public PersonName getClone ()
  {
    return new PersonName (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final PersonName rhs = (PersonName) o;
    return EqualsHelper.equals (m_eSalutation, rhs.m_eSalutation) &&
           EqualsHelper.equals (m_sPrefixTitle, rhs.m_sPrefixTitle) &&
           EqualsHelper.equals (m_sFirstName, rhs.m_sFirstName) &&
           EqualsHelper.equals (m_sMiddleName, rhs.m_sMiddleName) &&
           EqualsHelper.equals (m_sLastName, rhs.m_sLastName) &&
           EqualsHelper.equals (m_sSuffixTitle, rhs.m_sSuffixTitle);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eSalutation)
                                       .append (m_sPrefixTitle)
                                       .append (m_sFirstName)
                                       .append (m_sMiddleName)
                                       .append (m_sLastName)
                                       .append (m_sSuffixTitle)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).appendIfNotNull ("salutation", m_eSalutation)
                                       .appendIfNotNull ("prefixTitle", m_sPrefixTitle)
                                       .appendIfNotNull ("firstName", m_sFirstName)
                                       .appendIfNotNull ("middleName", m_sMiddleName)
                                       .appendIfNotNull ("lastName", m_sLastName)
                                       .appendIfNotNull ("suffixTitle", m_sSuffixTitle)
                                       .getToString ();
  }
}
