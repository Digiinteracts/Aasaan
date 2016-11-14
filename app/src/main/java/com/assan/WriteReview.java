package com.assan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import DAO.ReviewDAO;
import DTO.OutletDTO;
import ImageLoding.Loader;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.Prefrence;

/**
 * Created by Digi-T25 on 6/23/2016.
 */
public class WriteReview extends Activity  implements View.OnClickListener{
    AppSession appSession;
    String id,username,store_image,name_store,write_review = "",ratingvalue = "",userID,outletID;
    TextView txt_name,txtRatingValue;
    CircleImageView iv_profile;
    EditText writereview;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account;
    ImageView btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active,imageview_cross;
    Button btn_login;
    Prefrence sharpref;
    RatingBar ratingBar;
    OutletDTO searchCategoriesDTO=null;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);
        appSession=new AppSession(getApplicationContext());

        txt_name = (TextView) findViewById(R.id.txt_name);

        btn_account=(ImageView)findViewById(R.id.btn_account);
        iv_profile=(CircleImageView) findViewById(R.id.iv_profile);
        btn_recent=(ImageView)findViewById(R.id.btn_recent);
        btn_bookmak=(ImageView)findViewById(R.id.btn_bookmark);
        btn_home=(ImageView)findViewById(R.id.btn_home);
        btn_categorie=(ImageView)findViewById(R.id.btn_cat);
        imageview_cross=(ImageView)findViewById(R.id.imageview_cross);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
        btn_login=(Button)findViewById(R.id.btn_login);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        writereview = (EditText)findViewById(R.id.WriteReview_edittext);
        txtRatingValue=(TextView)findViewById(R.id.txt_ratingvalue);

        btn_account.setOnClickListener(this);
        btn_bookmak.setOnClickListener(this);
        btn_categorie.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_recent.setOnClickListener(this);
        imageview_cross.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ratingBar.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            outletID = bundle.getString("OUTlET_ID");
            store_image = bundle.getString("OUTlET_image");
            name_store = bundle.getString("OUTlET_NAME");
        }

        txt_name.setText(name_store);
        Loader loader = new Loader(WriteReview.this);
        loader.displayImage(store_image, iv_profile);

        sharpref = new Prefrence();

        if (sharpref.getUserID(WriteReview.this).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.login);
        } else {
            userID = sharpref.getUserID(WriteReview.this);
            btn_account.setBackgroundResource(R.drawable.account_btn);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,boolean fromUser) {
                txtRatingValue.setText(String.valueOf(rating));
                ratingvalue = String.valueOf(rating);
            }
        });

        btn_login.setTypeface(setfont("OpenSans-Bold.ttf"));
    }

    @Override
    public void onClick(View v) {


        if(v==ll_account)
        {
            if (userID.equals("null")) {
                Intent intent = new Intent(WriteReview.this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(WriteReview.this, MyAccount.class);
                startActivity(intent);
                finish();
            }
        }

        else if(v==btn_bookmak)
        {
            Intent inte=new Intent(WriteReview.this,BookmarkActivity.class);
            startActivity(inte);

        }

        else if(v==btn_categorie)
        {
            Intent inte=new Intent(WriteReview.this,CategoryActiviyt.class);
            startActivity(inte);

        }

        else if(v==btn_home)
        {
            Intent inte=new Intent(WriteReview.this,Home.class);
            startActivity(inte);

        }
        else if(v==btn_recent)
        {
            //Intent inte=new Intent(WriteReview.this,LoginActivity.class);
           // startActivity(inte);

        }
        else if(v==imageview_cross)
        {
            finish();
            /*Intent inte=new Intent(WriteReview.this,OutletDetails.class);
            startActivity(inte);*/

        }
        else if(v==btn_login)
        {
            write_review = writereview.getText().toString();
            if (write_review.equals("")||write_review.equals(null) || write_review.isEmpty() || ratingvalue.equals("") || ratingvalue.isEmpty()||ratingvalue.equals(null)){
                Toast.makeText(getApplicationContext(),"Write review And Select Rating",Toast.LENGTH_SHORT).show();
            }
            else {
                ReviewList(userID,outletID,ratingvalue,write_review);
            }
        }
    }

    private void ReviewList(String userId,String outlet_id,String rating,String review) {

        class SearchCatAsyntask extends AsyncTask<String, Void, OutletDTO> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(WriteReview.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected OutletDTO doInBackground(String... params) {

                ReviewDAO userDAO=new ReviewDAO(getApplicationContext());

                searchCategoriesDTO=userDAO.getWriteReviewDTO(params[0],params[1],params[2],params[3]);

                return searchCategoriesDTO;
            }
            @Override
            protected void onPostExecute(OutletDTO s) {
                super.onPostExecute(s);
                if(s !=null) {
                   // if (searchCategoriesDTO.getSuccess().equals("1")) {
                        Toast.makeText(getApplicationContext(), "Your Review has been submitted successfully", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                        finish();
                   // }
                }
            }
        }

        SearchCatAsyntask ru = new SearchCatAsyntask();
        ru.execute(userID,outletID,ratingvalue,write_review);
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }
}
