package com.assan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import DAO.StatiscticsDAO;

import DTO.stattisticsModel;
import ImageLoding.Loader;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.ImageLoader;

import Utils.Prefrence;
import adapter.StatisticsAdapter;

/**
 * Created by Sonu Saini on 4/22/2016.
 */
public class Statisctics extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account,iv_back;
    TextView txt_name,txt_badge,txt_location, myprofilelbl, txt_home_active, totalpnt, txt_recent_active, txt_categorie, txt_categorie_active, txt_boobkmark, txt_boobkmark_active, txt_account, txt_account_active;
    ImageView btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
   // ImageView iv_back;
    AppSession appSession;
    stattisticsModel stattisticsDTO=null;
    String name="",point="",count="",total="";
    ProgressDialog mProgressDialog;
    ImageLoader imageloder;
    String status;
    Loader loader;
    Context context;
    ListView listView;
    Prefrence sharpref;
    StatisticsAdapter sadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stattistics);

        appSession = new AppSession(getApplicationContext());
        txt_name=(TextView)findViewById(R.id.txt_name);
        txt_badge=(TextView)findViewById(R.id.txt_badge);
        txt_location=(TextView)findViewById(R.id.txt_location);

        sharpref = new Prefrence();

        CircleImageView user_image=(CircleImageView)findViewById(R.id.iv_profile);
        if (appSession.getUserAddress().equals("")){
            txt_location.setText("Please add address from edit profile");
        }else {
            txt_location.setText(appSession.getUserAddress());
        }


       // txt_badge.setText(appSession.getBadge());
        txt_name.setText(appSession.getUserNmae());


        iv_back = (LinearLayout) findViewById(R.id.iv_back);
        listView = (ListView) findViewById(R.id.listView);

        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
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
        myprofilelbl = (TextView) findViewById(R.id.myprofilelbl);
        totalpnt = (TextView) findViewById(R.id.totalpnt);


        //---------------------------------textview-----------------------------------------


        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        imageloder=new ImageLoader(getApplicationContext());
       // imageloder.DisplayImage(appSession.getUserImage(), user_image);
        imageloder.DisplayImage(sharpref.getUserImg(Statisctics.this), user_image);

        stattistcs_points();
        txt_location.setTypeface(setfont("OpenSans-Regular.ttf"));
        totalpnt.setTypeface(setfont("OpenSans-Bold.ttf"));
        txt_name.setTypeface(setfont("OpenSans-Bold.ttf"));
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


            Intent intenthome = new Intent(Statisctics.this, Home.class);
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

            Intent intenthome = new Intent(Statisctics.this, RecentList.class);
            startActivity(intenthome);
            finish();
        }
        else if (v.getId() == R.id.iv_back) {
            Intent intent=new Intent(getApplicationContext(),MyAccount.class);
            startActivity(intent);
            finish();


        }else if (v.getId() == R.id.ll_categorie) {
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

            Intent intent=new Intent(getApplicationContext(),CategoryActiviyt.class);
            startActivity(intent);
            finish();

        } else if (v.getId() == R.id.ll_bookmark) {
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Category_Status = false;
            Constant.TabLayout_Bookmark_Status = true;
            Constant.TabLayout_Resent_Status = false;
            Constant.TabLayout_Accounts_Status= false;

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

            Intent intent = new Intent(Statisctics.this, BookmarkActivity.class);
            startActivity(intent);


        } else if (v.getId() == R.id.ll_account) {
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Category_Status = false;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = false;
            Constant.TabLayout_Accounts_Status= true;

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
            if (appSession.getUserloginStatus() != null) {

                //    CommonUtils.showToast(getApplicationContext(),appSession.getUserloginStatus());
                Intent intent = new Intent(Statisctics.this, MyAccount.class);
                startActivity(intent);
                finish();
            } else {
                Intent intenthome = new Intent(Statisctics.this, LoginActivity.class);
                startActivity(intenthome);
                finish();

            }
        }

    }
    //anil atri
    private void stattistcs_points() {
        stattistics(appSession.getUserId(),name,point,count,total);
        //Log.e("Statics","userID"+appSession.getUserId());
    }

    private void stattistics(final String user_id, final String name, String point, String count, String total) {

        class stattisticsAsyntask extends AsyncTask<String, Void, stattisticsModel> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Statisctics.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected stattisticsModel doInBackground(String... params) {
                try {
                    StatiscticsDAO userDAO = new StatiscticsDAO(getApplicationContext());
                    stattisticsDTO = userDAO.getstatiscticsDTO(params[0]);
                    return stattisticsDTO;
                }catch ( Exception e){
                    Log.e("Statisctics ","error "+e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(final stattisticsModel s) {
                super.onPostExecute(s);
                try {
                    if (s != null) {
                        status = stattisticsDTO.getStatus();

                        if (status.equals("1")) {
                            loader = new Loader(context);
                            sadapter = new StatisticsAdapter(getApplicationContext(), s.getAdvertiseDTOs());

                            listView.setAdapter(sadapter);
                            sadapter.notifyDataSetChanged();
                            mProgressDialog.dismiss();
                        } else if (status.equals("0")) {
                            CommonUtils.showToast(getApplicationContext(), "No record found ...");
                            mProgressDialog.dismiss();
                        }

                    } else {
                        CommonUtils.showToast(getApplicationContext(), "Server Exceptions...");
                        mProgressDialog.dismiss();
                    }
                }
                catch ( Exception e){
                    Log.e("Statisctics ","error "+e.getMessage());
                }
            }
        }

        stattisticsAsyntask ru = new stattisticsAsyntask();
        ru.execute(user_id);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }

}
