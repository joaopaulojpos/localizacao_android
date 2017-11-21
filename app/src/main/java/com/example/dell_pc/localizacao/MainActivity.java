package com.example.dell_pc.localizacao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private TextView txtLatitude;
    private  TextView txtLongitude;
    //private Location location;
    //private LocationManager locationManager;


    private GoogleApiClient mGoogleApiClient;
    private static final int MY_LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (checkLocationPermission()) {
            callConnection();
        }

        /**
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "GPS DESLIGADO", Toast.LENGTH_SHORT).show();
        }else {
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (location != null) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            txtLongitude.setText("Latitude: " + latitude);
            txtLatitude.setText("Longitude: " + longitude);
        }
         */

        /**
        GpsTracker gt = new GpsTracker(getApplicationContext());
        Location l = gt.getLocation();
        if( l == null){
            Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
        }else {
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
        }
         */
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private synchronized void callConnection(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("LOG", "onConnected" + bundle + ")");

        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (l != null) {
            Log.i("LOG", "Latitude:" + l.getLatitude() + ")");
            Log.i("LOG", "longitude" + l.getLongitude() + ")");
            double longitude = l.getLongitude();
            double latitude = l.getLatitude();

            txtLongitude.setText("Longitude: " + longitude);
            txtLatitude.setText("Latitude: " + latitude);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
