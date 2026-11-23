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

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.CGlobal;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;

/**
 * ISO/IEC 7064 pure system check character algorithm "MOD M-r" (modulus M, radix r) with one or
 * two check characters. A value (payload followed by the check characters) is valid if
 * <code>&sum; a<sub>i</sub> &middot; r<sup>n-i</sup> mod M == 1</code>, where <code>a<sub>i</sub></code>
 * is the value of the character at position <code>i</code> (counting from 1 on the left) and
 * <code>n</code> is the total length.<br>
 * The value of a payload character is its index in the payload alphabet, the value of a check
 * character is its index in the check alphabet. All alphabets are case-sensitive, because ISO 7064
 * defines its character sets with uppercase letters only.<br>
 * The calculation uses a running remainder in <code>long</code> arithmetic, so the payload length
 * is not limited.<br>
 * This class is immutable and thread-safe.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@Immutable
public class ISO7064PureSystem extends AbstractCheckDigitAlgorithm
{
  /** The maximum supported modulus to avoid any arithmetic overflow */
  public static final int MAX_MODULUS = 1_000_000;
  /** The maximum supported radix to avoid any arithmetic overflow */
  public static final int MAX_RADIX = 100;

  /**
   * ISO 7064 MOD 11-2. The payload consists of the digits <code>0-9</code>. The single check
   * character is one of <code>0-9</code> or <code>X</code> (value 10). Used e.g. by ORCID and ISNI.
   * Case-sensitive: only the uppercase <code>X</code> is accepted.
   */
  public static final ISO7064PureSystem MOD_11_2 = new ISO7064PureSystem ("iso7064-mod11-2",
                                                                          11,
                                                                          2,
                                                                          1,
                                                                          CheckDigitHelper.DIGITS,
                                                                          CheckDigitHelper.DIGITS + "X",
                                                                          false);

  /**
   * ISO 7064 MOD 37-2. The payload consists of the digits <code>0-9</code> and the uppercase
   * letters <code>A-Z</code> (values 10-35). The single check character is one of
   * <code>0-9</code>, <code>A-Z</code> or <code>*</code> (value 36). Case-sensitive: lowercase
   * letters are not accepted.
   */
  public static final ISO7064PureSystem MOD_37_2 = new ISO7064PureSystem ("iso7064-mod37-2",
                                                                          37,
                                                                          2,
                                                                          1,
                                                                          CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS,
                                                                          CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS + "*",
                                                                          false);

  /**
   * ISO 7064 MOD 97-10 with two check digits. The payload consists of the digits <code>0-9</code>
   * only. Use {@link #MOD_97_10_ALPHA} if the payload may contain letters (e.g. IBAN).<br>
   * The calculated check digits are always in the range <code>02</code> to <code>98</code>
   * (<code>98 - (payload &middot; 100 mod 97)</code>), as used by the IBAN. Note that
   * {@link #isValid(String)} follows the ISO 7064 validation rule and therefore also accepts the
   * congruent check digits <code>99</code>, <code>00</code> and <code>01</code> instead of
   * <code>02</code>, <code>97</code> and <code>98</code>.
   */
  public static final ISO7064PureSystem MOD_97_10 = new ISO7064PureSystem ("iso7064-mod97-10",
                                                                           97,
                                                                           10,
                                                                           2,
                                                                           CheckDigitHelper.DIGITS,
                                                                           CheckDigitHelper.DIGITS,
                                                                           false);

  /**
   * MOD 97-10 as {@link #MOD_97_10}, but the payload may additionally contain the uppercase
   * letters <code>A-Z</code>, which are converted to the two-digit numbers 10-35 before the
   * calculation, as defined for the IBAN in ISO 13616 (and as used by the Leitweg-ID). This letter
   * conversion is not part of ISO 7064. Case-sensitive: lowercase letters are not accepted. The
   * check digits are always digits.<br>
   * Note: because a letter is converted to two digits, the ISO 7064 guarantees to detect all single
   * substitutions and all adjacent transpositions only hold for numeric payloads.
   */
  public static final ISO7064PureSystem MOD_97_10_ALPHA = new ISO7064PureSystem ("iso7064-mod97-10-alpha",
                                                                                 97,
                                                                                 10,
                                                                                 2,
                                                                                 CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS,
                                                                                 CheckDigitHelper.DIGITS,
                                                                                 true);

  private final int m_nModulus;
  private final int m_nRadix;
  private final String m_sPayloadAlphabet;
  private final String m_sCheckAlphabet;
  private final boolean m_bTwoDigitValues;

  /**
   * Constructor
   *
   * @param sID
   *        The ID of the algorithm. May neither be <code>null</code> nor empty.
   * @param nModulus
   *        The modulus M. Must be between 2 and {@link #MAX_MODULUS}.
   * @param nRadix
   *        The radix r. Must be between 2 and {@link #MAX_RADIX}.
   * @param nCheckCharCount
   *        The number of check characters. Must be 1 or 2. For 1 check character the check
   *        alphabet must have exactly M characters. For 2 check characters the check alphabet must
   *        have exactly r characters and M + 1 must be less than r<sup>2</sup>.
   * @param sPayloadAlphabet
   *        The payload alphabet. The index of each character is its value. May neither be
   *        <code>null</code> nor empty and must not contain duplicates.
   * @param sCheckAlphabet
   *        The check character alphabet. The index of each character is its value. May neither be
   *        <code>null</code> nor empty and must not contain duplicates.
   * @param bTwoDigitValues
   *        <code>true</code> if payload character values &ge; r are processed as two radix-r
   *        digits (the ISO 13616 letter conversion of the IBAN, where e.g. <code>A</code> becomes
   *        <code>10</code>), <code>false</code> if every payload character is processed as one
   *        value. If <code>true</code> the payload alphabet must not have more than r<sup>2</sup>
   *        characters.
   */
  public ISO7064PureSystem (@NonNull @Nonempty final String sID,
                            final int nModulus,
                            final int nRadix,
                            final int nCheckCharCount,
                            @NonNull @Nonempty final String sPayloadAlphabet,
                            @NonNull @Nonempty final String sCheckAlphabet,
                            final boolean bTwoDigitValues)
  {
    super (sID, nCheckCharCount);
    ValueEnforcer.isBetweenInclusive (nModulus, "Modulus", 2, MAX_MODULUS);
    ValueEnforcer.isBetweenInclusive (nRadix, "Radix", 2, MAX_RADIX);
    ValueEnforcer.isBetweenInclusive (nCheckCharCount, "CheckCharCount", 1, 2);
    ValueEnforcer.notEmpty (sPayloadAlphabet, "PayloadAlphabet");
    ValueEnforcer.notEmpty (sCheckAlphabet, "CheckAlphabet");
    ValueEnforcer.isTrue (CheckDigitHelper.isUniqueAlphabet (sPayloadAlphabet),
                          "PayloadAlphabet contains duplicate characters");
    ValueEnforcer.isTrue (CheckDigitHelper.isUniqueAlphabet (sCheckAlphabet),
                          "CheckAlphabet contains duplicate characters");
    if (nCheckCharCount == 1)
      ValueEnforcer.isTrue (sCheckAlphabet.length () == nModulus,
                            () -> "CheckAlphabet must have exactly " + nModulus + " characters");
    else
    {
      ValueEnforcer.isTrue (sCheckAlphabet.length () == nRadix,
                            () -> "CheckAlphabet must have exactly " + nRadix + " characters");
      ValueEnforcer.isTrue (nModulus + 1 < nRadix * nRadix,
                            () -> "Modulus " + nModulus + " is too large for two check characters with radix " + nRadix);
    }
    if (bTwoDigitValues)
      ValueEnforcer.isTrue (sPayloadAlphabet.length () <= nRadix * nRadix,
                            () -> "PayloadAlphabet may not have more than " + (nRadix * nRadix) + " characters");
    m_nModulus = nModulus;
    m_nRadix = nRadix;
    m_sPayloadAlphabet = sPayloadAlphabet;
    m_sCheckAlphabet = sCheckAlphabet;
    m_bTwoDigitValues = bTwoDigitValues;
  }

  private long _addValue (final long nRemainder, final int nValue)
  {
    if (m_bTwoDigitValues && nValue >= m_nRadix)
      return (nRemainder * m_nRadix * m_nRadix + nValue) % m_nModulus;
    return (nRemainder * m_nRadix + nValue) % m_nModulus;
  }

  /**
   * @return The running remainder of the payload or -1 if the payload contains an invalid
   *         character.
   */
  private long _getPayloadRemainder (@NonNull final String sPayload)
  {
    long nRemainder = 0;
    for (final char c : sPayload.toCharArray ())
    {
      final int nValue = m_sPayloadAlphabet.indexOf (c);
      if (nValue < 0)
        return -1;
      nRemainder = _addValue (nRemainder, nValue);
    }
    return nRemainder;
  }

  /**
   * @return The modulus M. Always &ge; 2.
   */
  @Nonnegative
  public final int getModulus ()
  {
    return m_nModulus;
  }

  /**
   * @return The radix r. Always &ge; 2.
   */
  @Nonnegative
  public final int getRadix ()
  {
    return m_nRadix;
  }

  /**
   * @return The payload alphabet. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public final String getPayloadAlphabet ()
  {
    return m_sPayloadAlphabet;
  }

  /**
   * @return The check character alphabet. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public final String getCheckAlphabet ()
  {
    return m_sCheckAlphabet;
  }

  /**
   * @return <code>true</code> if payload character values &ge; radix are processed as two radix
   *         digits (ISO 13616 letter conversion).
   */
  public final boolean isTwoDigitValues ()
  {
    return m_bTwoDigitValues;
  }

  public boolean isValidPayload (@Nullable final String sPayload)
  {
    return CheckDigitHelper.isAllInAlphabet (sPayload, m_sPayloadAlphabet);
  }

  /**
   * Get the remainder <code>&sum; a<sub>i</sub> &middot; r<sup>n-i</sup> mod M</code> of the
   * provided string, where every character is interpreted with the payload alphabet. In contrast to
   * {@link #getChecksum(String)} there is no separation between payload and check characters. If
   * the check alphabet is a subset of the payload alphabet (as for {@link #MOD_97_10} and
   * {@link #MOD_97_10_ALPHA}), the result for a complete value is identical to
   * {@link #getChecksum(String)}.
   *
   * @param sPayload
   *        The string to use. May be <code>null</code>.
   * @return The remainder in the range 0 to M-1, 0 for an empty string, or
   *         {@link CGlobal#ILLEGAL_UINT} if the string is <code>null</code> or contains a character
   *         that is not in the payload alphabet.
   */
  public int getPayloadRemainder (@Nullable final String sPayload)
  {
    if (sPayload == null)
      return CGlobal.ILLEGAL_UINT;
    final long nRemainder = _getPayloadRemainder (sPayload);
    return nRemainder < 0 ? CGlobal.ILLEGAL_UINT : (int) nRemainder;
  }

  /**
   * Get the ISO 7064 checksum of a complete value, consisting of the payload followed by
   * {@link #getCheckCharCount()} check characters. A value is valid if the checksum is 1.
   *
   * @param sValue
   *        The complete value. May be <code>null</code>.
   * @return The checksum in the range 0 to M-1, or {@link CGlobal#ILLEGAL_UINT} if the value is
   *         <code>null</code>, not longer than the number of check characters or contains an
   *         invalid character.
   */
  public int getChecksum (@Nullable final String sValue)
  {
    final int nCheckCharCount = getCheckCharCount ();
    if (sValue == null || sValue.length () <= nCheckCharCount)
      return CGlobal.ILLEGAL_UINT;

    final int nPayloadLen = sValue.length () - nCheckCharCount;
    long nRemainder = _getPayloadRemainder (sValue.substring (0, nPayloadLen));
    if (nRemainder < 0)
      return CGlobal.ILLEGAL_UINT;

    for (int i = nPayloadLen; i < sValue.length (); ++i)
    {
      final int nValue = m_sCheckAlphabet.indexOf (sValue.charAt (i));
      if (nValue < 0)
        return CGlobal.ILLEGAL_UINT;
      nRemainder = (nRemainder * m_nRadix + nValue) % m_nModulus;
    }
    return (int) nRemainder;
  }

  @Nullable
  public String calculateCheckChars (@Nullable final String sPayload)
  {
    if (!isValidPayload (sPayload))
      return null;

    final long nRemainder = _getPayloadRemainder (sPayload);
    if (getCheckCharCount () == 1)
    {
      // Find c with (remainder * r + c) mod M == 1
      final int nCheckValue = (int) ((m_nModulus + 1 - (nRemainder * m_nRadix) % m_nModulus) % m_nModulus);
      return Character.toString (m_sCheckAlphabet.charAt (nCheckValue));
    }

    // Find c1, c2 with (remainder * r^2 + c1 * r + c2) mod M == 1, in the range 2 to M + 1
    final int nCheckValue = (int) (m_nModulus + 1 - (nRemainder * m_nRadix * m_nRadix) % m_nModulus);
    return new String (new char [] { m_sCheckAlphabet.charAt (nCheckValue / m_nRadix),
                                     m_sCheckAlphabet.charAt (nCheckValue % m_nRadix) });
  }

  /**
   * {@inheritDoc}<br>
   * This implementation uses the ISO 7064 validation rule: the value is valid if
   * {@link #getChecksum(String)} returns 1.
   */
  @Override
  public boolean isValid (@Nullable final String sValue)
  {
    return getChecksum (sValue) == 1;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", getID ())
                                       .append ("Modulus", m_nModulus)
                                       .append ("Radix", m_nRadix)
                                       .append ("CheckCharCount", getCheckCharCount ())
                                       .append ("PayloadAlphabet", m_sPayloadAlphabet)
                                       .append ("CheckAlphabet", m_sCheckAlphabet)
                                       .append ("TwoDigitValues", m_bTwoDigitValues)
                                       .getToString ();
  }
}
