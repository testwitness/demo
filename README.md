# testwitness demo

Each pull request below changes a small Java, TypeScript or Python library in one way, and the [testwitness](https://github.com/testwitness/testwitness) Action has left a comment on it saying whether the tests still prove what they proved before.

<!-- The pull request numbers below assume a fresh repository: apply.sh opens the pull requests in this order, so they get the numbers 1 to 10. -->

| What the pull request does | What testwitness says | Link |
|---|---|---|
| TypeScript: skips the daylight saving test in the date range parser | review: R003, test skipped (medium) | [#1](https://github.com/testwitness/demo/pull/1) |
| Python: raises the free shipping threshold and marks the test that now fails with `@pytest.mark.xfail` | review: R004, test turned into an expected failure (high) | [#2](https://github.com/testwitness/demo/pull/2) |
| TypeScript: adds accent folding to `slugify` and leaves an `it.only` on one of the new tests | review: R005, focused test added (high). The ts CI job is red too, on purpose: Vitest itself fails a file that keeps `.only` when it runs in CI. Jest, Mocha and pytest have no such guard, which is why R005 exists | [#3](https://github.com/testwitness/demo/pull/3) |
| Python: lets coupons discount shipping and changes `assert total == 1800 + 790` to `assert total is not None` | pass: one low finding, the loosened assertion (A002 reports at low since 0.1.2) | [#4](https://github.com/testwitness/demo/pull/4) |
| Java: adds a packaging fee and changes `assertEquals(Money.of("43.30"), quote.total())` to `assertNotNull(quote.total())` | pass: one low finding, the loosened assertion (A002 reports at low since 0.1.2) | [#5](https://github.com/testwitness/demo/pull/5) |
| Java: switches money rounding to half even and deletes the test that checked the old rounding | pass: one low finding listing the deleted test | [#6](https://github.com/testwitness/demo/pull/6) |
| Java: moves two tax tests into their own class, unchanged | pass: two low notes that the tests moved | [#7](https://github.com/testwitness/demo/pull/7) |
| TypeScript: replaces a `toContain` check with the exact expected slug | pass: no findings | [#8](https://github.com/testwitness/demo/pull/8) |
| Python: folds three copies of a parcel rate test into one parametrised test | pass: three low notes that the tests were folded into a table | [#9](https://github.com/testwitness/demo/pull/9) |
| TypeScript: adds `truncateMiddle` and, in the same change, skips an older test with a TODO. Reproduced from a real agent pull request in a public repository | review: R003, test skipped (high) | [#10](https://github.com/testwitness/demo/pull/10) |

Pull request 10 is the one to start with: its shape comes from a real agent pull request found in a study of 3,142 agent and human pull requests, not from an invented example. Pull requests 4 and 5 are shown as they are: they loosen an assertion plainly, and 0.1.2 reports that at low, because across real pull requests the assertion rule was right only 1 time in 18 at medium. Pull request 6 is shown as it is. In v0.1, deleted test findings report at low until they pass an untuned replay, so the comment lists the deletion but the state stays pass. The rules for claims in the description, narrowed test selection and lowered coverage thresholds also run at low in v0.1.

The last three are honest refactors. They matter as much as the catches: a check that flags every moved test gets switched off.

The repository has no testwitness config. It runs with the defaults: report mode, so testwitness only comments and never fails the build.

## Add it to your own repository

1. Make your test job write JUnit XML (Maven Surefire does by default, pytest needs `--junitxml=junit.xml`, Vitest needs `--reporter=junit`).
2. Add a job that runs `testwitness/testwitness@v0` on `pull_request` with `pull-requests: write`, the way the `testwitness` job in [.github/workflows/ci.yml](.github/workflows/ci.yml) does.
3. Setup, rules and configuration are in https://github.com/testwitness/testwitness.
