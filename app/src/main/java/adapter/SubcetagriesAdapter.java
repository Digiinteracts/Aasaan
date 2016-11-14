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

import java.util.ArrayList;
import java.util.List;

import com.assan.Home;
import com.assan.OutlateListingPage;
import com.assan.R;

import Utils.SubCatUtil;

/**
 * Created by Digi-T1 on 4/26/2016.
 */
public class SubcetagriesAdapter extends BaseAdapter {
    Utils.ImageLoader imageLoader;

    ProgressDialog pDialog;
    Bitmap bitmap;
    Context mContext;
    String catid,catname;
    LayoutInflater inflater;
    String latitude= "" ,longitude= "";
    private List<SubCatUtil> menulistlist = null;
    private ArrayList<SubCatUtil> arraylist;

    public SubcetagriesAdapter(Context context, List<SubCatUtil> menulistlist,String catid,String catname,String longitude,String latitude) {

        mContext = context;
        this.catid=catid;
        this.catname=catname;
        this.menulistlist = menulistlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SubCatUtil>();
        imageLoader = new Utils.ImageLoader(mContext);
        this.longitude = longitude;
        this.latitude = latitude;
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
            convertView = inflater.inflate(R.layout.maintenance_list_item, null);

           holder.cat_iv=(ImageView)convertView.findViewById(R.id.babyicon);

            holder.txt_sub_cat_name = (TextView) convertView.findViewById(R.id.sub_cat_name);


            holder.subimage = (ImageView) convertView.findViewById(R.id.sub_cat_imageView);
           // holder.iv_temp = (ImageView) convertView.findViewById(R.id.sub_cat_imageView1);
            holder.iv_right_arrow = (ImageView) convertView.findViewById(R.id.iv_rightarrow);


            convertView.setTag(holder);


        } else {

            holder = (Viewholder) convertView.getTag();
        }

        holder.txt_sub_cat_name.setText(menulistlist.get(position).getSubName());

       imageLoader.DisplayImage(menulistlist.get(position)
               .getSubImgaepath(), holder.subimage);
        imageLoader.DisplayImage(menulistlist.get(position)
                .getSubCatImgaepath(), holder.cat_iv);
       // holder.iv_temp.setBackgroundResource(R.drawable.bg_temp);
        holder.subimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*Home.CATNAME= catname;
                Home.CATID= catid;
                Intent inti = new Intent(mContext, SearchCategories.class);
                inti.putExtra("subcatid", menulistlist.get(position).getSubCatId());
                inti.putExtra("subcatname", menulistlist.get(position).getSubName());
                inti.putExtra("catid", catid);
                inti.putExtra("catname", catname);
                inti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(inti);
*/
                Home.CATID= catid;
                Intent intent=new Intent(mContext,OutlateListingPage.class);
                intent.putExtra("catid", catid);
                intent.putExtra("subcatid",menulistlist.get(position).getSubCatId());
                intent.putExtra("subcatName",menulistlist.get(position).getSubName());
                intent.putExtra("catName",catname);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("subcatgiri_flag",8);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                Log.e("subcatid","subcatid "+menulistlist.get(position).getSubCatId());
                mContext.startActivity(intent);

            }
        });

        return convertView;
    }

    class Viewholder {
        TextView txt_sub_cat_name;
        TextView txt_id;
        ImageView subimage,iv_temp,iv_right_arrow;
        Button add_btn, quntity_btn;
        ImageView cat_iv;


    }


    }


