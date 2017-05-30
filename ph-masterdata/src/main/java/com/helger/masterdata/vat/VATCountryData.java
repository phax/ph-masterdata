/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsHashMap;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.collection.ext.ICommonsMap;
import com.helger.commons.locale.country.IHasCountry;
import com.helger.commons.math.MathHelper;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * Represents all the different VAT items for a single country.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class VATCountryData implements IHasCountry, Serializable
{
  private final Locale m_aCountry;
  private final boolean m_bZeroVATAllowed;
  private final ICommonsMap <String, IVATItem> m_aItems = new CommonsHashMap <> ();
  private final String m_sCountryName;
  private final String m_sInternalComment;

  public VATCountryData (@Nonnull final Locale aCountry,
                         final boolean bZeroVATAllowed,
                         @Nullable final String sCountryName,
                         @Nullable final String sInternalComment)
  {
    m_aCountry = ValueEnforcer.notNull (aCountry, "Country");
    m_bZeroVATAllowed = bZeroVATAllowed;
    m_sCountryName = sCountryName;
    m_sInternalComment = sInternalComment;
  }

  @Nonnull
  public Locale getCountry ()
  {
    return m_aCountry;
  }

  public boolean isZeroVATAllowed ()
  {
    return m_bZeroVATAllowed;
  }

  @Nullable
  public String getCountryName ()
  {
    return m_sCountryName;
  }

  @Nullable
  public String getInternalComment ()
  {
    return m_sInternalComment;
  }

  @Nonnull
  public EChange addItem (@Nonnull final VATItem aVATItem)
  {
    ValueEnforcer.notNull (aVATItem, "VATItem");

    final String sID = aVATItem.getID ();
    if (m_aItems.containsKey (sID))
      return EChange.UNCHANGED;
    m_aItems.put (sID, aVATItem);
    return EChange.CHANGED;
  }

  public boolean isEmpty ()
  {
    return m_aItems.isEmpty ();
  }

  /**
   * @return A non-<code>null</code> but may be empty map from VATItem ID to VAT
   *         item.
   */
  @Nonnull
  @ReturnsMutableCopy
  public ICommonsMap <String, IVATItem> getAllItems ()
  {
    return m_aItems.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <IVATItem> getItems (@Nonnull final Predicate <? super IVATItem> aFilter)
  {
    return m_aItems.copyOfValues (aFilter);
  }

  @Nullable
  public IVATItem getItemOfPercentage (@Nullable final BigDecimal aPercentage)
  {
    if (aPercentage != null)
    {
      for (final IVATItem aItem : m_aItems.values ())
        if (aItem.hasPercentage (aPercentage))
          return aItem;

      // Special handling for 0%
      if (isZeroVATAllowed () && MathHelper.isEQ0 (aPercentage))
        return VATManager.VATTYPE_NONE;
    }
    return null;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Country", m_aCountry)
                                       .append ("ZeroVATAllowed", m_bZeroVATAllowed)
                                       .append ("Items", m_aItems)
                                       .appendIfNotNull ("CountryName", m_sCountryName)
                                       .appendIfNotNull ("InternalComment", m_sInternalComment)
                                       .getToString ();
  }
}
