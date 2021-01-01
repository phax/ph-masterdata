/**
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
package com.helger.masterdata.exchangeratio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsTreeSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsNavigableSet;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.currency.IHasCurrency;

/**
 * This class maintains an ordered list of {@link ExchangeRatio}, sorted
 * ascending by date.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class ExchangeRatioList implements ICloneable <ExchangeRatioList>, Serializable, IHasCurrency
{
  private final ECurrency m_eCurrency;
  private final ICommonsNavigableSet <ExchangeRatio> m_aSet = new CommonsTreeSet <> (Comparator.comparing (ExchangeRatio::getDate));

  public ExchangeRatioList (@Nonnull final ECurrency eCurrency)
  {
    m_eCurrency = ValueEnforcer.notNull (eCurrency, "Currency");
  }

  public ExchangeRatioList (@Nonnull final ExchangeRatioList aOther)
  {
    ValueEnforcer.notNull (aOther, "Other");
    m_eCurrency = aOther.m_eCurrency;
    m_aSet.addAll (aOther.m_aSet);
  }

  @Nonnull
  public ECurrency getCurrency ()
  {
    return m_eCurrency;
  }

  @Nonnull
  public EChange addExchangeRatio (@Nonnull final ExchangeRatio aExchangeRatio)
  {
    ValueEnforcer.notNull (aExchangeRatio, "ExchangeRatio");
    return EChange.valueOf (m_aSet.add (aExchangeRatio));
  }

  @Nonnull
  public EChange mergeWith (@Nonnull final ExchangeRatioList aList)
  {
    ValueEnforcer.notNull (aList, "List");
    return EChange.valueOf (m_aSet.addAll (aList.m_aSet));
  }

  @Nullable
  public ExchangeRatio getCurrentExchangeRatio ()
  {
    return m_aSet.isEmpty () ? null : m_aSet.last ();
  }

  @Nullable
  public ExchangeRatio getExchangeRatioOfDate (@Nonnull final LocalDate aDate)
  {
    ValueEnforcer.notNull (aDate, "Date");
    for (final ExchangeRatio aExchangeRatio : m_aSet)
    {
      // As the exchange ratios are sorted from oldest to newest, we use the
      // first entry where the date is >= the expected date
      if (aExchangeRatio.getDate ().compareTo (aDate) >= 0)
        return aExchangeRatio;
    }
    return null;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <ExchangeRatio> getAllExchangeRatios ()
  {
    return m_aSet.getCopyAsList ();
  }

  @Nonnull
  public ExchangeRatioList getClone ()
  {
    return new ExchangeRatioList (this);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Currency", m_eCurrency).append ("Set", m_aSet).getToString ();
  }
}
