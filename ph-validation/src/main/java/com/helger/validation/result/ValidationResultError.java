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

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.text.display.HasDisplayTextWithArgs;
import com.helger.commons.text.display.IHasDisplayText;

/**
 * Default implementation of the {@link IValidationResult} interface for errors.
 *
 * @author Philip Helger
 */
@Immutable
public class ValidationResultError extends AbstractValidationResultError
{
  private final IHasDisplayText m_aErrorText;

  public ValidationResultError (@Nonnull final IHasDisplayText aErrorText)
  {
    m_aErrorText = ValueEnforcer.notNull (aErrorText, "ErrorText");
  }

  @Deprecated
  public ValidationResultError (@Nonnull final IHasDisplayText aErrorText, @Nullable final Object aArg)
  {
    this (aErrorText, aArg == null ? (Object []) null : new Object [] { aArg });
  }

  @Deprecated
  public ValidationResultError (@Nonnull final IHasDisplayText aErrorText, @Nullable final Object... aArgs)
  {
    this (ArrayHelper.isEmpty (aArgs) ? aErrorText : new HasDisplayTextWithArgs (aErrorText, aArgs));
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return m_aErrorText.getDisplayText (aContentLocale);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("errorText", m_aErrorText).getToString ();
  }

  @Nonnull
  public static ValidationResultError create (@Nonnull final IHasDisplayText aErrorText, @Nullable final Object aArg)
  {
    return create (aErrorText, aArg == null ? (Object []) null : new Object [] { aArg });
  }

  @Nonnull
  public static ValidationResultError create (@Nonnull final IHasDisplayText aErrorText,
                                              @Nullable final Object... aArgs)
  {
    ValueEnforcer.notNull (aErrorText, "ErrorText");
    return new ValidationResultError (ArrayHelper.isEmpty (aArgs) ? aErrorText
                                                                  : new HasDisplayTextWithArgs (aErrorText, aArgs));
  }
}
