package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Sonu Saini on 4/22/2016.
 */
public class EditAccount extends Activity implements View.OnClickListener{
    private ImageView iv_cross,iv_upload,iv_user_image,imageview_cross;
    EditText edt_name,edt_mail;
    Button btn_login;
    String username,email;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        edt_name=(EditText)findViewById(R.id.edt_name);
        edt_mail=(EditText)findViewById(R.id.edt_mail);

        imageview_cross=(ImageView)findViewById(R.id.imageview_cross);

        iv_upload=(ImageView)findViewById(R.id.iv_upload);
        iv_user_image=(ImageView)findViewById(R.id.iv_upload_VIEW);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        iv_upload.setOnClickListener(this);
        imageview_cross.setOnClickListener(this);
        //anil atri get text value from registration page

        Bundle b= getIntent().getExtras();
        if(b!=null) {
            username= b.getString("userName");
            email= b.getString("userEmail");
            // email= b.getString("email");

            //Log.e("next","username"+username);

        }
        edt_name.setText(username);
        edt_mail.setText(email);
       // edt_mail.setText(email);
        //finish();
        //edt_mail.setText(email);

        /*Intent intedit =new Intent(this,EditAccount.class);
        startActivity(intedit);
       */// finish();
        //Log.e("username:register",usernameString.toString());

       /* edt_txt.setText(username);
        edt_mail.setText(email);*/
    }
    @Override
    public void onClick(View v) {
        if (v == iv_upload) {
            //  iv_upload.setVisibility(View.GONE);
            //   iv_user_image.setVisibility(View.VISIBLE);
            selectImage();
        }
//        }else if(v == iv_user_image){
//            iv_upload.setVisibility(View.GONE);
//            selectImage();
//
//        }
        if(v==imageview_cross)
        {
           Intent cross=new Intent(getApplicationContext(),MyAccount.class);
            startActivity(cross);
            finish();
        }
        if(v==btn_login)
        {
            Log.e("Click ","me");
            Intent btnsave=new Intent(getApplicationContext(),MyAccount.class);
            startActivity(btnsave);
            finish();
        }

    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(EditAccount.this);
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
