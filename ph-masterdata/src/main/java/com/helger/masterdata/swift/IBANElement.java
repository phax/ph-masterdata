/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Represents a single IBAN element within a country's IBAN description.
 *
 * @author Philip Helger
 */
@Immutable
public final class IBANElement implements Serializable
{
  private final EIBANElementType m_eElementType;
  private final int m_nLength;

  public IBANElement (@Nonnull final EIBANElementType eElementType, @Nonnegative final int nLength)
  {
    m_eElementType = ValueEnforcer.notNull (eElementType, "ElementType");
    m_nLength = ValueEnforcer.isGT0 (nLength, "Length");
  }

  @Nonnull
  public EIBANElementType getElementType ()
  {
    return m_eElementType;
  }

  @Nonnegative
  public int getLength ()
  {
    return m_nLength;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final IBANElement rhs = (IBANElement) o;
    return m_eElementType.equals (rhs.m_eElementType) && m_nLength == rhs.m_nLength;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eElementType).append (m_nLength).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("elementType", m_eElementType).append ("length", m_nLength).getToString ();
  }
}
