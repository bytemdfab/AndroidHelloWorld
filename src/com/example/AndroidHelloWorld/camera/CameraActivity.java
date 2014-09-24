package com.example.AndroidHelloWorld.camera;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.example.AndroidHelloWorld.R;

public class CameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, R.string.camera_hello_message, Toast.LENGTH_SHORT).show();
    }
}
