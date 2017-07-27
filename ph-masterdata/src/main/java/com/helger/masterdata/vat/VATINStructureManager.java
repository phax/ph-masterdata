/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.masterdata.vat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.string.StringHelper;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.serialize.MicroReader;

/**
 * This class handles the different VATIN structures for different countries.
 *
 * @author Philip Helger
 */
public class VATINStructureManager
{
  public static final String DEFAULT_RESOURCE = "codelists/vatin-data.xml";

  private static final ICommonsList <VATINStructure> s_aList = new CommonsArrayList <> ();

  static
  {
    // Read all information from a file
    final IMicroDocument aDoc = MicroReader.readMicroXML (new ClassPathResource (DEFAULT_RESOURCE));
    final IMicroElement eRoot = aDoc.getDocumentElement ();
    for (final IMicroElement eVatin : eRoot.getAllChildElements ("vatin"))
    {
      // Read country and pattern
      final String sCountry = eVatin.getAttributeValue ("country");
      final String sPattern = eVatin.getAttributeValue ("pattern");

      // Read all examples
      final ICommonsList <String> aExamples = new CommonsArrayList <> ();
      for (final IMicroElement eExample : eVatin.getAllChildElements ("example"))
        aExamples.add (eExample.getTextContent ());

      // Add the structure
      s_aList.add (new VATINStructure (sCountry, sPattern, aExamples));
    }
  }

  private VATINStructureManager ()
  {}

  /**
   * Determine the structure for a given VATIN.
   *
   * @param sVATIN
   *        The VATIN to check
   * @return <code>null</code> if no VATIN structure was found for the passed
   *         VATIN.
   */
  @Nullable
  public static VATINStructure getFromValidVATIN (@Nullable final String sVATIN)
  {
    if (StringHelper.getLength (sVATIN) > 2)
      for (final VATINStructure aStructure : s_aList)
        if (aStructure.isValid (sVATIN))
          return aStructure;
    return null;
  }

  /**
   * Resolve the VATIN structure only from the country part of the given VATIN.
   * This should help indicate how the VATIN is valid.
   *
   * @param sVATIN
   *        The VATIN with at least 2 characters for the country code.
   * @return <code>null</code> if the passed string is shorter than 2 characters
   *         or if the passed VATIN country code is invalid/unknown.
   */
  @Nullable
  public static VATINStructure getFromVATINCountry (@Nullable final String sVATIN)
  {
    if (StringHelper.getLength (sVATIN) >= 2)
    {
      final String sCountry = sVATIN.substring (0, 2);
      for (final VATINStructure aStructure : s_aList)
        if (aStructure.getExamples ().get (0).substring (0, 2).equalsIgnoreCase (sCountry))
          return aStructure;
    }
    return null;
  }

  /**
   * Check if the passed VATIN is valid.
   *
   * @param sVATIN
   *        The VATIN to check
   * @return <code>true</code> if the passed VATIN is valid, <code>false</code>
   *         otherwise
   */
  public static boolean isValidVATIN (@Nullable final String sVATIN)
  {
    return getFromValidVATIN (sVATIN) != null;
  }

  /**
   * @return A list of all available VATIN structures
   */
  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <VATINStructure> getAllStructures ()
  {
    return s_aList.getClone ();
  }
}
