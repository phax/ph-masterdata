/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
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
package com.helger.masterdata.unit.impl;

import javax.annotation.Nonnull;

import com.helger.commons.CGlobal;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.WorkInProgress;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsCollection;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.string.StringParser;
import com.helger.commons.text.IMultilingualText;
import com.helger.commons.text.ReadOnlyMultilingualText;
import com.helger.masterdata.unit.UnitSector;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.convert.MicroTypeConverter;
import com.helger.xml.microdom.serialize.MicroReader;

/**
 * FIXME THIS CLASS IS NOT YET FINISHED!
 *
 * @author Philip Helger
 */
@WorkInProgress
public final class UnitManager
{
  public static final IReadableResource DEFAULT_UNIT_RES = new ClassPathResource ("codelists/rec20_Rev8e_2012.xml");

  private static final class SingletonHolder
  {
    static final UnitManager INSTANCE = new UnitManager (DEFAULT_UNIT_RES);
  }

  private final ICommonsMap <Integer, UnitSector> m_aSectors = new CommonsHashMap <> ();

  private void _readFromFile (@Nonnull final IReadableResource aRes)
  {
    final IMicroDocument aDoc = MicroReader.readMicroXML (aRes);
    if (aDoc == null)
      throw new IllegalArgumentException ("Failed to read " + aRes + " as XML document!");
    final IMicroElement eRoot = aDoc.getDocumentElement ();

    // Read all sectors
    for (final IMicroElement eSector : eRoot.getFirstChildElement ("sectors").getAllChildElements ("sector"))
    {
      final int nGroupNum = StringParser.parseInt (eSector.getAttributeValue ("groupnum"), CGlobal.ILLEGAL_UINT);
      final IMultilingualText aName = MicroTypeConverter.convertToNative (eSector.getFirstChildElement ("name"),
                                                                          ReadOnlyMultilingualText.class);
      final UnitSector aSector = new UnitSector (nGroupNum, aName);
      final Integer aKey = aSector.getIDObj ();
      if (m_aSectors.containsKey (aKey))
        throw new IllegalStateException ("A unit sector with group number " + aSector.getID () + " is already contained!");
      m_aSectors.put (aKey, aSector);
    }

    // Read all item
    for (final IMicroElement eItem : eRoot.getFirstChildElement ("body").getAllChildElements ("item"))
    {
      // TODO
      eItem.getAttributeValue ("id");
    }
  }

  public UnitManager (@Nonnull final IReadableResource aListRes)
  {
    _readFromFile (aListRes);
  }

  @Nonnull
  public static UnitManager getDefaultInstance ()
  {
    return SingletonHolder.INSTANCE;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsCollection <UnitSector> getAllSectors ()
  {
    return m_aSectors.copyOfValues ();
  }
}
