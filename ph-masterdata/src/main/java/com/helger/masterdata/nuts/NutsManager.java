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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.clone.ICloneable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.cache.regex.RegExHelper;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.ICommonsOrderedMap;
import com.helger.io.resource.ClassPathResource;
import com.helger.io.resource.IReadableResource;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.serialize.MicroReader;

/**
 * A manager for NUTS items. The data of 2021 is accessible via {@link #INSTANCE_2021}
 *
 * @author Philip Helger
 * @since 6.2.4
 */
@NotThreadSafe
public class NutsManager implements INutsManager, ICloneable <NutsManager>
{
  /**
   * The regular expression used to validate NUTS codes. It works for country codes and all NUTS
   * levels.
   */
  public static final String REGEX_NUTS_CODE = "^[A-Z]{2}[0-9A-Z]{0,3}$";

  private static final Logger LOGGER = LoggerFactory.getLogger (NutsManager.class);

  /**
   * This is the default instance of the {@link NutsManager} using the 2021 data for reference.
   * Never modify the default instance. Instead create a clone using {@link #getClone()} and work on
   * the clone.
   */
  public static final NutsManager INSTANCE_2021 = NutsManager.createFor2021 ();

  private final ICommonsOrderedMap <String, NutsItem> m_aItems = new CommonsLinkedHashMap <> ();

  public NutsManager ()
  {}

  @NonNull
  @ReturnsMutableObject
  public ICommonsOrderedMap <String, NutsItem> nutsItems ()
  {
    return m_aItems;
  }

  public static boolean isValidNutsCode (@Nullable final String s)
  {
    return StringHelper.isNotEmpty (s) && RegExHelper.stringMatchesPattern (REGEX_NUTS_CODE, s);
  }

  /**
   * Add a new item to the manager.
   *
   * @param aItem
   *        The item to be added. May not be <code>null</code>.
   */
  public void addItem (@NonNull final NutsItem aItem)
  {
    ValueEnforcer.notNull (aItem, "Item");
    final String sID = aItem.getID ();
    ValueEnforcer.isTrue ( () -> isValidNutsCode (sID), () -> "NUTS Code '" + sID + "' is invalid");

    if (m_aItems.containsKey (sID))
      throw new IllegalArgumentException ("A NUTS item with ID '" + sID + "' is already contained");
    m_aItems.put (sID, aItem);
  }

  @NonNull
  @ReturnsMutableCopy
  public NutsManager getClone ()
  {
    final NutsManager ret = new NutsManager ();
    ret.m_aItems.putAll (m_aItems);
    return ret;
  }

  @NonNull
  public static NutsManager createFromXML (@NonNull final IReadableResource aRes)
  {
    ValueEnforcer.notNull (aRes, "Res");
    ValueEnforcer.isTrue (aRes::exists, "Res must exist");

    LOGGER.info ("Reading NUTS data from XML: " + aRes);

    final IMicroDocument aDoc = MicroReader.readMicroXML (aRes);
    if (aDoc == null || aDoc.getDocumentElement () == null)
      throw new IllegalArgumentException ("Failed to read " + aRes + " as XML");

    final NutsManager ret = new NutsManager ();
    for (final IMicroElement eItem : aDoc.getDocumentElement ().getAllChildElements ("item"))
    {
      final String sID = eItem.getAttributeValue ("id");
      final String sName = eItem.getAttributeValue ("name");
      final String sLatinName = eItem.getAttributeValue ("latinName");
      final int nCountryOrdinal = eItem.getAttributeValueAsInt ("countryOrd", -1);
      final int nRegionOrdinal = eItem.getAttributeValueAsInt ("regionOrd", -1);
      ret.addItem (new NutsItem (sID, sName, sLatinName, nCountryOrdinal, nRegionOrdinal));
    }

    LOGGER.info ("Successfully read " + ret.m_aItems.size () + " NUTS items");

    return ret;
  }

  @NonNull
  public static NutsManager createFor2021 ()
  {
    return createFromXML (new ClassPathResource ("codelists/nuts2021.xml", NutsManager.class.getClassLoader ()));
  }
}
