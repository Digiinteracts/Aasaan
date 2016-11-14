package com.assan;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DTO.Country;
import UtilApi.RemotRequest;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.Constant;
import Utils.Prefrence;
import Utils.RequestHandler;
import adapter.CustomSpinnerAdapter;

public class StoreStats extends AppCompatActivity implements View.OnClickListener {

    TextView companyname,storename,startdate,end_date,text_selected,bookmarkvalue,callvalue,likevalue,reviewvalue,viewvalue,cleartxt;
    AppSession appSession;
    int company_List_Adapter_flag = 0;
    LinearLayout filterlayout;
    String store_name="",outletId="",from_tme="",to_tme="",filtervalue="all";
    private int year, month, day;
    boolean startDate_flag,endDate_flag;
    private Calendar calendar;
    ImageView search,imageview_cross,backicn,btn_home_acive,  btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account;
    Prefrence sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_stats);

        sharePref = new Prefrence();
        appSession = new AppSession(StoreStats.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            company_List_Adapter_flag = bundle.getInt("company_List_Adapter_flag");
            store_name = bundle.getString("store_name");
            outletId = bundle.getString("store_id");
        }

        init();

        companyname.setText("Company Name: "+appSession.getUserNmae());
        if (company_List_Adapter_flag == 9)
            storename.setText("Store Name: "+store_name);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

    }

    public void init(){

        backicn = (ImageView)findViewById(R.id.backicn);

        companyname = (TextView)findViewById(R.id.companyname);
        storename = (TextView)findViewById(R.id.storename);
        startdate = (TextView)findViewById(R.id.startdate);
        end_date = (TextView)findViewById(R.id.end_date);
        text_selected = (TextView)findViewById(R.id.text_selected);

        bookmarkvalue = (TextView)findViewById(R.id.bookmarkvalue);
        callvalue = (TextView)findViewById(R.id.callvalue);
        likevalue = (TextView)findViewById(R.id.likevalue);
        reviewvalue = (TextView)findViewById(R.id.reviewvalue);
        viewvalue = (TextView)findViewById(R.id.viewvalue);
        cleartxt = (TextView)findViewById(R.id.cleartxt);

        filterlayout = (LinearLayout) findViewById(R.id.filterlayout);

        //tablayout=========
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);

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


        backicn.setOnClickListener(this);
        startdate.setOnClickListener(this);
        end_date.setOnClickListener(this);
        filterlayout.setOnClickListener(this);
        cleartxt.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.backicn:
                finish();
                break;

            case R.id.startdate:
                showDialog(999);
                startDate_flag = true;
                endDate_flag = false;
                break;

            case R.id.end_date:
                showDialog(999);

                startDate_flag = false;
                endDate_flag = true;
                break;

            case R.id.filterlayout:
                filterDialogBox();
                break;

            case R.id.cleartxt:
                FilterData(filtervalue,"","");
                break;

            case R.id.ll_home:
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

                Intent intenthome = new Intent(StoreStats.this, Home.class);
                startActivity(intenthome);
                finish();
                break;

            case R.id.ll_recent:
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

                Intent intent2 = new Intent(StoreStats.this, MoreScreen.class);
                startActivity(intent2);
                break;

            case R.id.ll_categorie:
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

                Intent intcategory = new Intent(StoreStats.this, CategoryActiviyt.class);
                startActivity(intcategory);
                finish();
                break;

            case R.id.ll_bookmark:
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

                if (sharePref.getUserID(StoreStats.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView) dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(StoreStats.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(StoreStats.this, BookmarkActivity.class);
                    startActivity(intenthome1);
                    finish();
                }
                break;

            case R.id.ll_account:
                btn_bookmark_active.setVisibility(View.GONE);

                btn_home_acive.setVisibility(View.GONE);
                btn_categorie_active.setVisibility(View.GONE);
                btn_recent_active.setVisibility(View.GONE);

                btn_bookmak.setVisibility(View.VISIBLE);
                btn_home.setVisibility(View.VISIBLE);
                btn_categorie.setVisibility(View.VISIBLE);
                btn_recent.setVisibility(View.VISIBLE);

                if (sharePref.getUserID(StoreStats.this).equals("null")) {

                    Intent intent_account = new Intent(StoreStats.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status = true;

                    Intent intent6 = new Intent(StoreStats.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        if (startDate_flag) {
            startdate.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
            from_tme = ""+year+"-"+""+month+"-"+""+day;
        }

        else if (endDate_flag) {
            end_date.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
            to_tme= ""+year+"-"+""+month+"-"+""+day;
            FilterData(filtervalue,from_tme,to_tme);
        }
    }

    public void filterDialogBox(){
        final Dialog dialog = new Dialog(StoreStats.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filter_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView all = (TextView)dialog.findViewById(R.id.title);
        TextView iPhone = (TextView)dialog.findViewById(R.id.nearby);
        TextView Android = (TextView)dialog.findViewById(R.id.ratingby);
        TextView web = (TextView)dialog.findViewById(R.id.clearall);

        all.setText("All");
        all.setTextSize(19);
        all.setTextColor(getResources().getColor(R.color.filter_blue_color));

        iPhone.setText("iPhone");
        Android.setText("Android");
        web.setText("Web");

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selected.setText("All");
                dialog.dismiss();
                filtervalue = "all";
                FilterData(filtervalue,"","");
            }
        });

        iPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selected.setText("iPhone");
                dialog.dismiss();
                filtervalue = "iphone";
                FilterData(filtervalue,"","");
            }
        });

        Android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selected.setText("Android");
                dialog.dismiss();
                filtervalue = "android";
                FilterData(filtervalue,"","");
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selected.setText("Web");
                dialog.dismiss();
                filtervalue = "web";
                FilterData(filtervalue,"","");
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

    public void FilterData(final String filtervalue,final String from_tme,final String to_tme) {
        class GetData extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(StoreStats.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                String result = "";
                RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("type",filtervalue);
                data.put("outlet_id",outletId);
                data.put("from",from_tme);
                data.put("to",to_tme);


                result = ruc.sendPostRequest(AppConstants.BASEURL +"getOutletAnalytics", data);

                Log.e("filter data ", "teset" + result);
                Log.e("filter data ", "data" + data);

                return result;
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {

                   // listname.clear();
                   // listvalue.clear();
                    try {
                        JSONObject jobj = new JSONObject(s);
                        JSONArray jsonArray = jobj.getJSONArray("result");

                        for (int i=0; jsonArray.length()>i; i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            //listname.add(jsonObject.getString("name"));
                            //listvalue.add(jsonObject.getString("value"));

                            if (jsonObject.getString("name").equals("Bookmark"))
                                bookmarkvalue.setText(jsonObject.getString("value"));

                            if (jsonObject.getString("name").equals("Click"))
                                callvalue.setText(jsonObject.getString("value"));

                            if (jsonObject.getString("name").equals("Likes"))
                                likevalue.setText(jsonObject.getString("value"));

                            if (jsonObject.getString("name").equals("Review"))
                                reviewvalue.setText(jsonObject.getString("value"));

                            if (jsonObject.getString("name").equals("View"))
                                viewvalue.setText(jsonObject.getString("value"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                   /* for (int i=0; listname.size()>i; i++){

                        if (listname.get(i).equals("Bookmark")){
                            bookmarkvalue.setText(listvalue.get(i));
                        }
                        if (listname.get(i).equals("Click")){
                            callvalue.setText(listvalue.get(i));
                            Log.e("CLICK ","D "+listvalue.get(i));
                        }
                        if (listname.get(i).equals("Likes")){
                            likevalue.setText(listvalue.get(i));
                            Log.e("CLICK ","D "+listvalue.get(i));
                        }
                        if (listname.get(i).equals("Review")){
                            reviewvalue.setText(listvalue.get(i));
                            Log.e("CLICK ","D "+listvalue.get(i));
                        }
                        if (listname.get(i).equals("View")){
                            viewvalue.setText(listvalue.get(i));
                            Log.e("CLICK ","D "+listvalue.get(i));
                        }
                    }
*/
                    mProgressDialog.dismiss();
                }
            }
        }

        new GetData().execute();
    }
}
