package com.assan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DAO.SearchCategoriesDAO;
import DTO.SearchCategoriesDTO;
import DTO.searchhvDTO;
import UtilApi.RemotRequest;
import Utils.AppSession;
import Utils.CommonUtils;
import Utils.Constant;
import Utils.Prefrence;
import adapter.Headerapdater;
import adapter.SearchCategoriesAdapter;
import adapter.SearchHVAdapter;
import it.sephiroth.android.library.widget.HListView;

/**
 * Created by Sonu Saini on 5/4/2016.
 */

public class SearchCategories extends Activity implements View.OnClickListener {

    ProgressDialog mProgressDialog;
    // Declare Variables
    SearchCategoriesDTO searchCategoriesDTO=null;
    SearchCategoriesAdapter adapter;
    SearchHVAdapter hvadapetr;
    Headerapdater headerapdater;
    HListView hlv_sposensered;
    String success,catid,subcatid,catname,keyword = null;
    ListView listView;
    List<SearchCategoriesDTO> arraylist = new ArrayList<>();
    int count = 0;
    List<searchhvDTO> hvarraylist;
    boolean checklist = true;
    String latitude= "" ,longitude= "",pageno="1",page="1";
    LinearLayout ll_home,ll_recent,ll_categorie,ll_boobmark,ll_account,back_btn,search_catagri,filterbtn;
    TextView filterlbl,sponsoredid,txt_clearAll,txt_name,txt_catname,txt_sub_cat_name,txt_tryagain,txt_home_active,txt_recent,txt_recent_active,txt_categorie,txt_categorie_active,txt_boobkmark,txt_boobkmark_active,txt_account,txt_account_active;
    ImageView btn_filter,arrow,btn_mobile,btn_back,btn_home_acive,btn_home,btn_recent,btn_recent_active,btn_categorie,btn_categorie_active,btn_bookmak,btn_bookmark_active,btn_account,btn_account_active;
    Button btn_login;
    AppSession appSession;
    Prefrence sharePref;
    Utils.LightText txt_home,txt_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchcategories);

        appSession=new AppSession(getApplicationContext());
        sharePref = new Prefrence();

        ll_home=(LinearLayout)findViewById(R.id.ll_home);
        ll_recent=(LinearLayout)findViewById(R.id.ll_recent);
        ll_categorie=(LinearLayout)findViewById(R.id.ll_categorie);
        ll_boobmark=(LinearLayout)findViewById(R.id.ll_bookmark);
        ll_account=(LinearLayout)findViewById(R.id.ll_account);
        back_btn=(LinearLayout)findViewById(R.id.back_btn);
        search_catagri = (LinearLayout) findViewById(R.id.search_catagori);
        filterbtn = (LinearLayout) findViewById(R.id.filterbtn);

        hlv_sposensered = (HListView)findViewById(R.id.hlv_sposensered);
        // txt_tryagain=(TextView)findViewById(R.id.text_tryagain);
        txt_catname=(TextView)findViewById(R.id.txt_cat_name);
        txt_name=(TextView)findViewById(R.id.txt_name);
        sponsoredid=(TextView)findViewById(R.id.sponsoredid);
       /* txt_filter=(ImageView)findViewById(R.id.RL_filter);*/
        txt_sub_cat_name=(TextView)findViewById(R.id.txt_sub_name);
        listView = (ListView) findViewById(R.id.lv_outlet_list);
        filterlbl=(TextView)findViewById(R.id.filterlbl);
       // txt_sortRating=(TextView)findViewById(R.id.txt_sortrating);
        //txt_clearAll=(TextView)findViewById(R.id.txt_clearall);
        txt_home = (Utils.LightText) findViewById(R.id.txt_home);
        txt_category = (Utils.LightText) findViewById(R.id.txt_category);

        //--------------------------------------------------------button----------------------

        btn_home=(ImageView)findViewById(R.id.btn_home);
        btn_back=(ImageView)findViewById(R.id.iv_back);
        btn_filter=(ImageView)findViewById(R.id.RL_filter);

        btn_home_acive=(ImageView)findViewById(R.id.btn_home_active);
        btn_recent=(ImageView)findViewById(R.id.btn_recent);
        btn_recent_active=(ImageView)findViewById(R.id.btn_recent_active);
        btn_categorie=(ImageView)findViewById(R.id.btn_cat);
        btn_categorie_active=(ImageView)findViewById(R.id.btn_cat_active);
        btn_bookmak=(ImageView)findViewById(R.id.btn_bookmark);
        btn_bookmark_active=(ImageView)findViewById(R.id.btn_bookmark_active);
        btn_account=(ImageView)findViewById(R.id.btn_account);
        arrow=(ImageView)findViewById(R.id.arrow);
        btn_account_active=(ImageView)findViewById(R.id.btn_account_active);
        btn_login=(Button)findViewById(R.id.btn_login);

        //---------------------------------textview-----------------------------------------

        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
