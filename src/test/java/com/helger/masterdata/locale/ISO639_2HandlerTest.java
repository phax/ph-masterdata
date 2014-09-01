/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.masterdata.locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Test class for class {@link ISO639_2Handler}.
 * 
 * @author Philip Helger
 */
public final class ISO639_2HandlerTest
{
  @Test
  public void testDefaultInstance ()
  {
    final ISO639_2Handler aHdl = ISO639_2Handler.getDefaultInstance ();
    assertNotNull (aHdl);
    assertNotNull (aHdl.getItemOfAlpha3Code ("ger"));
    assertNull (aHdl.getItemOfAlpha3TerminologicCode ("ger"));
    assertSame (aHdl.getItemOfAlpha3Code ("ger"), aHdl.getItemOfAlpha2Code ("de"));
  }
}
