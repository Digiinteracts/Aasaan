package adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assan.MapsActivity;
import com.assan.OutletDetails;
import com.assan.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DTO.RecentOutlet;
import DTO.SearchCategoriesDTO;
import Utils.CommonUtils;
import Utils.Prefrence;

/**
 * Created by Sonu Saini  on 5/4/2016.
 */
public class SearchCategoriesAdapter extends BaseAdapter {


    Utils.ImageLoader imageLoader;

    ProgressDialog pDialog;
    Bitmap bitmap;
    Context mContext;
    LayoutInflater inflater;
    private List<SearchCategoriesDTO> searchdto = null;
    private ArrayList<SearchCategoriesDTO> arraylist;
    String mobileno;
    Prefrence sharpref;

    public SearchCategoriesAdapter(Context context, List<SearchCategoriesDTO> searchdto) {

        mContext = context;
        this.searchdto = searchdto;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchCategoriesDTO>();
        imageLoader = new Utils.ImageLoader(mContext);

        this.arraylist.addAll(searchdto);
        sharpref = new Prefrence();

    }


    @Override
    public int getCount() {
        return searchdto.size();
    }

    @Override
    public Object getItem(int position) {
        return searchdto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Viewholder holder;
        if (convertView == null) {
            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.searchcat_list_item, null);
            holder.subimage = (ImageView) convertView.findViewById(R.id.iv_item_image);
            holder.iv_mobile = (ImageView) convertView.findViewById(R.id.iv_mobile);
            holder.iv_bookmark = (ImageView) convertView.findViewById(R.id.iv_bookmark);
            holder.txt_address= (TextView)convertView.findViewById(R.id.text_address);
            holder.txt_distance= (TextView)convertView.findViewById(R.id.txt_distance);
            holder.txt_rating= (TextView)convertView.findViewById(R.id.txt_rating);
            holder.text_cat_name= (TextView)convertView.findViewById(R.id.text_cat_name);
            holder.text_market= (TextView)convertView.findViewById(R.id.text_market_type);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        if (searchdto.get(position).getBookmark().equals("no")){
            holder.iv_bookmark.setBackgroundResource(R.drawable.bookmark);
        }else {
            holder.iv_bookmark.setBackgroundResource(R.drawable.fav);
        }
        holder.txt_address.setText(searchdto.get(position).getLocation());
        holder.txt_distance.setText(searchdto.get(position).getDistance());
        holder.text_market.setText(searchdto.get(position).getMarketType());
        holder.txt_rating.setText(searchdto.get(position).getrating());
        holder.text_cat_name.setText(searchdto.get(position).getName());
        holder.text_cat_name.setTypeface(setfont("OpenSans-Bold.ttf"));

        float ratingno =Float.valueOf(searchdto.get(position).getrating());
        if (ratingno <= 2){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(3);
            drawable.setStroke(0, Color.parseColor("#c4171d"));
            drawable.setColor(Color.parseColor("#c4171d"));
            holder.txt_rating.setBackgroundDrawable(drawable);
        }
        else if (ratingno> 2 && ratingno <4){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(3);
            drawable.setStroke(0, Color.parseColor("#f8a820"));
            drawable.setColor(Color.parseColor("#f8a820"));
            holder.txt_rating.setBackgroundDrawable(drawable);
        }
        else if (ratingno>=4){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(3);
            drawable.setStroke(0, Color.parseColor("#009812"));
            drawable.setColor(Color.parseColor("#009812"));
            holder.txt_rating.setBackgroundDrawable(drawable);
        }

        mobileno=searchdto.get(position).getMobile();
        imageLoader.DisplayImage(searchdto.get(position)
                .getImage(), holder.subimage);

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        final String date = df.format(Calendar.getInstance().getTime());

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent inti = new Intent(mContext, OutletDetails.class);
                Bundle b =new Bundle();
                b.putString("id",searchdto.get(position).getId());
                b.putInt("SearchCategeriAdapter_flag",12);
                inti.putExtras(b);
                inti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(inti);
               // CommonUtils.showToast(mContext,searchdto.get(position).getId());

                RecentOutlet recentOutlet = new RecentOutlet();
                recentOutlet.setRecent_id(searchdto.get(position).getId());
                recentOutlet.setRecent_name(searchdto.get(position).getName());
                recentOutlet.setRecentt_type(searchdto.get(position).getMarketType());
                recentOutlet.setRecent_time(searchdto.get(position).getDistance());
                recentOutlet.setRecent_rating(searchdto.get(position).getrating());
                recentOutlet.setRecent_location(searchdto.get(position).getLocation());
                recentOutlet.setImage(searchdto.get(position).getImage());
                recentOutlet.setRecent_lat(searchdto.get(position).getSearchcat_lat());
                recentOutlet.setRecent_long(searchdto.get(position).getSearchcat_long());
                recentOutlet.setRecent_phonNO(searchdto.get(position).getMobile());

                recentOutlet.setCurrentTime(date);
                recentOutlet.setData("");

                sharpref.addRecentData(mContext,recentOutlet);
                sharpref.saveRlist(mContext,"DATA");


            }
        });

        onclickItem(holder,position);
        return convertView;
    }

    class Viewholder {
        TextView txt_distance,txt_rating;
        TextView txt_address,text_cat_name,text_market;
        ImageView subimage,iv_right_arrow,iv_bookmark,iv_mobile;
        Button add_btn, quntity_btn;

    }

    public void onclickItem(Viewholder holder, final int position){
        holder.txt_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(mContext,MapsActivity.class);
                inte.putExtra("lat",arraylist.get(position).getSearchcat_lat());
                inte.putExtra("long",arraylist.get(position).getSearchcat_long());
                mContext.startActivity(inte);
            }
        });

        holder.iv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.phone_dialog_box);

                LinearLayout lin = (LinearLayout) dialog.findViewById(R.id.layout);

                String[] items = arraylist.get(position).getMobile().split(",");
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

    }

    public Typeface setfont(String font){
        return Typeface.createFromAsset(mContext.getAssets(),"font/"+font);
    }
}
