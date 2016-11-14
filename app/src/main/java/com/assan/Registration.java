package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import DAO.RegistrationDAO;
import DTO.RegistrationDTO;
import UtilApi.RemotRequest;
import Utils.AlertDialogManager;
import Utils.AppSession;
import Utils.CommonUtils;

/**
 * Created by Sonu Saini on 4/14/2016.
 */
public class Registration extends Activity implements View.OnClickListener {
RegistrationDTO userDTO=null;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private ImageView iv_cross,iv_user_image;
    private String status;
    private TextView txt_signin;
    ProgressDialog mProgressDialog;
    private Button buttonRegister;
    String android_id;
    AppSession appSession;
    String getName,getEmail,getId="",getKey,getImage;
    private AlertDialogManager alert;
    CircleImageView iv_upload;
    String user_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        txt_signin=(TextView)findViewById(R.id.txt_signin);
        txt_signin=(TextView)findViewById(R.id.txtlogin_account);
        editTextUsername = (EditText) findViewById(R.id.txt_username);
        editTextPassword = (EditText) findViewById(R.id.txt_password);
        editTextEmail = (EditText) findViewById(R.id.txt_email);
        iv_cross=(ImageView)findViewById(R.id.imageview_cross);
      //  iv_upload=(ImageView)findViewById(R.id.iv_upload);
        iv_upload=(CircleImageView)findViewById(R.id.iv_upload);
        buttonRegister = (Button) findViewById(R.id.btn_login);
        buttonRegister.setOnClickListener(this);
        txt_signin.setOnClickListener(this);
        iv_cross.setOnClickListener(this);
        iv_upload.setOnClickListener(this);
        Intent intent = getIntent();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
                getName = intent.getStringExtra("name");
                getEmail = intent.getStringExtra("email");
                getId = intent.getStringExtra("id");
//                getKey = intent.getStringExtra("key");
                getImage = intent.getStringExtra("image");
                if (!getName.equals(null)) {
                    editTextUsername.setText(getName);
                    editTextEmail.setText(getEmail);

            }
        }
        Log.e("INTENT DATA",getName+getEmail+getId+getImage);
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        appSession=new AppSession(getApplicationContext());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //alert = new AlertDialogManager();
        //appSession.clearPrefr("status",status);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {

                registerUser();

        }else if(v==iv_cross){

            Intent intent=new Intent(Registration.this,LoginActivity.class);
            startActivity(intent);
            finish();


        }else if(v==txt_signin){

            Intent intent=new Intent(Registration.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }else if(v==iv_upload){

            iv_upload.setImageDrawable(null) ;
            selectImage();

        }
    }




    private void registerUser() {
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();

       // Log.e("output",username);
        //anil atri
       /* Intent i = new Intent(this, EditAccount.class);
        Bundle b=new Bundle();
        b.putString("username",username);
        b.putString("email",email);
        i.putExtras(b);
       // Log.e("username","sz"+username);
        //Log.e("email","dz@z.com"+email);
        *//*i.putExtra("txt_name", username);
        *//**//*Log.e("output:",username);*//**//*
        i.putExtra("txt_email",email);*//*
        startActivity(i);
*/
        // for Normal Registration
        //register(username, email, password, "2", android_id, "2.1","");
       // for FB
        register(username, email, password, "2", android_id, "2.1",getId,getImage);
    /*   // for G+
        register(username, email, password, "2", android_id, "2.1","");*/
    }

    private void register(final String name, String email, String password, String deviceType, String deviceId, String appVersion, String FbId,String getImage) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Registration.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                RegistrationDAO userDAO=new RegistrationDAO(getApplicationContext());
                userDTO=userDAO.registration(params[0], params[1], params[2], params[3], params[4], params[5], params[6],params[7]);
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(userDTO !=null)
                {
                    status=userDTO.getStatus();
                    if (status.equals("1")){
                           Intent intent=new Intent(Registration.this,Home.class);
                           startActivity(intent);
                           finish();
                           appSession.setUserloginStatus(status);
                           mProgressDialog.dismiss();

                           CommonUtils.showToast(getApplicationContext(), "User successfully registred");
                        /*//anil  atri


                        Intent intmy=new Intent(getApplicationContext(),MyAccount.class);
                        startActivity(intmy);
                        finish();
*/
                       }else if (status.equals("0")){
                           CommonUtils.showToast(getApplicationContext(),"User Alreday Exist...");
                           mProgressDialog.dismiss();
                       }

                }else {
                    CommonUtils.showToast(getApplicationContext(),"Server Exceptions...");
                    mProgressDialog.dismiss();
                }


               // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }


        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, email, password, deviceType, deviceId, appVersion,FbId,getImage);
    }

//---------------------select image--------------------------

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
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

        iv_upload.setImageBitmap(bm);
    }

}
