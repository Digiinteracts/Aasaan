package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.OutletDetails;
import com.assan.R;

import java.util.List;

import DTO.OutletListModel;
import ImageLoding.Loader;

/**
 * Created by Sonu Saini on 5/10/2016.
 */
public class OutletsListAdapterHlv extends BaseAdapter {
    Loader loader;
    Context mContext;
    LayoutInflater inflater;
    private List<OutletListModel> arraylist;
    Viewholder holder;

    public OutletsListAdapterHlv(Context context, List<OutletListModel> arraylist) {

        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = arraylist;
        loader = new Loader(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arraylist.get(position);
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

            holder.txt_sub_cat_name.setText(arraylist.get(position).getSponsered_name());
           // holder.name.setText(arraylist.get(position).getOutlet_name());
            loader.displayImage(arraylist.get(position).getSponsered_image(), holder.subimage);
            Log.e("IMAGE","URL "+arraylist.get(position).getSponsered_image());
        Log.e("text","URL "+arraylist.get(position).getSponsered_name());


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext, OutletDetails.class);
                intent.putExtra("id",arraylist.get(position).getSponsered_id());
                intent.putExtra("outletdetailpage_hlist_adapter",10);
                mContext.startActivity(intent);


            }
        });

        return convertView;
    }

    class Viewholder {
        TextView txt_sub_cat_name;
        ImageView subimage;

    }
}