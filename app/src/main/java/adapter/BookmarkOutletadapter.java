package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.OutletDetails;
import com.assan.R;

import java.util.ArrayList;
import java.util.List;

import DTO.SearchCategoriesDTO;
import Utils.CommonUtils;

/**
 * Created by sonusaini on 5/11/2016.
 */
public class BookmarkOutletadapter extends BaseAdapter {


    Utils.ImageLoader imageLoader;

    ProgressDialog pDialog;
    Bitmap bitmap;
    Context mContext;
    LayoutInflater inflater;
    private List<SearchCategoriesDTO> searchdto = null;
    private ArrayList<SearchCategoriesDTO> arraylist;
    String mobileno;
    public BookmarkOutletadapter(Context context, List<SearchCategoriesDTO> searchdto) {

        mContext = context;
        this.searchdto = searchdto;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchCategoriesDTO>();
        imageLoader = new Utils.ImageLoader(mContext);

        this.arraylist.addAll(searchdto);


    }


    @Override
    public int getCount() {
        return searchdto.size();
    }

    @Override
    public Object getItem(int position) {
        return searchdto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Viewholder holder;
        if (convertView == null) {
            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.searchcat_list_item, null);
            holder.subimage = (ImageView) convertView.findViewById(R.id.iv_item_image);
            holder.iv_mobile = (ImageView) convertView.findViewById(R.id.iv_mobile);
            holder.iv_bookmark = (ImageView) convertView.findViewById(R.id.iv_bookmark);
            holder.txt_address= (TextView)convertView.findViewById(R.id.text_address);
            holder.txt_distance= (TextView)convertView.findViewById(R.id.txt_distance);
            holder.txt_rating= (TextView)convertView.findViewById(R.id.txt_rating);
            holder.text_cat_name= (TextView)convertView.findViewById(R.id.text_cat_name);
            holder.text_market= (TextView)convertView.findViewById(R.id.text_market);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        if (searchdto.get(position).getBookmark().equals("no")){
            holder.iv_bookmark.setBackgroundResource(R.drawable.bookmarkd);
        }else {
            holder.iv_bookmark.setBackgroundResource(R.drawable.fav);
        }
        holder.txt_address.setText(searchdto.get(position).getLocation());
        holder.txt_distance.setText(searchdto.get(position).getDistance());
        holder.text_market.setText(searchdto.get(position).getMarketType());
        holder.txt_rating.setText(searchdto.get(position).getrating());
        holder.text_cat_name.setText(searchdto.get(position).getName());

        mobileno=searchdto.get(position).getMobile();
        imageLoader.DisplayImage(searchdto.get(position)
                .getImage(), holder.subimage);







        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent inti = new Intent(mContext, OutletDetails.class);
                inti.putExtra("id", searchdto.get(position).getId());
                //  inti.putExtra("name", menulistlist.get(position).getSubName());

                inti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(inti);
                CommonUtils.showToast(mContext, searchdto.get(position).getId());

            }
        });


        return convertView;
    }

    class Viewholder {
        TextView txt_distance,txt_rating;
        TextView txt_address,text_cat_name,text_market;
        ImageView subimage,iv_right_arrow,iv_bookmark,iv_mobile;
        Button add_btn, quntity_btn;

    }
}
