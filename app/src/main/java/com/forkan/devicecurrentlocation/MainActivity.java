package com.forkan.devicecurrentlocation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    double lat1 = 0;
    double lat2 = 0;
    double lon1 = 0;
    double lon2 = 0;

    double distanceinKm = 0;

    TextView tvCountry, tvDivision, tvArea, tvCity, tvPostalCode, tvAddress, distanceKM;
    LocationManager locationManager;

    Button locationButton, startLocation, stopLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Invoice Details");

        grantPermission();

        tvCountry = findViewById(R.id.tv_country);
        tvDivision = findViewById(R.id.tv_division);
        tvCity = findViewById(R.id.tv_city);
        tvArea = findViewById(R.id.tv_area);
        tvPostalCode = findViewById(R.id.tv_postal_code);
        tvAddress = findViewById(R.id.tv_address);
        tvAddress = findViewById(R.id.tv_address);

        distanceKM = findViewById(R.id.distance_km);


        locationButton = findViewById(R.id.btn_current_location);
        startLocation = findViewById(R.id.btn_current_location_start);
        stopLocation = findViewById(R.id.btn_current_location_stop);


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
                Toast.makeText(getApplicationContext(), "LOC", Toast.LENGTH_SHORT).show();
            }
        });


        checkLocationIsEnabledOrNot(); //this will redirect us to the location setting
        //     getMyLocation();


    }

    private void getMyLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    private void checkLocationIsEnabledOrNot() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //this intent redirect us to the location settings, if GPS is disabled this dialog will be show.
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        }
                    }).setNegativeButton("Cancel", null).show();
        }
    }

    //get permission method
    private void grantPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            tvCountry.setText(addresses.get(0).getCountryName());
            tvDivision.setText(addresses.get(0).getAdminArea());
            tvCity.setText(addresses.get(0).getLocality());
            tvArea.setText(addresses.get(0).getSubLocality());
            tvPostalCode.setText(addresses.get(0).getPostalCode());
            tvAddress.setText(addresses.get(0).getAddressLine(0));


            startLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lat1 = addresses.get(0).getLatitude();
                    lon1 = addresses.get(0).getLongitude();

                    Log.d("TAG_LAT", lat1 + "");
                    Log.d("TAG_LON", lon1 + "");
                    Toast.makeText(getApplicationContext(), "TAG_LAT_1: " + lat1, Toast.LENGTH_LONG).show();
                }
            });


            stopLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lat2 = addresses.get(0).getLatitude();
                    lon2 = addresses.get(0).getLongitude();

                    distance(lat1, lon1, lat2, lon2);
                    Toast.makeText(getApplicationContext(), "TAG_LAT_2: " + lat2, Toast.LENGTH_LONG).show();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        distanceinKm += (float) dist / 0.62137;  //divide to get km from miles

        distanceKM.setText((int) distanceinKm + " km");
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    //Back navigation
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lat1 = lat2;
        lon1 = lon2;
        Toast.makeText(getApplicationContext(), "Back Pressed", Toast.LENGTH_SHORT).show();
    }
}