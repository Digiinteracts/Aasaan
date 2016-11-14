package DAO;

import android.content.Context;
import android.util.Log;

import com.assan.RegisterUserClass;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import DTO.RegistrationDTO;
import Utils.AppConstants;
import Utils.AppSession;

/**
 * Created by Sonu Saini on 4/21/2016.
 */


public class RegistrationDAO extends RegisterUserClass {
    RegisterUserClass ruc = new RegisterUserClass();
    String serviceUrl = "";
    Context context;
    AppSession appSession;

    public RegistrationDAO(Context context) {
        this.context = context;
        appSession = new AppSession(context);
    }

    public RegistrationDTO registration(String name, String email, String password, String deviceType, String deviceId, String appVersion, String FbId,String profileimg){
        serviceUrl = AppConstants.REGISTER_URL;
        try{
            HashMap<String, String> data = new HashMap<String, String>();

            data.put("name", name);
            data.put("email",email);
            data.put("password",password);
            data.put("deviceType", deviceType);
            data.put("deviceId", deviceId);
            data.put("appVersion", appVersion);
            data.put("FbId", FbId);
            data.put("profileImage", profileimg);
            String json = ruc.sendPostRequest(AppConstants.REGISTER_URL, data);
            Log.v("Register", json);
            if (json != null) {
                Log.v("", "json " + json.toString());
                return registrationjsonpars(json);
            }
        }catch (Exception e){
            e.printStackTrace();
    }
        return null;
    }
    public RegistrationDTO registrationjsonpars(String json) {


            // from is 1 when data parse after login or registration otherwise 0

        RegistrationDTO userDTO = new RegistrationDTO();
        try {
            Object object = new JSONTokener(json).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("status")){
                    userDTO.setStatus(jsonObject.getString("status"));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userDTO;
    }
}
