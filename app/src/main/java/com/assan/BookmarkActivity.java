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
import adapter.Headerapdater;
import adapter.SearchCategoriesAdapter;
import adapter.SearchHVAdapter;
import Utils.Prefrence;

/**
 * Created by Sonu Saini on 4/15/2016.
 */
public class BookmarkActivity extends Activity implements View.OnClickListener {
    ProgressDialog mProgressDialog;
    // Declare Variables
    SearchCategoriesDTO searchCategoriesDTO = null;
    List<SearchCategoriesDTO> searchCategoriesDTOList = new ArrayList<>();
    SearchCategoriesAdapter adapter;
    SearchHVAdapter hvadapetr;
    Headerapdater headerapdater;
    String success,catid,subcatid,catname;
    List<SearchCategoriesDTO> arraylist;
    List<searchhvDTO> hvarraylist;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account,iv_back;
    TextView txt_name,txt_badge,txt_location, bookinglbl, txt_recent, txt_recent_active, txt_categorie, txt_categorie_active, txt_boobkmark, txt_boobkmark_active, txt_account_active;
    ImageView btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    AppSession appSession;
    ImageLoader imageloder;
    private ListView listView;
    Utils.LightText txt_bookmark,txt_account;
    Prefrence sharpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_activity);

        sharpref = new Prefrence();
        appSession = new AppSession(getApplicationContext());

        listView = (ListView) findViewById(R.id.listView);
        iv_back=(LinearLayout)findViewById(R.id.iv_back);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
        txt_name=(TextView)findViewById(R.id.txt_name);
        txt_badge=(TextView)findViewById(R.id.txt_badge);
        txt_location=(TextView)findViewById(R.id.txt_location);
        bookinglbl=(TextView)findViewById(R.id.bookinglbl);

        CircleImageView user_image=(CircleImageView)findViewById(R.id.iv_profile);
        if (appSession.getUserAddress().equals("")){
            txt_location.setText("Please add address from edit profile");
        }else {
            txt_location.setText(appSession.getUserAddress());
        }

      // txt_location.setText(appSession.getUserAddress());
        txt_badge.setText(appSession.getBadge());
        txt_name.setText(appSession.getUserNmae());
//Toast.makeText(getApplicationContext(), appSession.getUserNmae(),Toast.LENGTH_SHORT).show();
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
    //    btn_login = (Button) findViewById(R.id.btn_login);
        txt_bookmark = (Utils.LightText) findViewById(R.id.txt_bookmark);
        txt_account = (Utils.LightText) findViewById(R.id.txt_account);


        //---------------------------------textview-----------------------------------------

        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        if (Constant.TabLayout_Bookmark_Status == true) {
            btn_bookmark_active.setVisibility(View.VISIBLE);
            btn_bookmak.setVisibility(View.GONE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.lightred));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
        else if (Constant.TabLayout_Accounts_Status == true){

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.VISIBLE);
            btn_account.setVisibility(View.GONE);
            txt_account.setTextColor(getResources().getColor(R.color.lightred));
        }

        imageloder=new ImageLoader(getApplicationContext());

        imageloder.DisplayImage(sharpref.getUserImg(BookmarkActivity.this),user_image);
        Searchcategories(appSession.getUserId(), "", "1", "dubai", "1");
        Log.e("value","appsession"+appSession.getUserId());

        bookinglbl.setTypeface(setfont("OpenSans-Bold.ttf"));
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


            Intent intenthome = new Intent(BookmarkActivity.this, Home.class);
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

            Intent intent = new Intent(BookmarkActivity.this,MoreScreen.class);
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


            Intent intenthome = new Intent(BookmarkActivity.this, CategoryActiviyt.class);
            startActivity(intenthome);
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

            Intent intenthome = new Intent(BookmarkActivity.this, BookmarkActivity.class);
            startActivity(intenthome);
            finish();

        } else if (v.getId() == R.id.ll_account) {

            btn_bookmark_active.setVisibility(View.GONE);
            btn_home_acive.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);

            btn_bookmak.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);

            if(sharpref.getUserID(BookmarkActivity.this).equals("null")){
                Intent intenthome = new Intent(BookmarkActivity.this, LoginActivity.class);
                startActivity(intenthome);
                finish();

            }else {
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= true;

                Intent intent = new Intent(BookmarkActivity.this, MyAccount.class);
                startActivity(intent);
                finish();
            }


        } else if (v.getId() == R.id.iv_back) {
                Intent intent=new Intent(getApplicationContext(),MyAccount.class);
                startActivity(intent);
                finish();
            }
//        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookmarkActivity.this, Home.class);
        startActivity(intent);
        finish();


    }

    private void Searchcategories(String cat_id, String sub_cat_id, String location, String page, String userId) {
        class SearchCatAsyntask extends AsyncTask<String, Void, SearchCategoriesDTO> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(BookmarkActivity.this);
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
                    //    searchCategoriesDTO=userDAO.getbookmakoutlet(params[0], params[1], params[2], params[3], params[4]);
                    searchCategoriesDTOList = userDAO.getbookmakoutlet(params[0], params[1], params[2], params[3], params[4]);
                    return searchCategoriesDTO;
                }catch ( Exception e){
                    Log.e("BookmarkActivity ","error "+e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(SearchCategoriesDTO s) {
                super.onPostExecute(s);
                try {
                    adapter = new SearchCategoriesAdapter(BookmarkActivity.this, searchCategoriesDTOList);
                    listView.setAdapter(adapter);
                    mProgressDialog.dismiss();

                    if (s != null) {
                        hvarraylist = new ArrayList<searchhvDTO>();

                        arraylist = new ArrayList<SearchCategoriesDTO>();
                        arraylist.add(s);

                        for (int i = 0; searchCategoriesDTOList.size() > i; i++) {

                            success = searchCategoriesDTOList.get(i).getSuccess();

                        }

                        //success=searchCategoriesDTO.getSuccess();

                        if (success.equals("1")) {

//                        Prefrence.setPrefValue(getApplicationContext(), "LoginCheck", Boolean.TRUE,Prefrence.PREF_DATA_TYPE.BOOLEAN);
                            adapter = new SearchCategoriesAdapter(getApplicationContext(), arraylist);
                            listView.setAdapter(adapter);

                            mProgressDialog.dismiss();

                        } else if (success.equals("0")) {
                            CommonUtils.showToast(getApplicationContext(), "No record found ...");
                            mProgressDialog.dismiss();
                        }

                    } else {
                        // CommonUtils.showToast(getApplicationContext(), "Server Exceptions...");
                        mProgressDialog.dismiss();
                    }
                }
                catch ( Exception e){
                    Log.e("BookmarkActivity ","error "+e.getMessage());
                }
            }
        }

        SearchCatAsyntask ru = new SearchCatAsyntask();
        ru.execute(cat_id, sub_cat_id, location, page, userId);
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }
}
