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

import DTO.GallaryDTO;
import DTO.OutletDTO;
import Utils.AppConstants;
import Utils.AppSession;
import Utils.Constant;

/**
 * Created by Sonu Saini on 5/6/2016.
 */
public class OutletDAO extends RegisterUserClass {
    RegisterUserClass ruc = new RegisterUserClass();
    Context context;
    AppSession appSession;
    int SearchCatgeriadapter_flag =0;

    public OutletDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public OutletDTO getoutletDTO(String location, String user_id, String outlet_id,int SearchCatgeriadapter_flag){

        this.SearchCatgeriadapter_flag = SearchCatgeriadapter_flag;
        try{
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("location", location);
            data.put("user_id",user_id);
            data.put("outlet_id",outlet_id);
            data.put("user_lat", Constant.LATITUDE);
            data.put("user_long",Constant.LONGITUDE);

            String json_response = ruc.sendPostRequest(AppConstants.BASEURL +"outletdetail", data);
            Log.v("outlet_request", data.toString());
            if (json_response != null) {
                Log.v("jjjj", "json " +user_id+ json_response.toString());
                return Outletjsonpars(json_response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public OutletDTO Outletjsonpars(String json) {
        // from is 1 when data parse after login or registration otherwise 0
        String fridayBrkTime = "Fri";
        OutletDTO userDTO = new OutletDTO();
        try {
            Object object = new JSONTokener(json).nextValue();

            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                Log.e("Outlet_details_result",jsonObject.toString());
                if (jsonObject.has("success")){
                    userDTO.setSuccess(jsonObject.getString("success"));
                }
                if (jsonObject.has("result")){

                    if (SearchCatgeriadapter_flag == 10){
                        //if (SearchCatgeriadapter_flag == 12){
                        Log.e("rrrrrreview","Review");

                        JSONObject result = jsonObject.getJSONObject("result");
                        Log.e("ttttooo"," "+SearchCatgeriadapter_flag);

                        //  JSONArray review = result.getJSONArray("review_details");
                        JSONArray timings = result.getJSONArray("timings");
                        JSONArray todaytimearray = result.getJSONArray("Today_timings");
                        userDTO.setDistance(result.getString("distance"));

                        userDTO.setId(result.getString("id"));

                        if (!result.getString("banner").trim().equals("") || !result.getString("banner").equals(null))
                            userDTO.setBackground_imge(result.getString("banner"));
                        else
                            Log.e("banner ", "b ");


                        Log.e("banner ", "b2 ");
                        userDTO.setName(result.getString("name"));
                        userDTO.setMarket_type(result.getString("market_type"));
                        userDTO.setBookmark(result.getString("bookmark"));
                        userDTO.setPhone_no(result.getString("phone_no"));
                        userDTO.setLocation(result.getString("location"));

                        userDTO.setFull_location(result.getString("full_location"));
                        userDTO.setLandmark(result.getString("landmark"));
                      //  userDTO.setBanner(result.getString("banner"));


                        userDTO.setRating_outlate(result.getString("rating"));
                        // userDTO.setRating(review.getString("rating"));
                        userDTO.setLike_count(result.getString("likecount"));

                        userDTO.setLike(result.getString("like"));

                        userDTO.setReview_count(result.getString("reviewcount"));

                        userDTO.setHome_delivery(result.getString("delivery"));

                        userDTO.setCredit_card(result.getString("credit_card"));

                        userDTO.setCurrency_accepted(result.getString("currency_accepted"));

                        userDTO.setOutlet_lat(result.getString("lat"));

                        userDTO.setOutlet_long(result.getString("long"));

                        List<OutletDTO> outletDTOList = new ArrayList<>();
                        for (int i = 0; i < timings.length(); i++) {
                            JSONObject timeobj = timings.getJSONObject(i);
                            OutletDTO userDTO1 = new OutletDTO();
                            userDTO1.setOpenTime(timeobj.getString("open_time"));
                            userDTO1.setCloseTime(timeobj.getString("close_time"));
                            userDTO1.setName(timeobj.getString("name"));

                            if (timeobj.getString("name").equals("Fri")) {
                                if (timeobj.getString("open_time").equals("close")) {
                                    userDTO1.setFridayBrkTime("close");

                                } else {
                                    userDTO1.setFridayBrkTime(timeobj.getString("fridayBrkTime"));
                                }

                            } else {
                                userDTO1.setBrkTime(timeobj.getString("BrkTime"));
                            }

                            outletDTOList.add(userDTO1);
                            Log.e("OOOPP", " " + userDTO1.getCloseTime() + "cc " + userDTO1.getOpenTime());
                        }
                        Log.e("ttttooo33", " ");
                        userDTO.setGetOutletInfo(outletDTOList);

                        for (int i = 0; i < todaytimearray.length(); i++) {
                            JSONObject todaytimeobj = todaytimearray.getJSONObject(i);
                            userDTO.settoday_Opentime(todaytimeobj.getString("open_time"));
                            userDTO.setToday_Closetime(todaytimeobj.getString("close_time"));
                            userDTO.setToday_day(todaytimeobj.getString("name"));

                            if (todaytimeobj.getString("open_time").equals("close") || todaytimeobj.getString("close_time").equals("close") ) {

                            }
                            else {
                                userDTO.setToday_brktime(todaytimeobj.getString("BrkTime"));
                            }

                        }
                        JSONArray gallary = result.getJSONArray("photos");
                        List<GallaryDTO> dtos = new ArrayList<GallaryDTO>();
                        for (int i = 0; i < gallary.length(); i++) {
                            GallaryDTO dtoitem = new GallaryDTO();
                            dtoitem.setImage(gallary.getString(i));
                            dtos.add(dtoitem);
                        }

                        userDTO.setGallaryDTOs(dtos);

                        //review---------------------
                        JSONObject review = result.getJSONObject("review_details");
                        userDTO.setReview_details(review.toString());
                        if (!String.valueOf(review.toString()).equals("null")) {
                            Log.e("rrrrrreview IF", "Review" + review.getString("username"));

                            userDTO.setReview_name(review.getString("username"));
                            userDTO.setReview_ago(review.getString("ago"));
                            //userDTO.setReview_adress(review.getString("address"));
                            userDTO.setReview_imge(review.getString("profile_img"));
                            //userDTO.setReview_time(review.getString("ago"));
                            userDTO.setReview_rating(review.getString("rating"));
                            userDTO.setReview_type(review.getString("review"));
                        }

                        userDTO.setGallaryDTOs(dtos);
                    }

                    else {
                        JSONArray timings = null,todaytimearray = null;

                        JSONObject result = jsonObject.getJSONObject("result");
                        Log.e("ttttooo", " " + SearchCatgeriadapter_flag);
                       //

                        //  JSONArray review = result.getJSONArray("review_details");

                        if (jsonObject.has("timings"))
                           if (!String.valueOf(result.getJSONArray("timings")).equals("") || !String.valueOf(result.getJSONArray("timings")).equals(null))
                            timings = result.getJSONArray("timings");

                        if (jsonObject.has("Today_timings"))
                            if (!String.valueOf(result.getJSONArray("Today_timings")).equals("") || !String.valueOf(result.getJSONArray("Today_timings")).equals(null))
                            todaytimearray = result.getJSONArray("Today_timings");

                        userDTO.setDistance(result.getString("distance"));

                        userDTO.setId(result.getString("id"));
                        Log.e("ttttooo ", "b1 ");
                        if (!result.getString("banner").trim().equals("") || !result.getString("banner").equals(null))
                        userDTO.setBackground_imge(result.getString("banner"));
                        else
                            Log.e("ttttooo ", "b ");


                        Log.e("ttttooo ", "b2 ");

                        userDTO.setName(result.getString("name"));

                        userDTO.setMarket_type(result.getString("market_type"));

                        userDTO.setBookmark(result.getString("bookmark"));
                        userDTO.setPhone_no(result.getString("phone_no"));

                        userDTO.setLocation(result.getString("location"));

                        userDTO.setFull_location(result.getString("full_location"));
                        userDTO.setLandmark(result.getString("landmark"));
                       // userDTO.setBanner(result.getString("banner"));

                        userDTO.setRating_outlate(result.getString("rating"));
                        // userDTO.setRating(review.getString("rating"));
                        userDTO.setLike_count(result.getString("likecount"));

                        userDTO.setLike(result.getString("like"));

                        userDTO.setReview_count(result.getString("reviewcount"));

                        userDTO.setHome_delivery(result.getString("delivery"));

                        userDTO.setCredit_card(result.getString("credit_card"));

                        userDTO.setCurrency_accepted(result.getString("currency_accepted"));
                        userDTO.setOutlet_lat(result.getString("lat"));

                        userDTO.setOutlet_long(result.getString("long"));

                        Log.e("ttttooo33", "timings ");


                        if (jsonObject.has("timings"))
                        if (!String.valueOf(timings).equals("") || !String.valueOf(timings).equals(null)) {

                            List<OutletDTO> outletDTOList = new ArrayList<>();
                            for (int i = 0; i < timings.length(); i++) {
                                JSONObject timeobj = timings.getJSONObject(i);
                                OutletDTO userDTO1 = new OutletDTO();
                                userDTO1.setOpenTime(timeobj.getString("open_time"));
                                userDTO1.setCloseTime(timeobj.getString("close_time"));
                                userDTO1.setName(timeobj.getString("name"));

                                if (timeobj.getString("name").equals("Fri")) {
                                    if (timeobj.getString("open_time").equals("close")) {
                                        userDTO1.setFridayBrkTime("close");

                                    } else {
                                        userDTO1.setFridayBrkTime(timeobj.getString("fridayBrkTime"));
                                    }

                                } else if (timeobj.getString("name").equals("all")) {

                                } else {
                                    userDTO1.setBrkTime(timeobj.getString("BrkTime"));
                                }

                                outletDTOList.add(userDTO1);
                                Log.e("OOOPP", " " + userDTO1.getCloseTime() + "cc " + userDTO1.getOpenTime());
                            }
                            Log.e("ttttooo33", "timings2 ");
                            userDTO.setGetOutletInfo(outletDTOList);
                        }

                        if (jsonObject.has("Today_timings"))
                            if (!String.valueOf(todaytimearray).equals("") ||String.valueOf(todaytimearray).equals(null))
                        for (int i = 0; i < todaytimearray.length(); i++) {
                            Log.e("ttttooo33", "1 ");
                            JSONObject todaytimeobj = todaytimearray.getJSONObject(i);
                            userDTO.settoday_Opentime(todaytimeobj.getString("open_time"));
                            userDTO.setToday_Closetime(todaytimeobj.getString("close_time"));

                            userDTO.setToday_day(todaytimeobj.getString("name"));

                            if (todaytimeobj.getString("open_time").equals("close") || todaytimeobj.getString("close_time").equals("close") ) {
                            }
                            else if (todaytimeobj.getString("open_time").equals("24x7") || todaytimeobj.getString("close_time").equals("24x7")){

                            }
                            else {
                                userDTO.setToday_brktime(todaytimeobj.getString("BrkTime"));

                            }
                        }
                       // userDTO.setGallaryDTOs(dtos);
                        Log.e("ttttooo33", "10 ");
                        if (!result.getJSONArray("photos").equals("") || !result.getJSONArray("photos").equals(null)) {
                            JSONArray gallary = result.getJSONArray("photos");
                            List<GallaryDTO> dtos = new ArrayList<GallaryDTO>();
                            for (int i = 0; i < gallary.length(); i++) {
                                GallaryDTO dtoitem = new GallaryDTO();
                                dtoitem.setImage(gallary.getString(i));
                                dtos.add(dtoitem);
                            }
                            Log.e("ttttooo33", "100 ");
                            userDTO.setGallaryDTOs(dtos);
                        }
                        JSONObject review = result.getJSONObject("review_details");

                        userDTO.setReview_details(review.toString());
                        if (!String.valueOf(review.toString()).equals("null")) {
                            Log.e("rrrrrreview ELS2", "Review" + review.getString("username"));

                            userDTO.setReview_name(review.getString("username"));
                            userDTO.setReview_ago(review.getString("ago"));
                            //userDTO.setReview_adress(review.getString("address"));
                            userDTO.setReview_imge(review.getString("profile_img"));
                            //userDTO.setReview_time(review.getString("ago"));
                            userDTO.setReview_rating(review.getString("rating"));
                            userDTO.setReview_type(review.getString("review"));
                        }


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
