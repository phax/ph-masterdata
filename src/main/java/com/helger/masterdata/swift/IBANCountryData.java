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
package com.helger.masterdata.swift;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.LocalDate;

import com.helger.commons.CGlobal;
import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.regex.RegExPool;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.commons.string.ToStringGenerator;
import com.helger.datetime.period.LocalDatePeriod;

/**
 * The IBAN country data defines a list of elements that are contained within
 * the IBAN number of that country.
 * 
 * @author Philip Helger
 */
public final class IBANCountryData extends LocalDatePeriod
{
  private final int m_nExpectedLength;
  private final Pattern m_aPattern;
  private final List <IBANElement> m_aElements;
  private final String m_sFixedCheckDigits;

  /**
   * @param nExpectedLength
   *        The total expected length. Serves mainly as a checksum field to
   *        check whether the length of the passed fields matches.
   * @param aPattern
   *        <code>null</code> or the RegEx pattern to valid values of this
   *        country.
   * @param sFixedCheckDigits
   *        <code>null</code> or fixed check digits (of length 2)
   * @param aValidFrom
   *        Validity start date. May be <code>null</code>.
   * @param aValidTo
   *        Validity end date. May be <code>null</code>.
   * @param aElements
   *        The IBAN elements for this country. May not be <code>null</code>.
   */
  public IBANCountryData (@Nonnegative final int nExpectedLength,
                          @Nullable final Pattern aPattern,
                          @Nullable final String sFixedCheckDigits,
                          @Nullable final LocalDate aValidFrom,
                          @Nullable final LocalDate aValidTo,
                          @Nonnull final List <IBANElement> aElements)
  {
    super (aValidFrom, aValidTo);
    ValueEnforcer.notNull (aElements, "Elements");
    if (sFixedCheckDigits != null && sFixedCheckDigits.length () != 2)
      throw new IllegalArgumentException ("Check digits must be length 2!");
    if (sFixedCheckDigits != null && !StringParser.isUnsignedInt (sFixedCheckDigits))
      throw new IllegalArgumentException ("Check digits must be all numeric!");

    m_nExpectedLength = nExpectedLength;
    m_aPattern = aPattern;
    m_aElements = ContainerHelper.newList (aElements);
    m_sFixedCheckDigits = sFixedCheckDigits;

    int nCalcedLength = 0;
    for (final IBANElement aChar : aElements)
      nCalcedLength += aChar.getLength ();
    if (nCalcedLength != nExpectedLength)
      throw new IllegalArgumentException ("Expected length=" + nExpectedLength + "; calced length=" + nCalcedLength);
  }

  /**
   * @return The length the complete IBAN string needs to have (incl. country
   *         code)
   */
  @Nonnegative
  public int getExpectedLength ()
  {
    return m_nExpectedLength;
  }

  public boolean hasPattern ()
  {
    return m_aPattern != null;
  }

  @Nullable
  public Pattern getPattern ()
  {
    return m_aPattern;
  }

  public boolean matchesPattern (@Nonnull final String sIBAN)
  {
    if (m_aPattern == null)
      return true;
    return m_aPattern.matcher (sIBAN).matches ();
  }

  /**
   * @return An list of all IBAN elements for this country.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <IBANElement> getElements ()
  {
    return ContainerHelper.newList (m_aElements);
  }

  public boolean hasFixedCheckDigits ()
  {
    return m_sFixedCheckDigits != null;
  }

  @Nullable
  public String getFixedCheckDigits ()
  {
    return m_sFixedCheckDigits;
  }

  /**
   * Parse a given IBAN number string and convert it to elements according to
   * this country's definition of IBAN numbers.
   * 
   * @param sIBAN
   *        The IBAN number string to parse. May not be <code>null</code>.
   * @return The list of parsed elements.
   */
  @Nonnull
  @ReturnsMutableCopy
  public List <IBANElementValue> parseToElementValues (@Nonnull final String sIBAN)
  {
    ValueEnforcer.notNull (sIBAN, "IBANString");

    final String sRealIBAN = IBANManager.unifyIBAN (sIBAN);
    if (sRealIBAN.length () != m_nExpectedLength)
      throw new IllegalArgumentException ("Passed IBAN has an invalid length. Expected " +
                                          m_nExpectedLength +
                                          " but found " +
                                          sRealIBAN.length ());

    final List <IBANElementValue> ret = new ArrayList <IBANElementValue> ();
    int nIndex = 0;
    for (final IBANElement aElement : m_aElements)
    {
      final String sIBANPart = sRealIBAN.substring (nIndex, nIndex + aElement.getLength ());
      ret.add (new IBANElementValue (aElement, sIBANPart));
      nIndex += aElement.getLength ();
    }
    return ret;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("elements", m_aElements)
                                       .append ("expectedLength", m_nExpectedLength)
                                       .toString ();
  }

