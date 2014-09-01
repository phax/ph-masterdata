/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.state.EValidity;
import com.helger.commons.string.StringHelper;

/**
 * UPC-A implementation (Universal product code; =GTIN-12).
 * 
 * @author Philip Helger
 */
public final class UPCA extends AbstractUPCEAN
{
  /**
   * Constructor
   * 
   * @param sMsg
   *        The code string.
   */
  public UPCA (@Nonnull final String sMsg)
  {
    this (sMsg, DEFAULT_CHECKSUM_MODE);
  }

  /**
   * Constructor
   * 
   * @param sMsg
   *        The code string.
   * @param eMode
   *        the checksum mode
   */
  public UPCA (@Nonnull final String sMsg, @Nonnull final EEANChecksumMode eMode)
  {
    super (sMsg, eMode);
  }

  @Override
  @Nonnull
  public EValidity validate ()
  {
    return validateMessage (getMessage ());
  }

  /**
   * Validates a UPC-A message. The method throws IllegalArgumentExceptions if
   * an invalid message is passed.
   * 
   * @param sMsg
   *        the message to validate
   * @return {@link EValidity#VALID} if the msg is valid,
   *         {@link EValidity#INVALID} otherwise.
   */
  @Nonnull
  public static EValidity validateMessage (@Nullable final String sMsg)
  {
    final int nLen = StringHelper.getLength (sMsg);
    if (nLen >= 11 && nLen <= 12)
      if (AbstractUPCEAN.validateMessage (sMsg).isValid ())
        return EValidity.VALID;
    return EValidity.INVALID;
  }

  /**
   * Does checksum processing according to the checksum mode.
   * 
   * @param sMsg
   *        the message to process
   * @param eMode
   *        the checksum mode
   * @return the possibly modified message
   * @exception IllegalArgumentException
   *            In case of an internal inconsistency
   */
  public static String handleChecksum (@Nonnull final String sMsg, @Nonnull final EEANChecksumMode eMode) throws IllegalArgumentException
  {
    ValueEnforcer.notNull (sMsg, "Msg");
    ValueEnforcer.notNull (eMode, "ChecksumMode");

    EEANChecksumMode eRealMode = eMode;
    if (eRealMode == EEANChecksumMode.AUTO)
    {
      if (sMsg.length () == 11)
        eRealMode = EEANChecksumMode.ADD;
      else
        if (sMsg.length () == 12)
          eRealMode = EEANChecksumMode.CHECK;
        else
        {
          // Shouldn't happen because of validateMessage
          throw new IllegalArgumentException ("Failed to automatically detect the checksum mode");
        }
    }
    switch (eRealMode)
    {
      case ADD:
      {
        if (sMsg.length () != 11)
          throw new IllegalArgumentException ("Message must be 11 characters long");
        return sMsg + calcChecksumChar (sMsg, 11);
      }
      case CHECK:
      {
        if (sMsg.length () != 12)
          throw new IllegalArgumentException ("Message must be 12 characters long");
        final char cCheck = sMsg.charAt (11);
        final char cExpected = calcChecksumChar (sMsg, 11);
        if (cCheck != cExpected)
          throw new IllegalArgumentException ("Checksum is bad (" + cCheck + "). Expected: " + cExpected);
        return sMsg;
      }
      case IGNORE:
        return sMsg;
      default:
        throw new IllegalArgumentException ("Unknown checksum mode: " + eRealMode);
    }
  }
}
