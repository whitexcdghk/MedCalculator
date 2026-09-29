# MedCalculator

Wear OS app (+ Android phone companion) for veterinary drug dosage calculation. Built for
personal use first (a vet/vet tech using a Samsung Galaxy Watch FE 40mm), but designed from
the start to Wear OS / Play Store standards in case of a future public release.

## Architecture

Multi-module Gradle project:
- `shared`: Kotlin/Android library. `Medicine` data model, `DoseCalculator`, `DebugSeedData`
  (placeholder, non-clinical catalog). Will hold Room entities/DAOs in a later milestone.
- `wear-app`: Wear OS Compose app (Jetpack Compose for Wear OS, Wear Compose Material —
  the classic `androidx.wear.compose:compose-material`, not Material 3).
- `mobile-app`: Android phone companion app (standard Compose Material 3).

Formula: `doseMg = weightKg * dosageMgPerKg`, `volumeMl = doseMg / concentrationMgPerMl`.

`Medicine` fields: `id`, `name`, `species` (list — a medicine can apply to several species),
`concentrationMgPerMl` + up to 3 `concentrationPresetsMgPerMl`, `dosageMgPerKg` + up to 3
`dosagePresetsMgPerKg`, `notes`, `sortOrder` (manual ordering, set from the phone app). Dosage
and concentration are "semi-standard": prefilled from the catalog but editable per-calculation
(via presets or a custom value) without writing back to the catalog.

## Wear app screen flow (MVP, built)

Every screen uses the same 3-band round-screen layout: fixed top band (currently empty,
reserved for future use), scrollable/flexible middle band, fixed bottom band with the primary
action. Navigation via `SwipeDismissableNavHost` (gives swipe-to-go-back for free everywhere).

Home (medicine list, tap to select + "NEXT") -> Parametri (shows the selected medicine's
dosage/concentration defaults) -> Peso (two-column `Picker`, kg | tenths of a kg, same pattern
as the Wear OS system timer duration picker) -> Risultato (computed dose/volume via
`DoseCalculator`, "ok" loops back to Home).

State lives in a shared `CalculatorViewModel` (in `wear-app/.../ui/`), not yet backed by Room.

## Current status / what's not built yet

- Done: multi-module setup, `DoseCalculator` with unit tests, wear-app MVP flow above with
  `@Preview` on every screen, reading from `DebugSeedData` (no persistence yet).
- Not built: editing dosage/concentration via the preset+picker sub-screens (designed in the
  mockups, not implemented — Parametri currently only *displays* the defaults); Room
  persistence on the watch; the phone companion app's actual UI (currently a placeholder);
  phone->watch sync via the Wearable Data Layer API; IT/EN string coverage beyond what
  exists; UX polish (remembering the last weight across app restarts via DataStore, search,
  sync status indicator).

Recommended build order (still valid): shared+DoseCalculator -> wear-app UI on static data
(done) -> real Room on watch (read-only) -> mobile-app CRUD -> Data Layer API sync ->
localization -> UX polish + real-device testing.

## Deferred / optional feature (its own git branch when built — not in the MVP)

Save-a-result-per-animal + history feature: adds a "New Homepage" that replaces wear-app's
current Home as the entry point ("Nuovo calcolo" -> today's flow, "Animali salvati" ->
history), a save flow from Risultato, and a full standalone calculator on the phone app too
(reusing `shared`, different UI). `Animal`/`CalculationRecord` would sync **bidirectionally**
between watch and phone (unlike the one-way phone->watch medicine sync) via per-record
last-write-wins + soft delete, since either device can create/edit them.

Other noted-but-not-decided future ideas: a remote-managed medicine catalog (base catalog +
local user overrides, for wider distribution); structured extra per-medicine fields sourced
from a reference like Plumb's Veterinary Drug Handbook (`indications`, `warnings`,
`sideEffects`, `interactions`, `monitoring`, `storageNotes`) — companion-app-only, never
synced to the watch; a large (~400-600 drug) full catalog import, which would need the Home
screen's navigation rethought (favorites + search instead of one long scrolling list).

Real medicine data has not been entered yet — `DebugSeedData.kt` is 3 clearly-labeled
non-clinical placeholder entries. The user has a copy of Plumb's Veterinary Drug Handbook to
source real data from eventually; extraction should be done in small targeted batches (not
the whole 1100-page book at once) and any extracted values must be verified by the vet before
being trusted, since automated PDF extraction can introduce errors in clinically sensitive data.

## Build gotchas already hit (don't redo this research)

- **AGP 9+ has Kotlin built in.** Do NOT apply the separate `org.jetbrains.kotlin.android`
  plugin — it now throws a hard error. `jvmTarget` falls back to
  `android.compileOptions.targetCompatibility` automatically.
- Compose still needs its own plugin even with built-in Kotlin: apply
  `org.jetbrains.kotlin.plugin.compose` (version matching the Kotlin version) on every module
  with `buildFeatures.compose = true`.
- Wear Compose Material 1.6.2 specifics that differ from some docs/examples found online:
  `PickerState` exposes `selectedOption` (not `selectedOptionIndex`); `Picker`'s
  `contentDescription` parameter is a plain `String?`, not a lambda.
- Dev machine: Android SDK + AVDs and the Android Studio install itself were moved off the
  (nearly full) C: drive onto an SSD. Gradle's bundled JDK (`jbr` folder inside the Android
  Studio install dir) isn't on PATH for external terminals — set `JAVA_HOME` to that `jbr`
  path manually when running `./gradlew` outside Android Studio's own integrated terminal.

## Working branch

All work lives on `claude/blissful-einstein-b6ooc5`. Never rewrite history on it; keep
committing and pushing there.
