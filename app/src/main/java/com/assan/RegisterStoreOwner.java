package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import Utils.AlertDialogManager;
import Utils.AppConstants;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;

public class RegisterStoreOwner extends AppCompatActivity implements View.OnClickListener {

    EditText edt_mail,edt_pass,edt_cname,edit_cp_name,edt_conf_pass,edt_contact_person_no;
    Button btn_send;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark,
            ll_account, back_btn, search_catagri,rl_filter;
    String password,email,companyname,personname,message = "",android_id,getImage="",conform_pass="",contactpersonno="";
    ImageView imageview_cross,iv_upload,btn_home_acive,  btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Prefrence sharePref;
    private AlertDialogManager dialogbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store_owner);

        sharePref = new Prefrence();
        dialogbox = new AlertDialogManager(RegisterStoreOwner.this);

        init();

        btn_send.setOnClickListener(this);
        imageview_cross.setOnClickListener(this);
        iv_upload.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public void init(){
        edt_pass = (EditText)findViewById(R.id.edt_pass);
        edt_mail = (EditText)findViewById(R.id.edt_mail);
        edt_cname = (EditText)findViewById(R.id.edt_cname);
        edit_cp_name = (EditText)findViewById(R.id.edit_cp_name);
        edt_conf_pass = (EditText)findViewById(R.id.edt_conf_pass);
        edt_contact_person_no = (EditText)findViewById(R.id.edt_contact_person_no);

        iv_upload=(CircleImageView)findViewById(R.id.iv_upload);
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

                Intent intenthome = new Intent(RegisterStoreOwner.this, Home.class);
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

                Intent intent = new Intent(RegisterStoreOwner.this, MoreScreen.class);
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

                Intent intcategory = new Intent(RegisterStoreOwner.this, CategoryActiviyt.class);
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

                if (sharePref.getUserID(RegisterStoreOwner.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView)dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(RegisterStoreOwner.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(RegisterStoreOwner.this, BookmarkActivity.class);
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

                if (sharePref.getUserID(RegisterStoreOwner.this).equals("null")) {

                    Intent intent_account = new Intent(RegisterStoreOwner.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent6 = new Intent(RegisterStoreOwner.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;

            case R.id.btn_send:
                password = edt_pass.getText().toString();
                email = edt_mail.getText().toString();
                companyname = edt_cname.getText().toString();
                personname = edit_cp_name.getText().toString();
                conform_pass = edt_conf_pass.getText().toString();
                contactpersonno = edt_contact_person_no.getText().toString();

                if (password.equals("") || password.equals(null) || email.equals("") || email.equals(null) || companyname.equals("") || companyname.equals(null)
                        || personname.equals("") || personname.equals(null) || conform_pass.equals("") || conform_pass.equals(null)
                        || contactpersonno.equals("") || contactpersonno.equals(null))
                    valiDate();
                else {
                    if (password.equals(conform_pass))
                    RegisterStoreData(password, email, companyname, personname, getImage, contactpersonno);
                    else {
                        message = ("Enter: Valid Password");
                        dialogbox.DialogBox(message);
                        message = "";
                    }
                }
                    break;

            case R.id.imageview_cross:
                finish();
                break;

            case R.id.iv_upload:
                selectImage();
                break;

        }
    }

    public void valiDate(){
         if (email == null || email.equals("")) {
            message = ("Enter: Email");
             dialogbox.DialogBox(message);
            message = "";
        }
        else if (password == null || password.equals("")) {
            message = ("Enter: Password");
             dialogbox.DialogBox(message);
            message = "";

        } else if (conform_pass == null || conform_pass.equals("")) {
            message = ("Enter: Confirm Password");
             dialogbox.DialogBox(message);
            message = "";

        }else if (companyname == null || companyname.equals("")) {
            message = ("Enter: Company Name");
             dialogbox.DialogBox(message);
            message = "";
        }else if (contactpersonno == null || contactpersonno.equals("")) {
            message = ("Enter: Contact Person no.");
             dialogbox.DialogBox(message);
            message = "";
        } else if (personname == null || personname.equals("")) {
            message = ("Enter: Contact Person name");
             dialogbox.DialogBox(message);
            message = "";
        }
    }

  /*  public void DialogBox(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterStoreOwner.this);
        alertDialog.setMessage(message);
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }*/

    private void RegisterStoreData(String password,String email,String companyname,String personname,String getImage,String contactpersonno) {
        class Submit extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(RegisterStoreOwner.this);
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
                data.put("password", params[0]);
                data.put("email", params[1]);
                data.put("company_name", params[2]);
                data.put("contact_person", params[3]);
                data.put("profileImage", params[4]);

                data.put("deviceType", params[5]);
                data.put("deviceId", params[6]);
                data.put("appVersion", params[7]);
                data.put("phone_no", params[8]);

                String result = ruc.sendPostRequest(AppConstants.BASEURL+"owner_registration", data);
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
        ru.execute(password,email,companyname,personname,getImage,"2","","2.1",contactpersonno);
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterStoreOwner.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv_upload.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        getImage=Base64.encodeToString(b, Base64.DEFAULT);

        iv_upload.setImageBitmap(bm);
    }

}
