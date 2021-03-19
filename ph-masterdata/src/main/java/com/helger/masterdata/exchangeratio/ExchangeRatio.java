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
package com.helger.masterdata.exchangeratio;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Represents a single currency exchange ratio compared to a base currency (e.g.
 * EUR)
 *
 * @author Philip Helger
 */
@Immutable
@MustImplementEqualsAndHashcode
public class ExchangeRatio
{
  private final LocalDate m_aDate;
  private final BigDecimal m_aRatio;

  public ExchangeRatio (@Nonnull final LocalDate aDate, @Nonnull @Nonnegative final BigDecimal aRatio)
  {
    m_aDate = ValueEnforcer.notNull (aDate, "Date");
    m_aRatio = ValueEnforcer.isGT0 (aRatio, "Ratio");
  }

  @Nonnull
  public LocalDate getDate ()
  {
    return m_aDate;
  }

  @Nonnegative
  @Nonnull
  public BigDecimal getRatio ()
  {
    return m_aRatio;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ExchangeRatio rhs = (ExchangeRatio) o;
    return m_aDate.equals (rhs.m_aDate) && EqualsHelper.equals (m_aRatio, rhs.m_aRatio);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aDate).append (m_aRatio).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("date", m_aDate).append ("ratio", m_aRatio).getToString ();
  }
}
