/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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
package com.helger.masterdata.checkdigit;

import java.util.Arrays;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.builder.IBuilder;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;

/**
 * A generic weighted modulus check digit algorithm for numeric payloads, as used by many national
 * identifiers. The payload digits are multiplied by the weights, the products (or the digit sums of
 * the products) are summed up and the remainder of the sum modulo the modulus is mapped to the
 * check character(s) via an {@link IRemainderToCheckChars}.<br>
 * The weights are applied either from the left (first weight for the leftmost payload digit) or
 * from the right (first weight for the rightmost payload digit). If the payload is longer than the
 * weights, the weights are repeated cyclically. The payload length itself is not restricted.<br>
 * Only the digits <code>0-9</code> are accepted in the payload.<br>
 * Instances are created via {@link #builder()}. This class is immutable and thread-safe.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@Immutable
public class WeightedModulusCheckDigit extends AbstractCheckDigitAlgorithm
{
  /**
   * Builder for {@link WeightedModulusCheckDigit}.
   *
   * @author Philip Helger
   */
  public static class Builder implements IBuilder <WeightedModulusCheckDigit>
  {
    private String m_sID;
    private int [] m_aWeights;
    private boolean m_bWeightsFromRight = false;
    private int m_nModulus = 0;
    private boolean m_bSumDigitsOfProducts = false;
    private int m_nCheckCharCount = 1;
    private IRemainderToCheckChars m_aRemainderMapper;

    protected Builder ()
    {}

    /**
     * @param s
     *        The ID of the algorithm. Required.
     * @return this for chaining
     */
    @NonNull
    public Builder id (@Nullable final String s)
    {
      m_sID = s;
      return this;
    }

    /**
     * @param a
     *        The weights to use. Required. Each weight must be &ge; 0. The array is copied.
     * @return this for chaining
     */
    @NonNull
    public Builder weights (final int @Nullable... a)
    {
      m_aWeights = a == null ? null : a.clone ();
      return this;
    }

    /**
     * @param b
     *        <code>true</code> to apply the first weight to the rightmost payload digit,
     *        <code>false</code> to apply it to the leftmost payload digit. Default is
     *        <code>false</code>.
     * @return this for chaining
     */
    @NonNull
    public Builder weightsFromRight (final boolean b)
    {
      m_bWeightsFromRight = b;
      return this;
    }

    /**
     * @param n
     *        The modulus. Required. Must be &ge; 2.
     * @return this for chaining
     */
    @NonNull
    public Builder modulus (final int n)
    {
      m_nModulus = n;
      return this;
    }

    /**
     * @param b
     *        <code>true</code> to sum up the digit sums of the products (e.g. 2 &middot; 7 = 14
     *        contributes 1 + 4 = 5, as in Luhn), <code>false</code> to sum up the products
     *        themselves. Default is <code>false</code>.
     * @return this for chaining
     */
    @NonNull
    public Builder sumDigitsOfProducts (final boolean b)
    {
      m_bSumDigitsOfProducts = b;
      return this;
    }

    /**
     * @param n
     *        The number of check characters the remainder mapper creates. Default is 1.
     * @return this for chaining
     */
    @NonNull
    public Builder checkCharCount (final int n)
    {
      m_nCheckCharCount = n;
      return this;
    }

    /**
     * @param a
     *        The mapping from the remainder to the check character(s). Required.
     * @return this for chaining
     */
    @NonNull
    public Builder remainderMapper (@Nullable final IRemainderToCheckChars a)
    {
      m_aRemainderMapper = a;
      return this;
    }

    @NonNull
    public WeightedModulusCheckDigit build ()
    {
      return new WeightedModulusCheckDigit (m_sID,
                                            m_aWeights,
                                            m_bWeightsFromRight,
                                            m_nModulus,
                                            m_bSumDigitsOfProducts,
                                            m_nCheckCharCount,
                                            m_aRemainderMapper);
    }
  }

  private final int [] m_aWeights;
  private final boolean m_bWeightsFromRight;
  private final int m_nModulus;
  private final boolean m_bSumDigitsOfProducts;
  private final IRemainderToCheckChars m_aRemainderMapper;

  protected WeightedModulusCheckDigit (@NonNull @Nonempty final String sID,
                                       final int @NonNull [] aWeights,
                                       final boolean bWeightsFromRight,
                                       final int nModulus,
                                       final boolean bSumDigitsOfProducts,
                                       @Nonnegative final int nCheckCharCount,
                                       @NonNull final IRemainderToCheckChars aRemainderMapper)
  {
    super (sID, nCheckCharCount);
    ValueEnforcer.notEmpty (aWeights, "Weights");
    for (final int nWeight : aWeights)
      ValueEnforcer.isGE0 (nWeight, "Weight");
    ValueEnforcer.isTrue (nModulus >= 2, "Modulus must be at least 2");
    ValueEnforcer.notNull (aRemainderMapper, "RemainderMapper");
    m_aWeights = aWeights.clone ();
    m_bWeightsFromRight = bWeightsFromRight;
    m_nModulus = nModulus;
    m_bSumDigitsOfProducts = bSumDigitsOfProducts;
    m_aRemainderMapper = aRemainderMapper;
  }

  private static int _getDigitSum (final long nValue)
  {
    int nRet = 0;
    long nRest = nValue;
    while (nRest > 0)
    {
      nRet += (int) (nRest % 10);
      nRest /= 10;
    }
    return nRet;
  }

  private int _getRemainder (@NonNull final String sPayload)
  {
    final int nLen = sPayload.length ();
    final int nWeightCount = m_aWeights.length;
    long nSum = 0;
    for (int i = 0; i < nLen; ++i)
    {
      final int nWeightIndex = (m_bWeightsFromRight ? nLen - 1 - i : i) % nWeightCount;
      final long nProduct = (long) m_aWeights[nWeightIndex] * (sPayload.charAt (i) - '0');
      nSum = (nSum + (m_bSumDigitsOfProducts ? _getDigitSum (nProduct) : nProduct)) % m_nModulus;
    }
    return (int) nSum;
  }

  /**
   * @return A copy of the weights. Never <code>null</code> nor empty.
   */
  @NonNull
  @ReturnsMutableCopy
  public final int [] getAllWeights ()
  {
    return m_aWeights.clone ();
  }

  /**
   * @return <code>true</code> if the first weight is applied to the rightmost payload digit.
   */
  public final boolean isWeightsFromRight ()
  {
    return m_bWeightsFromRight;
  }

  /**
   * @return The modulus. Always &ge; 2.
   */
  @Nonnegative
  public final int getModulus ()
  {
    return m_nModulus;
  }

  /**
   * @return <code>true</code> if the digit sums of the products are summed up.
   */
  public final boolean isSumDigitsOfProducts ()
  {
    return m_bSumDigitsOfProducts;
  }

  public boolean isValidPayload (@Nullable final String sPayload)
  {
    return CheckDigitHelper.isAllDigits (sPayload);
  }

  @Nullable
  public String calculateCheckChars (@Nullable final String sPayload)
  {
    if (!isValidPayload (sPayload))
      return null;

    final String ret = m_aRemainderMapper.getCheckChars (_getRemainder (sPayload));
    // Ensure the mapper result fits the declared length
    return ret != null && ret.length () == getCheckCharCount () ? ret : null;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", getID ())
                                       .append ("Weights", Arrays.toString (m_aWeights))
                                       .append ("WeightsFromRight", m_bWeightsFromRight)
                                       .append ("Modulus", m_nModulus)
                                       .append ("SumDigitsOfProducts", m_bSumDigitsOfProducts)
                                       .append ("CheckCharCount", getCheckCharCount ())
                                       .getToString ();
  }

  /**
   * @return A new builder. Never <code>null</code>.
   */
  @NonNull
  public static Builder builder ()
  {
    return new Builder ();
  }
}
