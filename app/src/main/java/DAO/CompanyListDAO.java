package DAO;

import android.util.Log;

import com.assan.RegisterUserClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DTO.CompanyListDTO;
import Utils.AppConstants;
import Utils.RequestHandler;

/**
 * Created by DigiT-25 on 30-08-2016.
 */
public class CompanyListDAO extends RegisterUserClass {

    RequestHandler ruc = new RequestHandler();
    String result = "";

    public CompanyListDTO getCompanyList(String userID) {
        try {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("userId", userID);

            result = ruc.sendPostRequest(AppConstants.BASEURL + "getStoreList", data);

            Log.v("lOGINREQUEST eee", result);
            Log.v("Request_Anil ee", data.toString());
            return CompanyListJsonPars(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CompanyListDTO CompanyListJsonPars(String json){

        CompanyListDTO companyListDTO = new CompanyListDTO();
        List<CompanyListDTO> list = new ArrayList<>();

        try{
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("Outlet");

                for (int i = 0; i < jsonArray.length(); i++) {

                    CompanyListDTO companydto = new CompanyListDTO();
                    JSONObject jobj = jsonArray.getJSONObject(i);

                    companydto.setTitle(jobj.getString("title"));
                    companydto.setId(jobj.getString("id"));
                    companydto.setEmail(jobj.getString("name"));
                    companydto.setStatus(jobj.getString("status"));

                    list.add(companydto);
                }
                companyListDTO.setCompList(list);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return companyListDTO;
    }

}
