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
package com.helger.masterdata.vat;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.WillClose;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.io.iface.IHasInputStream;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringParser;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.collection.CollectionFind;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsSet;
import com.helger.datetime.format.PDTFromString;
import com.helger.io.resource.ClassPathResource;
import com.helger.text.locale.LocaleHelper;
import com.helger.text.locale.country.CountryCache;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.serialize.MicroReader;
import com.helger.xml.microdom.util.MicroHelper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Manages the available VAT types.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class VATManager implements IVATItemProvider
{
  private static final class SingletonHolder
  {
    static final VATManager INSTANCE = readFromXML (new ClassPathResource ("codelists/vat-data.xml"));
  }

  /**
   * Special VAT item with 0% - this is the only VAT item that has NO country locale
   */
  public static final IVATItem VATTYPE_NONE = new VATItem ("_none_",
                                                           (Locale) null,
                                                           EVATItemType.OTHER,
                                                           BigDecimal.ZERO,
                                                           false,
                                                           null,
                                                           null);

  private static final Logger LOGGER = LoggerFactory.getLogger (VATManager.class);
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;

  // The sources the data comes from
  private final ICommonsList <String> m_aSources = new CommonsArrayList <> ();

  // Maps from locale to the available VAT data
  private final ICommonsMap <Locale, VATCountryData> m_aVATItemsPerCountry = new CommonsHashMap <> ();

  // Overall VAT map (ID to item)
  private final ICommonsMap <String, IVATItem> m_aAllVATItems = new CommonsHashMap <> ();

  public VATManager ()
  {}

  @Nullable
  private static String _getCountryString (@Nullable final Locale aLocale)
  {
    if (aLocale == null)
      return null;

    // Is it "all" or "independent"?
    if (LocaleHelper.isSpecialLocale (aLocale))
      return aLocale.getLanguage ();
    return aLocale.getCountry ().toLowerCase (Locale.US);
  }

  public void initFromXML (@Nonnull final IMicroDocument aDoc)
  {
    ValueEnforcer.notNull (aDoc, "Doc");
    ValueEnforcer.notNull (aDoc.getDocumentElement (), "Doc.DocumentElement");

    m_aSources.clear ();
    m_aVATItemsPerCountry.clear ();
    m_aAllVATItems.clear ();

    final IMicroElement eSources = aDoc.getDocumentElement ().getFirstChildElement ("sources");
    if (eSources != null)
      eSources.forAllChildElements (IMicroElement.filterNamespaceURIAndName (null, "source"), eSource -> {
        final String sSource = eSource.getTextContent ();
        if (StringHelper.isNotEmpty (sSource))
          m_aSources.add (sSource);
      });
    for (final IMicroElement eVATTypes : aDoc.getDocumentElement ().getAllChildElements ("vattypes"))
    {
      // Country
      final String sCountry = eVATTypes.getAttributeValue ("country");
      final Locale aCountry = CountryCache.getInstance ().getCountry (sCountry);
      if (m_aVATItemsPerCountry.containsKey (aCountry))
      {
        LOGGER.warn ("VAT types for country " + aCountry + " have already been defined!");
        continue;
      }
      final String sCountryName = eVATTypes.getAttributeValue ("countryname");

      // zero VAT allowed?
      final String sZeroVATAllowed = eVATTypes.getAttributeValue ("zerovat");
      final boolean bZeroVATAllowed = StringParser.parseBool (sZeroVATAllowed);

      // Internal comment?
      final String sInternalComment = MicroHelper.getChildTextContent (eVATTypes, "comment");

      // read all items
      final VATCountryData aVATCountryData = new VATCountryData (aCountry,
                                                                 bZeroVATAllowed,
                                                                 sCountryName,
                                                                 sInternalComment);
      for (final IMicroElement eVATItem : eVATTypes.getAllChildElements ("item"))
      {
        // item ID
        final String sID = eVATItem.getAttributeValue ("id");
        if (StringHelper.isEmpty (sID))
        {
          LOGGER.warn ("VAT item in country " + aCountry + " has no ID. Skipping VAT item.");
          continue;
        }
        final String sRealID = _getCountryString (aCountry) + "." + sID;

        // item type
        final String sType = eVATItem.getAttributeValue ("type");
        final EVATItemType eType = EVATItemType.getFromIDOrNull (sType);
        if (eType == null)
        {
          LOGGER.warn ("VAT type '" + sType + "' for VAT item " + sRealID + " is illegal. Skipping VAT item.");
          continue;
        }
        // item percentage
        final String sPercentage = eVATItem.getAttributeValue ("percentage");
        final BigDecimal aPercentage = StringParser.parseBigDecimal (sPercentage, null);
        if (aPercentage == null)
        {
          LOGGER.warn ("Percentage value '" +
                       sPercentage +
                       "' for VAT item " +
                       sRealID +
                       " is illegal. Skipping VAT item.");
          continue;
        }
        // Deprecated?
        final String sDeprecated = eVATItem.getAttributeValue ("deprecated");
        final boolean bDeprecated = sDeprecated != null && StringParser.parseBool (sDeprecated);

        // Valid from (optional)
        final String sValidFrom = eVATItem.getAttributeValue ("validfrom");
        final LocalDate aValidFrom = PDTFromString.getLocalDateFromString (sValidFrom, DATE_FORMAT);

        // Valid to (optional)
        final String sValidTo = eVATItem.getAttributeValue ("validto");
        final LocalDate aValidTo = PDTFromString.getLocalDateFromString (sValidTo, DATE_FORMAT);

        // build and add item
        final VATItem aVATItem = new VATItem (sRealID, aCountry, eType, aPercentage, bDeprecated, aValidFrom, aValidTo);
        if (aVATCountryData.addItem (aVATItem).isUnchanged ())
          LOGGER.warn ("Found duplicate VAT item " + aVATItem + " for country " + aCountry);
        if (m_aAllVATItems.put (sRealID, aVATItem) != null)
          LOGGER.warn ("Found overall duplicate VAT item " + aVATItem);
      }
      if (aVATCountryData.isEmpty ())
        LOGGER.warn ("No VAT types for country " + aCountry + " defined!");
      m_aVATItemsPerCountry.put (aCountry, aVATCountryData);
    }
  }

  /**
   * @return A list with all URLs where the data was gathered from. Purely descriptive. Has no
   *         impact on the logic. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <String> getAllSources ()
  {
    return m_aSources.getClone ();
  }

  /**
   * @return All countries for which VAT type definitions are present. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public ICommonsSet <Locale> getAllAvailableCountries ()
  {
    return m_aVATItemsPerCountry.copyOfKeySet ();
  }

  /**
   * @return The complete map from locale to VAT country data. Never <code>null</code>.
   * @since 5.0.5
   */
  @Nonnull
  @ReturnsMutableCopy
  public ICommonsMap <Locale, VATCountryData> getAllCountryData ()
  {
    return m_aVATItemsPerCountry.getClone ();
  }

  /**
   * Check if zero VAT is allowed for the passed country
   *
   * @param aCountry
   *        The country to be checked.
   * @param bUndefinedValue
   *        The value to be returned, if no VAT data is available for the passed country
   * @return <code>true</code> or <code>false</code>
   */
  public boolean isZeroVATAllowed (@Nonnull final Locale aCountry, final boolean bUndefinedValue)
  {
    ValueEnforcer.notNull (aCountry, "Country");

    // first get locale specific VAT types
    final VATCountryData aVATCountryData = getVATCountryData (aCountry);
    return aVATCountryData != null ? aVATCountryData.isZeroVATAllowed () : bUndefinedValue;
  }

  /**
   * Get the VAT data of the passed country.
   *
   * @param aLocale
   *        The locale to use. May not be <code>null</code>.
   * @return <code>null</code> if no such country data is present.
   */
  @Nullable
  public VATCountryData getVATCountryData (@Nonnull final Locale aLocale)
  {
    ValueEnforcer.notNull (aLocale, "Locale");
    final Locale aCountry = CountryCache.getInstance ().getCountry (aLocale);
    return m_aVATItemsPerCountry.get (aCountry);
  }

  /**
   * Check if VAT data is available for the provided country.
   *
   * @param aLocale
   *        The locale to check. May be <code>null</code>.
   * @return <code>true</code> if VAT country data is available.
   * @since 5.0.5
   */
  public boolean isVATCountryDataAvailable (@Nullable final Locale aLocale)
  {
    return aLocale != null && getVATCountryData (aLocale) != null;
  }

  /**
   * Get all VAT types matching the given locale (without any fallback!). It contains both the
   * specific definitions and the locale independent definitions.
   *
   * @param aCountry
   *        The locale to use. May not be <code>null</code>.
   * @return A non-<code>null</code> map from ID to the matching VAT item. Also the deprecated VAT
   *         items are returned! VATTYPE_NONE.getID () is used if zero VAT is allowed
   */
  @ReturnsMutableCopy
  @Nonnull
  public ICommonsMap <String, IVATItem> getAllVATItemsForCountry (@Nonnull final Locale aCountry)
  {
    ValueEnforcer.notNull (aCountry, "Country");

    final ICommonsMap <String, IVATItem> ret = new CommonsHashMap <> ();

    // first get locale specific VAT types
    final VATCountryData aVATCountryData = getVATCountryData (aCountry);
    if (aVATCountryData != null)
    {
      if (aVATCountryData.isZeroVATAllowed ())
        ret.put (VATTYPE_NONE.getID (), VATTYPE_NONE);
      ret.putAll (aVATCountryData.getAllItems ());
    }
    return ret;
  }

  @Nullable
  public IVATItem getVATItemOfID (@Nullable final String sID)
  {
    IVATItem ret = m_aAllVATItems.get (sID);
    if (ret == null && VATTYPE_NONE.getID ().equals (sID))
      ret = VATTYPE_NONE;
    return ret;
  }

  @Nullable
  public IVATItem getVATItemOfID (@Nonnull final Locale aCountry, @Nullable final String sID)
  {
    return getVATItemOfID (_getCountryString (aCountry) + "." + sID);
  }

  /**
   * Find a matching VAT item with the passed properties, independent of the country.
   *
   * @param eType
   *        The VAT type to use. May be <code>null</code> resulting in a <code>null</code> result.
   * @param aPercentage
   *        The percentage to find. May be <code>null</code> resulting in a <code>null</code>
   *        result.
   * @return <code>null</code> if no matching item could be found,
   */
  @Nullable
  public IVATItem findVATItem (@Nullable final EVATItemType eType, @Nullable final BigDecimal aPercentage)
  {
    if (eType == null || aPercentage == null)
      return null;
    return findFirst (x -> x.getType ().equals (eType) && x.hasPercentage (aPercentage));
  }

  /**
   * Find the first matching VAT item.
   *
   * @param aFilter
   *        The filter to be used. May not be <code>null</code>.
   * @return <code>null</code> if no matching item could be found,
   */
  @Nullable
  public IVATItem findFirst (@Nonnull final Predicate <? super IVATItem> aFilter)
  {
    return CollectionFind.findFirst (m_aAllVATItems.values (), aFilter);
  }

  /**
   * Find all matching VAT items.
   *
   * @param aFilter
   *        The filter to be used. May not be <code>null</code>.
   * @return A non-<code>null</code> but maybe empty list with all matching {@link IVATItem}
   *         objects.
   * @since 5.0.5
   */
  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <IVATItem> findAll (@Nonnull final Predicate <? super IVATItem> aFilter)
  {
    final ICommonsList <IVATItem> ret = new CommonsArrayList <> ();
    CollectionFind.findAll (m_aAllVATItems.values (), aFilter, ret::add);
    return ret;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("sources", m_aSources)
                                       .append ("VATItemsPerCountry", m_aVATItemsPerCountry)
                                       .append ("allVATItems", m_aAllVATItems)
                                       .getToString ();
  }

  @Nonnull
  public static VATManager readFromXML (@Nonnull final IHasInputStream aISP)
  {
    ValueEnforcer.notNull (aISP, "InputStreamProvider");

    return readFromXML (aISP.getInputStream ());
  }

  @Nonnull
  public static VATManager readFromXML (@Nonnull @WillClose final InputStream aIS)
  {
    ValueEnforcer.notNull (aIS, "InputStream");

    final IMicroDocument aDoc = MicroReader.readMicroXML (aIS);
    final VATManager ret = new VATManager ();
    ret.initFromXML (aDoc);
    return ret;
  }

  /**
   * @return The default singleton instance. Never <code>null</code>.
   */
  @Nonnull
  public static VATManager getDefaultInstance ()
  {
    return SingletonHolder.INSTANCE;
  }
}
