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
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Abstract implementation of the {@link IValidationResult} interface for
 * success.
 *
 * @author Philip Helger
 */
@Immutable
public abstract class AbstractValidationResultSuccess implements IValidationResult
{
  public final boolean isValid ()
  {
    return true;
  }

  public final boolean isInvalid ()
  {
    return false;
  }

  @Nullable
  public String getErrorID ()
  {
    return null;
  }

  public boolean hasErrorID ()
  {
    return StringHelper.hasText (getErrorID ());
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return null;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).toString ();
  }
}
