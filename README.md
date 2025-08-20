# ph-masterdata

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.helger.masterdata/ph-masterdata-parent-pom/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.helger.masterdata/ph-masterdata-parent-pom) 
[![javadoc](https://javadoc.io/badge2/com.helger.masterdata/ph-masterdata-parent-pom/javadoc.svg)](https://javadoc.io/doc/com.helger.masterdata/ph-masterdata-parent-pom)
[![CodeCov](https://codecov.io/gh/phax/ph-masterdata/branch/master/graph/badge.svg)](https://codecov.io/gh/phax/ph-masterdata)

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

# News and noteworthy

v8.0.0 - work in progress
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