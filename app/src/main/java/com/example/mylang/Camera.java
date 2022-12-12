package com.example.mylang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Camera extends AppCompatActivity {
    Button btnCam;
    ImageView placeholder;
    private static final int cameracode = 222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

//        btnCam = (Button) findViewById(R.id.btnCam);
//        placeholder = (ImageView) findViewById(R.id.placeholder);
//
//        btnCam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(it, cameracode);
//            }
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case (cameracode):
//                    try {
//                        cameraProc(data);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
//    }
//
//    private void cameraProc(Intent zedata) throws IOException{
//        Bitmap bm;
//        bm = (Bitmap) zedata.getExtras().get("data");
//        placeholder.setImageBitmap(bm);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArr = stream.toByteArray();
//
//        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
////        File output = new File (dir, "s
////        impan.png");
////        FileOutputStream fo = new FileOutputStream(output);
////        fo.write(byteArr);
////        fo.flush();
////        fo.close();
//        Date d = new Date();
//        CharSequence s = DateFormat.format("dd-MM-yy hh-mm-ss", d.getTime());
//        File img  = new File(dir, s.toString() + ".png");
//        Uri urisavedimg = Uri.fromFile(img);
//        zedata.putExtra(MediaStore.EXTRA_OUTPUT, urisavedimg);
//    }
}