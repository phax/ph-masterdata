/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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
package com.helger.masterdata.company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link Company}
 *
 * @author Philip Helger
 */
public final class CompanyTest
{
  @Test
  public void testBasic ()
  {
    final Company c = new Company ("any");
    assertEquals ("any", c.getID ());
    assertTrue (c.setOfficialName ("offName").isChanged ());
    assertTrue (c.setOfficialName ("offName").isUnchanged ());
    assertTrue (c.setOfficialName ("offName2").isChanged ());
    assertTrue (c.setPublicName (null).isUnchanged ());
    assertTrue (c.setPublicName ("pubName").isChanged ());
    assertTrue (c.setPublicName ("pubName").isUnchanged ());
    assertTrue (c.setPublicName ("pubName2").isChanged ());
  }
}
