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
package com.helger.masterdata.person;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.LocalDate;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.db.jpa.eclipselink.converter.JPAJodaLocalDateConverter;
import com.helger.masterdata.address.IReadonlyAddress;
import com.helger.masterdata.email.IReadonlyExtendedEmailAddress;
import com.helger.masterdata.telephone.IReadonlyTelephoneNumber;

@Entity
@Converter (name = "joda-localdate", converterClass = JPAJodaLocalDateConverter.class)
@MappedSuperclass
@Access (value = AccessType.PROPERTY)
public class Person implements IPerson
{
  public static final String FIELD_ID = "id";
  public static final String FIELD_GENDER = "gender";
  public static final int LENGTH_GENDER = 2;
  public static final String FIELD_BIRTHDAY = "birthday";

  private String m_sID;
  private EGender m_eGender;
  private PersonName m_aName;
  private LocalDate m_aBirthday;
  private PersonTelephoneNumber m_aTelephoneNumber;
  private PersonEmailAddress m_aEmailAddress;
  private PersonAddress m_aAddress;

  public Person ()
  {
    m_aName = new PersonName ();
  }

  public Person (@Nonnull final IReadonlyPerson aBase, @Nonnull final Locale aSortLocale)
  {
    ValueEnforcer.notNull (aBase, "Base");
    // do not copy the ID!
    setGender (aBase.getGender ());
    setName (aBase.getName (), aSortLocale);
    setBirthday (aBase.getBirthday ());
    setTelephoneNumber (aBase.getTelephoneNumber ());
    setEmailAddress (aBase.getEmailAddress ());
    setAddress (aBase.getAddress (), aSortLocale);
  }

  public Person (@Nullable final EGender eGender,
                 @Nullable final IReadonlyPersonName aName,
                 @Nullable final LocalDate aBirthday,
                 @Nullable final IReadonlyTelephoneNumber aTelephoneNumber,
                 @Nullable final IReadonlyExtendedEmailAddress aEmailAddress,
                 @Nullable final IReadonlyAddress aAddress,
                 @Nonnull final Locale aSortLocale)
  {
    setGender (eGender);
    setName (aName, aSortLocale);
    setBirthday (aBirthday);
    setTelephoneNumber (aTelephoneNumber);
    setEmailAddress (aEmailAddress);
    setAddress (aAddress, aSortLocale);
  }

  @Column (name = FIELD_ID)
  @Id
  @GeneratedValue (generator = "person_gen")
  @Nullable
  public String getID ()
  {
    return m_sID;
  }

  public void setID (@Nullable final String sID)
  {
    m_sID = sID;
  }

  @Column (name = FIELD_GENDER, length = LENGTH_GENDER)
  @Nullable
  public EGender getGender ()
  {
    return m_eGender;
  }

  @Nonnull
  public EChange setGender (@Nullable final EGender eGender)
  {
    if (EqualsUtils.equals (m_eGender, eGender))
      return EChange.UNCHANGED;
    m_eGender = eGender;
    return EChange.CHANGED;
  }

  @Embedded
  @Nullable
  public PersonName getName ()
  {
    return m_aName;
  }

  @Nonnull
  public EChange setName (@Nullable final PersonName aName)
  {
    if (EqualsUtils.equals (aName, m_aName))
      return EChange.UNCHANGED;
    m_aName = aName;
    return EChange.CHANGED;
  }

  @Nonnull
  @Transient
  public EChange setName (@Nullable final IReadonlyPersonName aName, @Nonnull final Locale aSortLocale)
  {
    PersonName aRealName = null;
    if (aName != null)
      aRealName = new PersonName (aName, aSortLocale);
    return setName (aRealName);
  }

  @Column (name = FIELD_BIRTHDAY)
  @Convert ("joda-localdate")
  @Nullable
  public LocalDate getBirthday ()
  {
    return m_aBirthday;
  }

  @Nonnull
  public EChange setBirthday (@Nullable final LocalDate aBirthday)
  {
    if (EqualsUtils.equals (m_aBirthday, aBirthday))
      return EChange.UNCHANGED;
    m_aBirthday = aBirthday;
    return EChange.CHANGED;
  }

