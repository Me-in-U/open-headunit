# Transfer branch, not part of the app

This branch carries test briefs and results between the session that writes the code and the agent
that runs it on hardware. Orphan branch: no shared history with `main` or any feature branch, and
nothing here is ever merged.

It is `transfer/rig-rounds`. Until 2026-09-10 it was named after the round it was created for,
`transfer/hotspot-unreadable-config-results-20260807`; links into that name are dead, so cite files
here by path.

**The branch was rewritten on 2026-09-18** to move the captures out to release assets. If your
checkout predates that, or a transfer SHA you hold no longer resolves, read `REPOINT-20260918.md`
once: it carries the reset commands and what changed. Otherwise you do not need it.

## Read this, in this order, and stop there

1. **`TESTING-TEMPLATE.md`**, the standing method. Read it once: capture rules, how to read and
   write `settings.xml`, the app's automation surface, the clean-run protocol, install discipline,
   the four verdicts, and the format results come back in. The rig quirks are in `rig-quirks/`, indexed by
   §7a: read the files for your units and your runs' areas, not all of them.
2. **`## Queue` below, then your own row in `## Threads`**, found with
   `grep -F '| `<thread>`' README.md`. The row names the one brief to read; it names everything
   else it needs.

**Do not read the other threads' briefs and results.** They are a different fault on a different
build, and several were superseded by later rounds in their own thread. Reading them costs context
and, worse, primes you for a signature that does not apply to the run you are doing. If a brief
needs a prior round, it cites it by filename. Fetch that one, not its neighbours.

The same goes for `archive/`. It is the historical record, not orientation.

**A brief may cite a file that is not at the root.** Closed threads' briefs, results and findings
moved to `archive/rounds/` on 2026-10-08 under their own filenames. Look there by the same name.
Their README rows are in `archive/threads-table-through-2026-10-08.md`.

**Captures are not on this branch.** It is markdown only. Screenshots, logs and traces go to the fork
as a release asset, **one release per thread and one asset per round**: the release is
`rig-evidence-<thread>`, created on that thread's first round, and every later round uploads
`<thread>-round<N>-captures.zip` to the same one. The results file cites the release, the asset
filename and a sha256 rather than a path, and `TESTING-TEMPLATE.md` §7 gives the commands and the
retention rule for a thread that closes. Do not commit a capture here, the commit guard refuses one.

**There is exactly one archive release, `rig-evidence-legacy`, and every closed thread's captures are
in it.** A `rig-evidence-*` release that a results file cites no longer exists: `native-aa-wireless`
was swept in on 2026-09-21 along with `hands-free-wake-verdict`, `rotation-geometry` and
`wpp-endpoint-depoison`, and the per-round releases that predate the one-release-per-thread rule went
the same way. `adaptive-audio`, `check-for-updates`, `hold-aa-rfcomm` and
`native-aa-dsam-wifi-unavailable` followed on 2026-09-30; a thread that runs another round creates its
release again, as on its first round. Asset filenames are unchanged, so fetch by filename rather than by the tag a results
file names:

```bash
gh release download rig-evidence-legacy --repo o-jcardenass/open-headunit -p '<asset>.zip'
```

`archive/evidence-manifest-20260921.tsv` maps each old release tag to the asset now holding it, with
its size and sha256. Every hash a results file quoted was verified against its asset before the sweep
and again after it, so a cited sha256 still identifies its capture.

An `evidence/<round>/...` path in a results file written before 2026-09-18 is a historical path. Look
it up in `archive/evidence-manifest-20260918.tsv`, which carries every archived capture's size,
sha256 and the zip holding it, and fetch that zip from `rig-evidence-legacy` by the same filename.

Three rules from the template are worth repeating, because breaking any of them invalidates a round:

- **Use the rig's existing scripts.** `hur-wifi-test-scripts/` already has `build_hur.sh`,
  `run_unit_tests.sh` and others. Inventory the folder at the start of every round, use what fits,
  and only add a script when nothing does, leaving it there for next time.
- **Settings are changed in `shared_prefs/settings.xml` with the app stopped**, never through the
  UI, and the settings list is never scrolled with adb.
