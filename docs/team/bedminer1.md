---
layout: page
title: Alexander Seah's Project Portfolio Page
---

### Project: InternTrack

InternTrack is a desktop application for university Computing students managing internship and graduate role applications alongside the professional contacts who support their job search. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java and is built on AddressBook Level 3.

Given below are my contributions to the project.

* **New Feature**: Added a weekly application calendar ([\#76](https://github.com/bedminer1/tp/pull/76), [\#145](https://github.com/bedminer1/tp/pull/145), [\#146](https://github.com/bedminer1/tp/pull/146)).
  * What it does: shows application dates in a navigable weekly calendar next to the contact and application lists, so the user can see which weeks have deadlines or interviews at a glance.
  * Justification: students juggle many applications at once, and a list sorted by name does not show what is coming up. A calendar turns the stored application dates into a planning view.
  * Highlights: the calendar is a new JavaFX component (`ApplicationCalendarPanel`) wired into `MainWindow` and `MainWindow.fxml`. It had to stay in sync with the model whenever applications are added, edited or deleted, and I later fixed it so that it stays open after application changes (\#146). I then reworked it to be readable and informative, with styling in `WorkspaceTheme.css` (\#145).

* **New Feature**: Added Up/Down command history in the command box ([\#128](https://github.com/bedminer1/tp/pull/128)).
  * What it does: lets the user recall previously entered commands with the Up and Down keys, as in a shell.
  * Justification: the product targets users who prefer typing, who often need to repeat or slightly tweak a command.
  * Highlights: implemented as a separate, unit-tested `CommandHistory` class used by `CommandBox`.

* **New Feature**: Added keyboard-guided contact and application entry ([\#130](https://github.com/bedminer1/tp/pull/130)).
  * What it does: a `GuidedEntryDialog` walks the user through the fields of a new contact or application using only the keyboard.
  * Justification: the add commands have many prefixes, which are hard to remember. The guided dialog lowers that barrier while keeping the product keyboard-first.

* **Enhancements to existing features**:
  * Tightened name and phone validation, and updated test fixtures to match ([\#52](https://github.com/bedminer1/tp/pull/52)).
  * Fixed the contact list not refreshing after edit and delete, with added tests ([\#67](https://github.com/bedminer1/tp/pull/67)).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2627-s1.github.io/tp-dashboard/?search=bedminer1)

* **Project management**:
  * Prepared the InternTrack `v1.3` release ([\#68](https://github.com/bedminer1/tp/pull/68)).
  * Shipped separate Intel and Apple Silicon JARs so that JavaFX runs on both Mac architectures ([\#131](https://github.com/bedminer1/tp/pull/131)).

* **Documentation**:
  * User Guide:
    * Added sections for the weekly calendar, command history and guided entry ([\#76](https://github.com/bedminer1/tp/pull/76), [\#128](https://github.com/bedminer1/tp/pull/128), [\#130](https://github.com/bedminer1/tp/pull/130)).
    * Updated the calendar section and the quick start instructions for the JAR files ([\#145](https://github.com/bedminer1/tp/pull/145), [\#131](https://github.com/bedminer1/tp/pull/131)).
  * Developer Guide:
    * Wrote the internship tracker requirements ([\#51](https://github.com/bedminer1/tp/pull/51)).
    * Added the application calendar sequence diagram ([\#87](https://github.com/bedminer1/tp/pull/87)).
    * Updated the UI class diagram to include the calendar view ([\#191](https://github.com/bedminer1/tp/pull/191)).
  * Project website: tailored the README and project home page to InternTrack ([\#50](https://github.com/bedminer1/tp/pull/50)), and added my profile and photo to the About Us page ([\#22](https://github.com/bedminer1/tp/pull/22), [\#18](https://github.com/bedminer1/tp/pull/18)).

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#75](https://github.com/bedminer1/tp/pull/75), [\#85](https://github.com/bedminer1/tp/pull/85), [\#86](https://github.com/bedminer1/tp/pull/86), [\#121](https://github.com/bedminer1/tp/pull/121), [\#124](https://github.com/bedminer1/tp/pull/124), [\#125](https://github.com/bedminer1/tp/pull/125), [\#157](https://github.com/bedminer1/tp/pull/157), [\#158](https://github.com/bedminer1/tp/pull/158)
