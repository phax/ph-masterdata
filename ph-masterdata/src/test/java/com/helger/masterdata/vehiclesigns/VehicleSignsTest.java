/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
package com.helger.masterdata.vehiclesigns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.collection.impl.CommonsLinkedHashSet;
import com.helger.commons.locale.country.CountryCache;

/**
 * Test class for class {@link VehicleSigns}.
 *
 * @author Philip Helger
 */
public final class VehicleSignsTest
{
  @Test
  public void testGetSingleVehicleSign ()
  {
    assertEquals ("A", VehicleSigns.getSingleVehicleSign ("AT"));
    assertEquals ("A", VehicleSigns.getSingleVehicleSign ("aT"));
    assertEquals ("D", VehicleSigns.getSingleVehicleSign ("DE"));
    assertNull (VehicleSigns.getSingleVehicleSign ("ZZ"));
    try
    {
      // Has 2 signs
      VehicleSigns.getSingleVehicleSign ("TZ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }

  @Test
  public void testGetAllVehicleSigns ()
  {
    assertEquals (new CommonsLinkedHashSet <> ("A"), VehicleSigns.getAllVehicleSigns ("AT"));
    assertEquals (new CommonsLinkedHashSet <> ("A"), VehicleSigns.getAllVehicleSigns ("aT"));
    assertEquals (new CommonsLinkedHashSet <> ("D"), VehicleSigns.getAllVehicleSigns ("DE"));
    assertEquals (new CommonsLinkedHashSet <> (), VehicleSigns.getAllVehicleSigns ("ZZ"));
    // Has 2 signs
    assertEquals (new CommonsLinkedHashSet <> ("EAT", "EAZ"), VehicleSigns.getAllVehicleSigns ("TZ"));
  }

  @Test
  public void testGetSingleCountryFromVehicleSign ()
  {
    final CountryCache aCC = CountryCache.getInstance ();
    assertEquals (aCC.getCountry ("AT"), VehicleSigns.getSingleCountryFromVehicleSign ("A"));
    assertNull (VehicleSigns.getSingleCountryFromVehicleSign ("XYZ"));
    try
    {
      // Has 2 countries
      VehicleSigns.getSingleCountryFromVehicleSign ("ROK");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }

  @Test
  public void testGetAllCountriesFromVehicleSign ()
  {
    final CountryCache aCC = CountryCache.getInstance ();
    assertEquals (new CommonsLinkedHashSet <> (aCC.getCountry ("AT")), VehicleSigns.getAllCountriesFromVehicleSign ("A"));
    assertEquals (new CommonsLinkedHashSet <> (), VehicleSigns.getAllCountriesFromVehicleSign ("XYZ"));
    // Has 2 countries
    assertEquals (new CommonsLinkedHashSet <> (aCC.getCountry ("KP"), aCC.getCountry ("KR")),
                  VehicleSigns.getAllCountriesFromVehicleSign ("ROK"));
  }
}
