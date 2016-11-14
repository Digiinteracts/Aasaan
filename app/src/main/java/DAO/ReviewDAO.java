package DAO;

import android.content.Context;
import android.util.Log;

import com.assan.RegisterUserClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DTO.GallaryDTO;
import DTO.OutletDTO;
import Utils.AppConstants;
import Utils.AppSession;

/**
 * Created by Sonu Saini on 5/6/2016.
 */
public class ReviewDAO extends RegisterUserClass {
    RegisterUserClass ruc = new RegisterUserClass();
    Context context;
    AppSession appSession;

    public ReviewDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public OutletDTO getoutletDTO( String outlet_id){

        try{
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("outlet_id",outlet_id);

            String json_response = ruc.sendPostRequest(AppConstants.BASEURL +"reviewList", data);
            Log.v("review_request", data.toString());
            if (json_response != null) {
                Log.v("JIIIITTTUU", "json " +outlet_id+ json_response.toString());
                return Outletjsonpars(json_response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public OutletDTO Outletjsonpars(String json) {
        // from is 1 when data parse after login or registration otherwise 0

        OutletDTO userDTO = new OutletDTO();
        try {
            Object object = new JSONTokener(json).nextValue();

            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                Log.e("Review_result",jsonObject.toString());
                if (jsonObject.has("success")){
                    userDTO.setSuccess(jsonObject.getString("success"));
                }
                if (jsonObject.has("result")){
                    JSONArray result = jsonObject.getJSONArray("result");
                    List<GallaryDTO> dtos = new ArrayList<GallaryDTO>();
                    for (int i =0;i<result.length();i++) {
                        GallaryDTO dtoitem = new GallaryDTO();

                        dtoitem.setImage(result.getJSONObject(i).getString("profile_img"));
                        dtoitem.setName(result.getJSONObject(i).getString("username"));
                        dtoitem.setAddress(result.getJSONObject(i).getString("address"));
                        dtoitem.setRating(result.getJSONObject(i).getString("rating"));
                        dtoitem.setAgo(result.getJSONObject(i).getString("ago"));
                        dtoitem.setReview(result.getJSONObject(i).getString("review"));
                        /*
                        dtoitem.setComment(result.getJSONObject(i).getString("review"));*/


                       /* userDTO.setName(result.getJSONObject(i).getString("review"));
                        userDTO.setReview_imge(result.getJSONObject(i).getString("profile_img"));
                        userDTO.setRating(result.getJSONObject(i).getString("rating"));
                        userDTO.setPhone_no(result.getJSONObject(i).getString("ago"));*/
                        dtos.add(dtoitem);
                    }
                     userDTO.setGallaryDTOs(dtos);


                    /*}
                    JSONObject review = result.getJSONObject("review_details");
                    userDTO.setDistance(result.getString("distance"));
                    userDTO.setId(result.getString("id"));

                    userDTO.setLocation(result.getString("location"));

                    userDTO.setReview_type(review.getString("review"));

                    userDTO.setReview_count(result.getString("reviewcount"));*/


                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userDTO;
    }

    public OutletDTO getWriteReviewDTO(String userID,String outlet_id,String rating,String write_txt){
        try{
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("ref_id",outlet_id);
            data.put("user_id",userID);
            data.put("rating",rating);
            data.put("review",write_txt);

            String json_response = ruc.sendPostRequest(AppConstants.BASEURL +"writeReview", data);
            Log.v("review_request", data.toString());
            if (json_response != null) {
                Log.v("JIIIITTTUU", "json " +outlet_id+ json_response.toString());
                return writejsonpars(json_response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public  OutletDTO writejsonpars(String json){
        OutletDTO userDTO = new OutletDTO();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Log.e("writereview data","d "+jsonObject.getString("msg"));
            //userDTO.setSuccess("status");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userDTO;
    }

}
