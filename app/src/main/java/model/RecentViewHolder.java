package model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.R;

/**
 * Created by Digi T3 on 7/19/2016.
 */
public class RecentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public TextView name,type,rating,location,time;
   public ImageView image,iv_mobile;

    public RecentViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.text_cat_name);
        location= (TextView)itemView.findViewById(R.id.text_address);
        type= (TextView)itemView.findViewById(R.id.text_market_type);
        rating= (TextView)itemView.findViewById(R.id.txt_rating);
        image= (ImageView)itemView.findViewById(R.id.iv_item_image);
        iv_mobile= (ImageView)itemView.findViewById(R.id.iv_mobile);
        time= (TextView)itemView.findViewById(R.id.txt_distance);
    }

    @Override
    public void onClick(View v) {
    }
}
