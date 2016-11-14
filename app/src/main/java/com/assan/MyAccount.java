package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import Utils.AlertDialogManager;
import Utils.Constant;
import Utils.Prefrence;

import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import ImageLoding.Loader;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.RequestHandler;
import Utils.ImageLoader;

/**
 * Created by Sonu Saini on 4/21/2016.
 */
public class MyAccount extends Activity implements View.OnClickListener{

    ImageView iv_editaccount,btn_home_acive,btn_home,btn_recent,btn_recent_active,btn_categorie,btn_categorie_active,btn_bookmak,btn_bookmark_active,btn_account,btn_account_active;
    LinearLayout  ll_home,ll_recent,ll_categorie,ll_boobmark,ll_account,manageaccount,listorstore,ll_BADGE,ll_mng_cmpny,ll_mng_store,ll_analystics,ll_contact_admin,main_txt_skip;
    Typeface font_bold,font_light,font_reguler;
    TextView txt1,txt_address,text_review,txt_bookmark,txt_point,txt_b_count,txt_r_count,txt_p_count,txt_user_name,text_badge,text_logout;
    Button btn_login;
    RelativeLayout ll_eview,rl_stattistic,rl_boolmark,rl_pro_pic;
    String status,msg,name,badge,user_image,review_count,bookmark_count,point_count,username,email,company_id;
    EditText edt_name,edt_mail;
    CircleImageView USER_IMAGE;
    ProgressDialog mProgressDialog;
    Loader imageloading;
    ImageLoader load;
    Utils.Boldtext managecompany;
    Utils.LightText txt_account;
    ImageLoader imageloder;
    AppSession appSession;
    AlertDialogManager dialogManager;
    Prefrence sharePhref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        sharePhref = new Prefrence();

