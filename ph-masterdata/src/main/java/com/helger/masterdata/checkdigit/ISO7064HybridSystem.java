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
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;

/**
 * ISO/IEC 7064 hybrid system check character algorithm "MOD M+1,M" with a single check character.
 * The modulus M is the length of the alphabet, which is used for both the payload and the check
 * character. The value of a character is its index in the alphabet. The alphabet is
 * case-sensitive, because ISO 7064 defines its character sets with uppercase letters only.<br>
 * Calculation: start with <code>p = M</code>; for every character with value <code>a</code>:
 * <code>s = (p + a) mod M</code> (0 is replaced by M) and <code>p = 2s mod (M+1)</code>. The value
 * is valid if <code>s == 1</code> after the last (check) character.<br>
 * Note: hybrid systems detect all single substitution errors, but not all adjacent transpositions.
 * <br>
 * This class is immutable and thread-safe.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@Immutable
public class ISO7064HybridSystem extends AbstractCheckDigitAlgorithm
{
  /**
   * ISO 7064 MOD 11,10 with the alphabet <code>0-9</code>. Used e.g. by the German VAT number (USt-IdNr.)
   * and the Croatian OIB.
   */
  public static final ISO7064HybridSystem MOD_11_10 = new ISO7064HybridSystem ("iso7064-mod11-10",
                                                                              CheckDigitHelper.DIGITS);

  /**
   * ISO 7064 MOD 37,36 with the alphabet <code>0-9</code> and the uppercase letters
   * <code>A-Z</code> (values 10-35). Case-sensitive: lowercase letters are not accepted.
   */
  public static final ISO7064HybridSystem MOD_37_36 = new ISO7064HybridSystem ("iso7064-mod37-36",
                                                                              CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS);

  private final String m_sAlphabet;

  /**
   * Constructor
   *
   * @param sID
   *        The ID of the algorithm. May neither be <code>null</code> nor empty.
   * @param sAlphabet
   *        The alphabet for payload and check character. The index of each character is its value
   *        and the length is the modulus M. Must have at least 2 characters and must not contain
   *        duplicates.
   */
  public ISO7064HybridSystem (@NonNull @Nonempty final String sID, @NonNull @Nonempty final String sAlphabet)
  {
    super (sID, 1);
    ValueEnforcer.notEmpty (sAlphabet, "Alphabet");
    ValueEnforcer.isTrue (sAlphabet.length () >= 2, "Alphabet must have at least 2 characters");
    ValueEnforcer.isTrue (CheckDigitHelper.isUniqueAlphabet (sAlphabet), "Alphabet contains duplicate characters");
    m_sAlphabet = sAlphabet;
  }

  /**
   * @return The modulus M, which is the length of the alphabet. Always &ge; 2.
   */
  @Nonnegative
  public final int getModulus ()
  {
    return m_sAlphabet.length ();
  }

  /**
   * @return The alphabet for payload and check character. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public final String getAlphabet ()
  {
    return m_sAlphabet;
  }

  public boolean isValidPayload (@Nullable final String sPayload)
  {
    return CheckDigitHelper.isAllInAlphabet (sPayload, m_sAlphabet);
  }

  @Nullable
  public String calculateCheckChars (@Nullable final String sPayload)
  {
    if (!isValidPayload (sPayload))
      return null;

    final int nModulus = m_sAlphabet.length ();
    int nP = nModulus;
    for (final char c : sPayload.toCharArray ())
    {
      int nS = (nP + m_sAlphabet.indexOf (c)) % nModulus;
      if (nS == 0)
        nS = nModulus;
      nP = (2 * nS) % (nModulus + 1);
    }
    // Find c with (p + c) mod M == 1
    return Character.toString (m_sAlphabet.charAt ((nModulus + 1 - nP) % nModulus));
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", getID ()).append ("Alphabet", m_sAlphabet).getToString ();
  }
}
