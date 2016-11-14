package DAO;

import android.content.Context;
import android.util.Log;

import com.assan.RegisterUserClass;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DTO.OutletListModel;
//import DTO.OutletListModel.OutletListFeaturedModel;
import Utils.AppConstants;
import Utils.AppSession;


/**
 * Created by Sonu Saini on 5/6/2016.
 */
public class SearchLocationListDAO extends RegisterUserClass {
    RegisterUserClass ruc = new RegisterUserClass();
    Context context;
    AppSession appSession;

    public SearchLocationListDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public List<OutletListModel> getoutletDTO(String location){

        try{
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("location", location);
            Log.e("ttttd", "json ");
            String json_response = ruc.sendPostRequest(AppConstants.BASEURL +"locationSearch", data);

            if (json_response != null) {
                Log.e("tttt", "json " + json_response.toString());
                Log.v("searchloction", "json " + json_response.toString());
                return Outletjsonpars(json_response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<OutletListModel> getoutletDTO(String cat_id, String sub_cat_id, String location, String page, String user_id, String keyword, String cat_name, String filter){

        try{
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("cat_id", cat_id);
            data.put("sub_cat_id",sub_cat_id);
            data.put("location","dubai");
            data.put("page", page);
            data.put("user_id", user_id);
            data.put("keyword", keyword);
            data.put("cat_name", cat_name);
            data.put("filter", "");
            String json = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.SEARCHCATEGORIES, data);
            Log.e("search cat Request",json);

            if (json != null) {
                Log.v("", "json " + json.toString());
                return Outletjsonpars(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<OutletListModel> Outletjsonpars(String json) {
        // from is 1 when data parse after login or registration otherwise 0

        OutletListModel userDTO = new OutletListModel();
        OutletListModel userDTO2 = new OutletListModel();
        List<OutletListModel> dtos = new ArrayList<OutletListModel>();
        List<OutletListModel> dtos2 = new ArrayList<OutletListModel>();
             try {
                 Object object = new JSONTokener(json).nextValue();

                 if (object instanceof JSONObject) {
                     JSONObject jsonObject = new JSONObject(json);
                    // List<OutletListModel> dtos = new ArrayList<OutletListModel>();
                     Log.e("Outlet_details_result",jsonObject.toString());
                    /* if (jsonObject.has("status")){
                         userDTO.setSuccess(jsonObject.getString("status"));
                     }*/
                     if (jsonObject.has("location")){
                         JSONArray location = jsonObject.getJSONArray("location");
                         for (int i = 0;i<location.length();i++ ){
                             OutletListModel dto = new OutletListModel();
                             Log.e("NAME++",location.getJSONObject(i).getString("name"));
                             dto.setOutlet_name(location.getJSONObject(i).getString("name"));
                             dtos.add(dto);
                         }
                         userDTO.setAdvertiseDTOs(dtos);
                           }
                     else if (jsonObject.has("result")){
                         JSONArray location = jsonObject.getJSONArray("result");
                         for (int i = 0;i<location.length();i++ ){
                             OutletListModel dto = new OutletListModel();
                             Log.e("NAME++",location.getJSONObject(i).getString("name"));
                             dto.setOutlet_name(location.getJSONObject(i).getString("name"));
                             dtos2.add(dto);
                         }
                         userDTO2.setAdvertiseDTOs(dtos2);
                         }

                     if (jsonObject.has("status")){
//                         if (jsonObject.has("location")) {
                             userDTO.setSuccess(jsonObject.getString("status"));
                             dtos.add(userDTO);
//                         }

                     }
                     else if (jsonObject.has("success")){
                         userDTO2.setSuccess(jsonObject.getString("success"));
                         dtos2.add(userDTO2);
                     }
                     if (dtos2.size()>0){
                         dtos =dtos2;
                     }

                 } } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dtos;
    }

    public List<OutletListModel> getoutletDTO( String location,String keyword){
        Log.e("pppp cat Request","current ");
        try{
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("location",location);
            data.put("keyword", keyword);
            data.put("deviceType", "2");

            //String json = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.SEARCHCATEGORIES, data);
            String json = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.SEARCHCATEGORIESKEY, data);
            Log.e("dddddd","current "+json);
            Log.e("search cat Request","current "+json);
            Log.e("dddddd","current "+data);

            if (json != null) {
                Log.v("jjjiiitt", "json " + json.toString());
                return Outletjsonparsforkeyword(json);
            }
        }catch (Exception e){

            e.printStackTrace();
        }
        return null;
    }
    public List<OutletListModel> Outletjsonparsforkeyword(String json) {
        // from is 1 when data parse after login or registration otherwise 0

        OutletListModel userDTO = new OutletListModel();
        OutletListModel userDTO2 = new OutletListModel();
        List<OutletListModel> dtos = new ArrayList<OutletListModel>();
        List<OutletListModel> dtos2 = new ArrayList<OutletListModel>();
        try {
            Object object = new JSONTokener(json).nextValue();

            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                // List<OutletListModel> dtos = new ArrayList<OutletListModel>();
                Log.e("Outlet_details_result",jsonObject.toString());
                    /* if (jsonObject.has("status")){
                         userDTO.setSuccess(jsonObject.getString("status"));
                     }*/
                if (jsonObject.has("location")){
                    JSONArray location = jsonObject.getJSONArray("location");
                    for (int i = 0;i<location.length();i++ ){
                        OutletListModel dto = new OutletListModel();
                        Log.e("NAME++",location.getJSONObject(i).getString("name"));
                        dto.setOutlet_name(location.getJSONObject(i).getString("name"));
                        dtos.add(dto);
                    }
                    userDTO.setAdvertiseDTOs(dtos);
                }
                else if (jsonObject.has("result")){
                    JSONArray location = jsonObject.getJSONArray("result");
                    for (int i = 0;i<location.length();i++ ){
                        OutletListModel dto = new OutletListModel();
                        Log.e("NAME++",location.getJSONObject(i).getString("name"));
                        dto.setOutlet_name(location.getJSONObject(i).getString("name"));
                        dto.setOutlet_id(location.getJSONObject(i).getString("id"));
                        dtos2.add(dto);
                    }
                    userDTO2.setAdvertiseDTOs(dtos2);
                }

                if (jsonObject.has("status")){
//                         if (jsonObject.has("location")) {
                    userDTO.setSuccess(jsonObject.getString("status"));
                    dtos.add(userDTO);
//                         }

                }
                else if (jsonObject.has("success")){
                    userDTO2.setSuccess(jsonObject.getString("success"));
                    dtos2.add(userDTO2);
                }
                if (dtos2.size()>0){
                    dtos =dtos2;
                }

            } } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dtos;
    }
}
