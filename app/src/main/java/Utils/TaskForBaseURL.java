package Utils;

import android.content.Context;
import android.os.AsyncTask;


/**
 * @author Pintu Kumar Patil 9977368049
 * @author 13-May-2015
 */
public class TaskForBaseURL extends AsyncTask<Void, Void, Void> {
    String TAG = getClass().getSimpleName();
    Context context;

    public TaskForBaseURL(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
           // new UrlDAO(context).getUrls();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
