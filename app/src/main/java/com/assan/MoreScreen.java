package com.assan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import Utils.Constant;
import Utils.Prefrence;

public class MoreScreen extends AppCompatActivity implements View.OnClickListener {

    public ImageView backicn,btn_home_acive, search, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    LinearLayout aboutassan_layout,privacypolicy_layout,tert_of_licanee_layout,follow_fb_layout,follow_instagram_layout,
            contactus_layout,follow_twtter_layout,listyourstore_layout, ll_home, ll_recent, ll_categorie, ll_boobmark,
            ll_account, back_btn,rate_assan_layout;
    Utils.LightText listyourstore;
    Prefrence sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_screen);

        sharePref = new Prefrence();
        init();

        if (sharePref.getUserType(MoreScreen.this).equals("3"))
            listyourstore.setText("Manage Account");
        else
            listyourstore.setText("List your Store");

        backicn.setOnClickListener(this);
        aboutassan_layout.setOnClickListener(this);
        privacypolicy_layout.setOnClickListener(this);
        tert_of_licanee_layout.setOnClickListener(this);
        follow_fb_layout.setOnClickListener(this);
        follow_instagram_layout.setOnClickListener(this);
        follow_twtter_layout.setOnClickListener(this);
        listyourstore_layout.setOnClickListener(this);
        contactus_layout.setOnClickListener(this);
        rate_assan_layout.setOnClickListener(this);

        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    public void init(){
        backicn = (ImageView)findViewById(R.id.backicn);
        search = (ImageView)findViewById(R.id.search);

        aboutassan_layout = (LinearLayout)findViewById(R.id.aboutassan_layout);
        privacypolicy_layout = (LinearLayout)findViewById(R.id.privacypolicy_layout);
        tert_of_licanee_layout = (LinearLayout)findViewById(R.id.tert_of_licanee_layout);
        follow_fb_layout = (LinearLayout)findViewById(R.id.follow_fb_layout);
        follow_instagram_layout = (LinearLayout)findViewById(R.id.follow_instagram_layout);
        follow_twtter_layout = (LinearLayout)findViewById(R.id.follow_twtter_layout);
        contactus_layout = (LinearLayout)findViewById(R.id.contactus_layout);
        listyourstore_layout = (LinearLayout)findViewById(R.id.listyourstore_layout);
        rate_assan_layout = (LinearLayout)findViewById(R.id.rate_assan_layout);
        listyourstore = (Utils.LightText)findViewById(R.id.listyourstore);

        //tablayout=============================
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

        switch (v.getId()){
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

                Intent intenthome = new Intent(MoreScreen.this, Home.class);
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

                //Intent intent = new Intent(MoreScreen.this, MoreScreen.class);
                //startActivity(intent);
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

                Intent intcategory = new Intent(MoreScreen.this, CategoryActiviyt.class);
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

                if (sharePref.getUserID(MoreScreen.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView)dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(MoreScreen.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(MoreScreen.this, BookmarkActivity.class);
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

                if (sharePref.getUserID(MoreScreen.this).equals("null")) {

                    Intent intent_account = new Intent(MoreScreen.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent6 = new Intent(MoreScreen.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;

            case R.id.backicn:
                finish();
                break;

            case R.id.search:
                Intent int_search = new Intent(MoreScreen.this, SearchLocation.class);
                int_search.putExtra("KEY_SEARCH", 2);
                int_search.putStringArrayListExtra("CAT_ARRAY", Constant.CatArray);
                startActivity(int_search);
                break;

            case R.id.aboutassan_layout:
                Intent intent_about = new Intent(MoreScreen.this,WebViewScreen.class);
                intent_about.putExtra("about_aasaan",1);
                startActivity(intent_about);
                break;

            case R.id.privacypolicy_layout:
                Intent intent2 = new Intent(MoreScreen.this,WebViewScreen.class);
                intent2.putExtra("privacy",2);
                startActivity(intent2);
                break;

            case R.id.tert_of_licanee_layout:
                Intent intent3 = new Intent(MoreScreen.this,WebViewScreen.class);
                intent3.putExtra("term_condition",3);
                startActivity(intent3);
                break;

            case R.id.follow_fb_layout:
                Intent intent4 = new Intent(MoreScreen.this,WebViewScreen.class);
                intent4.putExtra("facebook",4);
                startActivity(intent4);
                break;

            case R.id.follow_instagram_layout:
                Intent intent5 = new Intent(MoreScreen.this,WebViewScreen.class);
                intent5.putExtra("instagram",5);
                startActivity(intent5);
                break;

            case R.id.follow_twtter_layout:
                Intent intent6 = new Intent(MoreScreen.this,WebViewScreen.class);
                intent6.putExtra("twitter",6);
                startActivity(intent6);
                break;

            case R.id.contactus_layout:
                Intent intent7 = new Intent(MoreScreen.this,ContactUs.class);
                startActivity(intent7);
                break;

            case R.id.rate_assan_layout:
                Intent intent9 = new Intent(MoreScreen.this,WebViewScreen.class);
                intent9.putExtra("playstore",7);
                startActivity(intent9);
                break;

            case R.id.listyourstore_layout:
                if (sharePref.getUserType(MoreScreen.this).equals("3")) {
                    Intent intent8 = new Intent(MoreScreen.this, MyAccount.class);
                    startActivity(intent8);
                }
                else {
                    DialogBox();
                }

                break;
        }
    }

    public void DialogBox(){
        final Dialog dialog = new Dialog(MoreScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filter_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView title = (TextView)dialog.findViewById(R.id.title);
        View view1 = (View) dialog.findViewById(R.id.view1);
        view1.setVisibility(View.GONE);
        title.setVisibility(View.GONE);

        TextView Login = (TextView)dialog.findViewById(R.id.nearby);
        Login.setText("Login");
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent8 = new Intent(MoreScreen.this, LoginActivity.class);
                intent8.putExtra("More_Screen_flag",7);
                startActivity(intent8);
                dialog.dismiss();
            }
        });

        TextView SingUp = (TextView)dialog.findViewById(R.id.ratingby);
        SingUp.setText("Sign up");

        SingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent8 = new Intent(MoreScreen.this, RegisterStoreOwner.class);
                startActivity(intent8);
                dialog.dismiss();
            }
        });


        TextView close = (TextView)dialog.findViewById(R.id.clearall);
        close.setText("Close");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
}
