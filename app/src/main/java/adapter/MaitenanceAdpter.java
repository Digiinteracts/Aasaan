package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.assan.R;

/**
 * Created by Digi-T1 on 4/15/2016.
 */
public class MaitenanceAdpter extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private Integer[] imageid;
    private Activity context;

    public MaitenanceAdpter(Activity context, String[] names, String[] desc, Integer[] imageid) {
        super(context, R.layout.maintenance_list_item, names);
        this.context = context;
        this.names = names;
        this.desc = desc;
        this.imageid = imageid;

    }
//
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.maintenance_list_item, null, true);

     ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);


     //   image.setImageResource(imageid[position]);
        return  listViewItem;
    }
}
