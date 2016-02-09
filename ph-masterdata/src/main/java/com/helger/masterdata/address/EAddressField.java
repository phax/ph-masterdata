package com.helger.masterdata.address;

import java.util.Locale;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum EAddressField
{
  CARE_OF ( (a, dl) -> a.hasCareOf () ? AddressHelper.getCareOfPrefix () + a.getCareOf () : null),
  STREET ( (a, dl) -> a.hasStreet () ? a.getStreet () : null),
  BUILDING_NUMBER ( (a, dl) -> a.hasBuildingNumber () ? a.getBuildingNumber () : null),
  STREET_AND_BUILDING_NUMBER ( (a, dl) -> AddressHelper.getStreetAndBuildingNumber (a)),
  POSTAL_CODE ( (a, dl) -> a.hasPostalCode () ? a.getPostalCode () : null),
  CITY ( (a, dl) -> a.hasCity () ? a.getCity () : null),
  POSTAL_CODE_AND_CITY ( (a, dl) -> AddressHelper.getPostalCodeAndCity (a)),
  POST_OFFICE_BOX ( (a, dl) -> a.hasPostOfficeBox () ? a.getPostOfficeBox () : null),
  STATE ( (a, dl) -> a.hasState () ? a.getState () : null),
  COUNTRY ( (a, dl) -> a.hasCountry () ? a.getCountryDisplayName (dl) : null);

  private BiFunction <IAddress, Locale, String> m_aGetter;

  private EAddressField (@Nonnull final BiFunction <IAddress, Locale, String> aGetter)
  {
    m_aGetter = aGetter;
  }

  @Nullable
  public String get (@Nonnull final IAddress aAddress, @Nonnull final Locale aDisplayLocale)
  {
    return m_aGetter.apply (aAddress, aDisplayLocale);
  }
}
