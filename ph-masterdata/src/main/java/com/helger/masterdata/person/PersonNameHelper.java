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
package com.helger.masterdata.person;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.array.ArrayHelper;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringImplode;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;

@Immutable
public final class PersonNameHelper
{
  /** By default complex name handling is disabled */
  public static final boolean DEFAULT_COMPLEX_NAME_HANDLING = false;
  /** By default a persons first name comes before the last name */
  public static final boolean DEFAULT_FIRST_NAME_FIRST = true;

  private static final String [] NOBILIARY_PARTICLES = { "aw",
                                                         "da",
                                                         "dalla",
                                                         "de",
                                                         "degli",
                                                         "del",
                                                         "dem",
                                                         "der",
                                                         "di",
                                                         "du",
                                                         "of",
                                                         "ter",
                                                         "thoe",
                                                         "tot",
                                                         "und",
                                                         "v.",
                                                         "van",
                                                         "vom",
                                                         "von",
                                                         "zu",
                                                         "zum" };

  private static final AtomicBoolean COMPLEX_NAME_HANDLING_ENABLED = new AtomicBoolean (DEFAULT_COMPLEX_NAME_HANDLING);
  private static final AtomicBoolean FIRST_NAME_FIRST = new AtomicBoolean (DEFAULT_FIRST_NAME_FIRST);

  private PersonNameHelper ()
  {}

  public static void setComplexNameHandlingEnabled (final boolean bEnabled)
  {
    COMPLEX_NAME_HANDLING_ENABLED.set (bEnabled);
  }

  public static boolean isComplexNameHandlingEnabled ()
  {
    return COMPLEX_NAME_HANDLING_ENABLED.get ();
  }

  public static void setFirstNameFirst (final boolean bFirstNameFirst)
  {
    FIRST_NAME_FIRST.set (bFirstNameFirst);
  }

  /**
   * Determine the order how the customer display name is assembled. This was introduced for
   * starkl.hu as they want the lastname before the firstname
   *
   * @return <code>true</code> if the customer display name is "firstname lastname".
   *         <code>false</code> if the customer display name is "lastname firstname"
   */
  public static boolean isFirstNameFirst ()
  {
    return FIRST_NAME_FIRST.get ();
  }

  /**
   * Unify a single name part. Performs the following transformations:
   * <ul>
   * <li>Remove leading and trailing whitespaces</li>
   * <li>If the name is all upper case, down case all except the first character</li>
   * </ul>
   *
   * @param sPart
   *        The string part to be unified
   * @param aSortLocale
   *        The local to use (for case changing)
   * @return The unified part
   */
  @Nullable
  private static String _unifySinglePart (@NonNull final String sPart, @NonNull final Locale aSortLocale)
  {
    // empty name?
    String s = sPart.trim ();
    final int nLength = s.length ();
    if (nLength == 0)
      return null;

    // all upper case name?
    if (nLength == 1)
      return s.toUpperCase (aSortLocale);

    // uppercase first only
    s = s.substring (0, 1).toUpperCase (aSortLocale) + s.substring (1).toLowerCase (aSortLocale);

    // special cases: nobiliary particles ;-)
    if (ArrayHelper.contains (NOBILIARY_PARTICLES, s))
      s = s.toLowerCase (aSortLocale);

    return s;
  }

  @Nullable
  public static String unifyName (@Nullable final String sName, @NonNull final Locale aSortLocale)
  {
    if (sName == null)
      return null;

    if (!isComplexNameHandlingEnabled ())
    {
      // Use old compatible name handling: to upper first character
      if (sName.length () <= 1)
        return sName.toUpperCase (aSortLocale);
      return sName.substring (0, 1).toUpperCase (aSortLocale) + sName.substring (1);
    }

    // empty name?
    String s = _unifySinglePart (sName, aSortLocale);
    if (s == null)
      return null;

    // double name handling ("Hans-Peter" or "Hans Peter")
    for (final char cSep : new char [] { ' ', '-' })
    {
      final String [] aParts = StringHelper.getExplodedArray (cSep, s);
      if (aParts.length > 1)
      {
        final StringBuilder aSB = new StringBuilder (s.length ());
        for (final String sPart : aParts)
        {
          final String sRealPart = _unifySinglePart (sPart, aSortLocale);
          if (sRealPart == null)
          {
            // Ignore all empty parts (e.g. "Kai Uwe" with 2 spaces between
            continue;
          }

          if (aSB.length () > 0)
            aSB.append (cSep);
          aSB.append (sRealPart);
        }
        s = aSB.toString ();
      }
    }

    return s;
  }

  @NonNull
  public static String getAsDisplayNameFirstNameFirst (@NonNull final IPersonName aName)
  {
    // Concatenate all non-empty parts
    return StringImplode.imploder ()
                        .source (aName.getFirstName (), aName.getMiddleName (), aName.getLastName ())
                        .separator (' ')
                        .filterNonEmpty ()
                        .build ();
  }

  @NonNull
  public static String getAsDisplayNameLastNameFirst (@NonNull final IPersonName aName)
  {
    // Concatenate all non-empty parts
    return StringImplode.imploder ()
                        .source (aName.getLastName (), aName.getFirstName (), aName.getMiddleName ())
                        .separator (' ')
                        .filterNonEmpty ()
                        .build ();
  }

  /**
   * Get the name of the person consisting of first name, middle name and last name. Titles are not
   * considered here. {@link #isFirstNameFirst()} is considered!
   *
   * @param aName
   *        The name to be converted. May not be <code>null</code>.
   * @return The non-<code>null</code> display name
   */
  @NonNull
  public static String getAsDisplayName (@NonNull final IPersonName aName)
  {
    if (isFirstNameFirst ())
      return getAsDisplayNameFirstNameFirst (aName);
    return getAsDisplayNameLastNameFirst (aName);
  }

  @NonNull
  public static String getAsCompleteDisplayNameFirstNameFirst (@NonNull final IPersonName aName)
  {
    // Concatenate all non-empty parts
    return StringImplode.imploder ()
                        .source (aName.getPrefixTitle (),
                                 aName.getFirstName (),
                                 aName.getMiddleName (),
                                 aName.getLastName (),
                                 aName.getSuffixTitle ())
                        .separator (' ')
                        .filterNonEmpty ()
                        .build ();
  }

  @NonNull
  public static String getAsCompleteDisplayNameLastNameFirst (@NonNull final IPersonName aName)
  {
    // Concatenate all non-empty parts
    return StringImplode.imploder ()
                        .source (aName.getPrefixTitle (),
                                 aName.getLastName (),
                                 aName.getFirstName (),
                                 aName.getMiddleName (),
                                 aName.getSuffixTitle ())
                        .separator (' ')
                        .filterNonEmpty ()
                        .build ();
  }

  /**
   * Get the display name of the person consisting of titles, first name, middle name and last name.
   * {@link #isFirstNameFirst()} is considered!
   *
   * @param aName
   *        The name to be converted. May not be <code>null</code>.
   * @return The non-<code>null</code> display name
   */
  @NonNull
  public static String getAsCompleteDisplayName (@NonNull final IPersonName aName)
  {
    if (isFirstNameFirst ())
      return getAsCompleteDisplayNameFirstNameFirst (aName);
    return getAsCompleteDisplayNameLastNameFirst (aName);
  }

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <String> getAllNobiliaryParticles ()
  {
    return new CommonsArrayList <> (NOBILIARY_PARTICLES);
  }
}
