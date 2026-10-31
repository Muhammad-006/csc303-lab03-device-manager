# CSC303 Lab 03 - Device Manager (Java + XML, intents, results, lifecycle)

Package: `com.example.devicemanager` - Java, XML layouts, AppCompat/Material 3.
Student: Muhammad Bilal - FA23-BCE-062

## What it does
| Screen | Behaviour |
|--------|-----------|
| `MainActivity` | Shows the current device summary. **Edit Device** starts `EditDeviceActivity` with an *explicit* intent; **Open Documentation** opens https://developer.android.com with an *implicit* `ACTION_VIEW` intent. |
| `EditDeviceActivity` | Validates name and battery (0-100). **Save** returns the values through `setResult(RESULT_OK, data)`; **Cancel** returns `RESULT_CANCELED`. |

## Key points (for the demonstration)
- **Explicit intent extra:** `editIntent.putExtra(EXTRA_DEVICE_NAME, deviceName)`; the key is namespaced
  (`com.example.devicemanager.DEVICE_NAME`) so it cannot collide with keys from other components.
- **Returned result:** `registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), ...)`
  replaces the deprecated `onActivityResult()`. The callback reads the extras and calls `updateSummary()`.
- **Validation:** empty name, non-numeric battery and out-of-range battery each call `setError()` and
  `requestFocus()`. The bounds live in one place: `DeviceValidator.MAX_BATTERY`.
- **Lifecycle:** every callback logs under the tags `MainActivityLC` and `EditDeviceActivityLC`
  (filter Logcat with `tag:ActivityLC`).
- **Manifest:** only `MainActivity` is exported; `EditDeviceActivity` has `exported="false"`.

## Tests
`.\gradlew.bat testDebugUnitTest` runs `DeviceValidatorTest` (7 JVM tests, no emulator needed).

## Build configuration
AGP 9.4.0, Gradle 9.6.0, compileSdk/targetSdk 36, minSdk 26, appcompat 1.7.1, material 1.13.0.

## Evidence (fill in after running)
| Item | Value |
|------|-------|
| Device / API level | |
| Git commit ID | |
