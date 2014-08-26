/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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
import com.helger.commons.string.StringParser;

/**
 * UPC-E implementation (Universal product code)
 * 
 * @author Philip Helger
 */
public final class UPCE extends AbstractUPCEAN
{
  /**
   * Constructor
   * 
   * @param sMsg
   *        The code string.
   */
  public UPCE (@Nonnull final String sMsg)
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
  public UPCE (@Nonnull final String sMsg, @Nonnull final EEANChecksumMode eMode)
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
   * Compacts an UPC-A message to an UPC-E message if possible.
   * 
   * @param sMsg
   *        an UPC-A message
   * @return String the derived UPC-E message (with checksum), null if the
   *         message is a valid UPC-A message but no UPC-E representation is
   *         possible
   */
  @Nullable
  public static String getCompactMessage (final String sMsg)
  {
    UPCA.validateMessage (sMsg);
    final String sUPCA = UPCA.handleChecksum (sMsg, EEANChecksumMode.AUTO);
    final byte nNumberSystem = _extractNumberSystem (sUPCA);
    if (nNumberSystem != 0 && nNumberSystem != 1)
      return null;

    final byte nCheck = StringParser.parseByte (sUPCA.substring (11, 12), (byte) -1);
    if (nCheck == (byte) -1)
      return null;

    final StringBuilder aSB = new StringBuilder ();
    aSB.append (Byte.toString (nNumberSystem));
    final String manufacturer = sUPCA.substring (1, 1 + 5);
    final String product = sUPCA.substring (6, 6 + 5);
    // Rule 1
    String mtemp = manufacturer.substring (2, 2 + 3);
    String ptemp = product.substring (0, 0 + 2);
    if ("000|100|200".indexOf (mtemp) >= 0 && "00".equals (ptemp))
    {
      aSB.append (manufacturer.substring (0, 2));
      aSB.append (product.substring (2, 2 + 3));
      aSB.append (mtemp.charAt (0));
    }
    else
    {
      // Rule 2
      ptemp = product.substring (0, 0 + 3);
      if ("300|400|500|600|700|800|900".indexOf (mtemp) >= 0 && "000".equals (ptemp))
      {
        aSB.append (manufacturer.substring (0, 0 + 3));
        aSB.append (product.substring (3, 3 + 2));
        aSB.append ('3');
      }
      else
      {
        // Rule 3
        mtemp = manufacturer.substring (3, 3 + 2);
        ptemp = product.substring (0, 0 + 4);
        if ("10|20|30|40|50|60|70|80|90".indexOf (mtemp) >= 0 && "0000".equals (ptemp))
        {
          aSB.append (manufacturer.substring (0, 0 + 4));
          aSB.append (product.substring (4, 4 + 1));
          aSB.append ('4');
        }
        else
        {
          // Rule 4
          mtemp = manufacturer.substring (4, 4 + 1);
          ptemp = product.substring (4, 4 + 1);
          if (!"0".equals (mtemp) && "5|6|7|8|9".indexOf (ptemp) >= 0)
          {
            aSB.append (manufacturer);
            aSB.append (ptemp);
          }
          else
          {
            return null;
          }
        }
      }
    }
    aSB.append (Byte.toString (nCheck));
    return aSB.toString ();
  }

  /**
   * Expands an UPC-E message to an UPC-A message.
   * 
   * @param sMsg
   *        an UPC-E message (7 or 8 characters)
   * @return String the expanded UPC-A message (with checksum, 12 characters)
   */
  public static String getExpandedMessage (final String sMsg)
  {
    final char cCheck = sMsg.length () == 8 ? sMsg.charAt (7) : '\u0000';
    final String sUpce = sMsg.substring (0, 0 + 7);
    final byte nNumberSystem = _extractNumberSystem (sUpce);
    if (nNumberSystem != 0 && nNumberSystem != 1)
      throw new IllegalArgumentException ("Invalid UPC-E message: " + sMsg);

    final StringBuilder aSB = new StringBuilder ();
    aSB.append (Byte.toString (nNumberSystem));
    final byte nMode = StringParser.parseByte (sUpce.substring (6, 6 + 1), (byte) -1);
    if (nMode >= 0 && nMode <= 2)
    {
      aSB.append (sUpce.substring (1, 1 + 2));
      aSB.append (Byte.toString (nMode));
      aSB.append ("0000");
      aSB.append (sUpce.substring (3, 3 + 3));
    }
    else
      if (nMode == 3)
      {
        aSB.append (sUpce.substring (1, 1 + 3));
        aSB.append ("00000");
        aSB.append (sUpce.substring (4, 4 + 2));
      }
      else
        if (nMode == 4)
        {
          aSB.append (sUpce.substring (1, 1 + 4));
          aSB.append ("00000");
          aSB.append (sUpce.substring (5, 5 + 1));
        }
        else
          if (nMode >= 5 && nMode <= 9)
          {
            aSB.append (sUpce.substring (1, 1 + 5));
            aSB.append ("0000");
            aSB.append (Byte.toString (nMode));
          }
          else
          {
            // Shouldn't happen
            throw new IllegalArgumentException ("Internal error");
          }
    final String sUpcaFinished = aSB.toString ();
    final char cExpectedCheck = calcChecksumChar (sUpcaFinished, sUpcaFinished.length ());
    if (cCheck != '\u0000' && cCheck != cExpectedCheck)
      throw new IllegalArgumentException ("Invalid checksum. Expected " + cExpectedCheck + " but was " + cCheck);

    return sUpcaFinished + cExpectedCheck;
  }

  private static byte _extractNumberSystem (@Nonnull final String sMsg)
  {
    // Only 0 and 1 are considered valid!
    return StringParser.parseByte (sMsg.substring (0, 1), (byte) -1);
  }

  /**
   * Validates an UPC-E message. The message can also be UPC-A in which case the
   * message is compacted to a UPC-E message if possible. If it's not possible
   * an IllegalArgumentException is thrown
   * 
   * @param sMsg
   *        the message to validate
   * @return {@link EValidity}
   */
  @Nonnull
  public static EValidity validateMessage (@Nullable final String sMsg)
  {
    final int nLen = StringHelper.getLength (sMsg);
    if (nLen >= 7 && nLen <= 8)
    {
      final byte nNumberSystem = _extractNumberSystem (sMsg);
      if (nNumberSystem >= 0 && nNumberSystem <= 1)
        return EValidity.VALID;
    }
    return EValidity.INVALID;
  }

  public static String handleChecksum (@Nonnull final String sMsg, @Nonnull final EEANChecksumMode eMode) throws IllegalArgumentException
  {
    ValueEnforcer.notNull (sMsg, "Msg");
    ValueEnforcer.notNull (eMode, "ChecksumMode");

    EEANChecksumMode eRealMode = eMode;
    if (eRealMode == EEANChecksumMode.AUTO)
    {
      if (sMsg.length () == 7)
        eRealMode = EEANChecksumMode.ADD;
      else
        if (sMsg.length () == 8)
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
        if (sMsg.length () != 7)
          throw new IllegalArgumentException ("Message must be 7 characters long");
        return sMsg + getExpandedMessage (sMsg).charAt (11);
      }
      case CHECK:
      {
        if (sMsg.length () != 8)
          throw new IllegalArgumentException ("Message must be 8 characters long");
        final char cCheck = sMsg.charAt (7);
        final char cExpected = getExpandedMessage (sMsg).charAt (11);
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
