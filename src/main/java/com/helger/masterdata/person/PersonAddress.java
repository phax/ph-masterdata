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
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.address.AddressWithID;
import com.helger.masterdata.address.IReadonlyAddress;

/**
 * Person specific implementation
 * 
 * @author Philip Helger
 */
@Entity
@Access (value = AccessType.PROPERTY)
public class PersonAddress extends AddressWithID
{
  private Person m_aOwner;

  public PersonAddress ()
  {}

  public PersonAddress (@Nonnull final Person aOwner)
  {
    setOwner (aOwner);
  }

  public PersonAddress (@Nonnull final Person aOwner,
                        @Nonnull final IReadonlyAddress aBase,
                        @Nonnull final Locale aSortLocale)
  {
    super (aBase, aSortLocale);
    setOwner (aOwner);
  }

  @ManyToOne
  @PrimaryKeyJoinColumn
  @JoinColumn (name = "owner", nullable = false)
  @Nullable
  public Person getOwner ()
  {
    return m_aOwner;
  }

  public void setOwner (@Nonnull final Person aOwner)
  {
    ValueEnforcer.notNull (aOwner, "Owner");
    m_aOwner = aOwner;
  }

  @Transient
  @Nullable
  public String getOwnerID ()
  {
    return m_aOwner == null ? null : m_aOwner.getID ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final PersonAddress rhs = (PersonAddress) o;
    return EqualsUtils.equals (getOwnerID (), rhs.getOwnerID ());
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (getOwnerID ()).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("ownerID", getOwnerID ()).toString ();
  }
}
