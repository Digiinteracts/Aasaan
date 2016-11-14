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
public class OutletListDAO extends RegisterUserClass {
    RegisterUserClass ruc = new RegisterUserClass();
    Context context;
    AppSession appSession;

    public OutletListDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public OutletListModel getoutletDTO(String user_id, String cat_id, String sub_cat_id,String location, String page ,String keyword,String cat_name,String latitudue,String longitude,String filter){

        try{
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("location", location);
            data.put("user_id",user_id);
            data.put("cat_id",cat_id);
            data.put("sub_cat_id",sub_cat_id );
            data.put("page", page);
            data.put("filter", filter);
            data.put("keyword", keyword);
            data.put("cat_name", cat_name);
            data.put("user_lat", latitudue);
            data.put("user_long", longitude);
            String json_response = ruc.sendPostRequest(AppConstants.BASEURL +"search", data);
            Log.e("longlat", "outlet " + data);
            Log.v("dataaaaa", "data" + data);
            if (json_response != null) {
                Log.v("jjj ", "json" + json_response);
                Log.e("Outlet_List_response","jjj"+json_response);
                return Outletjsonpars(json_response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public OutletListModel Outletjsonpars(String json) {
        // from is 1 when data parse after login or registration otherwise 0

        OutletListModel userDTO = new OutletListModel();
             try {
            Object object = new JSONTokener(json).nextValue();

            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                Log.e("Outlet_List_response",jsonObject.toString());
                // initialisation of Models


                List<OutletListModel> dtos = new ArrayList<OutletListModel>();
                List<OutletListModel> imgheader = new ArrayList<OutletListModel>();
//                List<OutletListFeaturedModel> dtos2 = new ArrayList<OutletListModel.OutletListFeaturedModel>();
//                OutletListModel userDTO = new OutletListModel();

                if (jsonObject.has("success")){
                    userDTO.setSuccess(jsonObject.getString("success"));

                    Log.e("Success:jsonanil",userDTO.toString());
                }

                if (jsonObject.has("result")){

                    JSONArray result = jsonObject.getJSONArray("result");
                dtos.clear();

                   // Log.e("Result:jsonanil",result.toString());
                for (int i=0; i<result.length();i++){
                  //  System.out.println("name" + result.getJSONObject(i).getString("name"));
                   Log.e("name" + result.getJSONObject(i).getString("name"),result.toString());
                    OutletListModel dto = new OutletListModel();

                    dto.setOutlet_id(result.getJSONObject(i).getString("id"));
                    dto.setOutlet_name(result.getJSONObject(i).getString("name"));
                    dto.setOutlet_location(result.getJSONObject(i).getString("location"));
                    dto.setOutlet_rating(result.getJSONObject(i).getString("rating"));
                    dto.setOutlet_type(result.getJSONObject(i).getString("market_type"));

                    if (result.getJSONObject(i).getString("image").equals(""))
                        dto.setImage("");
                    else
                        dto.setImage(result.getJSONObject(i).getString("image"));

                    dto.setPhone_no(result.getJSONObject(i).getString("phone_no"));
                    dto.setOutlet_time(result.getJSONObject(i).getString("distance") + "km");

                    dto.setOutlet_lat(result.getJSONObject(i).getString("lat"));
                    dto.setOutlet_long(result.getJSONObject(i).getString("long"));

                    dtos.add(dto);
                   // Log.e("dtos:after",dtos.toString());
                }
                   /* userDTO.setAdvertiseDTOs(dtos);*/
            }

                if(jsonObject.has("featured"))

                {
                    JSONArray feature=jsonObject.getJSONArray("featured");
                    for (int i=0; i<feature.length();i++) {
                        JSONObject object1 = feature.getJSONObject(i);

                        OutletListModel dto3 = new OutletListModel();
                        dto3.setSponsered_name(feature.getJSONObject(i).getString("name"));
                        dto3.setSponsered_image(feature.getJSONObject(i).getString("outlet_featured"));
                        dto3.setSponsered_id(feature.getJSONObject(i).getString("id"));

                        Log.e("teesstt","fffname "+object1.getString("name")+"  i "+feature.length()+"id "+object1.getString("id"));
                        imgheader.add(dto3);
                    }
                    userDTO.setheaderImg(imgheader);
                    userDTO.setAdvertiseDTOs(dtos);

                    Log.e("teesstt","fffnamed "+userDTO.getheaderImg().size());
                  /*  userDTOFeature.setAdvertiseDTOs2(dtos2);*/
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userDTO;
    }
}
