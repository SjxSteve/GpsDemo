package com.example.gpslocationdemo;

//@Author: Sun Jingxuan
//@Date: Nov 11 2018

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView GpsLocation;
    private TextView NetworkLocation;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GpsLocation = findViewById(R.id.GpsLocation);
        NetworkLocation = findViewById(R.id.NetworkLocation);

        // get system service
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();

        }
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "Please connect to network", Toast.LENGTH_SHORT).show();
        }

        // permission check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, GpsListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, NetworkListener);
    }

    // Gps location listener
    LocationListener GpsListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(LocationManager.GPS_PROVIDER);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    // Network location listener
    LocationListener NetworkListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(LocationManager.NETWORK_PROVIDER);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    // show the location in textview
    private void showLocation(String provider) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        switch(provider){
            case LocationManager.GPS_PROVIDER:
                String stringGps = "Gps Location:"+"\n"+"纬度: "+location.getLatitude()+"\n"+"经度："+location.getLongitude();
                GpsLocation.setText(stringGps);
                break;
            case LocationManager.NETWORK_PROVIDER:
                String stringNetwork = "Network Location:"+"\n"+"纬度: "+location.getLatitude()+"\n"+"经度："+location.getLongitude();
                NetworkLocation.setText(stringNetwork);
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(GpsListener);
            locationManager.removeUpdates(NetworkListener);
        }
    }

    // permission apply
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }
}
