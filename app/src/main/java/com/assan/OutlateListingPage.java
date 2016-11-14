package com.assan;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DAO.OutletListDAO;
import DTO.RecentOutlet;
import ImageLoding.Loader;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.CommonUtils;
import DTO.OutletListModel;
import Utils.Constant;
import Utils.Prefrence;
import adapter.OutletListAdapter;
import adapter.OutletsListAdapterHlv;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;


/**
 * Created by Digi-T25 on 5/13/2016.
 */
public class OutlateListingPage extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account, back_btn, search_catagri,rl_filter;
    ImageView btn_home_acive, errow, iv_back, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    OutletListModel OutletListDTO = null;
    ProgressDialog mProgressDialog;
    AppSession appSession;
    Loader loader;
    public String latitude = "", longitude = "";
    Context context;
    boolean checklist = true;
    int cnt = 1;
    public TextView txt_name, txt_cat_name, txt_sub_name,sponsored,filterlbl;
    public String sub_cat_id = "", catid = "", keyword = "", cat_name = "", filter = "";
    String page = "1",pageno="1";
    HListView hlv_sposensered;
    ListView listView;
    int count  = 0;
    public static OutlateListingPage instance;
    OutletListAdapter adapter;
    List<OutletListModel> listdata = new ArrayList<>();
    List<OutletListModel> listdata2 = new ArrayList<>();
    OutletsListAdapterHlv adapter2;
    String success, main_cg, sub_cg, catgori_name;
    int flag;
    public RecentOutlet recentOutlet, recentOutlet1;
    Prefrence sharpref;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    Utils.LightText txt_home,txt_category;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_listing_page);
        appSession = new AppSession(getApplicationContext());

        sharpref = new Prefrence();
        Home.CATID = catid;

        if (checkPlayServices()) {
            buildGoogleApiClient();
            // displayLocation();
            }

        instance = this;
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
        search_catagri = (LinearLayout) findViewById(R.id.search_catagori);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        rl_filter = (LinearLayout) findViewById(R.id.rl_filter);
        /*txt_tryagain = (TextView) findViewById(R.id.text_tryagain);
        txt_address=(TextView)findViewById(R.id.txtaddress);
        ll_left = (LinearLayout) findViewById(R.id.ll_left);*/
        //--------------------------------------------------------button----------------------

        btn_home = (ImageView) findViewById(R.id.btn_home);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        errow = (ImageView) findViewById(R.id.arrow);
        btn_home_acive = (ImageView) findViewById(R.id.btn_home_active);
        btn_recent = (ImageView) findViewById(R.id.btn_recent);
        btn_recent_active = (ImageView) findViewById(R.id.btn_recent_active);
        btn_categorie = (ImageView) findViewById(R.id.btn_cat);
        btn_categorie_active = (ImageView) findViewById(R.id.btn_cat_active);
        btn_bookmak = (ImageView) findViewById(R.id.btn_bookmark);
        btn_bookmark_active = (ImageView) findViewById(R.id.btn_bookmark_active);
        btn_account = (ImageView) findViewById(R.id.btn_account);
        btn_account_active = (ImageView) findViewById(R.id.btn_account_active);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_cat_name = (TextView) findViewById(R.id.txt_cat_name);
        txt_sub_name = (TextView) findViewById(R.id.txt_sub_name);
        sponsored = (TextView) findViewById(R.id.sponsored);
        filterlbl = (TextView) findViewById(R.id.filterlbl);
        // btn_login = (Button) findViewById(R.id.btn_login);
        txt_home = (Utils.LightText) findViewById(R.id.txt_home);
        txt_category = (Utils.LightText) findViewById(R.id.txt_category);
        //---------------------------------textview-----------------------------------------

        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);

        txt_name.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        rl_filter.setOnClickListener(this);
        search_catagri.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            catid = bundle.getString("catid");
            sub_cg = bundle.getString("subcatName");
            main_cg = bundle.getString("maincat");
            catgori_name = bundle.getString("catName");

            flag = bundle.getInt("flag");
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitude");
            //sub_cat_id = bundle.getString("subcatid");
            int subcat_flag = bundle.getInt("subcatgiri_flag");
            int home_header_adap_flag = bundle.getInt("home_header_adap_flag");

            int search_location_flag = bundle.getInt("search_location_flag");
            if (search_location_flag == 14) {
                errow.setVisibility(View.GONE);

                sub_cat_id = "";
                catid = "";
                latitude = "";
                longitude = "";

                txt_sub_name.setText(sub_cg);
            }
            if (subcat_flag == 8) {
                sub_cat_id = bundle.getString("subcatid");
                catgori_name = bundle.getString("catName");
            }

            if (home_header_adap_flag == 10) {
                sub_cat_id = bundle.getString("subcatid");
                catid = bundle.getString("catid");
                latitude = bundle.getString("latitude");
                longitude = bundle.getString("longitude");
                sub_cg = bundle.getString("subcatName");

                txt_sub_name.setText(sub_cg);
                errow.setVisibility(View.GONE);
                Log.e("subcat id ", "d" + sub_cg);
            }
        }
        btn_home_acive.setVisibility(View.VISIBLE);
        btn_home.setVisibility(View.GONE);

        if (Constant.SELECTED_LOCATION.equals("")) {
            txt_name.setText(Constant.UNSELECTED_LOCATION);
        } else {
            txt_name.setText(Constant.SELECTED_LOCATION);
        }

        if (flag == 7) {
            errow.setVisibility(View.GONE);
            txt_sub_name.setText(catgori_name);
            txt_cat_name.setVisibility(View.GONE);
        } else {
            txt_cat_name.setText(catgori_name);
            txt_sub_name.setText(sub_cg);
        }

        intit();
        product_list("1");
        txt_name.setTypeface(setfont("OpenSans-Regular.ttf"));
        sponsored.setTypeface(setfont("OpenSans-Bold.ttf"));
        filterlbl.setTypeface(setfont("OpenSans-Bold.ttf"));
    }

    public void intit() {
        listView = (ListView) findViewById(R.id.lv_outlet_list);
        hlv_sposensered = (HListView) findViewById(R.id.hlv_sposensered);

        if (String.valueOf(sharpref.getUserID(OutlateListingPage.this)).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        }

        if (Constant.TabLayout_Home_Status == true){
            btn_home_acive.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.GONE);
            txt_home.setTextColor(getResources().getColor(R.color.lightred));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
        else if(Constant.TabLayout_Category_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.GONE);
            txt_category.setTextColor(getResources().getColor(R.color.lightred));

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

            Intent intenthome = new Intent(OutlateListingPage.this, Home.class);
            startActivity(intenthome);
            finish();

        } else if (v.getId() == R.id.ll_recent) {
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Category_Status = false;
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

            Intent intent = new Intent(OutlateListingPage.this, MoreScreen.class);
            startActivity(intent);

        } else if (v.getId() == R.id.ll_categorie) {
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Category_Status = true;
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

            Intent intcategory = new Intent(OutlateListingPage.this, CategoryActiviyt.class);
            startActivity(intcategory);
            finish();


        } else if (v.getId() == R.id.search_catagori) {
            Constant.Check_current_status = false;
            Intent intent = new Intent(OutlateListingPage.this, SearchLocation.class);
            intent.putExtra("KEY_SEARCH", 2);
            intent.putExtra("CAT_ARRAY", Constant.CatArray);
            startActivity(intent);
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

            if (sharpref.getUserID(OutlateListingPage.this).equals("null")) {

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(OutlateListingPage.this, LoginActivity.class);
                        startActivity(intenthome);
                        finish();
                        dialog.dismiss();
                    }
                });
                dialog.show();

            } else {
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Bookmark_Status = true;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= false;

                Intent intenthome = new Intent(OutlateListingPage.this, BookmarkActivity.class);
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

                if (sharpref.getUserID(OutlateListingPage.this).equals("null")) {

                    Intent intent = new Intent(OutlateListingPage.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent = new Intent(OutlateListingPage.this, MyAccount.class);
                    startActivity(intent);
                    finish();
                }

           // }
        } else if (v.getId() == R.id.back_btn) {
            finish();
        }
        else if (v.getId() == R.id.rl_filter) {

            filterDialogBox();

        } else if (v.getId() == R.id.txt_name) {
            Constant.Check_current_status = true;
            Intent intent = new Intent(OutlateListingPage.this, SearchLocation.class);
            intent.putExtra("outletpage_screen_flag", 10);
            intent.putExtra("KEY_SEARCH", 1);
            startActivity(intent);
            // finish();
        }
    }

    private void product_list(String page) {
        if (Constant.SELECTED_LOCATION.equals("")) {
            Log.e("SELECTED", Constant.SELECTED_LOCATION + "unselected " + page);
            OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.UNSELECTED_LOCATION, page, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, filter);
        } else {
            Log.e("SELECTED", Constant.SELECTED_LOCATION + "unselected " + page);
            OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.SELECTED_LOCATION, page, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, filter);
        }

        // OutletList(appSession.getUserLocation(),appSession.getUserId(),catid,sub_cat_id,page,filter,keyword,cat_name);
    }

    public void OutletList(final String user_id, String cat_id, final String sub_cat_id, final String location, final String page, String keyword, String cat_name, String latitude, String longitude, final String filter) {

        class OutletListAsyntask extends AsyncTask<String, Void, OutletListModel> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();

            @Override
            public void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(OutlateListingPage.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            public OutletListModel doInBackground(String... params) {
                Log.e("outletpage response", "json1" + filter);
                /* dto = new CustomerDAO((Context) getActivity()).ProductListByMasterCategory("" + page, MktUsrId, categoryId, subCategoryId, searchTxt, "");*/
                OutletListDAO userDAO = new OutletListDAO(getApplicationContext());
                OutletListDTO = userDAO.getoutletDTO(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]);
                return OutletListDTO;
            }

            @Override
            public void onPostExecute(final OutletListModel s) {
                super.onPostExecute(s);
                recentOutlet1 = new RecentOutlet();

                try {
                    if (!String.valueOf(s).equals("null")) {

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy '@'HH:mm ss");
                        final String date = df.format(Calendar.getInstance().getTime());
                        //   Toast.makeText(getApplicationContext(),"positin "+date,Toast.LENGTH_SHORT).show();
                        success = OutletListDTO.getSuccess();
                        Log.e("success", "result" + s.getAdvertiseDTOs().size());
                        if (success.equals("1")) {
                            if (checklist) {
                                Log.e("success11", "result" + listdata.size());
                                listdata = s.getAdvertiseDTOs();
                            }else {
                                listdata.addAll(s.getAdvertiseDTOs());
                              //  s.getAdvertiseDTOs().clear();
                                Log.e("success2222", "result" + s.getAdvertiseDTOs().size());

                            }

                            Log.e("success22", "result" + listdata.size());
                            loader = new Loader(OutlateListingPage.this);
                            adapter = new OutletListAdapter(OutlateListingPage.this, listdata);
                            listView.setAdapter(adapter);
                            listView.setSelection(count);

                            listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                                 /*   recentOutlet = new RecentOutlet();
                                    recentOutlet.setRecent_id(s.getAdvertiseDTOs().get(position).getOutlet_id());
                                    recentOutlet.setRecent_name(s.getAdvertiseDTOs().get(position).getOutlet_name());
                                    recentOutlet.setRecentt_type(s.getAdvertiseDTOs().get(position).getOutlet_type());
                                    recentOutlet.setRecent_time(s.getAdvertiseDTOs().get(position).getOutlet_time());
                                    recentOutlet.setRecent_rating(s.getAdvertiseDTOs().get(position).getOutlet_rating());
                                    recentOutlet.setRecent_location(s.getAdvertiseDTOs().get(position).getOutlet_location());
                                    recentOutlet.setImage(s.getAdvertiseDTOs().get(position).getImage());
                                    recentOutlet.setCurrentTime(date);
                                    recentOutlet.setRecent_lat(s.getAdvertiseDTOs().get(position).getOutlet_lat());
                                    recentOutlet.setRecent_long(s.getAdvertiseDTOs().get(position).getOutlet_long());
                                    recentOutlet.setRecent_phonNO(s.getAdvertiseDTOs().get(position).getPhone_no());
                                    recentOutlet.setData("");

                                    sharpref.addRecentData(OutlateListingPage.this, recentOutlet);
                                    sharpref.saveRlist(OutlateListingPage.this, "DATA");*/

                                    Intent iout = new Intent(getApplicationContext(), OutletDetails.class);
                                    Bundle b = new Bundle();
                                    b.putString("id", s.getAdvertiseDTOs().get(position).getOutlet_id());
                                    b.putString("KEY2", location);
                                    iout.putExtras(b);
                                    startActivity(iout);
                                }
                            });

                            adapter2 = new OutletsListAdapterHlv(OutlateListingPage.this, s.getheaderImg());
                            hlv_sposensered.setAdapter(adapter2);

                            //  Log.e("second adpter",adapter2.toString());
                            adapter.notifyDataSetChanged();
                            mProgressDialog.dismiss();
                            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view,
                                                                 int scrollState) { // TODO Auto-generated method stub
                                    int threshold = 1;
                                     count = listView.getCount();
                                    //listView.setSelection(count);
                                    Log.e("listcount ","D "+ count +"dd "+s.getAdvertiseDTOs().size());
                                    if (scrollState == SCROLL_STATE_IDLE) {
                                        if (listView.getLastVisiblePosition() >=count-1) {
                                            if (s.getAdvertiseDTOs().size() == 20) {
                                                int result = Integer.parseInt(pageno);
                                                result = result + 1;
                                                pageno = "" + result;
                                                Log.e("page size ", pageno);
                                                product_list(pageno);
                                                checklist = false;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem,
                                                     int visibleItemCount, int totalItemCount) {



                                    // TODO Auto-generated method stub

                                }

                            });


                        } else if (success.equals("0")) {
                            CommonUtils.showToast(getApplicationContext(), "No record found ...");
                            mProgressDialog.dismiss();
                        }

                    } else {
                        CommonUtils.showToast(getApplicationContext(), "Server Exceptions...");
                        mProgressDialog.dismiss();
                    }
                }
                catch (Exception e){
                    Log.e("OutletlistPage ","Error : "+e.getMessage());
                }
            }

        }
