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
package com.helger.tenancy.uitext;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;

/**
 * Test class for class {@link IHasUIText}
 *
 * @author Philip Helger
 */
public final class IHasUITextTest
{
  private static final Locale L_EN = Locale.ENGLISH;
  private static final Locale L_DE = Locale.GERMAN;

  private static final class MockUIText implements IHasUIText
  {
    private final String m_sTextEN;
    private final String m_sTextDE;

    MockUIText (@NonNull final String sTextEN, @NonNull final String sTextDE)
    {
      m_sTextEN = sTextEN;
      m_sTextDE = sTextDE;
    }

    @NonNull
    public String getAsUIText (@NonNull final Locale aDisplayLocale)
    {
      return L_DE.equals (aDisplayLocale) ? m_sTextDE : m_sTextEN;
    }
  }

  @NonNull
  private static ICommonsList <IHasUIText> _list ()
  {
    return new CommonsArrayList <> (new MockUIText ("Zebra", "Zebra"),
                                    new MockUIText ("apple", "Apfel"),
                                    new MockUIText ("Banana", "Banane"));
  }

  @Test
  public void testComparatorCollating ()
  {
    final ICommonsList <IHasUIText> aList = _list ();
    aList.sort (IHasUIText.getComparatorCollating (L_EN, L_EN));

    // Collating sorting is case insensitive - a plain String comparison would
    // put "apple" last
    assertEquals ("apple", aList.get (0).getAsUIText (L_EN));
    assertEquals ("Banana", aList.get (1).getAsUIText (L_EN));
    assertEquals ("Zebra", aList.get (2).getAsUIText (L_EN));
  }

  @Test
  public void testComparatorCollatingUsesContentLocale ()
  {
    final ICommonsList <IHasUIText> aList = _list ();
    aList.sort (IHasUIText.getComparatorCollating (L_DE, L_DE));

    assertEquals ("Apfel", aList.get (0).getAsUIText (L_DE));
    assertEquals ("Banane", aList.get (1).getAsUIText (L_DE));
    assertEquals ("Zebra", aList.get (2).getAsUIText (L_DE));
  }

  @Test
  public void testComparatorCollatingNullSortLocale ()
  {
    final ICommonsList <IHasUIText> aList = _list ();
    aList.sort (IHasUIText.getComparatorCollating (L_EN, null));
    assertEquals (3, aList.size ());
  }
}
