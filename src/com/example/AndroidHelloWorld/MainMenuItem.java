package com.example.AndroidHelloWorld;

import com.example.AndroidHelloWorld.camera.CameraActivity;
import com.example.AndroidHelloWorld.cameraView.CameraViewActivity;
import com.example.AndroidHelloWorld.wifi.WifiControlActivity;

public enum MainMenuItem {
    WIFI_CONTROL("WiFi Control", WifiControlActivity.class),
    CAMERA("Camera usage", CameraActivity.class),
    CAMERA_VIEW("Camera view", CameraViewActivity.class);

    private final String displayName;

    public Class getActivity() {
        return activity;
    }

    private final Class activity;

    MainMenuItem(String displayName, Class activity) {
        this.displayName = displayName;
        this.activity = activity;
    }


    @Override
    public String toString() {
        return displayName;
    }
}
