package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utils.AppConstants;
import Utils.AppSession;
import Utils.Constant;
import Utils.HorizontalUtil;
import Utils.Prefrence;
import Utils.RequestHandler;
import adapter.Headerapdater;
import adapter.RecyclerAdapter;
import it.sephiroth.android.library.widget.HListView;
import model.catmodel;

/**
 * Created by Sonu Saini on 4/12/2016.
 */
public class CategoryActiviyt extends Activity implements View.OnClickListener {
    RecyclerView recyclerView;
    LinearLayout iv_back;
    String status, msg;

    ProgressDialog mProgressDialog;
    // Declare Variables

    RecyclerAdapter adapter;

    private List<catmodel> arraylist = new ArrayList<>();
    TextView txt_tryagain,location_search;
    AppSession appSession;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account;
    TextView txt_home,location_name, txt_home_active, txt_recent, txt_recent_active, txt_categorie, txt_categorie_active, txt_boobkmark, txt_boobkmark_active, txt_account, txt_account_active;
    ImageView btn_home_acive, btn_home,search_categori, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    Button btn_login;
    Prefrence sharpref;
    private ListView listView;
    public static CategoryActiviyt instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoris_activity);

        appSession=new AppSession(getApplicationContext());

        instance = this;

        iv_back = (LinearLayout) findViewById(R.id.iv_back);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
        //--------------------------------------------------------button----------------------
        btn_home = (ImageView) findViewById(R.id.btn_home);
        search_categori = (ImageView) findViewById(R.id.imageView2);
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
        location_name = (TextView)findViewById(R.id.location_name);
        location_search = (TextView)findViewById(R.id.location_search);
        txt_account = (TextView)findViewById(R.id.txt_account);

        //---------------------------------textview-----------------------------------------


        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        search_categori.setOnClickListener(this);
        location_search.setOnClickListener(this);


        btn_categorie_active.setVisibility(View.VISIBLE);
        btn_categorie.setVisibility(View.GONE);

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        sharpref = new Prefrence();
        if(Constant.SELECTED_LOCATION.equals("")) {
            Log.e("SELECTED",Constant.SELECTED_LOCATION+"unselected "+Constant.UNSELECTED_LOCATION);
            location_name.setText(Constant.UNSELECTED_LOCATION);
        }else {
            location_name.setText(Constant.SELECTED_LOCATION);

        }

        if (String.valueOf(sharpref.getUserID(CategoryActiviyt.this)).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.account_icon);
            txt_account.setText("Login");
        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
            txt_account.setText("Account");
        }
        registerUser();

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


            Intent intenthome = new Intent(CategoryActiviyt.this, Home.class);
            startActivity(intenthome);
            finish();

        } else if (v.getId() == R.id.ll_recent) {
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

            Intent intent = new Intent(CategoryActiviyt.this,MoreScreen.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.iv_back) {
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            finish();


        }else if (v.getId() == R.id.ll_categorie) {

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

            if (sharpref.getUserID(CategoryActiviyt.this).equals("null")) {

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(CategoryActiviyt.this, LoginActivity.class);
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

                Intent intenthome = new Intent(CategoryActiviyt.this, BookmarkActivity.class);
                startActivity(intenthome);
                finish();
            }

        }
        else if (v.getId() == R.id.imageView2){
            Constant.Check_current_status = false;
            Intent intent = new Intent(CategoryActiviyt.this, SearchLocation.class);
            Log.e("rRRRR",Constant.CatArray.toString());

            intent.putExtra("KEY_SEARCH", 2);
            intent.putStringArrayListExtra("CAT_ARRAY", Constant.CatArray);

            startActivity(intent);
        }else if (v.getId() == R.id.location_search){
            Constant.Check_current_status = true;
            Intent intent = new Intent(CategoryActiviyt.this, SearchLocation.class);
            intent.putExtra("category_screen_flag",12);
            intent.putExtra("KEY_SEARCH",1);
            startActivity(intent);
        }
        else if (v.getId() == R.id.ll_account) {
            btn_account_active.setVisibility(View.VISIBLE);
            btn_account.setVisibility(View.GONE);

            btn_bookmark_active.setVisibility(View.GONE);
            btn_home_acive.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);

            btn_bookmak.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);


            if(sharpref.getUserID(CategoryActiviyt.this).equals("null")){
                Intent intenthome = new Intent(CategoryActiviyt.this, LoginActivity.class);
                startActivity(intenthome);
                finish();

            }else {
                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= true;

                Intent intent = new Intent(CategoryActiviyt.this, MyAccount.class);
                startActivity(intent);
                finish();
            }
        }
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
                mProgressDialog = new ProgressDialog(CategoryActiviyt.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String result = ruc.sendGetRequest(AppConstants.BASEURL + "getcat_subCat");
                    Log.e("Register:anilapi", result);
                    return result;
                }
                catch (Exception e){
                    Log.e("Category Activity ","Error : "+e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null)
                {
                    try{
                        JSONObject jsonObj = new JSONObject(s);
                        Prefrence.setPrefValue(getApplicationContext(), "LoginCheck", Boolean.TRUE, Prefrence.PREF_DATA_TYPE.BOOLEAN);

                        JSONArray jsonSOSArray = jsonObj.getJSONArray("category");
                        Log.e("CATPARSE:category",jsonSOSArray.toString());
                        for (int i = 0; i < jsonSOSArray.length(); i++) {
                            HashMap<String, String> map = new HashMap<String, String>();

                            JSONObject jsonobject = jsonSOSArray.getJSONObject(i);
                            catmodel wp = new catmodel(jsonobject.getString("name"),jsonobject.getString("image_80"),jsonobject.getString("id"));
                            arraylist.add(wp);
                        }
                    } catch (JSONException e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

                    adapter = new RecyclerAdapter(getApplicationContext(), arraylist);

                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActiviyt.this));
                    mProgressDialog.dismiss();
                }else {
                    mProgressDialog.dismiss();
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute("", "", "", "", "", "");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CategoryActiviyt.this, Home.class);
        startActivity(intent);
        finish();
    }

    public static CategoryActiviyt getInstance(){
        return instance;
    }
}
