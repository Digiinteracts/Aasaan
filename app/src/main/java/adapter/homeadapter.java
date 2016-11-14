package adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.Home;
import com.assan.MaintenanceActivity;
import com.assan.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ImageLoding.Loader;
import Utils.homeutil;

/**
 * Created by Sonu Saini on 4/28/2016.
 */

public class homeadapter extends BaseAdapter {
   Loader loader;

    ProgressDialog pDialog;
    Bitmap bitmap;
    Context mContext;
    String latitude,longitude;
    LayoutInflater inflater;
    private List<homeutil> menulistlist = null;
    private ArrayList<homeutil> arraylist;
    Viewholder holder;
    public homeadapter(Context context, List<homeutil> menulistlist,String latitude,String longitude) {

        mContext = context;
        this.menulistlist = menulistlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<homeutil>();
        loader = new Loader(mContext);
        this.arraylist.addAll(menulistlist);
        this.latitude = latitude;
        this.longitude = longitude;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return menulistlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return menulistlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) {
            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.homeadapter, null);
            holder.subimage = (ImageView) convertView.findViewById(R.id.sub_cat_imageView);
            holder.text= (TextView)convertView.findViewById(R.id.text);
            holder.txt_sub_cat_name= (TextView)convertView.findViewById(R.id.text_image_name);
            convertView.setTag(holder);
        } else {

            holder = (Viewholder) convertView.getTag();
        }

        holder.text.setTypeface(setfont("OpenSans-Bold.ttf"));
        holder.txt_sub_cat_name.setTypeface(setfont("OpenSans-Bold.ttf"));

        holder.txt_sub_cat_name.setText(menulistlist.get(position).getSubName().toUpperCase());
      //  new LoadImage().execute(menulistlist.get(position)
             //   .getSubImgaepath1());
        loader.displayImage(menulistlist.get(position).getSubImgaepath1(), holder.subimage);

       // holder.subimage.setImageBitmap(getBitmapFromURL(menulistlist.get(position).getSubImgaepath1()));
        Log.e("imag url",menulistlist.get(position).getSubImgaepath1());


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Home.CATNAME=menulistlist.get(position).getSubName();
                Home.CATID= menulistlist.get(position).getSubCatId();

                Intent inti = new Intent(mContext, MaintenanceActivity.class);
                inti.putExtra("id", menulistlist.get(position).getSubCatId());
                inti.putExtra("name", menulistlist.get(position).getSubName());
                inti.putExtra("latitude",latitude);
                inti.putExtra("longitude",longitude);
                inti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(inti);

            }
        });

        return convertView;
    }

    class Viewholder {
        TextView txt_sub_cat_name;
        TextView text;
        ImageView subimage;
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(mContext.getAssets(),"font/"+font);
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
