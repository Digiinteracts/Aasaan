package DAO;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import DTO.BookmarkDTO;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.RequestHandler;

/**
 * Created by sonu saini on 5/11/2016.
 */


public class BookmarkDAO extends RequestHandler {
    RequestHandler ruc = new RequestHandler();
    String serviceUrl = "";
    Context context;
    AppSession appSession;
    public BookmarkDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public BookmarkDTO getbookmark(String refid, String type, String ref_type, String value,String user_id){

        try{
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("ref_id", refid);
            data.put("type",type);
            data.put("ref_type",ref_type);
            data.put("value", value);
            data.put("user_id", user_id);


            /* data.put("user_id", user_id);
            data.put("type",type);
            data.put("ref_type",ref_type);
            data.put("location", location);
            data.put("page", page);
*/
            String json = ruc.sendPostRequest(AppConstants.BASEURL+AppConstants.BOOKMARKLIKE, data);
            Log.e("parametr", user_id+type+ref_type+value);
            Log.e("Bookmark", json);

            if (json != null) {
                Log.v("jitu", "json " + json.toString());
                return SearchCategoriesjsonpars(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public BookmarkDTO SearchCategoriesjsonpars(String json) {


        // from is 1 when data parse after login or registration otherwise 0

        BookmarkDTO userDTO = new BookmarkDTO();

        try {
            Object object = new JSONTokener(json).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                userDTO.setLike(jsonObject.getString("likecount"));

                if (jsonObject.has("success")){
                    userDTO.setSuccess(jsonObject.getString("success"));
                }


                if (jsonObject.has("result")){
                    JSONArray jsonSOSArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonSOSArray.length(); i++){
                        JSONObject jsonobject=jsonSOSArray.getJSONObject(i);

                        userDTO.setName(jsonobject.getString("name"));

                        userDTO.setMarketType(jsonobject.getString("market_type"));

                        userDTO.setBookmark(jsonobject.getString("bookmark"));

                        userDTO.setRating(jsonobject.getString("rating"));

                        userDTO.setMobile(jsonobject.getString("phone_no"));

                        userDTO.setDistance(jsonobject.getString("distance"));

                        userDTO.setLocation(jsonobject.getString("location"));

                        userDTO.setImage(jsonobject.getString("image"));
                        userDTO.setId(jsonobject.getString("id"));
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

