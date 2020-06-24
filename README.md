# ph-masterdata

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
  <groupId>com.helger</groupId>
  <artifactId>ph-masterdata</artifactId>
  <version>x.y.z</version>
</dependency>
```

```xml
<dependency>
  <groupId>com.helger</groupId>
  <artifactId>ph-tenancy</artifactId>
  <version>x.y.z</version>
</dependency>
```

# News and noteworthy

* v6.1.8 - work in progress
    * Updated VAT rates for Germany (added 5%)
* v6.1.7 - 2020-06-22
    * Updated VAT rates for Austria (added 5%)
    * Updated postal codes for Austria
* v6.1.6 - 2020-06-10
    * Updated VAT rates for Germany (added 16%)
* v6.1.5 - 2020-03-29
    * Updated to ph-commons 9.4.0
* v6.1.4 - 2020-03-12
    * Added GS1 common prefix list to determine the country of a GLN number
    * Added new Dutch VAT number algorithm
* v6.1.3 - 2019-02-09
    * Added serializability of base classes
* v6.1.2 - 2018-11-22
    * Updated to ph-commons 9.2.0
* v6.1.1 - 2018-10-01
    * Added `IBusinessObject.isNotDeleted`
    * Added some APIs in `Tenant` and `AccountingArea` areas
* v6.1.0 - 2018-07-24
    * Fixed OSGI ServiceProvider configuration
    * Updated 5305 code list to version D16B
    * Renamed `EVATType` to `EVATItemType` (incompatible change)
    * Removed ID from class `Person`
    * Fixed code consistency issues
    * Extracted mutable parts of `ECurrency` into class `CurrencyHelper` (incompatible change)
* v6.0.0 - 2017-12-14
    * Added country Locale to VAT item
    * Updated vehicle sign list and API
    * Updated to ph-commons 9.0.0
    * Removed ph-validation and ph-masterdata-validation as they were badly designed
    * Added new subproject ph-tenancy
* v5.0.5 - 2017-05-30
    * Requires at least ph-common 8.6.5
    * API extensions
* v5.0.4 - 2017-04-20
    * Added VATIN checksum routines to validate the checksum character(s) in VATINs without a service call
    * Requires at least ph-common 8.6.x
* v5.0.3 - 2016-10-21
    * Small performance tweaks
    * Requires at least ph-common 8.5.2
* v5.0.2 - 2016-09-12
    * Binds to ph-commons 8.5.x
* v5.0.0
    * Binds to ph-commons 8.x
    * Requires JDK 8
* v4.0.0
    * Binds to ph-commons 6.x        

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
On Twitter: <a href="https://twitter.com/philiphelger">@philiphelger</a> |
Kindly supported by [YourKit Java Profiler](https://www.yourkit.com)