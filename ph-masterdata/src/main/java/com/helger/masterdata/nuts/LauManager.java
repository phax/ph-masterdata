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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.clone.ICloneable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.ICommonsOrderedMap;
import com.helger.io.resource.ClassPathResource;
import com.helger.io.resource.IReadableResource;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.serialize.MicroReader;

/**
 * A manager for LAU items. The data of 2021 is accessible via
 * {@link #INSTANCE_2021}
 *
 * @author Philip Helger
 * @since 6.2.4
 */
@NotThreadSafe
public class LauManager implements ILauManager, ICloneable <LauManager>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (LauManager.class);

  /**
   * This is the default instance of the {@link LauManager} using the 2021 data
   * for reference. Never modify the default instance. Instead create a clone
   * using {@link #getClone()} and work on the clone.
   */
  public static final LauManager INSTANCE_2021 = LauManager.createFor2021 ();

  private final ICommonsOrderedMap <String, LauItem> m_aItems = new CommonsLinkedHashMap <> ();

  public LauManager ()
  {}

  @Nonnull
  @ReturnsMutableObject
  public ICommonsOrderedMap <String, LauItem> lauItems ()
  {
    return m_aItems;
  }

  /**
   * Add a new item to the manager.
   *
   * @param aItem
   *        The item to be added. May not be <code>null</code>.
   */
  public void addItem (@Nonnull final LauItem aItem)
  {
    ValueEnforcer.notNull (aItem, "Item");
    ValueEnforcer.isTrue ( () -> NutsManager.isValidNutsCode (aItem.getNutsCode ()),
                           () -> "NUTS Code '" + aItem.getNutsCode () + "' is invalid");

    final String sID = aItem.getID ();
    if (m_aItems.containsKey (sID))
      throw new IllegalArgumentException ("An item with ID '" + sID + "' is already contained");
    m_aItems.put (sID, aItem);
  }

  @Nonnull
  @ReturnsMutableCopy
  public LauManager getClone ()
  {
    final LauManager ret = new LauManager ();
    ret.m_aItems.putAll (m_aItems);
    return ret;
  }

  @Nonnull
  public static LauManager createFromXML (@Nonnull final IReadableResource aRes)
  {
    ValueEnforcer.notNull (aRes, "Res");
    ValueEnforcer.isTrue (aRes::exists, "Res must exist");

    LOGGER.info ("Reading LAU data from XML: " + aRes);

    final IMicroDocument aDoc = MicroReader.readMicroXML (aRes);
    if (aDoc == null || aDoc.getDocumentElement () == null)
      throw new IllegalArgumentException ("Failed to read " + aRes + " as XML");

    final LauManager ret = new LauManager ();
    for (final IMicroElement eItem : aDoc.getDocumentElement ().getAllChildElements ("item"))
    {
      final String sNuts = eItem.getAttributeValue ("nuts");
      final String sID = eItem.getAttributeValue ("lau");
      final String sName = eItem.getAttributeValue ("name");
      final String sLatinName = eItem.getAttributeValue ("latinName");
      ret.addItem (new LauItem (sNuts, sID, sName, sLatinName));
    }

    LOGGER.info ("Successfully read " + ret.m_aItems.size () + " LAU items");

    return ret;
  }

  @Nonnull
  public static LauManager createFor2021 ()
  {
    return createFromXML (new ClassPathResource ("codelists/lau-nuts2021.xml", LauManager.class.getClassLoader ()));
  }
}
