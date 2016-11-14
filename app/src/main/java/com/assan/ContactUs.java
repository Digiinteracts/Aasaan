package com.assan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Utils.AlertDialogManager;
import Utils.AppConstants;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;

public class ContactUs extends AppCompatActivity implements View.OnClickListener {

    EditText edt_name,edt_mail,edt_phonno,edt_subject,edit_message;
    Button btn_send;
    String name,email,phonno,address,data,message = "";
    ImageView imageview_cross,iv_upload,btn_home_acive,  btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    LinearLayout  ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account;
    Prefrence sharePref;
    private AlertDialogManager dialogbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        dialogbox = new AlertDialogManager(ContactUs.this);
        sharePref = new Prefrence();
        init();

        btn_send.setOnClickListener(this);
        imageview_cross.setOnClickListener(this);

        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
    }

    public void init(){
        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_mail = (EditText)findViewById(R.id.edt_mail);
        edt_phonno = (EditText)findViewById(R.id.edt_phonno);
        edt_subject = (EditText)findViewById(R.id.edit_subject);
        edit_message = (EditText)findViewById(R.id.edt_message);

        imageview_cross= (ImageView)findViewById(R.id.imageview_cross);
        btn_send = (Button)findViewById(R.id.btn_send);

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

                Intent intenthome = new Intent(ContactUs.this, Home.class);
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

                Intent intent = new Intent(ContactUs.this, MoreScreen.class);
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

                Intent intcategory = new Intent(ContactUs.this, CategoryActiviyt.class);
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

                if (sharePref.getUserID(ContactUs.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView)dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(ContactUs.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(ContactUs.this, BookmarkActivity.class);
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

                if (sharePref.getUserID(ContactUs.this).equals("null")) {

                    Intent intent_account = new Intent(ContactUs.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent6 = new Intent(ContactUs.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;
            case R.id.btn_send:
                name = edt_name.getText().toString();
                email = edt_mail.getText().toString();
                phonno = edt_phonno.getText().toString();
                address = edt_subject.getText().toString();
                data = edit_message.getText().toString();

                if (name.equals("") || name.equals(null) || email.equals("") || email.equals(null) || phonno.equals("") || phonno.equals(null)
                        || address.equals("") || address.equals(null) || data.equals("") || data.equals(null))
                    valiDate();

                else
                    SubmitForm(name, email, phonno, address, data);

                break;

            case R.id.imageview_cross:
                finish();
                break;
        }
    }

    public void valiDate(){
        if (name == null || name.equals("")) {
            message = ("Enter: Name");
            dialogbox.DialogBox(message);
            message = "";

        } else if (email == null || email.equals("")) {
            message = ("Enter: Email");
            dialogbox.DialogBox(message);
            message = "";
        } else if (phonno == null || phonno.equals("")) {
            message = ("Enter: Contact no");
            dialogbox.DialogBox(message);
            message = "";
        } else if (address == null || address.equals("")) {
            message = ("Enter: Subject");
            dialogbox.DialogBox(message);
            message = "";

        } else if (data == null || data.equals("")) {
            message = ("Enter: Message");
            dialogbox.DialogBox(message);
            message = "";
        }
    }

   /* public void DialogBox(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContactUs.this);
        alertDialog.setMessage(message);
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }*/

    private void SubmitForm(String name,String email,String phonno,String address,String data) {
        class Submit extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(ContactUs.this);
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
                data.put("fullname", params[0]);
                data.put("email_id", params[1]);
                data.put("phone_no", params[2]);
                data.put("subject", params[3]);
                data.put("message", params[4]);
                String result = ruc.sendPostRequest(AppConstants.BASEURL+"contactus", data);
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
                    mProgressDialog.dismiss();

                    try {
                        JSONObject obj = new JSONObject(result);
                        status = obj.getString("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("1")) {
                        Toast.makeText(getApplicationContext(), "Form submit successfully", Toast.LENGTH_LONG).show();
                        Log.e("form submit or not ", result);
                        finish();
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

        Submit ru = new Submit();
        ru.execute(name,email,phonno,address,data);
    }

}
