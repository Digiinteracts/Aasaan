package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.R;

import java.util.List;

import DTO.GallaryDTO;
import DTO.OutletDTO;
import DTO.OutletListModel;
import ImageLoding.Loader;


/**
 * Created by Digi-T25 on 5/16/2016.
 */
public class ReviewListAdapter extends BaseAdapter
{
    Loader loader;
    Context mContext;
    LayoutInflater inflater;
    private List<GallaryDTO> arraylist;
    Viewholder holder;
    public ReviewListAdapter(Context context, List<GallaryDTO> arraylist) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        loader = new Loader(mContext);
        this.arraylist = arraylist;

        }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arraylist.size();
        //return 1;
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
            loader = new Loader(mContext);
            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.review_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_review_name);
            holder.where= (TextView)convertView.findViewById(R.id.tv_review_for_store);
            holder.comment= (TextView)convertView.findViewById(R.id.tv_comment);
            holder.rating= (TextView)convertView.findViewById(R.id.tv_rating);
            holder.time= (TextView)convertView.findViewById(R.id.tv_time_ago);
            holder.image= (ImageView)convertView.findViewById(R.id.iv_review_image);

            convertView.setTag(holder);
        } else {

            holder = (Viewholder) convertView.getTag();
        }
        float rate = Float.parseFloat(arraylist.get(position).getRating());
        if (rate<=2){
            holder.rating.setBackgroundResource(R.color.red_review);
        }
       else if (2<rate&&4>=rate){
            holder.rating.setBackgroundResource(R.color.yellow_review);
        }
        else  {
            holder.rating.setBackgroundResource(R.color.green);
        }
        loader.displayImage(arraylist.get(position).getImage(), holder.image);
        holder.name.setText(arraylist.get(position).getName());
        holder.where.setText(arraylist.get(position).getAddress());
        holder.rating.setText(arraylist.get(position).getRating());
        holder.time.setText(arraylist.get(position).getAgo());
        holder.comment.setText(arraylist.get(position).getReview());
       /* convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Home.CATNAME=menulistlist.get(position).getSubName();
                Home.CATID= menulistlist.get(position).getSubCatId();

                Intent inti = new Intent(mContext, MaintenanceActivity.class);
                inti.putExtra("id", menulistlist.get(position).getSubCatId());
                inti.putExtra("name", menulistlist.get(position).getSubName());
                inti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(inti);



            }
        });*/

        return convertView;
    }

    class Viewholder {
       TextView name,where,rating,comment,time;
        ImageView image;


    }


}
