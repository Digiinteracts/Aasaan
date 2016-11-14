package com.assan;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.SlideInEffect;
import com.twotoasters.jazzylistview.effects.WaveEffect;
import com.viewpagerindicator.CirclePageIndicator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.Constant;
import Utils.HorizontalUtil;
import Utils.Prefrence;
import Utils.RequestHandler;
import Utils.WebApiResult;
import Utils.WebRequest;
import Utils.homeutil;
import adapter.Headerapdater;
import adapter.SlidingImage_Adapter;
import adapter.homeadapter;
import it.sephiroth.android.library.widget.HListView;


/**
 * Created by Sonu Saini on 4/11/2016.
 */

public class Home extends Activity implements View.OnClickListener, WebApiResult,  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_FINE_LOCATION = 0;
    public static String CATNAME = "";
    public static String CATID = "";
    String latitude, longitude;
    LinearLayout ll_left;
    private GoogleMap mMap;
    Location mLastLocation = null;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    ProgressDialog mProgressDialog;
    // Declare Variables
    CirclePageIndicator indicator;
    homeadapter adapter;
    private List<Integer> imageIdList;
    private TextView indexText;
    Headerapdater headerapdater;
    String[] image;
    int count = 0;
    Timer myTimer;
    Handler mHandler;
    Runnable runnable;
    ArrayList<homeutil> arraylist = new ArrayList<homeutil>();
    ArrayList<String> CatArray = new ArrayList<String>();
    ArrayList<HorizontalUtil> harraylist = new ArrayList<HorizontalUtil>();
    LinearLayout ll_home, ll_more, ll_categorie, ll_boobmark, ll_account;
    TextView txt_address, txt_tryagain, quick_search, txt_home_active, txt_recent, txt_recent_active, txt_categorie, txt_categorie_active, txt_boobkmark, txt_boobkmark_active, txt_account, yourlocation;
    ImageView btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    Button btn_login;
    LinearLayout tv_search_outlet;
    AppSession appSession;
    private HListView hliv;
    public static Home instance;
   // private ListView listView;
   JazzyListView listView ;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
   // private static ViewPager mPager;
    private static cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    boolean checklogin = false;
    Prefrence prefrence;
    Utils.LightText txt_home;
    //private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<String> ImagesArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.home);
        //myTimer = new Timer();

        if (checkPlayServices()) {
            buildGoogleApiClient();
            // displayLocation();
        }
        instance = this;
        appSession = new AppSession(getApplicationContext());
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        tv_search_outlet = (LinearLayout) findViewById(R.id.tv_search_outlet);
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
        txt_tryagain = (TextView) findViewById(R.id.text_tryagain);
        txt_address = (TextView) findViewById(R.id.txtaddress);
        yourlocation = (TextView) findViewById(R.id.yourlocation);
        txt_account = (TextView) findViewById(R.id.txt_account);
        txt_home = (Utils.LightText) findViewById(R.id.txt_home);
        ll_left = (LinearLayout) findViewById(R.id.ll_left);
        //--------------------------------------------------------button----------------------
        btn_home = (ImageView) findViewById(R.id.btn_home);
        btn_home_acive = (ImageView) findViewById(R.id.btn_home_active);
        btn_recent = (ImageView) findViewById(R.id.btn_recent);
        btn_recent_active = (ImageView) findViewById(R.id.btn_recent_active);
        btn_categorie = (ImageView) findViewById(R.id.btn_cat);
        btn_categorie_active = (ImageView) findViewById(R.id.btn_cat_active);
        btn_bookmak = (ImageView) findViewById(R.id.btn_bookmark);
        btn_bookmark_active = (ImageView) findViewById(R.id.btn_bookmark_active);
        btn_account = (ImageView) findViewById(R.id.btn_account);
        btn_account_active = (ImageView) findViewById(R.id.btn_account_active);
        btn_login = (Button) findViewById(R.id.btn_login);
        //---------------------------------textview-----------------------------------------
        ll_home.setOnClickListener(this);
        tv_search_outlet.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        txt_tryagain.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        btn_home_acive.setVisibility(View.VISIBLE);
        btn_home.setVisibility(View.GONE);
       // listView = (ListView) findViewById(R.id.homelistview);
        listView =  (JazzyListView) findViewById(R.id.homelistview);
        listView.setTransitionEffect(new SlideInEffect());
        LayoutInflater inflater = getLayoutInflater();

        View header = inflater.inflate(R.layout.header, listView, false);
        hliv = (HListView) header.findViewById(R.id.hl_v);
        quick_search = (TextView) header.findViewById(R.id.txt_badge);
        //mPager = (ViewPager) header.findViewById(R.id.pager);
        mPager = (cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager) header.findViewById(R.id.pager);
        listView.addHeaderView(header, null, false);
        txt_tryagain.setVisibility(View.GONE);
        prefrence = new Prefrence();

        if (!String.valueOf(prefrence.getRecentSearch(this)).equals("null")){
            Log.e("Ram Ram", " " +prefrence.getRecentSearch(this).size()+1);
        }
        if (prefrence.getUserID(Home.this).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.account_icon);
            txt_account.setText("Login");
            checklogin = true;
        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
            txt_account.setText("Account");
            checklogin = false;
        }
     //   Log.e("tab or not", " " +getResources().getBoolean(R.bool.isTablet));
        registerUser();
        loadPermissions(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_FINE_LOCATION);

        txt_address.setTypeface(setfont("OpenSans-Regular.ttf"));
        quick_search.setTypeface(setfont("OpenSans-Light.ttf"));
        yourlocation.setTypeface(setfont("OpenSans-Light.ttf"));

        if (Constant.TabLayout_Home_Status == true){
            txt_home.setTextColor(getResources().getColor(R.color.lightred));
        }
        else {
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
    }

    public void viewpager(ArrayList<String> arraylist) {
        mPager.setAdapter(new SlidingImage_Adapter(getApplicationContext(), arraylist));
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = arraylist.size();
        // Auto start of viewpager

        mPager.setInterval(3000);
        mPager.startAutoScroll();


        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void registerUser() {

        register("", "", "", "2", "", "2.1");
    }
//getAllSubCat

    private void register(String name, String email, String password, String deviceType, String deviceId, String appVersion) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler ruc = new RequestHandler();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Home.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    txt_tryagain.setVisibility(View.GONE);

                    //  CommonUtils.showToast(getApplicationContext(),s);
                    try {
                        JSONObject jsonObj = new JSONObject(s);

                        JSONArray jsonSOSArray = jsonObj.getJSONArray("category");
                        Log.e("CATAGORY_DATA", jsonObj.toString());
                      /*  Log.e("Catogry:anil",jsonSOSArray.toString());*/
                        Constant.CatArray.clear();
                        for (int i = 0; i < jsonSOSArray.length(); i++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jsonobject = jsonSOSArray.getJSONObject(i);

                        //check mobile or tab
                            if(getResources().getBoolean(R.bool.isTablet)){
                                homeutil wp = new homeutil(jsonobject.getString("image_ipad"), jsonobject.getString("name"), jsonobject.getString("id"));
                                ImagesArray.add(wp.getSubImgaepath1());
                                arraylist.add(wp);
                                CatArray.add(jsonobject.getString("name"));
                                Constant.CatArray.add(jsonobject.getString("name"));
                                Constant.CatId.add(jsonobject.getString("id"));
                                Log.e("MOBILE TAB222", "tabx "+getResources().getBoolean(R.bool.isTablet));

                            }
                            else if(getResources().getBoolean(R.bool.isTablet600)) {
                                homeutil wp = new homeutil(jsonobject.getString("image"), jsonobject.getString("name"), jsonobject.getString("id"));
                                ImagesArray.add(wp.getSubImgaepath1());
                                arraylist.add(wp);
                                CatArray.add(jsonobject.getString("name"));
                                Constant.CatArray.add(jsonobject.getString("name"));
                                Constant.CatId.add(jsonobject.getString("id"));
                               // Log.e("MOBILE TAB", "tab600 "+(jsonobject.getString("image")));
                            }else if(getResources().getBoolean(R.bool.isphon_x)) {
                                homeutil wp = new homeutil(jsonobject.getString("image"), jsonobject.getString("name"), jsonobject.getString("id"));
                                ImagesArray.add(wp.getSubImgaepath1());
                                arraylist.add(wp);
                                CatArray.add(jsonobject.getString("name"));
                                Constant.CatArray.add(jsonobject.getString("name"));
                                Constant.CatId.add(jsonobject.getString("id"));
                                Log.e("MOBILE TAB", "tabx "+getResources().getBoolean(R.bool.isTablet));
                            }else {
                                homeutil wp = new homeutil(jsonobject.getString("image_6S"), jsonobject.getString("name"), jsonobject.getString("id"));
                                ImagesArray.add(wp.getSubImgaepath1());
                                arraylist.add(wp);
                                CatArray.add(jsonobject.getString("name"));
                                Constant.CatArray.add(jsonobject.getString("name"));
                                Constant.CatId.add(jsonobject.getString("id"));
                                Log.e("MOBILE TAB", "tabxx "+getResources().getBoolean(R.bool.isTablet));
                            }
                        }

                    } catch (JSONException e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    adapter = new homeadapter(getApplicationContext(), arraylist,latitude,longitude);
                    String str2 = arraylist.toString();
                    // Set the adapter to the ListView
                    System.out.println(str2);
                    listView.setAdapter(adapter);
                   /* viewpager(ImagesArray);*/
                    GetAllSubCat();
                    mProgressDialog.dismiss();
                    if (Constant.SELECTED_LOCATION == ""){
                         // Toast.makeText(getApplicationContext(),"LAt:-"+mLastLocation.getLatitude()+" ss  "+Constant.SELECTED_LOCATION ,Toast.LENGTH_SHORT).show();
                        txt_address.setText(Constant.UNSELECTED_LOCATION);
                    }
                    else {
                        txt_address.setText(Constant.SELECTED_LOCATION);
                    }
                } else {
                    txt_tryagain.setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                String result = ruc.sendGetRequest(AppConstants.BASEURL + "getcat_subCat");
                 Log.e("Register:anil", result);
                return result;
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute("", "", "", "", "", "");
    }
    //getallsubcatgreis--------------------
    private void GetAllSubCat() {
        allubcat("", "", "", "2", "", "2.1");
    }

    private void allubcat(String name, String email, String password, String deviceType, String deviceId, String appVersion) {

        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler ruc = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                try{
                String result = ruc.sendGetRequest(AppConstants.BASEURL + "getAllSubCat");
                Log.e("home:anilapi", result);
                return result;}
                catch (Exception e){
                    Log.e("HOME Activity subcat","Error "+e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    txt_tryagain.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObj = new JSONObject(s);
                        JSONArray banner = jsonObj.getJSONArray("banners");

                        ImagesArray.clear();
                        for (int i = 0; i < banner.length(); i++) {
                            if(getResources().getBoolean(R.bool.isTablet)) {
                                String ar = banner.getJSONObject(i).getString("androidtab");
                                ImagesArray.add(ar);
                            }
                            else if (getResources().getBoolean(R.bool.isphon_x)){
                                String ar = banner.getJSONObject(i).getString("iphone");
                                ImagesArray.add(ar);
                            }else{
                                String ar = banner.getJSONObject(i).getString("iphone");
                                ImagesArray.add(ar);
                            }
                        }
                        viewpager(ImagesArray);
                        //GetAllSubCat();
                        JSONArray jsonSOSArray = jsonObj.getJSONArray("subcategory");
                        Log.e("CATPARSE", jsonSOSArray.toString());
                        for (int i = 0; i < jsonSOSArray.length(); i++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jsonobject = jsonSOSArray.getJSONObject(i);
                            HorizontalUtil hwp = new HorizontalUtil(jsonobject.getString("logo"), jsonobject.getString("name"), jsonobject.getString("id"));
                            harraylist.add(hwp);
                            //Log.e("Subcatogry:harraylist",hwp.toString());
                        }
                    } catch (JSONException e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    headerapdater = new Headerapdater(getApplicationContext(), harraylist);
                    String str2 = arraylist.toString();
                    // Set the adapter to the ListView
                    System.out.println(str2);
                    hliv.setAdapter(headerapdater);
                    mProgressDialog.dismiss();
                } else {
                    txt_tryagain.setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                }
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute("", "", "", "", "", "");
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            moveTaskToBack(true);
           // finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_home) {
            Constant.TabLayout_Home_Status = true;
            Constant.TabLayout_Category_Status = false;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = false;
            Constant.TabLayout_Accounts_Status= false;

            btn_home_acive.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.GONE);

            btn_account_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);
            btn_bookmark_active.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);

            btn_account.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);
            btn_bookmak.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);

            Intent intenthome = new Intent(Home.this, Home.class);
            startActivity(intenthome);
            finish();

        } else if (v.getId() == R.id.ll_more) {

            Constant.TabLayout_Category_Status = false;
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = true;
            Constant.TabLayout_Accounts_Status= false;

            btn_recent_active.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.GONE);

            btn_account_active.setVisibility(View.GONE);
            btn_home_acive.setVisibility(View.GONE);
            btn_bookmark_active.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);

            btn_account.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_bookmak.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);

            Intent intent = new Intent(Home.this,MoreScreen.class);
            startActivity(intent);

        } else if (v.getId() == R.id.tv_search_outlet) {

            Constant.Check_current_status = false;
            Intent intent = new Intent(Home.this, SearchLocation.class);
            Log.e("result: Search Click", CatArray.toString());
            intent.putExtra("KEY_SEARCH", 2);
            intent.putExtra("CAT_ARRAY", CatArray);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);

        } else if (v.getId() == R.id.ll_categorie) {

            Constant.TabLayout_Category_Status = true;
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = false;
            Constant.TabLayout_Accounts_Status= false;

            btn_categorie_active.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.GONE);

            btn_account_active.setVisibility(View.GONE);
            btn_home_acive.setVisibility(View.GONE);
            btn_bookmark_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);

            btn_account.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_bookmak.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);

            Intent intcategory = new Intent(Home.this, CategoryActiviyt.class);
            startActivity(intcategory);
            finish();


        } else if (v.getId() == R.id.ll_bookmark) {
            btn_bookmark_active.setVisibility(View.VISIBLE);
            btn_bookmak.setVisibility(View.GONE);

            btn_account_active.setVisibility(View.GONE);
            btn_home_acive.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);

            btn_account.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);

            if (prefrence.getUserID(Home.this).equals("null")) {

               /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // LayoutInflater inflater = this.getLayoutInflater();
                alertDialogBuilder.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intenthome = new Intent(Home.this, LoginActivity.class);
                        startActivity(intenthome);
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();*/

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(Home.this, LoginActivity.class);
                        startActivity(intenthome);
                        finish();
                        dialog.dismiss();
                    }
                });
                dialog.show();

            } else {

                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Bookmark_Status = true;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= false;

                Intent intenthome = new Intent(Home.this, BookmarkActivity.class);
                startActivity(intenthome);
                finish();
            }

        } else if (v.getId() == R.id.ll_account) {

            btn_bookmark_active.setVisibility(View.GONE);
            btn_home_acive.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);

            if (prefrence.getUserID(Home.this).equals("null")) {
                Intent intent = new Intent(Home.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
            else {

                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= true;

                Intent intent = new Intent(Home.this, MyAccount.class);
                startActivity(intent);
                finish();
            }

            //Log.e("appsession:end",appSession.toString());

        } else if (v.getId() == R.id.text_tryagain) {

            registerUser();

        }
        else if (v.getId() == R.id.ll_left) {
            Constant.Check_current_status = true;
            Intent intent = new Intent(Home.this, SearchLocation.class);
            intent.putExtra("home_screen_flag",8);
            intent.putExtra("KEY_SEARCH",1);
            startActivity(intent);

        }
    }

    public void GetLocation(){
       // Log.e("long lat", "home1 " );
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.e("long lat", "home2 " );
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude= String.valueOf(mLastLocation.getLatitude());
            longitude= String.valueOf(mLastLocation.getLongitude());

            Constant.LATITUDE = String.valueOf(mLastLocation.getLatitude());;
            Constant.LONGITUDE = String.valueOf(mLastLocation.getLongitude());

            Log.e("long lat", "home " + latitude+"long "+longitude);
            if (Constant.SELECTED_LOCATION == ""){
                Log.e("long lat", "webRequest111 " );
                //  Toast.makeText(getApplicationContext(),"LAt:-"+mLastLocation.getLatitude(),Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(),"LOng:-"+mLastLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                WebRequest request = new WebRequest(getApplicationContext(), "http://maps.google.com/maps/api/geocode/json?latlng="+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+"&output=json&sensor=false", this, true);
                request.execute();
                appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                //txt_address.setText(Constant.UNSELECTED_LOCATION);
                Log.e("long lat", "webRequest22 " +Constant.UNSELECTED_LOCATION);
            }
            else {
                //txt_address.setText(Constant.SELECTED_LOCATION);
                appSession.setUserLocation(Constant.SELECTED_LOCATION);
            }
            mHandler.removeCallbacksAndMessages(null);
            myTimer.cancel();
            Log.e("long lat", "ssss " );
        }
        else{
           // Toast.makeText(getApplicationContext(), "(Couldn't get the location. Make sure location is enabled on the device)", Toast.LENGTH_SHORT).show();
            longitude ="";
            longitude = "";
        }
    }

    private void displayLocation() {
        try {
            mHandler = new Handler();
            runnable = new Runnable() {
                public void run() {
                    GetLocation();
                }
            };

            myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    mHandler.post(runnable);

                }
            },0, 200);
        }
        catch (Exception e){
            e.getMessage();
        }
        }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //  Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


