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
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.OutletDetails;
import com.assan.R;

import java.util.ArrayList;
import java.util.List;

import DTO.searchhvDTO;
import ImageLoding.Loader;
import Utils.CommonUtils;

/**
 * Created by Sonu Saini on 5/10/2016.
 */
public class SearchHVAdapter extends BaseAdapter {
    Loader loader;

    ProgressDialog pDialog;
    Bitmap bitmap;
    Context mContext;
    LayoutInflater inflater;
    private List<searchhvDTO> menulistlist = null;
    private ArrayList<searchhvDTO> arraylist;
    Viewholder holder;

    public SearchHVAdapter(Context context, List<searchhvDTO> menulistlist) {

        mContext = context;
        this.menulistlist = menulistlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<searchhvDTO>();
        loader = new Loader(mContext);
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

        if (convertView == null) {

            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.search_cat_horzontallistview, null);


            //    holder.subimage = (ImageView) convertView.findViewById(R.id.sub_cat_imageView);

            holder.subimage = (ImageView) convertView.findViewById(R.id.iv_hv_image);

            holder.txt_sub_cat_name = (TextView) convertView.findViewById(R.id.txt_hv_name);


            convertView.setTag(holder);


        } else {

            holder = (Viewholder) convertView.getTag();
        }

        holder.txt_sub_cat_name.setText(menulistlist.get(position).getName());

        loader.displayImage(menulistlist.get(position)
                .getImage(), holder.subimage);


        Log.e("imag", menulistlist.get(position)
                .getImage());
      //  CommonUtils.showToast(mContext,menulistlist.get(position).getImage());

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(mContext, OutletDetails.class);
                intent.putExtra("id",arraylist.get(position).getId());
                intent.putExtra("outletdetailpage_hlist_adapter",10);
                mContext.startActivity(intent);


            }
        });

        return convertView;
    }

    class Viewholder {
        TextView txt_sub_cat_name;
        TextView txt_id;
        ImageView subimage;

    }
}