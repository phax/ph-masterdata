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
package com.helger.masterdata.swift;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Represents the real value of an IBAN element with an IBAN number string.
 *
 * @author Philip Helger
 */
@Immutable
public final class IBANElementValue implements Serializable
{
  private final IBANElement m_aElement;
  private final String m_sValue;

  public IBANElementValue (@Nonnull final IBANElement aElement, @Nonnull final String sValue)
  {
    ValueEnforcer.notNull (aElement, "Element");
    ValueEnforcer.notNull (sValue, "Value");
    if (sValue.length () != aElement.getLength ())
      throw new IllegalArgumentException ("Value length mismatch. Having " +
                                          sValue.length () +
                                          " but expected " +
                                          aElement.getLength ());
    m_aElement = aElement;
    m_sValue = sValue;
  }

  @Nonnull
  public IBANElement getElement ()
  {
    return m_aElement;
  }

  @Nonnull
  public String getValue ()
  {
    return m_sValue;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final IBANElementValue rhs = (IBANElementValue) o;
    return m_aElement.equals (rhs.m_aElement) && m_sValue.equals (rhs.m_sValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aElement).append (m_sValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("element", m_aElement).append ("value", m_sValue).getToString ();
  }
}
