package com.example.mylang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Camera extends AppCompatActivity {
    public static final int SCALING_FACTOR = 10;
    ImageView b1;
    ImageView iv;
    String benda;
    String benda1;
    private Translator tljp;
    private static final int kodekamera= 1888;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;
    String nmFile;
    File f;
    private ObjectDetector objectDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        b1 = (ImageView) findViewById(R.id.btnCam);
        iv = (ImageView) findViewById(R.id.placeholder);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(it,kodekamera);
            }
        });

//        LocalModel localModel =
//                new LocalModel.Builder()
//                        .setAssetFilePath("model.tflite")
//                        // or .setAbsoluteFilePath(absolute file path to model file)
//                        // or .setUri(URI to model file)
//                        .build();

        ObjectDetectorOptions options =
                new ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                        .enableMultipleObjects()
                        .enableClassification()
                        .build();

        objectDetector = ObjectDetection.getClient(options);

        TranslatorOptions opjp = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.JAPANESE)
                .build();

        tljp = Translation.getClient(opjp);

        DownloadConditions dc = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        tljp.downloadModelIfNeeded(dc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Fail to download model", Toast.LENGTH_LONG).show();
                    }
                });
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

    public void translate(String natname){
        tljp.translate(natname)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        //
//                        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                        TextView txtObj = (TextView) findViewById(R.id.txtObj);
                        txtObj.setText(s);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Failed to translate" , Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void prosesKamera(Intent datanya) throws IOException{
        Bitmap bm;
//        String benda;
        bm = (Bitmap) datanya.getExtras().get("data");
        iv.setImageBitmap(bm);
        BitmapDrawable imgToPass = (BitmapDrawable) iv.getDrawable();
        Bitmap temp = imgToPass.getBitmap();
//        Bitmap smallerBm = Bitmap.createScaledBitmap(
//                temp,
//                temp.getWidth()/SCALING_FACTOR,
//                temp.getHeight()/SCALING_FACTOR,
//                false
//        );
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 85, stream);
//        byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
        InputImage image = InputImage.fromBitmap(temp, 0);


//        if(image.getByteBuffer() == null){
//            Toast.makeText(getBaseContext(), "fail",Toast.LENGTH_LONG).show();
//        } else {
        objectDetector.process(image)
                .addOnSuccessListener(
                        detectedObjects -> {
//                            Toast.makeText(getBaseContext(), "ya",Toast.LENGTH_LONG).show();
                            // Task completed successfully
                            StringBuilder sb = new StringBuilder();
//                            List<BoxWithText> list = new ArrayList<>();

                            for (DetectedObject object : detectedObjects) {
                                for (DetectedObject.Label label : object.getLabels()) {
                                    sb.append(label.getText()).append("\n");
//                                    Toast.makeText(getBaseContext(), label.getText(), Toast.LENGTH_LONG).show();
                                }
                                if (!object.getLabels().isEmpty()) {
//                                    Toast.makeText(getBaseContext(), "object.getLabels().get(0).getText()", Toast.LENGTH_LONG).show();
//                                    list.add(new BoxWithText(object.getLabels().get(0).getText(), object.getBoundingBox()));
                                } else {

//                                    Toast.makeText(getBaseContext(), "unknown", Toast.LENGTH_LONG).show();
//                                    list.add(new BoxWithText("Unknown", object.getBoundingBox()));
                                }
                            }
//                            getInputImageView().setImageBitmap(drawDetectionResult(bitmap, list));
                            if (detectedObjects.isEmpty()) {
//                                getOutputTextView().setText("Could not detect!!");
                                Toast.makeText(getBaseContext(), "Couldn't detect!!", Toast.LENGTH_LONG).show();
                            } else {
//                                getOutputTextView().setText(sb.toString());
                                benda = sb.toString();
//                                Toast.makeText(getBaseContext(), benda+"ss", Toast.LENGTH_LONG).show();
//                                Toast.makeText(getBaseContext(), translate(benda), Toast.LENGTH_LONG).show();
                                translate(benda);
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            // Task failed with an exception
                            // ...
                            e.printStackTrace();
                        });
    }
}