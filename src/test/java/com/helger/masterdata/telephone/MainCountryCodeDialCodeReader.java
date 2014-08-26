/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
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
package com.helger.masterdata.telephone;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import au.com.bytecode.opencsv.CSVReader;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.streams.StreamUtils;
import com.helger.commons.locale.LocaleCache;

public final class MainCountryCodeDialCodeReader
{
  public static void main (final String [] args) throws IOException
  {
    final CSVReader aReader = new CSVReader (new InputStreamReader (ClassPathResource.getInputStream ("countrycode.org.csv")),
                                             ';');
    try
    {
      for (int i = 0; i < 4; ++i)
        aReader.readNext ();

      String [] aLine;
      while ((aLine = aReader.readNext ()) != null)
      {
        // Country;ISO;Country;IDD;NDD
        final String sISO = aLine[1].replace ((char) 65533, ' ').trim ();
        final String sCountryCode = aLine[2].replace ((char) 65533, ' ').trim ();
        if (sISO.length () < 2 || sCountryCode.length () == 0)
          continue;

        final String sISO2 = sISO.substring (0, 2);
        Locale aCountry = null;
        for (final Locale aLocale : LocaleCache.getAllLocales ())
          if (aLocale.getCountry ().equals (sISO2))
          {
            aCountry = aLocale;
            break;
          }

        if (aCountry == null)
        {
          if (false)
            System.err.println ("Unknown locale: " + sISO);
          continue;
        }

        System.out.println ("<map key=\"" + sISO2 + "\" value=\"" + sCountryCode + "\" />");
      }
    }
    finally
    {
      StreamUtils.close (aReader);
    }
  }
}
