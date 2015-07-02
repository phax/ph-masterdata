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

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.locale.country.CollatingComparatorLocaleCountry;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.string.StringHelper;
import com.helger.masterdata.locale.EContinent;
import com.helger.poi.excel.ExcelReadHelper;

public class MainReadCountry2Continent
{
  private static final Locale LOC = Locale.US;

  @Nonnull
  private static EContinent _findContinent (final String s)
  {
    for (final EContinent e : EContinent.values ())
      if (e.getDisplayText (LOC).equals (s))
        return e;
    throw new IllegalStateException ("Failed to resolve continent " + s);
  }

  @Nullable
  private static Locale _findCountry (final String s)
  {
    for (final Locale l : CountryCache.getInstance ().getAllCountryLocales ())
      if (l.getDisplayCountry (LOC).equals (s))
        return l;
    return null;
  }

  @Nullable
  private static Locale _findCountryComplex (final String s)
  {
    Locale ret = _findCountry (s);
    if (ret == null)
    {
      final int i = s.indexOf ('(');
      if (i >= 0)
        ret = _findCountry (s.substring (0, i).trim ());
    }
    if (ret == null)
    {
      final int i = s.indexOf ('[');
      if (i >= 0)
        ret = _findCountry (s.substring (0, i).trim ());
    }
    if (ret == null)
    {
      final int i = s.indexOf ('/');
      if (i >= 0)
      {
        ret = _findCountry (s.substring (0, i).trim ());
        if (ret == null)
          ret = _findCountry (s.substring (i + 1).trim ());
      }
    }
    if (ret == null)
    {
      // Cut out "(...)" in the middle
      final int i = s.indexOf ('(');
      final int j = s.indexOf (')');
      if (i >= 0 && j >= 0 && j < s.length () - 1)
        ret = _findCountry (s.substring (0, i).trim () + ' ' + s.substring (j + 1).trim ());
    }
    return ret;
  }

  public static void main (final String [] args)
  {
    final IReadableResource aRes = new ClassPathResource ("country2continent.xlsx");
    final Workbook aWB = ExcelReadHelper.readWorkbookFromInputStream (aRes);
    final Sheet aSheet = aWB.getSheetAt (0);
    // Skip one row
    int nRow = 1;
    int nNotFound = 0;
    final Map <Locale, EContinent> aMap = new TreeMap <Locale, EContinent> (new CollatingComparatorLocaleCountry (LOC));
    while (true)
    {
      final Row aRow = aSheet.getRow (nRow);
      if (aRow == null)
        break;
      final String sContinent = ExcelReadHelper.getCellValueString (aRow.getCell (0));
      if (StringHelper.hasNoText (sContinent))
        break;
      final EContinent eContinent = _findContinent (sContinent);
      final String sCountryName = ExcelReadHelper.getCellValueString (aRow.getCell (1));
      final Locale aCountry = _findCountryComplex (sCountryName);
      if (aCountry == null)
      {
        System.out.println ("No such country: '" + sCountryName + "'");
        ++nNotFound;
      }
      else
      {
        final EContinent eOld = aMap.put (aCountry, eContinent);
        if (eOld != null)
          System.out.println ("Country " +
                              aCountry.getDisplayCountry () +
                              " is assigned to " +
                              eContinent.getDisplayText (LOC) +
                              " and " +
                              eOld.getDisplayText (LOC));
      }

      ++nRow;
    }
    System.out.println ("Countries not found: " + nNotFound);

    for (final Map.Entry <Locale, EContinent> e : aMap.entrySet ())
    {
      System.out.println ("s_aMap.put (CountryCache.getCountry (\"" +
                          e.getKey ().getCountry () +
                          "\"), EContinent." +
                          e.getValue ().name () +
                          "),");
    }
  }
}
