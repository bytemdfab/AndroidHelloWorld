package com.example.AndroidHelloWorld;

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

import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private WifiManager wifiManager;
    private Switch swStatus;
    private Button btnScan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        swStatus = (Switch) findViewById(R.id.swStatus);
        btnScan = (Button) findViewById(R.id.btnRescan);

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

        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    checkWifiStatus();

                    if (isWifiEnabled()) {
                        scanWifi();
                    }
                }
            }
        }, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }

    private void disableWifi() {
        if (wifiManager.setWifiEnabled(false)) {
            displayWifiStatus(false);
        }
    }

    private void enableWifi() {
        if (wifiManager.setWifiEnabled(true)) {
            displayWifiStatus(true);
//            scanWifi();
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

            List<ScanResult> scanResults = wifiManager.getScanResults();

            if (scanResults == null) {
                return;
            }

            //ArrayAdapter<ScanResult> adapter = new ArrayAdapter<ScanResult>(getApplicationContext(), android.R.layout.simple_list_item_1, scanResults);

            WifiScanArrayAdapter adapter = new WifiScanArrayAdapter(getBaseContext(), scanResults);

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }
}
