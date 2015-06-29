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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.io.file.FileHelper;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.MicroDocument;
import com.helger.commons.microdom.serialize.MicroWriter;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.masterdata.EUNCodelistStatus;
import com.helger.poi.excel.ExcelReadUtils;

public class MainReadPackageTypeCodeListExcel
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainReadPackageTypeCodeListExcel.class);

  private static String _convertNumericCodes (final String sNumericCode)
  {
    if (StringHelper.hasNoText (sNumericCode))
      return null;

    final String sRealNumericCode = sNumericCode.replace ('\n', ' ');
    // E.g. "22 to 25", "44 or 45", "21 to 23 or 31 to 33"
    final List <String> ret = new ArrayList <String> ();

    // First split by "or"
    final String [] aOrParts = RegExHelper.getSplitToArray (sRealNumericCode, " or ");
    for (final String sOrPart : aOrParts)
    {
      if (sOrPart.contains (" to "))
      {
        // Split by "to"
        final String [] aToParts = RegExHelper.getSplitToArray (sOrPart, " to ");
        if (aToParts.length != 2)
          throw new IllegalStateException (sRealNumericCode +
                                           " ==> " +
                                           Arrays.toString (aToParts) +
                                           " ==> more than 2 parts");
        final int nFrom = StringParser.parseInt (aToParts[0].trim (), -1);
        final int nTo = StringParser.parseInt (aToParts[1].trim (), -1);
        if (nFrom == -1 || nTo == -1)
          throw new IllegalStateException (sRealNumericCode + " ==> " + Arrays.toString (aToParts) + " ==> not numeric");
        for (int i = nFrom; i <= nTo; ++i)
          ret.add (Integer.toString (i));
      }
      else
      {
        // Explicit value
        final String sRealOrPart = sOrPart.trim ();
        if (!StringParser.isInt (sRealOrPart))
          throw new IllegalStateException (sRealNumericCode + " ==> " + sRealOrPart + " is not numeric!");

        ret.add (sRealOrPart);
      }
    }
    return StringHelper.getImploded (",", ret);
  }

  public static void main (final String [] args) throws Exception
  {
    final String sBaseName = "rec21_Rev9e_2012";
    final String sSource = "http://www.unece.org/cefact/recommendations/rec21/" + sBaseName + ".xls";
    final String sRevision = "9";

    // Ideally don't change anything from here on
    final File f = new File ("src/test/resources/" + sBaseName + ".xls");
    final Workbook aWB = new HSSFWorkbook (FileHelper.getInputStream (f));
    final Sheet aSheet = aWB.getSheetAt (0);
    final Iterator <Row> it = aSheet.rowIterator ();

    // Skip 3 rows
    for (int i = 0; i < 3; ++i)
      it.next ();

    final IMicroDocument aDoc = new MicroDocument ();
    final IMicroElement eRoot = aDoc.appendElement ("root");
    final IMicroElement eHeader = eRoot.appendElement ("header");
    eHeader.appendElement ("source").appendText (sSource);
    eHeader.appendElement ("revision").appendText (sRevision);

    final IMicroElement eBody = eRoot.appendElement ("body");
    while (it.hasNext ())
    {
      final Row aRow = it.next ();
      final String sStatus = ExcelReadUtils.getCellValueString (aRow.getCell (0));
      final EUNCodelistStatus [] aStatus = EUNCodelistStatus.getFromTextOrUnchanged (sStatus);
      final String sCode = ExcelReadUtils.getCellValueString (aRow.getCell (1));
      final String sName = ExcelReadUtils.getCellValueString (aRow.getCell (2));
      final String sDescription = ExcelReadUtils.getCellValueString (aRow.getCell (3));
      final String sNumericCode = _convertNumericCodes (ExcelReadUtils.getCellValueString (aRow.getCell (4)));

      // Avoid reading empty lines
      if (StringHelper.hasText (sCode))
      {
        final IMicroElement eItem = eBody.appendElement ("item");
        eItem.setAttribute ("status", EUNCodelistStatus.getAsString (aStatus));
        eItem.setAttribute ("code", sCode);
        eItem.appendElement ("name").appendElement ("text").setAttribute ("locale", "en").appendText (sName);
        if (StringHelper.hasText (sDescription))
          eItem.appendElement ("description")
               .appendElement ("text")
               .setAttribute ("locale", "en")
               .appendText (sDescription);
        eItem.setAttribute ("numericcodes", sNumericCode);
      }
    }

    MicroWriter.writeToStream (aDoc, FileHelper.getOutputStream ("src/main/resources/codelists/" + sBaseName + ".xml"));
    s_aLogger.info ("Done");
  }
}
