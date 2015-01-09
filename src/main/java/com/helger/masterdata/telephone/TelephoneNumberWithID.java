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
package com.helger.masterdata.telephone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.id.IHasSimpleIntID;
import com.helger.commons.string.ToStringGenerator;

// ESCA-JAVA0116:
@MappedSuperclass
@Embeddable
@Access (value = AccessType.PROPERTY)
public class TelephoneNumberWithID extends TelephoneNumber implements IHasSimpleIntID
{
  public static final String FIELD_ID = "telid";

  private int m_nID;

  public TelephoneNumberWithID ()
  {}

  public TelephoneNumberWithID (@Nonnull final IReadonlyTelephoneNumber aBase)
  {
    super (aBase);
  }

  public TelephoneNumberWithID (@Nullable final ETelephoneType eType)
  {
    super (eType);
  }

  public TelephoneNumberWithID (@Nullable final ETelephoneType eType,
                                @Nullable final String sCountryCode,
                                @Nullable final String sAreaCode,
                                @Nullable final String sLine,
                                @Nullable final String sDirectDial)
  {
    super (eType, sCountryCode, sAreaCode, sLine, sDirectDial);
  }

  @Column (name = FIELD_ID)
  @Id
  @GeneratedValue (generator = "tel_gen")
  public int getID ()
  {
    return m_nID;
  }

  public void setID (final int nID)
  {
    m_nID = nID;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final TelephoneNumberWithID rhs = (TelephoneNumberWithID) o;
    return m_nID == rhs.m_nID;
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_nID).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("id", m_nID).toString ();
  }
}
