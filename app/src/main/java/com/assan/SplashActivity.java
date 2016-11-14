package com.assan;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import Utils.AppSession;

/**
 * Created by Sonu Saini on 4/12/2016.
 */
public class SplashActivity extends Activity implements Animation.AnimationListener{
    ImageView imgPoster;
    // Animation
    Animation animSideDown;
    AppSession appSession;
    ProgressDialog mProgressDialog;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Location mLastLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        animSideDown = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_down);
        appSession=new AppSession(getApplicationContext());

        imgPoster = (ImageView) findViewById(R.id.imgLogo);
        animSideDown.setAnimationListener(SplashActivity.this);

        imgPoster.startAnimation(animSideDown);



    }
    @Override
    public void onBackPressed() {

    }
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}


        if(!gps_enabled || !isNetworkAvailable()) {
            // notify user
            if(!isNetworkAvailable()) {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                TextView title = (TextView) dialog.findViewById(R.id.title);
                title.setText("Please check your internet connection...");
                TextView ok = (TextView) dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent t = new Intent(SplashActivity.this, SplashActivity.class);
                        startActivity(t);
                        finish();
                    }
                });
                dialog.show();
            }
            else {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                TextView title = (TextView) dialog.findViewById(R.id.title);
                title.setText("Please first on your Location...");
                TextView ok = (TextView) dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent t = new Intent(SplashActivity.this, SplashActivity.class);
                        startActivity(t);
                        finish();
                    }
                });
                dialog.show();
            }
        }
        else {
            if (appSession.getUserloginStatus() != null) {
                Intent intent = new Intent(SplashActivity.this, Home.class);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
