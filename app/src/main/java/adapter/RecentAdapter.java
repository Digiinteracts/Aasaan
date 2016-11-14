package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assan.MapsActivity;
import com.assan.R;

import java.util.List;

import DTO.RecentOutlet;
import ImageLoding.Loader;
import model.RecentViewHolder;

/**
 * Created by Digi T3 on 7/19/2016.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentViewHolder> {

    List<RecentOutlet> arraylist = null;
    Context context;
    LayoutInflater inflater;
    Loader loader;

    public RecentAdapter(Context context , List<RecentOutlet> item){

        this.context  = context;
        this.arraylist = item;
        inflater=LayoutInflater.from(context);
        loader = new Loader(context);
    }
    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.searchcat_list_item, parent, false);

        RecentViewHolder viewHolder=new RecentViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecentViewHolder holder, final int position) {

        holder.name.setText(arraylist.get(position).getRecent_name());
        holder.time.setText(arraylist.get(position).getRecent_time());
        holder.type.setText(arraylist.get(position).getRecentt_type());

        holder.name.setTypeface(setfont("OpenSans-Bold.ttf"));
        String rating_string = arraylist.get(position).getRecent_rating().substring(0,arraylist.get(position).getRecent_rating().length()-1);

        holder.rating.setText(rating_string);
        holder.location.setText(arraylist.get(position).getRecent_location());
        loader.displayImage(arraylist.get(position).getImage(), holder.image);

        float ratingno =Float.valueOf(rating_string);
        if (ratingno <= 2){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(0, Color.parseColor("#c4171d"));
            drawable.setColor(Color.parseColor("#c4171d"));
            holder.rating.setBackgroundDrawable(drawable);
        }
        else if (ratingno> 2 && ratingno <4){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(0, Color.parseColor("#f8a820"));
            drawable.setColor(Color.parseColor("#f8a820"));
            holder.rating.setBackgroundDrawable(drawable);
        }
        else if (ratingno>=4){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(0, Color.parseColor("#009812"));
            drawable.setColor(Color.parseColor("#009812"));
            holder.rating.setBackgroundDrawable(drawable);
        }
        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(context,MapsActivity.class);
                inte.putExtra("lat",arraylist.get(position).getRecent_lat());
                inte.putExtra("long",arraylist.get(position).getRecent_long());
                context.startActivity(inte);
            }
        });

        holder.iv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.phone_dialog_box);

                LinearLayout lin = (LinearLayout) dialog.findViewById(R.id.layout);

                String[] items = arraylist.get(position).getRecent_phonNO().split(",");
                for (final String item : items) {

                    TextView dynamicTextView = new TextView(context);

                    View view = new View(context);
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
                                context.startActivity(callIntent);
                            }
                            catch (android.content.ActivityNotFoundException ex){
                                Toast.makeText(context,"yourActivity is not founded",Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        if (arraylist.size()>20)
            return 20;
        else
            return arraylist.size();
    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(context.getAssets(),"font/"+font);
    }
}
