/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
package com.helger.masterdata.currency;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.convert.IMicroTypeConverter;
import com.helger.commons.microdom.impl.MicroElement;

/**
 * Common base class for {@link CurrencyValue} and {@link ReadonlyCurrencyValue}
 * 
 * @author Philip Helger
 */
public abstract class AbstractCurrencyMicroTypeConverter implements IMicroTypeConverter
{
  protected static final String ATTR_CURRENCY = "currency";
  protected static final String ATTR_VALUE = "value";

  @Nonnull
  public final IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                                    @Nullable final String sNamespaceURI,
                                                    @Nonnull final String sTagName)
  {
    final IReadonlyCurrencyValue aPrice = (IReadonlyCurrencyValue) aObject;
    final IMicroElement ePrice = new MicroElement (sNamespaceURI, sTagName);
    ePrice.setAttribute (ATTR_CURRENCY, aPrice.getCurrency ().getID ());
    ePrice.setAttributeWithConversion (ATTR_VALUE, aPrice.getValue ());
    return ePrice;
  }
}
