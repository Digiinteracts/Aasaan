package com.assan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DAO.SearchCategoriesDAO;
import DTO.SearchCategoriesDTO;
import DTO.searchhvDTO;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.ImageLoader;
import Utils.Prefrence;
import adapter.Headerapdater;
import adapter.ReviewOutletAdapetr;
import adapter.SearchHVAdapter;

/**
 * Created by Sonu Saini on 4/22/2016.
 */
public class MyReview extends Activity implements View.OnClickListener{

    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account,iv_back;
    TextView txt_name,txt_badge,txt_location, reviewlbl, txt_home_active, myprofilelbl, txt_recent_active, txt_categorie, txt_categorie_active, txt_boobkmark, txt_boobkmark_active, txt_account, txt_account_active;
    ImageView btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;

    AppSession appSession;
    ImageLoader imageloder;
    ProgressDialog mProgressDialog;
    // Declare Variables
    SearchCategoriesDTO searchCategoriesDTO=null;
    ReviewOutletAdapetr adapter;

    SearchHVAdapter hvadapetr;
    Headerapdater headerapdater;
    String success,catid,subcatid,catname;
    ListView listView;
    List<SearchCategoriesDTO> arraylist;
    List<searchhvDTO> hvarraylist;
    Prefrence sharpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreview);

        appSession = new AppSession(getApplicationContext());
        sharpref = new Prefrence();

        iv_back = (LinearLayout) findViewById(R.id.iv_back);
        listView = (ListView) findViewById(R.id.listView);

        txt_name=(TextView)findViewById(R.id.txt_name);
        reviewlbl=(TextView)findViewById(R.id.reviewlbl);
        txt_badge=(TextView)findViewById(R.id.txt_badge);
        txt_location=(TextView)findViewById(R.id.txt_location);


        CircleImageView user_image=(CircleImageView)findViewById(R.id.iv_profile);
        if (appSession.getUserAddress().equals("")){
            txt_location.setText("Please add address from edit profile");
        }else {
            txt_location.setText(appSession.getUserAddress());
        }

        // txt_location.setText(appSession.getUserAddress());
        txt_badge.setText(appSession.getBadge());
        txt_name.setText(appSession.getUserNmae());
        imageloder=new ImageLoader(getApplicationContext());

       // imageloder.DisplayImage(appSession.getUserImage(), user_image);
        imageloder.DisplayImage(sharpref.getUserImg(MyReview.this), user_image);


        Searchcategories(sharpref.getUserID(MyReview.this));

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


        //---------------------------------textview-----------------------------------------


        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        myprofilelbl.setTypeface(setfont("OpenSans-Regular.ttf"));
        txt_name.setTypeface(setfont("OpenSans-Bold.ttf"));
        reviewlbl.setTypeface(setfont("OpenSans-Bold.ttf"));
        txt_location.setTypeface(setfont("OpenSans-Regular.ttf"));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            Intent intent = new Intent(MyReview.this, MyAccount.class);
            startActivity(intent);
            finish();
        }

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


            Intent intenthome = new Intent(MyReview.this, Home.class);
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

            Intent intent=new Intent(getApplicationContext(),RecentList.class);
            startActivity(intent);
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
            Intent intent = new Intent(MyReview.this, BookmarkActivity.class);
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
                Intent intent = new Intent(MyReview.this, MyAccount.class);
                startActivity(intent);
                finish();
            } else {
                Intent intenthome = new Intent(MyReview.this, LoginActivity.class);
                startActivity(intenthome);
                finish();
                ;
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyReview.this, Home.class);
        startActivity(intent);
        finish();
    }

    private void Searchcategories(String userId) {
        class SearchCatAsyntask extends AsyncTask<String, Void, SearchCategoriesDTO> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(MyReview.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected SearchCategoriesDTO doInBackground(String... params) {

                try {
                    SearchCategoriesDAO userDAO = new SearchCategoriesDAO(getApplicationContext());
                    searchCategoriesDTO = userDAO.getReview(params[0]);

                    return searchCategoriesDTO;
                }
                catch (Exception e){
                    Log.e("Review","error "+e.getMessage());
                }
                return null;
                }
            @Override
            protected void onPostExecute(SearchCategoriesDTO s) {
                super.onPostExecute(s);
                try {
                    if (s != null) {
                        searchhvDTO sh = new searchhvDTO();
                        hvarraylist = new ArrayList<searchhvDTO>();

                        arraylist = new ArrayList<SearchCategoriesDTO>();
                        arraylist.add(s);
                        success = searchCategoriesDTO.getSuccess();

                        if (success.equals("1")) {

                            adapter = new ReviewOutletAdapetr(MyReview.this, s.getReviewLiset());

                            listView.setAdapter(adapter);

                            mProgressDialog.dismiss();

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
                    Log.e("MyReview","ERROR "+e.getMessage());
                }
            }
        }

        SearchCatAsyntask ru = new SearchCatAsyntask();
        ru.execute(userId);
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }
}
