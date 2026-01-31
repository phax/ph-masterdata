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
package com.helger.masterdata.vat;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Interface for objects having VAT items.
 *
 * @author Philip Helger
 */
public interface IVATItemProvider
{
  /**
   * Get the VAT type with the given ID.
   *
   * @param sID
   *        The VAT type ID to search.
   * @return <code>null</code> if no such VAT type exists.
   */
  @Nullable
  IVATItem getVATItemOfID (@Nullable String sID);

  /**
   * Get the VAT type with the given ID.
   *
   * @param aCountry
   *        The country for which the locale is required.
   * @param sID
   *        The VAT type ID to search.
   * @return <code>null</code> if no such VAT type exists.
   */
  @Nullable
  IVATItem getVATItemOfID (@NonNull Locale aCountry, @Nullable String sID);
}
