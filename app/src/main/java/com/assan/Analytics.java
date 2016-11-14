package com.assan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import DAO.CompanyListDAO;
import DTO.CompanyListDTO;
import UtilApi.RemotRequest;
import Utils.Constant;
import Utils.Prefrence;
import adapter.CompanyListAdapter;

public class Analytics extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    CompanyListDTO companyListDTO = null;
    int analytics_flag = 8;
    ImageView search,imageview_cross,backicn,btn_home_acive,  btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account;
    Prefrence sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytics);

        sharePref = new Prefrence();
        init();

        GetStoreList(sharePref.getUserID(Analytics.this));
    }

    public void init(){

        listView = (ListView)findViewById(R.id.listview);
        backicn = (ImageView) findViewById(R.id.backicn);
        search = (ImageView) findViewById(R.id.search);

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
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    private void GetStoreList(String userId) {

        class SearchCatAsyntask extends AsyncTask<String, Void, CompanyListDTO> {
            ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Analytics.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected CompanyListDTO doInBackground(String... params) {

                CompanyListDAO companyDao = new CompanyListDAO();

                companyListDTO = companyDao.getCompanyList(params[0]);

                return companyListDTO;
            }
            @Override
            protected void onPostExecute(CompanyListDTO s) {
                super.onPostExecute(s);
                if(s !=null) {


                    if (s.getCompList().size()>0) {
                        CompanyListAdapter adapter = new CompanyListAdapter(Analytics.this, s.getCompList(),analytics_flag);
                        listView.setAdapter(adapter);
                    }
                    mProgressDialog.dismiss();

                }
            }
        }

        SearchCatAsyntask ru = new SearchCatAsyntask();
        ru.execute(userId);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.backicn:
                finish();
                break;
            case R.id.search:
                Intent intent = new Intent(Analytics.this, SearchLocation.class);
                intent.putExtra("KEY_SEARCH", 2);
                intent.putStringArrayListExtra("CAT_ARRAY", Constant.CatArray);
                startActivity(intent);
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

                Intent intenthome = new Intent(Analytics.this, Home.class);
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

                Intent intent2 = new Intent(Analytics.this, MoreScreen.class);
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

                Intent intcategory = new Intent(Analytics.this, CategoryActiviyt.class);
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

                if (sharePref.getUserID(Analytics.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView) dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(Analytics.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(Analytics.this, BookmarkActivity.class);
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

                if (sharePref.getUserID(Analytics.this).equals("null")) {

                    Intent intent_account = new Intent(Analytics.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status = true;

                    Intent intent6 = new Intent(Analytics.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;
        }
    }
}
