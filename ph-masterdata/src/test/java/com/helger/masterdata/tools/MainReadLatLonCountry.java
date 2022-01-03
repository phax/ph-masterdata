/*
 * Copyright (C) 2014-2022 Philip Helger (www.helger.com)
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
package com.helger.masterdata.tools;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.csv.CSVReader;
import com.helger.commons.io.file.FileHelper;
import com.helger.commons.string.StringParser;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;

/**
 * Read average county longitude and latitude from CSV. The CSV can be
 * downloaded and read as-is!
 *
 * @author Philip Helger
 */
public final class MainReadLatLonCountry
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainReadLatLonCountry.class);

  public static void main (final String [] args) throws IOException
  {
    final String sRevision = "20130209";
    final String sSource = "http://dev.maxmind.com/geoip/codes/country_latlon";

    try (final CSVReader aReader = new CSVReader (FileHelper.getReader (new File ("src/test/resources/country_latlon-" +
                                                                                  sRevision +
                                                                                  ".csv"),
                                                                        StandardCharsets.ISO_8859_1)))
    {
      // Skip one row
      aReader.readNext ();

      final IMicroDocument aDoc = new MicroDocument ();
      final IMicroElement eRoot = aDoc.appendElement ("root");
      final IMicroElement eHeader = eRoot.appendElement ("header");
      eHeader.appendElement ("source").appendText (sSource);
      eHeader.appendElement ("revision").appendText (sRevision);

      List <String> aLine;
      while ((aLine = aReader.readNext ()) != null)
      {
        final String sISO = aLine.get (0);
        final BigDecimal aLatitude = StringParser.parseBigDecimal (aLine.get (1));
        final BigDecimal aLongitude = StringParser.parseBigDecimal (aLine.get (2));
        eRoot.appendElement ("entry")
             .setAttribute ("id", sISO)
             .setAttributeWithConversion ("latitude", aLatitude)
             .setAttributeWithConversion ("longitude", aLongitude);
      }
      MicroWriter.writeToFile (aDoc, new File ("src/main/resources/codelists/latitude-longitude-country-" + sRevision + ".xml"));
    }
    LOGGER.info ("Done");
  }
}
