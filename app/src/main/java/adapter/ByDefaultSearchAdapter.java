package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.assan.R;
import java.util.ArrayList;
import java.util.List;
import DTO.OutletListModel;
import Utils.Constant;

/**
 * Created by Digi-T25 on 5/20/2016.
 */
public class ByDefaultSearchAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    Viewholder holder;
    private ArrayList<String>arraylist;
    public ByDefaultSearchAdapter(Context context, ArrayList<String>arraylist) {
        this.arraylist = arraylist;
        mContext = context;
        inflater = LayoutInflater.from(mContext);

    }
    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.search_location_item, null);
            holder.tv_location_name = (TextView) convertView.findViewById(R.id.tv_location_name);
            holder.iv_green = (ImageView) convertView.findViewById(R.id.iv_green);
            holder.iv_red = (ImageView) convertView.findViewById(R.id.iv_red);
            if (position == 0 ){
              //  holder.iv_red.setVisibility(View.VISIBLE);
                if (Constant.Check_current_status)
                holder.iv_green.setVisibility(View.VISIBLE);

            }
            else if (arraylist.get(position).equals(Constant.SELECTED_LOCATION)){
               // holder.iv_green.setVisibility(View.VISIBLE);
                holder.iv_red.setVisibility(View.VISIBLE);

            }
            convertView.setTag(holder);


        } else {

            holder = (Viewholder) convertView.getTag();
        }

        holder.tv_location_name.setText(arraylist.get(position));

        /*else
        {
            holder.iv_red.setVisibility(View.GONE);
            holder.iv_red.setVisibility(View.GONE);
        }*/


     /*  *//**//* convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();

            }
        });*/
          return convertView;
    }
    class Viewholder {
        TextView tv_location_name;
        ImageView iv_green,iv_red;


    }
}
