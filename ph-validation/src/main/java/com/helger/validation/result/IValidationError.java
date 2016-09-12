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
package com.helger.validation.result;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.error.IError;
import com.helger.commons.error.SingleError;

/**
 * A single validation error.
 *
 * @author Philip Helger
 */
public interface IValidationError extends IValidationResult
{
  /**
   * Get this validation error as an {@link IError}.
   *
   * @param sFieldName
   *        The current field name.
   * @param aContentLocale
   *        The local used to retrieve the texts.
   * @return Never <code>null</code>.
   * @since 5.0.2
   */
  @Nonnull
  default IError getAsError (@Nonnull final String sFieldName, @Nonnull final Locale aContentLocale)
  {
    return SingleError.builderError ()
                      .setErrorID (getErrorID ())
                      .setErrorFieldName (sFieldName)
                      .setErrorText (getDisplayText (aContentLocale))
                      .build ();
  }
}
