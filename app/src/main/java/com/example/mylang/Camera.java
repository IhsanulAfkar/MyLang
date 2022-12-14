package com.example.mylang;

import androidx.annotation.NonNull;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Camera extends AppCompatActivity {
    Button btnCam;
    ImageView placeholder;

    private static final int cameracode = 222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        btnBack.setOnClickListener(operasi);
        logo.setOnClickListener(operasi);
    }

//    @Override
    View.OnClickListener operasi = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:goBack();break;
            case R.id.logo:goHome();break;
            }
        }
    };

    void goBack(){
        finish();
//        Intent move = new Intent(getBaseContext(), Home.class);
//        startActivityForResult(move, 0);
    }

    void goHome(){
        Intent move = new Intent(getBaseContext(), Home.class);
        startActivityForResult(move, 0);
    }
}