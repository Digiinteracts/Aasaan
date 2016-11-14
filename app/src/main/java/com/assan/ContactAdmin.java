package com.assan;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Utils.AlertDialogManager;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.CommonUtils;

public class ContactAdmin extends AppCompatActivity implements View.OnClickListener {

    LinearLayout imageview_cross;
    Button btn_save;
    EditText edt_subject,message_edt;
    AppSession appSession;
    String subject="",message="",message1="";
    private AlertDialogManager dialogbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_admin);
        dialogbox = new AlertDialogManager(ContactAdmin.this);
        appSession = new AppSession(ContactAdmin.this);
        init();
    }

    public void init(){
        imageview_cross = (LinearLayout)findViewById(R.id.imageview_cross);
        edt_subject = (EditText) findViewById(R.id.edt_subject);
        message_edt = (EditText) findViewById(R.id.message_edt);
        btn_save = (Button) findViewById(R.id.btn_save);

        imageview_cross.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imageview_cross:
                finish();
                break;

            case R.id.btn_save:
                 subject = edt_subject.getText().toString();
                 message1 = message_edt.getText().toString();

                if (subject.equals("") || subject.equals(null) || message1.equals("") || message1.equals(null))
                    valiDate();

                else
                    SubmitForm(subject,message1);

                break;
        }
    }

    public void valiDate(){
        if (subject.equals("") || subject.equals(null)) {
            message = ("Enter: Subject");
            dialogbox.DialogBox(message);
            message = "";

        } else if (message1.equals("") || message1.equals(null)) {
            message = ("Enter: Message");
            dialogbox.DialogBox(message);
            message = "";
        }
    }

    private void SubmitForm(final String subject, final String msg) {
        class Submit extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(ContactAdmin.this);
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
                data.put("fullname", appSession.getUserNmae());
                data.put("email_id", appSession.getUserEmail());
                data.put("phone_no", appSession.getPhoneNo());
                data.put("subject", subject);
                data.put("message", msg);
                data.put("business_with_type", "2");

                String result= ruc.sendPostRequest(AppConstants.BASEURL+"contactus", data);
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
        ru.execute(subject,msg);
    }
}
