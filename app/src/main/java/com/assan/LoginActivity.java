package com.assan;

// for fb
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;

import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONTokener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utils.AppConstants;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;
import Utils.RequestHandler;


import android.view.View.OnClickListener;


/**
 * Created by Sonu Saini on 4/12/2016.
 */
public class LoginActivity extends Activity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public boolean expended = true;
    RelativeLayout rl_drop;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account,ll_clickhere,fb_gp_layout,layout_create_account;
    TextView txt_create, txtskipdrop,txt_skip, txt_home, txt_home_active, txt_recent, txt_recent_active, txt_categorie, txt_categorie_active, txt_boobkmark, txt_boobkmark_active, txt_account, txt_account_active;
    ImageView button_googlepluse,button_facebook, btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    ProgressDialog mProgressDialog;
    EditText username, editTextpassword,txt_userEmail;
    AppSession appSession;
    boolean mSignInClicked;
    boolean mIntentInProgress ;
    ConnectionResult mConnectionResult;
    private static final int RC_SIGN_IN = 9001;
    Button btn_login,forgot_password;
    GoogleApiClient mGoogleApiClient;
    View cover;
    int More_Screen_flag = 0;
    String status,userId,name,address,image,badge,email2,email,key,first_name,last_name,id,profile_image, userEmailId,company_id,phoneno;
    CallbackManager callbackManager;
    public LoginButton loginButton;
    Prefrence sharepref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            More_Screen_flag = bundle.getInt("More_Screen_flag");
        }
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.assan",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:is = ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:is = ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        callbackManager = CallbackManager.Factory.create();
        sharepref = new Prefrence();

        appSession = new AppSession(getApplicationContext());
        username = (EditText) findViewById(R.id.txt_username);
        editTextpassword = (EditText) findViewById(R.id.txt_password);
        txt_userEmail = (EditText) findViewById(R.id.txt_userEmail);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
        ll_clickhere = (LinearLayout) findViewById(R.id.ll_clickhere);
        fb_gp_layout = (LinearLayout) findViewById(R.id.fb_gp_layout);
        layout_create_account = (LinearLayout) findViewById(R.id.layout_create_account);
        rl_drop = (RelativeLayout) findViewById(R.id.RL_Drop);
        //--------------------------------------------------------button----------------------
        button_facebook = (ImageView) findViewById(R.id.button_facebook);
        loginButton = (LoginButton) findViewById(R.id.login_button2);
        btn_home = (ImageView) findViewById(R.id.btn_home);
        btn_home_acive = (ImageView) findViewById(R.id.btn_home_active);
        btn_recent = (ImageView) findViewById(R.id.btn_recent);
        btn_recent_active = (ImageView) findViewById(R.id.btn_recent_active);
        btn_categorie = (ImageView) findViewById(R.id.btn_cat);
        btn_categorie_active = (ImageView) findViewById(R.id.btn_cat_active);
        btn_bookmak = (ImageView) findViewById(R.id.btn_bookmark);
        btn_bookmark_active = (ImageView) findViewById(R.id.btn_bookmark_active);
        btn_account = (ImageView) findViewById(R.id.btn_account);
        button_googlepluse = (ImageView) findViewById(R.id.button_googlepluse);
        btn_account_active = (ImageView) findViewById(R.id.btn_account_active);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_create = (TextView) findViewById(R.id.txt_createaccount);
        txtskipdrop = (TextView) findViewById(R.id.txtskipdrop);
        forgot_password= (Button) findViewById(R.id.btn_Send);
        //txt_frogot = (TextView) findViewById(R.id.txt_frogotpassword);
        cover =(View)findViewById(R.id.cover);
        //---------------------------------textview-----------------------------------------
        txt_skip = (TextView) findViewById(R.id.txtskip);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        txt_skip.setOnClickListener(this);
        ll_clickhere.setOnClickListener(this);
        button_facebook.setOnClickListener(this);
        button_googlepluse.setOnClickListener(this);
        txt_create.setOnClickListener(this);
        txtskipdrop.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

        btn_account_active.setVisibility(View.VISIBLE);
        btn_account.setVisibility(View.GONE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        loginButton.setOnClickListener(LoginActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(LoginActivity.this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, new Plus.PlusOptions.Builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();


        loginButton.setReadPermissions("public_profile", "email");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                JSONObject res = response.getJSONObject();

                                try {
                                    Log.e("FACEBOOK RESPONSE" ,"S "+res.toString());
                                    email2 = res.getString("email");
                                    first_name = res.getString("first_name");
                                    last_name = res.getString("last_name");
                                    id = res.getString("id");
                                    //https://graph.facebook.com/USER_ID
                                    profile_image = "https://graph.facebook.com/" + res.getString("id") + "/picture?type=large";
                                    Log.e("Name", first_name + " " + last_name);
                                    Log.e("Email", email2);

                                    RequestHandler ruc = new RequestHandler();
                                    String url_sring = ruc.sendGetRequest(profile_image);
                                    Log.e("url_sring", profile_image);
                                    sharepref.saveUserId(LoginActivity.this,id);
                                    sharepref.saveUserImg(LoginActivity.this,profile_image);

                                    appSession.setUserId(id);
                                    appSession.setUserEmail(email2);
                                    appSession.setUserImage(profile_image);
                                    appSession.setUserName(first_name+" " +last_name);

                                    SocialLogin(id);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Application code
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,first_name,last_name,locale");
                request.setParameters(parameters);
                request.executeAsync();

               // username.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.email_1), null, null, null);
            }

            @Override
            public void onCancel() {
                // App code

                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                String Exp = exception.toString();
                // App code
                Toast.makeText(getApplicationContext(), Exp, Toast.LENGTH_SHORT).show();
            }
        });

        cover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              //  if (expended) {
                    rl_drop.animate().setDuration(200).translationY(-300);
                    expended = true;
                  //  return true;
                //} else {
                //}
                return false;
            }
        });

        if (More_Screen_flag == 7){
            fb_gp_layout.setVisibility(View.GONE);
            layout_create_account.setVisibility(View.GONE);
        }
        txt_skip.setTypeface(setfont("OpenSans-Regular.ttf"));
        btn_login.setTypeface(setfont("OpenSans-Bold.ttf"));
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ll_home) {

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

            Intent intenthome = new Intent(LoginActivity.this, Home.class);
            startActivity(intenthome);
            finish();

        } else if (v.getId() == R.id.ll_recent) {
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

            Intent intent = new Intent(LoginActivity.this,MoreScreen.class);
            startActivity(intent);

        } else if (v.getId() == R.id.ll_categorie) {
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

            //Toast.makeText(getApplicationContext(), "Please First Login, to see your Categories", Toast.LENGTH_LONG).show();
            Intent intcat=new Intent(LoginActivity.this,CategoryActiviyt.class);
            startActivity(intcat);
            finish();

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

            if(sharepref.getUserID(LoginActivity.this).equals("null")) {

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intenthome);
                       // finish();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            else
            { Intent intenthome = new Intent(LoginActivity.this, BookmarkActivity.class);
                startActivity(intenthome);
                finish();
            }
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

        } else if (v.getId() == R.id.btn_login) {

            if ((username.getText().toString() != "") || (editTextpassword.getText().toString() != "")) {

                if (isEmailValid(username.getText().toString())) {

                    Userlogin();

                } else {
                    Toast.makeText(getApplicationContext(), "PLease enter valid username", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(getApplicationContext(), "PLease enter username and passowrd", Toast.LENGTH_SHORT).show();
            }


        } else if (v.getId() == R.id.txtskip) {
            //Log.e("Login ","home ");
            Intent intent = new Intent(LoginActivity.this, Home.class);
            startActivity(intent);
            finish();
            //click here forgot password
        } else if (v.getId() == R.id.ll_clickhere) {
            if(expended){
                expended = false;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    rl_drop.animate().setDuration(200).translationY(rl_drop.getMeasuredHeight()); //rl_drop.getMeasuredHeight()
                }
                else {
                    rl_drop.animate().setDuration(200).translationY(rl_drop.getMeasuredHeight());
                }
            }

            else {
                rl_drop.animate().setDuration(200).translationY(-300);
                expended = true;
            }

        } else if (v.getId() == R.id.txt_createaccount) {
            Intent intent = new Intent(LoginActivity.this, Registration.class);
            startActivity(intent);
            finish();

        }

        else if (v.getId() == R.id.button_facebook) {
            loginButton.performClick();
        }
        else if (v.getId() == R.id.button_googlepluse) {
            signInWithGplus();
        }  else if (v.getId() == R.id.txtskipdrop) {
            rl_drop.animate().setDuration(200).translationY(-300);
            expended = true;
        } else if (v.getId() == R.id.btn_Send) {
            userEmailId = txt_userEmail.getText().toString();

            if (userEmailId.equals("") || userEmailId.equals(null))
            Toast.makeText(getApplicationContext(), "PLease enter Email", Toast.LENGTH_SHORT).show();

            else
                forgotpassword(userEmailId);
        }
    }

    protected void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

        System.out.println("ON SIGN IN BTN CLICK");
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void Userlogin() {
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String password = editTextpassword.getText().toString().trim().toLowerCase();
        String email = username.getText().toString().trim().toLowerCase();
        Log.e("Password:",password);

        UserLogin(email, password, "2", android_id, "" +".1");
    }

    private void UserLogin(final String email, String password, String deviceType, String deviceId, String appVersion) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("email", params[0]);
                data.put("password", params[1]);
                data.put("deviceType",params[2]);
                data.put("deviceId", params[3]);
                data.put("appVersion", params[4]);
                String result = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.USERLOGIN, data);
                Log.v("Register LoginActivity ", result);
                Log.v("Request_Anil", data.toString());
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(result !=null)
                {

                    Log.e("login data",result);
                    mProgressDialog.dismiss();
                    try{

                        Object object = new JSONTokener(result).nextValue();
                        if (object instanceof JSONObject) {
                            JSONObject jsonObject = new JSONObject(result);
                            status=jsonObject.getString("status");
                            if (jsonObject.has("result")) {

                                Prefrence.setPrefValue(getApplicationContext(), "LoginCheck", Boolean.TRUE,Prefrence.PREF_DATA_TYPE.BOOLEAN);
                                JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                                Log.e("login result "," d "+jsonObject2.toString());
                                if (jsonObject2.has("user_id")){
                                    userId= jsonObject2.getString("user_id");
                                    sharepref.saveUserType(LoginActivity.this,jsonObject2.getString("user_type"));
                                }
                                if (jsonObject2.has("name"))
                                    name= jsonObject2.getString("name");
                               /*if(jsonObject2.has("email"))
                                   email=jsonObject2.getString("email");*/
                                if (jsonObject2.has("badge"))
                                    badge= jsonObject2.getString("badge");
                                if (jsonObject2.has("address"))
                                    address= jsonObject2.getString("address");
                                if (jsonObject2.has("profile_img"))
                                    image= jsonObject2.getString("profile_img");
                                if (jsonObject2.has("phone_no"))
                                    phoneno= jsonObject2.getString("phone_no");

                            }

                        }

                        if (status.equals("1")){

                            sharepref.saveUserId(LoginActivity.this,userId);
                            sharepref.saveUserImg(LoginActivity.this,image);

                            appSession.setUserId(userId);
                            appSession.setUserName(name);
                            appSession.setUserEmail(email);
                            appSession.setUserAddress(address);
                            appSession.setUserImage(image);

                            appSession.setPhoneNo(phoneno);
                            Log.e("inside","login email "+appSession.getUserEmail());
                            appSession.setBadge(badge);

                            Intent intent=new Intent(LoginActivity.this,MyAccount.class);
                            startActivity(intent);
                            finish();
                            mProgressDialog.dismiss();

                            CommonUtils.showToast(getApplicationContext(), "User successfully Login");
                        }else if (status.equals("0")){
                            CommonUtils.showToast(getApplicationContext(),"User Alreday Exist...");
                            mProgressDialog.dismiss();
                        }


//                        JSONObject jsonObj = new JSONObject(s);
//                       status=jsonObj.getString("status");
//                       if (status.equals("1")){
//                           Intent intent=new Intent(LoginActivity.this,Home.class);
//                           startActivity(intent);
//                           finish();
//                          appSession.setUserloginStatus(status);
//                          mProgressDialog.dismiss();
//                           CommonUtils.showToast(getApplicationContext(), "User successfully registred");
//                       }else if (status.equals("0")){
//                           CommonUtils.showToast(getApplicationContext(),"User Alreday Exist...");
//                           mProgressDialog.dismiss();
//                       }

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


                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }


        }

        RegisterUser ru = new RegisterUser();
        ru.execute(email, password, deviceType, deviceId, appVersion);
    }

    // fb ASYNC
    private void SocialLogin(String socialid) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("socialid", params[0]);
                String result = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.USERLOGIN, data);
                Log.v("lOGINREQUEST", result);
                Log.v("Request_Anil", data.toString());
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result !=null)
                {
                    Log.e("Social login data Post",result);
                    mProgressDialog.dismiss();
                    try{

                        Object object = new JSONTokener(result).nextValue();
                        if (object instanceof JSONObject) {
                            JSONObject jsonObj = new JSONObject(result);
                            status = jsonObj.getString("status");
                            if (status.equals("1")) {
                                Intent intent = new Intent(LoginActivity.this, MyAccount.class);
                                startActivity(intent);
                                finish();
                                appSession.setUserloginStatus(status);
                                mProgressDialog.dismiss();
                                CommonUtils.showToast(getApplicationContext(), "User successfully registred");
                            } else if (status.equals("2")) {
                                Intent intent = new Intent(LoginActivity.this, Registration.class);
                                intent.putExtra("name",first_name+" "+last_name);
                                intent.putExtra("email",email2);
                                intent.putExtra("image",profile_image);
                                intent.putExtra("id",id);
                                startActivity(intent);
                                finish();
                                appSession.setUserloginStatus(status);
                                mProgressDialog.dismiss();
                                CommonUtils.showToast(getApplicationContext(), "Please set the password");
                            }

                        }

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


                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }


        }

        RegisterUser ru = new RegisterUser();
        ru.execute(socialid);
    }

    //forgot password async class
 private void forgotpassword(String emailid) {
        class Forgot_Password extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("email", params[0]);
                String result = ruc.sendPostRequest(AppConstants.BASEURL+"forgotPassword", data);
                Log.v("lOGINREQUEST eee", result);
                Log.v("Request_Anil ee", data.toString());
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                String status = "";

                if(result !=null)
                {
                    try {
                        JSONObject obj = new JSONObject(result);
                        status = obj.getString("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("1")) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Forgot your password successfully", Toast.LENGTH_LONG).show();
                        Log.e("Email is valiid or not",result);
                        userEmailId = "";
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Form not submit", Toast.LENGTH_LONG).show();
                    }


                }else {
                    CommonUtils.showToast(getApplicationContext(),"Server Exceptions...");
                    mProgressDialog.dismiss();
                }


                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }


        }

     Forgot_Password ru = new Forgot_Password();
        ru.execute(emailid);
    }

    //anil atri
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }

        System.out.println("ON RESOLVE SIGN IN ERROR");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.e(String.valueOf(requestCode),String.valueOf(resultCode));
        if (requestCode == 9001) {
            System.out.println("ON ACTIVITY RESULT G+");

            if (resultCode !=  this.RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }

        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            System.out.println("ON ACTIVITY RESULT FB");
        }
    }
    public void logout(){
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        // Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
        // Get user's information
        getProfileInformation();

        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
        System.out.println("ON CONNECTED");
    }
    private void getProfileInformation() {
        int PROFILE_PIC_SIZE = 400;
        try {
            // CommonUtils.showToast(getApplicationContext(),getResources().getString(R.string.msg_login_success));
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                first_name= currentPerson.getDisplayName();
                key= "GlId";
                // personPhotoUrl = currentPerson.getImage().getUrl();
//                String personGooglePlusProfile = currentPerson.getUrl();
                email2= Plus.AccountApi.getAccountName(mGoogleApiClient);
                id = currentPerson.getId();
                Log.e("gmail_social_name",first_name);
                Log.e("gmail_email",email2);
//                Log.e("gmail_social_Url",personGooglePlusProfile);
                Log.e("gmail_id",id);
                SocialLogin(id);
                Toast.makeText(this, "login successfully", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(this, "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        System.out.println("ON SUSPENDED");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
        System.out.println("ON CONNECTION FAILED");
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

        System.out.println("ON START");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        System.out.println("ON STOP");
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }
}
