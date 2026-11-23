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

import com.helger.annotation.concurrent.Immutable;

/**
 * Internal helper for the check digit algorithms.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@Immutable
final class CheckDigitHelper
{
  /** The decimal digits, where the index is the value */
  static final String DIGITS = "0123456789";
  /**
   * The decimal digits and the uppercase letters, where the index is the value (<code>A</code> = 10
   * to <code>Z</code> = 35)
   */
  static final String DIGITS_AND_UPPERCASE_LETTERS = DIGITS + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private CheckDigitHelper ()
  {}

  static boolean isUniqueAlphabet (@NonNull final String sAlphabet)
  {
    for (int i = 0; i < sAlphabet.length (); ++i)
      if (sAlphabet.indexOf (sAlphabet.charAt (i), i + 1) >= 0)
        return false;
    return true;
  }

  /**
   * @param sValue
   *        Value to check. May be <code>null</code>.
   * @return <code>true</code> if the value is not empty and consists only of the digits 0-9.
   */
  static boolean isAllDigits (@Nullable final String sValue)
  {
    if (sValue == null || sValue.isEmpty ())
      return false;
    for (final char c : sValue.toCharArray ())
      if (c < '0' || c > '9')
        return false;
    return true;
  }

  /**
   * @param sValue
   *        Value to check. May be <code>null</code>.
   * @param sAlphabet
   *        The alphabet to use. May not be <code>null</code>.
   * @return <code>true</code> if the value is not empty and every character is contained in the
   *         alphabet.
   */
  static boolean isAllInAlphabet (@Nullable final String sValue, @NonNull final String sAlphabet)
  {
    if (sValue == null || sValue.isEmpty ())
      return false;
    for (final char c : sValue.toCharArray ())
      if (sAlphabet.indexOf (c) < 0)
        return false;
    return true;
  }
}
