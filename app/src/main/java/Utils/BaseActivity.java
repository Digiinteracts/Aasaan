package Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.LayoutParams;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assan.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * All activities extends this class for extend it's common methods.
 */
public class BaseActivity extends FragmentActivity implements BaseInterface, OnClickListener {
    public static String[] defaultLocation = null;
    /* for Twitter login */
    private final Context context = this;
    AppSession appSession = null;

    public static String registrationId = "";
    public static File outputfile;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);

        appSession = new AppSession(context);
        try {
            if (isNetworkAvailable()) {
                if (appSession.getBaseUrl() == null
                        || appSession.getBaseUrl().equals(""))
                    new TaskForBaseURL(getBaseContext()).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /* method for checking valid email-id */
    public final static boolean checkEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    /* method for displaying simple message */
    public void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


    }

    public void toastWIPMessage(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.wip),
                Toast.LENGTH_SHORT).show();

    }

    /* method for displaying server error message */
    public void toastServerMessage(Context context) {
        Toast.makeText(context,
                context.getResources().getString(R.string.server_error),
                Toast.LENGTH_SHORT).show();

    }

    public void toastNetworkMessage(Context context) {

        Toast.makeText(context,
                context.getResources().getString(R.string.network_error),
                Toast.LENGTH_SHORT).show();

    }

    /* method for checking Network availability */
    public boolean isNetworkAvailable() {

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error r) {
            r.printStackTrace();
        }
        return false;
    }


    /* method for getting Device Id or IMEI number */
    public static String getDeviceId(Context context) {
        try {
            String deviceid = "";
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceid = telephonyManager.getDeviceId();
            return deviceid;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } catch (Error e1) {
            e1.printStackTrace();
            return null;
        }
    }

    /* method for getting app version */
    public String getAppVersion() {
        try {
            String appversion = "";
            PackageInfo pInfo = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            appversion = pInfo.versionName;
            return appversion;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } catch (Error e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static void closeKeyboard(final Context context, final View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /* method for mobile number validation */
    public final static Pattern MOBILE_NUMBER_PATTERN = Pattern
            .compile("^[0-9]{8,15}$");

    public static boolean checkMobile(String mobile) {
        try {
            mobile = mobile.replaceAll("[^0-9]", "");
            if (MOBILE_NUMBER_PATTERN.matcher(mobile).matches())
                return true;
            else
                return false;
        } catch (Exception exception) {
            return false;
        }
    }

    /* method for username validation */
    private final static Pattern NAME_PATTERN = Pattern
            .compile("[a-zA-Z0-9_.]+");

    public boolean checkName(String name) {
        try {
            System.out.println("chek name  :  "
                    + NAME_PATTERN.matcher(name).matches());
            return NAME_PATTERN.matcher(name).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }


    public static boolean isAndroidEmulator() {
        @SuppressWarnings("unused")
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        boolean isEmulator = false;
        if (product != null) {
            isEmulator = product.equals("sdk") || product.contains("_sdk")
                    || product.contains("sdk_");
        }

        return isEmulator;
    }


    public static String getFilePath(Bitmap bitmap, Context context) {
        File cacheDir;
        File file;

        try {
            if (android.os.Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED))
                cacheDir = new File(
                        android.os.Environment.getExternalStorageDirectory(),
                        "SMEX/Media/Images");
            else
                cacheDir = context.getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();

            String FILE_NAME = "lb_" + new Date().getTime() + ".jpg";
            file = new File(cacheDir, FILE_NAME);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo;

            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();

            return file.getAbsolutePath();
        } catch (Exception e1) {
            e1.printStackTrace();

        } catch (Error e1) {
            e1.printStackTrace();

        }

        return "";
    }

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);

    }

    public static String getReceiveFileDirectory(Context context) {
        File cacheDir;
        try {
            if (android.os.Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED))
                cacheDir = new File(
                        android.os.Environment.getExternalStorageDirectory(),
                        "SMEX/Media/Images");
            else
                cacheDir = context.getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            return cacheDir.getAbsolutePath();
        } catch (Exception e1) {
            e1.printStackTrace();
            Log.e("Teg", "Exception FileCache(Context context)");

        }
        return null;
    }

    public String getFilePath() {
        File file;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            file = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "SMEX/Media/Images");
        else
            file = context.getCacheDir();
        if (!file.exists())
            file.mkdirs();

        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }


    public void dialogOK(final Context context, String tv_message, final boolean isfinish) {
        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        if (isfinish) {
            dialog.setCancelable(true);
        }
        dialog.setCanceledOnTouchOutside(false);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        dialog.setContentView(R.layout.dialog_box_ok);
        // dialog.setTitle(null);
        // dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.and);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        // title.setTitleColor(Color.CYAN);
        // / title.setBackgroundDrawableResource(Color.TRANSPARENT);
        window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
        // ViewGroup.LayoutParams.FILL_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView tvMessage = (TextView) window.findViewById(R.id.tv_message);
        tvMessage.setText(tv_message);
        Button btn_ok = (Button) window.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                if (isfinish) {
                    ((Activity) context).finish();
                }
            }

        });

        dialog.show();

    }

    public void Back(View view) {
        finish();
    }

}
