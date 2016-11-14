package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.R;

import java.util.List;

import DTO.OutletListModel;
import DTO.stattisticsModel;
import ImageLoding.Loader;

/**
 * Created by Sonu Saini -T1 on 4/22/2016.
 */

public class StatisticsAdapter extends ArrayAdapter<stattisticsModel> {
    Loader loader;
    Context mContext;
    LayoutInflater inflater;
    private List<stattisticsModel> arraylist;

    public StatisticsAdapter(Context context, List<stattisticsModel> arraylist ) {
        super(context,0,arraylist);
        mContext=context;

        inflater = LayoutInflater.from(mContext);
        loader = new Loader(mContext);
        this.arraylist = arraylist;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.statistics_item, null);
            TextView name = (TextView) convertView.findViewById(R.id.txt_axtivity);
            TextView point= (TextView)convertView.findViewById(R.id.txt_points);
            TextView count= (TextView)convertView.findViewById(R.id.txt_count);
            TextView total= (TextView) convertView.findViewById(R.id.txt_totale);

            if (position % 2 == 1) {
                name.setBackgroundResource(R.color.light_gray);
                point.setBackgroundResource(R.color.light_gray);
                count.setBackgroundResource(R.color.light_gray);
                total.setBackgroundResource(R.color.light_gray);
            } else {

            }



            name.setText(arraylist.get(position).getActivityname());
            point.setText(arraylist.get(position).getPoints());
            count.setText(arraylist.get(position).getCount());
            total.setText(arraylist.get(position).getTotal());

        }

        return convertView;
    }


}