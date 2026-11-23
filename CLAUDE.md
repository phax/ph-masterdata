# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
mvn install                # Build all modules and run tests
mvn test                   # Run all tests
mvn test -pl ph-masterdata # Run tests for ph-masterdata module only
mvn test -pl ph-tenancy    # Run tests for ph-tenancy module only
mvn test -pl ph-masterdata -Dtest=VATINStructureTest  # Run a single test class
mvn clean install          # Clean build
```

Requires Java 17+ and Maven 3.9+. CI tests against Java 17, 21, and 25.

## Architecture

This is a Java library providing standard business master data objects, published to Maven Central as `com.helger.masterdata`.

### Modules

- **ph-masterdata-bom** — Maven BOM (Bill of Materials) managing the versions of all ph-masterdata artifacts. Must be updated when a module is added or removed.
- **ph-masterdata** — Core library with business objects for addresses, currencies, VAT, EAN/GTIN, ISBN, IBAN, SWIFT/BIC, telephone numbers, postal codes, companies, persons, DIN paper sizes, trade/incoterms, and more. 157 source files across 26 packages under `com.helger.masterdata.*`. Check digit algorithms (ISO 7064, Luhn, GS1, weighted modulus) live in `com.helger.masterdata.checkdigit`.
- **ph-tenancy** — Multi-tenancy support (tenant/Mandant, date/time, UI text). 15 source files under `com.helger.tenancy.*`.
- **ph-tenancy-accarea** — Accounting areas within multi-tenancy. 8 source files under `com.helger.tenancy.accarea`. Depends on ph-tenancy and ph-masterdata.

### Key Design Patterns

- **Interface-driven**: Business objects implement `I*` interfaces (e.g., `IPostalAddress`, `IVATItem`, `ITenantObject`).
- **Microtype converters**: XML serialization via the ph-commons MicroTypeConverter registry, using the Microdom XML framework (not JAXB/DOM).
- **Code list resources**: Static reference data lives in `ph-masterdata/src/main/resources/codelists/` as XML files (VAT rates, IBAN rules, dial codes, postal codes, NUTS regions, ISO language codes, etc.).
- **Null-safety annotations**: Uses JSpecify `@Nullable`/`@NonNull` annotations throughout.
- **Characterization tests**: `*CharacterizationTest` classes pin the behaviour of existing check digit code with a copy of the old implementation as oracle. Intended behaviour changes are documented in the class Javadoc and in the release notes.
- **Packaging**: All modules use plain `jar` packaging. The jars carry no OSGi bundle headers (`Bundle-*`, `Export-Package`) - the `maven-bundle-plugin` of the parent POM is not active here.

### Key Dependencies

Built on the `com.helger.commons` library ecosystem (ph-commons, ph-xml, ph-datetime). Parent POM is `com.helger:parent-pom`. Tests use JUnit 4. Property based tests use `ph-unittest-property` (`PropertyChecker`, `Generators`) with a fixed seed - no jqwik or JUnit Platform.
