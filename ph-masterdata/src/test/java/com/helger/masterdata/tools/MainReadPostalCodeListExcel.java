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
package com.helger.masterdata.tools;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsImmutableObject;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.io.file.FileHelper;
import com.helger.commons.string.StringHelper;
import com.helger.masterdata.postal.PostalCodeListReader;
import com.helger.poi.excel.ExcelReadHelper;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;

public class MainReadPostalCodeListExcel
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainReadPostalCodeListExcel.class);
  private static final String PREFIX_ONE_CODE = "one code: ";
  private static final ICommonsSet <String> NO_CODES = new CommonsHashSet <> ("- no codes -", "– no codes -");

  private static final class Item
  {
    private final String m_sCountry;
    private final LocalDate m_aValidFrom;
    private LocalDate m_aValidTo;
    private final String m_sISO;
    private final List <String> m_aFormats;
    private final String m_sNote;

    public Item (@Nonnull @Nonempty final String sCountry,
                 @Nullable final LocalDate aValidFrom,
                 @Nonnull @Nonempty final String sISO,
                 @Nonnull @Nonempty final List <String> aFormats,
                 @Nullable final String sNote)
    {
      ValueEnforcer.notEmpty (sCountry, "country");
      ValueEnforcer.notEmpty (sISO, "ISO");
      ValueEnforcer.notEmpty (aFormats, "formats");
      m_sCountry = sCountry;
      m_aValidFrom = aValidFrom;
      m_sISO = sISO;
      m_aFormats = aFormats;
      m_sNote = sNote;
    }

    @Nonnull
    @Nonempty
    public String getCountry ()
    {
      return m_sCountry;
    }

    @Nullable
    public LocalDate getValidFrom ()
    {
      return m_aValidFrom;
    }

    @Nullable
    public LocalDate getValidTo ()
    {
      return m_aValidTo;
    }

    public void setValidTo (final LocalDate aValidTo)
    {
      m_aValidTo = aValidTo;
    }

    @Nonnull
    @Nonempty
    public String getISO ()
    {
      return m_sISO;
    }

    @Nonnull
    @ReturnsImmutableObject
    public List <String> getFormats ()
    {
      return CollectionHelper.makeUnmodifiable (m_aFormats);
    }

    @Nullable
    public String getNote ()
    {
      return m_sNote;
    }
  }

  public static void main (final String [] args) throws Exception
  {
    final String sSource = "http://en.wikipedia.org/wiki/List_of_postal_codes";
    final String sRevision = "20220414";

    final File f = new File ("src/test/resources/postalcode/" + sRevision + "PostalCodes.xlsx");
    try (final Workbook aWB = new XSSFWorkbook (FileHelper.getInputStream (f)))
    {
      final Sheet aSheet = aWB.getSheetAt (0);
      final Iterator <Row> it = aSheet.rowIterator ();

      // Skip 1 row
      it.next ();

      final IMicroDocument aDoc = new MicroDocument ();
      final IMicroElement eRoot = aDoc.appendElement (PostalCodeListReader.ELEMENT_ROOT);
      final IMicroElement eHeader = eRoot.appendElement (PostalCodeListReader.ELEMENT_HEADER);
      eHeader.appendElement (PostalCodeListReader.ELEMENT_SOURCE).appendText (sSource);
      eHeader.appendElement (PostalCodeListReader.ELEMENT_REVISION).appendText (sRevision);

      final IMicroElement eBody = eRoot.appendElement (PostalCodeListReader.ELEMENT_BODY);
      final ICommonsList <Item> aItems = new CommonsArrayList <> ();
      int nRow = 0;
      while (it.hasNext ())
      {
        final Row aRow = it.next ();
        ++nRow;

        // Country name
        final String sCountryName = ExcelReadHelper.getCellValueString (aRow.getCell (0));
        if (StringHelper.hasNoText (sCountryName))
        {
          LOGGER.warn ("Line " + nRow + ": No country name present");
          continue;
        }

        // Validity
        final Cell aDateCell = aRow.getCell (1);
        LocalDate aIntroducedDate = null;
        if (aDateCell != null && aDateCell.getCellType () != CellType.BLANK)
        {
          final Number aNum = ExcelReadHelper.getCellValueNumber (aDateCell);
          if (aNum != null)
          {
            final int nYear = aNum.intValue ();
            if (nYear > 1800 && nYear < 3000)
              aIntroducedDate = PDTFactory.createLocalDate (nYear, Month.JANUARY, 1);
            else
              aIntroducedDate = ExcelReadHelper.getCellValueLocalDate (aDateCell);
          }
        }

        // Country ISO
        final String sCountryISO = ExcelReadHelper.getCellValueString (aRow.getCell (2));
        if (StringHelper.hasNoText (sCountryISO))
        {
          LOGGER.warn ("Line " + nRow + ": No Country ISO code for " + sCountryName);
          continue;
        }

        // Postal code Format
        final String sFormat = ExcelReadHelper.getCellValueString (aRow.getCell (3));
        if (NO_CODES.contains (sFormat) || StringHelper.hasNoText (sFormat))
          continue;
        final ICommonsList <String> aFormats = StringHelper.getExploded ("\n",
                                                                         StringHelper.replaceAll (sFormat, ",", "\n"));
        for (int i = 0; i < aFormats.size (); ++i)
          aFormats.set (i, aFormats.get (i).trim ());

        // Skip street name format

        // The note
        final String sNote = ExcelReadHelper.getCellValueString (aRow.getCell (6));
        aItems.add (new Item (sCountryName, aIntroducedDate, sCountryISO, aFormats, sNote));
      }

      // Convert to map, where the key is the ISO
      final ICommonsMap <String, ICommonsList <Item>> aMap = new CommonsHashMap <> ();
      for (final Item aItem : aItems)
        aMap.computeIfAbsent (aItem.getISO (), k -> new CommonsArrayList <> ()).add (aItem);

      // Sort all sub-lists by introduction date
      for (final List <Item> aSubList : aMap.values ())
      {
        // null-safe sorting
        aSubList.sort ( (x, y) -> CompareHelper.compare (x.getValidFrom (), y.getValidFrom (), true));
        for (int i = 1; i < aSubList.size (); ++i)
        {
          final Item aPrevItem = aSubList.get (i - 1);
          final Item aThisItem = aSubList.get (i);
          if (aThisItem.getValidFrom () != null)
            aPrevItem.setValidTo (aThisItem.getValidFrom ().minusDays (1));
        }
      }

      // Print sorted by ISO code
      for (final Map.Entry <String, ? extends List <Item>> aEntry : CollectionHelper.getSortedByKey (aMap).entrySet ())
      {
        IMicroElement eCountry = null;
        for (final Item aItem : aEntry.getValue ())
        {
          if (eCountry == null)
          {
            // First item - ISO and name only once
            eCountry = eBody.appendElement (PostalCodeListReader.ELEMENT_COUNTRY);
            eCountry.setAttribute (PostalCodeListReader.ATTR_ISO, aItem.getISO ());
            eCountry.setAttribute (PostalCodeListReader.ATTR_NAME, aItem.getCountry ());
          }

          final IMicroElement ePostalCodes = eCountry.appendElement (PostalCodeListReader.ELEMENT_POSTALCODES);
          if (aItem.getValidFrom () != null)
            ePostalCodes.setAttribute (PostalCodeListReader.ATTR_VALIDFROM,
                                       DateTimeFormatter.ISO_LOCAL_DATE.format (aItem.getValidFrom ()));
          if (aItem.getValidTo () != null)
            ePostalCodes.setAttribute (PostalCodeListReader.ATTR_VALIDTO,
                                       DateTimeFormatter.ISO_LOCAL_DATE.format (aItem.getValidTo ()));
          for (final String sSingleFormat : aItem.getFormats ())
            if (sSingleFormat.startsWith (PREFIX_ONE_CODE))
              ePostalCodes.appendElement (PostalCodeListReader.ELEMENT_SPECIFIC)
                          .appendText (sSingleFormat.substring (PREFIX_ONE_CODE.length ()));
            else
            {

              ePostalCodes.appendElement (PostalCodeListReader.ELEMENT_FORMAT).appendText (sSingleFormat);
            }
          if (StringHelper.hasText (aItem.getNote ()))
            ePostalCodes.appendElement (PostalCodeListReader.ELEMENT_NOTE).appendText (aItem.getNote ());
        }
      }

      MicroWriter.writeToStream (aDoc,
                                 FileHelper.getBufferedOutputStream (new File ("src/main/resources/codelists/postal-codes-" +
                                                                               sRevision +
                                                                               ".xml")));
      LOGGER.info ("Done");
    }
  }
}
