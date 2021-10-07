/*
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.csv.CSVReader;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.locale.LocaleCache;
import com.helger.commons.string.StringHelper;

public final class MainCountryCodeDialCodeReader
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCountryCodeDialCodeReader.class);

  public static void main (final String [] args) throws IOException
  {
    try (final CSVReader aCSVReader = new CSVReader (new ClassPathResource ("countrycode.org.csv").getReader (StandardCharsets.ISO_8859_1)))
    {
      aCSVReader.setSeparatorChar (';');
      for (int i = 0; i < 4; ++i)
        aCSVReader.readNext ();

      ICommonsList <String> aLine;
      while ((aLine = aCSVReader.readNext ()) != null)
      {
        // Country;ISO;Country;IDD;NDD
        final String sISO = StringHelper.replaceAll (aLine.get (1), (char) 65533, ' ').trim ();
        final String sCountryCode = StringHelper.replaceAll (aLine.get (2), (char) 65533, ' ').trim ();
        if (sISO.length () < 2 || sCountryCode.length () == 0)
          continue;

        final String sISO2 = sISO.substring (0, 2);
        Locale aCountry = null;
        for (final Locale aLocale : LocaleCache.getInstance ().getAllLocales ())
          if (aLocale.getCountry ().equals (sISO2))
          {
            aCountry = aLocale;
            break;
          }

        if (aCountry == null)
        {
          if (false)
            LOGGER.error ("Unknown locale: " + sISO);
          continue;
        }

        LOGGER.info ("<map key=\"" + sISO2 + "\" value=\"" + sCountryCode + "\" />");
      }
    }
  }
}
