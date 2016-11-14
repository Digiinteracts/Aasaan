package adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.assan.R;

/**
 * Created by Sonu Saini on 4/20/2016.
 */
public class imageadapter extends BaseAdapter
{
    private Context mContext;



    public imageadapter(Context context)
    {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(130, 130));

        i.setScaleType(ImageView.ScaleType.FIT_XY);




        return i;
    }

    public Integer[] mImageIds = {
           /* R.drawable.woman_1,
            R.drawable.woman_2,
            R.drawable.woman_3,
            R.drawable.woman_4,
            R.drawable.woman_5,
            R.drawable.woman_6,
            R.drawable.woman_7,
            R.drawable.woman_9,
            R.drawable.woman_10,
            R.drawable.woman_1,
            R.drawable.woman_2,
            R.drawable.woman_3,
            R.drawable.woman_4,
            R.drawable.woman_5,
            R.drawable.woman_6,
            R.drawable.woman_7,
            R.drawable.woman_9,
            R.drawable.woman_10,
            R.drawable.woman_8*/
    };

}