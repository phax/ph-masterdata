/**
 * Copyright (C) 2014-2019 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.telephone.ITelephoneNumber;
import com.helger.masterdata.telephone.ITelephoneType;
import com.helger.masterdata.telephone.TelephoneNumber;

/**
 * Person specific implementation
 *
 * @author Philip Helger
 */
public class PersonTelephoneNumber extends TelephoneNumber
{
  private Person m_aOwner;

  public PersonTelephoneNumber ()
  {}

  public PersonTelephoneNumber (@Nonnull final Person aOwner)
  {
    setOwner (aOwner);
  }

  public PersonTelephoneNumber (@Nonnull final Person aOwner, @Nonnull final ITelephoneNumber aBase)
  {
    super (aBase);
    setOwner (aOwner);
  }

  public PersonTelephoneNumber (@Nonnull final Person aOwner,
                                @Nullable final ITelephoneType aType,
                                @Nullable final String sCountryCode,
                                @Nullable final String sAreaCode,
                                @Nullable final String sLine,
                                @Nullable final String sDirectDial)
  {
    super (aType, sCountryCode, sAreaCode, sLine, sDirectDial);
    setOwner (aOwner);
  }

  @Nullable
  public Person getOwner ()
  {
    return m_aOwner;
  }

  public final void setOwner (@Nonnull final Person aOwner)
  {
    ValueEnforcer.notNull (aOwner, "Owner");
    m_aOwner = aOwner;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final PersonTelephoneNumber rhs = (PersonTelephoneNumber) o;
    return EqualsHelper.equals (m_aOwner, rhs.m_aOwner);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_aOwner).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("owner", m_aOwner).getToString ();
  }
}
