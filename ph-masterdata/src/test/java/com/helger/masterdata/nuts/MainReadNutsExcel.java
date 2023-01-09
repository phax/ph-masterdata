/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.io.file.FileHelper;
import com.helger.poi.excel.EExcelVersion;
import com.helger.poi.excel.ExcelReadHelper;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;

/**
 * Take the NUTS Excel from https://ec.europa.eu/eurostat/web/nuts/background
 * and convert it to an XML
 *
 * @author Philip Helger
 */
public final class MainReadNutsExcel
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainReadNutsExcel.class);

  public static void main (final String [] args)
  {
    final Workbook aWB = EExcelVersion.XLSX.readWorkbook (FileHelper.getBufferedInputStream (new File ("src/test/resources/nuts/NUTS2021.xlsx")));
    final Sheet aSheet = aWB.getSheet ("NUTS & SR 2021");

    // Code 2021 Country NUTS level 1 NUTS level 2 NUTS level 3 NUTS level
    // Country order Region order
    final Iterator <Row> aRowIt = aSheet.rowIterator ();

    // Skip 1 header line
    for (int j = 0; j < 1; ++j)
      if (aRowIt.hasNext ())
        aRowIt.next ();

    final IMicroDocument aDoc = new MicroDocument ();
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", "nuts2021");
    while (aRowIt.hasNext ())
    {
      final Row aRow = aRowIt.next ();
      final String sID = aRow.getCell (0).getStringCellValue ();
      final ENutsLevel eLevel = ENutsLevel.getFromLengthOrNull (sID.length ());
      if (eLevel == null)
        break;

      final int nNameIdx = 1 + eLevel.getCharCount () - 2;
      final String sName = ExcelReadHelper.getCellValueString (aRow.getCell (nNameIdx));
      final String sLatinName = ExcelReadHelper.getCellValueString (aRow.getCell (nNameIdx + 8));
      final int nCountryOrdinal = ExcelReadHelper.getCellValueNumber (aRow.getCell (6)).intValue ();
      final int nRegionOrdinal = ExcelReadHelper.getCellValueNumber (aRow.getCell (7)).intValue ();

      // Names
      eRoot.appendElement ("item")
           .setAttribute ("id", sID)
           .setAttribute ("name", sName)
           .setAttribute ("latinName", sLatinName)
           .setAttribute ("countryOrd", nCountryOrdinal)
           .setAttribute ("regionOrd", nRegionOrdinal);
    }

    MicroWriter.writeToFile (aDoc, new File ("src/main/resources/codelists/nuts2021.xml"));
    LOGGER.info ("Wrote XML file");
  }
}
