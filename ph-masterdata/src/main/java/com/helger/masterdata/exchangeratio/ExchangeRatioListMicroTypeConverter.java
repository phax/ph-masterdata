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
package com.helger.masterdata.exchangeratio;

import com.helger.masterdata.currency.ECurrency;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.IMicroQName;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;
import com.helger.xml.microdom.convert.MicroTypeConverter;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class ExchangeRatioListMicroTypeConverter implements IMicroTypeConverter <ExchangeRatioList>
{
  private static final IMicroQName ATTR_CURRENCY = new MicroQName ("currency");
  private static final String ELEMENT_EXCHANGE_RATIO = "exchangeratio";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final ExchangeRatioList aValue,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    aElement.setAttribute (ATTR_CURRENCY, aValue.getCurrencyID ());
    for (final ExchangeRatio aExchangeRatio : aValue.getAllExchangeRatios ())
      aElement.addChild (MicroTypeConverter.convertToMicroElement (aExchangeRatio,
                                                                   sNamespaceURI,
                                                                   ELEMENT_EXCHANGE_RATIO));
    return aElement;
  }

  @Nonnull
  public ExchangeRatioList convertToNative (@Nonnull final IMicroElement aElement)
  {
    final String sCurrencyID = aElement.getAttributeValue (ATTR_CURRENCY);
    final ECurrency eCurrency = ECurrency.getFromIDOrNull (sCurrencyID);
    if (eCurrency == null)
      throw new IllegalStateException ("Failed to resolve currency with ID '" + sCurrencyID + "'");
    final ExchangeRatioList ret = new ExchangeRatioList (eCurrency);
    for (final IMicroElement eChild : aElement.getAllChildElements (ELEMENT_EXCHANGE_RATIO))
      ret.addExchangeRatio (MicroTypeConverter.convertToNative (eChild, ExchangeRatio.class));
    return ret;
  }
}
