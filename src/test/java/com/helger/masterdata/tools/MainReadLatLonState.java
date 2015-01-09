/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.helger.commons.charset.CCharset;
import com.helger.commons.io.file.FileUtils;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.impl.MicroDocument;
import com.helger.commons.microdom.serialize.MicroWriter;
import com.helger.commons.string.StringParser;

/**
 * Read average county longitude and latitude from CSV. The CSV can be
 * downloaded and read as-is!
 * 
 * @author Philip Helger
 */
public final class MainReadLatLonState
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainReadLatLonState.class);

  public static void main (final String [] args) throws IOException
  {
    final String sRevision = "20130209";
    final String sSource = "http://dev.maxmind.com/geoip/codes/state_latlon";

    final CSVReader aReader = new CSVReader (FileSystemResource.getReader (new File ("src/test/resources/state_latlon-" +
                                                                                     sRevision +
                                                                                     ".csv"),
                                                                           CCharset.CHARSET_ISO_8859_1_OBJ));
    // Skip one row
    aReader.readNext ();

    final IMicroDocument aDoc = new MicroDocument ();
    final IMicroElement eRoot = aDoc.appendElement ("root");
    final IMicroElement eHeader = eRoot.appendElement ("header");
    eHeader.appendElement ("source").appendText (sSource);
    eHeader.appendElement ("revision").appendText (sRevision);

    String [] aLine;
    while ((aLine = aReader.readNext ()) != null)
    {
      final String sISO = aLine[0];
      final BigDecimal aLatitude = StringParser.parseBigDecimal (aLine[1]);
      final BigDecimal aLongitude = StringParser.parseBigDecimal (aLine[2]);
      eRoot.appendElement ("entry")
           .setAttribute ("id", sISO)
           .setAttributeWithConversion ("latitude", aLatitude)
           .setAttributeWithConversion ("longitude", aLongitude);
    }
    aReader.close ();
    MicroWriter.writeToStream (aDoc,
                               FileUtils.getOutputStream ("src/main/resources/codelists/latitude-longitude-us-" +
                                                          sRevision +
                                                          ".xml"));
    s_aLogger.info ("Done");
  }
}
