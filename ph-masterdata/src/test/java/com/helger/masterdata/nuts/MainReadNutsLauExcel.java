/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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
package com.helger.masterdata.nuts;

import java.io.File;
import java.util.Iterator;

import javax.annotation.Nullable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.iterate.IterableIterator;
import com.helger.commons.io.file.FileHelper;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.poi.excel.EExcelVersion;
import com.helger.poi.excel.ExcelReadHelper;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;

/**
 * Take the LAU Excel from
 * https://ec.europa.eu/eurostat/web/nuts/local-administrative-units and convert
 * to XML.
 *
 * @author Philip Helger
 */
public final class MainReadNutsLauExcel
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainReadNutsLauExcel.class);

  @Nullable
  private static String _getString (@Nullable final Cell aCell)
  {
    return StringHelper.trim (ExcelReadHelper.getCellValueString (aCell));
  }

  public static void main (final String [] args)
  {
    // This file contains the LAU to NUTS mapping
    final Workbook aWB = EExcelVersion.XLSX.readWorkbook (FileHelper.getBufferedInputStream (new File ("src/test/resources/nuts/EU-27-LAU-2021-NUTS-2021.xlsx")));

    final IMicroDocument aDoc = new MicroDocument ();
    final IMicroElement eRoot = aDoc.appendElement ("root");

    for (final Sheet aSheet : new IterableIterator <> (aWB.sheetIterator ()))
    {
      // Only consider sheets, that represent a country
      if (RegExHelper.stringMatchesPattern ("^[A-Z]{2}$", aSheet.getSheetName ()))
      {
        LOGGER.info ("Reading sheet " + aSheet.getSheetName ());

        final Iterator <Row> aRowIt = aSheet.rowIterator ();

        // Skip 1 header line
        for (int j = 0; j < 1; ++j)
          if (aRowIt.hasNext ())
            aRowIt.next ();

        // NUTS 3 CODE, LAU CODE, LAU NAME NATIONAL, LAU NAME LATIN
        eRoot.setAttribute ("version", "2021");
        while (aRowIt.hasNext ())
        {
          final Row aRow = aRowIt.next ();
          final String sNuts = _getString (aRow.getCell (0));
          final String sLau = _getString (aRow.getCell (1));
          final String sName = _getString (aRow.getCell (2));
          final String sLatinName = _getString (aRow.getCell (3));

          if (StringHelper.hasText (sLau))
          {
            final IMicroElement eItem = eRoot.appendElement ("item")
                                             .setAttribute ("nuts", sNuts)
                                             .setAttribute ("lau", sLau)
                                             .setAttribute ("name", sName);
            if (StringHelper.hasText (sLatinName) && !sLatinName.equals (sName))
              eItem.setAttribute ("latinName", sLatinName);
          }
        }
      }
    }

    MicroWriter.writeToFile (aDoc, new File ("src/main/resources/codelists/lau-nuts2021.xml"));
    LOGGER.info ("Wrote XML file");
  }
}