/*
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
       // mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            //    here to request the missing permissions, and then overriding
            //    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //    int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        Log.e("mlast loca"," "+mLastLocation);
        if (mLastLocation != null) {
            latitude= String.valueOf(mLastLocation.getLatitude());
            longitude= String.valueOf(mLastLocation.getLongitude());

            Constant.LATITUDE = String.valueOf(mLastLocation.getLatitude());;
            Constant.LONGITUDE = String.valueOf(mLastLocation.getLongitude());

            Log.e("long lat", "home " + latitude+"long "+longitude);
            if (Constant.SELECTED_LOCATION == ""){
              //  Toast.makeText(getApplicationContext(),"LAt:-"+mLastLocation.getLatitude(),Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(),"LOng:-"+mLastLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                WebRequest request = new WebRequest(getApplicationContext(), "http://maps.google.com/maps/api/geocode/json?latlng="+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+"&output=json&sensor=false", this, true);
                request.execute();
                appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                //txt_address.setText(Constant.UNSELECTED_LOCATION);
                }
            else {
                //txt_address.setText(Constant.SELECTED_LOCATION);
                appSession.setUserLocation(Constant.SELECTED_LOCATION);
            }
        }
        else{
            longitude ="";
            longitude = "";
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
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    private void loadPermissions(String perm,int requestCode) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm}, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                }
                else{
                    // no granted
                }
                return;
            }

        }

    }

    @Override
    public void getWebResult(JSONObject result) {
       Log.e("Result+++++",result.toString());
        if (!String.valueOf(result.toString()).equals(null)) {
            Log.e("result=" , result.toString());
            try {
                JSONArray Result = result.getJSONArray("results");
                JSONArray  address_components = Result.getJSONObject(0).getJSONArray("address_components");
                String CityName ="";
                String StateName = "";
                for (int i=0 ;i <address_components.length(); i++){
                    JSONArray Types = address_components.getJSONObject(i).getJSONArray("types");
                    for (int j = 0;j <Types.length();j++ ){
                        if (Types.getString(j).equals("sublocality_level_1") ) {
                            CityName = address_components.getJSONObject(i).getString("long_name");
                            if (CityName.equals("Al Souq Al Kabeer"))
                                CityName = "Meena Bazaar";
                        }
                        else  if (Types.getString(j).equals("administrative_area_level_1")){
                            StateName = address_components.getJSONObject(i).getString("long_name");
                        }
                    }
                }
                Log.e("CityName ",CityName);
                Log.e("StateName ",StateName);
                Constant.UNSELECTED_LOCATION =CityName+", "+StateName;
                txt_address .setText(CityName+", "+StateName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else Log.e("Web services response ", "Null");
    }
    public static Home getInstance(){
        return instance;
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
          //  indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
               //     .append(ListUtils.getSize(imageIdList)));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        mPager.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start auto scroll when onResume
        checkPlayServices();
        mPager.startAutoScroll();
    }
}

