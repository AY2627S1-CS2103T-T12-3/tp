# Git conventions

Use this reference when creating issue branches or commits for InternTrack. Follow the course conventions and the team's current repository workflow.

## Branches

- Use a separate issue-focused branch for each issue.
- Include the issue number and descriptive kebab-case keywords, such as `143-java-git-contribution-guidance`. Follow an existing repository prefix if the team uses one consistently.
- Base feature branches on the current team `master`; use the team's configured fork or centralized workflow for the PR.

## Commit messages

- Write the subject in the imperative mood, capitalize the first word, and omit the final period.
- Aim for a 50-character subject; 72 characters is the hard limit.
- Keep each commit to one logical change.
- For non-trivial commits, add a body after a blank line, wrap it at 72 characters, and explain what changed and why. Use the diff to show how.
- Before committing, inspect the staged diff and stage only files for that change. Preserve unrelated working-tree changes.

## Sources

- [SE-EDU Git conventions](https://se-education.org/guides/conventions/git.html)
- [SE-EDU GitHub conventions](https://se-education.org/guides/conventions/github.html)
