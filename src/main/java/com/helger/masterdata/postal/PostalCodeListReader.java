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
package com.helger.masterdata.postal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.io.IReadableResource;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.serialize.MicroReader;
import com.helger.commons.string.StringHelper;
import com.helger.datetime.PDTFactory;
import com.helger.masterdata.MasterdataLogger;

// ESCA-JAVA0116:
/**
 * Read postal code definitions from an XML resource.
 * 
 * @author Philip Helger
 */
public final class PostalCodeListReader
{
  public static final String ELEMENT_ROOT = "root";
  public static final String ELEMENT_HEADER = "header";
  public static final String ELEMENT_SOURCE = "source";
  public static final String ELEMENT_REVISION = "revision";
  public static final String ELEMENT_BODY = "body";
  public static final String ELEMENT_COUNTRY = "country";
  public static final String ATTR_ISO = "iso";
  public static final String ATTR_NAME = "name";
  public static final String ELEMENT_POSTALCODES = "postalcodes";
  public static final String ATTR_VALIDFROM = "validfrom";
  public static final String ATTR_VALIDTO = "validto";
  public static final String ELEMENT_SPECIFIC = "specific";
  public static final String ELEMENT_FORMAT = "format";
  public static final String ELEMENT_NOTE = "note";

  private final PostalCodeManager m_aMgr;

  public PostalCodeListReader (@Nonnull final PostalCodeManager aMgr)
  {
    m_aMgr = ValueEnforcer.notNull (aMgr, "Mgr");
  }

  @Nonnull
  @ReturnsMutableCopy
  private static List <EPostalCodeFormatElement> _parseFormat (final String sFormat)
  {
    final List <EPostalCodeFormatElement> ret = new ArrayList <EPostalCodeFormatElement> ();
    int nIndex = 0;
    EPostalCodeFormatElement eElement;
    while (nIndex < sFormat.length ())
    {
      eElement = EPostalCodeFormatElement.getFromString (sFormat, nIndex);
      if (eElement == null)
        throw new IllegalArgumentException ("The format '" +
                                            sFormat +
                                            "' contains an illegal element at index " +
                                            nIndex);
      ret.add (eElement);
      nIndex += eElement.getTokenLength ();
    }
    return ret;
  }

  public void readFromFile (@Nonnull final IReadableResource aRes)
  {
    ValueEnforcer.notNull (aRes, "Resource");
    final IMicroDocument aDoc = MicroReader.readMicroXML (aRes);
    if (aDoc == null)
      throw new IllegalArgumentException ("Passed resource is not an XML file: " + aRes);

    final IMicroElement eBody = aDoc.getDocumentElement ().getFirstChildElement (ELEMENT_BODY);
    if (eBody == null)
      throw new IllegalArgumentException ("Missing body element in file " + aRes);

    final LocalDate aNow = PDTFactory.getCurrentLocalDate ();

    // Read all countries
    for (final IMicroElement eCountry : eBody.getAllChildElements (ELEMENT_COUNTRY))
    {
      final String sCountryName = eCountry.getAttribute (ATTR_NAME);
      final String sISO = eCountry.getAttribute (ATTR_ISO);
      final PostalCodeCountry aCountry = new PostalCodeCountry (sISO);

      // Read all postal code definitions
      for (final IMicroElement ePostalCode : eCountry.getAllChildElements (ELEMENT_POSTALCODES))
      {
        final String sValidFrom = ePostalCode.getAttribute (ATTR_VALIDFROM);
        final LocalDate aValidFrom = sValidFrom == null ? null : ISODateTimeFormat.date ().parseLocalDate (sValidFrom);
        final String sValidTo = ePostalCode.getAttribute (ATTR_VALIDTO);
        final LocalDate aValidTo = sValidTo == null ? null : ISODateTimeFormat.date ().parseLocalDate (sValidTo);

        if (aValidFrom != null && aValidFrom.isAfter (aNow))
        {
          MasterdataLogger.getInstance ().info ("Ignoring some postal code definitions of " +
                                                sCountryName +
                                                " because they are valid from " +
                                                aValidFrom.toString ());
          continue;
        }
        if (aValidTo != null && aValidTo.isBefore (aNow))
        {
          MasterdataLogger.getInstance ().info ("Ignoring some postal code definitions of " +
                                                sCountryName +
                                                " because they are valid until " +
                                                aValidTo.toString ());
          continue;
        }

        // Read all formats
        for (final IMicroElement eFormat : ePostalCode.getAllChildElements (ELEMENT_FORMAT))
        {
          final String sFormat = eFormat.getTextContent ();
          if (StringHelper.hasNoText (sFormat))
            throw new IllegalArgumentException ("The country " + sISO + " contains an empty postal code format!");

          // Parse into tokens
          final List <EPostalCodeFormatElement> aElements = _parseFormat (sFormat);
          if (aElements.isEmpty ())
            throw new IllegalStateException ("The country " + sISO + " contains an invalid format '" + sFormat + "'");

          aCountry.addFormat (new PostalCodeFormat (sISO, aElements));
        }

        // Is exactly one code present?
        for (final IMicroElement eOneCode : ePostalCode.getAllChildElements (ELEMENT_SPECIFIC))
          aCountry.addSpecificPostalCode (eOneCode.getTextContent ());

        // Is a note present
        final IMicroElement eNote = ePostalCode.getFirstChildElement (ELEMENT_NOTE);
        if (eNote != null)
          aCountry.setNote (eNote.getTextContent ());
      }

      if (aCountry.getFormatCount () == 0 && aCountry.getSpecificPostalCodeCount () == 0)
        throw new IllegalStateException ("Country " + sISO + " has no formats defined!");

      m_aMgr.addCountry (aCountry);
    }
  }
}
