package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.assan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 19-10-2016.
 */
public class SubscriptionSpinnerAdapter extends ArrayAdapter<String> {

    List<String> name = null;
    List<String> id = null;
    List<String> checkCategory = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    int items;
    public SubscriptionSpinnerAdapter(Context context, List<String> name,List<String> id,int items) {
        super(context,0, name);

        this.context = context;
        this.name = name;
        this.id = id;
        inflater = LayoutInflater.from(context);
        this.items = items;
        Log.e("ITMES ","S "+items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView = null;
        view = inflater.inflate(R.layout.custom_spinner, null);

        TextView spinner_text = (TextView) view.findViewById(R.id.spinner_text);
        spinner_text.setTypeface(setfont("OpenSans-Regular.ttf"));
        spinner_text.setText(name.get(position));
        //  spinner_text.setTextSize(17);
        return view;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        //   LayoutInflater inflater = getLayoutInflater();
        View row = inflater.inflate(R.layout.customspinner2, parent, false);
        TextView make = (TextView) row.findViewById(R.id.text);

        make.setText(name.get(position));
        //   make.setTextSize(17);
        make.setTypeface(setfont("OpenSans-Regular.ttf"));
        return row;
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(context.getAssets(),"font/"+font);
    }

    @Override
    public int getCount() {
        return items;
    }
}
