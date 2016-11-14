package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DAO.ReviewDAO;
import DTO.OutletDTO;
import ImageLoding.Loader;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;
import adapter.Headerapdater;
import adapter.ReviewListAdapter;
import adapter.SearchHVAdapter;

/**
 * Created by Sonu Saini on 4/22/2016.
 */
public class Review extends Activity implements View.OnClickListener{

    TextView txt_name,txt_location;
    ImageView  iv_profile,btn_home_acive,btn_home_active, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    Utils.LightText txt_home,txt_category,txt_recents,txt_bookmark,txt_account;
    AppSession appSession;
    String store_id = "";
    String name_store = "";
    String store_location = "";
    String store_image = "";
    ProgressDialog mProgressDialog;
    // Declare Variables
    OutletDTO searchCategoriesDTO=null;
    ReviewListAdapter adapter;
    LinearLayout iv_back;
    LinearLayout ll_home,today_ll, ll_recent, ll_categorie, ll_boobmark, ll_account,call_btn_layout,map_btn_layout,rating_btn,like_btn,review_layout;
    SearchHVAdapter hvadapetr;
    Headerapdater headerapdater;
    String success,catid,subcatid,catname;
    ListView listView;
    List<OutletDTO> arraylist;
//    List<searchhvDTO> hvarraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        Loader loader = new Loader(getApplicationContext());

        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);

        btn_account=(ImageView)findViewById(R.id.btn_account);
        btn_account_active=(ImageView)findViewById(R.id.btn_account_active);
        btn_recent=(ImageView)findViewById(R.id.btn_recent);
        btn_recent_active=(ImageView)findViewById(R.id.btn_recent_active);
        btn_bookmak=(ImageView)findViewById(R.id.btn_bookmark);
        btn_bookmark_active=(ImageView)findViewById(R.id.btn_bookmark_active);
        btn_home=(ImageView)findViewById(R.id.btn_home);
        btn_home_acive=(ImageView)findViewById(R.id.btn_home_active);
        btn_categorie=(ImageView)findViewById(R.id.btn_cat);
        btn_categorie_active=(ImageView)findViewById(R.id.btn_cat_active);

        txt_home = (Utils.LightText) findViewById(R.id.txt_home);
        txt_category = (Utils.LightText) findViewById(R.id.txt_category);
        txt_recents = (Utils.LightText) findViewById(R.id.txt_recents);
        txt_bookmark = (Utils.LightText) findViewById(R.id.txt_bookmark);
        txt_account = (Utils.LightText) findViewById(R.id.txt_account);

        listView = (ListView)findViewById(R.id.listView);
       txt_location = (TextView)findViewById(R.id.txt_location);
       txt_name = (TextView)findViewById(R.id.txt_name);
       iv_profile = (ImageView) findViewById(R.id.iv_profile);
        iv_back = (LinearLayout)findViewById(R.id.iv_back);

        Bundle inte = getIntent().getExtras();
        if(inte != null) {
            store_id = inte.getString("OUTlET_ID");
            store_location = inte.getString("OUTlET_location");
            store_image = inte.getString("OUTlET_image");
            name_store = inte.getString("OUTlET_NAME");
        }
        txt_location.setText(store_location);
        txt_name.setText(name_store);
        loader.displayImage(store_image,iv_profile);
        ReviewList(store_id);
        appSession = new AppSession(getApplicationContext());

        iv_back.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);

        TabLayout();
    }

    public void TabLayout(){
        if (Constant.TabLayout_Home_Status == true){
            btn_home_acive.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.GONE);
            txt_home.setTextColor(getResources().getColor(R.color.lightred));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));

        }
        else if(Constant.TabLayout_Category_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.GONE);
            txt_category.setTextColor(getResources().getColor(R.color.lightred));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
        else if(Constant.TabLayout_Resent_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.GONE);
            txt_recents.setTextColor(getResources().getColor(R.color.lightred));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));

        } else if(Constant.TabLayout_Bookmark_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.VISIBLE);
            btn_bookmak.setVisibility(View.GONE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.lightred));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
        else if(Constant.TabLayout_Accounts_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.VISIBLE);
            btn_account.setVisibility(View.GONE);
            txt_account.setTextColor(getResources().getColor(R.color.lightred));
        }
    }
    @Override
    public void onClick(View v) {
        Prefrence sharepref = new Prefrence();

        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;

            case R.id.ll_home:
                Constant.TabLayout_Home_Status = true;
                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= false;

                Intent intenthome = new Intent(Review.this, Home.class);
                startActivity(intenthome);
                finish();
                break;

            case R.id.ll_recent:
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = true;
                Constant.TabLayout_Accounts_Status= false;

                Intent intrecent = new Intent(Review.this, RecentList.class);
                startActivity(intrecent);
                finish();
                break;

            case R.id.ll_categorie:
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Category_Status = true;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= false;

                Intent intcategory = new Intent(Review.this, CategoryActiviyt.class);
                startActivity(intcategory);
                finish();

                break;

            case R.id.ll_account:
                if (sharepref.getUserID(Review.this).equals("null")) {

                    Intent intent = new Intent(Review.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent = new Intent(Review.this, MyAccount.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.ll_bookmark:
                if (sharepref.getUserID(Review.this).equals("null")) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    // LayoutInflater inflater = this.getLayoutInflater();
                    alertDialogBuilder.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intenthome = new Intent(Review.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = true;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= false;

                    Intent intbook = new Intent(Review.this, BookmarkActivity.class);
                    startActivity(intbook);
                    finish();
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void ReviewList(String userId) {

        class SearchCatAsyntask extends AsyncTask<String, Void, OutletDTO> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Review.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected OutletDTO doInBackground(String... params) {

                ReviewDAO userDAO=new ReviewDAO(getApplicationContext());

                searchCategoriesDTO=userDAO.getoutletDTO(params[0]);

                return searchCategoriesDTO;
            }
            @Override
            protected void onPostExecute(OutletDTO s) {
                super.onPostExecute(s);
                if(s !=null)
                {
                    arraylist=new ArrayList<OutletDTO>();
                    arraylist.add(s);
                 //   Log.e("getgallary","d "+s.getGallaryDTOs().size());
                    adapter = new ReviewListAdapter(getApplicationContext(), s.getGallaryDTOs());
                    listView.setAdapter(adapter);
                    mProgressDialog.dismiss();

                    }else if (success.equals("0")){
                        CommonUtils.showToast(getApplicationContext(), "No record found ...");
                        mProgressDialog.dismiss();
                    }

                }
        }

        SearchCatAsyntask ru = new SearchCatAsyntask();
        ru.execute(userId);
    }
}
