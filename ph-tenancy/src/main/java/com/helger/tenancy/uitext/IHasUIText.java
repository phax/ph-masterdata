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
package com.helger.tenancy.uitext;

import java.util.Comparator;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.compare.IComparator;

/**
 * Interface for objects that have a UI display text
 *
 * @author Philip Helger
 */
public interface IHasUIText
{
  /**
   * @param aDisplayLocale
   *        Display locale. May not be <code>null</code>.
   * @return The UI text
   */
  @Nonnull
  @Nonempty
  String getAsUIText (@Nonnull Locale aDisplayLocale);

  @Nonnull
  static Comparator <IHasUIText> getComparatorCollating (@Nonnull final Locale aContentLocale,
                                                         @Nullable final Locale aSortLocale)
  {
    return IComparator.getComparatorCollating (aObject -> aObject.getAsUIText (aContentLocale), aSortLocale);
  }
}