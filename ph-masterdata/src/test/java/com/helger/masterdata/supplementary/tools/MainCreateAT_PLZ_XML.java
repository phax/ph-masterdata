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
package com.helger.masterdata.supplementary.tools;

import java.io.File;
import java.time.LocalDate;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.datetime.PDTFromString;
import com.helger.commons.io.file.FileHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.masterdata.austria.EAustriaState;
import com.helger.poi.excel.EExcelVersion;
import com.helger.poi.excel.ExcelReadHelper;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;

public class MainCreateAT_PLZ_XML
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreateAT_PLZ_XML.class);

  public static void main (final String [] args) throws Exception
  {
    // Source:
    // https://www.post.at/geschaeftlich_werben_produkte_und_services_adressen_postlexikon.php
    final File fSrc = new File ("src/test/resources/at_plz/PLZ-Verzeichnis-01062020.xls").getAbsoluteFile ();
    final Workbook aWB = EExcelVersion.XLS.readWorkbook (FileHelper.getInputStream (fSrc));
    final IMicroDocument aDoc = new MicroDocument ();
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", fSrc.getName ());

    // Check whether all required sheets are present
    final Sheet aExcelSheet = aWB.getSheetAt (0);
    // Skip 1 row
    int nRowIndex = 1;
    while (true)
    {
      // Read a single excel row
      final org.apache.poi.ss.usermodel.Row aExcelRow = aExcelSheet.getRow (nRowIndex++);
      if (aExcelRow == null)
        break;

      // read fields
      final String sPLZ = ExcelReadHelper.getCellValueString (aExcelRow.getCell (0));
      final String sCity = ExcelReadHelper.getCellValueString (aExcelRow.getCell (1));
      final String sState = ExcelReadHelper.getCellValueString (aExcelRow.getCell (2));
      final String sValidFrom = ExcelReadHelper.getCellValueString (aExcelRow.getCell (3));
      final String sValidTo = ExcelReadHelper.getCellValueString (aExcelRow.getCell (4));
      final String sNamePLZTyp = ExcelReadHelper.getCellValueString (aExcelRow.getCell (5));
      final String sInternExtern = ExcelReadHelper.getCellValueString (aExcelRow.getCell (6));
      final String sAddressable = ExcelReadHelper.getCellValueString (aExcelRow.getCell (7));
      final String sPostfach = ExcelReadHelper.getCellValueString (aExcelRow.getCell (8));

      final int nPLZ = StringParser.parseInt (sPLZ, 0);
      if (nPLZ < 1000 || nPLZ > 9999)
        throw new IllegalStateException ("Invalid PLZ: " + sPLZ);

      final EAustriaState eState = ArrayHelper.findFirst (EAustriaState.values (), x -> x.getPostID ().equals (sState));
      if (eState == null)
        throw new IllegalStateException ("Failed to resolve state: " + sState);

      final LocalDate aValidFrom = PDTFromString.getLocalDateFromString (sValidFrom, "dd.MM.uuuu");
      if (StringHelper.hasText (sValidFrom) && aValidFrom == null)
        throw new IllegalStateException ("Invalid valid from: " + sValidFrom);
      final LocalDate aValidTo = PDTFromString.getLocalDateFromString (sValidTo, "dd.MM.uuuu");
      if (StringHelper.hasText (sValidTo) && aValidTo == null)
        throw new IllegalStateException ("Invalid valid to: " + sValidTo);

      boolean bIntern;
      if ("intern".equalsIgnoreCase (sInternExtern))
        bIntern = true;
      else
        if ("extern".equalsIgnoreCase (sInternExtern))
          bIntern = false;
        else
          throw new IllegalStateException ("Illegal internextern: " + sInternExtern);

      boolean bAddressable;
      if ("ja".equalsIgnoreCase (sAddressable))
        bAddressable = true;
      else
        if ("nein".equalsIgnoreCase (sAddressable))
          bAddressable = false;
        else
          throw new IllegalStateException ("Illegal Addressable: " + sAddressable);

      boolean bPostfach;
      if ("ja".equalsIgnoreCase (sPostfach))
        bPostfach = true;
      else
        if ("nein".equalsIgnoreCase (sPostfach))
          bPostfach = false;
        else
          throw new IllegalStateException ("Illegal Postfach: " + sPostfach);

      final IMicroElement ePLZ = eRoot.appendElement ("plz");
      ePLZ.setAttribute ("code", nPLZ);
      ePLZ.setAttribute ("city", sCity);
      ePLZ.setAttribute ("state", eState.getID ());
      ePLZ.setAttributeWithConversion ("validfrom", aValidFrom);
      ePLZ.setAttributeWithConversion ("validto", aValidTo);
      ePLZ.setAttribute ("type", sNamePLZTyp);
      ePLZ.setAttribute ("intern", bIntern);
      ePLZ.setAttribute ("addressable", bAddressable);
      ePLZ.setAttribute ("postfach", bPostfach);
    }
    MicroWriter.writeToFile (aDoc, new File ("src/main/resources/at/plz.xml"));
    LOGGER.info ("Found " + nRowIndex + " rows");
  }
}
