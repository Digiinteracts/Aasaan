package com.assan;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import UtilApi.RemotRequest;
import Utils.AppConstants;
import Utils.Constant;
import Utils.Prefrence;
import Utils.RequestHandler;
import adapter.AddStoreCategory;
import adapter.SubscriptionSpinnerAdapter;

public class Subscriptions extends AppCompatActivity implements View.OnClickListener {

    Spinner basic_spinner, priority_spinner, home_spinner, sponser_spinner;
    List<String> basiclist = new ArrayList<String>(), prio_list = new ArrayList<String>(), home_list = new ArrayList<String>(), spon_list = new ArrayList<String>();
    List<String> monthid = new ArrayList<String>();
    TextView basic_amount_txt, prioritytxt, sponsertxt, hometxt, totaltxt;
    AddStoreCategory adapter2;
    int prioritySpnPosition = 0, homePosition = 0, sponserPos = 0;
    SubscriptionSpinnerAdapter adapter;
    CheckBox baisclistcheckbox, prioritycheckbox, homecheckbox, sponserchcekbox;
    ImageView imageview_cross, iv_upload, btn_home_acive, btn_home, btn_recent, btn_recent_active, btn_categorie, btn_categorie_active, btn_bookmak, btn_bookmark_active, btn_account, btn_account_active;
    LinearLayout ll_home, ll_recent, ll_categorie, ll_boobmark, ll_account, backicn;
    Prefrence sharePref;
    String SelectItmValue = "", temp = "", HometempVlu = "", SponserTemVlu = "", PrioritTxtvalue = "";
    boolean basicCheck = false, priority = false, homeCheck = false, sponserCheck = false, checkvalue = true,prioritycheck= false;
    int spinertotalnoItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscriptions);

        sharePref = new Prefrence();
        init();

        // new GetMonthList().execute();

        basiclist.add("Select");
        basiclist.add("1");
        basiclist.add("3");
        basiclist.add("6");
        basiclist.add("12");

        backicn.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_categorie.setOnClickListener(this);
        ll_account.setOnClickListener(this);
        ll_boobmark.setOnClickListener(this);

        spinnerOnclick();
        checkBokOnclick();
    }

    public void init() {
        basic_spinner = (Spinner) findViewById(R.id.basic_spinner);
        priority_spinner = (Spinner) findViewById(R.id.priority_spinner);
        home_spinner = (Spinner) findViewById(R.id.home_spinner);
        sponser_spinner = (Spinner) findViewById(R.id.sponser_spinner);

        basic_amount_txt = (TextView) findViewById(R.id.basictxt);
        prioritytxt = (TextView) findViewById(R.id.prioritytxt);
        hometxt = (TextView) findViewById(R.id.hometxt);
        sponsertxt = (TextView) findViewById(R.id.sponsertxt);
        totaltxt = (TextView) findViewById(R.id.totaltxt);

        backicn = (LinearLayout) findViewById(R.id.backicn);

        baisclistcheckbox = (CheckBox) findViewById(R.id.baisclistcheckbox);
        prioritycheckbox = (CheckBox) findViewById(R.id.prioritycheckbox);
        homecheckbox = (CheckBox) findViewById(R.id.homecheckbox);
        sponserchcekbox = (CheckBox) findViewById(R.id.sponserchcekbox);

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

        baisclistcheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
        prioritycheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
        homecheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
        sponserchcekbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));

    }

    public void spinnerOnclick() {

        basic_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                basicCheck = true;
                return false;
            }
        });

        priority_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                  prioritycheck = true;
                return false;
            }
        });
        home_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //homeCheck = true;
                return false;
            }
        });
        sponser_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //  sponserCheck = true;
                return false;
            }
        });

        adapter = new SubscriptionSpinnerAdapter(Subscriptions.this, basiclist, monthid, basiclist.size());
        basic_spinner.setAdapter(adapter);

        basic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                if (!basiclist.get(position).equals("Select")) {
                    baisclistcheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_chekbox));
                    GetPlanPrice("basic", basiclist.get(position));
                }
                Log.e("testonclicspiner","spin");
                SelectItmValue = basiclist.get(position);
                spinertotalnoItem = position + 1;

                adapter2 = new AddStoreCategory(Subscriptions.this, basiclist, monthid, spinertotalnoItem);
                priority_spinner.setAdapter(adapter2);

                //  adapter2 = new AddStoreCategory(Subscriptions.this, home_list,monthid,spinertotalnoItem);
                home_spinner.setAdapter(adapter2);

                //  adapter2 = new AddStoreCategory(Subscriptions.this, spon_list,monthid,spinertotalnoItem);
                sponser_spinner.setAdapter(adapter2);

                if (!basiclist.get(position).equals("Select")) {
                    if (!PrioritTxtvalue.trim().equals("") && !PrioritTxtvalue.equals("Select"))
                        if (Integer.parseInt(basiclist.get(position)) >= Integer.parseInt(PrioritTxtvalue)) {
                            priority_spinner.setSelection(prioritySpnPosition);
                            priority = true;
                        } else {
                            priority_spinner.setSelection(0);
                            priority = true;
                            prioritycheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                            prioritytxt.setText("0");
                        }
                }

                if (!basiclist.get(position).equals("Select"))
                    if (!HometempVlu.trim().equals("") && !HometempVlu.equals("Select"))
                        if (Integer.parseInt(basiclist.get(position)) >= Integer.parseInt(HometempVlu)) {
                            home_spinner.setSelection(homePosition);
                            homeCheck = true;
                        } else {
                            home_spinner.setSelection(0);
                            homeCheck = true;
                            homecheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                            hometxt.setText("0");
                        }

                if (!basiclist.get(position).equals("Select"))
                    if (!SponserTemVlu.trim().equals("") && !SponserTemVlu.equals("Select"))
                        if (Integer.parseInt(basiclist.get(position)) >= Integer.parseInt(SponserTemVlu)) {
                            sponser_spinner.setSelection(sponserPos);
                            sponserCheck = true;
                        } else {
                            sponser_spinner.setSelection(0);
                            sponserCheck = true;
                            sponserchcekbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                            sponsertxt.setText("0");
                        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        priority_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (prioritycheck)
                    GetPlanPrice("priority", basiclist.get(position));

                PrioritTxtvalue = basiclist.get(position);

                if (!PrioritTxtvalue.equals("Select")) {
                    prioritycheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_chekbox));
                    priority = true;
                }
                if (PrioritTxtvalue.equals("Select"))
                    prioritycheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));

                prioritySpnPosition = position;

                prioritycheckbox.setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        home_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homecheckbox.setChecked(true);

                HometempVlu = basiclist.get(position);

                if (!HometempVlu.equals("Select")) {
                    homecheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_chekbox));
                    homeCheck = true;
                    GetPlanPrice("banner", basiclist.get(position));
                }
                if (HometempVlu.equals("Select"))
                    homecheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                homePosition = position;

                //hometxt.setText("$ " + (position + 1) * 100);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sponser_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sponserchcekbox.setChecked(true);

                SponserTemVlu = basiclist.get(position);

                if (!SponserTemVlu.equals("Select")) {
                    sponserchcekbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_chekbox));
                    sponserCheck = true;
                    GetPlanPrice("ads", basiclist.get(position));
                }
                if (SponserTemVlu.equals("Select")) {
                    sponserchcekbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                    sponserCheck = false;
                }
                sponserPos = position;

              //  sponsertxt.setText("$ " + (position + 1) * 100);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void checkBokOnclick() {
        baisclistcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                baisclistcheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                prioritycheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                homecheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                sponserchcekbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));

                adapter = new SubscriptionSpinnerAdapter(Subscriptions.this, basiclist, monthid, basiclist.size());
                basic_spinner.setAdapter(adapter);


                adapter2 = new AddStoreCategory(Subscriptions.this, basiclist, monthid, spinertotalnoItem);
                priority_spinner.setAdapter(adapter2);

                //  adapter2 = new AddStoreCategory(Subscriptions.this, home_list,monthid,spinertotalnoItem);
                home_spinner.setAdapter(adapter2);

                // adapter2 = new AddStoreCategory(Subscriptions.this, spon_list,monthid,spinertotalnoItem);
                sponser_spinner.setAdapter(adapter2);

                totaltxt.setText("Total: $"+"0");
                basic_amount_txt.setText("0");
                prioritytxt.setText("0");
                hometxt.setText("0");
                sponsertxt.setText("0");

                sponser_spinner.setSelection(0);
                home_spinner.setSelection(0);
                priority_spinner.setSelection(0);
                basic_spinner.setSelection(0);
                //}

            }
        });

        prioritycheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (priority) {
                    prioritycheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                    priority_spinner.setSelection(0);
                    prioritytxt.setText("0");

                    int basicValue = Integer.parseInt(basic_amount_txt.getText().toString());
                    int homeValue = Integer.parseInt(hometxt.getText().toString());
                    int priorityValue = Integer.parseInt(prioritytxt.getText().toString());
                    int sponserValue = Integer.parseInt(sponsertxt.getText().toString());

                    int amount = basicValue + homeValue + priorityValue + sponserValue;
                    totaltxt.setText("Total: $"+""+amount);
                }

            }
        });

        homecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (homeCheck) {
                    homecheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                    home_spinner.setSelection(0);
                    hometxt.setText("0");

                    int basicValue = Integer.parseInt(basic_amount_txt.getText().toString());
                    int homeValue = Integer.parseInt(hometxt.getText().toString());
                    int priorityValue = Integer.parseInt(prioritytxt.getText().toString());
                    int sponserValue = Integer.parseInt(sponsertxt.getText().toString());

                    int amount = basicValue + homeValue + priorityValue + sponserValue;
                    totaltxt.setText("Total: $"+""+amount);
                }
            }
        });

        sponserchcekbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sponserCheck) {
                    sponserchcekbox.setButtonDrawable(getResources().getDrawable(R.drawable.green_unchek));
                    sponser_spinner.setSelection(0);
                    sponsertxt.setText("0");

                    int basicValue = Integer.parseInt(basic_amount_txt.getText().toString());
                    int homeValue = Integer.parseInt(hometxt.getText().toString());
                    int priorityValue = Integer.parseInt(prioritytxt.getText().toString());
                    int sponserValue = Integer.parseInt(sponsertxt.getText().toString());

                    int amount = basicValue + homeValue + priorityValue + sponserValue;
                    totaltxt.setText("Total: $"+""+amount);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.backicn:
                finish();
                break;

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

                Intent intenthome = new Intent(Subscriptions.this, Home.class);
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

                Intent intent = new Intent(Subscriptions.this, MoreScreen.class);
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

                Intent intcategory = new Intent(Subscriptions.this, CategoryActiviyt.class);
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

                if (sharePref.getUserID(Subscriptions.this).equals("null")) {

                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.filter);
                    //   dialog.setTitle(Html.fromHtml("<font color='#000000'>Please first login to see your bookmark</font>"));

                    TextView ok = (TextView) dialog.findViewById(R.id.ok);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome = new Intent(Subscriptions.this, LoginActivity.class);
                            startActivity(intenthome);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    Intent intenthome1 = new Intent(Subscriptions.this, BookmarkActivity.class);
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

                if (sharePref.getUserID(Subscriptions.this).equals("null")) {

                    Intent intent_account = new Intent(Subscriptions.this, LoginActivity.class);
                    startActivity(intent_account);
                    finish();
                } else {
                    Constant.TabLayout_Home_Status = false;
                    Constant.TabLayout_Category_Status = false;
                    Constant.TabLayout_Bookmark_Status = false;
                    Constant.TabLayout_Resent_Status = false;
                    Constant.TabLayout_Accounts_Status = true;

                    Intent intent6 = new Intent(Subscriptions.this, MyAccount.class);
                    startActivity(intent6);
                    finish();
                }
                break;
        }
    }

    public void GetPlanPrice(final String plan, final String month) {
        class PlanPrice extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();
            String result = "";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //loader = new Loader(Subscriptions.this);

                mProgressDialog = new ProgressDialog(Subscriptions.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<>();
                data.put("plan", plan);
                data.put("month", month);
                data.put("user_Id", sharePref.getUserID(Subscriptions.this));

                result = ruc.sendPostRequest(AppConstants.BASEURL + "getPlanPrice", data);

                Log.e("Month list data ", "teset" + result);
                Log.e("Month list data ", "data" + data);
                return result;
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    try {
                        mProgressDialog.dismiss();

                        JSONObject obj = new JSONObject(s);

                        if (plan.equals("basic"))
                            basic_amount_txt.setText(obj.getString("price"));
                        else if (plan.equals("priority"))
                            prioritytxt.setText(obj.getString("price"));
                        else if (plan.equals("banner"))
                            hometxt.setText(obj.getString("price"));
                        else if (plan.equals("ads"))
                            sponsertxt.setText(obj.getString("price"));

                        int basicValue = Integer.parseInt(basic_amount_txt.getText().toString());
                        int homeValue = Integer.parseInt(hometxt.getText().toString());
                        int priorityValue = Integer.parseInt(prioritytxt.getText().toString());
                        int sponserValue = Integer.parseInt(sponsertxt.getText().toString());

                        int amount = basicValue + homeValue + priorityValue + sponserValue;
                        totaltxt.setText("Total: $"+""+amount);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        PlanPrice planPrice = new PlanPrice();
        planPrice.execute();
    }
}
