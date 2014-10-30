/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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

import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.io.IInputStreamProvider;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.locale.LocaleCache;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.serialize.MicroReader;
import com.helger.commons.string.ToStringGenerator;

/**
 * This class manages the deprecated locales.
 *
 * @author Philip Helger
 */
public final class DeprecatedLocaleHandler
{
  private static final class SingletonHolder
  {
    static final DeprecatedLocaleHandler s_aInstance = readFromXML (new ClassPathResource ("codelists/locale-deprecated.xml"));
  }

  @Immutable
  private static final class LocaleParts
  {
    private final String m_sLanguage;
    private final String m_sCountry;
    private final String m_sVariant;

    public LocaleParts (@Nullable final String sLanguage,
                        @Nullable final String sCountry,
                        @Nullable final String sVariant)
    {
      m_sLanguage = sLanguage;
      m_sCountry = sCountry;
      m_sVariant = sVariant;
    }

    public boolean matchesLocale (@Nonnull final Locale aLocale)
    {
      final boolean bLanguage = m_sLanguage == null || m_sLanguage.equals (aLocale.getLanguage ());
      final boolean bCountry = m_sCountry == null || m_sCountry.equals (aLocale.getCountry ());
      final boolean bVariant = m_sVariant == null || m_sVariant.equals (aLocale.getVariant ());
      return bLanguage && bCountry && bVariant;
    }
  }

  private static final Logger s_aLogger = LoggerFactory.getLogger (DeprecatedLocaleHandler.class);

  private final Set <Locale> m_aLocales = new HashSet <Locale> ();
  private final Set <LocaleParts> m_aLocaleParts = new HashSet <LocaleParts> ();

  public void initFromXML (@Nonnull final IMicroDocument aDoc)
  {
    ValueEnforcer.notNull (aDoc, "Doc");
    ValueEnforcer.notNull (aDoc.getDocumentElement (), "Doc.DocumentElement");

    m_aLocales.clear ();

    for (final IMicroElement eLocale : aDoc.getDocumentElement ().getAllChildElements ("locale"))
    {
      final String sLanguage = eLocale.getAttributeValue ("language");
      final String sCountry = eLocale.getAttributeValue ("country");
      final String sVariant = eLocale.getAttributeValue ("variant");

      final Locale aLocale = LocaleCache.getLocale (sLanguage, sCountry, sVariant);
      if (aLocale == null)
        s_aLogger.warn ("Deprecated locale could not be resolved!");
      else
      {
        m_aLocales.add (aLocale);
        m_aLocaleParts.add (new LocaleParts (sLanguage, sCountry, sVariant));
      }
    }
  }

  /**
   * @return A set of all locales as specified in the file.
   */
  @Nonnull
  @ReturnsMutableCopy
  public Set <Locale> getAllDeprecatedLocales ()
  {
    return ContainerHelper.newSet (m_aLocales);
  }

  /**
   * Check if the passed locale is directly deprecated.
   *
   * @param aLocale
   *        The locale to check
   * @return <code>true</code> if it is deprecated
   */
  public boolean isDeprecatedLocale (@Nullable final Locale aLocale)
  {
    return m_aLocales.contains (aLocale);
  }

  /**
   * Check if the passed locale is deprecated. Also checks fallbacks (e.g. the
   * country "CS" is marked deprecated, therefore the locale "sr_CS" is also
   * implicitly deprecated)
   *
   * @param aLocale
   *        The locale to check
   * @return <code>true</code> if it is deprecated
   */
  public boolean isDeprecatedLocaleWithFallback (@Nullable final Locale aLocale)
  {
    if (aLocale != null)
    {
      if (m_aLocales.contains (aLocale))
        return true;
      for (final LocaleParts aParts : m_aLocaleParts)
        if (aParts.matchesLocale (aLocale))
          return true;
    }
    return false;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("locales", m_aLocales).toString ();
  }

  @Nonnull
  public static DeprecatedLocaleHandler readFromXML (@Nonnull final IInputStreamProvider aISP)
  {
    ValueEnforcer.notNull (aISP, "InputStreamProvider");

    return readFromXML (aISP.getInputStream ());
  }

  @Nonnull
  public static DeprecatedLocaleHandler readFromXML (@Nonnull @WillClose final InputStream aIS)
  {
    ValueEnforcer.notNull (aIS, "InputStream");

    final IMicroDocument aDoc = MicroReader.readMicroXML (aIS);
    final DeprecatedLocaleHandler ret = new DeprecatedLocaleHandler ();
    ret.initFromXML (aDoc);
    return ret;
  }

  /**
   * @return The default singleton instance. Never <code>null</code>.
   */
  @Nonnull
  public static DeprecatedLocaleHandler getDefaultInstance ()
  {
    return SingletonHolder.s_aInstance;
  }
}
