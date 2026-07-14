# Recipes

A small Android app that lists recipes from a bundled JSON file and shows a detail screen for each
one. The list is sorted by total time (prep + cook), shortest first.

Built with Kotlin, Jetpack Compose, Navigation 3, and Hilt.

```
:app      Compose UI, ViewModels, navigation
:data     JSON parsing, mappers, repository        --> depends on :domain
:domain   Recipe model, use case interfaces        --> depends on nothing
```

## Key architectural decisions

**The app follows clean architecture and is split into three layers.**

- `:domain` holds the `Recipe` model, the use case contracts, and any business rule worth reusing.
- `:data` reads and parses the JSON, maps it into domain models, and implements the contracts the
  domain declares.
- `:app` holds the Compose UI and navigation, and wires the layers together through Hilt.

**Each contract is a single-abstract-method `fun interface`.** A caller is handed precisely the one
operation it performs, so it cannot reach for behaviour it was never given. And because a SAM
interface can be implemented by a lambda, a test fake is a single line written inline at the call
site, which is why the project uses no mocking framework.

**A ViewModel exposes a single `StateFlow` of a sealed `Loading | Success | Error` type.** The screen
is then split in two: a public composable that takes the ViewModel and collects that flow with
`collectAsStateWithLifecycle()`, and a private one that takes plain state and lambdas. The stateless
half makes it easy to write a `@Preview` for each possible state.

## Trade-offs

**The JSON is re-read and re-parsed on every call.** Opening the detail screen calls `getRecipe(id)`,
which reads the whole asset off disk, parses every recipe in it, and then keeps just one. The list
should be parsed once and cached in memory.

**There is no retry mechanism.** A failure lands the screen in the `Error` state and leaves it there.
The state is set once when the ViewModel is constructed, so the only way to try again is to recreate
the ViewModel. The error is a dead end rather than something the user can act on.

## Possible improvements

**Move the recipes code into a feature module split into `api` and `impl`.** Today everything lives
in `:app`, `:data`, and `:domain`, which does not scale past one feature. A `:feature:recipes` module
would expose only what other features legitimately need — the navigation key for the recipe list —
from its `api` package, and keep everything else behind `impl`, organised into `data`, `domain`, and
`ui` packages.

**Add architecture tests to enforce the layering.** Automated architecture tests would assert the
boundaries and turn those conventions into something the build can check.

**Make the navigation logic unit-testable and move it into a `navigation` package in a core module.**

**Move user-facing strings into `strings.xml`.**

**Add Compose snapshot tests for every preview, and UI tests for the critical flows.**

## AI tooling

**This project was built with the help of Claude Code.** Each change went through plan mode:
explore the existing code, agree on a plan, then implement it. Verification was performed manually by
me.
