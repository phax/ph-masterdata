/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.collection.commons.CommonsTreeSet;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsNavigableSet;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.currency.IHasCurrency;

/**
 * This class maintains an ordered list of {@link ExchangeRatio}, sorted
 * ascending by date.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class ExchangeRatioList implements ICloneable <ExchangeRatioList>, IHasCurrency, Serializable
{
  private final ECurrency m_eCurrency;
  private final ICommonsNavigableSet <ExchangeRatio> m_aSet = new CommonsTreeSet <> (Comparator.comparing (ExchangeRatio::getDate));

  public ExchangeRatioList (@NonNull final ECurrency eCurrency)
  {
    m_eCurrency = ValueEnforcer.notNull (eCurrency, "Currency");
  }

  public ExchangeRatioList (@NonNull final ExchangeRatioList aOther)
  {
    ValueEnforcer.notNull (aOther, "Other");
    m_eCurrency = aOther.m_eCurrency;
    m_aSet.addAll (aOther.m_aSet);
  }

  @NonNull
  public ECurrency getCurrency ()
  {
    return m_eCurrency;
  }

  @NonNull
  public EChange addExchangeRatio (@NonNull final ExchangeRatio aExchangeRatio)
  {
    ValueEnforcer.notNull (aExchangeRatio, "ExchangeRatio");
    return EChange.valueOf (m_aSet.add (aExchangeRatio));
  }

  @NonNull
  public EChange mergeWith (@NonNull final ExchangeRatioList aList)
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
  public ExchangeRatio getExchangeRatioOfDate (@NonNull final LocalDate aDate)
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

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <ExchangeRatio> getAllExchangeRatios ()
  {
    return m_aSet.getCopyAsList ();
  }

  @NonNull
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
