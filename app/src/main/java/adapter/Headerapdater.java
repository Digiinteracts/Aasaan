package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.Home;
import com.assan.OutlateListingPage;
import com.assan.R;

import java.util.ArrayList;
import java.util.List;

import Utils.Constant;
import Utils.HorizontalUtil;


/**
 * Created by Sonu Saini  on 4/29/2016.
 */

public class Headerapdater extends BaseAdapter {
    Utils.ImageLoader imageLoader;

    ProgressDialog pDialog;
    Bitmap bitmap;
    Context mContext;
String catid;
    LayoutInflater inflater;

    private List<HorizontalUtil> menulistlist = null;

    private ArrayList<HorizontalUtil> arraylist;

    public Headerapdater(Context context, List<HorizontalUtil> menulistlist) {

        mContext = context;
        this.menulistlist = menulistlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<HorizontalUtil>();
        imageLoader = new Utils.ImageLoader(mContext);

        this.arraylist.addAll(menulistlist);
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
        final Viewholder holder;
        if (convertView == null) {

            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.horizontal_listview_item, null);


            //    holder.subimage = (ImageView) convertView.findViewById(R.id.sub_cat_imageView);

            holder.subimage = (ImageView) convertView.findViewById(R.id.hl_image_iv);
            holder.txt_id = (TextView) convertView.findViewById(R.id.txt_subcat);
            holder.txt_sub_cat_name = (TextView) convertView.findViewById(R.id.txt_subcat_name);


            convertView.setTag(holder);


        } else {

            holder = (Viewholder) convertView.getTag();
        }

        holder.txt_id.setText(menulistlist.get(position).getSubCatId());
        holder.txt_sub_cat_name.setText(menulistlist.get(position).getSubName());

        imageLoader.DisplayImage(menulistlist.get(position)
                .getSubImgaepath(), holder.subimage);

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
              /*  Home.CATNAME="";
                Home.CATID="";
                Intent inti = new Intent(mContext, SearchCategories.class);
                inti.putExtra("subcatid", menulistlist.get(position).getSubCatId());
                inti.putExtra("subcatname", menulistlist.get(position).getSubName());
                inti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(inti);*/
                Home.CATID= catid;
                Intent intent=new Intent(mContext,OutlateListingPage.class);
                intent.putExtra("subcatid", menulistlist.get(position).getSubCatId());
                intent.putExtra("subcatName", menulistlist.get(position).getSubName());

                intent.putExtra("subcatgiri_flag",8);
                intent.putExtra("catid","");
                intent.putExtra("latitude", Constant.LATITUDE);
                intent.putExtra("longitude",Constant.LONGITUDE);
                intent.putExtra("home_header_adap_flag",10);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class Viewholder {
        TextView txt_sub_cat_name;
        TextView txt_id;
        ImageView subimage, iv_right_arrow;
        Button add_btn, quntity_btn;

    }


}

