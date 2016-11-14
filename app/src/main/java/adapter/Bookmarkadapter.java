package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.assan.OutletDetails;
import com.assan.R;

/**
 * Created by Sonu Saini on 4/15/2016.
 */
public class Bookmarkadapter extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private Integer[] imageid;
    private Activity context;

    public Bookmarkadapter(Activity context, String[] names, String[] desc, Integer[] imageid) {
        super(context, R.layout.list_layout, names);
        this.context = context;
        this.names = names;
        this.desc = desc;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.bookmark_list_item, null, true);

        ImageView image = (ImageView) listViewItem.findViewById(R.id.iv_item_image);


        image.setImageResource(imageid[position]);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inte=new Intent(getContext(), OutletDetails.class);
                getContext().startActivity(inte);
            }
        });

        return  listViewItem;
    }
}