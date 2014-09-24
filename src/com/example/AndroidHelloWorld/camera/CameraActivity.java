package com.example.AndroidHelloWorld.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.AndroidHelloWorld.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity {

    private final int CAMERA_RESULT_THUMB = 0;
    private final int CAMERA_RESULT_FULL = 1;
    private ImageView imageView;
    private android.net.Uri mFullPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void btnCameraThumb_Click(View v) {
        if (v.getId() == R.id.btnCameraThumb) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_RESULT_THUMB);
        }
    }

    public void btnCameraFull_Click(View v) {
        if (v.getId() == R.id.btnCameraFull) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            if (imageFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(cameraIntent, CAMERA_RESULT_FULL);
            }

        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mFullPhotoUri = Uri.fromFile(image);

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT_THUMB) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);
        } else if (requestCode == CAMERA_RESULT_FULL) {
            imageView.setImageURI(mFullPhotoUri);
        }
    }
}
