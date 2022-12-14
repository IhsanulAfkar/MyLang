package com.example.mylang;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mylang.databinding.ActivityMapBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private Translator tljp;
    TextView txtLocTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        binding = ActivityMapBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        Button go = (Button) findViewById(R.id.btnGo);
        Button seek = (Button) findViewById(R.id.btnSeek);
        go.setOnClickListener(op);
        seek.setOnClickListener(op);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        btnBack.setOnClickListener(op);
        logo.setOnClickListener(op);

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ITS = new LatLng(-7.2819705, 112.795323);
        mMap.addMarker(new MarkerOptions().position(ITS).title("Marker in ITS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ITS));
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnGo:hideKeys(view);gotoSpot();break;
                case R.id.btnSeek:hideKeys(view);goSeek();break;
                case R.id.btnBack:goBack();break;
                case R.id.logo:goHome();break;
            }
        }
    };

    private void hideKeys(View view){
        InputMethodManager a = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    private void gotoMap(Double lat, Double lon, float zoom){
        LatLng newLoc = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(newLoc).title("Marker in (" +lat +":" +lon));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc,zoom));
    }

    private void gotoSpot(){
        EditText lat = (EditText) findViewById(R.id.txtLat);
        EditText lon = (EditText) findViewById(R.id.txtLon);
        EditText zoom = (EditText) findViewById(R.id.txtZoom);


        Double latnum = Double.parseDouble(lat.getText().toString());
        Double lonnum = Double.parseDouble(lon.getText().toString());
        Float zoomnum = Float.parseFloat(zoom.getText().toString());
        if (zoomnum == null){
            zoomnum = Float.parseFloat("8");
        }

        Toast.makeText(this, "Showing Lat: " +latnum + " Lon: " +lonnum, Toast.LENGTH_LONG).show();
        gotoMap(latnum, lonnum, zoomnum);
        getAddress(latnum, lonnum);
    }
    public void getAddress(double lat, double lng) {
        Geocoder g = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = g.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String natname = obj.getCountryName();
            translate(natname);
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void translate(String natname){
        tljp.translate(natname)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        txtLocTrans = findViewById(R.id.txtLocTrans);
                        txtLocTrans.setText(s);
                        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Failed to translate" , Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void goSeek(){
        EditText basho = (EditText) findViewById(R.id.txtLoc);
        Geocoder g = new Geocoder(getBaseContext());
        try {
            List<Address> daftar =
                    g.getFromLocationName(basho.getText().toString(),1);
            Address alamat = daftar.get(0);
            String alamatFound = alamat.getAddressLine(0);
            Double lintang = alamat.getLatitude();
            Double bujur = alamat.getLongitude();

            Toast.makeText(getBaseContext(), "Found " +alamatFound + "\nLat: " +lintang + "\nLon: " +bujur, Toast.LENGTH_LONG).show();

            EditText zoom = (EditText) findViewById(R.id.txtZoom);
            Float zoomnum = Float.parseFloat(zoom.getText().toString());
            if (zoomnum == null){
                zoomnum = Float.parseFloat("8");
            }
            Toast.makeText(this, "Move to " +alamatFound + "\nLat: " +lintang + "\nLon: " +bujur, Toast.LENGTH_LONG).show();
            gotoMap(lintang,bujur,zoomnum);
            getAddress(lintang, bujur);

            EditText lattxt = (EditText) findViewById(R.id.txtLat);
            EditText lontxt = (EditText) findViewById(R.id.txtLon);
            lattxt.setText(lintang.toString());
            lontxt.setText(bujur.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

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