/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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
package com.helger.masterdata.currencyvalue;

import java.math.BigDecimal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.masterdata.currency.ECurrency;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.IMicroQName;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

/**
 * MicroType converter for {@link CurrencyValue}.
 *
 * @author Philip Helger
 */
public class CurrencyValueMicroTypeConverter implements IMicroTypeConverter <CurrencyValue>
{
  private static final IMicroQName ATTR_CURRENCY = new MicroQName ("currency");
  private static final IMicroQName ATTR_VALUE = new MicroQName ("value");

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final CurrencyValue aValue,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement ePrice = new MicroElement (sNamespaceURI, sTagName);
    ePrice.setAttribute (ATTR_CURRENCY, aValue.getCurrency ().getID ());
    ePrice.setAttributeWithConversion (ATTR_VALUE, aValue.getValue ());
    return ePrice;
  }

  @Nonnull
  public CurrencyValue convertToNative (@Nonnull final IMicroElement ePrice)
  {
    final ECurrency eCurrency = ECurrency.getFromIDOrNull (ePrice.getAttributeValue (ATTR_CURRENCY));
    final BigDecimal aValue = ePrice.getAttributeValueWithConversion (ATTR_VALUE, BigDecimal.class);
    return new CurrencyValue (eCurrency, aValue);
  }
}
