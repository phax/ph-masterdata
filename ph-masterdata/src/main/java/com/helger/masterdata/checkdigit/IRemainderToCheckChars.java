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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonnegative;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;

/**
 * Maps the remainder of a weighted sum to the check character(s). Used by
 * {@link WeightedModulusCheckDigit}. Implementations must be stateless and thread-safe.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@FunctionalInterface
public interface IRemainderToCheckChars
{
  /**
   * Get the check character(s) for the provided remainder.
   *
   * @param nRemainder
   *        The remainder of the weighted sum, in the range 0 to modulus - 1.
   * @return The check character(s) or <code>null</code> if no check character exists for this
   *         remainder (meaning the payload cannot get a valid check character).
   */
  @Nullable
  String getCheckChars (@Nonnegative int nRemainder);

  /**
   * @return A mapper where the check digit is the remainder itself. Remainders &gt; 9 have no
   *         check digit and lead to <code>null</code>.
   */
  @NonNull
  static IRemainderToCheckChars remainderAsDigit ()
  {
    return nRemainder -> nRemainder <= 9 ? Integer.toString (nRemainder) : null;
  }

  /**
   * Get a mapper where the check digit is <code>modulus - remainder</code>, so a value in the range
   * 1 to modulus. The values 0-9 are used as digits, the value 10 and 11 are mapped explicitly and
   * all other values have no check digit.<br>
   * Examples:
   * <ul>
   * <li><code>complement (10, "0", null)</code> is the classical Mod 10 <code>(10 - r) mod
   * 10</code></li>
   * <li><code>complement (11, "X", "0")</code> is the classical Mod 11 with <code>X</code></li>
   * <li><code>complement (11, null, "0")</code> is Mod 11 where 10 is invalid</li>
   * <li><code>complement (11, "0", "0")</code> is Mod 11 where 10 and 11 become <code>0</code></li>
   * </ul>
   *
   * @param nModulus
   *        The modulus. Must be &ge; 2.
   * @param sFor10
   *        The check character(s) for the value 10. May be <code>null</code> to indicate that
   *        there is no valid check character.
   * @param sFor11
   *        The check character(s) for the value 11. May be <code>null</code> to indicate that
   *        there is no valid check character.
   * @return The mapper and never <code>null</code>.
   */
  @NonNull
  static IRemainderToCheckChars complement (final int nModulus,
                                            @Nullable final String sFor10,
                                            @Nullable final String sFor11)
  {
    ValueEnforcer.isTrue (nModulus >= 2, "Modulus must be at least 2");
    return nRemainder -> {
      final int nValue = nModulus - nRemainder;
      if (nValue <= 9)
        return Integer.toString (nValue);
      if (nValue == 10)
        return sFor10;
      if (nValue == 11)
        return sFor11;
      return null;
    };
  }

  /**
   * Get a mapper where the check digits are <code>modulus - remainder</code>, formatted as two
   * digits with a leading zero. Values &gt; 99 have no check digits. Example:
   * <code>complementTwoDigits (97)</code> for <code>97 - (n mod 97)</code>.
   *
   * @param nModulus
   *        The modulus. Must be between 2 and 100.
   * @return The mapper and never <code>null</code>.
   */
  @NonNull
  static IRemainderToCheckChars complementTwoDigits (final int nModulus)
  {
    ValueEnforcer.isBetweenInclusive (nModulus, "Modulus", 2, 100);
    return nRemainder -> {
      final int nValue = nModulus - nRemainder;
      return nValue <= 99 ? StringHelper.getLeadingZero (nValue, 2) : null;
    };
  }
}
