/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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
package com.helger.masterdata.postal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.string.StringHelper;
import com.helger.masterdata.MasterDataLogger;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.serialize.MicroReader;

/**
 * Read postal code definitions from an XML resource.
 *
 * @author Philip Helger
 */
public class PostalCodeListReader
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
  private static ICommonsList <EPostalCodeFormatElement> _parseFormat (final String sFormat)
  {
    final ICommonsList <EPostalCodeFormatElement> ret = new CommonsArrayList <> ();
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
      final String sCountryName = eCountry.getAttributeValue (ATTR_NAME);
      final String sISO = eCountry.getAttributeValue (ATTR_ISO);
      final PostalCodeCountry aCountry = new PostalCodeCountry (sISO);

      // Read all postal code definitions
      for (final IMicroElement ePostalCode : eCountry.getAllChildElements (ELEMENT_POSTALCODES))
      {
        final String sValidFrom = ePostalCode.getAttributeValue (ATTR_VALIDFROM);
        final LocalDate aValidFrom = sValidFrom == null ? null
                                                        : DateTimeFormatter.ISO_LOCAL_DATE.parse (sValidFrom,
                                                                                                  LocalDate::from);
        final String sValidTo = ePostalCode.getAttributeValue (ATTR_VALIDTO);
        final LocalDate aValidTo = sValidTo == null ? null : DateTimeFormatter.ISO_LOCAL_DATE.parse (sValidTo,
                                                                                                     LocalDate::from);

        if (aValidFrom != null && aValidFrom.isAfter (aNow))
        {
          MasterDataLogger.getInstance ()
                          .info ("Ignoring some postal code definitions of " +
                                 sCountryName +
                                 " because they are valid from " +
                                 aValidFrom.toString ());
          continue;
        }
        if (aValidTo != null && aValidTo.isBefore (aNow))
        {
          MasterDataLogger.getInstance ()
                          .info ("Ignoring some postal code definitions of " +
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