        intit();
        registerUser();
        dialogManager = new AlertDialogManager(MyAccount.this);
        imageloading=new Loader(getApplicationContext());

    }
    public  void intit(){
        appSession=new AppSession(MyAccount.this);
       // Utils.RoundedImageView UI=(Utils.RoundedImageView)findViewById(R.id.iv_my_pro_pic);
        USER_IMAGE=(CircleImageView)findViewById(R.id.iv_my_pro_pic);
        text_badge=(TextView)findViewById(R.id.txt_begnner);
        txt_user_name=(TextView)findViewById(R.id.txt_name);
        text_review=(TextView)findViewById(R.id.text_review);
       // txt_bookmark=(TextView)findViewById(R.id.txt_book_mark);
        txt_point=(TextView)findViewById(R.id.txt_statistics);
        txt_r_count=(TextView)findViewById(R.id.txt_review_count);
        txt_b_count=(TextView)findViewById(R.id.txt_bookmark_count);
        txt_p_count=(TextView)findViewById(R.id.txt_ststics_count);
        txt_address=(TextView)findViewById(R.id.txt_account_location);
        text_logout=(TextView)findViewById(R.id.my_profile_logout);
        txt_account=( Utils.LightText)findViewById(R.id.txt_account);
        managecompany=( Utils.Boldtext)findViewById(R.id.managecompany);
        managecompany.setText("MANAGE COMPANY & STORES");
        txt1=(TextView)findViewById(R.id.txt1);

        if (appSession.getUserAddress().equals("")){
            txt_address.setText("Please add address from edit profile");
        }else {
            txt_address.setText(appSession.getUserAddress());
        }

        //-------------------imageview------------------
        // --
        iv_editaccount=(ImageView)findViewById(R.id.iv_edit);
        ll_home=(LinearLayout)findViewById(R.id.ll_home);
        ll_recent=(LinearLayout)findViewById(R.id.ll_recent);
        ll_categorie=(LinearLayout)findViewById(R.id.ll_categorie);
        ll_boobmark=(LinearLayout)findViewById(R.id.ll_bookmark);
        ll_account=(LinearLayout)findViewById(R.id.ll_account);
        manageaccount=(LinearLayout)findViewById(R.id.manageaccount);
        listorstore=(LinearLayout)findViewById(R.id.listorstore);
        ll_BADGE=(LinearLayout)findViewById(R.id.ll);
        ll_mng_cmpny=(LinearLayout)findViewById(R.id.ll_mng_cmpny);
        ll_mng_store=(LinearLayout)findViewById(R.id.ll_mng_store);
        ll_analystics=(LinearLayout)findViewById(R.id.ll_analystics);
        ll_contact_admin=(LinearLayout)findViewById(R.id.ll_contact_admin);
        main_txt_skip=(LinearLayout)findViewById(R.id.main_txt_skip);
        ll_eview=(RelativeLayout)findViewById(R.id.rl_review);
        rl_stattistic=(RelativeLayout)findViewById(R.id.rl_stattistic);
        rl_boolmark=(RelativeLayout)findViewById(R.id.rl_bookmak);
        rl_pro_pic=(RelativeLayout)findViewById(R.id.rl_pro_pic);
        //--------------------------------------------------------button---------------------------------------------

        btn_home=(ImageView)findViewById(R.id.btn_home);
        btn_home_acive=(ImageView)findViewById(R.id.btn_home_active);
        btn_recent=(ImageView)findViewById(R.id.btn_recent);
        btn_recent_active=(ImageView)findViewById(R.id.btn_recent_active);
        btn_categorie=(ImageView)findViewById(R.id.btn_cat);
        btn_categorie_active=(ImageView)findViewById(R.id.btn_cat_active);
        btn_bookmak=(ImageView)findViewById(R.id.btn_bookmark);
        btn_bookmark_active=(ImageView)findViewById(R.id.btn_bookmark_active);
        btn_account=(ImageView)findViewById(R.id.btn_account);
        btn_account_active=(ImageView)findViewById(R.id.btn_account_active);
        btn_login=(Button)findViewById(R.id.btn_login);

        imageloder=new ImageLoader(getApplicationContext());

        //imageloder.DisplayImage(appSession.getUserImage(), USER_IMAGE);
        imageloder.DisplayImage(sharePhref.getUserImg(MyAccount.this), USER_IMAGE);
       txt_user_name.setText(appSession.getUserNmae());

        btn_account_active.setVisibility(View.VISIBLE);
        btn_account.setVisibility(View.GONE);

        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        text_logout.setOnClickListener(this);
        ll_eview.setOnClickListener(this);
        rl_boolmark.setOnClickListener(this);
        rl_stattistic.setOnClickListener(this);
        iv_editaccount.setOnClickListener(this);
        ll_mng_cmpny.setOnClickListener(this);
        ll_mng_store.setOnClickListener(this);
        ll_analystics.setOnClickListener(this);
        ll_contact_admin.setOnClickListener(this);
        main_txt_skip.setOnClickListener(this);

        txt1.setTypeface(setfont("OpenSans-Regular.ttf"));
        txt_r_count.setTypeface(setfont("OpenSans-Regular.ttf"));

        if (Constant.TabLayout_Accounts_Status == true){
            txt_account.setTextColor(getResources().getColor(R.color.lightred));
        }
        else {
            txt_account.setTextColor(getResources().getColor(R.color.lightred));
        }

        if (sharePhref.getUserType(MyAccount.this).equals("3")) {
            manageaccount.setVisibility(View.VISIBLE);
            listorstore.setVisibility(View.GONE);
            txt_address.setVisibility(View.GONE);
            ll_BADGE.setVisibility(View.GONE);
            iv_editaccount.setVisibility(View.GONE);
            rl_pro_pic.setVisibility(View.GONE);
        }
        else{
            listorstore.setVisibility(View.VISIBLE);
            manageaccount.setVisibility(View.GONE);
            txt_address.setVisibility(View.VISIBLE);
            ll_BADGE.setVisibility(View.VISIBLE);
            iv_editaccount.setVisibility(View.VISIBLE);
            rl_pro_pic.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ll_home){
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
            Intent intenthome=new Intent(MyAccount.this,Home.class);
            startActivity(intenthome);
            finish();

        }else if(v.getId()==R.id.ll_recent){
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

            Intent intenthome=new Intent(MyAccount.this,MoreScreen.class);
            startActivity(intenthome);
            finish();

        }else if(v.getId()==R.id.ll_categorie){
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

            Intent intenthome=new Intent(MyAccount.this,CategoryActiviyt.class);
            startActivity(intenthome);
            finish();

        }else if(v.getId()==R.id.ll_bookmark){
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

            if (sharePhref.getUserID(MyAccount.this).equals("null")) {

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(MyAccount.this, LoginActivity.class);
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

                Intent intenthome = new Intent(MyAccount.this, BookmarkActivity.class);
                startActivity(intenthome);
                finish();
            }
        }else if(v.getId()==R.id.ll_account){

            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Category_Status = false;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = false;
            Constant.TabLayout_Accounts_Status= true;

            btn_bookmark_active.setVisibility(View.GONE);
            btn_home_acive.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);

            btn_bookmak.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);

        }else if(v.getId()==R.id.my_profile_logout){
            Prefrence sharPref = new Prefrence();
            sharPref.clearSharedPreference(MyAccount.this);
            sharPref.removeValue(MyAccount.this);
            sharPref.clearImgprefrence(MyAccount.this);
            sharPref.removeImgprefrence(MyAccount.this);
            sharPref.clearUserType(MyAccount.this);
            sharPref.removeValueUserType(MyAccount.this);

            LoginActivity lgactivity = new LoginActivity();
            lgactivity.logout();

            if (sharPref.getUserID(MyAccount.this).equals("null")){
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
        }else if(v.getId()==R.id.iv_back){
            finish();
           // Intent intent=new Intent(MyAccount.this,Home.class);
           // startActivity(intent);


        }
        else if(v.getId()==R.id.rl_review){

            Intent intent=new Intent(MyAccount.this,MyReview.class);
            startActivity(intent);


        }else if(v.getId()==R.id.rl_bookmak){

            Intent intent=new Intent(MyAccount.this,BookmarkActivity.class);
            startActivity(intent);


        }
        else if(v.getId()==R.id.rl_stattistic){

            Intent intent=new Intent(MyAccount.this,Statisctics.class);
            startActivity(intent);


        }else if(v.getId()==R.id.iv_edit){

            Intent intent=new Intent(MyAccount.this,EditAccount.class);
            Bundle b=new Bundle();
            b.putString("userName",appSession.getUserNmae());
            b.putString("userEmail",appSession.getUserEmail());

            //Log.e("NextActivity","MyAccout"+appSession.getUserNmae());
            // b.putString("email",email);
            intent.putExtras(b);

            startActivity(intent);
        }
        else if(v.getId() ==R.id.ll_mng_cmpny){
            Intent intent=new Intent(MyAccount.this,CompanyList.class);
            startActivity(intent);
        }
        else if(v.getId() ==R.id.ll_analystics){
            Intent intent=new Intent(MyAccount.this,Analytics.class);
            intent.putExtra("my_account_activity_add_store_flag",6);
            startActivity(intent);
        }
        else if(v.getId() == R.id.ll_mng_store){
            dialogManager.DialogBox("Coming Soon");
        }
        else if(v.getId() == R.id.ll_contact_admin){
            Intent intent=new Intent(MyAccount.this,ContactAdmin.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.main_txt_skip){
            Constant.Check_current_status = false;
            Intent intent = new Intent(MyAccount.this, SearchLocation.class);
            intent.putExtra("KEY_SEARCH", 2);
            intent.putStringArrayListExtra("CAT_ARRAY", Constant.CatArray);
            startActivity(intent);
        }
    }


    private void registerUser() {
        register(appSession.getUserId(), "", "", "2", "", "");
    }

    private void register(String userId, String email, String password, String deviceType, String deviceId, String appVersion) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler ruc = new RequestHandler();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(MyAccount.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(true);

                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                if(s !=null)
                {


                    Log.e("login data",s);
                    mProgressDialog.dismiss();
                    try{

                        Object object = new JSONTokener(s).nextValue();
                        if (object instanceof JSONObject) {
                            JSONObject jsonObject = new JSONObject(s);
                            status=jsonObject.getString("status");
                            if (jsonObject.has("result")) {
                                JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                                if (jsonObject2.has("name"))
                                    name= jsonObject2.getString("name");
                                   // txt_user_name.setText(name);
                                if (jsonObject2.has("review_count"))
                                    review_count= jsonObject2.getString("review_count");
                                txt_r_count.setText(review_count);
                                if (jsonObject2.has("statistics_count"))
                                    point_count= jsonObject2.getString("statistics_count");
                                txt_p_count.setText(point_count);
                                if (jsonObject2.has("bookmark_count"))
                                    bookmark_count= jsonObject2.getString("bookmark_count");
                                txt_b_count.setText(bookmark_count);
                                if (jsonObject2.has("badge"))
                                    badge= jsonObject2.getString("badge");
                                   text_badge.setText(badge);
                                if (jsonObject2.has("profile_img"));

                                if (jsonObject2.has("company_id"))
                                    company_id= jsonObject2.getString("company_id");
                                appSession.setCompany_id(company_id);
                                  //  user_image= jsonObject2.getString("profile_img");
                              //  Toast.makeText(getApplicationContext(),user_image,Toast.LENGTH_SHORT).show();


                               // imageloading.displayImage(user_image, USER_IMAGE);

                            }

                        }

                      // load=new ImageLoader(getApplicationContext());
                     //  load.DisplayImage(user_image,USER_IMAGE);

//

                    } catch (JSONException e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }else {
                    CommonUtils.showToast(getApplicationContext(),"Server Exceptions...");
                    mProgressDialog.dismiss();
                }

                //261 education 2

                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("userId", params[0]);
                data.put("email", params[1]);
                data.put("password", params[2]);
                data.put("deviceType", params[3]);
                data.put("deviceId", params[4]);
                data.put("appVersion", params[5]);


                String result = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.MYACCOUNT,data);
                Log.e("Register:login","name" + result);
                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(userId, "", "", "", "", "");
    }


    public void logout()
    {
        Prefrence.removeAllPrefValue(getApplicationContext());
        //appSession.clearPrefr(getApplicationContext());
        Log.e("logout:anil","user_id");
       // editor.clear();
       // editor.commit();
        //appSession.clearPrefr("user_id",status);


    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }
}
