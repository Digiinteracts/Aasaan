package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.assan.R;

import java.util.List;

import DTO.OutletListModel;

/**
 * Created by Digi-T25 on 5/20/2016.
 */
public class LocationSearchAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    Viewholder holder;
    private List<OutletListModel> arraylist;
    public LocationSearchAdapter(Context context, List<OutletListModel> arraylist) {
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
            convertView.setTag(holder);


        } else {

            holder = (Viewholder) convertView.getTag();
        }

        Log.e("catname ","adapter "+arraylist.get(position).getOutlet_name());
        holder.tv_location_name.setText(arraylist.get(position).getOutlet_name());

          return convertView;
    }
    class Viewholder {
        TextView tv_location_name;


    }
}