- **Run the whole round unattended.** Moving between runs, restoring state and deciding that a gated
  run is INCONCLUSIVE are all yours. Escalate only for a failed build gate, a genuinely ambiguous
  fork the brief did not cover, something destructive, or a broken rig.

## Queue

A brief with no results file of the same name. Add a line here when a brief is pushed; delete it
in the commit that pushes the results.

- `station-scan-round1-brief.md` (D-POCO, D-HU and D-SAM as head units, D-MOTO as phone; measurement, no candidate)
- `call-audio-route-round1-brief.md` (measurement on `main` `28d73e2f`, D-HU and D-MOTO with the KY Pro intercom; hand-rung calls; I-car against I-moto is the point)
- `pr-1076-text-keycodes-round1-brief.md` (D-HU with D-POCO, A/B; R1 is the point)
- `pr-stack-1047-1067-round1-brief.md` (the contributor's five PRs merged as they will land, tree `2c4b06fd` on main `77914f18`; replaces the pr-1045 and pr-1067 round 3 briefs; Stage W D-HU with D-POCO unattended (A1-S, K-S the point, XS with 3 Exit taps), Stage U D-POCO with the dongle (US); run Stage U right after pr-1047 round 3 Stage U; about 50 min)
- `pr-1001-devserver-p2p-round1-brief.md` (B `0cbff004`, P `7f5bc6e9` = `fork/review/pr-1001`, F `ce5e61cb` = `fork/review/pr-1001-loopback-fix`; stage 1 D-POCO as head unit in Self Mode, stage 2 D-HU with D-POCO; R3 is the point; hand steps HS1 to HS4; renames D-HU's WiFi Direct device to OpenHU)
- `pr-1047-session-reconnect-round3-brief.md` (PR head `7cc36de8` on main `77914f18`, plus an export build for D-POCO; Stage A D-HU with D-POCO, Stage U D-POCO with the dongle and D-MOTO; N2 is the point; runs N2, ND, U1 only; each Save is two taps on dumped targets, 4 per run at most; Stage A unattended, Stage U needs the operator for cables; about 55 min)

## Threads

| Thread | State | Next |
|---|---|---|
| `call-audio-route` | Round 1 queued | `call-audio-route-round1-brief.md`. Measures whether Motorcycle plus head unit microphone off moves a phone call's audio, with and without an intercom on the phone, and where the assistant opens SCO. No candidate. |
| `settings-defaults` | Round 2 reported | `settings-defaults-round2-results.md`. `77c0b1fe`: E2b, E2b-2 (Exit ends as user_exit, phone does not return), E4, R3b PASS; R1b shape 2 (window alpha capped at 0.8 on the merged build, tap passes at 0%); R2b skipped. |
| `usb-reattach` | Round 2 done | `usb-reattach-round2-results.md`. Plain retry on `main` recovered 0 of 11 cycles; candidate reset re-enumerated 8 of 8, re-switch 0 of 6; give-up, bound, banner and clearing all held (RG PASS). Round 1: `usb-reattach-round1-results.md`. |
| `build-speed` | Round 1 done | `build-speed-round1-results.md`. Rig build `build_hur.sh` 243.6 s cold, 4.5 s warm; cold with kapt 270 s, without 215 to 231 s (kapt saves 39 to 55 s); every cold build swaps and peaks 90C or more. |
| `bluetooth-audio-disabled-usb-connect` | ROUND 4 STOPPED | `bluetooth-audio-disabled-usb-connect-round4-results.md`: D-HP hosted, R1 on `fff96699` hit the abort rule (pairing request, no disabler) with D-HP and again with D-POCO as host, S and R2 to R3b not run. Round 3: `bluetooth-audio-disabled-usb-connect-round3-results.md`. |
| `pr-1045-framing` | Round 3 superseded | `pr-stack-1047-1067-round1-brief.md` tests the rebased head on the full stack; do not run `pr-1045-framing-round3-brief.md`. Round 2: `pr-1045-framing-round2-results.md` (no FAIL; A1, A2, U2S INCONCLUSIVE). |
| `pr-1047-session-reconnect` | Round 3 queued | `pr-1047-session-reconnect-round3-brief.md`. Head `7cc36de8`: Native Save wakes at once (N2), Disconnect stops the wake (ND), USB Save keeps its retry (U1, U2). Round 2 results: `pr-1047-session-reconnect-round2-results.md` (N2 reconnect 32.5 to 34.1 s). |
| `pr-1064-nearby-attempts` | Round 1 reported, round INCONCLUSIVE, no FAIL | `pr-1064-nearby-attempts-round1-results.md`. R0 PASS; N0 B 0/3 (Gearhead 17.9 refuses the helper launch), N1-N3 stopped; U1 B dongle never spent its budget, C not run. |
| `pr-1065-reconnect-timers` | Round 1 reported, U1 INCONCLUSIVE | `pr-1065-reconnect-timers-round1-results.md`. C = `de1f9848` (same tree as brief). H 10/10 and N 5/5 match B; U1 all cycles masked by dongle re-enumeration; P INCONCLUSIVE (Gearhead 17.9 refuses helper). |
| `pr-1067-tls-pump` | Round 3 superseded | `pr-stack-1047-1067-round1-brief.md` tests the reworked head on the full stack; do not run `pr-1067-tls-pump-round3-brief.md`. Round 2: `pr-1067-tls-pump-round2-results.md` (no FAIL; XW, KW-C INCONCLUSIVE). |
| `pr-stack-1047-1067` | Round 1 queued | `pr-stack-1047-1067-round1-brief.md`. Stack of #1047, #1045, #1064, #1065, #1067 at tree `2c4b06fd`: load soak K-S is the point, one Exit (XS), one USB unplug (US), album art (A1-S). |
| `pr-1001-devserver-p2p` | Round 1 queued | `pr-1001-devserver-p2p-round1-brief.md`. Automatic WiFi Direct for Headunit Server mode. R3: a Self Mode session must survive station WiFi off beside an armed WiFi Direct launcher (B and F keep it, P is expected to close it). |
| `pr-1076-text-keycodes` | Round 1 queued | `pr-1076-text-keycodes-round1-brief.md`. Letter keycodes advertised to the phone. R1: does a Maps search open the head unit or phone keyboard, B vs M; R2 typed keys; R3 a typed 'n' with a night key mapped. |
| `pr-1042-disabled-home-buttons` | Round 1 reported | `pr-1042-disabled-home-buttons-round1-results.md`. `3404e4e4`: R4 FAIL as predicted (only WiFi ticked, session live in picture-in-picture: Self Mode button reads enabled=no, press does nothing); R1, R2, R3, R5 PASS; R2 shows the legacy single choice greys Self Mode and USB. |
| `station-scan` | **QUEUED, round 1** | `station-scan-round1-brief.md` on probe `b887adeb`. Two reporter tablets stutter on every one of their unjoined station's scans; D-HU does not (link-stall-periodic-scan round 5). Measures whether a `LocalOnlyHotspot` takes the station down and carries a session. |
| `dsam-widget-layout-dpi` | Round 1 reported, no brief | `dsam-widget-layout-dpi-round1-results.md`. D-SAM shows the portrait widget layout from the announced density, not orientation: 1280x720 landscape at 175 dpi (about 658 dp tall) shows the weather widget, at 198 it does not. Auto orientation also pins a portrait start (R1 FAIL). dpi left at 198. |

Round files are `<thread>-round<N>-brief.md` and `<thread>-round<N>-results.md`. A brief with no
matching results file is a round nobody has run yet. That pairing is the only queue there is, so
keep the names regular.

## Keeping this file lean

This README was 1,137 lines on 2026-08-14 and a tester agent read all of it before every round;
that history is in `archive/rounds-log-through-2026-08-14.md`. By 2026-09-10 it had grown back to
171 KB in 67 table rows averaging 2,450 characters, about 43,000 tokens read at the start of every
round. The rows as they stood are in `archive/threads-table-through-2026-09-10.md`; the table above
is what they condensed to, under 300 characters a row.

So, for whoever writes the next brief: **the outcome of a round goes in its own results file and
the table above, not into a narrative here.** One row, one state, one filename, and a Queue line
while the brief is unrun. The State cell is one of **QUEUED**, **IN FLIGHT**, **DONE**, **MERGED**,
**CLOSED**. If the state needs a sentence of context, it belongs in the next brief, where the
person who needs it is already reading. A row over 300 characters is the signal this file is
growing back.
