/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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

import com.helger.annotation.Nonnegative;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.state.EValidity;

import jakarta.annotation.Nonnull;

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
  public AbstractUPCEAN (@Nonnull final String sMsg, @Nonnull final EEANChecksumMode eMode)
  {
    m_sMsg = ValueEnforcer.notNull (sMsg, "Msg");
    m_eChecksumMode = ValueEnforcer.notNull (eMode, "ChecksumMode");
  }

  @Nonnull
  public String getMessage ()
  {
    return m_sMsg;
  }

  /**
   * Returns the current checksum mode.
   *
   * @return the checksum mode
   */
  @Nonnull
  public EEANChecksumMode getChecksumMode ()
  {
    return m_eChecksumMode;
  }

  /**
   * Validate this code.
   *
   * @return {@link EValidity#VALID} if the msg is valid,
   *         {@link EValidity#INVALID} otherwise.
   */
  @Nonnull
  protected abstract EValidity validate ();

  /**
   * Validates a UPC/EAN message.
   *
   * @param sMsg
   *        the message to validate
   * @return {@link EValidity#VALID} if the msg is valid,
   *         {@link EValidity#INVALID} otherwise.
   */
  @Nonnull
  protected static EValidity validateMessage (@Nonnull final String sMsg)
  {
    ValueEnforcer.notNull (sMsg, "Msg");

    return validateMessage (sMsg.toCharArray ());
  }

  /**
   * Validates a UPC/EAN/GTIN/GLN message.
   *
   * @param aChars
   *        the chars to validate
   * @return {@link EValidity#VALID} if the msg is valid,
   *         {@link EValidity#INVALID} otherwise.
   */
  @Nonnull
  protected static EValidity validateMessage (@Nonnull final char [] aChars)
  {
    ValueEnforcer.notNull (aChars, "Chars");

    for (final char c : aChars)
      if (c < '0' || c > '9')
        return EValidity.INVALID;
    return EValidity.VALID;
  }

  static int asInt (final char c)
  {
    return Character.digit (c, 10);
  }

  static char asChar (final int i)
  {
    return Character.forDigit (i, 10);
  }

  protected static int calcChecksum (@Nonnull final char [] aChars, @Nonnegative final int nLen)
  {
    int nChecksumBase = 0;
    // 13-1=12 chars: 1,3
    // 12-1=11 chars: 3,1
    // 8-1= 7 chars: 3,1
    int nFactor = (nLen % 2) == 0 ? 1 : 3;
    for (int i = 0; i < nLen; ++i)
    {
      nChecksumBase += asInt (aChars[i]) * nFactor;
      // Alternate between 1 and 3
      nFactor = 4 - nFactor;
    }

    // 1000 is larger as "18*9*3" (18 == length of SSCC; 9 == largest possible
    // number; 3 == largest multiplication factor)
    return (1000 - nChecksumBase) % 10;
  }

  /**
   * Calculates the check character for a given message
   *
   * @param sMsg
   *        the message
   * @param nLength
   *        The number of characters to be checked. Must be &ge; 0 and &lt;
   *        message.length
   * @return char the check character
   */
  protected static char calcChecksumChar (@Nonnull final String sMsg, @Nonnegative final int nLength)
  {
    ValueEnforcer.notNull (sMsg, "Msg");
    ValueEnforcer.isBetweenInclusive (nLength, "Length", 0, sMsg.length ());

    return asChar (calcChecksum (sMsg.toCharArray (), nLength));
  }
}
