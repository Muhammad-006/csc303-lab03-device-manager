package com.example.devicemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditDeviceActivity extends AppCompatActivity {

    private static final String TAG = "EditDeviceActivityLC";

    private EditText deviceNameInput;
    private EditText batteryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_edit_device);
        SystemBars.applyPadding(findViewById(R.id.main));

        deviceNameInput = findViewById(R.id.etDeviceName);
        batteryInput = findViewById(R.id.etBattery);
        Button saveButton = findViewById(R.id.btnSave);
        Button cancelButton = findViewById(R.id.btnCancel);

        // Pre-fill the form with the values sent by MainActivity (only on first creation;
        // after rotation Android restores what the user typed).
        if (savedInstanceState == null) {
            Intent incoming = getIntent();
            String name = incoming.getStringExtra(MainActivity.EXTRA_DEVICE_NAME);
            int battery = incoming.getIntExtra(MainActivity.EXTRA_BATTERY, -1);
            if (name != null) {
                deviceNameInput.setText(name);
            }
            if (battery >= 0) {
                batteryInput.setText(String.valueOf(battery));
            }
        }

        saveButton.setOnClickListener(view -> saveIfValid());
        cancelButton.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    /** Task 4 - validate, then return the edited data to MainActivity. */
    private void saveIfValid() {
        String name = deviceNameInput.getText().toString().trim();
        String batteryText = batteryInput.getText().toString().trim();

        deviceNameInput.setError(null);
        batteryInput.setError(null);

        if (!DeviceValidator.isNameValid(name)) {
            deviceNameInput.setError(getString(R.string.error_name_required));
            deviceNameInput.requestFocus();
            return;
        }

        Integer battery = DeviceValidator.parseBattery(batteryText);
        if (battery == null) {
            batteryInput.setError(getString(R.string.error_battery_number));
            batteryInput.requestFocus();
            return;
        }
        if (!DeviceValidator.isBatteryInRange(battery)) {
            batteryInput.setError(getString(R.string.error_battery_range,
                    DeviceValidator.MIN_BATTERY, DeviceValidator.MAX_BATTERY));
            batteryInput.requestFocus();
            return;
        }

        Intent resultData = new Intent();
        resultData.putExtra(MainActivity.EXTRA_DEVICE_NAME, name);
        resultData.putExtra(MainActivity.EXTRA_BATTERY, battery.intValue());
        setResult(RESULT_OK, resultData);
        finish(); // returns control to MainActivity's registered ActivityResultLauncher callback
    }

    // Lifecycle callbacks logged under the tag EditDeviceActivityLC.
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
