# Java code-quality guide

Use this reference when changing Java code in InternTrack. Follow the repository's established patterns when they are more specific. The course Java standard is the baseline; use Google Java Style for topics it does not cover.

## Java conventions

- Use lowercase package names. Name classes and enums as nouns in `PascalCase`, methods as verbs in `camelCase`, and variables in `camelCase`.
- Name constants with `SCREAMING_SNAKE_CASE`. Make booleans read like questions (`isVisible`, `hasData`); use plural names for collections.
- Use four spaces for indentation, K&R braces, and braces for every loop and conditional. Keep lines below 110 characters where practical and within 120 characters.
- Prefer explicit imports. Initialize variables at declaration when practical and keep them in the smallest scope that supports clear code.
- Use descriptive English names. In tests, `featureUnderTest_scenario_expectedBehavior()` is an accepted naming pattern.
- Keep compatibility with the project's configured Java version (Java 25); do not change the toolchain to make a local implementation work.

## Code quality

- Apply SLAP: keep statements within a method at a consistent level of abstraction. Let high-level methods describe steps and move lower-level details into focused helpers when that improves clarity.
- Reconsider methods longer than about 30 lines and deeply nested logic. Prefer simpler control flow and guard clauses when they make the main path easier to follow; these are review prompts, not hard limits.
- Keep solutions simple (KISS). Avoid clever code, premature optimization, and abstractions that add indirection without a concrete use.
- Reduce meaningful duplication (DRY), but do not force unrelated behavior into a shared helper merely because it looks similar.
- Keep comments useful to another developer: explain purpose and rationale when they are not clear from the code; do not narrate obvious implementation steps.
- Add or update tests for important behavior and regression cases. Prefer tests that demonstrate observable behavior over tests tied to implementation details.

## Sources

- [SE-EDU Java coding standard](https://se-education.org/guides/conventions/java/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) (for topics not covered by the SE-EDU standard)
- [CS2103/T textbook: Code Quality](https://nus-cs2103-ay2627-s1.github.io/website/se-book-adapted/chapters/codeQuality.html)
- [CS2103/T programming language requirements](https://nus-cs2103-ay2627-s1.github.io/website/admin/programmingLanguages.html)
