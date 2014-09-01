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
package com.helger.masterdata.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.helger.commons.charset.CCharset;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.impl.MicroDocument;
import com.helger.commons.microdom.serialize.MicroWriter;
import com.helger.commons.string.StringHelper;

/**
 * Source of the file: http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt
 * 
 * @author Philip Helger
 */
public class MainReadISO639_2CodeList
{
  public static void main (final String [] args) throws IOException
  {
    final String sRevision = "20130111";
    final BufferedReader aReader = new BufferedReader (new ClassPathResource ("ISO-639-2_utf-8.txt").getReader (CCharset.CHARSET_UTF_8_OBJ));
    final IMicroDocument aDoc = new MicroDocument ();
    final IMicroElement eRoot = aDoc.appendElement ("iso639-2");
    String sLine;
    // Skip the BOM!
    aReader.read ();
    while ((sLine = aReader.readLine ()) != null)
    {
      // An alpha-3 (bibliographic) code
      // an alpha-3 (terminologic) code (when given)
      // an alpha-2 code (when given)
      // an English name
      // a French name
      final String [] aParts = StringHelper.getExplodedArray ('|', sLine);
      if (aParts.length != 5)
        throw new IllegalStateException ();
      final String sAlpha3B = aParts[0];
      final String sAlpha3T = aParts[1];
      final String sAlpha2 = aParts[2];
      final String sEN = aParts[3];
      final String sFR = aParts[4];

      if (StringHelper.hasNoText (sAlpha3B))
        throw new IllegalArgumentException ("Alpha3B");
      if (StringHelper.hasNoText (sEN))
        throw new IllegalArgumentException ("EN");
      if (StringHelper.hasNoText (sFR))
        throw new IllegalArgumentException ("FR");

      // "Reserved for local use"
      if (sAlpha3B.equals ("qaa-qtz"))
        continue;

      final IMicroElement eItem = eRoot.appendElement ("item");
      eItem.setAttribute ("alpha3", sAlpha3B.toLowerCase (Locale.US));
      if (StringHelper.hasText (sAlpha3T))
        eItem.setAttribute ("alpha3t", sAlpha3T.toLowerCase (Locale.US));
      if (StringHelper.hasText (sAlpha2))
        eItem.setAttribute ("alpha2", sAlpha2.toLowerCase (Locale.US));
      eItem.setAttribute ("en", sEN);
      eItem.setAttribute ("fr", sFR);
    }
    MicroWriter.writeToFile (aDoc, new File ("src/main/resources/codelists/iso639-2-data-" + sRevision + ".xml"));
  }
}
