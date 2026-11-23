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

import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonnegative;
import com.helger.base.id.IHasID;

/**
 * Base interface for all check digit algorithms. A value protected by a check digit algorithm
 * consists of a payload followed by {@link #getCheckCharCount()} check characters.<br>
 * All methods are tolerant: <code>null</code>, empty or otherwise invalid input never leads to an
 * exception, but to a <code>null</code> or <code>false</code> result.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
public interface ICheckDigitAlgorithm extends IHasID <String>
{
  /**
   * @return The number of check characters appended to the payload. Usually 1 or 2.
   */
  @Nonnegative
  int getCheckCharCount ();

  /**
   * Check if the provided payload can be handled by this algorithm.
   *
   * @param sPayload
   *        The payload to check. May be <code>null</code>.
   * @return <code>true</code> if the payload is not empty and every character of the payload is in
   *         the alphabet of the algorithm, <code>false</code> otherwise.
   */
  boolean isValidPayload (@Nullable String sPayload);

  /**
   * Calculate the check character(s) for the provided payload.
   *
   * @param sPayload
   *        The payload to calculate the check characters for. May be <code>null</code>.
   * @return The check character(s) with a length of exactly {@link #getCheckCharCount()}, or
   *         <code>null</code> if the payload is <code>null</code>, empty or invalid, or if the
   *         algorithm cannot produce a check character for this payload (e.g. a weighted Mod 11
   *         remainder of 10 that is defined as "invalid").
   */
  @Nullable
  String calculateCheckChars (@Nullable String sPayload);

  /**
   * Check if the provided value consists of a valid payload followed by the correct check
   * character(s).
   *
   * @param sValue
   *        The value to check. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> if it is <code>null</code>,
   *         empty or invalid. Never throws an exception.
   */
  boolean isValid (@Nullable String sValue);

  /**
   * Convenience method that appends the check character(s) to the provided payload.
   *
   * @param sPayload
   *        The payload to use. May be <code>null</code>.
   * @return The payload followed by the check character(s) or <code>null</code> if
   *         {@link #calculateCheckChars(String)} returned <code>null</code>.
   */
  @Nullable
  default String appendCheckChars (@Nullable final String sPayload)
  {
    final String sCheckChars = calculateCheckChars (sPayload);
    return sCheckChars == null ? null : sPayload + sCheckChars;
  }
}
