package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class tesimg extends AppCompatActivity {

    Button b1;
    ImageView iv;
    private static final int kodekamera= 1888;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;
    String nmFile;
    File f;
    String photoPath;
    private ObjectDetector objectDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesimg);

        b1 = (Button) findViewById(R.id.bSnap);
        iv = (ImageView) findViewById(R.id.iv);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(it,kodekamera);
            }
        });

        ObjectDetectorOptions options =
                new ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                        .enableMultipleObjects()
                        .enableClassification()
                        .build();

        objectDetector = ObjectDetection.getClient(options);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if (resultCode== Activity.RESULT_OK) {
            switch (requestCode) {
                case (kodekamera):
                    try {
                        prosesKamera(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    private void prosesKamera(Intent datanya) throws IOException{
        Bitmap bm;
        bm = (Bitmap) datanya.getExtras().get("data");
        iv.setImageBitmap(bm);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 85, stream);
        byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
//        Toast.makeText(getBaseContext(), "fail",Toast.LENGTH_LONG).show();
        InputImage image = InputImage.fromByteArray(byteArray,bm.getWidth(),bm.getHeight(), 0, InputImage.IMAGE_FORMAT_NV21);

//        if(image.getByteBuffer() == null){
//            Toast.makeText(getBaseContext(), image.getByteBuffer().toString(),Toast.LENGTH_LONG).show();
//        }
        objectDetector.process(image)
                .addOnSuccessListener(
                        detectedObjects -> {
                            // Task completed successfully
                            StringBuilder sb = new StringBuilder();
//                            List<BoxWithText> list = new ArrayList<>();
                            for (DetectedObject object : detectedObjects) {
                                for (DetectedObject.Label label : object.getLabels()) {
                                    sb.append(label.getText()).append(" : ")
                                            .append(label.getConfidence()).append("\n");
                                    Toast.makeText(getBaseContext(), label.getText(), Toast.LENGTH_LONG).show();
                                }
                                if (!object.getLabels().isEmpty()) {
//                                    Toast.makeText(getBaseContext(), object.getLabels().get(0).getText(), Toast.LENGTH_LONG).show();
//                                    list.add(new BoxWithText(object.getLabels().get(0).getText(), object.getBoundingBox()));
                                } else {

                                    Toast.makeText(getBaseContext(), "unknown", Toast.LENGTH_LONG).show();
//                                    list.add(new BoxWithText("Unknown", object.getBoundingBox()));
                                }
                            }
//                            getInputImageView().setImageBitmap(drawDetectionResult(bitmap, list));
                            if (detectedObjects.isEmpty()) {
//                                getOutputTextView().setText("Could not detect!!");
                                Toast.makeText(getBaseContext(), "Couldn't detect!!", Toast.LENGTH_LONG).show();
                            } else {
//                                getOutputTextView().setText(sb.toString());
                                Toast.makeText(getBaseContext(), sb.toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            // Task failed with an exception
                            // ...
                            e.printStackTrace();
                        });
//    }
//        runDetection(bm);
        // Set imageview to image that was
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
//        // save it in your external storage.
//        File dir=  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        File output=new File(dir, "simpan.png");
//        FileOutputStream fo = new FileOutputStream(output);
//        fo.write(byteArray);
//        fo.flush();
//        fo.close();

//        Toast.makeText(this,"Data Telah Terload ke ImageView" , Toast.LENGTH_SHORT).show();
    }
//    protected void runDetection(Bitmap bitmap) {
//        InputImage image = InputImage.fromBitmap(bitmap, 0);
//        if(image.getByteBuffer() == null){
//        Toast.makeText(getBaseContext(), "fail",Toast.LENGTH_LONG).show();
//        }
//        objectDetector.process(image)
//                .addOnSuccessListener(
//                        detectedObjects -> {
//                            // Task completed successfully
//                            StringBuilder sb = new StringBuilder();
////                            List<BoxWithText> list = new ArrayList<>();
//                            for (DetectedObject object : detectedObjects) {
//                                for (DetectedObject.Label label : object.getLabels()) {
//                                    sb.append(label.getText()).append(" : ")
//                                            .append(label.getConfidence()).append("\n");
////                                    Toast.makeText(getBaseContext(), label.getText(), Toast.LENGTH_LONG).show();
//                                }
//                                if (!object.getLabels().isEmpty()) {
////                                    Toast.makeText(getBaseContext(), object.getLabels().get(0).getText(), Toast.LENGTH_LONG).show();
////                                    list.add(new BoxWithText(object.getLabels().get(0).getText(), object.getBoundingBox()));
//                                } else {
//
////                                    Toast.makeText(getBaseContext(), "unknown", Toast.LENGTH_LONG).show();
////                                    list.add(new BoxWithText("Unknown", object.getBoundingBox()));
//                                }
//                            }
////                            getInputImageView().setImageBitmap(drawDetectionResult(bitmap, list));
//                            if (detectedObjects.isEmpty()) {
////                                getOutputTextView().setText("Could not detect!!");
////                                Toast.makeText(getBaseContext(), "Couldn't detect!!", Toast.LENGTH_LONG).show();
//                            } else {
////                                getOutputTextView().setText(sb.toString());
////                                Toast.makeText(getBaseContext(), sb.toString(), Toast.LENGTH_LONG).show();
//                            }
//                        })
//                .addOnFailureListener(
//                        e -> {
//                            // Task failed with an exception
//                            // ...
//                            e.printStackTrace();
//                        });
//    }
}