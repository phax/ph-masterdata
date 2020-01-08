/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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
package com.helger.masterdata.telephone;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.string.StringHelper;
import com.helger.xml.microdom.util.XMLMapHandler;

/**
 * Contains a list of dial up codes.
 *
 * @author Philip Helger
 */
@Immutable
public final class DialCodeManager
{
  private static final ICommonsMap <String, String> s_aCountryToDialCode = new CommonsHashMap <> ();

  static
  {
    if (XMLMapHandler.readMap (new ClassPathResource ("codelists/dialcode-country-data.xml"), s_aCountryToDialCode)
                     .isFailure ())
      throw new InitializationException ("Failed to init dial code country data");
  }

  private DialCodeManager ()
  {}

  /**
   * Get the dial code for the specified country (in the ISO-3166 two letter
   * type).
   *
   * @param sCountry
   *        The country code. Must be 2 characters long.
   * @return <code>null</code> if no mapping exists.
   */
  @Nullable
  public static String getDialCodeOfCountry (@Nullable final String sCountry)
  {
    if (StringHelper.hasNoText (sCountry))
      return null;
    return s_aCountryToDialCode.get (sCountry.toUpperCase (Locale.US));
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsMap <String, String> getAllDialCodes ()
  {
    return s_aCountryToDialCode.getClone ();
  }
}
