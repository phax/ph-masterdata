/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
package com.helger.validation.validator.string;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringParser;
import com.helger.commons.string.ToStringGenerator;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

@Immutable
public class LongMinMaxValidator extends AbstractStringValidator
{
  private final long m_nMinInclusive;
  private final long m_nMaxInclusive;

  /**
   * Ctor. Note: only one value may be infinity. If both values should be
   * infinity, use the {@link LongValidator} which imposes no limits!
   *
   * @param nMinInclusive
   *        Minimum allowed value. Must be &le; maximum value. Pass in
   *        {@link Long#MIN_VALUE} to indicate no lower limit.
   * @param nMaxInclusive
   *        Maximum allowed value. Must be &ge; minimum value. Pass in
   *        {@link Long#MAX_VALUE} to indicate no upper limit.
   */
  public LongMinMaxValidator (final long nMinInclusive, final long nMaxInclusive)
  {
    final boolean bMinInfinity = nMinInclusive == Long.MIN_VALUE;
    final boolean bMaxInfinity = nMaxInclusive == Long.MAX_VALUE;
    if (bMinInfinity && bMaxInfinity)
      throw new IllegalArgumentException ("Both min and max are infinity!");
    if (!bMinInfinity && !bMaxInfinity && nMinInclusive > nMaxInclusive)
      throw new IllegalArgumentException ("Minimum value is greater than maximum value!");
    m_nMinInclusive = nMinInclusive;
    m_nMaxInclusive = nMaxInclusive;
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String sValue)
  {
    final Long aValue = StringParser.parseLongObj (sValue);
    if (aValue == null)
      return new ValidationResultError (EStandardValidationErrorTexts.INVALID_INT);
    final long nValue = aValue.longValue ();
    if (m_nMinInclusive != Long.MIN_VALUE && nValue < m_nMinInclusive)
    {
      return new ValidationResultError (EStandardValidationErrorTexts.INVALID_INT_RANGE,
                                        Long.toString (m_nMinInclusive),
                                        Long.toString (m_nMaxInclusive));
    }
    if (m_nMaxInclusive != Long.MAX_VALUE && nValue > m_nMaxInclusive)
    {
      return new ValidationResultError (EStandardValidationErrorTexts.INVALID_INT_RANGE,
                                        Long.toString (m_nMinInclusive),
                                        Long.toString (m_nMaxInclusive));
    }
    return ValidationResultSuccess.getInstance ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final LongMinMaxValidator rhs = (LongMinMaxValidator) o;
    return m_nMinInclusive == rhs.m_nMinInclusive && m_nMaxInclusive == rhs.m_nMaxInclusive;
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ())
                            .append (m_nMinInclusive)
                            .append (m_nMaxInclusive)
                            .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("minIncl", m_nMinInclusive)
                            .append ("maxIncl", m_nMaxInclusive)
                            .toString ();
  }
}
