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
package com.helger.masterdata.vat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;

/**
 * Test class for class {@link VATINStructureManager}.
 *
 * @author Philip Helger
 */
public final class VATINStructureManagerTest
{
  @Test
  public void testAll ()
  {
    for (final VATINStructure aStructure : VATINStructureManager.getAllStructures ())
      for (final String sExample : aStructure.getExamples ())
        assertEquals (aStructure, VATINStructureManager.getFromValidVATIN (sExample));
  }

  @Test
  public void testConvertToXML ()
  {
    final IMicroDocument aDoc = new MicroDocument ();
    final IMicroElement eRoot = aDoc.appendElement ("vatins");
    for (final VATINStructure aStructure : VATINStructureManager.getAllStructures ())
    {
      final IMicroElement eVatin = eRoot.appendElement ("vatin");
      eVatin.setAttribute ("country", aStructure.getCountry ().getCountry ());
      eVatin.setAttribute ("pattern", aStructure.getPattern ());
      for (final String sExample : aStructure.getExamples ())
        eVatin.appendElement ("example").appendText (sExample);
    }
    final String sXML = MicroWriter.getNodeAsString (aDoc);
    assertNotNull (sXML);
  }
}
