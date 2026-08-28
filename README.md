# ph-masterdata

<!-- ph-badge-start -->
[![Sonatype Central](https://maven-badges.sml.io/sonatype-central/com.helger.masterdata/ph-masterdata-parent-pom/badge.svg)](https://maven-badges.sml.io/sonatype-central/com.helger.masterdata/ph-masterdata-parent-pom/)
[![javadoc](https://javadoc.io/badge2/com.helger.masterdata/ph-masterdata/javadoc.svg)](https://javadoc.io/doc/com.helger.masterdata/ph-masterdata)

> If this project saved you some time or made your day a little easier, a star would mean a lot — it helps others find it too.
<!-- ph-badge-end -->

Java library with lots of default business objects and algorithms:
* Postal address data
* Company and site descriptors
* Currency handling
* DIN sizes
* EAN/GLN code handling
* Extended email address handling
* ISBN algorithms
* Extended locale handling (continent and EU country handling)
* Person descriptor
* Postal code handling
* Price and currency value handling
* IBAN and BIC handling
* Tax category and type handling
* Telephone number handling
* Incoterms support
* Units of measure (work in progress)
* VAT and VATIN handling
* Vehicle sign handling 

# Maven usage

Add the following to your pom.xml to use this artifact, replacing `x.y.z` with the effective version number:

```xml
<dependency>
  <groupId>com.helger.masterdata</groupId>
  <artifactId>ph-masterdata</artifactId>
  <version>x.y.z</version>
</dependency>
```

```xml
<dependency>
  <groupId>com.helger.masterdata</groupId>
  <artifactId>ph-tenancy</artifactId>
  <version>x.y.z</version>
</dependency>
```

```xml
<dependency>
  <groupId>com.helger.masterdata</groupId>
  <artifactId>ph-tenancy-accarea</artifactId>
  <version>x.y.z</version>
</dependency>
```

# News and noteworthy

v8.2.1 - work in progress
* Updated the `EGS1Prefix` code list based on the GS1 Company Prefix list and the latest Wikipedia data
    * Added 11 new GS1 country prefixes: `381` (Kosovo), `605` (Uganda), `606` (Angola), `607` (Oman), `617` (Cameroon), `630` (Qatar), `631` (Namibia), `632` (Rwanda), `680-681` (China), `883` (Myanmar) and `887` (Laos)
    * Added the `EGS1Prefix` entries `X17` (610), `X18` (614), `X19` (758) and `X20` (894) for the prefixes that GS1 manages for a future Member Organisation. Note: Wikipedia lists `894` as Bangladesh, but the GS1 Company Prefix list does not
    * Added `EGS1Prefix.X14` for the prefix 952 that is used for demonstrations and examples of the GS1 system
    * Fixed the country codes of `EGS1Prefix` to be ISO 3166-1 alpha-2 conformant - the entries `TK` (Turkey), `CN_TP` (Taiwan), `CN_HK` (Hong Kong) and `CN_MO` (Macau) were renamed to `TR`, `TW`, `HK` and `MO`
    * `EGS1Prefix` prefixes assigned to more than one country now list all of them - added the country codes `MC` (300-379), `LU` (540-549), `FO` and `GL` (570-579), `LI` (760-769), `SM` and `VA` (800-839) and `AD` (840-849)
    * Added new method `EGS1Prefix.getAllCountryCodes ()` returning all countries of a prefix - `EGS1Prefix.getCountryCode ()` now returns the primary country code only
    * Added new method `EGS1Prefix.getCountryCodeFromCode (String)` as a shortcut to resolve the country of a GS1 identifier
    * Renamed `EGS1Prefix.CN` to `EGS1Prefix.CN_2` because the newly added prefix 680-681 uses `CN_1`
    * Replaced `EGS1Prefix.BN` (Brunei) with `EGS1Prefix.X13` because the prefix 623 is managed by the GS1 Global Office since 2021-05
    * Updated `EGS1Prefix` prefix 880 to 880-881 (South Korea), 981-984 to 981-983 and 99 to 990-999
    * Split the `EGS1Prefix` GTIN-8 prefix 960-969 into `X7` (9600-9624, GS1 UK), `X15` (9625-9626, GS1 Poland) and `X16` (9627-9699, GS1 Global Office) as defined in note 3 of the GS1 Company Prefix list
* Aligned the `EGS1Prefix` descriptions with the GS1 Company Prefix list - most visibly 760-769 is now "GS1 Switzerland" and 868-869 is now "GS1 Türkiye"
* `EGS1Prefix.getPrefixFromCode (String)` uses a lookup map instead of iterating all prefixes on every call
* Added `EGS1Prefix` test coverage for all prefixes, ensuring that no prefix is claimed by more than one entry

v8.2.0 - 2026-08-12
* Added new submodule `ph-tenancy-accarea` containing the package `com.helger.tenancy.accarea` that was previously part of `ph-tenancy`. The package name is unchanged, so only the Maven dependency needs to be added
* `ph-tenancy` no longer depends on `ph-masterdata` and `ph-xml` - the remaining dependencies are `ph-text` and `ph-datetime`
* Removed OSGI bundling
* Updated the IBAN country data code list based on latest Wikipedia data
* Added 23 new IBAN countries: Belarus, Burundi, Djibouti, East Timor, Egypt, El Salvador, Falkland Islands, Honduras, Iraq, Kosovo, Libya, Mongolia, Nicaragua, Oman, Russia, Saint Lucia, Sao Tome and Principe, Seychelles, Somalia, Sudan, Ukraine, Vatican City, Yemen
* Updated IBAN data for Costa Rica (length 21 to 22), North Macedonia (name), Serbia (check digits), Bulgaria, Sweden, Guatemala
* Added IBAN test coverage for all supported countries
* Added test coverage for `ph-tenancy` - `AbstractBusinessObject`, `AbstractHasTenant`, `AbstractTenantObject`, `IBusinessObject`, `IHasTenant`, `IHasTrashInfo`, `IHasUIText` and `ITenant`

v8.1.1 - 2026-03-27
* Fixed `IVATItem.hasPercentage` returning false if the percentage to compare has a different scale. See [#3](https://github.com/phax/ph-masterdata/pull/3) - thx @domids

v8.1.0 - 2025-11-16
* Updated to ph-commons 12.1.0
* Using JSpecify annotations

v8.0.2 - 2025-09-21
* Updated Belgium VAT number check to include numbers starting with '1'. See [#2](https://github.com/phax/ph-masterdata/pull/2) - thx @Karting06 

v8.0.1 - 2025-09-18
* Fixed some `BigDecimal` equals implementations

v8.0.0 - 2025-08-25
* Requires Java 17 as the minimum version
* Updated to ph-commons 12.0.0

v7.0.2 - 2024-03-27
* Updated to ph-commons 11.1.5
* Created Java 21 compatibility

v7.0.1 - 2023-07-31
* Updated to ph-commons 11.1

v7.0.0 - 2023-01-08
* Using Java 11 as the baseline
* Updated to ph-commons 11

v6.2.4 - 2022-04-21
* Updated the ISO 639-2 list to the latest version
* Added support for NUTS (Nomenclature of territorial units for statistics) data (version 2021)
* Added support for NUTS LAU (Local Administrative Units) data (version 2021)

v6.2.3 - 2022-01-14
* Updated international postal code list

v6.2.2 - 2021-12-13
* Added support for validating Leitweg IDs

v6.2.1 - 2021-09-17
* Updated VAT country data

v6.2.0 - 2021-03-22
* Updated to ph-commons 10
* Changed the Maven group ID to `com.helger.masterdata`

v6.1.10 - 2021-01-07
* Updated VAT country data

v6.1.9 - 2021-01-04
* Extended the `EEUCountry` enum with a "leave date" to represent UK leaving the EU

v6.1.8 - 2020-09-17
* Updated VAT rates for Germany (added 5%)
* Updated to Jakarta JAXB 2.3.3

v6.1.7 - 2020-06-22
* Updated VAT rates for Austria (added 5%)
* Updated postal codes for Austria

v6.1.6 - 2020-06-10
* Updated VAT rates for Germany (added 16%)

v6.1.5 - 2020-03-29
* Updated to ph-commons 9.4.0

v6.1.4 - 2020-03-12
* Added GS1 common prefix list to determine the country of a GLN number
* Added new Dutch VAT number algorithm

v6.1.3 - 2019-02-09
* Added serializability of base classes

v6.1.2 - 2018-11-22
* Updated to ph-commons 9.2.0

v6.1.1 - 2018-10-01
* Added `IBusinessObject.isNotDeleted`
* Added some APIs in `Tenant` and `AccountingArea` areas

v6.1.0 - 2018-07-24
* Fixed OSGI ServiceProvider configuration
* Updated 5305 code list to version D16B
* Renamed `EVATType` to `EVATItemType` (incompatible change)
* Removed ID from class `Person`
* Fixed code consistency issues
* Extracted mutable parts of `ECurrency` into class `CurrencyHelper` (incompatible change)

v6.0.0 - 2017-12-14
* Added country Locale to VAT item
* Updated vehicle sign list and API
* Updated to ph-commons 9.0.0
* Removed ph-validation and ph-masterdata-validation as they were badly designed
* Added new subproject ph-tenancy

v5.0.5 - 2017-05-30
* Requires at least ph-common 8.6.5
* API extensions

v5.0.4 - 2017-04-20
* Added VATIN checksum routines to validate the checksum character(s) in VATINs without a service call
* Requires at least ph-common 8.6.x

v5.0.3 - 2016-10-21
* Small performance tweaks
* Requires at least ph-common 8.5.2

v5.0.2 - 2016-09-12
* Binds to ph-commons 8.5.x

v5.0.0
* Binds to ph-commons 8.x
* Requires JDK 8

v4.0.0
* Binds to ph-commons 6.x        

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
It is appreciated if you star the GitHub project if you like it.
