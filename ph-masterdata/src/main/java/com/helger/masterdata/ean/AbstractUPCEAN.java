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
package com.helger.masterdata.ean;

import java.io.Serializable;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonnegative;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.state.EValidity;
import com.helger.masterdata.checkdigit.GS1CheckDigit;

public abstract class AbstractUPCEAN implements Serializable
{
  protected static final EEANChecksumMode DEFAULT_CHECKSUM_MODE = EEANChecksumMode.AUTO;

  private final String m_sMsg;
  private final EEANChecksumMode m_eChecksumMode;

  /**
   * Main constructor
   *
   * @param sMsg
   *        The code string.
   * @param eMode
   *        the checksum mode
   */
  public AbstractUPCEAN (@NonNull final String sMsg, @NonNull final EEANChecksumMode eMode)
  {
    m_sMsg = ValueEnforcer.notNull (sMsg, "Msg");
    m_eChecksumMode = ValueEnforcer.notNull (eMode, "ChecksumMode");
  }

  @NonNull
  public String getMessage ()
  {
    return m_sMsg;
  }

  /**
   * Returns the current checksum mode.
   *
   * @return the checksum mode
   */
  @NonNull
  public EEANChecksumMode getChecksumMode ()
  {
    return m_eChecksumMode;
  }

  /**
   * Validate this code.
   *
   * @return {@link EValidity#VALID} if the msg is valid, {@link EValidity#INVALID} otherwise.
   */
  @NonNull
  protected abstract EValidity validate ();

  /**
   * Validates a UPC/EAN message.
   *
   * @param sMsg
   *        the message to validate
   * @return {@link EValidity#VALID} if the msg is valid, {@link EValidity#INVALID} otherwise.
   */
  @NonNull
  protected static EValidity validateMessage (@NonNull final String sMsg)
  {
    ValueEnforcer.notNull (sMsg, "Msg");

    return validateMessage (sMsg.toCharArray ());
  }

  /**
   * Validates a UPC/EAN/GTIN/GLN message.
   *
   * @param aChars
   *        the chars to validate
   * @return {@link EValidity#VALID} if the msg is valid, {@link EValidity#INVALID} otherwise.
   */
  @NonNull
  protected static EValidity validateMessage (@NonNull final char [] aChars)
  {
    ValueEnforcer.notNull (aChars, "Chars");

    for (final char c : aChars)
      if (c < '0' || c > '9')
        return EValidity.INVALID;
    return EValidity.VALID;
  }

  static char asChar (final int i)
  {
    return Character.forDigit (i, 10);
  }

  /**
   * Calculate the GS1 Mod 10 check digit of the first <code>nLen</code> characters.
   *
   * @param aChars
   *        The characters to use. May not be <code>null</code>.
   * @param nLen
   *        The number of characters to use. Must be between 1 and the number of characters.
   * @return The check digit in the range 0-9.
   * @throws IllegalArgumentException
   *         If the length is invalid or if the characters to use are not all ASCII digits 0-9
   */
  protected static int calcChecksum (@NonNull final char [] aChars, @Nonnegative final int nLen)
  {
    ValueEnforcer.notNull (aChars, "Chars");
    ValueEnforcer.isBetweenInclusive (nLen, "Length", 1, aChars.length);

    final String sPayload = new String (aChars, 0, nLen);
    final String sCheckChars = GS1CheckDigit.INSTANCE.calculateCheckChars (sPayload);
    if (sCheckChars == null)
      throw new IllegalArgumentException ("The message may only contain the digits 0-9: '" + sPayload + "'");
    return sCheckChars.charAt (0) - '0';
  }

  /**
   * Calculates the check character for a given message
   *
   * @param sMsg
   *        the message
   * @param nLength
   *        The number of characters to be checked. Must be &ge; 1 and &le; message length.
   * @return char the check character
   * @throws IllegalArgumentException
   *         If the length is invalid or if the characters to be checked are not all ASCII digits 0-9
   */
  protected static char calcChecksumChar (@NonNull final String sMsg, @Nonnegative final int nLength)
  {
    ValueEnforcer.notNull (sMsg, "Msg");
    ValueEnforcer.isBetweenInclusive (nLength, "Length", 1, sMsg.length ());

    return asChar (calcChecksum (sMsg.toCharArray (), nLength));
  }
}
