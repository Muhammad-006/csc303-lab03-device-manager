package com.example.devicemanager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Extra keys are prefixed with the package name so they cannot collide with
    // keys used by other components or apps.
    public static final String EXTRA_DEVICE_NAME = "com.example.devicemanager.DEVICE_NAME";
    public static final String EXTRA_BATTERY = "com.example.devicemanager.BATTERY";

    private static final String TAG = "MainActivityLC";
    private static final String DOCS_URL = "https://developer.android.com";
    private static final String STATE_NAME = "state_device_name";
    private static final String STATE_BATTERY = "state_device_battery";

    private String deviceName = "Sensor-A";
    private int deviceBattery = 15;
    private TextView summaryView;

    // Task 5 - Activity Result API (replaces the deprecated onActivityResult()).
    private final ActivityResultLauncher<Intent> editLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (result.getResultCode() == RESULT_OK && data != null) {
                    String name = data.getStringExtra(EXTRA_DEVICE_NAME);
                    int battery = data.getIntExtra(EXTRA_BATTERY, -1);
                    if (name != null && DeviceValidator.isBatteryInRange(battery)) {
                        deviceName = name;
                        deviceBattery = battery;
                        updateSummary();
                    }
                } else {
                    Toast.makeText(this, R.string.edit_cancelled, Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        SystemBars.applyPadding(findViewById(R.id.main));

        if (savedInstanceState != null) {
            // Keep the values across rotation (they are not kept across process death).
            deviceName = savedInstanceState.getString(STATE_NAME, deviceName);
            deviceBattery = savedInstanceState.getInt(STATE_BATTERY, deviceBattery);
        }

        summaryView = findViewById(R.id.tvSummary);
        Button editButton = findViewById(R.id.btnEditDevice);
        Button docsButton = findViewById(R.id.btnOpenDocs);

        // Task 3 - explicit intent: the target class is named directly.
        editButton.setOnClickListener(view -> {
            Intent editIntent = new Intent(this, EditDeviceActivity.class);
            editIntent.putExtra(EXTRA_DEVICE_NAME, deviceName);
            editIntent.putExtra(EXTRA_BATTERY, deviceBattery);
            editLauncher.launch(editIntent);
        });

        // Task 6 - implicit intent: Android picks an app (a browser) that can VIEW the URL.
        docsButton.setOnClickListener(view -> openDocumentation());

        updateSummary();
    }

    private void updateSummary() {
        summaryView.setText(getString(R.string.summary_format, deviceName, deviceBattery));
    }

    private void openDocumentation() {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DOCS_URL));
        try {
            startActivity(viewIntent);
        } catch (ActivityNotFoundException ex) {
            // No installed app can handle the intent (for example an emulator without a browser).
            Toast.makeText(this, R.string.no_browser, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_NAME, deviceName);
        outState.putInt(STATE_BATTERY, deviceBattery);
    }

    // Task 7 - lifecycle callbacks logged under the tag MainActivityLC.
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
