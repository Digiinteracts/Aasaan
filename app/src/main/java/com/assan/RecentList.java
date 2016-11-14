package com.assan;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import DTO.RecentOutlet;
import Utils.Constant;
import Utils.Prefrence;
import Utils.RecyclerItemClickListener;
import adapter.RecentAdapter;

public class RecentList extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    Prefrence sharepref;
    ArrayList<RecentOutlet> list = null;
    boolean check = true;
    TextView noRecord;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account,tv_search_outlet;
    ImageView btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_list);

        sharepref = new Prefrence();
        list = new ArrayList<>();
        init();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_listview);

        if (sharepref.getRlist(RecentList.this).equals("null")){
            noRecord.setVisibility(View.VISIBLE);
        }
        else {
            list = sharepref.getRecentData(RecentList.this);
            Collections.reverse(list);
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(i).getRecent_id().equals(list.get(j).getRecent_id())) {
                        list.remove(j);
                        j--;
                    }
                }
            }

            RecentAdapter adapter = new RecentAdapter(RecentList.this, list);
            recyclerView.setAdapter(adapter);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecentList.this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(RecentList.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(), OutletDetails.class);
                Bundle b = new Bundle();
                b.putString("id", list.get(position).getRecent_id());
                b.putInt("Resent_List_flag",14);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                // Toast.makeText(getApplicationContext(),"positin "+sharepref.getRecentData(RecentList.this).get(position).getRecent_name(),Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void init(){

        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_recent = (LinearLayout) findViewById(R.id.ll_recent);
        ll_categorie = (LinearLayout) findViewById(R.id.ll_categorie);
        ll_boobmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        ll_account = (LinearLayout) findViewById(R.id.ll_account);
        tv_search_outlet = (LinearLayout) findViewById(R.id.tv_search_outlet);

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
        noRecord = (TextView) findViewById(R.id.noRecord);

        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);
        tv_search_outlet.setOnClickListener(this);
        btn_recent_active.setVisibility(View.VISIBLE);
        btn_recent.setVisibility(View.GONE);

        if (String.valueOf(sharepref.getUserID(RecentList.this)).equals("null")) {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        } else {
            btn_account.setBackgroundResource(R.drawable.account_icon);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_home) {
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

            Intent intenthome = new Intent(RecentList.this, Home.class);
            startActivity(intenthome);
            finish();

        } else if (v.getId() == R.id.ll_recent) {
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


        }
        else if (v.getId() == R.id.ll_categorie) {
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

            Intent intcategory = new Intent(RecentList.this, CategoryActiviyt.class);
            startActivity(intcategory);
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

            if (sharepref.getUserID(RecentList.this).equals("null")) {

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter);
             //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                TextView ok = (TextView)dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenthome = new Intent(RecentList.this, LoginActivity.class);
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

                Intent intenthome = new Intent(RecentList.this, BookmarkActivity.class);
                startActivity(intenthome);
                finish();
            }

        } else if (v.getId() == R.id.ll_account) {

            btn_bookmark_active.setVisibility(View.GONE);

            btn_home_acive.setVisibility(View.GONE);
            btn_categorie_active.setVisibility(View.GONE);
            btn_recent_active.setVisibility(View.GONE);

            btn_bookmak.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            btn_categorie.setVisibility(View.VISIBLE);
            btn_recent.setVisibility(View.VISIBLE);


            if (sharepref.getUserID(RecentList.this).equals("null")) {

                Intent intent = new Intent(RecentList.this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else {
                Constant.TabLayout_Home_Status = false;
                Constant.TabLayout_Category_Status = false;
                Constant.TabLayout_Bookmark_Status = false;
                Constant.TabLayout_Resent_Status = false;
                Constant.TabLayout_Accounts_Status= true;

                Intent intent = new Intent(RecentList.this, MyAccount.class);
                startActivity(intent);
                finish();
            }
        }

        else if (v.getId() == R.id.tv_search_outlet) {

            Intent intent = new Intent(RecentList.this, SearchLocation.class);
            Log.e("result: Search Click",Constant.CatArray.toString());
            intent.putExtra("KEY_SEARCH", 2);
            intent.putExtra("CAT_ARRAY", Constant.CatArray);
            intent.putExtra("latitude", "");
            intent.putExtra("longitude", "");
            startActivity(intent);

        }
    }
}
