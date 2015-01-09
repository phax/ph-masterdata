/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.helger.commons.mock.AbstractPHTestCase;
import com.helger.commons.mock.PHTestUtils;

/**
 * Test class for class {@link ReadonlyTelephoneNumber}
 *
 * @author Philip Helger
 */
public final class ReadonlyTelephoneNumberTest extends AbstractPHTestCase
{
  @Test
  public void testBasic ()
  {
    ReadonlyTelephoneNumber a = new ReadonlyTelephoneNumber (null, null, null, null, null);
    assertNull (a.getType ());
    assertNull (a.getCountryCode ());
    assertNull (a.getAreaCode ());
    assertNull (a.getLine ());
    assertNull (a.getDirectDial ());

    a = new ReadonlyTelephoneNumber (ETelephoneType.OFFICE, "0043", "01", "1231234", "47");
    assertEquals (ETelephoneType.OFFICE, a.getType ());
    assertEquals ("0043", a.getCountryCode ());
    assertEquals ("01", a.getAreaCode ());
    assertEquals ("1231234", a.getLine ());
    assertEquals ("47", a.getDirectDial ());
  }

  @Test
  public void testDefaultImpl ()
  {
    final ReadonlyTelephoneNumber a = new ReadonlyTelephoneNumber (ETelephoneType.OFFICE, "0043", "01", "1231234", "47");
    PHTestUtils.testDefaultImplementationWithEqualContentObject (a, new ReadonlyTelephoneNumber (ETelephoneType.OFFICE,
                                                                                                 "0043",
                                                                                                 "01",
                                                                                                 "1231234",
                                                                                                 "47"));
    PHTestUtils.testDefaultImplementationWithEqualContentObject (a, new ReadonlyTelephoneNumber (a));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (a,
                                                                     new ReadonlyTelephoneNumber (ETelephoneType.OFFICE_FAX,
                                                                                                  "0043",
                                                                                                  "01",
                                                                                                  "1231234",
                                                                                                  "47"));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (a,
                                                                     new ReadonlyTelephoneNumber (ETelephoneType.OFFICE,
                                                                                                  "0049",
                                                                                                  "01",
                                                                                                  "1231234",
                                                                                                  "47"));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (a,
                                                                     new ReadonlyTelephoneNumber (ETelephoneType.OFFICE,
                                                                                                  "0043",
                                                                                                  "02",
                                                                                                  "1231234",
                                                                                                  "47"));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (a,
                                                                     new ReadonlyTelephoneNumber (ETelephoneType.OFFICE,
                                                                                                  "0043",
                                                                                                  "01",
                                                                                                  "9988776",
                                                                                                  "47"));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (a,
                                                                     new ReadonlyTelephoneNumber (ETelephoneType.OFFICE,
                                                                                                  "0043",
                                                                                                  "01",
                                                                                                  "1231234",
                                                                                                  "52"));
  }
}
