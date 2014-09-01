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
package com.helger.masterdata.swift;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link BICManager}.
 * 
 * @author Philip Helger
 */
public final class BICManagerTest
{
  @Test
  public void testIsValid ()
  {
    assertTrue (BICManager.isValidBIC ("BBBBCCLLbbb"));
    assertTrue (BICManager.isValidBIC ("BBBBCCLL"));
    assertFalse (BICManager.isValidBIC ("BBBBCCL"));
    assertFalse (BICManager.isValidBIC ("12345678"));
    assertFalse (BICManager.isValidBIC (""));
    assertFalse (BICManager.isValidBIC (null));

    assertTrue (BICManager.isValidBIC ("RBOSGGSX"));
    assertTrue (BICManager.isValidBIC ("RZTIAT22263"));
    assertTrue (BICManager.isValidBIC ("BCEELULL"));
    assertTrue (BICManager.isValidBIC ("MARKDEFF"));
    assertTrue (BICManager.isValidBIC ("GENODEF1JEV"));
    assertTrue (BICManager.isValidBIC ("UBSWCHZH80A"));
    assertTrue (BICManager.isValidBIC ("CEDELULLXXX"));
    assertTrue (BICManager.isValidBIC ("HELADEF1RRS"));
  }

  @Test
  public void testIsPassive ()
  {
    assertFalse (BICManager.isPassiveBICParticipant ("RBOSGGSX"));
    assertFalse (BICManager.isPassiveBICParticipant ("RZTIAT22263"));
    assertFalse (BICManager.isPassiveBICParticipant ("BCEELULL"));
    assertFalse (BICManager.isPassiveBICParticipant ("MARKDEFF"));
    assertTrue (BICManager.isPassiveBICParticipant ("GENODEF1JEV"));
    assertFalse (BICManager.isPassiveBICParticipant ("UBSWCHZH80A"));
    assertFalse (BICManager.isPassiveBICParticipant ("CEDELULLXXX"));
    assertTrue (BICManager.isPassiveBICParticipant ("HELADEF1RRS"));
  }

  @Test
  public void testIsTest ()
  {
    assertFalse (BICManager.isTestBIC ("RBOSGGSX"));
    assertFalse (BICManager.isTestBIC ("RZTIAT22263"));
    assertFalse (BICManager.isTestBIC ("BCEELULL"));
    assertFalse (BICManager.isTestBIC ("MARKDEFF"));
    assertFalse (BICManager.isTestBIC ("GENODEF1JEV"));
    assertFalse (BICManager.isTestBIC ("UBSWCHZH80A"));
    assertFalse (BICManager.isTestBIC ("CEDELULLXXX"));
    assertFalse (BICManager.isTestBIC ("HELADEF1RRS"));

    assertTrue (BICManager.isTestBIC ("HELADEF0RRS"));
  }
}
