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
package com.helger.masterdata.price;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.vat.IVATItem;
import com.helger.masterdata.vat.VATManager;
import com.helger.xml.microdom.IMicroElement;

public final class ReadOnlyPriceMicroTypeConverter extends AbstractPriceMicroTypeConverter
{
  @Nonnull
  public ReadOnlyPrice convertToNative (@Nonnull final IMicroElement ePrice)
  {
    final String sCurrency = ePrice.getAttributeValue (ATTR_CURRENCY);
    final ECurrency eCurrency = ECurrency.getFromIDOrNull (sCurrency);
    if (eCurrency == null)
      throw new IllegalStateException ("Failed to resolve currency with ID '" + sCurrency + "'");

    final BigDecimal aNetAmount = ePrice.getAttributeValueWithConversion (ATTR_NETAMOUNT, BigDecimal.class);
    final String sVATItemID = ePrice.getAttributeValue (ATTR_VATITEM);
    final IVATItem aVATItem = VATManager.getDefaultInstance ().getVATItemOfID (sVATItemID);
    if (aVATItem == null)
      throw new IllegalStateException ("Failed to resolve VAT item with ID '" + sVATItemID + "'");

    return new ReadOnlyPrice (eCurrency, aNetAmount, aVATItem);
  }
}
