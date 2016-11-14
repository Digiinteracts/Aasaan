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
import android.widget.Toast;

import java.util.HashMap;

import DAO.CompanyListDAO;
import DAO.ReviewDAO;
import DTO.CompanyListDTO;
import DTO.OutletDTO;
import UtilApi.RemotRequest;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.Constant;
import Utils.Prefrence;
import adapter.CompanyListAdapter;

public class CompanyList extends AppCompatActivity implements View.OnClickListener {

    ListView listview;
    Prefrence sharePref;
    CompanyListAdapter adapter;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark,
            ll_account, back_btn, search_catagri,rl_filter;
    ImageView backicn,search,btn_home_acive,  btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    TextView addnewstore,companylisttxt,titaltxt,statustxt,actiontxt,companyname,emailtxt,companyedit,companytitle;
    CompanyListDTO companyListDTO = null;
    int store_flag=0,companylist_flag=0;
    AppSession appSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_list);

        appSession = new AppSession(CompanyList.this);

        init();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            store_flag = bundle.getInt("my_account_activity_add_store_flag");
            if (store_flag == 6){
             /*   companylisttxt.setText("Company List");
                titaltxt.setText("Store name");
                emailtxt.setText("Subscription");
                statustxt.setText("Status");
                actiontxt.setText("Action");*/
            }
        }

        sharePref = new Prefrence();

        addnewstore.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        backicn.setOnClickListener(this);
        companyedit.setOnClickListener(this);
        search.setOnClickListener(this);
        GetCompanyData(sharePref.getUserID(CompanyList.this));
    }

    public void init(){
        listview = (ListView)findViewById(R.id.listview);
        addnewstore = (TextView)findViewById(R.id.addnewstore);
        companylisttxt = (TextView)findViewById(R.id.companylist);
        titaltxt = (TextView)findViewById(R.id.tital);
        emailtxt = (TextView)findViewById(R.id.email);
        statustxt = (TextView)findViewById(R.id.status);
        actiontxt = (TextView)findViewById(R.id.action);

        companyname = (TextView)findViewById(R.id.titaltxt);
        emailtxt = (TextView)findViewById(R.id.emailtxt);
        companyedit = (TextView)findViewById(R.id.companyedit);
        companytitle = (TextView)findViewById(R.id.companytitle);

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
        backicn = (ImageView) findViewById(R.id.backicn);
        search = (ImageView) findViewById(R.id.search);
    }

    private void GetCompanyData(String userId) {

        class SearchCatAsyntask extends AsyncTask<String, Void, CompanyListDTO> {
            ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(CompanyList.this);
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
                    Log.e("inside","login email1 "+appSession.getUserEmail());
                    companyname.setText(appSession.getUserNmae());
                    companytitle.setText(appSession.getUserNmae());
                    emailtxt.setText(appSession.getUserEmail());

                    if (s.getCompList().size()>0) {
                        adapter = new CompanyListAdapter(CompanyList.this, s.getCompList(),companylist_flag);
                        listview.setAdapter(adapter);
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
            case R.id.addnewstore:
                    Intent intent1 = new Intent(CompanyList.this, AddNewStore.class);
                    startActivityForResult(intent1,101);
                break;

            case R.id.search:
                Intent intent2 = new Intent(CompanyList.this, SearchLocation.class);
                intent2.putExtra("KEY_SEARCH", 2);
                intent2.putStringArrayListExtra("CAT_ARRAY", Constant.CatArray);
                startActivity(intent2);
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

                Intent intenthome = new Intent(CompanyList.this, Home.class);
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

                Intent intent = new Intent(CompanyList.this, MoreScreen.class);
                startActivity(intent);
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

                Intent intcategory = new Intent(CompanyList.this, CategoryActiviyt.class);
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

                if (sharePref.getUserID(CompanyList.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView)dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(CompanyList.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(CompanyList.this, BookmarkActivity.class);
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

                if (sharePref.getUserID(CompanyList.this).equals("null")) {

                    Intent intent_account = new Intent(CompanyList.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent6 = new Intent(CompanyList.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;
            
            case R.id.backicn:
                finish();
                break;

            case R.id.companyedit:
                Intent intent6 = new Intent(CompanyList.this, AddNewCompany.class);
                intent6.putExtra("company_List_flag",7);
                intent6.putExtra("id",sharePref.getUserID(CompanyList.this));
                startActivity(intent6);
              //  finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101){
            if ( data != null) {
                GetCompanyData(sharePref.getUserID(CompanyList.this));
            }
        }

    }
}
