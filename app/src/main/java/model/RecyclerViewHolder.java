package model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.R;

/**
 * Created by Sonu Saini 10/26/2015.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

   public TextView tv1,txt_id;
    public  com.assan.CircleImageView imageView;
    public  ImageView imageView2;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        tv1= (TextView) itemView.findViewById(R.id.textViewName);
        txt_id=(TextView)itemView.findViewById(R.id.text_xat_id);
        imageView2=(ImageView)itemView.findViewById(R.id.imageView2);

        imageView= (com.assan.CircleImageView) itemView.findViewById(R.id.imageView);

    }
}
