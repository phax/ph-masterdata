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
package com.helger.masterdata.tax;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link ETaxCategoryUN5305}.
 *
 * @author Philip Helger
 */
public final class ETaxCategoryUN5305Test
{
  @Test
  public void testAll ()
  {
    for (final ETaxCategoryUN5305 e : ETaxCategoryUN5305.values ())
    {
      assertTrue (StringHelper.hasText (e.getID ()));
      assertNotNull (e.getDisplayText (Locale.GERMAN));
      assertSame (e, ETaxCategoryUN5305.valueOf (e.name ()));
      assertSame (e, ETaxCategoryUN5305.getFromIDOrNull (e.getID ()));
    }
  }
}
