package com.assan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import Utils.Constant;
import Utils.Prefrence;

public class WebViewScreen extends AppCompatActivity implements View.OnClickListener {

    public WebView webView;
    String title;
    int aboutaasaan_flag = 0,privacy = 0,term_condition = 0,fb = 0,instagram = 0,twitter = 0,playstore=0;
    public Utils.Boldtext setTitle;
    ImageView imageview_cross,iv_upload,btn_home_acive,  btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account,backicn;
    Prefrence sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_screen);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            aboutaasaan_flag = bundle.getInt("about_aasaan");
            privacy = bundle.getInt("privacy");
            term_condition = bundle.getInt("term_condition");
            fb = bundle.getInt("facebook");
            instagram = bundle.getInt("instagram");
            twitter = bundle.getInt("twitter");
            playstore = bundle.getInt("playstore");
        }

        sharePref = new Prefrence();
        init();

        if (aboutaasaan_flag == 1) {
            setTitle.setText("About Aasaan");
            webView.loadUrl("http://www.aasaan.ae/webservice/getPages/about-us");

            //webView.setWebViewClient(new WebViewClient());
            //webView.loadUrl("http://www.videovibe.co/platform/login");
        }
        if (privacy == 2) {
            setTitle.setText("Privacy Policy");
            webView.loadUrl("http://www.aasaan.ae/webservice/getPages/privacy");
        }
        if (term_condition == 3) {
            setTitle.setText("Terms of License and Use ");
            webView.loadUrl("http://www.aasaan.ae/webservice/getPages/terms-conditions");
        }
        if (fb == 4) {

            Uri uri = Uri.parse("https://www.facebook.com/aasaanapp");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        }
        if (instagram == 5) {
            Uri uri = Uri.parse("https://www.instagram.com/aasaan_app/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        }
        if (twitter == 6) {
            Uri uri = Uri.parse("https://twitter.com/Aasaan_app");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        }
        if (playstore == 7) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.assan");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        }

        backicn.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
    }

    public void init(){
        backicn = (LinearLayout) findViewById(R.id.backicn);
        setTitle = (Utils.Boldtext)findViewById(R.id.title);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

                Intent intenthome = new Intent(WebViewScreen.this, Home.class);
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

                Intent intent = new Intent(WebViewScreen.this, MoreScreen.class);
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

                Intent intcategory = new Intent(WebViewScreen.this, CategoryActiviyt.class);
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

                if (sharePref.getUserID(WebViewScreen.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView) dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(WebViewScreen.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(WebViewScreen.this, BookmarkActivity.class);
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

                if (sharePref.getUserID(WebViewScreen.this).equals("null")) {

                    Intent intent_account = new Intent(WebViewScreen.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status = true;

                    Intent intent6 = new Intent(WebViewScreen.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;
            case R.id.backicn:
                    finish();
                break;
        }
    }
}
