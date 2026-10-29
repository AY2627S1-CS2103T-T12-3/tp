---
name: tp-project-work
description: Plan features, implementation slices, commits, and documentation for this CS2103T team project; use when shaping or implementing future tP work.
---

# CS2103T team project work

Help evolve InternTrack as a cohesive, working product while meeting the current CS2103T tP requirements. Ground decisions in the live repository, the team's agreed product scope, and the course website. Do not treat old issues, milestones, release dates, or plans as current without checking.

## Before planning or changing code

- Work from the actual checkout at `tp/team-repo`. Read applicable `AGENTS.md` files, inspect `git status`, the current branch, and recent history. Preserve existing user edits and untracked files.
- Read the relevant product and code documentation: `README.md`, `docs/UserGuide.md`, `docs/DeveloperGuide.md`, and `docs/Testing.md`. Trace the current implementation before proposing a design; existing architecture may have changed.
- For iteration planning, inspect the team's current GitHub issues, PRs, and milestones. Use the current week's tP page, [tP expectations](https://nus-cs2103-ay2627-s1.github.io/website/admin/tp-expectations.html), [tP grading criteria](https://nus-cs2103-ay2627-s1.github.io/website/admin/tp-grading.html), and [GitHub workflow appendix](https://nus-cs2103-ay2627-s1.github.io/website/admin/appendixE-gitHub.html) as authoritative guidance. Course schedules can change; find the applicable page from the [weekly schedule](https://nus-cs2103-ay2627-s1.github.io/website/schedule/).
- Separate confirmed team decisions from suggestions. Do not invent assigned work or silently broaden the product scope. Get team consent for an enhancement that changes the agreed product direction.

## Plan features as reviewable slices

Start from a user need and the product's stated scope: managing internship and graduate-role applications and their professional contacts on one computer. Prefer cohesive, user-visible improvements that fit this audience. Keep out-of-scope services (such as finding vacancies or contacting employers) out unless the team explicitly changes the product direction.

For each proposed feature, write down:

- the user problem and expected behavior, including important invalid-input and boundary cases;
- a small set of observable acceptance criteria;
- affected code layers, data/persistence implications, tests, and UG/DG updates;
- dependencies, risks, and a sensible implementation order.

Prioritize by user value, risk, dependency, and available time. For an alpha iteration, plan breadth-first: get a working, coherent version of each agreed final-product feature in place, then polish the highest-priority areas. Do not chase a LOC target: the course's 500–600 functional LOC per person is a secondary ballpark, while product quality is the main consideration and there is no bonus for exceeding the effort bar.

Reflect the agreed work in issues with clear outcomes, owners, and the current milestone. Follow the course's issue-tracker guidance: make tasks standalone and reasonably small (typically a few hours for one person), use a specific title, keep the description only as detailed as needed, and assign an owner and milestone once agreed. Use the team's configured type/priority labels when applicable; inspect available labels instead of assuming they exist.

Split work into small, independently reviewable PR-sized increments that keep the product working. Prefer ownership by feature so each contributor can carry their change through code, tests, and documentation. Use parallel PRs for independent slices when useful, and check the current weekly instructions for required workflow practice. Do not create or edit hosted issues, milestones, PRs, or releases unless the user asks for that action.

## Make good work easy to evaluate

Treat peers, tutors, and automated reviewers as readers who need to verify the work quickly. Optimize for genuine quality and clear evidence, not for appearances or inflated activity. The grading criteria emphasize product fit and cohesion, code quality, testing, documentation, iterative project management, and a fair individual contribution.

- Make the chain from user problem to acceptance criteria, implementation, tests, and guide updates easy to follow. Keep issue and PR descriptions specific; state what changed, why it helps the target user, how to exercise it, which checks were actually run, and any known limits.
- Make behavior observable and testable, including ordinary and invalid input paths. Keep CLI commands suitable for fast typists, errors predictable, and the User Guide consistent with actual behavior.
- Keep code straightforward to review: follow project conventions, use clear names and focused methods, avoid duplication, and include useful defensive handling where appropriate. For UI changes, include a focused screenshot or short demonstration when it helps a reviewer judge the result.
- Keep the UG and DG accurate, navigable, and proportionate. Use examples for user workflows and high-level diagrams for design; avoid stale diagrams, broken links, unexplained terminology, and duplicated prose.
- Make contribution evidence authentic: use meaningful issues, reviewable PRs, and coherent commits that reflect real work. Do not pad LOC, split changes into empty commits, overstate test coverage, or claim checks that were not run.

When proposing a task plan, show for each slice its user value, acceptance evidence, owner/dependencies, affected code/docs/tests, and intended PR or commit boundary. End with a short self-review against the current official grading criteria; do not promise a mark or imply that presentation can compensate for incomplete behavior.

## Plan commits and collaboration

When commit work is requested, make each commit a coherent, buildable step that explains one change. A feature may have several commits when that makes review easier, for example a model/parser slice, a command/UI slice, and the associated docs; avoid both one oversized commit and commits so fragmented that they cannot be understood alone. Follow the repository's existing commit-message style (short imperative summary, no trailing period).

Before committing, inspect the diff and stage only the files for the requested change. Never include unrelated user changes. For code changes, create a PR from a separate feature branch, use the current course workflow and team's decision (fork workflow, or the optional centralized team-repo workflow after v1.3), and target the correct base branch. Match the PR title to its issue where practical, include `Fixes #<issue>` when merging should close the issue, and assign the PR to the issue's milestone; the PR author is its owner, so do not assign it to a person. Get a teammate review and do not merge a failing CI run unless the failure is confirmed to be safely ignorable. Use a separate branch for each parallel PR and minimize overlap. Avoid squashing or rebasing merges because commit timestamps are used for course progress tracking. Do not push, open a PR, merge, release, or otherwise publish without explicit user authorization; follow the user's approval-harness requirement immediately before each such action. Do not amend, rebase, force-push, or discard user work without explicit direction.

## Write future code

- For Java implementation, follow [the project Java code-quality guide](references/java-code-quality.md).
- For branch and commit work, follow [the project Git conventions](references/git-conventions.md).
- Follow the existing Java, JavaFX, command, parser, model, and storage patterns. Make the smallest coherent change that satisfies the accepted behavior; avoid unrelated refactors and new dependencies without a clear need.
- Trace a feature through the relevant layers. Keep input parsing and user-facing errors consistent with neighboring commands; keep domain rules in the model; route state changes through the existing model/logic flow; preserve JSON persistence and restart behavior when stored data changes.
- Consider invalid, duplicate, missing, empty, and boundary inputs where relevant. Preserve existing behavior unless the feature explicitly changes it. Keep GUI behavior and CLI behavior consistent when both expose the same feature.
- Add or update automated tests for the behavior and regressions that matter. Update the User Guide for user-visible behavior and the Developer Guide for design or architecture changes. Keep documentation consistent with the shipped implementation.
- Use assertions, exceptions, or logging where they strengthen real invariants and failure handling; do not add defensive code mechanically. Keep changes compatible with the project's supported Java and build configuration.

## Finish and report

Inspect the final diff and use the project's documented checks appropriate to the change when verification is requested. Report what changed, the checks actually run and their result, and any unverified GUI or integration behavior. Keep the change uncommitted unless the user requested a commit, and do not publish it without the authorization above.
