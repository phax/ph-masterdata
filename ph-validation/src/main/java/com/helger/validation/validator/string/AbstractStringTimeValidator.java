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
package com.helger.validation.validator.string;

import java.time.LocalTime;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.datetime.PDTFormatPatterns;
import com.helger.commons.datetime.PDTFromString;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

/**
 * Base class for validating time values.
 *
 * @author Philip Helger
 */
@Immutable
public abstract class AbstractStringTimeValidator extends AbstractStringValidator
{
  @Nonnull
  protected abstract Locale getParseLocale ();

  @Nonnull
  public final IValidationResult validate (@Nullable final String sValue)
  {
    final Locale aParseLocale = getParseLocale ();
    final LocalTime aDate = PDTFromString.getLocalTimeFromString (sValue, aParseLocale);
    if (aDate != null)
      return ValidationResultSuccess.getInstance ();
    final Object [] aArgs = { PDTFormatPatterns.getDefaultPatternTime (aParseLocale) };
    return ValidationResultError.create (EStandardValidationErrorTexts.INVALID_TIME, aArgs);
  }
}
