package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.SlideInEffect;

import Utils.AppConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;
import Utils.RequestHandler;
import Utils.SubCatUtil;
import Utils.AppSession;
import adapter.SubcetagriesAdapter;

/**
 * Created by Sonu Saini on 4/15/2016.
 */
public class MaintenanceActivity extends Activity implements View.OnClickListener{

    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account;
    public TextView main_txt_skip,loc_sub,txt_address,txt_tryagain,txt_catname, txt_home_active, txt_recent, txt_recent_active, txt_categorie_active, txt_boobkmark, txt_boobkmark_active, txt_account, txt_account_active;
    ImageView btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    Button btn_login;
    String status,msg;
    String sub_cat_id,catname,catid;
    ProgressDialog mProgressDialog;
    // Declare Variables
    String latitude= "" ,longitude= "";
    SubcetagriesAdapter adapter;
    ArrayList<SubCatUtil> arraylist = new ArrayList<SubCatUtil>();
    LinearLayout iv_back;
    public static MaintenanceActivity instance;
    AppSession appSession;
    Prefrence sharpref;
    Utils.LightText txt_home,txt_category;
//239
 //   private ListView listView;
    JazzyListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintinance_activity);

        sharpref = new Prefrence();
        appSession=new AppSession(getApplicationContext());
        iv_back=(LinearLayout)findViewById(R.id.iv_back);
      //  listView = (ListView) findViewById(R.id.listView);

        listView =  (JazzyListView) findViewById(R.id.homelistview);
        listView.setTransitionEffect(new SlideInEffect());

        iv_back.setOnClickListener(this);

        instance = this;
        Intent intent=getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sub_cat_id =bundle.getString("id");
            catname =  bundle.getString("name");
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitude");
            Log.e("cat_id","id "+sub_cat_id);
        }
            intit();
        txt_catname.setText(catname);
        if(Constant.SELECTED_LOCATION.equals("")){
            loc_sub.setText(Constant.UNSELECTED_LOCATION);
        }
        else {
            loc_sub.setText(Constant.SELECTED_LOCATION);
        }

        registerUser();
        if (!appSession.getUserloginStatus().equals("")) {
           btn_account.setBackgroundResource(R.drawable.account_icon);

        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        }

    }

    public void intit(){
        loc_sub = (TextView) findViewById(R.id.tv_subCat_loc) ;
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);

        //--------------------------------------------------------button----------------------
        main_txt_skip = (TextView)findViewById(R.id.main_txt_skip);
        txt_catname = (TextView)findViewById(R.id.supermarkit);
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

        txt_home = (Utils.LightText) findViewById(R.id.txt_home);
        txt_category = (Utils.LightText) findViewById(R.id.txt_category);
        //---------------------------------textview-----------------------------------------
        main_txt_skip.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        loc_sub.setOnClickListener(this);

        btn_home_acive.setVisibility(View.VISIBLE);
        btn_home.setVisibility(View.GONE);

        if (String.valueOf(sharpref.getUserID(MaintenanceActivity.this)).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        }

        Log.e("chekc","D "+Constant.TabLayout_Home_Status +"ccc "+Constant.TabLayout_Category_Status);
        if (Constant.TabLayout_Home_Status == true){
            btn_home_acive.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.GONE);
            txt_home.setTextColor(getResources().getColor(R.color.lightred));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(Color.parseColor("#AAAAAA"));

        }
        else if(Constant.TabLayout_Category_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(Color.parseColor("#AAAAAA"));

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

            Intent intenthome = new Intent(MaintenanceActivity.this, Home.class);
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

            Intent intent = new Intent(MaintenanceActivity.this,MoreScreen.class);
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

            Intent intent = new Intent(MaintenanceActivity.this, CategoryActiviyt.class);
            startActivity(intent);
            finish();


        } else if (v == ll_boobmark) {
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
           
            if (sharpref.getUserID(MaintenanceActivity.this).equals("null")) {

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(MaintenanceActivity.this, LoginActivity.class);
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

                Intent intenthome = new Intent(MaintenanceActivity.this, BookmarkActivity.class);
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

                if(sharpref.getUserID(MaintenanceActivity.this).equals("null")){
                    Intent intenthome = new Intent(MaintenanceActivity.this, LoginActivity.class);
                    startActivity(intenthome);
                    finish();

                }else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent = new Intent(MaintenanceActivity.this, MyAccount.class);
                    startActivity(intent);
                    finish();
                }

        }else if (v == iv_back) {
                finish();

            }else if (v == loc_sub) {
            Constant.Check_current_status = true;
            Intent intent = new Intent(MaintenanceActivity.this, SearchLocation.class);
            intent.putExtra("maintince_screen_flag",9);
            intent.putExtra("KEY_SEARCH",1);
            startActivity(intent);
            }
        else if (v.getId() == R.id.main_txt_skip){
            Home.CATID= catid;
            Log.e("outletpage","pp "+sub_cat_id+"lat "+latitude+"cat id  "+catname+"long "+longitude);
            Intent intent=new Intent(MaintenanceActivity.this,OutlateListingPage.class);
            intent.putExtra("flag",7);
            intent.putExtra("catName",catname);
            intent.putExtra("catid", sub_cat_id);
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            startActivity(intent);

        }
    }

    private void registerUser() {

        register("", "", "", "2", "", "2.1");
    }

    private void register(String name, String email, String password, String deviceType, String deviceId, String appVersion) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler ruc = new RequestHandler();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(MaintenanceActivity.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String result = ruc.sendGetRequest(AppConstants.BASEURL + "getcat_subCat/" + sub_cat_id);
                    Log.e("subcategories", result);
                    return result;
                } catch ( Exception e){
                    Log.e("Maintenance ","error "+e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if (s != null) {
                        //  CommonUtils.showToast(getApplicationContext(),s);
                        try {
                            JSONObject jsonObj = new JSONObject(s);

                            JSONArray jsonSOSArray = jsonObj.getJSONArray("subcategory");
                        /*Log.e("Maintenance Activity:",jsonSOSArray.toString());*/
                            for (int i = 0; i < jsonSOSArray.length(); i++) {

                                HashMap<String, String> map = new HashMap<String, String>();
                                JSONObject jsonobject = jsonSOSArray.getJSONObject(i);

                                if(getResources().getBoolean(R.bool.isTablet)) {
                                    SubCatUtil wp = new SubCatUtil(jsonobject.getString("image_ipad"), jsonobject.getString("name"), jsonobject.getString("logo"), jsonobject.getString("id"));
                                    arraylist.add(wp);
                                }
                                else if (getResources().getBoolean(R.bool.isphon_x)){
                                    SubCatUtil wp = new SubCatUtil(jsonobject.getString("image"), jsonobject.getString("name"), jsonobject.getString("logo"), jsonobject.getString("id"));
                                    arraylist.add(wp);
                                }
                                else {
                                    SubCatUtil wp = new SubCatUtil(jsonobject.getString("image_6S"), jsonobject.getString("name"), jsonobject.getString("logo"), jsonobject.getString("id"));
                                    arraylist.add(wp);
                                }
                                //Log.e("ArrayList",arraylist.toString());image_6S
                            }
                        } catch (JSONException e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        adapter = new SubcetagriesAdapter(getApplicationContext(), arraylist, sub_cat_id, catname, longitude, latitude);
                        String str2 = arraylist.toString();
                        // Set the adapter to the ListView
                        System.out.println(str2);
                        listView.setAdapter(adapter);
                        mProgressDialog.dismiss();

                    } else {
                        CommonUtils.showToast(getApplicationContext(), "Server Exceptions...");
                        mProgressDialog.dismiss();
                    }
                }catch ( Exception e){
                    Log.e("Maintenance ","error "+e.getMessage());
                }
            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute("", "", "", "", "", "");
    }

    public static MaintenanceActivity getInstance(){
        return instance;
    }


}
