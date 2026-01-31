/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.string.StringHelper;
import com.helger.base.string.StringParser;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.helper.CollectionSort;
import com.helger.io.file.FileHelper;
import com.helger.masterdata.EUNCodelistStatus;
import com.helger.poi.excel.ExcelReadHelper;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;

/**
 * Utility to read the Excel file from CEFACT Recommendation No. 20 and convert it to XML for
 * internal use.
 *
 * @author Philip Helger
 */
public final class MainReadUnitTypeCodeListExcel
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainReadUnitTypeCodeListExcel.class);

  public static void main (final String [] args) throws Exception
  {
    final String sBaseName = "rec20_Rev8e_2012";
    final String sSource = "http://www.unece.org/cefact/recommendations/rec20/" + sBaseName + ".xls";
    final String sRevision = "8";

    // Ideally don't change anything from here on
    final File f = new File ("src/test/resources/" + sBaseName + ".xls");
    try (final Workbook aWB = new HSSFWorkbook (FileHelper.getInputStream (f)))
    {
      final Sheet aSheet = aWB.getSheetAt (1);
      final Iterator <Row> it = aSheet.rowIterator ();

      // Skip 1 row
      it.next ();

      final IMicroDocument aDoc = new MicroDocument ();
      final IMicroElement eRoot = aDoc.addElement ("root");
      final IMicroElement eHeader = eRoot.addElement ("header");
      eHeader.addElement ("source").addText (sSource);
      eHeader.addElement ("revision").addText (sRevision);

      final IMicroElement eBody = eRoot.addElement ("body");
      final ICommonsMap <String, String> aSectors = new CommonsHashMap <> ();
      final ICommonsMap <String, Integer> aQuantities = new CommonsHashMap <> ();
      while (it.hasNext ())
      {
        final Row aRow = it.next ();
        final String sGroupNumber = ExcelReadHelper.getCellValueString (aRow.getCell (0));
        final String sSector = ExcelReadHelper.getCellValueString (aRow.getCell (1));
        final String sGroupID = ExcelReadHelper.getCellValueString (aRow.getCell (2));
        final String sQuantity = ExcelReadHelper.getCellValueString (aRow.getCell (3));
        final String sLevel = ExcelReadHelper.getCellValueString (aRow.getCell (4));
        final int nLevel = StringParser.parseInt (sLevel.substring (0, 1), -1);
        final String sLevelSuffix = sLevel.length () != 2 ? null : sLevel.substring (1, 2);
        final String sStatus = ExcelReadHelper.getCellValueString (aRow.getCell (5));
        final ICommonsList <EUNCodelistStatus> aStatus = EUNCodelistStatus.getFromTextOrUnchanged (sStatus);
        final String sCommonCode = ExcelReadHelper.getCellValueString (aRow.getCell (6));
        final String sName = ExcelReadHelper.getCellValueString (aRow.getCell (7));
        final String sConversionFactor = ExcelReadHelper.getCellValueString (aRow.getCell (8));
        final String sSymbol = ExcelReadHelper.getCellValueString (aRow.getCell (9));
        final String sDescription = ExcelReadHelper.getCellValueString (aRow.getCell (10));

        // Avoid reading empty lines
        if (StringHelper.isNotEmpty (sCommonCode))
        {
          aSectors.put (sGroupNumber, sSector);

          Integer aQuantityID = aQuantities.get (sQuantity);
          if (aQuantityID == null)
          {
            aQuantityID = Integer.valueOf (aQuantities.size () + 1);
            aQuantities.put (sQuantity, aQuantityID);
          }

          final IMicroElement eItem = eBody.addElement ("item");
          eItem.setAttribute ("groupnum", sGroupNumber);
          eItem.setAttribute ("groupid", sGroupID);
          eItem.setAttribute ("quantityid", aQuantityID.intValue ());
          eItem.setAttribute ("level", nLevel);
          if (StringHelper.isNotEmpty (sLevelSuffix))
            eItem.setAttribute ("levelsuffix", sLevelSuffix);
          eItem.setAttribute ("status", EUNCodelistStatus.getAsString (aStatus));
          eItem.setAttribute ("commoncode", sCommonCode);
          eItem.addElement ("name").addElement ("text").setAttribute ("locale", "en").addText (sName);
          eItem.setAttribute ("conversion", sConversionFactor);
          eItem.setAttribute ("symbol", sSymbol);
          if (StringHelper.isNotEmpty (sDescription))
            eItem.addElement ("description").addElement ("text").setAttribute ("locale", "en").addText (sDescription);
        }
      }

      // sectors
      final IMicroElement eSectors = eRoot.addElement ("sectors");
      for (final Map.Entry <String, String> aEntry : CollectionSort.getSortedByKey (aSectors).entrySet ())
      {
        final IMicroElement eSector = eSectors.addElement ("sector");
        eSector.setAttribute ("groupnum", aEntry.getKey ());
        eSector.addElement ("name").addElement ("text").setAttribute ("locale", "en").addText (aEntry.getValue ());
      }

      // quantities
      final IMicroElement eQuantities = eRoot.addElement ("quantities");
      for (final Map.Entry <String, Integer> aEntry : CollectionSort.getSortedByValue (aQuantities).entrySet ())
      {
        final IMicroElement eSector = eQuantities.addElement ("quantity");
        eSector.setAttribute ("id", aEntry.getValue ().intValue ());
        eSector.addElement ("name").addElement ("text").setAttribute ("locale", "en").addText (aEntry.getKey ());
      }

      MicroWriter.writeToFile (aDoc, new File ("src/main/resources/codelists/" + sBaseName + ".xml"));
      LOGGER.info ("Done");
    }
  }
}
