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

# ph-validation

Java library for validation artifacts (errors etc.)

# ph-masterdata-validation

Java library that combines ph-masterdata and ph-validation. The same version requirements need to be matched there.

# News and noteworthy

  * v5.0.6 - work in progress
    * Added country Locale to VAT item
    * Updated vehicle sign list and API
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

# Maven usage
Add the following to your pom.xml to use this artifact:
```xml
<dependency>
  <groupId>com.helger</groupId>
  <artifactId>ph-masterdata</artifactId>
  <version>5.0.5</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>ph-validation</artifactId>
  <version>5.0.5</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>ph-masterdata-validation</artifactId>
  <version>5.0.5</version>
</dependency>
```

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodeingStyleguide.md) |
On Twitter: <a href="https://twitter.com/philiphelger">@philiphelger</a>
