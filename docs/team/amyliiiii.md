---
layout: page
title: Li Zhaohan's Project Portfolio Page
---

### Project: InternTrack

InternTrack is a desktop application for university Computing students who manage internship and graduate-role applications alongside the professional contacts who support their job search. It combines a GUI with a command line interface (CLI), is written in Java, and is built on AddressBook-Level3 (AB3).

Given below are my contributions to the project.

### Summary of Contributions

- **New Feature**: Added the application-tracking data model and the `addapp` command ([#44](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/44), [#45](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/45)).
    - What it does: lets users store applications with a company, role, date and description, and add them through the CLI.
    - Justification: applications are the core records of an internship tracker. They need to remain available after restart and must not silently duplicate an existing application.
    - Highlights: introduced the `Application` domain object, value objects, uniqueness checks, model APIs, JSON serialisation and deserialisation, parser support, command handling, test fixtures and unit tests. This required changes across the model, storage and logic layers while preserving existing AB3 contact data.

- **New Feature**: Added application-status tracking ([#72](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/72)).
    - What it does: users can assign a status while adding an application and update it with `editapp`; the status is displayed in the application list and saved with the application.
    - Justification: a list of applications alone does not show where each application is in the job-search process. Statuses make the tracker useful for follow-up planning.
    - Highlights: implemented the `ApplicationStatus` value, CLI prefix and validation, add/edit flow, JSON persistence, JavaFX card rendering and styling, and regression tests for parsing, model behaviour, storage and command feedback.

- **Enhancement to an existing feature**: Expanded contact search to include company and role ([#63](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/63)).
    - What it does: `find` now matches a contact's name, company or role rather than only the name.
    - Highlights: replaced the name-only predicate with a purpose-specific predicate, kept matching case-insensitive, updated command/parser tests, and documented the new search semantics in the User Guide.

- **Enhancement to the user interface**: Built the InternTrack dashboard and unified workspace interface ([#89](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/89)).
    - What it does: gives users a single workspace containing an at-a-glance dashboard, the contact/application lists, calendar integration and the command box.
    - Highlights: restructured `MainWindow` and its FXML layout, added a reusable `DashboardPanel`, connected it to application state through the logic/UI boundary, and created the corresponding CSS theme while retaining command-driven workflows.

- **Code contributed**: [RepoSense dashboard](https://nus-cs2103-ay2627-s1.github.io/tp-dashboard/?search=amyliiiii)

- **Documentation**:
    - **User Guide**: wrote and maintained the `addapp`, application-status and enhanced contact-search documentation ([#45](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/45), [#63](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/63), [#72](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/72)); later carried out a compliance pass that corrected command behaviour, setup steps, screenshots and manual-test instructions ([#154](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/154), [#163](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/163), [#165](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/165), [#167](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/167)).
    - **Developer Guide**: updated the model class diagram for InternTrack's application domain ([#79](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/79)); documented data-save rollback, contact search and weekly-calendar implementation, and corrected the source links and diagrams in the final documentation pass ([#173](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/173), [#186](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/186)).
    - **Project website**: created the initial InternTrack UI mock-up, product-page content and site-wide settings, and added my profile to the About Us page ([#13](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/13), [#28](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/28), [#29](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/29), [#31](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/31)).

- **Contributions to team-based tasks**:
    - Planned the v1.3 MVP by breaking the product into assigned, feature-specific issues with concrete acceptance criteria and release checks: contact creation, contact edit/delete, contact view, application view, application edit/delete, and the v1.3 release checklist ([#55](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/55), [#56](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/56), [#57](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/57), [#58](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/58), [#59](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/59), [#61](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/61)).
    - Initiated and specified new-feature work, including all-field contact search, application-status management, the updated model diagram, and the unified InternTrack UI ([#62](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/62), [#69](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/69), [#78](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/78), [#88](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/88)).
    - Coordinated alpha testing by allocating each feature package to independent testers, defining the minimum bug-report evidence, and setting an individual bug-finding target ([#90](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/90)).
    - Performed detailed v1.4 feedback and issue triage: reported six alpha findings covering destructive remark behaviour, documentation gaps, help links and command-summary accuracy ([#91](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/91), [#92](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/92), [#93](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/93), [#94](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/94), [#95](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/95), [#96](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/96)).
    - Continued release-quality triage during beta testing, with reproducible reports and acceptance criteria for invalid saved data, calendar clipping, documentation inconsistencies, setup guidance and developer documentation ([#162](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/162), [#164](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/164), [#166](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/166), [#168](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/168), [#170](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/170), [#172](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/172), [#184](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/184), [#185](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/185), [#187](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/187)).

- **Review and mentoring contributions**:
    - Provided inline review comments across team PRs, covering UI list switching and test/design quality, release packaging, keyboard-shortcut edge cases, validation and boundary testing, data-model and search correctness, and UG/DG accuracy ([#65](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/65#discussion_r4274044258), [#65 follow-up](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/65#discussion_r4280710990), [#66 test](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/66#discussion_r4282178081), [#66 design](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/66#discussion_r4282190009), [#68](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/68#discussion_r4284460938), [#71](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/71#discussion_r4345428885), [#108](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/108#discussion_r4398414490), [#109](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/109#discussion_r4398473113), [#150](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/150#discussion_r4411064532), [#151](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/151#discussion_r4411225787), [#183 UG](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/183#discussion_r4441994608), [#183 UML](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/183#discussion_r4443454609), [#195](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/195#discussion_r4444261744)).

- **Contributions beyond the project team**:
    - Helped another class team by smoke-testing its Herta JAR on macOS with Java 25.0.3. I verified launch, GUI rendering, add/list/find/mark/unmark/delete flows, invalid-date handling, persistence after restart and normal application exit, with screenshots and a written test report ([forum evidence](https://github.com/NUS-CS2103-AY2627-S1/forum/issues/237#issuecomment-5691690555)).
