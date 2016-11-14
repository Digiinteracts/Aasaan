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

import DTO.stattisticsModel;
import Utils.AppConstants;
import Utils.AppSession;

//import DTO.OutletListModel.OutletListFeaturedModel;


/**
 * Created by Sonu Saini on 5/6/2016.
 */
public class StatiscticsDAO extends RegisterUserClass {
    RegisterUserClass ruc = new RegisterUserClass();
    RegisterUserClass ruc2 = new RegisterUserClass();
    Context context;
    AppSession appSession;

    public StatiscticsDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public stattisticsModel getstatiscticsDTO(String user_id ){

        try{
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("user_id",user_id);

            String json_response = ruc2.sendPostRequest(AppConstants.BASEURL +AppConstants.STATUSRATING , data);

            if (json_response != null) {
                Log.v("STATUSRATING", "json" + json_response.toString());
                return statisctics(json_response);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public stattisticsModel statisctics(String json) {


        stattisticsModel userDTO = new stattisticsModel();
        try {
            Object object = new JSONTokener(json).nextValue();


            if (object instanceof JSONObject) {

                JSONObject jsonObject = new JSONObject(json);
                List<stattisticsModel> dtos = new ArrayList<stattisticsModel>();
                Log.e("stattistics_response", jsonObject.toString());
                if (jsonObject.has("status")) {
                    userDTO.setStatus(jsonObject.getString("status"));
                }

                if (jsonObject.has("results")) {
                    JSONArray results = jsonObject.getJSONArray("results");

                    Log.e("jsonArray","Result"+results.toString());
                    if (results.length() >0) {
                        for (int i = 0; i <results.length();i++)
                        {
                            stattisticsModel dto = new stattisticsModel();
                            dto.setActivityname(results.getJSONObject(i).getString("name"));
                            dto.setPoints(results.getJSONObject(i).getString("point"));
                            dto.setCount(results.getJSONObject(i).getString("count"));
                            dto.setTotal(results.getJSONObject(i).getString("total"));
                            dtos.add(dto);

                        }
                       userDTO.setAdvertiseDTOs(dtos);

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
