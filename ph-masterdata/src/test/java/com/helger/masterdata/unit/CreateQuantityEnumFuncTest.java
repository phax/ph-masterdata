/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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

import java.util.Locale;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.commons.CGlobal;
import com.helger.commons.collection.ext.CommonsLinkedHashMap;
import com.helger.commons.collection.ext.ICommonsOrderedMap;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.convert.MicroTypeConverter;
import com.helger.commons.microdom.serialize.MicroReader;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.commons.text.IMultilingualText;

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
    final ICommonsOrderedMap <Integer, String> aTexts = new CommonsLinkedHashMap <> ();
    for (final IMicroElement eQuantity : eRoot.getFirstChildElement ("quantities").getAllChildElements ("quantity"))
    {
      final int nQuantity = StringParser.parseInt (eQuantity.getAttributeValue ("id"), CGlobal.ILLEGAL_UINT);
      final IMultilingualText aName = MicroTypeConverter.convertToNative (eQuantity.getFirstChildElement ("name"),
                                                                          IMultilingualText.class);
      final String sEN = aName.getText (Locale.ENGLISH).trim ();
      aTexts.put (Integer.valueOf (nQuantity), sEN);
    }

    // Build enum
    final ICommonsOrderedMap <Integer, String> aEnumNames = new CommonsLinkedHashMap <> ();
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
      sEnumName = StringHelper.trimStartRepeatedly (sEnumName, '_');
      sEnumName = StringHelper.trimEndRepeatedly (sEnumName, '_');
      sEnumName = StringHelper.replaceAllRepeatedly (sEnumName, "__", "_");
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

      final String sEN = StringHelper.replaceAll (aTexts.get (aQuantity), "\n", "\\n");
      aSB2.append (sEnumName).append (" (\"").append (sEN).append ("\", \"").append (sEN).append ("\"),");
    }
  }
}