  @Nonnull
  @ReturnsMutableCopy
  private static List <IBANElement> _parseElements (@Nonnull final String sDesc)
  {
    final List <IBANElement> aList = new ArrayList <IBANElement> ();
    // Always starts with the country code
    aList.add (new IBANElement (EIBANElementType.COUNTRY_CODE, 2));

    EIBANElementType eLastCharType = null;
    int nLastLength = 0;
    final int len = sDesc.length ();
    for (int i = 2; i < len; ++i)
    {
      final char c = sDesc.charAt (i);

      // ignore all whitespaces
      if (Character.isWhitespace (c))
        continue;

      // resolve current character to an element type
      final EIBANElementType eCharType = EIBANElementType.getElementTypeFromChar (c);
      if (eCharType == null)
        throw new IllegalArgumentException ("Illegal char '" + c + "' in description '" + sDesc + "'");

      if (eLastCharType == null || eLastCharType == eCharType)
      {
        // first group, or same as before
        eLastCharType = eCharType;
        nLastLength++;
      }
      else
      {
        // new group
        aList.add (new IBANElement (eLastCharType, nLastLength));
        eLastCharType = eCharType;
        nLastLength = 1;
      }
    }

    // add rest
    if (nLastLength > 0)
      aList.add (new IBANElement (eLastCharType, nLastLength));
    return aList;
  }

  @Nullable
  private static Pattern _parseLayout (@Nonnull @Nonempty final String sCountryCode,
                                       @Nonnegative final int nExpectedLength,
                                       @Nullable final String sFixedCheckDigits,
                                       @Nullable final String sLayout)
  {
    // E.g. Burkina Faso has no layout
    if (sLayout == null)
      return null;

    final StringBuilder aRegEx = new StringBuilder ();
    // Always start with country code
    // Depending on fixed check digits or not different check
    if (sFixedCheckDigits != null)
      aRegEx.append (Pattern.quote (sCountryCode + sFixedCheckDigits));
    else
    {
      aRegEx.append (Pattern.quote (sCountryCode));
      aRegEx.append ("[0-9]{2}");
    }

    int nLen = 4;
    for (final String sPart : StringHelper.getExploded (',', sLayout))
    {
      final String [] aParts = RegExHelper.getAllMatchingGroupValues ("([0-9]+)([anc])", sPart);
      if (aParts.length != 2)
        throw new IllegalArgumentException ("Failed to parse layout part '" + sPart + "'");
      final int nPartLen = StringParser.parseInt (aParts[0], CGlobal.ILLEGAL_UINT);
      if (nPartLen <= 0)
        throw new IllegalArgumentException ("Failed to parse layout part '" + sPart + "' - illegal numeric value");
      nLen += nPartLen;
      if (aParts[1].length () != 1)
        throw new IllegalArgumentException ("Failed to parse layout part '" + sPart + "' - type length is invalid");
      final char cType = aParts[1].charAt (0);
      if (cType == 'a')
        aRegEx.append ("[A-Z]{" + nPartLen + "}");
      else
        if (cType == 'n')
          aRegEx.append ("[0-9]{" + nPartLen + "}");
        else
          if (cType == 'c')
            aRegEx.append ("[a-zA-Z0-9]{" + nPartLen + "}");
          else
            throw new IllegalArgumentException ("Failed to parse layout part '" + sPart + "' - type is invalid");
    }
    if (nLen != nExpectedLength)
      throw new IllegalArgumentException ("Failed to parse layout - length mismatch. Having " +
                                          nLen +
                                          " but expected " +
                                          nExpectedLength);

    return RegExPool.getPattern (aRegEx.toString ());
  }

  /**
   * This method is used to create an instance of this class from a string
   * representation.
   * 
   * @param sCountryCode
   *        Country code to use. Neither <code>null</code> nor empty.
   * @param nExpectedLength
   *        The expected length having only validation purpose.
   * @param sLayout
   *        <code>null</code> or the layout descriptor
   * @param sFixedCheckDigits
   *        <code>null</code> or fixed check digits (of length 2)
   * @param aValidFrom
   *        Validity start date. May be <code>null</code>.
   * @param aValidTo
   *        Validity end date. May be <code>null</code>.
   * @param sDesc
   *        The string description of this country data. May not be
   *        <code>null</code>.
   * @return The parsed county data.
   */
  @Nonnull
  public static IBANCountryData createFromString (@Nonnull @Nonempty final String sCountryCode,
                                                  @Nonnegative final int nExpectedLength,
                                                  @Nullable final String sLayout,
                                                  @Nullable final String sFixedCheckDigits,
                                                  @Nullable final LocalDate aValidFrom,
                                                  @Nullable final LocalDate aValidTo,
                                                  @Nonnull final String sDesc)
  {
    ValueEnforcer.notEmpty (sDesc, "Desc");
    if (sDesc.length () < 4)
      throw new IllegalArgumentException ("Cannot converted passed string because it is too short!");

    final List <IBANElement> aList = _parseElements (sDesc);

    final Pattern aPattern = _parseLayout (sCountryCode, nExpectedLength, sFixedCheckDigits, sLayout);

    // And we're done
    try
    {
      return new IBANCountryData (nExpectedLength, aPattern, sFixedCheckDigits, aValidFrom, aValidTo, aList);
    }
    catch (final IllegalArgumentException ex)
    {
      throw new IllegalArgumentException ("Failed to parse '" + sDesc + "': " + ex.getMessage ());
    }
  }
}
