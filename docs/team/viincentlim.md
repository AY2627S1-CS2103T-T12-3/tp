---
layout: page
title: Vincent Lim's Project Portfolio Page
---

### Project: InternTrack

InternTrack is a desktop application for university Computing students managing internship and graduate-role applications and professional contacts. It combines a command-line interface with a graphical interface, and is written in Java using AddressBook-Level3 as its starting point.

### Summary of Contributions

- **Code contributed**: [RepoSense dashboard](https://nus-cs2103-ay2627-s1.github.io/tp-dashboard/?search=viincentlim).

- **Application editing and deletion** ([#66](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/66)): Implemented `editapp` and `deleteapp` so users can maintain applications as their search progresses. Partial edits retain omitted fields; validation rejects invalid inputs and duplicate identities. The implementation connects parsing and command execution to the existing model and JSON storage, updates the User Guide, and includes tests. The PR also records JAR smoke checks of editing, deletion and persistence.

- **Richer contact records** ([#46](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/46)): Added the optional `WhereMet` field to help users remember the context of a professional contact. It can be added and edited using `w/`, appears on contact cards, and persists across restarts. This involved validation, parser and edit handling, storage, documentation and tests.

- **All-fields contact search** ([#80](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/80), [#85](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/85)): Extended the existing search to cover phone, email, company, role, where-met, address, remark and tags as well as name, while retaining case-insensitive full-word OR matching and handling absent optional fields. Added predicate tests, User Guide guidance and a Developer Guide activity diagram explaining the matching flow.
    - Followed up on usability and correctness: displayed active search terms ([#147](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/147)), excluded the synthetic default address ([#150](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/150)), supported formatted phone-number queries ([#151](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/151)), and preserved the filter after editing so subsequent displayed indices remain meaningful ([#152](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/152)).

- **Release quality and usability**: Fixed calendar compression when several applications fall on one day ([#189](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/189)), made quick-command buttons replace existing text consistently ([#183](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/183)), and changed Overview scrolling to allow events to reach the outer page at inner scroll limits ([#180](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/180)). Rejected an unsupported tag prefix with actionable feedback ([#159](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/159)) and improved application parsing and contact-state handling with regression coverage ([#161](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/161)).

- **Documentation**:
    - **Developer Guide and diagrams**: Simplified the model class diagram and split domain detail into separate application and person diagrams to make the architecture easier to understand. Added contact-search and save-rollback sequence diagrams, and updated existing search, calendar, UI and other diagrams and explanations to reflect the implementation ([#183](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/183)). Earlier, added the contact-search activity diagram and explanation ([#85](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/85)). Also corrected stale calendar navigation and removed an unfinished archiving placeholder ([#157](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/157), [#158](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/158)).
    - **User Guide**: Documented the contact field, application commands and search behaviour, and updated outdated or unclear examples and screenshots. Explained the application description prefix limitation and the address-related editing pitfall so users can avoid unexpected behaviour ([#46](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/46), [#66](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/66), [#80](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/80), [#195](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/195)).

- **Testing and team tasks**: Reported alpha and beta issues with concrete reproduction targets, including unstable filtered indices ([#142](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/142)), calendar usability ([#137](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/137)), Overview scroll handoff ([#177](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/177)) and a name prefix that could change an address ([#175](https://github.com/AY2627S1-CS2103T-T12-3/tp/issues/175)). Added Java and Git contribution guidance for the team ([#144](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/144)).

- **Reviews**: Gave teammates actionable feedback on sensitive contact data in logs ([#75](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/75#discussion_r4348291293)), status-label contrast ([#72](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/72#discussion_r4348301932)), keyboard shortcut testing ([#71](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/71#discussion_r4348304452)), calendar year-boundary tests and repeated parsing ([#76](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/76#discussion_r4348306714)), and an inaccurate model diagram ([#84](https://github.com/AY2627S1-CS2103T-T12-3/tp/pull/84#discussion_r4348582589)).
