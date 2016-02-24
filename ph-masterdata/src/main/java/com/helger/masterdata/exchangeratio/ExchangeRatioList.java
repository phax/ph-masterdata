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
package com.helger.masterdata.exchangeratio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsTreeSet;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.collection.ext.ICommonsNavigableSet;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.datetime.util.PDTHelper;
import com.helger.masterdata.currency.ECurrency;

/**
 * This class maintains an ordered list of {@link ExchangeRatio}, sorted
 * ascending by date.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class ExchangeRatioList implements ICloneable <ExchangeRatioList>, Serializable
{
  private final ECurrency m_eCurrency;
  private final ICommonsNavigableSet <ExchangeRatio> m_aList = new CommonsTreeSet <> (Comparator.comparing (ExchangeRatio::getDate));

  public ExchangeRatioList (@Nonnull final ECurrency eCurrency)
  {
    m_eCurrency = ValueEnforcer.notNull (eCurrency, "Currency");
  }

  public ExchangeRatioList (@Nonnull final ExchangeRatioList aOther)
  {
    ValueEnforcer.notNull (aOther, "Other");
    m_eCurrency = aOther.m_eCurrency;
    m_aList.addAll (aOther.m_aList);
  }

  @Nonnull
  public ECurrency getCurrency ()
  {
    return m_eCurrency;
  }

  @Nonnull
  @Nonempty
  public String getCurrencyID ()
  {
    return m_eCurrency.getID ();
  }

  @Nonnull
  public EChange addExchangeRatio (@Nonnull final ExchangeRatio aExchangeRatio)
  {
    ValueEnforcer.notNull (aExchangeRatio, "ExchangeRatio");
    return EChange.valueOf (m_aList.add (aExchangeRatio));
  }

  @Nonnull
  public EChange mergeWith (@Nonnull final ExchangeRatioList aList)
  {
    ValueEnforcer.notNull (aList, "List");
    return EChange.valueOf (m_aList.addAll (aList.m_aList));
  }

  @Nullable
  public ExchangeRatio getCurrentExchangeRatio ()
  {
    return m_aList.isEmpty () ? null : m_aList.last ();
  }

  @Nullable
  public ExchangeRatio getExchangeRatioOfDate (@Nonnull final LocalDate aDate)
  {
    ValueEnforcer.notNull (aDate, "Date");
    for (final ExchangeRatio aExchangeRatio : m_aList)
    {
      // As the exchange ratios are sorted from oldest to newest, we use the
      // first entry where the date is >= the expected date
      if (PDTHelper.isGreaterOrEqual (aExchangeRatio.getDate (), aDate))
        return aExchangeRatio;
    }
    return null;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <ExchangeRatio> getAllExchangeRatios ()
  {
    return m_aList.getCopyAsList ();
  }

  @Nonnull
  public ExchangeRatioList getClone ()
  {
    return new ExchangeRatioList (this);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("currency", m_eCurrency).append ("list", m_aList).toString ();
  }
}
