package Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sonu saini on 4/12/2016.
 */
public class AppSession {
    private SharedPreferences sharedPref;
    private String SHARED = "com.assan";

    public String getPhoneNo() {
        return sharedPref.getString("phoneNo", "");
    }

    public void setPhoneNo(String phoneNo) {
        setSharedPref("phoneNo", phoneNo);
    }

    private String phoneNo="";

    public String getCompany_id() {
        return sharedPref.getString("company_id", "");
    }

    public void setCompany_id(String company_id) {
        setSharedPref("company_id", company_id);
    }

    private String company_id;

    public String getUserEmail() {
        return sharedPref.getString("userEmail", "");
    }

    public void setUserEmail(String userEmail) {
        setSharedPref("userEmail", userEmail);
    }

    private String UserEmail;
    public AppSession(Context context) {
        try {
            sharedPref = context.getSharedPreferences(SHARED,
                    Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setBaseUrl(String baseUrl) {
        setSharedPref("baseUrl", baseUrl);
    }

    public String getBaseUrl() {
        return sharedPref.getString("baseUrl", null);
    }

    private void setSharedPref(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void setUserAddress(String address) {
        setSharedPref("address", address);
    }

    public String getUserAddress() {
        return sharedPref.getString("address", "");
    }
    public void setBadge(String badge) {
        setSharedPref("badge", badge);
    }
    public String getBadge() {
        return sharedPref.getString("badge", "");
    }

    public String getUserImage() {
        return sharedPref.getString("profile_img", "");
    }
    public void setUserImage(String profile_img) {
        setSharedPref("profile_img", profile_img);
    }


    public void setUserName(String name) {
        setSharedPref("name", name);
    }


    public String getUserNmae() {
        return sharedPref.getString("name", "");
    }

    public void setUserId(String id) {
        setSharedPref("id", id);
    }

    public String getUserId() {
        return sharedPref.getString("id", "");
    }

    public void setUserLocation(String location) {
        setSharedPref("location", location);
    }

    public String getUserLocation() {
        return sharedPref.getString("location", "");
    }
    public void setUserloginStatus(String Status) {
        setSharedPref("status", Status);
    }

    public String getUserloginStatus() {
        return sharedPref.getString("status", "");
    }

    public void clearPrefr(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.clear();
        editor.commit();

    }
}
