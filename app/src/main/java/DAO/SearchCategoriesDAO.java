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

import DTO.SearchCategoriesDTO;
import DTO.searchhvDTO;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.Constant;
import Utils.RequestHandler;

/**
 * Created by Sonu Saini on 5/4/2016.
 */


public class SearchCategoriesDAO extends RequestHandler{
    RequestHandler ruc = new RequestHandler();
    String serviceUrl = "";
    Context context;
    AppSession appSession;
    public SearchCategoriesDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public SearchCategoriesDTO SearchCategories(String cat_name, String keyword, String location, String page, String user_id,String latitude,String longitude,String filter){

        try{
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("cat_name", cat_name);
            data.put("sub_cat_id","");
            data.put("location",location);
            data.put("page", page);
            data.put("user_id", user_id);
            data.put("keyword", keyword);
            data.put("user_lat",latitude);
            data.put("user_long",longitude);
            data.put("filter",filter);

            String json = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.SEARCHCATEGORIES, data);
            Log.v("search categories", json);
            Log.v("datacategories", data.toString());
            Log.e("success333:","dd "+json);

            if (json != null) {
                Log.v("", "json " + json.toString());
                return SearchCategoriesjsonpars(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<SearchCategoriesDTO> getbookmakoutlet(String user_id, String type, String filter, String location, String page){


        try{
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("user_id",user_id);
            data.put("type",type);
            data.put("filter",filter);
            data.put("location", "dubai");
            data.put("page", page);
            data.put("user_lat", Constant.LATITUDE);
            data.put("user_long", Constant.LONGITUDE);

            String json = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.GETOUTLETBOOKMARK, data);

            Log.v("search categories", json);
            Log.e("Request for Bookmark",data.toString());
            Log.e("BOOKMARK JSON", json);
            if (json != null) {
                Log.v("", "json sumit" + SearchCategoriesjsonpars(json));
                return SearchBookmarkJsonpars(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public SearchCategoriesDTO deleteReview(String review_id){
        RegisterUserClass ruc1 = new RegisterUserClass();
        SearchCategoriesDTO categoriesDTO = new SearchCategoriesDTO();
        try{
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("id", review_id);
            String json = ruc1.sendPostRequest(AppConstants.BASEURL+"deleteReview", data);

            if (json != null) {
                JSONObject object = new JSONObject(json.toString());
                categoriesDTO.setMsg(object.getString("msg"));
                categoriesDTO.setDeletemsg_status(object.getString("status"));
                return categoriesDTO;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }public SearchCategoriesDTO getReview(String user_id){

        try{
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("user_id", user_id);
            String json = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.GETOUTLETREVIEW, data);

            if (json != null) {
                return getreviewjson(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public SearchCategoriesDTO SearchCategoriesjsonpars(String json) {

        SearchCategoriesDTO userDTO = new SearchCategoriesDTO();

        try {
            Object object = new JSONTokener(json).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("status")){
                    userDTO.setSuccess(jsonObject.getString("status"));
                    // Log.e("status:userDTO",userDTO.toString());
                }
                userDTO.setSuccess(jsonObject.getString("success"));

                if (jsonObject.has("result")){
                    JSONArray jsonSOSArray = jsonObject.getJSONArray("result");
                    // Log.e("JSONArray:anilatri",jsonSOSArray.toString());
                    List<SearchCategoriesDTO> dtos = new ArrayList<SearchCategoriesDTO>();

                    for (int i = 0; i < jsonSOSArray.length(); i++){

                        SearchCategoriesDTO userDTO2 = new SearchCategoriesDTO();
                        JSONObject jsonobject=jsonSOSArray.getJSONObject(i);

                        userDTO2.setName(jsonobject.getString("name"));

                        userDTO2.setMarketType(jsonobject.getString("market_type"));

                        userDTO2.setBookmark(jsonobject.getString("bookmark"));

                        userDTO2.setRating(jsonobject.getString("rating"));

                        userDTO2.setMobile(jsonobject.getString("phone_no"));

                        userDTO2.setDistance(jsonobject.getString("distance")+"km");

                        userDTO2.setLocation(jsonobject.getString("location"));

                        userDTO2.setImage(jsonobject.getString("image"));

                        userDTO2.setId(jsonobject.getString("id"));

                        userDTO2.setSearchcat_lat(jsonobject.getString("lat"));

                        userDTO2.setSearchcat_long(jsonobject.getString("long"));

                        dtos.add(userDTO2);

                    }
                    userDTO.setCategoriesList(dtos);

                }

                if (jsonObject.has("featured")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("featured");
                    List<searchhvDTO> dtos = new ArrayList<searchhvDTO>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        searchhvDTO dtoitem = new searchhvDTO();
                        JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                        if (jsonObject3.has("name"))
                            dtoitem.setName(jsonObject3.getString("name"));
                            dtoitem.setId(jsonObject3.getString("id"));
                        Log.e("name",jsonObject3.getString("name"));
                        if (jsonObject3.has("outlet_featured"))
                            dtoitem.setImage(jsonObject3.getString("outlet_featured"));

                        dtos.add(dtoitem);
                        Log.e("dtoitem:",dtoitem.toString());
                    }
                    userDTO.setCategoriesDtos(dtos);
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userDTO;
    }

    public List<SearchCategoriesDTO> SearchBookmarkJsonpars(String json) {

        List<SearchCategoriesDTO> userDtolist = new ArrayList<>();
        // from is 1 when data parse after login or registration otherwise 0

        SearchCategoriesDTO userDTO = new SearchCategoriesDTO();

        try {
            Object object = new JSONTokener(json).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("status")){
                    userDTO.setSuccess(jsonObject.getString("status"));
                    // Log.e("status:userDTO",userDTO.toString());
                    //  userDtolist.add(userDTO);
                }
                userDTO.setSuccess(jsonObject.getString("success"));
                //userDtolist.add(userDTO);

                if (jsonObject.has("result")){
                    JSONArray jsonSOSArray = jsonObject.getJSONArray("result");
                    // Log.e("JSONArray:anilatri",jsonSOSArray.toString());
                    List<searchhvDTO> dtos = new ArrayList<searchhvDTO>();

                    for (int i = 0; i < jsonSOSArray.length(); i++){
                        SearchCategoriesDTO userDTO1 = new SearchCategoriesDTO();
                        JSONObject jsonobject=jsonSOSArray.getJSONObject(i);

                        userDTO1.setName(jsonobject.getString("name"));

                        userDTO1.setMarketType(jsonobject.getString("market_type"));

                        userDTO1.setBookmark(jsonobject.getString("bookmark"));

                        userDTO1.setRating(jsonobject.getString("rating"));

                        userDTO1.setMobile(jsonobject.getString("phone_no"));

                        userDTO1.setDistance(jsonobject.getString("distance"));

                        userDTO1.setLocation(jsonobject.getString("location"));

                        userDTO1.setImage(jsonobject.getString("image"));

                        userDTO1.setId(jsonobject.getString("id"));

                        userDTO1.setSearchcat_lat(jsonobject.getString("lat"));

                        userDTO1.setSearchcat_long(jsonobject.getString("long"));


                        userDtolist.add(userDTO1);
                        userDTO.setCategoriesDtos(dtos);
                        // Log.e("JSONArray:anilatri",jsonSOSArray.toString());
                    }


                    // userDtolist.add(userDTO);

                    Log.e("size ","dd "+userDtolist.size());

                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userDtolist;
    }

    public SearchCategoriesDTO getreviewjson(String json) {

        SearchCategoriesDTO userDTO = new SearchCategoriesDTO();

        try {
            Object object = new JSONTokener(json).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("success")){
                    userDTO.setSuccess(jsonObject.getString("success"));
                }

                List<SearchCategoriesDTO> rlist = new ArrayList<>();

                if (jsonObject.has("result")){
                    JSONArray jsonSOSArray = jsonObject.getJSONArray("result");
                    Log.e("getreviewjson:anil",jsonSOSArray.toString());
                    for (int i = 0; i < jsonSOSArray.length(); i++){
                        JSONObject jsonobject=jsonSOSArray.getJSONObject(i);
                        Log.e("getreviewjson:anil","CHEKCK 1 ");
                        SearchCategoriesDTO userDTO1 = new SearchCategoriesDTO();
                        userDTO1.setName(jsonobject.getString("name"));
                        userDTO1.setRating(jsonobject.getString("rating"));
                        userDTO1.setLocation(jsonobject.getString("location"));
                        userDTO1.setImage(jsonobject.getString("image"));
                        userDTO1.setId(jsonobject.getString("review_id"));
                        userDTO1.setReview_created(jsonobject.getString("review_created"));
                        userDTO1.setCurrent_date(jsonobject.getString("current_date"));
                        userDTO1.setLikecount(jsonobject.getString("likecount"));
                        userDTO1.setReview(jsonobject.getString("review"));
                        userDTO1.setLike(jsonobject.getString("like"));
                        userDTO1.setDay_ago(jsonobject.getString("ago"));
                        userDTO1.setStatus(jsonobject.getString("status"));

                        rlist.add(userDTO1);
                        userDTO.setReviewLiset(rlist);
                    }

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userDTO;
    }
}
