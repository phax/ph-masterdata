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
package com.helger.validation.validator.string;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringParser;
import com.helger.commons.string.ToStringGenerator;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

@Immutable
public class DoubleMinMaxValidator extends AbstractStringValidator
{
  private final double m_dMinInclusive;
  private final double m_dMaxInclusive;

  /**
   * Ctor. Note: only one value may be infinity. If both values should be
   * infinity, use the {@link DoubleValidator} which imposes no limits!
   *
   * @param dMinInclusive
   *        Minimum allowed value. Must be &le; maximum value. Pass in
   *        {@link Double#NEGATIVE_INFINITY} to indicate no lower limit.
   * @param dMaxInclusive
   *        Maximum allowed value. Must be &ge; minimum value. Pass in
   *        {@link Double#POSITIVE_INFINITY} to indicate no upper limit.
   */
  public DoubleMinMaxValidator (final double dMinInclusive, final double dMaxInclusive)
  {
    final boolean bMinInfinity = Double.isInfinite (dMinInclusive);
    final boolean bMaxInfinity = Double.isInfinite (dMaxInclusive);
    if (bMinInfinity && bMaxInfinity)
      throw new IllegalArgumentException ("Both min and max are infinity! Use simple DoubleValidator instead");
    if (!bMinInfinity && !bMaxInfinity && dMinInclusive > dMaxInclusive)
      throw new IllegalArgumentException ("Minimum value is greater than maximum value!");
    m_dMinInclusive = dMinInclusive;
    m_dMaxInclusive = dMaxInclusive;
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String sValue)
  {
    final Double aValue = StringParser.parseDoubleObj (sValue);
    if (aValue == null)
      return new ValidationResultError (EStandardValidationErrorTexts.INVALID_DOUBLE);
    final double dValue = aValue.doubleValue ();
    if (!Double.isInfinite (m_dMinInclusive) && dValue < m_dMinInclusive)
    {
      return new ValidationResultError (EStandardValidationErrorTexts.INVALID_DOUBLE_RANGE,
                                        Double.toString (m_dMinInclusive),
                                        Double.toString (m_dMaxInclusive));
    }
    if (!Double.isInfinite (m_dMaxInclusive) && dValue > m_dMaxInclusive)
    {
      return new ValidationResultError (EStandardValidationErrorTexts.INVALID_DOUBLE_RANGE,
                                        Double.toString (m_dMinInclusive),
                                        Double.toString (m_dMaxInclusive));
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
    final DoubleMinMaxValidator rhs = (DoubleMinMaxValidator) o;
    return EqualsHelper.equals (m_dMinInclusive, rhs.m_dMinInclusive) &&
           EqualsHelper.equals (m_dMaxInclusive, rhs.m_dMaxInclusive);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ())
                            .append (m_dMinInclusive)
                            .append (m_dMaxInclusive)
                            .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("minIncl", m_dMinInclusive)
                            .append ("maxIncl", m_dMaxInclusive)
                            .toString ();
  }
}