/*String location, String user_id, String cat_id,String sub_cat_id,String page,String keyword,String cat_name,String filter*/
        OutletListAsyntask ru = new OutletListAsyntask();
        ru.execute(user_id, cat_id, sub_cat_id, location, page, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, filter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public static OutlateListingPage getInstance() {
        return instance;
    }

    private void displayLocation() {

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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            //lblLocation.setText(latitude + ", " + longitude);
            Constant.LATITUDE = String.valueOf(mLastLocation.getLatitude());
            Constant.LONGITUDE = String.valueOf(mLastLocation.getLongitude());
            Log.e("long lat", " " + latitude+"long "+longitude);
            //Toast.makeText(getApplicationContext(),"LATITUDE "+latitude+"longitude "+longitude,Toast.LENGTH_SHORT).show();

        } else {
            Constant.LATITUDE = "";
            Constant.LONGITUDE = "";
            Toast.makeText(getApplicationContext(),"(Couldn't get the location. Make sure location is enabled on the device)",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
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

    public void filterDialogBox(){

        final Dialog dialog = new Dialog(OutlateListingPage.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filter_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView nearBy = (TextView)dialog.findViewById(R.id.nearby);
        TextView ratingBy = (TextView)dialog.findViewById(R.id.ratingby);
        TextView clearAll = (TextView)dialog.findViewById(R.id.clearall);
        nearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.SELECTED_LOCATION.equals("")) {
                    OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.UNSELECTED_LOCATION, pageno, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, "nearby");
                } else {
                    OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.SELECTED_LOCATION, pageno, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, "nearby");
                }
                dialog.dismiss();
            }
        });
        ratingBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.SELECTED_LOCATION.equals("")) {
                    OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.UNSELECTED_LOCATION, pageno, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, "rating");
                } else {
                    Log.e("rating", "");
                    OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.SELECTED_LOCATION, pageno, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, "rating");
                }
                dialog.dismiss();
            }
        });
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.SELECTED_LOCATION.equals("")) {
                    Log.e("SELECTED", "");
                    OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.UNSELECTED_LOCATION, pageno, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, "");
                } else {
                    OutletList(appSession.getUserId(), catid, sub_cat_id, Constant.SELECTED_LOCATION, pageno, keyword, cat_name, Constant.LATITUDE, Constant.LONGITUDE, "");
                }
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }

}
