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
package com.helger.masterdata.locale;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.string.ToStringGenerator;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.serialize.MicroReader;

public class ISO639_2Handler
{
  public static final String DEFAULT_RESOURCE = "codelists/iso639-2-data-20220414.xml";

  private static final class SingletonHolder
  {
    static final ISO639_2Handler INSTANCE = new ISO639_2Handler ().readFromResource (new ClassPathResource (DEFAULT_RESOURCE));
  }

  private final ICommonsMap <String, ISO639_2Item> m_aAlpha3B = new CommonsHashMap <> ();
  private final ICommonsMap <String, ISO639_2Item> m_aAlpha3T = new CommonsHashMap <> ();
  private final ICommonsMap <String, ISO639_2Item> m_aAlpha2 = new CommonsHashMap <> ();

  public ISO639_2Handler ()
  {}

  @Nonnull
  public static ISO639_2Handler getDefaultInstance ()
  {
    return SingletonHolder.INSTANCE;
  }

  @Nullable
  private static String _unifyKey (@Nullable final String sKey)
  {
    return sKey == null ? null : sKey.toLowerCase (Locale.US);
  }

  @Nonnull
  public ISO639_2Handler readFromResource (@Nonnull final IReadableResource aRes)
  {
    final IMicroDocument aDoc = MicroReader.readMicroXML (aRes);
    for (final IMicroElement eItem : aDoc.getDocumentElement ().getAllChildElements ("item"))
    {
      final String sAlpha3B = eItem.getAttributeValue ("alpha3");
      final String sAlpha3T = eItem.getAttributeValue ("alpha3t");
      final String sAlpha2 = eItem.getAttributeValue ("alpha2");
      final String sEN = eItem.getAttributeValue ("en");
      final String sFR = eItem.getAttributeValue ("fr");
      final ISO639_2Item aItem = new ISO639_2Item (sAlpha3B, sAlpha3T, sAlpha2, sEN, sFR);
      registerItem (aItem);
    }
    return this;
  }

  public void registerItem (@Nonnull final ISO639_2Item aItem)
  {
    ValueEnforcer.notNull (aItem, "Item");

    m_aAlpha3B.put (_unifyKey (aItem.getAlpha3Bibliographic ()), aItem);

    final String sAlpha3T = _unifyKey (aItem.getAlpha3Terminologic ());
    if (sAlpha3T != null)
      m_aAlpha3T.put (sAlpha3T, aItem);

    final String sAlpha2 = _unifyKey (aItem.getAlpha2 ());
    if (sAlpha2 != null)
      m_aAlpha2.put (sAlpha2, aItem);
  }

  @Nullable
  public ISO639_2Item getItemOfAlpha3Code (@Nullable final String sAlpha3B)
  {
    if (sAlpha3B == null)
      return null;
    return m_aAlpha3B.get (_unifyKey (sAlpha3B));
  }

  @Nullable
  public ISO639_2Item getItemOfAlpha3TerminologicCode (@Nullable final String sAlpha3T)
  {
    if (sAlpha3T == null)
      return null;
    return m_aAlpha3T.get (_unifyKey (sAlpha3T));
  }

  @Nullable
  public ISO639_2Item getItemOfAlpha2Code (@Nullable final String sAlpha2)
  {
    if (sAlpha2 == null)
      return null;
    return m_aAlpha2.get (_unifyKey (sAlpha2));
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("alpha3Bibliographic", m_aAlpha3B)
                                       .append ("alpha3Terminologic", m_aAlpha3T)
                                       .append ("alpha2", m_aAlpha2)
                                       .getToString ();
  }
}
