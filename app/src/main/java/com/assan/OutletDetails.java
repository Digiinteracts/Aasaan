package com.assan;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import DAO.BookmarkDAO;
import DAO.OutletDAO;
import DTO.BookmarkDTO;
import DTO.OutletDTO;
import ImageLoding.Loader;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;
import adapter.DetailGalllary;
import adapter.imageadapter;
import it.sephiroth.android.library.widget.HListView;

/**
 * Created by Digi-Sonu Saini on 4/20/2016.
 */


public class OutletDetails extends Activity implements View.OnClickListener{
    OutletDTO OutletsDTO=null;
    AppSession appSession;
    Loader loader ;
    Context context;
    DetailGalllary gallaryAdapter;
    ProgressDialog mProgressDialog;
    LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6,ll_7,ll_WriteReview,reviews;
    ArrayList<OutletDTO> arrayList = new ArrayList<OutletDTO>();
    RelativeLayout rating_layout;
    String id = "",outletlat,outletlong;
    int SearchCatgeriadapter_flag = 0;
    String name_store = "";
    String name_image = "";
    String locatio_store = "";
    LinearLayout ll_home,today_ll, ll_recent, ll_categorie, ll_bookmark, ll_account,call_btn_layout,map_btn_layout,rating_btn,like_btn,review_layout,iv_outlet_Excelent;
    ImageView  btn_home_acive,btn_home_active, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    TextView txt_show_more,tv_rating,tv_review_type,txt_map,txt_distance,tv_outlet_type,tv_outlet_name,rating_value,full_adress,landmark,ll_currency,homedeliverylbl,ll_creditcard,hours,glylbl,writreviewlbl,
                ratelbl, recentlbl,addresstxt,today_time_ll,mon_time_,tv_like_count,tv_read_all_review,tv_outlet_adress1,tv_reviewer_name,tv_rating_type,tv_review_adress,tv_reviewtime,tv_review_rate,today_time,tuesday,wed_textview,thu_text,fri_Text,sat_txt,sun_text;
    ImageView iv_back,iv_background,iv_outlet_like,bookmark_btn;
    CircleImageView iv_review_image;
    imageadapter imageadpt;
    String success;
    Utils.LightText txt_home,txt_category,txt_recents,txt_bookmark,txt_account;
    RatingBar ratingBar;
    HListView gallery;
    View cover;
    int totle_like;
    Button rating_save_btn;
    Prefrence sharePref;
    public boolean expended = false,like = false,checkbookmark = false;
    String outlet_id,user_id;
    BookmarkDTO bookmarkDAO;
    ScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_detail);
        appSession=new AppSession(getApplicationContext());
        sharePref = new Prefrence();
        intiview();

        txt_map.setOnClickListener(this);
        tv_read_all_review.setOnClickListener(this);
        txt_show_more.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_bookmark.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
      //  btn_recent.setOnClickListener(this);
        ll_WriteReview.setOnClickListener(this);
        reviews.setOnClickListener(this);
        bookmark_btn.setOnClickListener(this);
        call_btn_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneDialogBox();
            }
        });
        map_btn_layout.setOnClickListener(this);
        rating_save_btn.setOnClickListener(this);
        like_btn.setOnClickListener(this);
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rating_value.setText(String.valueOf(ratingBar.getRating()));
                return false;
            }
        });

        rating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating();
            }
        });

        product_det();
    }

    public void intiview(){

        scrollview = (ScrollView)findViewById(R.id.scrollview);

        txt_distance=(TextView)findViewById(R.id.txt_distance);
        txt_map=(TextView)findViewById(R.id.text_map);
        tv_outlet_name = (TextView)findViewById(R.id.tv_outlet_name);
        ratingBar = (RatingBar)findViewById(R.id.ratingbar);
        gallery = (HListView) findViewById(R.id.gallery1);
        today_ll=(LinearLayout)findViewById(R.id.today_ll);
        ll_1=(LinearLayout)findViewById(R.id.ll_1);
        ll_2=(LinearLayout)findViewById(R.id.ll_2);
        ll_3=(LinearLayout)findViewById(R.id.ll_3);
        ll_4=(LinearLayout)findViewById(R.id.ll_4);
        ll_5=(LinearLayout)findViewById(R.id.ll_5);
        ll_6=(LinearLayout)findViewById(R.id.ll_6);
        ll_7=(LinearLayout)findViewById(R.id.ll_7);
        ll_recent=(LinearLayout)findViewById(R.id.ll_recent);
        ll_account=(LinearLayout)findViewById(R.id.ll_account);
        ll_home=(LinearLayout)findViewById(R.id.ll_home);
        ll_bookmark=(LinearLayout)findViewById(R.id.ll_bookmark);
        ll_categorie=(LinearLayout)findViewById(R.id.ll_categorie);
        review_layout=(LinearLayout)findViewById(R.id.review_layout);
        ll_WriteReview=(LinearLayout)findViewById(R.id.WriteReview);
        iv_outlet_Excelent=(LinearLayout)findViewById(R.id.iv_outlet_Excelent);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(5);
        drawable.setStroke(0, Color.parseColor("#c4171d"));
        drawable.setColor(Color.parseColor("#c4171d"));
        iv_outlet_Excelent.setBackgroundDrawable(drawable);

        reviews = (LinearLayout)findViewById(R.id.reviews);
        like_btn = (LinearLayout)findViewById(R.id.like_btn);
        call_btn_layout = (LinearLayout)findViewById(R.id.call_btn_layout);
        map_btn_layout = (LinearLayout)findViewById(R.id.map_btn_layout);
        rating_btn = (LinearLayout)findViewById(R.id.rating_btn);
        rating_layout = (RelativeLayout)findViewById(R.id.rating_layout);
      //  cover = (View)findViewById(R.id.cover);

        rating_save_btn = (Button)findViewById(R.id.rating_save_btn);

        tv_rating =(TextView)findViewById(R.id.txt_rating);
        today_time =(TextView)findViewById(R.id.today_time);
        mon_time_ =(TextView)findViewById(R.id.mon_time_);

        rating_value =(TextView)findViewById(R.id.rating_value);
        txt_show_more=(TextView)findViewById(R.id.ll_show);
        // tv_outlet_adress=(TextView)findViewById(R.id.tv_adress);
        tv_outlet_adress1=(TextView)findViewById(R.id.tv_putlet_adress1);
        tv_review_rate=(TextView)findViewById(R.id.tv_review_rate);
        tv_read_all_review=(TextView)findViewById(R.id.tv_read_all_review);
        tv_review_type =(TextView)findViewById(R.id.review_type);
        tv_like_count =(TextView)findViewById(R.id.tv_like_count);
        tv_reviewtime=(TextView)findViewById(R.id.tv_review_time);
        tv_reviewer_name=(TextView)findViewById(R.id.tv_reviewer_name);
        iv_review_image=(CircleImageView)findViewById(R.id.tv_reviewer_image);
        tv_review_adress=(TextView)findViewById(R.id.tv_reviewer_address);
        tv_rating_type=(TextView)findViewById(R.id.tv_rating_type);
        tv_outlet_type=(TextView)findViewById(R.id.tv_type);
        addresstxt=(TextView)findViewById(R.id.addresstxt);
        hours=(TextView)findViewById(R.id.hours);
        glylbl=(TextView)findViewById(R.id.gly);
        recentlbl=(TextView)findViewById(R.id.recentlbl);
        ratelbl=(TextView)findViewById(R.id.ratelbl);
        writreviewlbl=(TextView)findViewById(R.id.writreviewlbl);

        txt_home = (Utils.LightText) findViewById(R.id.txt_home);
        txt_category = (Utils.LightText) findViewById(R.id.txt_category);
        txt_recents = (Utils.LightText) findViewById(R.id.txt_recents);
        txt_bookmark = (Utils.LightText) findViewById(R.id.txt_bookmark);
        txt_account = (Utils.LightText) findViewById(R.id.txt_account);

        tuesday=(TextView)findViewById(R.id.tueday);
        today_time_ll=(TextView)findViewById(R.id.today_time_ll);
        wed_textview=(TextView)findViewById(R.id.wed);
        thu_text=(TextView)findViewById(R.id.thu);
        fri_Text=(TextView)findViewById(R.id.fri);
        sat_txt=(TextView)findViewById(R.id.sat);
        sun_text=(TextView)findViewById(R.id.sun);

        full_adress=(TextView)findViewById(R.id.full_adress);

        homedeliverylbl=(TextView)findViewById(R.id.homedeliverylbl);
        ll_currency=(TextView)findViewById(R.id.ll_curency);
        ll_creditcard=(TextView)findViewById(R.id.ll_creditcard);

        landmark=(TextView)findViewById(R.id.landmark);
        iv_back=(ImageView)findViewById(R.id.iv_back);

        btn_account=(ImageView)findViewById(R.id.btn_account);
        btn_account_active=(ImageView)findViewById(R.id.btn_account_active);
        btn_recent=(ImageView)findViewById(R.id.btn_recent);
        btn_recent_active=(ImageView)findViewById(R.id.btn_recent_active);
        btn_bookmak=(ImageView)findViewById(R.id.btn_bookmark);
        btn_bookmark_active=(ImageView)findViewById(R.id.btn_bookmark_active);
        btn_home=(ImageView)findViewById(R.id.btn_home);
        btn_home_acive=(ImageView)findViewById(R.id.btn_home_active);
        btn_categorie=(ImageView)findViewById(R.id.btn_cat);
        btn_categorie_active=(ImageView)findViewById(R.id.btn_cat_active);
        iv_background = (ImageView)findViewById(R.id.iv_background);
        iv_outlet_like = (ImageView)findViewById(R.id.iv_outlet_like);
        bookmark_btn = (ImageView)findViewById(R.id.main_txt_skip);

        Drawable progress = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.parseColor("#ffff99"));

        tv_outlet_name.setTypeface(setfont("OpenSans-Bold.ttf"));
        tv_outlet_adress1.setTypeface(setfont("OpenSans-Regular.ttf"));
        tv_outlet_type.setTypeface(setfont("OpenSans-Regular.ttf"));
        full_adress.setTypeface(setfont("OpenSans-Regular.ttf"));
        addresstxt.setTypeface(setfont("OpenSans-Bold.ttf"));
        landmark.setTypeface(setfont("OpenSans-Regular.ttf"));
        hours.setTypeface(setfont("OpenSans-Regular.ttf"));
        txt_show_more.setTypeface(setfont("OpenSans-Regular.ttf"));
        homedeliverylbl.setTypeface(setfont("OpenSans-Regular.ttf"));
        ll_currency.setTypeface(setfont("OpenSans-Regular.ttf"));
        ll_creditcard.setTypeface(setfont("OpenSans-Regular.ttf"));
        txt_map.setTypeface(setfont("OpenSans-Bold.ttf"));
        glylbl.setTypeface(setfont("OpenSans-Regular.ttf"));
        recentlbl.setTypeface(setfont("OpenSans-Bold.ttf"));
        tv_reviewer_name.setTypeface(setfont("OpenSans-Regular.ttf"));
        tv_review_adress.setTypeface(setfont("OpenSans-Regular.ttf"));
        ratelbl.setTypeface(setfont("OpenSans-Regular.ttf"));
        tv_reviewtime.setTypeface(setfont("OpenSans-Regular.ttf"));
        tv_review_type.setTypeface(setfont("OpenSans-Regular.ttf"));
        tv_read_all_review.setTypeface(setfont("OpenSans-Regular.ttf"));
        writreviewlbl.setTypeface(setfont("OpenSans-Regular.ttf"));


        if (String.valueOf(sharePref.getUserID(OutletDetails.this)).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        }

        TabLayout();
    }

    public void TabLayout(){
        if (Constant.TabLayout_Home_Status == true){
            btn_home_acive.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.GONE);
            txt_home.setTextColor(getResources().getColor(R.color.lightred));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));

        }
        else if(Constant.TabLayout_Category_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.GONE);
            txt_category.setTextColor(getResources().getColor(R.color.lightred));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
        else if(Constant.TabLayout_Resent_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.GONE);
            txt_recents.setTextColor(getResources().getColor(R.color.lightred));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));

        } else if(Constant.TabLayout_Bookmark_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.VISIBLE);
            btn_bookmak.setVisibility(View.GONE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.lightred));

            btn_account_active.setVisibility(View.GONE);
            btn_account.setVisibility(View.VISIBLE);
            txt_account.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
        else if(Constant.TabLayout_Accounts_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_recent_active.setVisibility(View.GONE);
            btn_recent.setVisibility(View.VISIBLE);
            txt_recents.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_bookmark_active.setVisibility(View.GONE);
            btn_bookmak.setVisibility(View.VISIBLE);
            txt_bookmark.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_account_active.setVisibility(View.VISIBLE);
            btn_account.setVisibility(View.GONE);
            txt_account.setTextColor(getResources().getColor(R.color.lightred));
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

        if(v==txt_show_more){

            if (txt_show_more.getText().toString().equals("Show more")){

                String datevalue ;
                txt_show_more.setText("Show Less");
                today_ll.setVisibility(View.GONE);
                ll_1.setVisibility(View.VISIBLE);
                ll_2.setVisibility(View.VISIBLE);
                ll_3.setVisibility(View.VISIBLE);
                ll_4.setVisibility(View.VISIBLE);
                ll_5.setVisibility(View.VISIBLE);
                ll_6.setVisibility(View.VISIBLE);
                ll_7.setVisibility(View.VISIBLE);

                for (int i=0; i<OutletsDTO.getGetOutletInfo().size(); i++){
                    datevalue = OutletsDTO.getGetOutletInfo().get(i).getOpenTime()
                            .replace("\"","")
                            .replace("{","")
                            .replace("}","")
                            .replace("name","")
                            .replace("","")
                            .replace("open_time","")
                            .replace("close_time","")
                            .trim();

                    if (OutletsDTO.getGetOutletInfo().get(i).getName().equals("Fri")){
                        if (OutletsDTO.getGetOutletInfo().get(i).getFridayBrkTime().equals("n")){

                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Fri")){
                                if( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().equals("close") || OutletsDTO.getGetOutletInfo().get(i).getOpenTime().equals("24x7")){
                                    fri_Text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime());
                                }
                                else {
                                    fri_Text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" to "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                                }
                            }
                        }
                        else if(OutletsDTO.getGetOutletInfo().get(i).getOpenTime().equals("24x7")){
                            mon_time_.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime());
                            tuesday.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime());
                        }
                        else {
                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Fri")){
                                if( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().equals("close") || OutletsDTO.getGetOutletInfo().get(i).getOpenTime().equals("24x7")){
                                    fri_Text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime());
                                }
                                else {
                                    fri_Text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" , "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                                }
                            }
                        }
                    }
                    else if(OutletsDTO.getGetOutletInfo().get(i).getOpenTime().equals("24x7")) {
                        mon_time_.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().substring(0,2)+"hours");
                        tuesday.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().substring(0,2)+"hours");
                        wed_textview.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().substring(0,2)+"hours");
                        thu_text.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().substring(0,2)+"hours");
                        sat_txt.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().substring(0,2)+"hours");
                        sun_text.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().substring(0,2)+"hours");
                        fri_Text.setText( OutletsDTO.getGetOutletInfo().get(i).getOpenTime().substring(0,2)+"hours");
                    }
                    else {
                        if (OutletsDTO.getGetOutletInfo().get(i).getBrkTime().equals("n")){
                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Mon")){
                                String mondata = datevalue.replace("Mon","").replace("","");
                                //mon_time_.setText(mondata.substring(1,mondata.length()-2));
                                mon_time_.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" to "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                            }
                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Tue")){
                                String mondata = datevalue.replace("Tue","").replace("","");
                                tuesday.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" to "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                            }
                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Wed")){
                                String mondata = datevalue.replace("Wed","").replace("","");
                                wed_textview.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" to "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                            }
                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Thu")){
                                String mondata = datevalue.replace("Thu","").replace("","");
                                thu_text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" to "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                            }
                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Sat")){
                                String mondata = datevalue.replace("Sat","").replace("","");
                                sat_txt.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" to "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());

                            }
                            if(OutletsDTO.getGetOutletInfo().get(i).getName().equals("Sun")){
                                String mondata = datevalue.replace("Sun","").replace("","");
                                sun_text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime()+" to "+OutletsDTO.getGetOutletInfo().get(i).getCloseTime());

                            }
                        }
                        else {
                            if (OutletsDTO.getGetOutletInfo().get(i).getName().equals("Mon")) {
                                String mondata = datevalue.replace("Mon", "").replace("", "");
                                //mon_time_.setText(mondata.substring(1,mondata.length()-2));
                                mon_time_.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime() + " , " + OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                            }
                            if (OutletsDTO.getGetOutletInfo().get(i).getName().equals("Tue")) {
                                String mondata = datevalue.replace("Tue", "").replace("", "");
                                tuesday.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime() + " , " + OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                            }
                            if (OutletsDTO.getGetOutletInfo().get(i).getName().equals("Wed")) {
                                String mondata = datevalue.replace("Wed", "").replace("", "");
                                wed_textview.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime() + " , " + OutletsDTO.getGetOutletInfo().get(i).getCloseTime());

                            }
                            if (OutletsDTO.getGetOutletInfo().get(i).getName().equals("Thu")) {
                                String mondata = datevalue.replace("Thu", "").replace("", "");
                                thu_text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime() + " , " + OutletsDTO.getGetOutletInfo().get(i).getCloseTime());
                            }
                            if (OutletsDTO.getGetOutletInfo().get(i).getName().equals("Sat")) {
                                String mondata = datevalue.replace("Sat", "").replace("", "");
                                sat_txt.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime() + " , " + OutletsDTO.getGetOutletInfo().get(i).getCloseTime());

                            }
                            if (OutletsDTO.getGetOutletInfo().get(i).getName().equals("Sun")) {
                                String mondata = datevalue.replace("Sun", "").replace("", "");
                                sun_text.setText(OutletsDTO.getGetOutletInfo().get(i).getOpenTime() + " , " + OutletsDTO.getGetOutletInfo().get(i).getCloseTime());

                            }
                        }
                    }

                }

            }else {
                txt_show_more.setText("Show more");

                today_ll.setVisibility(View.VISIBLE);
                ll_1.setVisibility(View.GONE);
                ll_2.setVisibility(View.GONE);
                ll_3.setVisibility(View.GONE);
                ll_4.setVisibility(View.GONE);
                ll_5.setVisibility(View.GONE);
                ll_6.setVisibility(View.GONE);
                ll_7.setVisibility(View.GONE);

                // ll_2.removeAllViews();
            }

        }else if(v==iv_back){
            finish();
        }
        else if (v == ll_recent) {
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Category_Status = false;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = true;
            Constant.TabLayout_Accounts_Status= false;

            Intent intent = new Intent(OutletDetails.this,MoreScreen.class);
            startActivity(intent);
            finish();

        }else if(v==txt_map){
            Intent inte=new Intent(OutletDetails.this,MapsActivity.class);
            inte.putExtra("lat",outletlat);
            inte.putExtra("long",outletlong);
            startActivity(inte);
        }else if(v==tv_read_all_review){
            Intent inte=new Intent(OutletDetails.this,Review.class);
            Bundle b =new Bundle();
            b.putString("OUTlET_ID",id);
            b.putString("OUTlET_NAME",name_store);
            b.putString("OUTlET_location",locatio_store);
            b.putString("OUTlET_image",name_image);
            inte.putExtras(b);
            startActivity(inte);
        }
        else if(v==ll_account)
        {
            if (sharePref.getUserID(OutletDetails.this).equals("null")) {
                Intent inte = new Intent(OutletDetails.this, LoginActivity.class);
                startActivity(inte);
            }
            else {
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= true;

                Intent inte = new Intent(OutletDetails.this, MyAccount.class);
                startActivity(inte);
            }
        }

        else if(v==ll_bookmark)
        {
            if (sharePref.getUserID(OutletDetails.this).equals("null")) {


                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(OutletDetails.this, LoginActivity.class);
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

                Intent intenthome = new Intent(OutletDetails.this, BookmarkActivity.class);
                startActivity(intenthome);
                finish();
            }

        }

        else if(v==ll_categorie)
        {
            Constant.TabLayout_Home_Status = false;
            Constant.TabLayout_Category_Status = true;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = false;
            Constant.TabLayout_Accounts_Status= false;

            Intent inte=new Intent(OutletDetails.this,CategoryActiviyt.class);
            startActivity(inte);

        }
        else if(v == ll_recent){

        }
        else if(v==ll_home)
        {
            Constant.TabLayout_Home_Status = true;
            Constant.TabLayout_Category_Status = false;
            Constant.TabLayout_Bookmark_Status = false;
            Constant.TabLayout_Resent_Status = false;
            Constant.TabLayout_Accounts_Status= false;

            Intent inte=new Intent(OutletDetails.this,Home.class);
            startActivity(inte);

        }
        else if(v==ll_WriteReview)
        {
            //Log.e("Click","me review");
            if (sharePref.getUserID(OutletDetails.this).equals("null")) {
                Intent intreview = new Intent(this, LoginActivity.class);
                startActivity(intreview);
            }
            else {
                Intent intewrite=new Intent(OutletDetails.this,WriteReview.class);
                Bundle b =new Bundle();
                b.putString("OUTlET_ID",id);
                b.putString("OUTlET_NAME",name_store);
                b.putString("OUTlET_location",locatio_store);
                b.putString("OUTlET_image",name_image);
                intewrite.putExtras(b);
                startActivity(intewrite);
            }
        }
        else if(v==reviews)
        {
            Intent inte=new Intent(OutletDetails.this,Review.class);
            Bundle b =new Bundle();
            b.putString("OUTlET_ID",id);
            b.putString("OUTlET_NAME",name_store);
            b.putString("OUTlET_location",locatio_store);
            b.putString("OUTlET_image",name_image);
            inte.putExtras(b);
            startActivity(inte);

        }

        else if(v == map_btn_layout){
            Intent inte=new Intent(OutletDetails.this,MapsActivity.class);
            inte.putExtra("lat",outletlat);
            inte.putExtra("long",outletlong);
            startActivity(inte);
        }
        else if(v == rating_save_btn){
            rating_save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rating_value.setText(String.valueOf(ratingBar.getRating()));
                    rating_layout.animate().setDuration(200).translationY(-230);
                    expended = false;
                }
            });
        }
        else if(v == like_btn){

            if (sharePref.getUserID(OutletDetails.this).equals("null")) {
                Intent intreview = new Intent(this, LoginActivity.class);
                startActivity(intreview);
            }
            else {
                if (like){
                    like = false;
                    Drawable drawable = getResources().getDrawable(R.drawable.like);
                    iv_outlet_like.setImageDrawable(drawable);
                    OutletLikeOrBookBark(OutletsDTO.getId(), "like","1" , "no",appSession.getUserId() );

                }
                else {
                    like = true;
                    Drawable drawable = getResources().getDrawable(R.drawable.unlike);
                    iv_outlet_like.setImageDrawable(drawable);
                    OutletLikeOrBookBark(OutletsDTO.getId(), "like","1" , "yes",appSession.getUserId() );
                }
            }
        }else if(v == bookmark_btn){

            if (sharePref.getUserID(OutletDetails.this).equals("null")) {
                Intent intreview = new Intent(this, LoginActivity.class);
                startActivity(intreview);
            }
            else {
                if (checkbookmark){
                    checkbookmark = false;
                    Drawable drawable = getResources().getDrawable(R.drawable.fav);
                    bookmark_btn.setImageDrawable(drawable);
                    OutletLikeOrBookBark(OutletsDTO.getId(), "bookmark","1" , "no",appSession.getUserId() );

                }
                else {
                    checkbookmark = true;
                    Drawable drawable = getResources().getDrawable(R.drawable.bookmark_active);
                    bookmark_btn.setBackground(drawable);
                    OutletLikeOrBookBark(OutletsDTO.getId(), "bookmark","1" , "yes",appSession.getUserId() );
                }
            }

        }
    }

    public void setRating(){
        expended = !expended;

        if(expended){

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                rating_layout.animate().setDuration(200).translationY(rating_layout.getMeasuredHeight());
            }
            else {
                rating_layout.animate().setDuration(200).translationY(rating_layout.getMeasuredHeight());
            }
        }

        else {
            rating_layout.animate().setDuration(200).translationY(-230);
            expended = false;
        }

    }

    public void phoneDialogBox(){

        final Dialog dialog = new Dialog(OutletDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.phone_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout lin = (LinearLayout) dialog.findViewById(R.id.layout);

        String[] items = OutletsDTO.getPhone_no().split(",");
        for (final String item : items) {

            TextView dynamicTextView = new TextView(this);

            View view = new View(OutletDetails.this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,1));
            view.setBackgroundColor(Color.GRAY);

            dynamicTextView.setTextSize(24);
            dynamicTextView.setGravity(Gravity.CENTER);
            dynamicTextView.setTextColor(Color.parseColor("#007AFF"));
            dynamicTextView.setPadding(0,20,0,20);
            dynamicTextView.setText(item);

            lin.addView(view);
            lin.addView(dynamicTextView);

            dynamicTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+item));
                    try {
                        startActivity(callIntent);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });

        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void showImage(int imageuri) {
        final Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                builder.dismiss();
            }
        });

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageuri);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
    }

    private void product_det() {
        String id = "";
        int outletdetailpage_hlist_adapter_flag = 0,Resent_List_flag=0;

        Bundle inte = getIntent().getExtras();

        if(inte != null) {

            Resent_List_flag = inte.getInt("Resent_List_flag");
            if (Resent_List_flag == 14){
                id = inte.getString("id");
            }
            if ( outletdetailpage_hlist_adapter_flag == 10){
                id = inte.getString("id");
                //SearchCatgeriadapter_flag = inte.getInt("SearchCategeriAdapter_flag");
            }else {
                id = inte.getString("id");
                SearchCatgeriadapter_flag = inte.getInt("SearchCategeriAdapter_flag");
            }

        }
        if (Constant.SELECTED_LOCATION.equals("")) {
            OutletDetail(Constant.UNSELECTED_LOCATION, appSession.getUserId(), id);
           // Toast.makeText(getApplicationContext(),"UNSELECTED_LOCATION  "+Constant.UNSELECTED_LOCATION,Toast.LENGTH_SHORT).show();
        }
        else {
            OutletDetail(Constant.SELECTED_LOCATION, appSession.getUserId(), id);
            //Toast.makeText(getApplicationContext(),"SELECTED_LOCATION  "+Constant.SELECTED_LOCATION,Toast.LENGTH_SHORT).show();
        }
        Log.e("user id","uswer ud"+id+"uuerid  "+appSession.getUserId()+appSession.getUserLocation() );
    }

    private void OutletDetail(String location, String user_id, String outlet_id) {

        class SearchCatAsyntask extends AsyncTask<String, Void, OutletDTO> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(OutletDetails.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected OutletDTO doInBackground(String... params) {
                try {
                    OutletDAO userDAO = new OutletDAO(getApplicationContext());
                    OutletsDTO = userDAO.getoutletDTO(params[0], params[1], params[2], SearchCatgeriadapter_flag);
                    return OutletsDTO;
                }
                catch ( Exception e){
                    Log.e("OutletDetails ","error "+e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(OutletDTO s) {
                super.onPostExecute(s);
                try {
                    if (s != null) {
                        arrayList.add(s);

                        success = OutletsDTO.getSuccess();
                        if (String.valueOf(success).equals("1")) {

                            gallaryAdapter = new DetailGalllary(OutletDetails.this, s.getGallaryDTOs());
                            gallery.setAdapter(gallaryAdapter);

                            if (String.valueOf(OutletsDTO.getLike()).equals("no")) {
                                Drawable drawable = getResources().getDrawable(R.drawable.unlike);
                                iv_outlet_like.setImageDrawable(drawable);
                                like = true;
                            } else {
                                Drawable drawable = getResources().getDrawable(R.drawable.like);
                                iv_outlet_like.setImageDrawable(drawable);
                                like = false;
                            }
                            if (OutletsDTO.getBookmark().equals("no")) {
                                Drawable drawable = getResources().getDrawable(R.drawable.fav_inactive);
                                bookmark_btn.setImageDrawable(drawable);
                                checkbookmark = true;
                            } else {
                                Drawable drawable = getResources().getDrawable(R.drawable.bookmark_active);
                                bookmark_btn.setImageDrawable(drawable);
                                checkbookmark = false;
                            }

                            loader = new Loader(OutletDetails.this);
                            full_adress.setText(OutletsDTO.getFull_location());

                            if (String.valueOf(OutletsDTO.getLandmark()).equals("null")) {
                                landmark.setText("Landmark: " + OutletsDTO.getLandmark());
                            } else {
                                landmark.setVisibility(View.GONE);
                            }
                            tv_outlet_name.setText(OutletsDTO.getName());
                            name_store = OutletsDTO.getName();
                            name_image = OutletsDTO.getBackground_imge();
                            locatio_store = OutletsDTO.getLocation();


                            Log.e("llllaaty delicery222","d "+OutletsDTO.getToday_Closetime()+"loing "+OutletsDTO.getCloseTime()+"cu  "+OutletsDTO.getCurrency_accepted());
                            if (String.valueOf(OutletsDTO.getHome_delivery()).equals("no"))
                                homedeliverylbl.setText("Home Delivery : " + OutletsDTO.getHome_delivery().substring(0, 1).toUpperCase() + OutletsDTO.getHome_delivery().substring(1, OutletsDTO.getHome_delivery().length()));
                            else
                                homedeliverylbl.setText("Home Delivery : " + OutletsDTO.getHome_delivery().substring(0, 1).toUpperCase() + OutletsDTO.getHome_delivery().substring(1, OutletsDTO.getHome_delivery().length()));

                            if (String.valueOf(OutletsDTO.getCredit_card()).equals("no"))
                                ll_creditcard.setText("Credit Cards Accepted : " + OutletsDTO.getCredit_card().substring(0, 1).toUpperCase() + OutletsDTO.getCredit_card().substring(1, OutletsDTO.getCredit_card().length()));
                            else
                                ll_creditcard.setText("Credit Cards Accepted : " + OutletsDTO.getCredit_card().substring(0, 1).toUpperCase() + OutletsDTO.getCredit_card().substring(1, OutletsDTO.getCredit_card().length()));

                            if (String.valueOf(OutletsDTO.getCurrency_accepted()).equals("") ||String.valueOf(OutletsDTO.getCurrency_accepted()).equals("no"))
                                ll_currency.setText("Currency Accepted : " + OutletsDTO.getCurrency_accepted());
                            else
                            {
                                 String giveSpace = OutletsDTO.getCurrency_accepted().replace(",", ", ");
                                ll_currency.setText("Currency Accepted : " + giveSpace);
                            }

                            // tv_outlet_adress.setText(OutletsDTO.getLocation());
                            txt_distance.setText(OutletsDTO.getDistance() + "km");
                            tv_outlet_type.setText(OutletsDTO.getMarket_type());
                            tv_outlet_adress1.setText(OutletsDTO.getLocation());
                            // tv_outlet_adress.setText(OutletsDTO.getLocation());
                            tv_review_adress.setText(OutletsDTO.getReview_adress());
                            tv_reviewer_name.setText(OutletsDTO.getReview_name());
                            tv_reviewtime.setText(OutletsDTO.getReview_ago());
                            //  tv_review_rate.setText(OutletsDTO.getRating());

                            tv_review_rate.setText(OutletsDTO.getReview_rating());

                            Log.e("llllaaty delicery","d "+OutletsDTO.getRating_outlate()+"loing "+String.valueOf( OutletsDTO.getHome_delivery()));
                            outletlat = OutletsDTO.getOutlet_lat();
                            outletlong = OutletsDTO.getOutlet_long();

                            float rate = Float.parseFloat(OutletsDTO.getRating_outlate());
                                if (rate <= 2) {
                                    GradientDrawable drawable = new GradientDrawable();
                                    drawable.setShape(GradientDrawable.RECTANGLE);
                                    drawable.setCornerRadius(3);
                                    drawable.setStroke(0, Color.parseColor("#c4171d"));
                                    drawable.setColor(Color.parseColor("#c4171d"));
                                    tv_review_rate.setBackgroundDrawable(drawable);
                                } else if (2 < rate && 4 >= rate) {
                                    GradientDrawable drawable = new GradientDrawable();
                                    drawable.setShape(GradientDrawable.RECTANGLE);
                                    drawable.setCornerRadius(3);
                                    drawable.setStroke(0, Color.parseColor("#f8a820"));
                                    drawable.setColor(Color.parseColor("#f8a820"));
                                    tv_review_rate.setBackgroundDrawable(drawable);
                                } else {
                                    GradientDrawable drawable = new GradientDrawable();
                                    drawable.setShape(GradientDrawable.RECTANGLE);
                                    drawable.setCornerRadius(3);
                                    drawable.setStroke(0, Color.parseColor("#009812"));
                                    drawable.setColor(Color.parseColor("#009812"));
                                    tv_review_rate.setBackgroundDrawable(drawable);
                                }
                           // if (!String.valueOf(OutletsDTO.getReview_count()).equals("0"))
                            tv_read_all_review.setText("READ ALL REVIEWS " + "(" + OutletsDTO.getReview_count() + ")");

                            tv_review_type.setText(OutletsDTO.getReview_type());
                            tv_like_count.setText("Likes" + "(" + OutletsDTO.getLike_count() + ")");
                            tv_rating.setText(OutletsDTO.getRating_outlate());
                            rating_value.setText(OutletsDTO.getRating_outlate());

                            if (!String.valueOf(OutletsDTO.getReview_details()).equals("null")) {

                            } else {
                                review_layout.setVisibility(View.GONE);
                            }

                            if(!String.valueOf(OutletsDTO.getToday_Closetime()).equals("null")) {
                            if (OutletsDTO.getToday_Closetime().equals("close") ) {
                                today_time.setText("close");
                            }
                            else if( OutletsDTO.getToday_Closetime().equals("24x7"))
                                today_time.setText(OutletsDTO.getToday_Closetime().substring(0,2)+"hours");
                            else {
                                if (OutletsDTO.getToday_brktime().equals("n")) {
                                    today_time.setText(OutletsDTO.gettoday_Opentime() + " to " + OutletsDTO.getToday_Closetime());
                                } else {
                                    today_time.setText(OutletsDTO.gettoday_Opentime() + " , " + OutletsDTO.getToday_Closetime());
                                }
                            }
                            }

                                float ratingno = Float.valueOf(OutletsDTO.getRating_outlate());
                                if (ratingno <= 2) {
                                    GradientDrawable drawable = new GradientDrawable();
                                    drawable.setShape(GradientDrawable.RECTANGLE);
                                    drawable.setCornerRadius(3);
                                    drawable.setStroke(0, Color.parseColor("#c4171d"));
                                    drawable.setColor(Color.parseColor("#c4171d"));
                                    iv_outlet_Excelent.setBackgroundDrawable(drawable);
                                } else if (ratingno > 2 && ratingno < 4) {
                                    GradientDrawable drawable = new GradientDrawable();
                                    drawable.setShape(GradientDrawable.RECTANGLE);
                                    drawable.setCornerRadius(3);
                                    drawable.setStroke(0, Color.parseColor("#f8a820"));
                                    drawable.setColor(Color.parseColor("#f8a820"));
                                    iv_outlet_Excelent.setBackgroundDrawable(drawable);
                                } else if (ratingno >= 4) {
                                    GradientDrawable drawable = new GradientDrawable();
                                    drawable.setShape(GradientDrawable.RECTANGLE);
                                    drawable.setCornerRadius(3);
                                    drawable.setStroke(0, Color.parseColor("#009812"));
                                    drawable.setColor(Color.parseColor("#009812"));
                                    iv_outlet_Excelent.setBackgroundDrawable(drawable);
                                }
                            // today_time.setText(todaytime.substring(2, todaytime.length() - 5));
                            loader.displayImage(OutletsDTO.getReview_imge(), iv_review_image);
                            loader.displayImage(OutletsDTO.getBackground_imge(), iv_background);
                            Log.e("image is","D "+OutletsDTO.getBackground_imge()+ "      d "+ OutletsDTO.getReview_imge());
                            id = OutletsDTO.getId();
                            float ratingValue = Float.valueOf(OutletsDTO.getRating_outlate());
                            ratingBar.setRating(ratingValue);

                            scrollview.setVisibility(View.VISIBLE);
                            mProgressDialog.dismiss();

                        } else if (success.equals("0")) {
                            CommonUtils.showToast(getApplicationContext(), "No record found ...");
                            mProgressDialog.dismiss();
                        }
                    }

                    else {
                        CommonUtils.showToast(getApplicationContext(), "Server Exceptions...");
                        mProgressDialog.dismiss();
                    }
                }
                  catch ( Exception e){
                        Log.e("OutletDetails ","error "+e.getMessage());
                    }
            }
        }

        SearchCatAsyntask ru = new SearchCatAsyntask();
        ru.execute(location,user_id,outlet_id);
    }

    private void OutletLikeOrBookBark(final String userid, final String type, final String reftype , final String value,final String user_id){

        class likeOrBookmark extends AsyncTask<String, Void, BookmarkDTO> {

            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog = new ProgressDialog(OutletDetails.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected BookmarkDTO doInBackground(String... params) {

                try {
                    BookmarkDAO bkDAO = new BookmarkDAO(getApplicationContext());
                    bookmarkDAO = bkDAO.getbookmark(params[0], params[1], params[2], params[3], params[3]);
                    return bookmarkDAO;
                } catch ( Exception e){
                    Log.e("OutletDetails ","error "+e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(BookmarkDTO bookmarkDTO) {
                super.onPostExecute(bookmarkDTO);
                mProgressDialog.dismiss();
                tv_like_count.setText("Likes"+"("+bookmarkDTO.getLike()+")");
            }
        }

        likeOrBookmark lke_bk = new likeOrBookmark();
        lke_bk.execute(userid,type,reftype,value,user_id);
    }
    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }
}
