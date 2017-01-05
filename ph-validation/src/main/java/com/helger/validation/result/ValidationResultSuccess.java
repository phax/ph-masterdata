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
package com.helger.validation.result;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.Singleton;

/**
 * Default implementation of the {@link IValidationResult} interface.
 *
 * @author Philip Helger
 */
@Immutable
@Singleton
public final class ValidationResultSuccess extends AbstractValidationResultSuccess
{
  private static final ValidationResultSuccess s_aSuccess = new ValidationResultSuccess ();

  private ValidationResultSuccess ()
  {}

  /**
   * @return The singleton for success. Never <code>null</code>.
   */
  @Nonnull
  public static IValidationResult getInstance ()
  {
    return s_aSuccess;
  }
}
