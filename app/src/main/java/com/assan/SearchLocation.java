package com.assan;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DAO.SearchLocationListDAO;
import DTO.OutletListModel;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;
import Utils.WebApiResult;
import Utils.WebRequest;
import adapter.ByDefaultSearchAdapter;
import adapter.LocationSearchAdapter;


/**
 * Created by Digi-T25 on 5/20/2016.
 */
public class SearchLocation extends Activity implements View.OnClickListener, WebApiResult, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    TextView location_cancle,tv_serch_dummy;
    EditText et_search;
    Context context;
    private GoogleMap mMap;
    public static SearchLocation instance;
    Location mLastLocation = null;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int REQUEST_FINE_LOCATION = 0;
    Boolean  TextChange = false;
    AppSession appSession;
    OutletListModel OutletListDTO=null;
    HashMap<String ,String> data = new HashMap<String ,String>();
    String keyword,catName = "";
    RelativeLayout ll,rl;
    ArrayList<String> resultArray= null;
    Prefrence prefrence;
    int search,cat = 0,home_screen_flag=0,maintince_screen_flag=0,outletpage_screen_flag=0,searchcategories_flag=0,category_screen_flag=0;
    SearchLocationListDAO userDAO = null;
    ListView list_view;
    LocationSearchAdapter adapter;
    ByDefaultSearchAdapter adapter_default;
    List<OutletListModel> arraylist;
    ArrayList<String> list = new ArrayList<>();
    List<OutletListModel> arraylist2;
    ProgressDialog mProgressDialog;
    String latitude= "" ,longitude= "";
    boolean check = true;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_location);
         Intent mIntent = getIntent();
         buildGoogleApiClient();
         instance = this;
         loadPermissions(
                 Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_FINE_LOCATION);
         if (mIntent != null) {
             search = mIntent.getIntExtra("KEY_SEARCH", 0);
             home_screen_flag = mIntent.getIntExtra("home_screen_flag", 0);
             maintince_screen_flag = mIntent.getIntExtra("maintince_screen_flag", 0);
             outletpage_screen_flag = mIntent.getIntExtra("outletpage_screen_flag", 0);
             searchcategories_flag = mIntent.getIntExtra("searchcategories_flag", 0);
             category_screen_flag = mIntent.getIntExtra("category_screen_flag", 0);
             latitude = mIntent.getStringExtra("latitude");
             longitude = mIntent.getStringExtra("longitude");
         }
         appSession = new AppSession(getApplicationContext());
         tv_serch_dummy = (TextView) findViewById(R.id.tv_serch_dummy);
         ll = (RelativeLayout) findViewById(R.id.ll);
         rl = (RelativeLayout) findViewById(R.id.rl);
         et_search = (EditText) findViewById(R.id.et_search);
         list_view = (ListView) findViewById(R.id.list_view);

 if (search==1) {
     SharedPreferences pref = getSharedPreferences("PREF_NAME",0);
     final Set<String> set = pref.getStringSet("Search_val",null);

     Log.e("CHECK PREF++++",String.valueOf(set));
     if (!String.valueOf(set).equals("null")){
         list.add(0,"Auto-detect my location");
         list.addAll(set);
         adapter_default =new ByDefaultSearchAdapter(this,list);
         list_view.setAdapter(adapter_default);
     }
         list_view.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {

             Home home2 = Home.getInstance();
             MaintenanceActivity maintence = MaintenanceActivity.getInstance();
             OutlateListingPage outlist = OutlateListingPage.getInstance();
             CategoryActiviyt categoryActiviyt = CategoryActiviyt.getInstance();

             if (TextChange == true) {
                 Set<String> set2 = new HashSet<String>();
                 if (!String.valueOf(set).equals("null")) {
                     set2 = set;
                 }
                 if (arraylist.get(position).equals(1)) {
                     Toast.makeText(getApplicationContext(), "First", Toast.LENGTH_SHORT).show();
                 }
                 set2.add(arraylist.get(position).getOutlet_name());
                 SharedPreferences pref = getSharedPreferences("PREF_NAME", 0);
                 SharedPreferences.Editor editor = pref.edit();
                 editor.putStringSet("Search_val", set2);
                 editor.commit();
                 Constant.SELECTED_LOCATION = arraylist.get(position).getOutlet_name();

             }
             else
             {
                 Constant.SELECTED_LOCATION = list.get(position);
                 if(list.get(position).equals("Auto-detect my location")){
                     Toast.makeText(getApplicationContext(),"First",Toast.LENGTH_SHORT).show();
                     WebRequest request = new WebRequest(SearchLocation.this, "http://maps.google.com/maps/api/geocode/json?latlng="+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+"&output=json&sensor=false", SearchLocation.this, true);
                     request.execute();
                     appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                     Constant.SELECTED_LOCATION = Constant.UNSELECTED_LOCATION;
                 }
//                 Constant.SELECTED_LOCATION = arraylist.get(position).getOutlet_name();
             }
             if (home_screen_flag == 8) {
                 Intent intent = new Intent(getApplicationContext(), Home.class);
                 startActivity(intent);
                 finish();
             }
             if (maintince_screen_flag == 9){

                 if (TextChange == true){
                     home2.txt_address.setText(arraylist.get(position).getOutlet_name());
                     maintence.loc_sub.setText(arraylist.get(position).getOutlet_name());
                     appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                     Constant.SELECTED_LOCATION = Constant.UNSELECTED_LOCATION;
                 }
                 else {
                     Constant.SELECTED_LOCATION = list.get(position);

                     if(list.get(position).equals("Auto-detect my location")){
                         Toast.makeText(getApplicationContext(),"First",Toast.LENGTH_SHORT).show();
                         WebRequest request = new WebRequest(SearchLocation.this, "http://maps.google.com/maps/api/geocode/json?latlng="+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+"&output=json&sensor=false", SearchLocation.this, true);
                         request.execute();
                         appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                         Constant.SELECTED_LOCATION = Constant.UNSELECTED_LOCATION;
                         }
                     maintence.loc_sub.setText(Constant.SELECTED_LOCATION);
                     home2.txt_address.setText(Constant.SELECTED_LOCATION);
                 }
                 finish();

             }if (outletpage_screen_flag == 10){
                  outlist.listView.setAdapter(null);
                 if (TextChange == true){
                     outlist.txt_name.setText(arraylist.get(position).getOutlet_name());
                     home2.txt_address.setText(arraylist.get(position).getOutlet_name());
                     maintence.loc_sub.setText(arraylist.get(position).getOutlet_name());
                     appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                     outlist.OutletList(appSession.getUserId(),outlist.catid,outlist.sub_cat_id,arraylist.get(position).getOutlet_name(),outlist.page,outlist.filter,outlist.keyword,outlist.cat_name,outlist.latitude,outlist.longitude);
                     outlist.txt_name.setText(Constant.SELECTED_LOCATION);
                     Constant.SELECTED_LOCATION = Constant.UNSELECTED_LOCATION;
                 }
                 else {
                     Constant.SELECTED_LOCATION = list.get(position);

                     if(list.get(position).equals("Auto-detect my location")){
                         Toast.makeText(getApplicationContext(),"First",Toast.LENGTH_SHORT).show();
                         WebRequest request = new WebRequest(SearchLocation.this, "http://maps.google.com/maps/api/geocode/json?latlng="+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+"&output=json&sensor=false", SearchLocation.this, true);
                         request.execute();
                         appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                         Constant.SELECTED_LOCATION = Constant.UNSELECTED_LOCATION;
                       }
                     outlist.OutletList(appSession.getUserId(),outlist.catid,outlist.sub_cat_id,Constant.SELECTED_LOCATION,outlist.page,outlist.filter,outlist.keyword,outlist.cat_name,outlist.latitude,outlist.longitude);
                     outlist.txt_name.setText(Constant.SELECTED_LOCATION);
                     home2.txt_address.setText(Constant.SELECTED_LOCATION);
                     maintence.loc_sub.setText(Constant.SELECTED_LOCATION);
                 }

                 finish();
             }
             if (searchcategories_flag == 11){
                     Intent intent = new Intent(SearchLocation.this,OutlateListingPage.class);
                     intent.putExtra("search_location_flag",14);
                     startActivity(intent);
                     finish();
             }
             if (category_screen_flag == 12){

                 if (TextChange == true){
                     categoryActiviyt.location_name.setText(arraylist.get(position).getOutlet_name());
                     appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                     Constant.SELECTED_LOCATION = Constant.UNSELECTED_LOCATION;
                 }
                 else {
                     Constant.SELECTED_LOCATION = list.get(position);
                     if(list.get(position).equals("Auto-detect my location")){
                         Toast.makeText(getApplicationContext(),"First",Toast.LENGTH_SHORT).show();
                         WebRequest request = new WebRequest(SearchLocation.this, "http://maps.google.com/maps/api/geocode/json?latlng="+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+"&output=json&sensor=false", SearchLocation.this, true);
                         request.execute();
                         appSession.setUserLocation(Constant.UNSELECTED_LOCATION);
                         Constant.SELECTED_LOCATION = Constant.UNSELECTED_LOCATION;

                     }
                     categoryActiviyt.location_name.setText(Constant.SELECTED_LOCATION);
                     }

                 finish();
             }
//             Constant.SELECTED_LOCATION = arraylist.get(position).getOutlet_name();

         }
     });
 }
 else {
     tv_serch_dummy.setText("Search store by name");
     resultArray = mIntent.getStringArrayListExtra("CAT_ARRAY");
     Log.e("CAT+ARRAY",resultArray.toString());
     adapter_default =new ByDefaultSearchAdapter(this,resultArray);
     list_view.setAdapter(adapter_default);

     list_view.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {

             if (check){
                 //Toast.makeText(SearchLocation.this, "WAit", Toast.LENGTH_SHORT).show();
                 catName = resultArray.get(position);
                 Intent intent = new Intent(SearchLocation.this,SearchCategories.class);
                 intent.putExtra("cat_name",catName);
                 intent.putExtra("subcatid","");
                 intent.putExtra("latitude",Constant.LATITUDE);
                 intent.putExtra("longitude",Constant.LONGITUDE);
                 Log.e("catname ","d ");
                 // Home.CATNAME = resultArray.get(position);
                 startActivity(intent);
                 finish();
             }
             else {
                 //Toast.makeText(SearchLocation.this, "WAit", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(SearchLocation.this,SearchCategories.class);
                 intent.putExtra("cat_name","");
                 intent.putExtra("keyword",arraylist2.get(position).getOutlet_name());
                 intent.putExtra("latitude",Constant.LATITUDE);
                 intent.putExtra("longitude",Constant.LONGITUDE);
                 // Home.CATNAME = resultArray.get(position);
                 Log.e("catname ","deee "+arraylist2.get(position).getOutlet_name());
                 startActivity(intent);
                 finish();
             }
         }
     });
         }
         location_cancle = (TextView) findViewById(R.id.location_cancle);
         location_cancle.setOnClickListener(this);
         //ll.setOnClickListener(this);
         et_search.setOnClickListener(this);
         //rl.setOnClickListener(this);
       //  tv_serch_dummy.setOnClickListener(this);
     et_search.addTextChangedListener(new TextWatcher() {
     @Override
     public void beforeTextChanged(CharSequence s, int start, int count, int after) {

     }

     @Override
     public void onTextChanged(CharSequence s, int start, int before, int count) {

          if (s.length()>=2){
              TextChange = true;
              if (search==1)
              LocationList(s.toString());
              else
              if (Constant.SELECTED_LOCATION.equals("")) {
                  OutletSearch(Constant.UNSELECTED_LOCATION, s.toString());
              }
              else {
                  OutletSearch(Constant.SELECTED_LOCATION, s.toString());
              }
          }
     }

     @Override
     public void afterTextChanged(Editable s) {

     }
 });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location_cancle:
                et_search.setText("");
                location_cancle.setTextColor(getResources().getColor(R.color.gray_dark));
                et_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                tv_serch_dummy.setVisibility(View.VISIBLE);
                et_search.setHint("");
                Log.e("VALUE_CAN",String.valueOf(et_search.getText().toString()));
                if ( et_search.getText().toString().equals("")){
                    finish();
                   // Toast.makeText(SearchLocation.this, "Cancle", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll:
                tv_serch_dummy.setVisibility(View.GONE);
                location_cancle.setTextColor(getResources().getColor(R.color.dialog_red));
                et_search.setHint("Search location by name");
                et_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search2, 0, 0, 0);
                et_search.setCompoundDrawablePadding(4);
                break;
            case R.id.rl:
                rl.setVisibility(View.GONE);
                location_cancle.setTextColor(getResources().getColor(R.color.dialog_red));
                et_search.setHint("Search location by name");
                et_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search2,0, 0, 0);
                et_search.setCompoundDrawablePadding(4);
                break;
            case R.id.tv_serch_dummy:
                tv_serch_dummy.setVisibility(View.GONE);
                location_cancle.setTextColor(getResources().getColor(R.color.dialog_red));
                et_search.setHint("Search location by name");
                et_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search2,0,0,0);
                et_search.setCompoundDrawablePadding(4);
                break;
            case R.id.et_search:
                //if (tv_serch_dummy.VISIBLE == View.VISIBLE){
                    tv_serch_dummy.setVisibility(View.GONE);
                    location_cancle.setTextColor(getResources().getColor(R.color.dialog_red));
                    et_search.setHint("");
                    //et_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow,0,0,0);
                    et_search.setCompoundDrawablePadding(4);
                    rl.setVisibility(View.GONE);
               // }

        }

    }

  // Async For Location Search
    private void LocationList(final String Location_string) {

        class OutletListAsyntask extends AsyncTask<String, Void, List<OutletListModel>> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(SearchLocation.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected List<OutletListModel> doInBackground(String... params) {
                /* dto = new CustomerDAO((Context) getActivity()).ProductListByMasterCategory("" + page, MktUsrId, categoryId, subCategoryId, searchTxt, "");*/
                userDAO = new SearchLocationListDAO(getApplicationContext());
               // OutletListDTO =userDAO.getoutletDTO(params[0]);
                arraylist = userDAO.getoutletDTO(params[0]);
                return arraylist;
            }

            @Override
            protected void onPostExecute(List<OutletListModel> s) {
                super.onPostExecute(s);
                String success="";
                    if(s !=null)
                {

                    for (int i=0;i<s.size(); i++){
                        success = s.get(i).getSuccess();
                    }
                   // Log.e("VALUE","S"+s.toString());

                    if (success.equals("1")){
                        adapter = new LocationSearchAdapter(getApplicationContext(),arraylist);
                        list_view.setAdapter(adapter);
                       // Log.e("location","data"+list_view.toString());
                        mProgressDialog.dismiss();
                    }else if (success.equals("0")){
                        CommonUtils.showToast(getApplicationContext(), "No record found ...");
                        mProgressDialog.dismiss();
                    }

                }else {
                    CommonUtils.showToast(getApplicationContext(),"Server Exceptions...");
                    mProgressDialog.dismiss();
                }
            }
        }

        OutletListAsyntask ru = new OutletListAsyntask();
        ru.execute(Location_string);
    }
