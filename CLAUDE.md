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

- **ph-masterdata** — Core library with business objects for addresses, currencies, VAT, EAN/GTIN, ISBN, IBAN, SWIFT/BIC, telephone numbers, postal codes, companies, persons, DIN paper sizes, trade/incoterms, and more. 147 source files across 25 packages under `com.helger.masterdata.*`.
- **ph-tenancy** — Multi-tenancy support (tenant/Mandant, accounting areas, UI text). 23 source files under `com.helger.tenancy.*`. Depends on ph-masterdata.

### Key Design Patterns

- **Interface-driven**: Business objects implement `I*` interfaces (e.g., `IPostalAddress`, `IVATItem`, `ITenantObject`).
- **Microtype converters**: XML serialization via the ph-commons MicroTypeConverter registry, using the Microdom XML framework (not JAXB/DOM).
- **Codelist resources**: Static reference data lives in `ph-masterdata/src/main/resources/codelists/` as XML files (VAT rates, IBAN rules, dial codes, postal codes, NUTS regions, ISO language codes, etc.).
- **Null-safety annotations**: Uses JSpecify `@Nullable`/`@NonNull` annotations throughout.
- **OSGi bundles**: Both modules are packaged as OSGi bundles via `maven-bundle-plugin`.

### Key Dependencies

Built on the `com.helger.commons` library ecosystem (ph-commons, ph-xml, ph-datetime). Parent POM is `com.helger:parent-pom`. Tests use JUnit 4.
