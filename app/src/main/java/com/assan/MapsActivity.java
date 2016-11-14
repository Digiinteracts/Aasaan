package com.assan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

import Utils.Constant;
import Utils.GPSTrackersonu;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {
    String latitude, longitude,outletLat,outletLong;
    private GoogleMap mMap;
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    SupportMapFragment mapFragment;
    TextView naviate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            outletLat = bundle.getString("lat");
            outletLong = bundle.getString("long");
        }
        buildGoogleApiClient();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

         Toast.makeText(getApplicationContext(),"lat "+Constant.LATITUDE+"LONG "+"outla t" +outletLat+"loing "+outletLong,Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(),"outla t" +outletLat+"loing "+outletLong,Toast.LENGTH_SHORT).show();
        naviate_btn = (TextView) findViewById(R.id.naviate_btn);
        naviate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://maps.google.com/?saddr=" + Constant.LATITUDE +","+  Constant.LONGITUDE + "&daddr="+outletLat+","+outletLong); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        /*double lat = Double.parseDouble(outletLat);
        double outlong = Double.parseDouble(outletLong);

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, outlong))
                .title("Hello world"));*/
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        GPSTrackersonu gps = new GPSTrackersonu(getApplicationContext());
        String address = getCompleteAddressString(Double.valueOf(gps.getLatitude()), Double.valueOf(gps.getLongitude()));
        mMap = googleMap;
        LatLng sydney = new LatLng(Double.valueOf(gps.getLatitude()), Double.valueOf(gps.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromResource(R.drawable.address_icon)).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        // mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitude= String.valueOf(mLastLocation.getLatitude());
            longitude= String.valueOf(mLastLocation.getLongitude());

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        latitude = String.valueOf(location.getLongitude());

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }


    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 2; i < 3; i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("address", "" + strReturnedAddress.toString());
            } else {

                Log.w("My ", "No Address returned!");

            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.w("My ", "Canont get Address!");

        }

        return strAdd;
    }

}
