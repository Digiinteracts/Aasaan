package com.assan;

import android.annotation.TargetApi;
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
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DTO.CityList;
import DTO.Country;
import DTO.LandMarkDTO;
import ImageLoding.Loader;
import UtilApi.RemotRequest;
import Utils.AppConstants;
import Utils.Prefrence;
import Utils.RequestHandler;
import adapter.CityListAdapter;
import adapter.CustomSpinnerAdapter;
import adapter.LandMarkAdapter;

public class AddNewCompany extends AppCompatActivity implements View.OnClickListener {

    Spinner countrySpinner,statespinner,landmarkspinner,licencespinner;
    private boolean isSpinnerTouched = false,stateSpinnerCheck = false,landmarkcheck= false;
    String countryid="",stateId="",name="",email="",no_of_store="",building_name="",street="",pobox="",contact_person="",personal_no="",phone_no=""
            ,phone_no2="",fax="",website="",licence_no="",city="",state="",message="",landmark="",licence_type="",profileImg="",companyId;
    Country country2;
    EditText edt_noofstore,edt_building_name,edt_street,edt_pobox,edt_phonno,edt_phonno2,edt_fax,edt_website
            ,edt_licenceno;
    Button btn_save;
    TextView edt_title,edt_email,edt_cperson,edt_personal_no;
    Prefrence sharePre;
    ImageView imageview_cross;
    List<String> list=new ArrayList<String>();
     ArrayAdapter<String> adapter;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1,company_List_flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_company);

        Bundle bundle = getIntent().getExtras();

        list.add("LLC");
        list.add("FZE");
        list.add("FZC");
        list.add("Other");
        adapter = new ArrayAdapter<String>(this, R.layout.customspinner2, list);

        if (bundle!=null){
            company_List_flag = bundle.getInt("company_List_flag");

            if (company_List_flag == 7){

                companyId = bundle.getString("id");
                new GetCompanyDetail().execute();
                new CountryList().execute();
                new StateList().execute();
                new LandMark().execute();
            }
        }

        sharePre = new Prefrence();
        country2 = new Country();

        init();

        OntuchListener();

        btn_save.setOnClickListener(this);
       // iv_upload.setOnClickListener(this);
        imageview_cross.setOnClickListener(this);
    }

    public void init(){

        countrySpinner = (Spinner)findViewById(R.id.cityspinner);
        statespinner = (Spinner)findViewById(R.id.statespinner);
        landmarkspinner = (Spinner)findViewById(R.id.landmarkspinner);
        licencespinner = (Spinner)findViewById(R.id.licencespinner);

        edt_noofstore = (EditText)findViewById(R.id.edt_noofstore);
        edt_building_name = (EditText)findViewById(R.id.edt_building_name);
        edt_street = (EditText)findViewById(R.id.edt_street);
        edt_pobox = (EditText)findViewById(R.id.edt_pobox);

        edt_phonno = (EditText)findViewById(R.id.edt_phonno);
        edt_phonno2 = (EditText)findViewById(R.id.edt_phonno2);
        edt_fax = (EditText)findViewById(R.id.edt_fax);
        edt_website = (EditText)findViewById(R.id.edt_website);
        edt_licenceno = (EditText)findViewById(R.id.edt_licenceno);

        edt_title = (TextView) findViewById(R.id.edt_title);
        edt_email = (TextView) findViewById(R.id.edt_email);
        edt_cperson = (TextView)findViewById(R.id.edt_cperson);
        edt_personal_no = (TextView)findViewById(R.id.edt_personal_no);
        //iv_upload = (ImageView)findViewById(R.id.iv_upload);
        imageview_cross = (ImageView)findViewById(R.id.imageview_cross);

        btn_save = (Button)findViewById(R.id.btn_save);

    }

    public void OntuchListener(){
        countrySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new CountryList().execute();
                isSpinnerTouched = true;
                return false;
            }
        });

        statespinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isSpinnerTouched)
                new StateList().execute();

                isSpinnerTouched = false;
                stateSpinnerCheck = true;
                return false;
            }
        });
        landmarkspinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (landmarkcheck)
                    new LandMark().execute();

                landmarkspinner.setPrompt("Title");
                landmarkcheck= false;
                return false;
            }
        });

        licencespinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                licencespinner.setAdapter(adapter);
                licencespinner.setPadding(0,0,0,0);
                return false;
            }
        });

        licencespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int res = position+1;
                licence_type = ""+res;
                Log.e("Licentype ","tt "+licence_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
            });
        }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_save:

              //  name = edt_title.getText().toString();
                no_of_store = edt_noofstore.getText().toString();
                building_name = edt_building_name.getText().toString();
                street = edt_street.getText().toString();
                pobox = edt_pobox.getText().toString();
                contact_person = edt_cperson.getText().toString();
                personal_no = edt_personal_no.getText().toString();
                phone_no = edt_phonno.getText().toString();
                phone_no2 = edt_phonno2.getText().toString();
                fax = edt_fax.getText().toString();
                website = edt_website.getText().toString();
                licence_no = edt_licenceno.getText().toString();

                if (licence_no.equals("") || licence_no.equals(null)
                        || city.equals("") || city.equals(null) || state.equals("") || state.equals(null))
                    valiDate();
                else
                    Save();
                break;

           /* case R.id.iv_upload:
                selectImage();
                break;
*/
            case R.id.imageview_cross:
                finish();
                break;
        }
    }

    public void valiDate(){
        if (city == null || city.equals("")) {
            message = ("Select: City");
            DialogBox();
            message = "";
        } else if (state == null || state.equals("")) {
            message = ("Select: State");
            DialogBox();
            message = "";
        }
        else if (licence_no == null || licence_no.equals("")) {
            message = ("Enter: Licence no.");
            DialogBox();
            message = "";
        }
    }

    public void DialogBox(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNewCompany.this);
        alertDialog.setMessage(message);
        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void Save(){

            class SaveFormData extends AsyncTask<String, Void, String> {
                 ProgressDialog mProgressDialog;
                RegisterUserClass ruc = new RegisterUserClass();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                mProgressDialog = new ProgressDialog(AddNewCompany.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
                }
                @Override
                protected String doInBackground(String... params) {

                    String result="";
                    RequestHandler ruc = new RequestHandler();

                    HashMap<String, String> data = new HashMap<String, String>();

                    data.put("name", name);
                    data.put("phone_no1",phone_no);
                    data.put("licenceType",licence_type);
                    data.put("noOfStore",no_of_store);
                    data.put("phone_no2",phone_no2);
                    data.put("city",city);
                    data.put("postBox",pobox);
                    data.put("street",street);
                    data.put("contactMob",personal_no);
                    data.put("licence_no",licence_no);
                    data.put("faxNO",fax);
                    data.put("compWebAddr",website);
                    data.put("contactPerson",contact_person);
                    data.put("email",email);
                    data.put("landmark",landmark);
                    data.put("building",building_name);
                    data.put("area",state);
                    data.put("profileImage",profileImg);
                    data.put("userId",sharePre.getUserID(AddNewCompany.this));

                    //edit form==================

                        data.put("id",companyId);
                        result = ruc.sendPostRequest(AppConstants.BASEURL + "editCompany", data);

                    Log.e("AddnewCompany data ","teset"+result);
                    Log.e("AddnewCompany data ","check"+data);

                    return result;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if(s !=null) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            if (jsonObject.getString("status").equals("1"))
                            {
                                mProgressDialog.dismiss();
                                finish();
                            }
                            else {
                                Toast.makeText(AddNewCompany.this,"Try again",Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            new SaveFormData().execute();
        }

    class CountryList extends AsyncTask<String, Void, String> {
           // ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                /*mProgressDialog = new ProgressDialog(AddNewCompany.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();*/
            }
            @Override
            protected String doInBackground(String... params) {

                String result="";
                RequestHandler ruc = new RequestHandler();

                 result = ruc.sendGetRequest(AppConstants.BASEURL + "getStateCityLandmark");

                Log.e("country data ","teset"+result);

                return result;
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null) {

                    List<Country> list = new ArrayList<>();

                    try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("state");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobj = jsonArray.getJSONObject(i);
                                Country country = new Country();

                                country.setName(jobj.getString("name"));
                                country.setId(jobj.getString("id"));

                                list.add(country);
                            }
                            country2.setCountry_list(list);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    if (company_List_flag == 7){

                        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddNewCompany.this, country2.getCountry_list(), R.layout.customspinner2);
                        countrySpinner.setAdapter(adapter);

                        if (!String.valueOf(city).equals("") && !city.equals("0"))
                        for (int i=0; i<country2.getCountry_list().size(); i++){
                            if (country2.getCountry_list().get(i).getId().equals(city)) {
                                countrySpinner.setSelection(i);
                            }
                        }
                    }
                    else {
                        Log.e("country data ", "size" + country2.getCountry_list().size());
                        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddNewCompany.this, country2.getCountry_list(), R.layout.customspinner2);
                        countrySpinner.setAdapter(adapter);
                        countrySpinner.setPopupBackgroundResource(R.drawable.spinnerstyle);
                    }
                    countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (isSpinnerTouched) {
                                city = country2.getCountry_list().get(position).getId();
                                countryid = country2.getCountry_list().get(position).getId();
                                Log.e("country data ", "check ce");

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                   // mProgressDialog.dismiss();
                }
            }
        }

    class StateList extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();
            CityList clist;

        @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(AddNewCompany.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                Log.e("country id ","teset"+countryid);
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                String result="";
                RequestHandler ruc = new RequestHandler();

                if (company_List_flag == 7)
                result = ruc.sendGetRequest(AppConstants.BASEURL + "getStateCityLandmark/"+city);

              else
                    result = ruc.sendGetRequest(AppConstants.BASEURL + "getStateCityLandmark/"+countryid);

                Log.e("country data ","teset"+result);

                return result;
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null) {

                List<CityList> citylist = new ArrayList<>();
                    final CityList clist2  =new CityList();

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("city");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobj = jsonArray.getJSONObject(i);
                            clist = new CityList();

                            clist.setName(jobj.getString("name"));
                            clist.setId(jobj.getString("id"));

                            citylist.add(clist);
                        }
                        //clist.setName("Selected");
                       // citylist.add(clist);
                        clist2.setCity_list(citylist);

                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    Log.e("state data ", "iddd" +state);

                    if (company_List_flag == 7){

                        CityListAdapter adapter = new CityListAdapter(AddNewCompany.this, clist2.getCity_list(), R.layout.customspinner2);
                        statespinner.setAdapter(adapter);

                        if (!state.equals("0") && !String.valueOf(state).equals(""))
                        for (int i=0; i<clist2.getCity_list().size(); i++){
                            if (clist2.getCity_list().get(i).getId().equals(state)) {
                                statespinner.setSelection(i);
                            }
                        }
                    }
                    else {
                        if (stateSpinnerCheck) {
                            CityListAdapter adapter = new CityListAdapter(AddNewCompany.this, clist2.getCity_list(), R.layout.customspinner2);
                            statespinner.setAdapter(adapter);
                         //   statespinner.setSelection(statespinner.getCount());
                        }
                    }
                        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (stateSpinnerCheck) {
                                state = clist2.getCity_list().get(position).getId();
                               // stateId = clist2.getCity_list().get(position).getId();
                                landmarkcheck= true;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    mProgressDialog.dismiss();
                }
            }
        }

    class LandMark extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();
            LandMarkDTO lmlist;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(AddNewCompany.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                Log.e("country id ","teset"+countryid);
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                String result="";
                RequestHandler ruc = new RequestHandler();

                result = ruc.sendGetRequest(AppConstants.BASEURL + "getCityLandmark/" + state);
                    Log.e("landmark data edit ","ed "+result);

                Log.e("landmark data ","teset"+result);

                return result;
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null) {

                List<LandMarkDTO> landMarkDTOs = new ArrayList<>();
                    final LandMarkDTO landmarklist  =new LandMarkDTO();

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("landmark");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobj = jsonArray.getJSONObject(i);

                            lmlist = new LandMarkDTO();

                            lmlist.setName(jobj.getString("name"));
                            lmlist.setId(jobj.getString("id"));

                            landMarkDTOs.add(lmlist);
                        }
                        landmarklist.setLandMarkDTOList(landMarkDTOs);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    Log.e("state data ", "size" +landmarklist.getLandMarkDTOList().size());

                    if (company_List_flag == 7){

                        LandMarkAdapter adapter = new LandMarkAdapter(AddNewCompany.this, landmarklist.getLandMarkDTOList(), R.layout.customspinner2);
                        landmarkspinner.setAdapter(adapter);

                        if (!landmark.equals("0") && !String.valueOf(landmark).equals("") ) {
                            for (int i = 0; landmarklist.getLandMarkDTOList().size() > i; i++) {
                                if (landmarklist.getLandMarkDTOList().get(i).getId().equals(landmark)) {
                                    landmarkspinner.setSelection(i);
                                }
                            }
                        }
                    }
                    else {
                        LandMarkAdapter adapter = new LandMarkAdapter(AddNewCompany.this, landmarklist.getLandMarkDTOList(), R.layout.customspinner2);
                        landmarkspinner.setAdapter(adapter);
                    }
                    landmarkspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                           landmark = landmarklist.getLandMarkDTOList().get(position).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    mProgressDialog.dismiss();
                }
            }
        }

    class GetCompanyDetail extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RemotRequest ruc = new RemotRequest();
            Loader loader;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loader = new Loader(AddNewCompany.this);

                mProgressDialog = new ProgressDialog(AddNewCompany.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                Log.e("country id ","teset"+countryid);
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                String result="";
                RequestHandler ruc = new RequestHandler();

                result = ruc.sendGetRequest(AppConstants.BASEURL + "companyDetails/"+companyId);

                Log.e("company data ","teset"+result);

                return result;
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject.getString("status").equals("1")){

                            JSONObject jobj = jsonObject.getJSONObject("data");

                            edt_title.setText(jobj.getString("title"));
                            name = edt_title.getText().toString();

                            edt_email.setText(jobj.getString("email"));
                            email = edt_email.getText().toString();

                            edt_noofstore.setText(jobj.getString("no_store"));
                            edt_building_name.setText(jobj.getString("building_no"));
                            edt_street.setText(jobj.getString("street"));
                            //edt_pobox.setText(jobj.getString("title"));
                            edt_cperson.setText(jobj.getString("contact_person"));

                            edt_personal_no.setText(jobj.getString("personal_no"));

                            edt_phonno.setText(jobj.getString("phone_no"));
                            edt_phonno2.setText(jobj.getString("phone_no2"));
                            edt_fax.setText(jobj.getString("fax"));
                            edt_website.setText(jobj.getString("website"));
                            edt_licenceno.setText(jobj.getString("licence_no"));
                            licence_type = jobj.getString("licence_type");
                            city = jobj.getString("state");
                            state = jobj.getString("city");
                            landmark = jobj.getString("landmark_id");
                            //loader.displayImage(jsonObject.getString("image")+jobj.getString("logo"),iv_upload);

                            licencespinner.setAdapter(adapter);
                            for (int i = 0; i < list.size(); i++) {
                                Log.e("licentye ","t "+String.valueOf(licence_type));
                                if (String.valueOf(licence_type).trim().equals("")){

                                }else {
                                    int ltype = Integer.parseInt(licence_type);
                                    int checkdata = i + 1;
                                    if (checkdata == ltype) {
                                        licencespinner.setSelection(i);
                                    }
                                }
                            }
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewCompany.this);
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

        //iv_upload.setImageBitmap(thumbnail);
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
        profileImg= Base64.encodeToString(b, Base64.DEFAULT);

        //iv_upload.setImageBitmap(bm);
    }

}
