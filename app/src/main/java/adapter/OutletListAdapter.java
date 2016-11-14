package adapter;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assan.MapsActivity;
import com.assan.R;
import java.util.List;
import DTO.OutletListModel;
import ImageLoding.Loader;


/**
 * Created by Digi-T25 on 5/16/2016.
 */
public class OutletListAdapter extends BaseAdapter
{
    Loader loader;
    Context mContext;
    LayoutInflater inflater;
    private List<OutletListModel> arraylist;
    Viewholder holder;
    String rating_string = "";

    public OutletListAdapter(Context context, List<OutletListModel> arraylist) {
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) {
            loader = new Loader(mContext);
            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.searchcat_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.text_cat_name);
            holder.location= (TextView)convertView.findViewById(R.id.text_address);
            holder.type= (TextView)convertView.findViewById(R.id.text_market_type);
            holder.rating= (TextView)convertView.findViewById(R.id.txt_rating);
            holder.image= (ImageView)convertView.findViewById(R.id.iv_item_image);
            holder.iv_mobile= (ImageView)convertView.findViewById(R.id.iv_mobile);
            holder.time= (TextView)convertView.findViewById(R.id.txt_distance);
            convertView.setTag(holder);
        } else {

            holder = (Viewholder) convertView.getTag();
        }
        rating_string = arraylist.get(position).getOutlet_rating().substring(0,arraylist.get(position).getOutlet_rating().length()-1);
        holder.name.setText(arraylist.get(position).getOutlet_name());
        holder.time.setText(arraylist.get(position).getOutlet_time());
        holder.type.setText(arraylist.get(position).getOutlet_type());
        holder.rating.setText(rating_string);
        holder.location.setText(arraylist.get(position).getOutlet_location());
        loader.displayImage(arraylist.get(position).getImage(), holder.image);

        float ratingno =Float.valueOf(rating_string);
        if (ratingno <= 2){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(3);
            drawable.setStroke(0, Color.parseColor("#c4171d"));
            drawable.setColor(Color.parseColor("#c4171d"));
            holder.rating.setBackgroundDrawable(drawable);
        }
        else if (ratingno> 2 && ratingno <4){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(3);
            drawable.setStroke(0, Color.parseColor("#f8a820"));
            drawable.setColor(Color.parseColor("#f8a820"));
            holder.rating.setBackgroundDrawable(drawable);
        }
        else if (ratingno>=4){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(3);
            drawable.setStroke(0, Color.parseColor("#009812"));
            drawable.setColor(Color.parseColor("#009812"));
            holder.rating.setBackgroundDrawable(drawable);
        }

        holder.name.setTypeface(setfont("OpenSans-Bold.ttf"));
        holder.location.setTypeface(setfont("OpenSans-Regular.ttf"));
        holder.type.setTypeface(setfont("OpenSans-Regular.ttf"));
        holder.time.setTypeface(setfont("OpenSans-Regular.ttf"));

        onclickItem(holder,position);

    return convertView;
    }

    class Viewholder {
       TextView name,type,rating,location,time,text_gray_color;
        ImageView image,iv_mobile;

    }

    public void onclickItem(Viewholder holder, final int position){

        holder.iv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.phone_dialog_box);

                LinearLayout lin = (LinearLayout) dialog.findViewById(R.id.layout);

                String[] items = arraylist.get(position).getPhone_no().split(",");
                for (final String item : items) {

                    TextView dynamicTextView = new TextView(mContext);

                    View view = new View(mContext);
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,1));
                    view.setBackgroundColor(Color.GRAY);

                    dynamicTextView.setTextSize(22);
                    dynamicTextView.setGravity(Gravity.CENTER);
                    dynamicTextView.setTextColor(Color.parseColor("#007AFF"));
                    dynamicTextView.setPadding(0,8,0,8);
                    dynamicTextView.setText(item);

                    lin.addView(view);
                    lin.addView(dynamicTextView);

                    dynamicTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:"+item));
                            try {
                                mContext.startActivity(callIntent);
                            }
                            catch (android.content.ActivityNotFoundException ex){
                                Toast.makeText(mContext,"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });

                }

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                dialog.show();
            }
        });


        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(mContext,MapsActivity.class);
                mContext.startActivity(inte);
            }
        });
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(mContext.getAssets(),"font/"+font);
    }
}
