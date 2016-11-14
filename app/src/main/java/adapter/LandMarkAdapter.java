package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.assan.R;

import java.util.List;

import DTO.LandMarkDTO;

/**
 * Created by DigiT-25 on 30-08-2016.
 */
public class LandMarkAdapter extends ArrayAdapter<LandMarkDTO> {

    private LayoutInflater inflater;
    Context context;
    List<LandMarkDTO> items;
    LandMarkDTO landMarkDTO;

    public LandMarkAdapter(Context context, List<LandMarkDTO> items, int textViewResourceId) {
        super(context, textViewResourceId, items);

        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView = null;
        view = inflater.inflate(R.layout.custom_spinner, null);
        landMarkDTO = items.get(position);
        TextView spinner_text = (TextView) view.findViewById(R.id.spinner_text);
        spinner_text.setTypeface(setfont("OpenSans-Regular.ttf"));
        spinner_text.setText(landMarkDTO.getName());
       // spinner_text.setTextSize(17);
        return view;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        //   LayoutInflater inflater = getLayoutInflater();
        View row = inflater.inflate(R.layout.customspinner2, parent, false);
        TextView make = (TextView) row.findViewById(R.id.text);

        make.setText(items.get(position).getName());
        //make.setTextSize(17);
        make.setTypeface(setfont("OpenSans-Regular.ttf"));
        return row;
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(context.getAssets(),"font/"+font);
    }


}