// Async for Outlet Search
 private void OutletSearch(String Location, String Keyword ) {

    class OutletListAsyntask extends AsyncTask<String, Void, List<OutletListModel>> {
        ProgressDialog loading;
        RemotRequest ruc = new RemotRequest();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            check = false;
            mProgressDialog = new ProgressDialog(SearchLocation.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait..");
            // Set progressdialog message
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected List<OutletListModel> doInBackground(String... params) {
                /* dto = new CustomerDAO((Context) getActivity()).ProductListByMasterCategory("" + page, MktUsrId, categoryId, subCategoryId, searchTxt, "");*/
            userDAO = new SearchLocationListDAO(getApplicationContext());
            // OutletListDTO =userDAO.getoutletDTO(params[0]);
            arraylist2 = userDAO.getoutletDTO(params[0],params[1]);
            return arraylist2;
        }

        @Override
        protected void onPostExecute(List<OutletListModel> s) {
            super.onPostExecute(s);
            String success="";
            if(s !=null)
            {

                for (int i=0;i<s.size(); i++){
                    success = s.get(i).getSuccess();
//                    data.put("Outlet_Id",s.get(i).getOutlet_id());
//                    data.put("Outlet_Name",s.get(i).getOutlet_name());
                    data.put("anil ",s.get(i).getOutlet_id());
                    keyword = s.get(i).getOutlet_name();

                }
                Log.e("location size","data"+s.size());
                keyword = s.get(0).getOutlet_name();


//                if (success.equals("1")){
                    adapter = new LocationSearchAdapter(getApplicationContext(),arraylist2);
                    list_view.setAdapter(adapter);
                    // Log.e("location","data"+list_view.toString());
                    mProgressDialog.dismiss();
//                }else if (success.equals("0")){
//                    CommonUtils.showToast(getApplicationContext(), "No record found ...");
//                    mProgressDialog.dismiss();
//                }

            }else {
                CommonUtils.showToast(getApplicationContext(),"Server Exceptions...");
                mProgressDialog.dismiss();
            }
        }
    }

    OutletListAsyntask ru = new OutletListAsyntask();
    ru.execute( Location, Keyword);
}
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        // mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        }
        else{


            longitude ="";
            longitude = "";
            /*txt_address.setText("Dubai");
            appSession.setUserLocation("Dubai");*/
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // LayoutInflater inflater = this.getLayoutInflater();
            alertDialogBuilder.setTitle(Html.fromHtml("<font color='#000000'>Please first on your Location</font>"));
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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
        if (result != null) {
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
               /* txt_address .setText(CityName+", "+StateName);*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else Log.e("Web services response ", "Null");
    }

}
