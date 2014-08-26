/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.convert.IMicroTypeConverter;
import com.helger.commons.microdom.impl.MicroElement;

/**
 * Common base class for {@link Price} and {@link ReadonlyPrice}
 * 
 * @author Philip Helger
 */
public abstract class AbstractPriceMicroTypeConverter implements IMicroTypeConverter
{
  protected static final String ATTR_CURRENCY = "currency";
  protected static final String ATTR_NETAMOUNT = "netamount";
  protected static final String ATTR_GROSSAMOUNT = "grossamount";
  protected static final String ATTR_VATITEM = "vatitem";

  @Nonnull
  public final IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                                    @Nullable final String sNamespaceURI,
                                                    @Nonnull final String sTagName)
  {
    final IReadonlyPrice aPrice = (IReadonlyPrice) aObject;
    final IMicroElement ePrice = new MicroElement (sNamespaceURI, sTagName);
    if (aPrice.getCurrency () != null)
      ePrice.setAttribute (ATTR_CURRENCY, aPrice.getCurrency ().getID ());
    if (aPrice.getNetAmount () != null)
      ePrice.setAttribute (ATTR_NETAMOUNT, aPrice.getNetAmount ().getValue ().toString ());
    if (aPrice.getGrossAmount () != null)
      ePrice.setAttribute (ATTR_GROSSAMOUNT, aPrice.getGrossAmount ().getValue ().toString ());
    if (aPrice.getVATItem () != null)
      ePrice.setAttribute (ATTR_VATITEM, aPrice.getVATItem ().getID ());
    return ePrice;
  }
}