  @OneToOne (mappedBy = "owner", cascade = CascadeType.ALL)
  @Nullable
  public PersonTelephoneNumber getTelephoneNumber ()
  {
    return m_aTelephoneNumber;
  }

  @Nonnull
  public EChange setTelephoneNumber (@Nullable final PersonTelephoneNumber aTelephoneNumber)
  {
    if (EqualsUtils.equals (m_aTelephoneNumber, aTelephoneNumber))
      return EChange.UNCHANGED;
    m_aTelephoneNumber = aTelephoneNumber;
    return EChange.CHANGED;
  }

  @Transient
  @Nonnull
  public EChange setTelephoneNumber (@Nullable final IReadonlyTelephoneNumber aTelephoneNumber)
  {
    PersonTelephoneNumber aPersonTelNo = null;
    if (aTelephoneNumber != null)
      aPersonTelNo = new PersonTelephoneNumber (this, aTelephoneNumber);
    return setTelephoneNumber (aPersonTelNo);
  }

  @OneToOne (mappedBy = "owner", cascade = CascadeType.ALL)
  @Nullable
  public PersonEmailAddress getEmailAddress ()
  {
    return m_aEmailAddress;
  }

  @Nonnull
  public EChange setEmailAddress (@Nullable final PersonEmailAddress aEmailAddress)
  {
    if (EqualsUtils.equals (m_aEmailAddress, aEmailAddress))
      return EChange.UNCHANGED;
    m_aEmailAddress = aEmailAddress;
    return EChange.CHANGED;
  }

  @Transient
  @Nonnull
  public EChange setEmailAddress (@Nullable final IReadonlyExtendedEmailAddress aEmailAddress)
  {
    PersonEmailAddress aPersonEmailAddress = null;
    if (aEmailAddress != null)
      aPersonEmailAddress = new PersonEmailAddress (this, aEmailAddress);
    return setEmailAddress (aPersonEmailAddress);
  }

  @OneToOne (mappedBy = "owner", cascade = CascadeType.ALL)
  @Nullable
  public PersonAddress getAddress ()
  {
    return m_aAddress;
  }

  @Nonnull
  public EChange setAddress (@Nullable final PersonAddress aAddress)
  {
    if (EqualsUtils.equals (m_aAddress, aAddress))
      return EChange.UNCHANGED;
    m_aAddress = aAddress;
    return EChange.CHANGED;
  }

  @Transient
  @Nonnull
  public EChange setAddress (@Nullable final IReadonlyAddress aAddress, @Nonnull final Locale aSortLocale)
  {
    PersonAddress aPersonAddress = null;
    if (aAddress != null)
      aPersonAddress = new PersonAddress (this, aAddress, aSortLocale);
    return setAddress (aPersonAddress);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final Person rhs = (Person) o;
    return EqualsUtils.equals (m_sID, rhs.m_sID) &&
           EqualsUtils.equals (m_eGender, rhs.m_eGender) &&
           EqualsUtils.equals (m_aName, rhs.m_aName) &&
           EqualsUtils.equals (m_aBirthday, rhs.m_aBirthday) &&
           EqualsUtils.equals (m_aTelephoneNumber, rhs.m_aTelephoneNumber) &&
           EqualsUtils.equals (m_aEmailAddress, rhs.m_aEmailAddress) &&
           EqualsUtils.equals (m_aAddress, rhs.m_aAddress);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID)
                                       .append (m_eGender)
                                       .append (m_aName)
                                       .append (m_aBirthday)
                                       .append (m_aTelephoneNumber)
                                       .append (m_aEmailAddress)
                                       .append (m_aAddress)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).appendIfNotNull ("id", m_sID)
                                       .appendIfNotNull ("gender", m_eGender)
                                       .appendIfNotNull ("name", m_aName)
                                       .appendIfNotNull ("birthday", m_aBirthday)
                                       .appendIfNotNull ("telephone", m_aTelephoneNumber)
                                       .appendIfNotNull ("email", m_aEmailAddress)
                                       .appendIfNotNull ("address", m_aAddress)
                                       .toString ();
  }
}
