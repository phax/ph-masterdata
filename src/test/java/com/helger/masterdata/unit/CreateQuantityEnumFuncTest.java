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
package com.helger.masterdata.unit;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.commons.CGlobal;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.convert.MicroTypeConverter;
import com.helger.commons.microdom.serialize.MicroReader;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringParser;
import com.helger.commons.text.IReadonlyMultiLingualText;

/**
 * Test class for class {@link UnitManager}.
 *
 * @author Philip Helger
 */
public final class CreateQuantityEnumFuncTest
{
  @Ignore
  @Test
  public void createCodeList ()
  {
    final IMicroDocument aDoc = MicroReader.readMicroXML (UnitManager.DEFAULT_UNIT_RES);
    final IMicroElement eRoot = aDoc.getDocumentElement ();
    // Read all quantities
    final Map <Integer, String> aTexts = new LinkedHashMap <Integer, String> ();
    for (final IMicroElement eQuantity : eRoot.getFirstChildElement ("quantities").getAllChildElements ("quantity"))
    {
      final int nQuantity = StringParser.parseInt (eQuantity.getAttributeValue ("id"), CGlobal.ILLEGAL_UINT);
      final IReadonlyMultiLingualText aName = MicroTypeConverter.convertToNative (eQuantity.getFirstChildElement ("name"),
                                                                                  IReadonlyMultiLingualText.class);
      final String sEN = aName.getTextWithLocaleFallback (Locale.ENGLISH).trim ();
      aTexts.put (Integer.valueOf (nQuantity), sEN);
    }

    // Build enum
    final Map <Integer, String> aEnumNames = new LinkedHashMap <Integer, String> ();
    for (final Map.Entry <Integer, String> aEntry : aTexts.entrySet ())
    {
      String sEnumName = aEntry.getValue ().toUpperCase (Locale.US);

      int i = sEnumName.indexOf ('(');
      if (i > 0)
      {
        i = sEnumName.indexOf ('(', i + 1);
        if (i > 0)
          sEnumName = sEnumName.substring (0, i).trim () + "_PLUS";
      }

      i = sEnumName.indexOf (',');
      if (i > 0)
      {
        i = sEnumName.indexOf (',', i + 1);
        if (i > 0)
          sEnumName = sEnumName.substring (0, i).trim () + "_PLUS";
      }

      sEnumName = RegExHelper.getAsIdentifier (sEnumName, "_");
      while (sEnumName.startsWith ("_"))
        sEnumName = sEnumName.substring (1);
      while (sEnumName.endsWith ("_"))
        sEnumName = sEnumName.substring (0, sEnumName.length () - 1);
      while (sEnumName.contains ("__"))
        sEnumName = sEnumName.replace ("__", "_");
      aEnumNames.put (aEntry.getKey (), sEnumName);
    }

    final StringBuilder aSB1 = new StringBuilder ();
    final StringBuilder aSB2 = new StringBuilder ();
    for (final Map.Entry <Integer, String> aEntry : aEnumNames.entrySet ())
    {
      final Integer aQuantity = aEntry.getKey ();
      final String sEnumName = aEntry.getValue ();
      aSB1.append (sEnumName)
          .append (" (")
          .append (aQuantity)
          .append (", EUnitQuantityName.")
          .append (sEnumName)
          .append ("),");

      final String sEN = aTexts.get (aQuantity).replace ("\n", "\\n");
      aSB2.append (sEnumName).append (" (\"").append (sEN).append ("\", \"").append (sEN).append ("\"),");
    }
  }
}
