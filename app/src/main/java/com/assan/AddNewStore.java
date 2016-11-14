package com.assan;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DAO.CompanyListDAO;
import DTO.CityList;
import DTO.CompanyListDTO;
import DTO.Country;
import DTO.LandMarkDTO;
import ImageLoding.Loader;
import UtilApi.RemotRequest;
import Utils.AlertDialogManager;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.Constant;
import Utils.Prefrence;
import Utils.RequestHandler;
import adapter.AddStoreCategory;
import adapter.CityListAdapter;
import adapter.CustomSpinnerAdapter;
import adapter.LandMarkAdapter;


public class AddNewStore extends AppCompatActivity implements View.OnClickListener, it.sephiroth.android.library.widget.AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    String outletID="",title="",description,building_name,POBox,storeContactNo,landlineNo,landlineNo2,sun_thu_timing24="1",delivery="1",credit_card="1",sponsered="1",tags,banner="",street,message="", sun_thu_opentime ="",img_0="",img_1="",img_2="",img_3="",img_4="",
            sun_thu_closetime ="",brksun_thu_opentime ="", brksun_thu_closetime ="", fri_open_flag ="0",fri_time_24="1",fri_open_time="",fri_close_time="",fri_open_time_brk="",fri_close_time_brk="",sat_open_flag="0",sat_timings24="1",sat_open_time="",sat_close_time="",sat_open_time_brk="",sat_close_time_brk="";
    EditText edt_title,edt_description,edt_building_name,edt_postalcode,edt_Latitude,edt_longitude,edt_store_contactno,edt_landlineno,edt_landlineno2,edt_tags
            ,edt_street;
    TextView edt_subcategory,open_time_from_hour,open_time_from_mint,open_time_to_hour,open_time_to_mint,brk_time_from_hour,brk_time_from_mint,
            brk_time_to_hour,brk_time_to_mint,select_currency,companyname;
    TextView fri_no_open_time_from_hour,fri_no_open_time_from_mint,fri_no_open_time_to_hour,fri_no_open_time_to_mint, fri_no_brk_time_from_hour,fri_no_brk_time_from_mint,
            fri_no_brk_time_to_hour,fri_no_brk_time_to_mint,sat_no_open_time_from_hour,sat_no_open_time_from_mint,sat_no_open_time_to_hour,sat_no_open_time_to_mint,sat_no_brk_time_from_hour,
    sat_no_brk_time_from_mint,sat_no_brk_time_to_hour,sat_no_brk_time_to_mint;
    Spinner category_spinner,citySpinner,statespinner,landmarkspinner;
    boolean category_spin_check = false,opentime_from_check = false,open_tme_to_check = false,brk_time_from_check = false,brk_tme_to_check = false,
            fdy_no_opn_tme_hur = false,fdy_no_opn_tme_to_hur = false, fdy_brk_opn_tme_hur = false,fdy_brk_opn_tme_to_tohur = false,isSpinnerTouched = false,stateSpinnerCheck = false,landmarkcheck= false
            ,bannercheck , sat_opentime_from_hour = false, sat_open_tme_to_mint = false, sat_brk_time_from_hur = false, sat_brk_tme_to_mint = false,banner1 = true,banner2 = false
            ,banner3 = false,banner4 = false,banner5 = false,iscitytouch = true;
    Prefrence sharePre;
    String category_parent_id = "";
    List<String> subCatName = new ArrayList<>();
    List<String> SubCatIDList = new ArrayList<>();
    List<String> catagoryList = new ArrayList<>(),catagoryId = new ArrayList<>();
    List<String> checkCategory = new ArrayList<>(),subCatId_list = new ArrayList<>();
    StringBuilder listString = new StringBuilder(),subcat_data = new StringBuilder(),currency_accepted = new StringBuilder();
    List<String> currency_name=new ArrayList<String>();
    List<String> currency_id=new ArrayList<String>(),currency_selected_id=new ArrayList<String>(),currency_selected_name=new ArrayList<String>();
    Country country2;
    Button btn_save;
    Bitmap bm = null;
    CheckBox sun_thu_cb_yes, sun_thu_cb_no,fri_checkbox,fri_uncheckbox,fri_yes_checkbox,fri_no_uncheckbox,delevety_checkbox1,delevery_uncheck,
            credit_card_chek_yes,credit_card_chek_no,sponsered_checkbox_yes,sponsered_checkbox_no,sat_checkbox,sat_uncheckbox,sat_yes_checkbox1,sat_no_checkbox2;
    LinearLayout satthu_store_timing_layout,fri_yes_storetimingLayout,getyourlocation,sat_yes_storetiming;
    RelativeLayout fri_yes_layout,sat_yes_layout;
    final int SELECT_FILE = 1,REQUEST_CAMERA = 0,SELECT_GELLERY = 11;
    private int minute,hour,company_List_Adapter_flag=0;
    static final int TIME_DIALOG_ID = 1111;
    String city  = "",state = "",stateId="",landmark="",latitude,longitude;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    AlertDialogManager dialog;
    AppSession appSession;
    FloatingActionButton floatingActionButton;
    ImageView banner_img_view,upload_img_view1,upload_img_view2,upload_img_view3,upload_img_view4,upload_img_view5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_store);

        country2 = new Country();
        sharePre = new Prefrence();
        dialog = new AlertDialogManager(this);
        appSession = new AppSession(AddNewStore.this);

        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            company_List_Adapter_flag = bundle.getInt("company_List_Adapter_flag");
            outletID = bundle.getString("store_id");

            new GetSotreData().execute();
            Log.e("category parent","i00d "+category_parent_id);
        }

        SpinnerOntuchListener();
        OntuchSpinnerListener();
        CheckBoxclick();
        SetTime();

        if (checkPlayServices()) {
            getLatLong();
        }

        if (company_List_Adapter_flag == 7) {

            //category_spinner.setSelection("");
        }
        else {
            Log.e("category parent","i111d "+city+"state "+catagoryList);
            AddStoreCategory storeAdapter = new AddStoreCategory(AddNewStore.this, catagoryList, catagoryId,107);
            category_spinner.setAdapter(storeAdapter);
        }

        select_currency.setOnClickListener(this);
        getyourlocation.setOnClickListener(this);
        banner_img_view.setOnClickListener(this);
        upload_img_view1.setOnClickListener(this);
        upload_img_view2.setOnClickListener(this);
        upload_img_view3.setOnClickListener(this);
        upload_img_view4.setOnClickListener(this);
        upload_img_view5.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    public void init(){
        Log.e("category parent","test init ");
        edt_title = (EditText)findViewById(R.id.edt_title);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        edt_description = (EditText)findViewById(R.id.edt_description);
        edt_building_name = (EditText)findViewById(R.id.edt_building_name);
        edt_postalcode = (EditText)findViewById(R.id.edt_postalcode);
        edt_store_contactno = (EditText)findViewById(R.id.edt_store_contactno);
        edt_landlineno = (EditText)findViewById(R.id.edt_landlineno);
        edt_landlineno2 = (EditText)findViewById(R.id.edt_landlineno2);
        edt_tags = (EditText)findViewById(R.id.edt_tags);
        edt_street = (EditText)findViewById(R.id.edt_street);

        edt_subcategory = (TextView)findViewById(R.id.edt_subcategory);

        open_time_from_hour = (TextView)findViewById(R.id.open_time_from_hour);
        open_time_from_mint = (TextView)findViewById(R.id.open_time_from_mint);

        open_time_to_hour = (TextView)findViewById(R.id.open_time_to_hour);
        open_time_to_mint = (TextView)findViewById(R.id.open_time_to_mint);

        brk_time_from_hour = (TextView)findViewById(R.id.brk_time_from_hour);
        brk_time_from_mint = (TextView)findViewById(R.id.brk_time_from_mint);

        brk_time_to_hour = (TextView)findViewById(R.id.brk_time_to_hour);
        brk_time_to_mint = (TextView)findViewById(R.id.brk_time_to_mint);
        select_currency = (TextView)findViewById(R.id.currency_accepted_edt);

// FRIDAY TIME IF NO SELECTED
        citySpinner = (Spinner)findViewById(R.id.cityspinner);
        statespinner = (Spinner)findViewById(R.id.statespinner);
        landmarkspinner = (Spinner)findViewById(R.id.landmarkspinner);

        fri_no_open_time_from_hour = (TextView)findViewById(R.id.fri_no_open_time_from_hour);
        fri_no_open_time_from_mint = (TextView)findViewById(R.id.fri_no_open_time_from_mint);

        fri_no_open_time_to_hour = (TextView)findViewById(R.id.fri_no_open_time_to_hour);
        fri_no_open_time_to_mint = (TextView)findViewById(R.id.fri_no_open_time_to_mint);

        fri_no_brk_time_from_hour = (TextView)findViewById(R.id.fri_no_brk_time_from_hour);
        fri_no_brk_time_from_mint = (TextView)findViewById(R.id.fri_no_brk_time_from_mint);

        fri_no_brk_time_to_hour = (TextView)findViewById(R.id.fri_no_brk_time_to_hour);
        fri_no_brk_time_to_mint = (TextView)findViewById(R.id.fri_no_brk_time_to_mint);

        sat_no_open_time_from_hour = (TextView)findViewById(R.id.sat_no_open_time_from_hour);
        sat_no_open_time_from_mint = (TextView)findViewById(R.id.sat_no_open_time_from_mint);

        sat_no_open_time_to_hour = (TextView)findViewById(R.id.sat_no_open_time_to_hour);
        sat_no_open_time_to_mint = (TextView)findViewById(R.id.sat_no_open_time_to_mint);

        sat_no_brk_time_from_hour = (TextView)findViewById(R.id.sat_no_brk_time_from_hour);
        sat_no_brk_time_from_mint = (TextView)findViewById(R.id.sat_no_brk_time_from_mint);

        sat_no_brk_time_to_hour = (TextView)findViewById(R.id.sat_no_brk_time_to_hour);
        sat_no_brk_time_to_mint = (TextView)findViewById(R.id.sat_no_brk_time_to_mint);
        companyname = (TextView)findViewById(R.id.companyname);


        category_spinner = (Spinner) findViewById(R.id.category_spinner);
      //  company_spinner = (Spinner) findViewById(R.id.company_spinner);

        sun_thu_cb_yes = (CheckBox)findViewById(R.id.checkbox1);
        sun_thu_cb_no = (CheckBox)findViewById(R.id.checkbox2);

        satthu_store_timing_layout = (LinearLayout)findViewById(R.id.satthu_store_timing_layout);
        fri_yes_storetimingLayout = (LinearLayout)findViewById(R.id.fri_yes_storetiming);
        sat_yes_storetiming = (LinearLayout)findViewById(R.id.sat_yes_storetiming);
        getyourlocation = (LinearLayout)findViewById(R.id.getyourlocation);

        fri_yes_layout = (RelativeLayout)findViewById(R.id.fri_yes_layout);
        sat_yes_layout = (RelativeLayout)findViewById(R.id.sat_yes_layout);

        fri_checkbox = (CheckBox)findViewById(R.id.fricheckbox1);
        fri_uncheckbox = (CheckBox)findViewById(R.id.fricheckbox2);

        fri_yes_checkbox = (CheckBox)findViewById(R.id.fri_yes_checkbox1);
        fri_no_uncheckbox = (CheckBox)findViewById(R.id.fri_yes_checkbox2);

        sat_checkbox = (CheckBox)findViewById(R.id.satcheckbox1);
        sat_uncheckbox = (CheckBox)findViewById(R.id.satcheckbox2);

        sat_yes_checkbox1 = (CheckBox)findViewById(R.id.sat_yes_checkbox1);
        sat_no_checkbox2 = (CheckBox)findViewById(R.id.sat_no_checkbox2);

        delevety_checkbox1 = (CheckBox)findViewById(R.id.delevity_checkbox1);
        delevery_uncheck = (CheckBox)findViewById(R.id.delevity_checkbox2);

        //credit card check box======
        credit_card_chek_yes = (CheckBox)findViewById(R.id.c_card_checkbox1);
        credit_card_chek_no = (CheckBox)findViewById(R.id.c_card_checkbox2);

        sponsered_checkbox_yes = (CheckBox)findViewById(R.id.sponsered_checkbox1);
        sponsered_checkbox_no = (CheckBox)findViewById(R.id.sponsered_checkbox2);

        // Edittext=================
        edt_Latitude = (EditText)findViewById(R.id.edt_Latitude);
        edt_longitude = (EditText)findViewById(R.id.edt_longitude);

        //Imageview================
        banner_img_view = (ImageView)findViewById(R.id.banner_img_view);
        upload_img_view1 = (ImageView)findViewById(R.id.upload_img_view1);
        upload_img_view2 = (ImageView)findViewById(R.id.upload_img_view2);
        upload_img_view3 = (ImageView)findViewById(R.id.upload_img_view3);
        upload_img_view4 = (ImageView)findViewById(R.id.upload_img_view4);
        upload_img_view5 = (ImageView)findViewById(R.id.upload_img_view5);

        btn_save = (Button) findViewById(R.id.btn_save);

        companyname.setText(appSession.getUserNmae());
        currency_name.add("AED");
        currency_name.add("USD");
        currency_name.add("INR");
        currency_name.add("EURO");
        currency_name.add("GCC");
        currency_name.add("Other");

        currency_id.add("1");
        currency_id.add("2");
        currency_id.add("3");
        currency_id.add("4");
        currency_id.add("5");
        currency_id.add("6");

        catagoryList.add(0,"Select");
        catagoryList.addAll(Constant.CatArray);

        catagoryId.add(0,"0");
        catagoryId.addAll(Constant.CatId);
    }

    public void OntuchSpinnerListener(){
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (category_spin_check) {
                    Log.e("category ","D ");
                   // category_parent_id = Constant.CatId.get(position);
                    category_parent_id = catagoryId.get(position);
                    new GetSubCategory().execute();

                    edt_subcategory.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (iscitytouch)
                new CountryList().execute();
                isSpinnerTouched = true;
                return false;
            }
        });

        statespinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isSpinnerTouched)
                    new StateList().execute();

                isSpinnerTouched = false;
                stateSpinnerCheck = true;
                return false;
            }
        });
        landmarkspinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (landmarkcheck)
                    new LandMark().execute();

                landmarkspinner.setPrompt("Title");
                landmarkcheck= false;
                return false;
            }
        });

    }

    public void SetTime(){
        final Calendar c = Calendar.getInstance();
        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
        minute = c.get(Calendar.MINUTE);

        // set current time into output textview
        updateTime(hour, minute);

        open_time_from_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = true;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        open_time_from_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = true;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        open_time_to_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = true;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        open_time_to_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = true;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        brk_time_from_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = true;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        brk_time_from_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = true;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        brk_time_to_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = true;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        brk_time_to_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = true;

                fdy_no_opn_tme_hur =false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        //FRIDAY TIME IF SELECTED NO

        fri_no_open_time_from_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = true;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        fri_no_open_time_from_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = true;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        fri_no_open_time_to_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = true;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        fri_no_open_time_to_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = true;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        fri_no_brk_time_from_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = true;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        fri_no_brk_time_from_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = true;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });
        fri_no_brk_time_to_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = true;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });
        fri_no_brk_time_to_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = true;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        //saturday if no select=====

        sat_no_open_time_from_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = true;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        sat_no_open_time_from_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = true;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        sat_no_open_time_to_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("sat_no_open_ti","hour ");
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = true;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        sat_no_open_time_to_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("sat_no_open_ti","mint ");
                showDialog(TIME_DIALOG_ID);
                Log.e("sat_no_open_ti","saf ");

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = true;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = false;
            }
        });

        sat_no_brk_time_from_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = true;
                sat_brk_tme_to_mint = false;
            }
        });
        sat_no_brk_time_from_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = true;
                sat_brk_tme_to_mint = false;
            }
        });
        sat_no_brk_time_to_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = true;
            }
        });
        sat_no_brk_time_to_mint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
                opentime_from_check = false;
                open_tme_to_check = false;
                brk_time_from_check = false;
                brk_tme_to_check = false;

                fdy_no_opn_tme_hur = false;
                fdy_no_opn_tme_to_hur = false;
                fdy_brk_opn_tme_hur = false;
                fdy_brk_opn_tme_to_tohur = false;

                sat_opentime_from_hour = false;
                sat_open_tme_to_mint = false;
                sat_brk_time_from_hur = false;
                sat_brk_tme_to_mint = true;
            }
        });
    }

    public void CheckBoxclick(){
    //sun_thu layout=============
        if (company_List_Adapter_flag == 7) {

        }
        else {
            //sun thu layout
            sun_thu_cb_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            sun_thu_cb_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

            //friday layout==============
            fri_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            fri_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));


            //friday available 24 x 7  check box yes store timing layout
            fri_yes_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            fri_no_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

            //Saturday checkbox================
            sat_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            sat_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

            //Saturday Available 24x7================
            sat_yes_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            sat_no_checkbox2.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

            // delevery checkbox======
            delevety_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            delevery_uncheck.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

            //credit card checkbox=============
            credit_card_chek_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            credit_card_chek_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

            //sponsered checkbox==================
            sponsered_checkbox_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            sponsered_checkbox_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));


        }
        sun_thu_cb_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    sun_thu_timing24 = "1";
                    sun_thu_cb_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                    sun_thu_cb_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                    satthu_store_timing_layout.setVisibility(View.GONE);
            }
        });

        sun_thu_cb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sun_thu_timing24 = "2";
                    sun_thu_cb_yes.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                    sun_thu_cb_no.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));

                    satthu_store_timing_layout.setVisibility(View.VISIBLE);
            }
        });

        //friday layout==============
        fri_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fri_open_flag = "1";
                fri_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                fri_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                fri_yes_layout.setVisibility(View.VISIBLE);
            }
        });

        fri_uncheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                fri_open_flag = "0";
                fri_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                fri_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));

                fri_yes_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                fri_no_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

                fri_yes_layout.setVisibility(View.GONE);
                fri_yes_storetimingLayout.setVisibility(View.GONE);
            }
        });

        //friday available 24 x 7  check box yes store timing layout
     //   fri_time_24="1";

        fri_yes_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fri_time_24="1";
                fri_yes_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                fri_no_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                fri_yes_storetimingLayout.setVisibility(View.GONE);
            }
        });

        fri_no_uncheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fri_time_24="2";
                fri_yes_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                fri_no_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                fri_yes_storetimingLayout.setVisibility(View.VISIBLE);
            }
        });

        //Saturday checkbox================

        sat_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sat_open_flag = "1";
                sat_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sat_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sat_yes_layout.setVisibility(View.VISIBLE);
            }
        });

        sat_uncheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sat_open_flag = "0";
                sat_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sat_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));

                sat_yes_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sat_no_checkbox2.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));

                sat_yes_layout.setVisibility(View.GONE);
                sat_yes_storetiming.setVisibility(View.GONE);
            }
        });

        //Saturday Available 24x7================

        sat_yes_checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sat_timings24 = "1";
                sat_yes_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sat_no_checkbox2.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sat_yes_storetiming.setVisibility(View.GONE);
            }
        });

        sat_no_checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sat_timings24 = "2";
                sat_yes_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sat_no_checkbox2.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sat_yes_storetiming.setVisibility(View.VISIBLE);
            }
        });

        // delevery checkbox======
        delevety_checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                delivery = "1";
                delevety_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                delevery_uncheck.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
            }
        });

        delevery_uncheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                delivery = "0";
                delevety_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                delevery_uncheck.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            }
        });

        //credit card checkbox=============

        credit_card_chek_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                credit_card = "1";
                credit_card_chek_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                credit_card_chek_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
            }
        });

        credit_card_chek_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                credit_card = "0";
                credit_card_chek_yes.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                credit_card_chek_no.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            }
        });

        //sponsered checkbox==================
        sponsered_checkbox_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sponsered = "1";
                sponsered_checkbox_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sponsered_checkbox_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
            }
        });

        sponsered_checkbox_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sponsered = "0";
                sponsered_checkbox_yes.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sponsered_checkbox_no.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            }
        });
    }

    public void SpinnerOntuchListener(){
        category_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                category_spin_check = true;

                checkCategory.clear();
                listString = new StringBuilder();

                subcat_data = new StringBuilder();
                subCatId_list.clear();
                return false;
            }
        });


    }

    public void Dialog_list(){

        final Dialog dialog = new Dialog(AddNewStore.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_listview);

        ListView lv = (ListView ) dialog.findViewById(R.id.listview);
        Button done = (Button)dialog.findViewById(R.id.done);

        DialogListAdapter1 adapter = new DialogListAdapter1(AddNewStore.this, subCatName,SubCatIDList);
        lv.setAdapter(adapter);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    for (String s : checkCategory)
                        listString.append(s + ",");

                    for (String s : subCatId_list)
                        subcat_data.append(s +",");

                if (checkCategory.size()>0 && subCatId_list.size()>0) {
                    edt_subcategory.setText(listString.deleteCharAt(listString.lastIndexOf(",")));
                    subcat_data.deleteCharAt(subcat_data.lastIndexOf(","));
                    dialog.dismiss();
                }
                  //  subcat_data.append(edt_subcategory.getText().toString());

            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void Currency_DialogBox(){

        final Dialog dialog = new Dialog(AddNewStore.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_listview);

        ListView lv = (ListView ) dialog.findViewById(R.id.listview);
        Button done = (Button)dialog.findViewById(R.id.done);
        Log.e("SIze","name "+currency_name.size());
        DialogListAdapter1 adapter = new DialogListAdapter1(AddNewStore.this, currency_name,currency_id);
        lv.setAdapter(adapter);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    for (String s : currency_selected_name)
                      //  listString.append(s + ",");
                        currency_accepted.append(s + ",");

                if (currency_selected_name.size()>0) {
                    select_currency.setText(currency_accepted.deleteCharAt(currency_accepted.lastIndexOf(",")));
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.currency_accepted_edt:
                select_currency.setText("");
                Currency_DialogBox();
               // select_currency.setText("");

                currency_accepted = new StringBuilder();

                currency_selected_id.clear();
                currency_selected_name.clear();


                break;

            case R.id.getyourlocation:
                edt_Latitude.setText(latitude);
                edt_longitude.setText(longitude);
                break;

            case R.id.banner_img_view:
                bannercheck = true;
                selectImage();
                break;

            case R.id.upload_img_view1:
                bannercheck = false;
                banner1 = true;
                banner2 = false;
                banner3 = false;
                banner4 = false;
                banner5 = false;
                selectImage();
                break;

            case R.id.upload_img_view2:
                bannercheck = false;
                banner1 = false;
                banner2 = true;
                banner3 = false;
                banner4 = false;
                banner5 = false;
                selectImage();
                break;

            case R.id.upload_img_view3:
                bannercheck = false;
                banner1 = false;
                banner2 = false;
                banner3 = true;
                banner4 = false;
                banner5 = false;
                selectImage();
                break;

            case R.id.upload_img_view4:
                bannercheck = false;
                banner1 = false;
                banner2 = false;
                banner3 = false;
                banner4 = true;
                banner5 = false;
                selectImage();
                break;
            case R.id.upload_img_view5:
                bannercheck = false;
                banner1 = false;
                banner2 = false;
                banner3 = false;
                banner4 = false;
                banner5 = true;
                selectImage();
                break;

            case R.id.fab:
                finish();
                break;

            case R.id.btn_save:
                title = edt_title.getText().toString();
                description = edt_description.getText().toString();
                building_name = edt_building_name.getText().toString();
                POBox = edt_postalcode.getText().toString();
                storeContactNo= edt_store_contactno.getText().toString();
                landlineNo= edt_landlineno.getText().toString();
                landlineNo2= edt_landlineno2.getText().toString();
                tags= edt_tags.getText().toString();
                street= edt_street.getText().toString();

                if (edt_longitude.getText().toString().equals("") || edt_longitude.getText().toString().equals(null)|| edt_Latitude.getText().toString().equals("") || edt_Latitude.getText().toString().equals(null) || title.equals("") || title.equals(null)|| category_parent_id.equals("") || category_parent_id.equals(null)
                        || subcat_data.toString().equals("") || subcat_data.toString().equals(null)|| city.toString().equals("") || city.toString().equals(null)|| state.toString().equals("") || state.toString().equals(null) || sun_thu_timing24.equals("2") ) {

                   if (sun_thu_timing24.equals("2")) {
                       if (sun_thu_opentime.equals("") || sun_thu_closetime.equals("") || brksun_thu_opentime.equals("") || brksun_thu_closetime.equals("")) {
                           message = ("Enter: Sun to thu timings");
                           dialog.DialogBox(message);
                           message = "";
                       }
                       else
                           Save();
                   }
                   else
                       valiDate();
                }
                else {
                    Save();

                }
                break;
        }
    }

    public void valiDate(){

         if (title.equals("") || title.equals(null)) {
            message = ("Enter: Title");
            dialog.DialogBox(message);
            message = "";
        }
        else if (category_parent_id.equals("") || category_parent_id.equals(null)) {
            message = ("Select: Category");
            dialog.DialogBox(message);
            message = "";
        }
         else if (subcat_data.toString().equals("") || subcat_data.toString().equals(null)) {
            message = ("Select: Subcatagory");
            dialog.DialogBox(message);
            message = "";
        }
         else if (city.toString().equals("") || city.toString().equals(null)) {
            message = ("Select: City");
            dialog.DialogBox(message);
            message = "";
        }
         else if (state.toString().equals("") || state.toString().equals(null)) {
            message = ("Select: State");
            dialog.DialogBox(message);
            message = "";
        }
         else  if (edt_longitude.getText().toString().equals("") || edt_longitude.getText().toString().equals(null)|| edt_Latitude.getText().toString().equals("") || edt_Latitude.getText().toString().equals(null)) {
            message = ("Enter: Latitude and Longitude");
            dialog.DialogBox(message);
            message = "";
        }
    }

    class DialogListAdapter1 extends ArrayAdapter<String> {

        List<String> name = null;
        List<String> id = null;
        Context context;
        LayoutInflater inflater;

        public DialogListAdapter1(Context context, List<String> name,List<String> id) {
            super(context,0, name);

            this.context = context;
            this.name = name;
            this.id = id;
            inflater = LayoutInflater.from(context);
            Log.e("SIze","name "+name.size());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.dialog_list_adapter, null, true);

            TextView title = (TextView)listViewItem.findViewById(R.id.title);
            final CheckBox checkBox = (CheckBox)listViewItem.findViewById(R.id.checkbox);

            title.setText(name.get(position));

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (checkBox.isChecked()) {
                        checkCategory.add(name.get(position));
                        subCatId_list.add(id.get(position));

                        currency_selected_id.add(id.get(position));
                        currency_selected_name.add(name.get(position));

                    } else {
                        checkCategory.remove(name.get(position));
                        subCatId_list.remove(id.get(position));

                        currency_selected_id.remove(id.get(position));
                        currency_selected_name.remove(name.get(position));
                    }
                }
            });
            return  listViewItem;
        }
    }

    class CountryList extends AsyncTask<String, Void, String> {
        // ProgressDialog mProgressDialog;
        RemotRequest ruc = new RemotRequest();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
                /*mProgressDialog = new ProgressDialog(AddNewCompany.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();*/
        }
        @Override
        protected String doInBackground(String... params) {

            String result="";
            RequestHandler ruc = new RequestHandler();

            result = ruc.sendGetRequest(AppConstants.BASEURL + "getStateCityLandmark");

            Log.e("country data ","teset"+result);

            return result;
        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s !=null) {

                List<Country> list = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("state");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);
                        Country country = new Country();

                        country.setName(jobj.getString("name"));
                        country.setId(jobj.getString("id"));

                        list.add(country);
                    }
                    country2.setCountry_list(list);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                iscitytouch = false;
                if (company_List_Adapter_flag == 7){

                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddNewStore.this, country2.getCountry_list(), R.layout.customspinner2);
                    citySpinner.setAdapter(adapter);
                    for (int i=0; i<country2.getCountry_list().size(); i++){

                        if (country2.getCountry_list().get(i).getId().equals(city)) {
                            citySpinner.setSelection(i);
                        }
                    }
                }
                else {

                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddNewStore.this, country2.getCountry_list(), R.layout.customspinner2);
                citySpinner.setAdapter(adapter);
                 citySpinner.setPopupBackgroundResource(R.drawable.spinnerstyle);
                 }
                citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (isSpinnerTouched) {
                            city = country2.getCountry_list().get(position).getId();

                            Log.e("country data ", "check ce " + city);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                    Log.e("country data ", "size" + country2.getCountry_list().size());

                // mProgressDialog.dismiss();
            }
        }
    }

    class StateList extends AsyncTask<String, Void, String> {
        ProgressDialog mProgressDialog;
        RemotRequest ruc = new RemotRequest();
        CityList clist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AddNewStore.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait..");
            // Set progressdialog message
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            String result="";
            RequestHandler ruc = new RequestHandler();

            if (company_List_Adapter_flag == 7)
                result = ruc.sendGetRequest(AppConstants.BASEURL + "getStateCityLandmark/"+city);

            else
                result = ruc.sendGetRequest(AppConstants.BASEURL + "getStateCityLandmark/"+city);

            Log.e("country data ","teset"+result);

            return result;
        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s !=null) {

                List<CityList> citylist = new ArrayList<>();
                final CityList clist2  =new CityList();

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("city");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);
                        clist = new CityList();

                        clist.setName(jobj.getString("name"));
                        clist.setId(jobj.getString("id"));

                        citylist.add(clist);
                    }
                    //clist.setName("Selected");
                    // citylist.add(clist);
                    clist2.setCity_list(citylist);

                }catch(JSONException e){
                    e.printStackTrace();
                }
                Log.e("state data ", "iddd" +state);

                if (company_List_Adapter_flag == 7){

                    CityListAdapter adapter = new CityListAdapter(AddNewStore.this, clist2.getCity_list(), R.layout.customspinner2);
                    statespinner.setAdapter(adapter);
                    for (int i=0; i<clist2.getCity_list().size(); i++){

                        if (clist2.getCity_list().get(i).getId().equals(state)) {
                            statespinner.setSelection(i);
                        }
                    }
                }
                else {
                    if (stateSpinnerCheck) {
                        CityListAdapter adapter = new CityListAdapter(AddNewStore.this, clist2.getCity_list(), R.layout.customspinner2);
                        statespinner.setAdapter(adapter);
                        //   statespinner.setSelection(statespinner.getCount());
                    }
                }
                statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (stateSpinnerCheck) {
                            state = clist2.getCity_list().get(position).getId();
                            stateId = clist2.getCity_list().get(position).getId();
                            landmarkcheck= true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mProgressDialog.dismiss();
            }
        }
    }

    class LandMark extends AsyncTask<String, Void, String> {
        ProgressDialog mProgressDialog;
        RemotRequest ruc = new RemotRequest();
        LandMarkDTO lmlist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AddNewStore.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait..");
            // Set progressdialog message
            Log.e("country id ","teset");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            String result="";
            RequestHandler ruc = new RequestHandler();

            if(company_List_Adapter_flag == 7) {
                result = ruc.sendGetRequest(AppConstants.BASEURL + "getCityLandmark/" + state);
                Log.e("landmark data edit ","ed "+result);
            }
            else
                result = ruc.sendGetRequest(AppConstants.BASEURL + "getCityLandmark/"+stateId);
            Log.e("landmark data ","teset"+result);

            return result;
        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s !=null) {

                List<LandMarkDTO> landMarkDTOs = new ArrayList<>();
                final LandMarkDTO landmarklist  =new LandMarkDTO();

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("landmark");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        lmlist = new LandMarkDTO();

                        lmlist.setName(jobj.getString("name"));
                        lmlist.setId(jobj.getString("id"));

                        landMarkDTOs.add(lmlist);
                    }
                    landmarklist.setLandMarkDTOList(landMarkDTOs);
                }catch(JSONException e){
                    e.printStackTrace();
                }
                Log.e("state data ", "landmark" +landmark);

                if (company_List_Adapter_flag == 7){

                    LandMarkAdapter adapter = new LandMarkAdapter(AddNewStore.this, landmarklist.getLandMarkDTOList(), R.layout.customspinner2);
                    landmarkspinner.setAdapter(adapter);

                    if (!landmark.equals("0") ) {
                        for (int i = 0; landmarklist.getLandMarkDTOList().size() > i; i++) {
                            if (landmarklist.getLandMarkDTOList().get(i).getId().equals(landmark)) {
                                landmarkspinner.setSelection(i);
                            }
                        }
                    }
                }
                else {
                    LandMarkAdapter adapter = new LandMarkAdapter(AddNewStore.this, landmarklist.getLandMarkDTOList(), R.layout.customspinner2);
                    landmarkspinner.setAdapter(adapter);
                }
                landmarkspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        landmark = landmarklist.getLandMarkDTOList().get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mProgressDialog.dismiss();
            }
        }
    }

    class GetSubCategory extends AsyncTask<String, Void, String> {
        ProgressDialog mProgressDialog;
        RequestHandler ruc = new RequestHandler();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AddNewStore.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait..");
            // Set progressdialog message
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

            subCatName.clear();
            SubCatIDList.clear();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = ruc.sendGetRequest(AppConstants.BASEURL + "getcat_subCat/"+ category_parent_id);
            Log.e("Register:anil", result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(s);
                    JSONArray jsonSOSArray = jsonObj.getJSONArray("subcategory");

                    for (int i = 0; i < jsonSOSArray.length(); i++) {
                        JSONObject jsonobject = jsonSOSArray.getJSONObject(i);
                        subCatName.add(jsonobject.getString("name"));
                        SubCatIDList.add(jsonobject.getString("id"));
                    }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressDialog.dismiss();

                    Dialog_list();
            }
        }
    }

        class GetSotreData extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            String result="";
            Prefrence sharePref;
            Loader loader;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                AddStoreCategory storeAdapter = new AddStoreCategory(AddNewStore.this, catagoryList, catagoryId,107);
                category_spinner.setAdapter(storeAdapter);

                loader = new Loader(AddNewStore.this);
                sharePref = new Prefrence();

                mProgressDialog = new ProgressDialog(AddNewStore.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                CompanyListDAO companyDao = new CompanyListDAO();
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userId", sharePref.getUserID(AddNewStore.this));

                result = ruc.sendGetRequest(AppConstants.BASEURL + "outletDetails/"+outletID);

                Log.e("sizeee","com "+result);
                Log.e("sizeee","com "+outletID);

                return result;
            }
            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                if(json !=null) {

                    try {
                        JSONObject jsonObject = new JSONObject(json);

                        String status = jsonObject.getString("status");

                        if (status.equals("1")) {
                            JSONObject jobj = jsonObject.getJSONObject("data");



                            edt_title.setText(jobj.getString("title"));
                            edt_description.setText(jobj.getString("description"));
                            edt_building_name.setText(jobj.getString("building_no"));
                            edt_street.setText(jobj.getString("street"));
                            edt_postalcode.setText(jobj.getString("zipcode"));
                            edt_Latitude.setText(jobj.getString("lat"));
                            edt_longitude.setText(jobj.getString("lng"));
                            edt_store_contactno.setText(jobj.getString("personal_mob_no"));
                            edt_landlineno.setText(jobj.getString("phone_no"));
                            edt_landlineno2.setText(jobj.getString("phone_no2"));
                            edt_tags.setText(jobj.getString("tags"));
                            edt_subcategory.setText(jobj.getString("subcatname_list"));
                            category_parent_id = jobj.getString("category_parent_id");
                            subcat_data.append(jobj.getString("subcatid_list"));

                            city = jobj.getString("state");
                            state = jobj.getString("city");
                            landmark = jobj.getString("landmark_id");

                            if (!String.valueOf(category_parent_id).equals("") || !String.valueOf(category_parent_id).equals("0"))
                             for (int i=0; catagoryId.size()>i; i++ )
                                if (catagoryId.get(i).equals(category_parent_id))
                                    category_spinner.setSelection(i);

                            currency_accepted.append(jobj.getString("currency_accepted"));
                            select_currency.setText(currency_accepted.toString());

                            delivery = jobj.getString("delivery");
                            if (delivery.equals("1")){
                                delevety_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                                delevery_uncheck.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                            }
                            else {
                                delevery_uncheck.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                                delevety_checkbox1 .setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                            }

                            credit_card = jobj.getString("cc_flag");
                            if (credit_card.equals("1")){
                                credit_card_chek_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                                credit_card_chek_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                            }
                            else {
                                credit_card_chek_yes.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                                credit_card_chek_no.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                            }

                            sponsered = jobj.getString("sponsered");
                            if (sponsered.equals("1")){
                                sponsered_checkbox_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                                sponsered_checkbox_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                            }
                            else {
                                sponsered_checkbox_yes.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                                sponsered_checkbox_no.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                            }

                            List<String> galleryimgString = new ArrayList<>();
                            String imagelist = jobj.getString("imagelist");

                            if (!String.valueOf(imagelist).equals("")) {
                                String[] items = imagelist.split(",");
                                for (final String item : items) {
                                    galleryimgString.add(item);
                                }

                                for (int i=0; galleryimgString.size()>i; i++) {
                                    if (i == 0) {
                                        loader.displayImage(jsonObject.getString("image") + galleryimgString.get(0), upload_img_view1);
                                        upload_img_view2.setVisibility(View.VISIBLE);
                                    }
                                    if (i == 1) {
                                        loader.displayImage(jsonObject.getString("image") + galleryimgString.get(1), upload_img_view2);
                                        upload_img_view3.setVisibility(View.VISIBLE);
                                    }
                                    if (i == 2) {
                                        loader.displayImage(jsonObject.getString("image") + galleryimgString.get(2), upload_img_view3);
                                        upload_img_view4.setVisibility(View.VISIBLE);
                                    }
                                    if (i == 3) {
                                        loader.displayImage(jsonObject.getString("image") + galleryimgString.get(3), upload_img_view4);
                                        upload_img_view5.setVisibility(View.VISIBLE);
                                    }
                                    if (i == 4) {
                                        loader.displayImage(jsonObject.getString("image") + galleryimgString.get(4), upload_img_view5);
                                    }
                                }
                            }

                            //set timings
                            timingsSection(jobj);

                            mProgressDialog.dismiss();

                            new CountryList().execute(); // city spinner
                            new StateList().execute();
                            new LandMark().execute();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    public void timingsSection(JSONObject jobj){

        try {
            sun_thu_timing24 = jobj.getString("timings24");
            if (sun_thu_timing24.equals("2")){
                sun_thu_cb_no.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sun_thu_cb_yes.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                satthu_store_timing_layout.setVisibility(View.VISIBLE);

                //sun thu_open time
                sun_thu_opentime = jobj.getString("open_time");
                open_time_from_hour.setText(RemoveColon(sun_thu_opentime).get(0));
                open_time_from_mint.setText(RemoveColon(sun_thu_opentime).get(1));
                sun_thu_opentime = open_time_from_hour.getText().toString()+":"+open_time_from_mint.getText().toString()+":"+"00";

                //sun thu close time
                sun_thu_closetime = jobj.getString("close_time");
                open_time_to_hour.setText(RemoveColon(sun_thu_closetime).get(0));
                open_time_to_mint.setText(RemoveColon(sun_thu_closetime).get(1));
                sun_thu_closetime = open_time_to_hour.getText().toString()+":"+open_time_to_mint.getText().toString()+":"+"00";

                //sun thu break open time
                brksun_thu_opentime = jobj.getString("open_time_brk");
                brk_time_from_hour.setText(RemoveColon(brksun_thu_opentime).get(0));
                brk_time_from_mint.setText(RemoveColon(brksun_thu_opentime).get(1));
                brksun_thu_opentime = brk_time_from_hour.getText().toString()+":"+brk_time_from_mint.getText().toString()+":"+"00";

                //sun thu break close time
                brksun_thu_closetime = jobj.getString("close_time_brk");
                brk_time_to_hour.setText(RemoveColon(brksun_thu_closetime).get(0));
                brk_time_to_mint.setText(RemoveColon(brksun_thu_closetime).get(1));
                brksun_thu_closetime = brk_time_to_hour.getText().toString()+":"+brk_time_to_mint.getText().toString()+":"+"00";
                Log.e("open time1 ","d "+sun_thu_opentime);

            }
            else {
                sun_thu_cb_yes.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sun_thu_cb_no.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                satthu_store_timing_layout.setVisibility(View.GONE);
            }

            // fri open time
            fri_open_flag = jobj.getString("fri_open_flag");
            if (fri_open_flag.equals("1")){
                fri_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                fri_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                fri_yes_layout.setVisibility(View.VISIBLE);

                // fri 24 timing
                fri_time_24 = jobj.getString("fri_timings24");
                if (fri_time_24.equals("2")){
                    fri_yes_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                    fri_no_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                    fri_yes_storetimingLayout.setVisibility(View.VISIBLE);

                    //fri_open_time
                    fri_open_time = jobj.getString("fri_open_time");
                    fri_no_open_time_from_hour.setText(RemoveColon(fri_open_time).get(0));
                    fri_no_open_time_from_mint.setText(RemoveColon(fri_open_time).get(1));

                    //fri_close_time
                    fri_close_time = jobj.getString("fri_close_time");
                    fri_no_open_time_to_hour.setText(RemoveColon(fri_close_time).get(0));
                    fri_no_open_time_to_mint.setText(RemoveColon(fri_close_time).get(1));

                    //fri_open_time_brk
                    fri_open_time_brk = jobj.getString("fri_open_time_brk");
                    fri_no_brk_time_from_hour.setText(RemoveColon(fri_open_time_brk).get(0));
                    fri_no_brk_time_from_mint.setText(RemoveColon(fri_open_time_brk).get(1));

                    //fri_close_time_brk
                    fri_close_time_brk = jobj.getString("fri_close_time_brk");
                    fri_no_brk_time_to_hour.setText(RemoveColon(fri_close_time_brk).get(0));
                    fri_no_brk_time_to_mint.setText(RemoveColon(fri_close_time_brk).get(1));
                }
                else {
                    fri_no_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                    fri_yes_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                }
            }
            else {
                fri_yes_layout.setVisibility(View.GONE);
                fri_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                fri_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
            }

            //sat_open_flag
            sat_open_flag = jobj.getString("sat_open_flag");
            if (sat_open_flag.equals("1")){
                sat_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                sat_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sat_yes_layout.setVisibility(View.VISIBLE);

                //sat timing 24
                sat_timings24 = jobj.getString("sat_timings24");
                if (sat_timings24.equals("2")){
                    sat_yes_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                    sat_no_checkbox2.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
                    sat_yes_storetiming.setVisibility(View.VISIBLE);

                    //sat_open_time
                    sat_open_time = jobj.getString("sat_open_time");
                    sat_no_open_time_from_hour.setText(RemoveColon(sat_open_time).get(0));
                    sat_no_open_time_from_mint.setText(RemoveColon(sat_open_time).get(1));

                    //sat_close_time
                    sat_close_time = jobj.getString("sat_close_time");
                    sat_no_open_time_to_hour.setText(RemoveColon(sat_close_time).get(0));
                    sat_no_open_time_to_mint.setText(RemoveColon(sat_close_time).get(1));

                    //sat_open_time
                    sat_open_time_brk = jobj.getString("sat_open_time_brk");
                    sat_no_brk_time_from_hour.setText(RemoveColon(sat_open_time_brk).get(0));
                    sat_no_brk_time_from_mint.setText(RemoveColon(sat_open_time_brk).get(1));

                    //sat_open_time
                    sat_close_time_brk = jobj.getString("sat_close_time_brk");
                    sat_no_brk_time_to_hour.setText(RemoveColon(sat_close_time_brk).get(0));
                    sat_no_brk_time_to_mint.setText(RemoveColon(sat_close_time_brk).get(1));
                }
            }
            else {
                sat_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sat_uncheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));

                sat_no_checkbox2.setButtonDrawable(getResources().getDrawable(R.drawable.uncheckbtn));
                sat_yes_checkbox1.setButtonDrawable(getResources().getDrawable(R.drawable.checkbtn));
            }

        }
        catch (Exception e){
            e.getMessage();
        }
    }

    public List<String> RemoveColon(String time){
        List<String> timedata = new ArrayList<>();
        String[] items = time.split(":");

        for (final String item : items) {
                timedata.add(item);
        }
        return timedata;
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        true );
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour   = hourOfDay;
            minute = minutes;
            updateTime(hour,minute);
        }

    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 24) {
            hours -= 24;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 24;
            timeSet = "AM";
        } else if (hours == 24)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        //open time FROM  open time
        if (opentime_from_check) {
            open_time_from_hour.setText("" + hours);
            open_time_from_mint.setText(minutes);
            sun_thu_opentime = hours+":"+minutes+":"+"00";
        }
        //close time TO
        if (open_tme_to_check) {
            open_time_to_hour.setText("" + hours);
            open_time_to_mint.setText(minutes);
            sun_thu_closetime= hours+":"+minutes+":"+"00";
        }
         //break time from open
        if (brk_time_from_check) {
            brk_time_from_hour.setText("" + hours);
            brk_time_from_mint.setText(minutes);
            brksun_thu_opentime = hours+":"+minutes+":"+"00";
        }
         //break time to close
        if (brk_tme_to_check) {
            brk_time_to_hour.setText("" + hours);
            brk_time_to_mint.setText(minutes);
            brksun_thu_closetime = hours+":"+minutes+":"+"00";
        }

        //Friday ===========no selected from check box open time from
        if (fdy_no_opn_tme_hur) {
            fri_open_time = hours+":"+minutes+":"+"00";
            fri_no_open_time_from_hour.setText("" + hours);
            fri_no_open_time_from_mint.setText(minutes);
        }
        // friday open time to// TODO: 08-09-2016
        if (fdy_no_opn_tme_to_hur) {
            fri_close_time = hours+":"+minutes+":"+"00";
            fri_no_open_time_to_hour.setText("" + hours);
            fri_no_open_time_to_mint.setText(minutes);
        }
        // friday break time from // TODO: 08-09-2016
        if (fdy_brk_opn_tme_hur) {
            fri_open_time_brk = hours+":"+minutes+":"+"00";
            fri_no_brk_time_from_hour.setText("" + hours);
            fri_no_brk_time_from_mint.setText(minutes);
        }

        // friday break time to // TODO: 08-09-2016
        if (fdy_brk_opn_tme_to_tohur) {
            fri_close_time_brk = hours+":"+minutes+":"+"00";
            fri_no_brk_time_to_hour.setText("" + hours);
            fri_no_brk_time_to_mint.setText(minutes);
        }

        //Saturday =================
        if (sat_opentime_from_hour) {
            sat_open_time =  hours+":"+minutes+":"+"00";
            sat_no_open_time_from_hour.setText("" + hours);
            sat_no_open_time_from_mint.setText(minutes);
        }
        if (sat_open_tme_to_mint) {
            sat_close_time = hours+":"+minutes+":"+"00";
            sat_no_open_time_to_hour.setText("" + hours);
            sat_no_open_time_to_mint.setText(minutes);
        }
        if (sat_brk_time_from_hur) {
            sat_open_time_brk = hours+":"+minutes+":"+"00";
            sat_no_brk_time_from_hour.setText("" + hours);
            sat_no_brk_time_from_mint.setText(minutes);
        }
        if (sat_brk_tme_to_mint) {
            sat_close_time_brk= hours+":"+minutes+":"+"00";
            sat_no_brk_time_to_hour.setText("" + hours);
            sat_no_brk_time_to_mint.setText(minutes);
        }
    }

    //get latitude or longitude============
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
            Log.e("clong lat", " " + latitude+"long "+longitude);
            //Toast.makeText(getApplicationContext(),"LATITUDE "+latitude+"longitude "+longitude,Toast.LENGTH_SHORT).show();

        } else {
            Constant.LATITUDE = "";
            Constant.LONGITUDE = "";
            Toast.makeText(getApplicationContext(),"(Couldn't get the location. Make sure location is enabled on the device)",Toast.LENGTH_SHORT).show();
        }
    }

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

    protected synchronized void getLatLong() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
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

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //  Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        Log.e("clll","d");
        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position, long id) {

    }

    //GetImage from gallery

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewStore.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {

                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(AddNewStore.this,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(AddNewStore.this,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            } else {
                                ActivityCompat.requestPermissions(AddNewStore.this,
                                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                        SELECT_FILE);
                            }
                        } else {
                            ActivityCompat.requestPermissions(AddNewStore.this,
                                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                    SELECT_FILE);
                        }
                    }
                    else {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_GELLERY);
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grandResults) {
        switch (requestCode) {

            case SELECT_FILE: {
                if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_GELLERY);
                } else {
                }
                return;
            }

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_GELLERY) {
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo = null;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();

        if (bannercheck) {
            banner_img_view.setImageBitmap(thumbnail);
            banner = Base64.encodeToString(b, Base64.DEFAULT);
        }
        else {
            if (banner1) {
                img_0 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view1.setImageBitmap(thumbnail);
                upload_img_view2.setVisibility(View.VISIBLE);
                banner2 = true;
            }
            else if (banner2){
                img_1 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view2.setImageBitmap(thumbnail);
                upload_img_view3.setVisibility(View.VISIBLE);
                banner3 = true;
            }
            else if (banner3){
                img_2 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view3.setImageBitmap(thumbnail);
                upload_img_view4.setVisibility(View.VISIBLE);
                banner3 = true;
            }
            else if (banner4){
                img_3 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view4.setImageBitmap(thumbnail);
                upload_img_view5.setVisibility(View.VISIBLE);
                banner5 = true;
            }
            else if (banner5){
                img_4 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view5.setImageBitmap(thumbnail);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();

        bm = BitmapFactory.decodeFile(selectedImagePath, options);
       // bm = Bitmap.createScaledBitmap(bm,100,50,true);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        byte[] b = null;

        if (bm !=null) {
            b = baos.toByteArray();
        }
       // getImage= Base64.encodeToString(b, Base64.DEFAULT);

        if (bannercheck) {
            banner = Base64.encodeToString(b, Base64.DEFAULT);
            Log.e("imgae resu11", " d " + banner);
            int imageWidth=bm.getWidth();
            int imageHeight=bm.getHeight();

            if (imageWidth >= 1200 && imageHeight >= 400)
            banner_img_view.setImageBitmap(bm);
            else
                dialog.DialogBox("Banner size should be 1200 x 400");
        }
        else {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;

            if (bm!=null)
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

            if (banner1) {
                img_0 = Base64.encodeToString(b, Base64.DEFAULT);
                Log.e("imgae resu", " d " + img_0);
                upload_img_view1.setImageBitmap(bm);
                upload_img_view2.setVisibility(View.VISIBLE);
                banner2 = true;
            } else if (banner2) {
                img_1 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view2.setImageBitmap(bm);
                upload_img_view3.setVisibility(View.VISIBLE);
                banner3 = true;
            } else if (banner3) {
                img_2 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view3.setImageBitmap(bm);
                upload_img_view4.setVisibility(View.VISIBLE);
                banner3 = true;
            } else if (banner4) {
                img_3 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view4.setImageBitmap(bm);
                upload_img_view5.setVisibility(View.VISIBLE);
                banner5 = true;
            } else if (banner5) {
                img_4 = Base64.encodeToString(b, Base64.DEFAULT);
                upload_img_view5.setImageBitmap(bm);
            }
        }
    }

    public void Save(){

        class SaveFormData extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(AddNewStore.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                String result="";
                RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("user_id", sharePre.getUserID(AddNewStore.this));
                data.put("outlet_name_id",appSession.getCompany_id());
                data.put("title",title);
                data.put("subcategory",subcat_data.toString());

                data.put("category_parent_id",category_parent_id);
                data.put("type","0");

                data.put("description",description);
                data.put("licence_no","");
                data.put("address","");

                data.put("landmark","");
                data.put("landmark_id",landmark);

                data.put("city",state);
                data.put("state",city);
                data.put("street",street);
                data.put("building_no",building_name);
                data.put("zipcode",POBox);
                data.put("lat",latitude);
                data.put("lng",longitude);
                data.put("contact_person","");
                data.put("designation","");
                data.put("personal_mob_no",storeContactNo);
                data.put("phone_no",landlineNo);
                data.put("phone_no2",landlineNo2);
                data.put("fax_no","");

                data.put("timings24",sun_thu_timing24);
                data.put("open_time",sun_thu_opentime);
                data.put("close_time",sun_thu_closetime);
                data.put("open_time_brk",brksun_thu_opentime);
                data.put("close_time_brk",brksun_thu_closetime);
                data.put("timings_delivery",sun_thu_timing24);

                data.put("fri_open_flag", fri_open_flag);
                data.put("fri_timings24", fri_time_24);
                data.put("fri_open_time", fri_open_time);
                data.put("fri_close_time", fri_close_time);
                data.put("fri_open_time_brk", fri_open_time_brk);
                data.put("fri_close_time_brk", fri_close_time_brk);
                data.put("fri_timings_delivery",fri_time_24);

                data.put("sat_open_flag",sat_open_flag);
                data.put("sat_timings24",sat_timings24);
                data.put("sat_open_time",sat_open_time);
                data.put("sat_close_time",sat_close_time);
                data.put("sat_open_time_brk",sat_open_time_brk);
                data.put("sat_close_time_brk",sat_close_time_brk);

                data.put("currency_accepted",currency_accepted.toString());
                data.put("cc_flag",credit_card);
                data.put("delivery",delivery);
                data.put("tags",tags);
                data.put("extra_data","");
                data.put("like_count","0");
                data.put("featured","0");
                data.put("sponsered",sponsered);
                data.put("banner",banner);
                data.put("slug","");
                data.put("status","0");

                data.put("0",img_0);
                data.put("1",img_1);
                data.put("2",img_2);
                data.put("3",img_3);
                data.put("4",img_4);
                data.put("mem_type","3");

                //edit form==================
                if (company_List_Adapter_flag == 7) {
                 data.put("id",outletID);
                    result = ruc.sendPostRequest(AppConstants.BASEURL + "updateStore", data);
                }
                else {
                    result = ruc.sendPostRequest(AppConstants.BASEURL + "addStore", data);
                }

                Log.e("Addnew Sotore data ","check "+result);
                JSONObject jsonObject = new JSONObject(data);
                Log.e("AddnewCompany ddddd ","teset"+jsonObject);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        if (jsonObject.getString("status").equals("1"))
                        {
                            Toast.makeText(AddNewStore.this,"Store added successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("Addnewstoredata","data");
                            setResult(101,intent);
                            mProgressDialog.dismiss();
                            finish();
                        }
                        else {
                            Toast.makeText(AddNewStore.this,"Try again",Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        new SaveFormData().execute();
    }
}