//        txt_tryagain.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        filterbtn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        search_catagri.setOnClickListener(this);
        txt_name.setOnClickListener(this);

        btn_home_acive.setVisibility(View.VISIBLE);
        btn_home.setVisibility(View.GONE);
        Intent inte=getIntent();

        catid=Home.CATID;
        catname = inte.getStringExtra("cat_name");
        subcatid=inte.getStringExtra("subcatid");
        keyword=inte.getStringExtra("keyword");
        latitude = inte.getStringExtra("latitude");
        longitude = inte.getStringExtra("longitude");

        String subcatname=inte.getStringExtra("subcatname");
        txt_sub_cat_name.setText(subcatname);
        txt_catname.setText(catname);

        if (Constant.SELECTED_LOCATION.equals("")){
            txt_name.setText(Constant.UNSELECTED_LOCATION);
        }
        else {
            txt_name.setText(Constant.SELECTED_LOCATION);
        }

        if (catname==""){
            arrow.setVisibility(View.GONE);
        }else {

        }

        SearchCat("1");

        if (String.valueOf(sharePref.getUserID(SearchCategories.this)).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        }


        if (Constant.TabLayout_Home_Status == true){
            btn_home_acive.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.GONE);
            txt_home.setTextColor(getResources().getColor(R.color.lightred));

            btn_categorie_active.setVisibility(View.GONE);
            btn_categorie.setVisibility(View.VISIBLE);
            txt_category.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
        else if(Constant.TabLayout_Category_Status == true){
            btn_home_acive.setVisibility(View.GONE);
            btn_home.setVisibility(View.VISIBLE);
            txt_home.setTextColor(getResources().getColor(R.color.text_gray_color));

            btn_categorie_active.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.GONE);
            txt_category.setTextColor(getResources().getColor(R.color.lightred));

        }
        filterlbl.setTypeface(setfont("OpenSans-Bold.ttf"));
        sponsoredid.setTypeface(setfont("OpenSans-Bold.ttf"));
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

            Intent intenthome=new Intent(SearchCategories.this,Home.class);
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

            Intent intenthome=new Intent(SearchCategories.this,MoreScreen.class);
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

        }else if(v.getId()==R.id.ll_bookmark){
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

            if (sharePref.getUserID(SearchCategories.this).equals("null")) {

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
                //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(SearchCategories.this, LoginActivity.class);
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

                Intent intenthome = new Intent(SearchCategories.this, BookmarkActivity.class);
                startActivity(intenthome);
                finish();
            }


        }else if (v.getId() == R.id.search_catagori) {
            Constant.Check_current_status = false;
            Intent intent = new Intent(SearchCategories.this, SearchLocation.class);
            intent.putExtra("KEY_SEARCH", 2);
            intent.putExtra("CAT_ARRAY", Constant.CatArray);
            startActivity(intent);
            finish();
        }
        else if(v.getId()==R.id.ll_account){

                btn_bookmark_active.setVisibility(View.GONE);

                btn_home_acive.setVisibility(View.GONE);
                btn_categorie_active.setVisibility(View.GONE);
                btn_recent_active.setVisibility(View.GONE);

                btn_bookmak.setVisibility(View.VISIBLE);
                btn_home.setVisibility(View.VISIBLE);
                btn_categorie.setVisibility(View.VISIBLE);
                btn_recent.setVisibility(View.VISIBLE);


                if (sharePref.getUserID(SearchCategories.this).equals("null")) {

                    Intent intent = new Intent(SearchCategories.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status= true;

                    Intent intent = new Intent(SearchCategories.this, MyAccount.class);
                    startActivity(intent);
                    finish();
                }

        }/*else if(v.getId()==R.id.text_tryagain){


        }*/else if(v.getId()==R.id.iv_back){
            finish();

        }else if(v.getId()==R.id.back_btn){
            finish();
        }
        else if(v.getId()==R.id.filterbtn)
        {
            filterDialogBox();
        }
        else if(v.getId() == R.id.txt_name){
            Constant.Check_current_status = true;
            Intent intent = new Intent(SearchCategories.this, SearchLocation.class);
            intent.putExtra("searchcategories_flag",11);
            intent.putExtra("KEY_SEARCH",1);
            startActivity(intent);
            finish();
        }
    }


    private void SearchCat(String page) {

        if (catname.equals("")){
            txt_sub_cat_name.setText("Location");
            txt_catname.setText("   ");
            txt_sub_cat_name.setGravity(Gravity.CENTER);
            arrow.setVisibility(View.GONE);

            if (Constant.SELECTED_LOCATION.equals("")) {
                Searchcategories("", keyword, Constant.UNSELECTED_LOCATION, page, appSession.getUserId(),"");
            }
            else {
                Searchcategories("", keyword, Constant.SELECTED_LOCATION, page, appSession.getUserId(),"");
            }
        }else {
            txt_sub_cat_name.setText("Location");
            arrow.setVisibility(View.GONE);
            txt_catname.setText("    ");
            txt_sub_cat_name.setGravity(Gravity.CENTER);

            if (Constant.SELECTED_LOCATION.equals("")) {
                Searchcategories(catname, "", Constant.UNSELECTED_LOCATION,page,appSession.getUserId(),"");
            }
            else {
                Searchcategories(catname, "", Constant.SELECTED_LOCATION,page,appSession.getUserId(),"");
            }
        }

    }

    private void Searchcategories(String cat_id, String sub_cat_id, String location, String page, String userId,String filter) {
        class SearchCatAsyntask extends AsyncTask<String, Void, SearchCategoriesDTO> {
            ProgressDialog loading;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(SearchCategories.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected SearchCategoriesDTO doInBackground(String... params) {
                try {
                    SearchCategoriesDAO userDAO = new SearchCategoriesDAO(getApplicationContext());
                    searchCategoriesDTO = userDAO.SearchCategories(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);

                    return searchCategoriesDTO;
                }
                catch (Exception e){
                    Log.e("SearchCategories ","Error "+e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(final SearchCategoriesDTO s) {
                super.onPostExecute(s);

                try {
                    if (s != null) {
                        searchhvDTO sh = new searchhvDTO();
                        hvarraylist = new ArrayList<searchhvDTO>();

                        success = searchCategoriesDTO.getSuccess();

                        if (String.valueOf(success).equals("1")) {

                            if (checklist) {
                                Log.e("success11", "result" + arraylist.size());
                                arraylist = s.getCategoriesList();
                            }else {
                                arraylist.addAll(s.getCategoriesList());
                                //  s.getAdvertiseDTOs().clear();
                                Log.e("success2222", "result" + s.getCategoriesList().size());
                            }

                            adapter = new SearchCategoriesAdapter(SearchCategories.this, arraylist);
                            hvadapetr = new SearchHVAdapter(SearchCategories.this, s.getCategoriesDtos());
                           // String str2 = arraylist.toString();
                            // Set the adapter to the ListView
                            hlv_sposensered.setAdapter(hvadapetr);
                          //  System.out.println(str2);
                            listView.setAdapter(adapter);
                            listView.setSelection(count);

                            mProgressDialog.dismiss();

                            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view,
                                                                 int scrollState) { // TODO Auto-generated method stub
                                    int threshold = 1;
                                    count = listView.getCount();

                                    if (scrollState == SCROLL_STATE_IDLE) {
                                        if (listView.getLastVisiblePosition() >=count-1) {
                                            if (s.getCategoriesList().size() == 20) {
                                                int result = Integer.parseInt(pageno);
                                                result = result + 1;
                                                pageno = "" + result;
                                                Log.e("page size ", pageno);
                                                SearchCat(pageno);
                                                checklist = false;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem,
                                                     int visibleItemCount, int totalItemCount) {

                                }

                            });
                        } else if (success.equals("0")) {
                            CommonUtils.showToast(getApplicationContext(), "No record found ...");
                            mProgressDialog.dismiss();
                        }
                    } else {
                        CommonUtils.showToast(getApplicationContext(), "Server Exceptions...");
                        mProgressDialog.dismiss();
                    }
                }
                catch (Exception e){
                    Log.e("SearchCategories ","Error "+e.getMessage());
                }

            }

        }

        SearchCatAsyntask ru = new SearchCatAsyntask();
        ru.execute(cat_id,sub_cat_id,location,page,userId,Constant.LATITUDE,Constant.LONGITUDE,filter);
    }

    public void filterDialogBox(){
        final Dialog dialog = new Dialog(SearchCategories.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filter_dialog_box);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView nearBy = (TextView)dialog.findViewById(R.id.nearby);
        TextView ratingBy = (TextView)dialog.findViewById(R.id.ratingby);
        TextView clearAll = (TextView)dialog.findViewById(R.id.clearall);

        nearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (catname.equals("")){
                    if (Constant.SELECTED_LOCATION.equals("")) {
                        Searchcategories("", keyword, Constant.UNSELECTED_LOCATION, pageno, appSession.getUserId(),"nearby");
                    }
                    else {
                        Searchcategories("", keyword, Constant.SELECTED_LOCATION, pageno, appSession.getUserId(),"nearby");
                    }
                }else {
                    if (Constant.SELECTED_LOCATION.equals("")) {
                        Searchcategories(catname, "", Constant.UNSELECTED_LOCATION,pageno,appSession.getUserId(),"nearby");
                    }
                    else {
                        Searchcategories(catname, "", Constant.SELECTED_LOCATION,pageno,appSession.getUserId(),"nearby");
                    }
                }
                dialog.dismiss();
            }
        });

        ratingBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (catname.equals("")){
                    if (Constant.SELECTED_LOCATION.equals("")) {
                        Searchcategories("", keyword, Constant.UNSELECTED_LOCATION, pageno, appSession.getUserId(),"rating");
                    }
                    else {
                        Searchcategories("", keyword, Constant.SELECTED_LOCATION, pageno, appSession.getUserId(),"rating");
                    }
                }else {
                    if (Constant.SELECTED_LOCATION.equals("")) {
                        Searchcategories(catname, "", Constant.UNSELECTED_LOCATION,pageno,appSession.getUserId(),"rating");
                    }
                    else {
                        Searchcategories(catname, "", Constant.SELECTED_LOCATION,pageno,appSession.getUserId(),"rating");
                    }
                }
                dialog.dismiss();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (catname.equals("")){
                    if (Constant.SELECTED_LOCATION.equals("")) {
                        Searchcategories("", keyword, Constant.UNSELECTED_LOCATION, pageno, appSession.getUserId(),"");
                    }
                    else {
                        Searchcategories("", keyword, Constant.SELECTED_LOCATION, pageno, appSession.getUserId(),"");
                    }
                }else {
                    if (Constant.SELECTED_LOCATION.equals("")) {
                        Searchcategories(catname, "", Constant.UNSELECTED_LOCATION,pageno,appSession.getUserId(),"");
                    }
                    else {
                        Searchcategories(catname, "", Constant.SELECTED_LOCATION,pageno,appSession.getUserId(),"");
                    }
                }
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

    public Typeface setfont(String font){
        return Typeface.createFromAsset(getAssets(),"font/"+font);
    }
}
