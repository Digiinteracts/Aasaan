package Utils;

/**
 * Created by Sonu Saini on 4/14/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import DTO.RecentOutlet;
import DTO.Search;

public class Prefrence {

    public static final String PREF_NAME = "pogmas_pref";

    public static final String USER_NAME = "USER_NAME";
    public static final String USER_KEY = "USER_KEY";

    public static final String USER_IMG_NAME = "USER_IMG_NAME";
    public static final String USER_IMG_KEY = "USER_IMG_KEY";

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";
    public static final String PREFS_NAME2 = "PRODUCT_APP2";
    public static final String RECENT_SEARCH_KEY = "Recent_Search";

    public static final String RLIST_FLAG = "RLIST_FLAG";
    public static final String RLIST_KEY = "RLIST_KEY";

    public static final String USER_TYPE = "USER_TYPE";
    public static final String USERTYPE_KEY = "USERTYPE_KEY";


    public static enum PREF_DATA_TYPE{
        BOOLEAN, STRING, INT, FLOAT, LONG,STRING_SET
    };

    public static SharedPreferences getSharedPref(Context mContext) {
        return mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setPrefValue(Context mContext, String key, Object value, PREF_DATA_TYPE type){
        Editor edit = getSharedPref(mContext).edit();
        switch(type){
            case BOOLEAN:
                edit.putBoolean(key, (Boolean)value);
                break;
            case STRING:
                edit.putString(key, (String)value);
                break;
            case INT:
                edit.putInt(key, (Integer)value);
                break;
            case FLOAT:
                edit.putFloat(key, (Float)value);
                break;
            case LONG:
                edit.putLong(key, (Long)value);
                break;
            case STRING_SET:
                edit.putStringSet(key, (Set<String>) value);
                break;
            default:
                break;
        }
        edit.commit();
    }

    public static Object getPrefValue(Context mContext, String key, PREF_DATA_TYPE type){
        Object resultant = null;
        SharedPreferences pref = getSharedPref(mContext);
        switch(type){
            case BOOLEAN:
                resultant = pref.getBoolean(key, false);
                break;
            case STRING:
                resultant = pref.getString(key, "");
                break;
            case INT:
                resultant = pref.getInt(key, 0);
                break;
            case FLOAT:
                resultant = pref.getFloat(key, 0.0f);
                break;
            case LONG:
                resultant = pref.getLong(key, 0L);
                break;
            case STRING_SET:
                resultant = pref.getStringSet(key,null);
                break;
            default:
                break;
        }
        return resultant;
    }
    public static void removeAllPrefValue(Context mContext){
        Editor edit = getSharedPref(mContext).edit();
        edit.clear();
        edit.commit();
    }


    public void saveUserId(Context context, String text) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(USER_KEY, text); //3

        editor.commit(); //4
    }

    public String getUserID(Context context) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
        text = settings.getString(USER_KEY, "null");
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(USER_KEY);
        editor.commit();
    }

    //userimage------------------

    public void saveUserImg(Context context, String text) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_IMG_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(USER_IMG_KEY, text); //3

        editor.commit(); //4
    }

    public String getUserImg(Context context) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_IMG_NAME, Context.MODE_PRIVATE);
        text = settings.getString(USER_IMG_KEY, "null");
        return text;
    }

    public void clearImgprefrence(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_IMG_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeImgprefrence(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(USER_IMG_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(USER_IMG_KEY);
        editor.commit();
    }

    //recent list data-----------------------------------------------------------------


    public void saveRecentData(Context context, List<RecentOutlet> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addRecentData(Context context, RecentOutlet product) {
        List<RecentOutlet> favorites = getRecentData(context);
        if (favorites == null)
            favorites = new ArrayList<RecentOutlet>();
            favorites.add(product);
        saveRecentData(context, favorites);
    }

    public ArrayList<RecentOutlet> getRecentData(Context context) {
        SharedPreferences settings;
        List<RecentOutlet> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            RecentOutlet[] favoriteItems = gson.fromJson(jsonFavorites,
                    RecentOutlet[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<RecentOutlet>(favorites);
        } else
            return null;

        return (ArrayList<RecentOutlet>) favorites;
    }
//----------------------------------- SEARCH--------------------------------
    // Save Search
    public void saveRecentSearch(Context context,ArrayList<String> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME2,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(RECENT_SEARCH_KEY, jsonFavorites);

        editor.commit();
    }
    // add Search
    public void addRecentSearch(Context context, String product) {
        ArrayList<String> favorites = getRecentSearch(context);
        if (favorites == null)
            favorites = new ArrayList<String>();
        favorites.add(product);
        saveRecentSearch(context, favorites);
    }
    // get Search
    public ArrayList<String> getRecentSearch(Context context) {
        SharedPreferences settings;
        ArrayList<String> favorites = new ArrayList<>();

        settings = context.getSharedPreferences(PREFS_NAME2,
                Context.MODE_PRIVATE);

        if (settings.contains(RECENT_SEARCH_KEY)) {
            String jsonFavorites = settings.getString(RECENT_SEARCH_KEY, null);
            favorites.add(jsonFavorites);
            favorites = new ArrayList<String>(favorites);
        } else
            return null;

        return (ArrayList<String>) favorites;
    }
    public void removeFavorite(Context context, RecentOutlet product) {
        ArrayList<RecentOutlet> favorites = getRecentData(context);
        if (favorites != null) {
            favorites.remove(product);
            saveRecentData(context, favorites);
        }
    }
 // check recent list data----------
    public void saveRlist(Context context, String text) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(RLIST_FLAG, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(RLIST_KEY, text); //3

        editor.commit(); //4
    }

    public String getRlist(Context context) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(RLIST_FLAG, Context.MODE_PRIVATE);
        text = settings.getString(RLIST_KEY, "null");
        return text;
    }

    public void clearRlist(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(RLIST_FLAG, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeRlist(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(RLIST_FLAG, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(RLIST_KEY);
        editor.commit();
    }

    //user type============================================


    public void saveUserType(Context context, String text) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_TYPE, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(USERTYPE_KEY, text); //3

        editor.commit(); //4
    }

    public String getUserType(Context context) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_TYPE, Context.MODE_PRIVATE);
        text = settings.getString(USERTYPE_KEY, "null");
        return text;
    }

    public void clearUserType(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_TYPE, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValueUserType(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(USER_TYPE, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(USERTYPE_KEY);
        editor.commit();
    }
}
