/*
 * Copyright (C) 2014-2022 Philip Helger (www.helger.com)
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

import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;

public final class ESalutationTest
{
  private static final Locale L_DE = new Locale ("de");

  @Test
  public void testGetText ()
  {
    final String sDisplayName = ESalutation.COMPANY.getDisplayText (L_DE);
    assertNotNull (sDisplayName);
  }
}
