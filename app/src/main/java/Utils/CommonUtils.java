package Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;




/**
 * Created by Sonu Saini on 4/14/2016.
 */




public class CommonUtils {
    static AlertDialog dialog;

    public static void showAlertDialog(Context mContext, String title, String message) {

        if (mContext == null) return;
        if (dialog != null && dialog.isShowing()) return;

        dialog = new AlertDialog.Builder(mContext).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showToast(Context mContext, String message) {
        Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void hideSoftKeyboard(Activity activity, View view)

    {
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }


    public static void ShowSoftKeyBoard(Activity activity, View view){
        InputMethodManager inputMethodManager=(InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }


    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public static String convertToPostedTime(long time) {
        Log.d("TAG", "time is " + time);
        if (time == 0) {
            return "";
        }
        long currentTimeMillis = System.currentTimeMillis();
        long postMills = time * 1000;
        postMills = currentTimeMillis - postMills;
        long remHour = postMills;
        remHour = remHour / 1000;
        long sec = remHour % 60;
        remHour /= 60;
        long minutes = remHour % 60;
        remHour /= 60;
        long hour = remHour % 24;
        remHour /= 24;
        long days = remHour;

        Log.d("TAG", "remaining days:  " + days);
        remHour/=30;
        long months = remHour;
        remHour/=12;
        long years = remHour;

        if(years>0){
            String content = years > 1 ? " years ago" : " year ago";
            return years + content;
        }

        if(months>0){
            String content = months > 1 ? " months ago" : " month ago";
            return months + content;
        }

        if (days > 0) {
            String content = days > 1 ? " days ago" : " day ago";
            return days + content;
        }
        else if (hour > 0) {
            String content = hour > 1 ? " hours ago" : " hour ago";
            return hour + content;
        } else if (minutes > 0) {
            String content = minutes > 1 ? " minutes ago" : " minute ago";
            return minutes + content;
        } else {
            String content = sec > 1 ? " seconds ago" : " second ago";
            return sec + content;
        }

    }


//    public boolean checkPlayServices() {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Log.d(TAG, "This device is not supported - Google Play Services.");
//
//            }
//            return false;
//        }
//        return true;
//    }


}