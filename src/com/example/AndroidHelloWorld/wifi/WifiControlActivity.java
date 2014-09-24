package com.example.AndroidHelloWorld.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.AndroidHelloWorld.R;

import java.util.List;

public class WifiControlActivity extends Activity {

    private WifiManager wifiManager;
    private Switch swStatus;
    private Button btnScan;
    private ListView listView;
    private final MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                checkWifiStatus();

                if (isWifiEnabled()) {
                    scanWifi();
                }
            } if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                List<ScanResult> scanResults = wifiManager.getScanResults();

                if (scanResults == null) {
                    return;
                }

                WifiScanArrayAdapter adapter = new WifiScanArrayAdapter(getBaseContext(), scanResults);
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        swStatus = (Switch) findViewById(R.id.swStatus);
        btnScan = (Button) findViewById(R.id.btnRescan);
        listView = (ListView) findViewById(R.id.listView);

        checkWifiStatus();

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
            }
        });

        swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean wifiEnabled = isWifiEnabled();
                if (isChecked && !wifiEnabled) {
                    enableWifi();
                } else if (!isChecked && wifiEnabled) {
                    disableWifi();
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(broadcastReceiver);
    }

    private void disableWifi() {
        if (wifiManager.setWifiEnabled(false)) {
            displayWifiStatus(false);
            clearListView();
        }
    }

    private void clearListView() {
        ((WifiScanArrayAdapter)listView.getAdapter()).clearAll();
    }

    private void enableWifi() {
        if (wifiManager.setWifiEnabled(true)) {
            displayWifiStatus(true);
        }
    }

    private void checkWifiStatus() {
        boolean status = isWifiEnabled();
        displayWifiStatus(status);
    }

    private void displayWifiStatus(boolean status) {
        swStatus.setChecked(status);
        setVisibility(status);
    }

    private boolean isWifiEnabled() {
        return wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }

    private void setVisibility(boolean status) {
        btnScan.setEnabled(status);
    }

    private void scanWifi() {
        if (wifiManager.startScan()) {
            Toast.makeText(this, R.string.scan_started_text, Toast.LENGTH_SHORT).show();
        }
    }
}
